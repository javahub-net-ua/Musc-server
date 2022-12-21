package net.javahub.musc.networking;

import net.minecraft.server.MinecraftServer;

import java.io.IOException;

import static net.javahub.musc.Musc.*;

public class MuscHttpServer {

    private static MuscHttpServer instance;
    private final HttpServer server;

    public static synchronized MuscHttpServer getInstance() {
        if (instance == null) {
            instance = new MuscHttpServer();
        } return instance;
    }

    private MuscHttpServer() {
        server = new HttpServer();
    }

    public void start(MinecraftServer ignored) {
        server.start();
        LOGGER.info("HttpServer starting...");
    }

    public void stop(MinecraftServer ignored) {
        try {
            server.interrupt();
            server.serverSocket.close();
            LOGGER.info("HttpServer stopping...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
