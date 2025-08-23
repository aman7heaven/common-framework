package com.autopilot.config.logging;

import org.slf4j.Logger;

public record AppLogger(Logger logger) {

    public void info(String message, Object... args) {
        logger.info("[INFO] " + message, args);
    }

    public void debug(String message, Object... args) {
        logger.debug("[DEBUG] " + message, args);
    }

    public void warn(String message, Object... args) {
        logger.warn("[WARN] " + message, args);
    }

    public void error(String message, Object... args) {
        logger.error("[ERROR] " + message, args);
    }

    public void error(String message, Throwable ex, Object... args) {
        logger.error("[ERROR] " + message, args, ex);
    }
}
