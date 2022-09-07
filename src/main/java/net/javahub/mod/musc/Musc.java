package net.javahub.mod.musc;

import net.javahub.mod.musc.config.MuscConfig;
import net.javahub.mod.musc.records.MuscRecords;

import net.fabricmc.api.ModInitializer;
import net.javahub.mod.musc.logging.MuscLogger;

public class Musc implements ModInitializer {

    public static final MuscConfig CONFIG = MuscConfig.init();
    public static final MuscLogger LOGGER = new MuscLogger();
    public static final MuscRecords RECORDS = new MuscRecords();
    //public static final MuscTCPServer SERVER = new MuscTCPServer();

    @Override
    public void onInitialize() {
        //ServerLifecycleEvents.SERVER_STARTING.register(SERVER::start);
        //ServerLifecycleEvents.SERVER_STOPPING.register(SERVER::stop);
    }

}