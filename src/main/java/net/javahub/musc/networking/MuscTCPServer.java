package net.javahub.musc.networking;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MuscTCPServer {

    public static Selector selector;
    public static ServerSocketChannel server;
    public static MuscTCPServer instance;
    public static byte[] file;
    public static Map<SocketChannel, Integer> connections = new HashMap<>();

    static {
        try {
            instance = new MuscTCPServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    MuscTCPServer() throws IOException {
        file = new FileInputStream("/home/martin/musc-full.tar").readAllBytes();
        selector = Selector.open();
        server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(3000));
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
    }

    public static void main(String[] args) throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

            while(keys.hasNext()) {
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
    }

    private static void accept(SelectionKey key) throws IOException {
         var serverSocketChannel = (ServerSocketChannel) key.channel();
         SocketChannel client = serverSocketChannel.accept();
         client.configureBlocking(false);
         client.register(selector, SelectionKey.OP_WRITE);
         if (connections.get(client) == null) {
             connections.put(client, 0);
         }
    }

    private static void write(SelectionKey key) throws IOException {
        var client = (SocketChannel) key.channel();
        int pos = connections.get(client);
        pos += client.write(ByteBuffer.wrap(file, pos, file.length - pos));
        if (pos != file.length) connections.put(client, pos);
        else {
            connections.remove(client);
            client.close();
        }
    }

}