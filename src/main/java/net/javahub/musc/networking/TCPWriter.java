package net.javahub.musc.networking;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class TCPWriter {

    private static SelectionKey key;
    private static FileChannel channel;

    public TCPWriter(FileChannel channel, SelectionKey key) {
        this.channel = channel;
        this.key = key;
    }

    //@Override
    public void run() {
        var socketChannel = (SocketChannel) key.channel();
        try {
            long position = 0;
            long count = channel.size();
            while (position < count) {
                //position += channel.transferTo(position, count, socketChannel);
                System.out.println(position + "\t" + count);
            }
            socketChannel.close();
        } catch (IOException e) {

        }
    }

}