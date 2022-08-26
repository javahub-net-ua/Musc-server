package net.javahub.mod.musc;

import net.javahub.mod.musc.config.MuscConfig;
import net.javahub.mod.musc.list.MuscSetList;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.javahub.mod.musc.networking.MuscTCPServer;

public class Musc implements ModInitializer {

    public static final String MOD_ID = "musc";

    public static final MuscConfig CONFIG = MuscConfig.init();
    public static final MuscSetList MUSIC_LIST = new MuscSetList();
    public static final MuscTCPServer server = new MuscTCPServer();

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server::start);
        ServerLifecycleEvents.SERVER_STOPPING.register(server::stop);
    }

}