/*
 * MIT License
 *
 * Copyright (c) 2022 - present Gregor Anders
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
package io.github.gregoranders.gradle.dependencies.tooling;

import io.github.gregoranders.gradle.dependencies.tooling.model.mapper.ConfigurationContainerMapper;
import io.github.gregoranders.gradle.dependencies.tooling.model.mapper.ConfigurationMapper;
import io.github.gregoranders.gradle.dependencies.tooling.model.mapper.DependencyMapper;
import io.github.gregoranders.gradle.dependencies.tooling.model.mapper.DependencySetMapper;
import io.github.gregoranders.gradle.dependencies.tooling.model.mapper.ProjectMapper;
import org.gradle.api.NonNullApi;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry;

import javax.inject.Inject;

@NonNullApi
public final class DependenciesPlugin implements Plugin<Project> {

    private final DependencyMapper dependencyMapper = new DependencyMapper();

    private final DependencySetMapper dependencySetMapper = new DependencySetMapper(dependencyMapper);

    private final ConfigurationMapper configurationMapper = new ConfigurationMapper(dependencySetMapper);

    private final ConfigurationContainerMapper configurationContainerMapper = new ConfigurationContainerMapper(configurationMapper);

    private final ProjectMapper projectMapper = new ProjectMapper(configurationContainerMapper);

    private final ToolingModelBuilderRegistry toolingModelBuilderRegistry;

    @Inject
    public DependenciesPlugin(final ToolingModelBuilderRegistry registry) {
        toolingModelBuilderRegistry = registry;
    }

    @Override
    public void apply(final Project project) {
        toolingModelBuilderRegistry.register(new DependenciesModelBuilder(projectMapper));
    }
}
