package com.github.jakemarsden.githooksgradleplugin;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

class GitHooksPluginTest {

  @Test
  void pluginExistsUnderCorrectId() {
    Project project = ProjectBuilder.builder().build();
    project.getPluginManager().apply("com.github.jakemarsden.git-hooks");

    GitHooksPlugin plugin = project.getPlugins().findPlugin(GitHooksPlugin.class);
    assertThat(plugin, instanceOf(GitHooksPlugin.class));
  }

  @Test
  void extensionExistsUnderCorrectName() {
    Project project = ProjectBuilder.builder().build();
    project.getPluginManager().apply(GitHooksPlugin.class);

    Object extension = project.getExtensions().findByName("gitHooks");
    assertThat(extension, instanceOf(GitHooksExtension.class));
  }
}
