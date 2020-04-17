package com.jakemarsden.githooksgradleplugin;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

class GitHooksPluginTest {

  @Test
  void pluginExistsUnderCorrectId() {
    var project = ProjectBuilder.builder().build();
    project.getPluginManager().apply("com.jakemarsden.git-hooks");

    var plugin = project.getPlugins().findPlugin(GitHooksPlugin.class);
    assertThat(plugin, instanceOf(GitHooksPlugin.class));
  }

  @Test
  void extensionExistsUnderCorrectName() {
    var project = ProjectBuilder.builder().build();
    project.getPluginManager().apply(GitHooksPlugin.class);

    var extension = project.getExtensions().findByName("gitHooks");
    assertThat(extension, instanceOf(GitHooksExtension.class));
  }
}
