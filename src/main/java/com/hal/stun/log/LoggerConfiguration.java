package com.hal.stun.log;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import java.io.IOException;

public class LoggerConfiguration {
  private static final String CONFIG_FILE_NAME = "config.properties";

  private static final File outputFile;
  static {
    try {
      outputFile = initializeOutputFile();
    } catch (IOException error) {
      throw new ExceptionInInitializerError(error);
    }
  }

  private static File initializeOutputFile() throws IOException {
    File configFile = new File(CONFIG_FILE_NAME);
    if (configFile.exists()) {
      Properties fileConfig = new Properties();
      fileConfig.load(new FileReader(configFile));
      return initializeOutputFileFromConfig(fileConfig);
    } else {
      return null;
    }
  }

  private static File initializeOutputFileFromConfig(Properties fileConfig) throws IOException {
    String outputFileName = fileConfig.getProperty("outputFile");
    if (outputFileName != null) {
      File outputFile = new File(outputFileName);
      if (!outputFile.exists()) {
        outputFile.createNewFile();
      }
      return outputFile;
    } else {
      return null;
    }
  }
}
