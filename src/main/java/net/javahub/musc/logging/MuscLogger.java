package net.javahub.musc.logging;

import net.javahub.musc.Musc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MuscLogger {

    private static final Logger LOGGER =
            LogManager.getLogger(Musc.CONFIG.getName());

    public void info(String log) {
        if (Musc.CONFIG.logging.showInfo)
            LOGGER.info(log);
    }

    public MuscLogger() {
        if (Musc.CONFIG.logging.showBanner) {
            LOGGER.info("##########################");
            LOGGER.info("#------JAVAHUB-MUSC------#");
            LOGGER.info("#-https://javahub.net.ua-#");
            LOGGER.info("##########################");
        }
    }

    public void warn(String log) {
        if (Musc.CONFIG.logging.showWarn)
            LOGGER.warn(log);
    }

    public void warn(String log, Exception e) {
        if (Musc.CONFIG.logging.showWarn)
            LOGGER.warn(log);
        if (Musc.CONFIG.logging.showError)
            LOGGER.error(e);
        if (Musc.CONFIG.logging.doPanic)
            throw new RuntimeException(e);
    }

}
