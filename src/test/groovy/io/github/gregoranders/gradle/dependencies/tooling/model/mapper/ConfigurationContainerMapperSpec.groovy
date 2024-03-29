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
package io.github.gregoranders.gradle.dependencies.tooling.model.mapper

import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.DependencySet
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.See
import spock.lang.Subject

import java.util.stream.Stream

@Narrative('''
> # As a user I would like to map a [Gradle][gradle-url] [ConfigurationContainer][configurationContainer-url] to a set of [model][model-url] using a custom [mapper][mapper-url].

[gradle-url]: https://gradle.org
[configurationContainer-url]: https://docs.gradle.org/current/javadoc/org/gradle/api/artifacts/ConfigurationContainer.html
[model-url]: https://github.com/gregoranders/gradle-dependencies-check/blob/main/src/main/java/io/github/gregoranders/gradle/dependencies/tooling/model/api/Configuration.java
[mapper-url]: https://github.com/gregoranders/gradle-dependencies-check/blob/main/src/main/java/io/github/gregoranders/gradle/dependencies/tooling/model/mapper/ConfigurationContainerMapper.java
''')
@Subject([ConfigurationContainerMapper, io.github.gregoranders.gradle.dependencies.tooling.model.api.Configuration])
@See([
    'https://docs.gradle.org/current/javadoc/org/gradle/api/artifacts/Configuration.html',
    'https://docs.gradle.org/current/javadoc/org/gradle/api/artifacts/ConfigurationContainer.html',
    'https://github.com/gregoranders/gradle-dependencies-check/blob/main/src/main/java/io/github/gregoranders/gradle/dependencies/tooling/model/api/Configuration.java',
    'https://github.com/gregoranders/gradle-dependencies-check/blob/main/src/main/java/io/github/gregoranders/gradle/dependencies/tooling/model/mapper/ConfigurationContainerMapper.java',
])
@Issue([
    '2'
])
class ConfigurationContainerMapperSpec extends MapperSpecification {

    def dependencyMapper = new DependencyMapper()

    def dependencySetMapper = new DependencySetMapper(dependencyMapper)

    def configurationMapper = new ConfigurationMapper(dependencySetMapper)

    @Subject
    def testSubject = new ConfigurationContainerMapper(configurationMapper)

    def 'should map a gradle configuration container preserving the order'() {
        given: 'a gradle configuration A'
            def configurationA = Mock(Configuration)
        and: 'a gradle dependency B'
            def configurationB = Mock(Configuration)
        and: 'a gradle configuration container containing A and B'
            def configurationContainer = Mock(ConfigurationContainer)
        when: 'the unit under test maps this container'
            def set = testSubject.map(configurationContainer)
        then: 'following interactions should be executed'
            interaction {
                1 * configurationContainer.stream() >> Stream.of(configurationA, configurationB)

                1 * configurationA.getName() >> 'configurationAName'
                1 * configurationA.getDependencies() >> {
                    def dependencySet = Mock(DependencySet)
                    1 * dependencySet.stream() >> Stream.empty()
                    dependencySet
                }
                1 * configurationB.getName() >> 'configurationBName'
                1 * configurationB.getDependencies() >> {
                    def dependencySet = Mock(DependencySet)
                    1 * dependencySet.stream() >> Stream.empty()
                    dependencySet
                }
            }
        and: 'the returned set should contain two mapped configurations'
            set.size() == 2
        and: 'the first configuration should be of the expected type'
            set[0] instanceof io.github.gregoranders.gradle.dependencies.tooling.model.api.Configuration
        and: 'have the expected values'
            set[0].name() == 'configurationAName'
            set[0].dependencies().size() == 0
        and: 'the second configuration should be of the expected type'
            set[1] instanceof io.github.gregoranders.gradle.dependencies.tooling.model.api.Configuration
        and: 'have the expected values'
            set[1].name() == 'configurationBName'
            set[1].dependencies().size() == 0
        and: 'no exceptions should be thrown'
            noExceptionThrown()
    }
}
