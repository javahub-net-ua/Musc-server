package net.javahub.musc.records;

import net.fabricmc.loader.api.FabricLoader;
import net.javahub.musc.Musc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MuscRecords {

    //public static final RecordHandler HANDLER = new RecordHandler();
    private static final Map<Path, Record> records = new HashMap<>();

    public MuscRecords() {

        Set<Path> paths = new HashSet<>();

        try {
            paths = getRecordFiles();
            for (Path path:paths) {
                try {
                    String id = Musc.CONFIG.overrides.get(path.toString().replace(".mp3", ""));
                    String name = path.toString().replace(".mp3", "");
                    records.put(path, new Record(name, id));
                } catch (IllegalArgumentException e) {
                    Musc.LOGGER.warn(e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            for (Map.Entry<Path, Record> entry:records.entrySet()) {
                Musc.LOGGER.info(entry.getValue().getSoundEventID());
            } Musc.LOGGER.info(String.format("%s/%s totally", records.size(), paths.size()));
        }
    }

    public Set<Path> getRecordFiles() throws IOException {
        Path path = Paths.get(FabricLoader
                        .getInstance()
                        .getConfigDir()
                        .toString(),
                        Musc.CONFIG.getDirectory(),
                        "/records/");
        try (Stream<Path> stream = Files.walk(path)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".mp3"))
                    .map(Path::getFileName)
                    .collect(Collectors.toSet());
        }
    }

}