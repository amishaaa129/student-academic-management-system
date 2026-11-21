package com.college.util;

import java.io.IOException;
import java.util.logging.*;

public class AppLogger {

    private static final Logger logger =
        Logger.getLogger("StudentManagement");

    static {
        try {

            java.io.File directory =
                new java.io.File("logs");

            if (!directory.exists()) {
                directory.mkdirs();
            }

            FileHandler fileHandler =
                new FileHandler(
                    "logs/application.log",
                    true
                );

            fileHandler.setFormatter(
                new SimpleFormatter()
            );

            logger.addHandler(fileHandler);

            logger.setLevel(Level.ALL);

        } catch (IOException e) {

            System.err.println(
                "Unable to initialize application logging"
            );
        }
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void error(String message, Exception e) {
        logger.log(Level.SEVERE, message, e);
    }
}