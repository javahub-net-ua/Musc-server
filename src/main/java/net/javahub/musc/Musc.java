package net.javahub.musc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.javahub.musc.config.MuscConfig;
import net.javahub.musc.logging.MuscLogger;
import net.javahub.musc.networking.MuscTCPServer_;

public class Musc implements ModInitializer {

    public static final MuscConfig CONFIG = MuscConfig.init();
    public static final MuscLogger LOGGER = new MuscLogger();
    //public static final MuscRecords RECORDS = new MuscRecords();
    //public static final ZIP ZIP = new ZIP();
    //public static final MuscTCPServer_ SERVER = new MuscTCPServer_();
    public static final MuscTCPServer_ SERVER = new MuscTCPServer_();

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(SERVER::start);
        ServerLifecycleEvents.SERVER_STOPPING.register(SERVER::stop);
    }

}