# Contributing Guide

Contributing to `gradle-dependencies-check` is fairly easy. This document shows you how to get the project, run all
provided tests and generate a production-ready build.

It also covers provided tasks that help you develop with `gradle-dependencies-check`.

## Dependencies

To make sure that the following instructions work, please install the following dependencies on you machine:

- JAVA 11
- Git

## Installation

To get the source of `gradle-dependencies-check`, clone the git repository via:

```shell
git clone git@github.com:gregoranders/gradle-dependencies-check.git
```

This will clone the complete source to your local machine. Navigate to the project folder and install all needed
dependencies via **gradle**:

```shell
./gradlew clean check build
```

This commands installs everything which is required for building and testing the project.

## Testing

### Unit testing using [Spock][spock-url]

`./gradle clean test` executes the unit tests

## Quality checks

`./gradle clean check` executes various code quality checks

## Building

`./gradle clean build` executes the build

## Contributing/Submitting changes

- Check out a new branch based on <code>development</code> and name it to what you intend to do:
  - Example:

    ```shell
    git checkout -b BRANCH_NAME origin/development
    ```

    If you get an error, you may need to fetch <code>development</code> first by using

    ```shell
    git remote update && git fetch -p
    ```

  - Use one branch per fix/feature
- Make your changes
  - Make sure to provide a spec for unit tests.
  - Run your tests with <code>./gradlew clean test</code>.
  - Save integration time and run code quality checks locally with <code>./gradlew clean check</code>.
  - When all tests and checks pass, everything's fine.
- Commit your changes
  - Please provide a git message that explains what you've done following the [conventional commits][commit-url]
    pattern.
  - Commit to the forked repository.
  - Sign your commit.
- Make a pull request
  - Make sure you send the PR to the <code>development</code> branch.
  - CI is watching you!

[spock-url]: https://spockframework.org
[commit-url]: https://www.conventionalcommits.org
