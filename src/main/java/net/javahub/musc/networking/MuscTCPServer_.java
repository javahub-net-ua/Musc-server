package net.javahub.musc.networking;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.LOGGER;

import net.minecraft.server.MinecraftServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;

public class MuscTCPServer_ {

    private static String hostname;
    private static int port;
    private static Thread server;

    public MuscTCPServer_() {
        hostname = CONFIG.listening.ip;
        port = CONFIG.listening.port;

        try {
            server = new Thread(new TCPServer(hostname, port));
        } catch (IOException e) {
            LOGGER.warn("FUCK!", e);
        }
    }

    public void start(MinecraftServer server) {
        this.server.start();
    }

    public void stop(MinecraftServer server) {
        this.server.interrupt();
    }

    private static class TCPServer implements Runnable {

        private InetSocketAddress address;
        private ServerSocketChannel serverSocketChannel;
        private Selector selector;
        private static FileChannel channel;

        TCPServer(String hostname, int port) throws IOException {
            address = getAddress(hostname, port);
            System.out.println(address);
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(address);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    int select = 0;
                    select = selector.select();
                    if (select == 0)
                        continue;
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
                } catch (IOException e) {
                    LOGGER.warn("FUCK!", e);
                }
            }
            closeConnections();
        }

        private InetSocketAddress getAddress(String hostname, int port) {
            if (hostname == "") {
                return new InetSocketAddress(port);
            }
            else {
                return new InetSocketAddress(hostname, port);
            }
        }

        private void accept(SelectionKey key) throws IOException {
            var serverSocketChannel = (ServerSocketChannel) key.channel();
            var socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_WRITE);
        }

        private void write(SelectionKey key) throws IOException {
            channel = FileChannel.open(Paths.get("/home/martin/musc.zip"));
            var socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 4);

            while (channel.read(buffer) != -1) {
                buffer.flip();
                socketChannel.write(buffer);
                buffer.compact();
            } key.channel().close();
        }

        private void closeConnections() {
            try {
                selector.close();
                serverSocketChannel.socket().close();
                serverSocketChannel.close();
            } catch (IOException e) {
                LOGGER.warn("Unable to close connections", e);
            }
        }

    }

}