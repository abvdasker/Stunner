package com.hal.stun.log;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import java.io.IOException;

public class LoggerConfiguration {
  private static final String CONFIG_FILE_NAME = "config.properties";

  private static final Properties fileConfig;
  private static final File outputFile;
  private static final boolean silence;

  static {
    try {
      fileConfig = loadProperties();
      outputFile = initializeOutputFile();
      silence = initializeSilence();
    } catch (IOException error) {
      throw new ExceptionInInitializerError(error);
    }
  }

  private static Properties loadProperties() throws IOException {
    File configFile = new File(CONFIG_FILE_NAME);
    if (configFile.exists()) {
      Properties newFileConfig = new Properties();
      newFileConfig.load(new FileReader(configFile));
      return newFileConfig;
    } else {
      return null;
    }
  }

  private static File initializeOutputFile() throws IOException {
    if (fileConfig != null) {
      String outputFileName = fileConfig.getProperty("outputFile");
      if (outputFileName != null) {
        File outputFile = new File(outputFileName);
        if (!outputFile.exists()) {
          outputFile.createNewFile();
        }
        return outputFile;
      }
    }

    return null;
  }

  private static boolean initializeSilence() {
    if (fileConfig != null) {
      String shouldSilence = fileConfig.getProperty("silent");
      if (shouldSilence != null) {
        return Boolean.parseBoolean(shouldSilence);
      }
    }
    return false;
  }

}
