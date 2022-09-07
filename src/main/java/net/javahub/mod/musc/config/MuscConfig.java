package net.javahub.mod.musc.config;

import draylar.omegaconfig.OmegaConfig;
import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;

import java.util.HashMap;
import java.util.Map;

public class MuscConfig implements Config {

    public Listening listening = new Listening();
    public Logging logging = new Logging();

    public Map<String, String> overrides = new HashMap<>();

    public static class Listening {
        public String hostname = "";
        public int port = 4500;
    }

    public static class Logging {
        public boolean showBanner = false;
        public boolean showInfo = true;
        public boolean showWarn = true;
        public boolean showError = false;
        @Comment("Stops the server if the plugin does not work correctly")
        public boolean doPanic = false;
    }

    public String getName() {
        return  "Musc";
    }

    public String getModid() {
        return "musc";
    }

    public String getDirectory() {
        return "/musc/";
    }

    public static MuscConfig init() {
        return OmegaConfig.register(MuscConfig.class);
    }

}