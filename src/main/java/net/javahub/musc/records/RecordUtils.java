package net.javahub.musc.records;

import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.LOGGER;

public class RecordUtils {

    private static Path getRecordsDirectory() throws IOException {
        Path path = Path.of(FabricLoader.getInstance().getConfigDir() + CONFIG.getDirectory() + "/records");
        return Files.exists(path) ? path : Files.createDirectory(path);
    }

    private static Set<Path> getRecordFiles() throws IOException {
        Path dir = getRecordsDirectory();
        try (Stream<Path> stream = Files.walk(dir)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".mp3"))
                    .collect(Collectors.toSet());
        }
    }

    public static Set<Record> getRecords() {
        try {
            Set<Path> files = getRecordFiles();
            Set<Record> records = new HashSet<>();
            for (Path file: files) {
                String title = file.getFileName().toString().replace(".mp3", "");
                String override = CONFIG.overrides.get(title);
                try {
                    records.add(Record.buildRecord(title, file, override));
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getMessage());
                }
            }
            LOGGER.info(String.format("%s records added", records.size()));
            return records;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}