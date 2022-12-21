package net.javahub.musc.records;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.LOGGER;
import static net.javahub.musc.records.RecordBuilder.Record;

public class RecordUtils {

    private static Path getRecordsDirectory() throws IOException {
        Path path = Path.of(FabricLoader.getInstance().getConfigDir().toString(), "records");
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

    public static LinkedHashSet<Record> getRecords() {
        try {
            TreeSet<Path> files = getRecordFiles();
            RecordBuilder builder = new RecordBuilder(CONFIG.overrides);
            LinkedHashSet<Record> records = files.stream().map(builder::of)
            .filter(Objects::nonNull).collect(Collectors.toCollection(LinkedHashSet::new));
            LOGGER.info(String.format("%s out of %s records will be added", records.size(), files.size()));
            return records;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
