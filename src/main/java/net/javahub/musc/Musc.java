package net.javahub.musc;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.javahub.musc.config.MuscConfig;
import net.javahub.musc.logging.MuscLogger;
import net.javahub.musc.networking.MuscTCPServer;
import net.javahub.musc.records.Record;
import net.javahub.musc.records.RecordUtils;
import net.javahub.musc.resources.ResourceBuilder;

import java.nio.file.Path;
import java.util.Set;

public class Musc implements ModInitializer {
    public static final MuscConfig CONFIG = MuscConfig.init();
    public static final MuscLogger LOGGER = new MuscLogger();

    @Override
    public void onInitialize() {
        Set<Record> records = RecordUtils.getRecords();
        ResourceBuilder resourceBuilder = new ResourceBuilder(records);
        Path resources = resourceBuilder.getResources();
//        if (CONFIG.distribution.useMuscTCPServer) {
//            MuscTCPServer SERVER = MuscTCPServer.getInstance(resources);
//            ServerLifecycleEvents.SERVER_STARTING.register(SERVER::start);
//            ServerLifecycleEvents.SERVER_STOPPING.register(SERVER::stop);
//        }
    }
}