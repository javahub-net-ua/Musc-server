package net.javahub.musc.records;

import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.LOGGER;

public class RecordUtils {

    private static Path getRecordsDirectory() throws IOException {
        Path path = Path.of(FabricLoader.getInstance().getConfigDir() + File.separator + "records");
        return Files.exists(path) ? path : Files.createDirectory(path);
    }

    private static TreeSet<Path> getRecordFiles() throws IOException {
        Path dir = getRecordsDirectory();
        try (Stream<Path> stream = Files.walk(dir)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".ogg"))
                    .collect(Collectors.toCollection(TreeSet::new));
        }
    }

    public static Set<Record> getRecords() {
        try {
            TreeSet<Path> files = getRecordFiles();
            Set<Record> records = new LinkedHashSet<>();
            for (Path file: files) {
                String title = file.getFileName().toString().replace(".ogg", "");
                String override = CONFIG.overrides().get(title);
                try {
                    records.add(Record.of(title, file, override));
                } catch (IllegalArgumentException e) {
                    LOGGER.warn(e.getMessage());
                }
            }
            LOGGER.info(String.format("%s records will be added", records.size()));
            return records;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
