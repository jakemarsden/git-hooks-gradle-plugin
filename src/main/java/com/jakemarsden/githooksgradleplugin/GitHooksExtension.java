package com.jakemarsden.githooksgradleplugin;

import javax.inject.Inject;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

public class GitHooksExtension {

  public final MapProperty<String, String> hooks;
  public final DirectoryProperty hooksDirectory;
  public final Property<String> gradleScript;

  @Inject
  public GitHooksExtension(ProjectLayout layout, ObjectFactory objFactory) {
    this.hooks = objFactory.mapProperty(String.class, String.class);
    this.hooksDirectory =
        objFactory
            .directoryProperty()
            .convention(layout.getProjectDirectory().dir(".git").dir("hooks"));
    this.gradleScript = objFactory.property(String.class).convention("./gradlew");
  }
}
