package javahub.mod.musc;

import com.sun.net.httpserver.HttpServer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Musc implements ModInitializer {

    public static final String MOD_ID = "musc";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final HttpServer SERVER = getHttpServer();

    public static HttpServer getHttpServer() {
        try {
            return HttpServer.create(new InetSocketAddress(4500), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> onDisable());
        LOGGER.info("Server is here!");
        SERVER.createContext("/api/hello", (exchange -> {
            String respText = "Hello!";
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
            exchange.close();
        }));
        SERVER.setExecutor(null); // creates a default executor
        SERVER.start();
    }

    public static void onEnable() {

    }

    public static void onDisable() {

    }
}
