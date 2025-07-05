package com.autopilot.config.logging;

import org.slf4j.Logger;

public record AppLogger(Logger logger) {

    public void info(String message) {
        logger.info("[INFO]{}", message);
    }

    public void debug(String message) {
        logger.debug("[DEBUG] {}", message);
    }

    public void warn(String message) {
        logger.warn("[WARN] {}", message);
    }

    public void error(String message, Throwable ex) {
        logger.error("[ERROR] {}", message, ex);
    }

    public void error(String message) {
        logger.error("[ERROR] {}", message);
    }
}
