package net.javahub.musc.networking;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.RESOURCES;

class HttpServer extends Thread {

    public ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(CONFIG.distribution.port);
            ExecutorService executor = Executors.newCachedThreadPool();
            while (!Thread.currentThread().isInterrupted()) {
                ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
                executor.execute(clientHandler);
            } executor.shutdownNow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record ClientHandler(Socket clientSocket) implements Runnable {

        @Override
        public void run() {
            try {
                OutputStream os = clientSocket.getOutputStream();
                String header = getHeader();
                os.write(header.getBytes());
                Files.copy(RESOURCES, os);
                os.flush();
            } catch (IOException ignored) {}
        }

        private static String getHeader() throws IOException {
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String REQ_FOUND = "HTTP/1.0 200 OK\n";
            String SERVER = "Server: HTTP server/0.1\n";
            String DATE = "Date: " + format.format(new java.util.Date()) + "\n";
            String CONTENT_TYPE = "Content-type: application/zip\n";
            String NAME = "Content-Disposition: form-data; filename=" + RESOURCES.getFileName() + "\n";
            String LENGTH = "Content-Length: " + Files.size(RESOURCES) + "\n\n";
            return REQ_FOUND + SERVER + DATE + NAME + CONTENT_TYPE + LENGTH;
        }
    }
}
