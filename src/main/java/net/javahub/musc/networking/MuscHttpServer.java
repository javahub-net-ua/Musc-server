package net.javahub.musc.networking;

import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.nio.file.Path;

import static net.javahub.musc.Musc.LOGGER;

public class MuscHttpServer {

    private static MuscHttpServer instance;
    private final Thread SERVER;

    public static synchronized MuscHttpServer getInstance(Path resources) {
        try {
            if (instance == null) {
                instance = new MuscHttpServer(resources);
            } return instance;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MuscHttpServer(Path resources) throws IOException {
        HttpServer server = new HttpServer(resources);
        SERVER = new Thread(server);
    }

    public void start(MinecraftServer ignored) {
        SERVER.start();
        LOGGER.info("HttpServer starting...");
    }

    public void stop(MinecraftServer ignored) {
        SERVER.interrupt();
        LOGGER.info("HttpServer stopping...");
    }
}
