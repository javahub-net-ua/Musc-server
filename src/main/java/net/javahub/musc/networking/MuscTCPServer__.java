package net.javahub.musc.networking;

import net.javahub.musc.Musc;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

public class MuscTCPServer__ {

    private static String hostname;
    private static int port;
    private static Thread server;

    public MuscTCPServer__() {
        hostname = Musc.CONFIG.listening.ip;
        port = Musc.CONFIG.listening.port;

        try {
            server = new Thread(new TCPServer(hostname, port));
            server.setName("MuscTCPServer");
        } catch (IOException e) {
            Musc.LOGGER.warn("FUCK!", e);
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
            //get_zip()

            address = getAddress(hostname, port);
            Musc.LOGGER.info("Listening on " + address);

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(address);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        @Override
        public void run() {
            Musc.LOGGER.info("Running MuscTCPServer...");
            try {
                channel = FileChannel.open(Paths.get("/home/martin/musc.zip"));
                while (!Thread.currentThread().isInterrupted()) {
                    int select = 0;
                    select = selector.select();
                    if (select == 0)
                        continue;
                    System.out.println(select);
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = keys.iterator();
                    //Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        if (!key.isValid())
                            continue;
                        if (key.isAcceptable())
                            accept(key);
                        if (key.isWritable()) {
                            //new Thread(new TCPWriter(channel, key)).start();
                            ByteBuffer buffer = ByteBuffer.allocate(1);
                            buffer.flip();
                            TCPWriter writer = new TCPWriter(channel, key);
                            new Thread(writer::run).start();
                        }
                        iter.remove();
                    }
                }
            } catch (IOException e) {
                Musc.LOGGER.warn("FUCK!", e);
            } finally {
                closeConnections();
            }
        }

        private InetSocketAddress getAddress(String hostname, int port) {
            if (hostname == "") return new InetSocketAddress(port);
            else return new InetSocketAddress(hostname, port);
        }

        private void accept(SelectionKey key) throws IOException {
            var serverSocketChannel = (ServerSocketChannel) key.channel();
            var socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_WRITE);
        }

        private void closeConnections() {
            try {
                selector.close();
                serverSocketChannel.socket().close();
                serverSocketChannel.close();
            } catch (IOException e) {
                Musc.LOGGER.warn("Unable to close connections", e);
            }
        }

    }

}