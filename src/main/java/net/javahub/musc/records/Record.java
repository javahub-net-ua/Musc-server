package net.javahub.musc.records;

import java.io.Serializable;
import java.nio.file.Path;

public class Record {
    private final String title;
    private final Path path;
    private final String id;

    private Record(String title, Path path, String id) {
        this.title = title;
        this.path = path;
        this.id = id;
    }

    public static Record buildRecord(String title, Path path, String override) throws IllegalArgumentException {
        return override == null ? new Record(title, path, format(title)) : new Record(title, path, verify(override, title));
    }

    private static String verify(String id, String title) throws IllegalArgumentException {
        if (!id.matches("(.+?)@(.+?)") || !id.matches("[a-z0-9@._-]+$")) throw new IllegalArgumentException(title);
        return id;
    }

    private static String format(String title) throws IllegalArgumentException {
        String result = title.toLowerCase()
                .replaceFirst(" - ", "@")
                .replaceAll("[\\p{Punct}&&[^@-]]", "")
                .replaceAll("[\\s-]", "_");
        return verify(result, title);
    }

    public String getTitle() {
        return title;
    }
    public Path getPath() {
        return path;
    }
    public String getSoundEventID() {
        return id.replace("@", ".");
    }
    public String getItemID() {
        return "music_disc_" + id.replace("@", "_");
    }
}