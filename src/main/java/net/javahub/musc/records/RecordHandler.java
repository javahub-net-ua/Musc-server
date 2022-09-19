package net.javahub.musc.records;

import net.fabricmc.loader.api.FabricLoader;
import net.javahub.musc.Musc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RecordHandler {

    private static Path path;

    RecordHandler() {
        path = Paths.get(FabricLoader
                .getInstance()
                .getConfigDir()
                .toString(),
                Musc.CONFIG.getDirectory(),
                "/records/");
        try {
            if (getDirectory(path) != null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getDirectory(Path path) throws IOException {
        return Files.createDirectory(path);
    }

}