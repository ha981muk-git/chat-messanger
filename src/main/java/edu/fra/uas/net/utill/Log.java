package edu.fra.uas.net.utill;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class presents a Logger
 */
public class Log {
    private Logger logger;

    /**
     * default constructor
     */
    public Log() {
        logger = Logger.getLogger("Chat-Server");
        logger.setLevel(Level.ALL);
    }

    /**
     * to log info-message
     *
     * @param message a string message
     */
    public void info(String message) {
        logger.info(message);
    }

    /**
     * to log warning-message
     *
     * @param message a string message
     */
    public void warning(String message) {
        logger.warning(message);
    }

    /**
     * to log severe-message
     *
     * @param message a string message
     */
    public void severe(String message) {
        logger.severe(message);
    }
}
