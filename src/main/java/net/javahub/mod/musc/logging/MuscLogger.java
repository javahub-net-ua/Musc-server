package net.javahub.mod.musc.logging;

import static net.javahub.mod.musc.Musc.CONFIG;

import net.javahub.mod.musc.Musc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MuscLogger {

    private static final Logger LOGGER =
            LogManager.getLogger(CONFIG.getName());

    public void info(String log) {
        if (CONFIG.logging.showInfo)
            LOGGER.info(log);
    }

    public MuscLogger() {
        if (CONFIG.logging.showBanner) {
            LOGGER.info("##########################");
            LOGGER.info("#------JAVAHUB-MUSC------#");
            LOGGER.info("#-https://javahub.net.ua-#");
            LOGGER.info("##########################");
        }
    }

    public void warn(String log) {
        if (CONFIG.logging.showWarn)
            LOGGER.warn(log);
    }

    public void warn(String log, Exception e) {
        if (CONFIG.logging.showWarn)
            LOGGER.warn(log);
        if (CONFIG.logging.showError)
            LOGGER.error(e);
        if (CONFIG.logging.doPanic)
            throw new RuntimeException(e);
    }

}
