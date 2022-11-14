package net.javahub.musc.networking;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static net.javahub.musc.Musc.CONFIG;

class HttpServer implements Runnable {

    private final Path resources;

    public HttpServer(Path resources) {
        this.resources = resources;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(CONFIG.distribution().port)) {
            while (!Thread.currentThread().isInterrupted()) {
                ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class ClientHandler implements Runnable {

        private final Socket clientSocket;

        ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (OutputStream os = clientSocket.getOutputStream()) {
                String header = getHeader();
                os.write(header.getBytes());
                Files.copy(resources, os);
                os.flush();
                Thread.currentThread().join();
            } catch (IOException | InterruptedException ignored) {}
        }

        private String getHeader() throws IOException {
            SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String REQ_FOUND = "HTTP/1.0 200 OK\n";
            String SERVER = "Server: HTTP server/0.1\n";
            String DATE = "Date: " + format.format(new java.util.Date()) + "\n";
            String CONTENT_TYPE = "Content-type: application/zip\n";
            String NAME = "Content-Disposition: form-data; filename=" + resources.getFileName() + "\n";
            String LENGTH = "Content-Length: " + Files.size(resources) + "\n\n";
            System.out.println(REQ_FOUND + SERVER + DATE + NAME + CONTENT_TYPE + LENGTH);
            return REQ_FOUND + SERVER + DATE + NAME + CONTENT_TYPE + LENGTH;
        }
    }
}
