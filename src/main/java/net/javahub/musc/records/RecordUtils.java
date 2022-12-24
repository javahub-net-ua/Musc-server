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

    private static void updateConfig(Set<Path> files) {
        Set<String> titles = files.stream().map(Path::getFileName).map(Path::toString)
                .map(f -> f.split(".ogg")[0]).collect(Collectors.toSet());
        for (String title: titles) {
            if (!CONFIG.mobBindings.containsKey(title))
                CONFIG.mobBindings.put(title, "");
            CONFIG.mobBindings.keySet().stream()
                    .filter(k -> !titles.contains(k)).forEach(CONFIG.mobBindings::remove);
            if (!CONFIG.overrides.containsKey(title))
                CONFIG.overrides.put(title, "");
            CONFIG.overrides.keySet().stream()
                    .filter(k -> !titles.contains(k)).forEach(CONFIG.overrides::remove);
        }
        CONFIG.save();
    }

    public static LinkedHashSet<Record> getRecords() {
        try {
            TreeSet<Path> files = getRecordFiles();
            updateConfig(files);
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
