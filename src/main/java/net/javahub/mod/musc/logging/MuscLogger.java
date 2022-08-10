package net.javahub.mod.musc.logging;

import static net.javahub.mod.musc.Musc.CONFIG;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.javahub.mod.musc.Musc;

public class MuscLogger {

    private static final Logger LOGGER =
            LogManager.getLogger(Musc.class.getName());

    public static void info(String log) {
        if (CONFIG.logging.showInfo)
            LOGGER.info("[Musc] {}", log);
    }

    public static void warn(String log) {
        if (CONFIG.logging.showWarn)
            LOGGER.warn("[Musc] {}", log);
    }

    public static void error(Exception log) {
        if (CONFIG.logging.showError)
            LOGGER.error(log);
    }

    public static void trace(Exception log) {
        if (CONFIG.logging.showError)
            LOGGER.trace(log);
    }

}