# git-hooks-gradle-plugin

Configure shared [Git hooks][git-hooks] right from your [Gradle][gradle] buildscript

[![CI][workflow-ci-badge]][workflow-ci]

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
[workflow-ci]: https://github.com/jakemarsden/git-hooks-gradle-plugin/actions?query=workflow%3ACI
[workflow-ci-badge]: https://github.com/jakemarsden/git-hooks-gradle-plugin/workflows/CI/badge.svg
