package net.javahub.musc.config;

import io.wispforest.owo.config.annotation.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Config(name = "musc", wrapperName = "MuscConfig")
public class MuscConfigModel {
    public Resources resources = new Resources();
    public Distribution distribution = new Distribution();
    public Logging logging = new Logging();
    public Map<String, String> overrides = new HashMap<>();
    public Map<String, String> mobBinding = new HashMap<>();

    public static class Resources {
        public int packFormat = 9;
        public String description = "Musc resources :)";
        public String pathToResources = "musc.zip";
        public Map<String, String> translations = new LinkedHashMap<>() {{
            put("en_us", "Music disc");
        }};
    }

    public static class Distribution {
        public int port = 4500;
        public boolean useMuscHttpServer = true;
        public int threadPoolSize = 4;
    }

    public static class Logging {
        public boolean showHints = true;
        public boolean showInfo = true;
        public boolean showWarn = true;
        public boolean showError = true;
    }
}
