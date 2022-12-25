package net.javahub.musc.config;

import draylar.omegaconfig.api.Config;

import java.util.LinkedHashMap;
import java.util.Map;

public class MuscConfig implements Config {

    public final Resources resources = new Resources();
    public final Distribution distribution = new Distribution();

    public static class Resources {
        public int packFormat = 10;
        public String description = "Musc resources :)";
        public String pathToResources = "musc.zip";
        public Map<String, String> localizations = new LinkedHashMap<>(){{
            put("en_us", "Music disc");
            put("uk_ua", "Платівка");
        }};
    }

    public static class Distribution {
        public boolean useMuscHttpServer = true;
        public int port = 4500;
    }

    @Override
    public String getName() {
        return "musc";
    }

    @Override
    public String getDirectory() {
        return "musc";
    }
}
