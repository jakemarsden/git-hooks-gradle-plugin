package com.jakemarsden.githooksgradleplugin;

import static java.util.stream.Collectors.toUnmodifiableSet;

import java.util.Map;
import java.util.Set;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GitHooksPlugin implements Plugin<Project> {

  private static final Set<String> VALID_HOOKS =
      Set.of(
          "applypatch-msg",
          "commit-msg",
          "fsmonitor-watchman",
          "post-applypatch",
          "post-checkout",
          "post-commit",
          "post-merge",
          "post-receive",
          "post-rewrite",
          "post-update",
          "pre-applypatch",
          "pre-auto-gc",
          "pre-commit",
          "pre-push",
          "pre-rebase",
          "pre-receive",
          "prepare-commit-msg",
          "push-to-checkout",
          "update",
          "sendemail-validate");

  public GitHooksPlugin() {}

  @Override
  public void apply(Project project) {
    var extension = project.getExtensions().create("gitHooks", GitHooksExtension.class);
    project.afterEvaluate(p -> writeHooks(extension));
  }

  private void writeHooks(GitHooksExtension extension) {
    var hooks = extension.finalizeHooks();
    var hooksDirectory = extension.finalizeHooksDirectory().getAsFile().toPath();
    var gradleCommand = extension.finalizeGradleCommand();
    var writer = new GitHookWriter(hooksDirectory, gradleCommand);

    this.validateHooks(hooks);
    hooks.forEach(writer::writeHook);
  }

  private void validateHooks(Map<String, String> hooks) {
    if (hooks.isEmpty()) {
      throw new IllegalArgumentException("No hooks found");
    }

    var invalidHooks =
        hooks.keySet().stream()
            .filter(hook -> !VALID_HOOKS.contains(hook))
            .collect(toUnmodifiableSet());
    if (!invalidHooks.isEmpty()) {
      throw new IllegalArgumentException("Unsupported hook(s): " + invalidHooks);
    }
  }
}
