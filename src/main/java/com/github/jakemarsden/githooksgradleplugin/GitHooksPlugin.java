package com.github.jakemarsden.githooksgradleplugin;

import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GitHooksPlugin implements Plugin<Project> {

  private static final Set<String> VALID_HOOKS;

  static {
    Set<String> validHooks = new HashSet<>();
    validHooks.add("applypatch-msg");
    validHooks.add("commit-msg");
    validHooks.add("fsmonitor-watchman");
    validHooks.add("post-applypatch");
    validHooks.add("post-checkout");
    validHooks.add("post-commit");
    validHooks.add("post-merge");
    validHooks.add("post-receive");
    validHooks.add("post-rewrite");
    validHooks.add("post-update");
    validHooks.add("pre-applypatch");
    validHooks.add("pre-auto-gc");
    validHooks.add("pre-commit");
    validHooks.add("pre-push");
    validHooks.add("pre-rebase");
    validHooks.add("pre-receive");
    validHooks.add("prepare-commit-msg");
    validHooks.add("push-to-checkout");
    validHooks.add("update");
    validHooks.add("sendemail-validate");
    VALID_HOOKS = unmodifiableSet(validHooks);
  }

  public GitHooksPlugin() {}

  @Override
  public void apply(Project project) {
    GitHooksExtension extension =
        project.getExtensions().create("gitHooks", GitHooksExtension.class);
    project.afterEvaluate(p -> writeHooks(extension));
  }

  private void writeHooks(GitHooksExtension extension) {
    Map<String, String> hooks = extension.finalizeHooks();
    Path hooksDirectory = extension.finalizeHooksDirectory().getAsFile().toPath();
    String gradleCommand = extension.finalizeGradleCommand();
    GitHookWriter writer = new GitHookWriter(hooksDirectory, gradleCommand);

    this.validateHooks(hooks);
    hooks.forEach(writer::writeHook);
  }

  private void validateHooks(Map<String, String> hooks) {
    if (hooks.isEmpty()) {
      throw new IllegalArgumentException("No hooks found");
    }

    Set<String> invalidHooks =
        hooks.keySet().stream()
            .filter(hook -> !VALID_HOOKS.contains(hook))
            .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
    if (!invalidHooks.isEmpty()) {
      throw new IllegalArgumentException("Unsupported hook(s): " + invalidHooks);
    }
  }
}
