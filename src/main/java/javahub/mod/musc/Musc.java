package javahub.mod.musc;

import javahub.mod.musc.config.MuscConfig;
import javahub.mod.musc.config.MuscMusicConfig;
import javahub.mod.musc.networking.MuscTCPServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;


public class Musc implements ModInitializer {

    public static final String MOD_ID = "musc";

    public static final MuscConfig CONFIG = MuscConfig.init();

    @Override
    public void onInitialize() {

        new MuscMusicConfig();

        ServerLifecycleEvents.SERVER_STARTING.register(MuscTCPServer::start);
        ServerLifecycleEvents.SERVER_STOPPING.register(MuscTCPServer::stop);
    }

}