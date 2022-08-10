package net.javahub.mod.musc.config;

import static net.javahub.mod.musc.Musc.MOD_ID;

import draylar.omegaconfig.OmegaConfig;
import draylar.omegaconfig.api.Config;

public class MuscConfig implements Config {

    public Listening listening = new Listening();

    public Logging logging = new Logging();

    public static class Listening {
        public String hostname = "localhost";
        public int port = 4500;
    }

    public static class Logging {
        public boolean showInfo = true;
        public boolean showWarn = true;
        public boolean showError = false;
        public boolean showTracing = false;
    }

    @Override
    public String getName() {
        return MOD_ID;
    }

    public static MuscConfig init() {
        return OmegaConfig.register(MuscConfig.class);
    }

}