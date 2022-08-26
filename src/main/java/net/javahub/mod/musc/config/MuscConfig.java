package net.javahub.mod.musc.config;

import static net.javahub.mod.musc.Musc.MOD_ID;

import draylar.omegaconfig.OmegaConfig;
import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class MuscConfig implements Config {

    public Server server = new Server();
    public Logging logging = new Logging();

    public static class Server {
        public String hostname = "#";
        public int port = 4500;
    }

    public static class Logging {
        public boolean showInfo = true;
        public boolean showWarn = true;
        public boolean showError = false;
        @Comment("Stops the server if the plugin does not work correctly")
        public boolean doPanic = false;
    }


    public String getName() {
        return MOD_ID;
    }

    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static MuscConfig init() {
        return OmegaConfig.register(MuscConfig.class);
    }

}
