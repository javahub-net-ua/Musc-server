package net.javahub.mod.musc.networking;

import static net.javahub.mod.musc.Musc.CONFIG;

import net.javahub.mod.musc.logging.MuscLogger;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MuscTCPServer {

    private static Thread tcpServer;

    static class TCPServer implements Runnable {

        private String hostname;
        private int port;

        private final ServerSocketChannel serverSocketChannel;
        private final Selector selector;
        private final Map<SocketChannel, byte[]> dataTracking = new HashMap<>();

        TCPServer(String hostname, int port) {
            MuscLogger.info("Initializing TCP server...");

            this.hostname = hostname;
            this.port = port;

            try {
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(
                        new InetSocketAddress(hostname, port));
                selector = Selector.open();
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                MuscLogger.warn("Unable to initialize TCP server");
                MuscLogger.error(e);
                MuscLogger.trace(e);
                throw new RuntimeException(e);
            }
        }

        public void run() {
            MuscLogger.info("Starting TCP Server...");
            MuscLogger.info(String.format("%s:%d", hostname, port));

            try {
                while (!Thread.currentThread().isInterrupted()) {
                    selector.select(0);
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();
                        if (!key.isValid())
                            continue;
                        if (key.isAcceptable())
                            accept(key);
                        if (key.isWritable())
                            write(key);
                    }
                }
                closeConnection();
            } catch (IOException e) {
                MuscLogger.error(e);
                MuscLogger.trace(e);
            }
        }

        private void accept(SelectionKey key) {
            try {
                var serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);

                socketChannel.register(selector, SelectionKey.OP_WRITE);
                byte[] hello = "sssss\nsssss".getBytes();
                dataTracking.put(socketChannel, hello);
            } catch (IOException e) {
                MuscLogger.error(e);
                MuscLogger.trace(e);
            }
        }

        private void write(SelectionKey key) {
            try {
                var socketChannel = (SocketChannel) key.channel();
                byte[] data = dataTracking.get(socketChannel);
                dataTracking.remove(socketChannel);
                socketChannel.write(ByteBuffer.wrap(data));
                key.interestOps(SelectionKey.OP_READ);
            } catch (IOException e) {
                MuscLogger.error(e);
                MuscLogger.trace(e);
            }
        }

        private void closeConnection() {
            try {
                selector.close();
                serverSocketChannel.socket().close();
                serverSocketChannel.close();
            } catch (IOException e) {
                MuscLogger.error(e);
                MuscLogger.trace(e);
            }
        }
    }

    public static void start(MinecraftServer server) {
        try {
            tcpServer = new Thread(new TCPServer(
                    CONFIG.listening.hostname,
                    CONFIG.listening.port
            ));
            tcpServer.start();
        } catch (Exception e) {
            MuscLogger.error(e);
            MuscLogger.trace(e);
        }
    }

    public static void stop(MinecraftServer server) {
        MuscLogger.info("Stopping TCP Server...");
        try {
            tcpServer.interrupt();
        } catch (NullPointerException e) {
            MuscLogger.warn("Unable to stop TCP Server correctly");
            MuscLogger.error(e);
            MuscLogger.trace(e);
        }
    }

}