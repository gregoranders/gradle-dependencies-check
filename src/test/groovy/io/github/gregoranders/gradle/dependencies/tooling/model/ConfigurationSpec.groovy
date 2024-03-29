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
import spock.lang.*

@Narrative('''
> # As a user I would like to be able to get a custom [model][model-url] from a [Gradle][gradle-url] build to collect dependencies.

[gradle-url]: https://gradle.org
[model-url]: https://github.com/bmuschko/tooling-api-custom-model
''')
@Subject([Configuration])
@See([
    'https://gradle.org',
    'https://github.com/bmuschko/tooling-api-custom-model',
    'https://docs.gradle.org/current/javadoc/org/gradle/api/artifacts/Configuration.html',
    'https://github.com/gregoranders/gradle-dependencies-check/blob/main/src/main/java/io/github/gregoranders/gradle/dependencies/tooling/model/api/Configuration.java'
])
@Issue([
    '2'
])
class ConfigurationSpec extends Specification {

    final String dependencyGroup = 'testGroup'

    final String dependencyName = 'testName'

    final String dependencyVersion = 'testVersion'

    final String configurationName = 'testConfiguration'

    Dependency dependency = ImmutableDependency.of(dependencyGroup, dependencyName, dependencyVersion)

    @Subject
    Configuration testSubject = ImmutableConfiguration.of(configurationName, Set.of(dependency))

    def 'should return expected configuration name'() {
        expect: 'name to equal "testConfiguration"'
            testSubject.name() == configurationName
    }

    def 'should return a set with one dependency'() {
        expect: 'set of dependencies should have one element'
            testSubject.dependencies().size() == 1
    }

    def 'should contain expected dependency'() {
        expect: 'should equal provided dependency'
            testSubject.dependencies()[0] == dependency
    }
}
