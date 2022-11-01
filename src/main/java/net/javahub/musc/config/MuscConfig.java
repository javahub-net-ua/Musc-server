package net.javahub.musc.config;

import draylar.omegaconfig.OmegaConfig;
import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import net.fabricmc.loader.api.FabricLoader;
import org.checkerframework.checker.units.qual.C;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static net.javahub.musc.Musc.CONFIG;

public class MuscConfig implements Config {
    public Resources resources = new Resources();
    public Distribution distribution = new Distribution();
    public Logging logging = new Logging();
    public Map<String, String> overrides = new HashMap<>();
    public Map<String, String> mobBinding = new HashMap<>();

    public static class Resources {
        public String pack_version = "9";
        public String description = "Musc resources :)";
        public String pathOverride = "";
    }

    public static class Distribution {
        public int port = 4500;
        public boolean useMuscTCPServer = true;
    }

    public static class Logging {
        public boolean showBanner = false;
        public boolean showInfo = true;
        public boolean showWarn = true;
        public boolean showError = false;
        public boolean doPanic = false;
    }

    public String getName() {
        return  "Musc";
    }

    public String getModID() {
        return "musc";
    }

    public String getDirectory() {
        return "/musc";
    }

    public static MuscConfig init() {
        return OmegaConfig.register(MuscConfig.class);
    }
}