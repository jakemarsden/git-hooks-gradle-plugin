package com.github.jakemarsden.githooksgradleplugin;

import static java.util.Collections.unmodifiableMap;

import java.util.Map;
import javax.inject.Inject;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

public class GitHooksExtension {

  private final MapProperty<String, String> hooks;
  private final DirectoryProperty hooksDirectory;
  private final Property<String> gradleCommand;

  @Inject
  public GitHooksExtension(ProjectLayout layout, ObjectFactory objFactory) {
    this.hooks = objFactory.mapProperty(String.class, String.class);
    this.hooksDirectory =
        objFactory
            .directoryProperty()
            .convention(layout.getProjectDirectory().dir(".git").dir("hooks"));
    this.gradleCommand = objFactory.property(String.class).convention("./gradlew");
  }

  public MapProperty<String, String> getHooks() {
    return this.hooks;
  }

  public void setHooks(Map<String, String> hooks) {
    this.hooks.set(Map.copyOf(hooks));
  }

  public DirectoryProperty getHooksDirectory() {
    return this.hooksDirectory;
  }

  public void setHooksDirectory(Directory hooksDirectory) {
    this.hooksDirectory.set(hooksDirectory);
  }

  public Property<String> getGradleCommand() {
    return this.gradleCommand;
  }

  public void setGradleCommand(String gradleCommand) {
    this.gradleCommand.set(gradleCommand);
  }

  Map<String, String> finalizeHooks() {
    this.hooks.finalizeValue();
    return unmodifiableMap(this.hooks.get());
  }

  Directory finalizeHooksDirectory() {
    this.hooksDirectory.finalizeValue();
    return this.hooksDirectory.get();
  }

  String finalizeGradleCommand() {
    this.gradleCommand.finalizeValue();
    return this.gradleCommand.get();
  }
}
