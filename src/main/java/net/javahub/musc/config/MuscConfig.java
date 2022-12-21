package net.javahub.musc.config;

import draylar.omegaconfig.api.Config;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MuscConfig implements Config {

    public Resources resources = new Resources();
    public Distribution distribution = new Distribution();
    public Map<String, String> overrides = new HashMap<>();
    public Map<String, String> mobBindings = new HashMap<>();

    public static class Resources {
        public int packFormat = 9;
        public String description = "Musc resources :)";
        public String pathToResources = "musc.zip";
        public Map<String, String> translations = new LinkedHashMap<>(){{
            put("en_us", "Music disc");
        }};
    }

    public static class Distribution {
        public boolean useMuscHttpServer = true;
        public int port = 4500;
    }

    @Override
    public String getName() {
        return "Musc";
    }

    @Override
    @Nullable
    public String getModid() {
        return "musc";
    }
}
