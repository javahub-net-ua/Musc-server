package net.javahub.musc.records;

import com.google.gson.annotations.Expose;
import net.javahub.musc.Musc;
import net.minecraft.util.Identifier;

import java.nio.file.Path;

public class Record {

    @Expose
    private final String title;
    private final Path path;
    @Expose
    private final String id;

    private Record(String title, Path path, String id) {
        this.title = title;
        this.path = path;
        this.id = id;
    }

    public static Record of(String title, Path path, String override) throws IllegalArgumentException {
        return override == null ? new Record(title, path, format(title)) : new Record(title, path, verify(override, title));
    }

    private static String verify(String id, String title) throws IllegalArgumentException {
        if (!id.matches("(.+?)@(.+?)") || !id.matches("[a-z0-9@._-]+$")) throw new IllegalArgumentException(title);
        return id;
    }

    private static String format(String title) throws IllegalArgumentException {
        String result = title.toLowerCase()
                .replaceAll(" \\(.*?\\)", "")
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

    public Identifier getSoundEventID() {
        return new Identifier(Musc.MOD_ID, id.replace("@", "."));
    }

    public Identifier getItemID() {
        return new Identifier(Musc.MOD_ID, String.format("music_disc_%s", id.replace("@", "_")));
    }

    public String getItemName() {
        return String.format("music_disc_%s", id.replace("@", "_"));
    }

    public String getSoundFileName() {
        return String.format("%s.ogg", id.replace("@", "."));
    }

    public String getModelFileName() {
        return String.format("music_disc_%s.json", id.replace("@", "_"));
    }

    public String getSoundName() {
        return id.replace("@", ".");
    }
}
