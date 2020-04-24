package com.github.jakemarsden.githooksgradleplugin;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;

final class GitHookWriter {

  private static final Logger LOGGER = getLogger(GitHookWriter.class);

  private final Path hooksDirectory;
  private final String gradleCommand;

  GitHookWriter(Path hooksDirectory, String gradleCommand) {
    this.hooksDirectory = hooksDirectory;
    this.gradleCommand = gradleCommand;
  }

  void writeHook(String hookName, String gradleTask) {
    var script = this.generateScript(gradleTask);
    var scriptPath = this.hooksDirectory.resolve(hookName);

    LOGGER.info("Writing hook script '{}'", scriptPath);
    try {
      Files.createDirectories(scriptPath.getParent());
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to create missing parent(s) for: " + scriptPath, e);
    }
    try {
      Files.writeString(scriptPath, script, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new UncheckedIOException("Failed to write to file: " + scriptPath, e);
    }
    if (!scriptPath.toFile().setExecutable(true, false)) {
      throw new UnsupportedOperationException("Failed to set as executable: " + scriptPath);
    }
  }

  private String generateScript(String gradleTask) {
    return "#!/bin/bash\n" + this.gradleCommand + ' ' + gradleTask + '\n';
  }
}
