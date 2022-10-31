package net.javahub.musc.networking;

import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.LOGGER;

public class MuscTCPServer {
    private static MuscTCPServer instance;
    private Thread tcpServer;
    private MuscTCPServer(Path resources) {
        try {
            InetSocketAddress address = new InetSocketAddress(CONFIG.distribution.port);
            tcpServer = new Thread(new TCPServer(address, resources));
            tcpServer.setName("MuscTCPServer Thread");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized MuscTCPServer getInstance(Path resources) {
        if (instance == null)
            instance = new MuscTCPServer(resources);
        return instance;
    }

    public void start(MinecraftServer minecraftServer) {
        if (!tcpServer.isAlive())
            tcpServer.start();
    }

    public void stop(MinecraftServer minecraftServer) {
        tcpServer.interrupt();
    }

    private static class TCPServer implements Runnable {
        Map<SocketChannel, Integer> sessions = new HashMap<>();
        ServerSocketChannel serverSocketChannel;
        Selector selector;
        byte[] resources;

        public TCPServer(InetSocketAddress address, Path resourcePath) throws IOException {
            resources = Files.readAllBytes(resourcePath);
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        @Override
        public void run() {
            System.out.println(resources.length);
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    selector.select();
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
            } catch (IOException e) {
                LOGGER.error(e);
            } finally {
                closeConnection();
            }
        }

        private void accept(SelectionKey key) throws IOException {
            var serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_WRITE);
            sessions.put(client, 0);
        }

        private void write(SelectionKey key) throws IOException {
            var client = (SocketChannel) key.channel();
            int pos = sessions.get(client);
            try {
                pos += client.write(ByteBuffer.wrap(resources, pos, resources.length - pos));
                sessions.put(client, pos);
                if (pos == resources.length) {
                    LOGGER.info("END " + client.getRemoteAddress());
                    sessions.remove(client);
                    client.close();
                }
            } catch (IOException e) {
                LOGGER.error(e);
                sessions.remove(client);
                client.close();
            }
        }

        private void closeConnection() {
            System.out.println("Closing server down");
            if (selector != null) {
                try {
                    selector.close();
                    serverSocketChannel.socket().close();
                    serverSocketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}