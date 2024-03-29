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
package io.github.gregoranders.gradle.dependencies.tooling.model

import io.github.gregoranders.gradle.dependencies.tooling.model.api.Configuration
import io.github.gregoranders.gradle.dependencies.tooling.model.api.Dependency
import io.github.gregoranders.gradle.dependencies.tooling.model.api.ImmutableConfiguration
import io.github.gregoranders.gradle.dependencies.tooling.model.api.ImmutableDependency
import io.github.gregoranders.gradle.dependencies.tooling.model.api.ImmutableProject
import io.github.gregoranders.gradle.dependencies.tooling.model.api.Project
import spock.lang.*

@Narrative('''
> # As a user I would like to be able to get a custom [model][model-url] from a [Gradle][gradle-url] build to collect dependencies.

[gradle-url]: https://gradle.org
[model-url]: https://github.com/bmuschko/tooling-api-custom-model
''')
@Subject([Project])
@See([
    'https://gradle.org',
    'https://github.com/bmuschko/tooling-api-custom-model',
    'https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html',
    'https://github.com/gregoranders/gradle-dependencies-check/blob/main/src/main/java/io/github/gregoranders/gradle/dependencies/tooling/model/api/Project.java',
])
@Issue([
    '2'
])
class ProjectSpec extends Specification {

    final String dependencyGroup = 'testGroup'

    final String dependencyName = 'testName'

    final String dependencyVersion = 'testVersion'

    final String configurationName = 'testConfiguration'

    final String projectGroup = 'testProjectGroup'

    final String projectName = 'testProjectName'

    final String projectDescription = 'testProjectDescription'

    final String projectVersion = 'testProjectVersion'

    final String projectPath = 'testProjectPath'

    Dependency dependency = ImmutableDependency.of(dependencyGroup, dependencyName, dependencyVersion)

    Configuration configuration = ImmutableConfiguration.of(configurationName, Set.of(dependency))

    Project subProject = ImmutableProject.of(projectGroup, projectName, projectDescription, projectVersion, projectPath, Set.of(configuration), Set.of())

    @Subject
    Project testSubject = ImmutableProject.of(projectGroup, projectName, projectDescription, projectVersion, projectPath, Set.of(configuration), Set.of(subProject))

    def 'should return expected project group'() {
        expect: 'name to equal "testProjectGroup"'
            testSubject.group() == projectGroup
    }

    def 'should return expected project name'() {
        expect: 'name to equal "testProjectName"'
            testSubject.name() == projectName
    }

    def 'should return expected project description'() {
        expect: 'name to equal "testProjectDescription"'
            testSubject.description() == projectDescription
    }

    def 'should return expected project version'() {
        expect: 'name to equal "testProjectVersion"'
            testSubject.version() == projectVersion
    }

    def 'should return expected project path'() {
        expect: 'name to equal "testProjectPath"'
            testSubject.path() == projectPath
    }

    def 'should return a set with one configuration'() {
        expect: 'set of configurations should have one element'
            testSubject.configurations().size() == 1
    }

    def 'should contain expected configuration'() {
        expect: 'should equal provided configuration'
            testSubject.configurations()[0] == configuration
    }

    def 'should contain expected sub project'() {
        expect: 'should equal sub project'
            testSubject.subProjects()[0] == subProject
    }
}
