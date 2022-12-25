package net.javahub.musc.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.javahub.musc.networking.MuscHttpServer;

import static net.javahub.musc.Musc.Config;

public class MuscServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        if (Config.distribution.useMuscHttpServer) {
            MuscHttpServer server = MuscHttpServer.getInstance();
            ServerLifecycleEvents.SERVER_STARTING.register(server::start);
            ServerLifecycleEvents.SERVER_STOPPING.register(server::stop);
        }
    }
}
