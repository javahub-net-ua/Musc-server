package javahub.mod.musc;

import javahub.mod.musc.networking.MuscServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Musc implements ModInitializer {

    public static final String MOD_ID = "musc";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(MuscServer::start);
        ServerLifecycleEvents.SERVER_STOPPING.register(MuscServer::stop);
    }

}
