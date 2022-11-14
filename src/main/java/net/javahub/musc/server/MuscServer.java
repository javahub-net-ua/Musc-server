package net.javahub.musc.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.javahub.musc.networking.MuscHttpServer;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.RESOURCES;

public class MuscServer implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        if (CONFIG.distribution().useMuscHttpServer) {
            MuscHttpServer server = MuscHttpServer.getInstance(RESOURCES);
            MuscHttpServer server1 = MuscHttpServer.getInstance(RESOURCES);
            MuscHttpServer server2 = MuscHttpServer.getInstance(RESOURCES);
            MuscHttpServer server3 = MuscHttpServer.getInstance(RESOURCES);
            System.out.println(server);
            System.out.println(server1);
            System.out.println(server2);
            System.out.println(server3);
            ServerLifecycleEvents.SERVER_STARTING.register(server::start);
            ServerLifecycleEvents.SERVER_STOPPING.register(server::stop);
        }
    }
}
