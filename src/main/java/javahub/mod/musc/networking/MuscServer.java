package javahub.mod.musc.networking;

import javahub.mod.musc.Musc;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MuscServer {

    private static final Thread tcpServer = new Thread(new TCPServer());

    static class TCPServer implements Runnable {
        public static String HOSTNAME = "192.168.0.165";
        public static int PORT = 8123;

        private ServerSocketChannel serverChannel;
        private Selector selector;
        private final Map<SocketChannel, byte[]> dataTracking = new HashMap<>();

        public TCPServer() {
            init();
        }

        private void init() {
            Musc.LOGGER.info("Server initialization...");
            if (selector != null) return;
            if (serverChannel != null) return;

            try {
                serverChannel = ServerSocketChannel.open();
                serverChannel.configureBlocking(false);
                serverChannel.socket().bind(new InetSocketAddress(HOSTNAME, PORT));
                selector = Selector.open();
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            Musc.LOGGER.info("Now distributing...");
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
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                Musc.LOGGER.info("BB");
                closeConnection();
            }
        }

        private void write(SelectionKey key) throws IOException {
            SocketChannel channel = (SocketChannel) key.channel();
            byte[] data = dataTracking.get(channel);
            dataTracking.remove(channel);
            channel.write(ByteBuffer.wrap(data));
            key.interestOps(SelectionKey.OP_READ);
        }

        private void closeConnection() {
            System.out.println("Closing server down");
            if (selector != null) {
                try {
                    selector.close();
                    serverChannel.socket().close();
                    serverChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void accept(SelectionKey key) throws IOException {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_WRITE);
            byte[] hello = "Hello from server".getBytes();
            dataTracking.put(socketChannel, hello);
        }
    }

    public static void start(MinecraftServer server) {
        tcpServer.start();
    }

    public static void stop(MinecraftServer server) {
        tcpServer.interrupt();
    }
}