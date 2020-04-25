# git-hooks-gradle-plugin

Configure shared [Git hooks][git-hooks] right from your [Gradle][gradle] buildscript

[![CI][workflow-ci-badge]][workflow-ci]
[![Latest version][latest-version-badge]][latest-version]
[![Gradle plugin][gradle-plugin-badge]][gradle-plugin]
[![License][license-badge]][license]

## Setup

```groovy
plugins {
  id 'com.github.jakemarsden.git-hooks' version 'x.x.x'
}
```

## Example usage

Ensure the `check` task succeeds before every commit:

```groovy
gitHooks {
  hooks = ['pre-commit': 'check']
}
```

This will create a `.git/hooks/pre-commit` script the next time any Gradle task runs:

```bash
#!/bin/bash
./gradlew check
```

The hooks directory and the Gradle command can also be changed, and a single project can have
multiple hooks:

```groovy
gitHooks {
  hooks = [
    'pre-commit': 'check',
    'pre-push': 'myPrePushTask myOtherPrePushTask --info'
  ]
  hooksDirectory = file('../.git/hooks')
  gradleCommand = '/usr/bin/my-gradle-executable'
}
```

This will create a `../.git/hooks/pre-commit` script:

```bash
#!/bin/bash
/usr/bin/my-gradle-executable check
```

...and a `../.git/hooks/pre-push` script:

```bash
#!/bin/bash
/usr/bin/my-gradle-executable myPrePushTask myOtherPrePushTask --info
```

[git-hooks]: https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks
[gradle]: https://gradle.org/

[gradle-plugin]: https://plugins.gradle.org/plugin/com.github.jakemarsden.git-hooks
[gradle-plugin-badge]: https://img.shields.io/maven-metadata/v?label=gradle-plugin&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fcom%2Fgithub%2Fjakemarsden%2Fgit-hooks-gradle-plugin%2Fmaven-metadata.xml
[latest-version]: https://github.com/jakemarsden/git-hooks-gradle-plugin/releases/latest
[latest-version-badge]: https://img.shields.io/github/v/tag/jakemarsden/git-hooks-gradle-plugin?color=blue&label=latest-version&sort=semver
[license]: https://github.com/jakemarsden/git-hooks-gradle-plugin/blob/master/LICENSE
[license-badge]: https://img.shields.io/github/license/jakemarsden/git-hooks-gradle-plugin
[workflow-ci]: https://github.com/jakemarsden/git-hooks-gradle-plugin/actions?query=workflow%3ACI
[workflow-ci-badge]: https://github.com/jakemarsden/git-hooks-gradle-plugin/workflows/CI/badge.svg
