/*
 * MIT License
 *
 * Copyright (c) 2022 Gregor Anders
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.github.gregoranders.gradle.dependencies.utilities;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.gregoranders.gradle.dependencies.tooling.DependenciesPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.internal.impldep.org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class InitScriptCreator implements AutoCloseable {

    private final String initScriptPath;

    private Path temporaryScriptPath;

    public InitScriptCreator(final String scriptPath) {
        initScriptPath = scriptPath;
    }

    @Override
    public void close() throws Exception {
        if (temporaryScriptPath != null) {
            Files.delete(temporaryScriptPath);
        }
    }

    public String getAbsolutePath() throws IOException {
        return getTemporaryInitScriptPath();
    }

    private String getTemporaryInitScriptPath() throws IOException {
        return createTemporaryInitScript(getPluginPath(), initScriptPath);
    }

    private String createTemporaryInitScript(final String pluginPath, final String path) throws IOException {
        temporaryScriptPath = createSecureTempFile();

        final List<String> lines = splitStringIntoLines(FileUtilities.getResourceAsString(getClass(), path));
        final String initScriptContent = getInitScriptContentWithReplacedPluginPath(pluginPath, lines);

        return FileUtilities.getAbsolutePathAsString(Files.writeString(temporaryScriptPath, initScriptContent, StandardCharsets.UTF_8));
    }

    @SuppressWarnings({"PMD.LawOfDemeter", "java:S5443"})
    private Path createSecureTempFile() throws IOException {
        final Path path = Files.createTempFile("gradle-dependencies-plugin-init", ".gradle");
        setFilePermissions(path.toFile());
        return path;
    }

    @SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_BAD_PRACTICE")
    @SuppressWarnings({"java:S899", "ResultOfMethodCallIgnored"})
    private void setFilePermissions(final File file) {
        file.setReadable(true, true);
        file.setWritable(true, true);
        file.setExecutable(false, true);
    }

    private String getInitScriptContentWithReplacedPluginPath(final String pluginPath, final List<String> lines) {
        final StringBuilder stringBuilder = new StringBuilder();
        lines.forEach(line -> {
            final String replaced = replaceLine(line, "%%GRADLE_DEPENDENCIES_PLUGIN_PATH%%",
                FilenameUtils.separatorsToUnix(pluginPath));
            stringBuilder.append(replaceLine(replaced, "'%%GRADLE_DEPENDENCIES_PLUGIN%%'", getPluginCanonicalName()));
            stringBuilder.append(System.lineSeparator());
        });

        return stringBuilder.toString();
    }

    private String replaceLine(final String line, final String target, final String replacement) {
        return line.replace(target, replacement);
    }

    private String getPluginCanonicalName() {
        return DependenciesPlugin.class.getCanonicalName();
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private String getPluginPath() {
        final Class<? extends Plugin<Project>> aClass = DependenciesPlugin.class;
        final URL resource = aClass.getResource(aClass.getSimpleName() + ".class");
        final String url = Objects.requireNonNull(resource, "Class resource not found").toString();
        final String suffix = aClass.getCanonicalName().replace('.', '/') + ".class";
        final String base = url.substring(0, url.length() - suffix.length());
        return FileUtilities.removeLeadingPrefixes(base);
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private List<String> splitStringIntoLines(final String string) {
        return Arrays
            .stream(string.split("\\r?\\n"))
            .collect(Collectors.toList());
    }

}
