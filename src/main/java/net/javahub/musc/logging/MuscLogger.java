package net.javahub.musc.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.javahub.musc.Musc.CONFIG;

public class MuscLogger {

    private static final Logger LOGGER = getLogger(CONFIG.name());

    private static Logger getLogger(String name) {
        Logger logger = LoggerFactory.getLogger(name);
        if (CONFIG.logging().showHints)
            logger.info("Musc-server Wiki: https://github.com/javahub-net-ua/Musc-server/wiki");
        return logger;
    }

    public void info(String log) {
        if (CONFIG.logging().showInfo)
            LOGGER.info(log);
    }

    public void warn(String log) {
        if (CONFIG.logging().showWarn)
            LOGGER.warn(log);
    }

    public void error(Exception e) {
        if (CONFIG.logging().showError)
            LOGGER.error(e.getMessage());
    }
}