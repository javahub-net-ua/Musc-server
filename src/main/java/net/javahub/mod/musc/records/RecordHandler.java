package net.javahub.mod.musc.records;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.javahub.mod.musc.Musc.CONFIG;

public class RecordHandler {

    private static Path path;

    RecordHandler() {
        path = Paths.get(FabricLoader.getInstance().getConfigDir().toString(), CONFIG.getDirectory(), "/records/");
        try {
            if (getDirectory(path) != null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getDirectory(Path path) throws IOException {
        if (!Files.exists(path)) return Files.createDirectory(path);
        else return null;
    }

}