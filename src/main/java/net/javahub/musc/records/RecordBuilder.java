package net.javahub.musc.records;

import com.google.gson.annotations.Expose;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.nio.file.Path;

import static net.javahub.musc.Musc.DiscIDs;

public class RecordBuilder {

    private static boolean verify(String id) {
        return id.matches("(.+?)@(.+?)") && id.matches("[a-z0-9@._-]+$");
    }

    public Record of(Path file) {
        String title = file.getFileName().toString().replace(".ogg", "");
        String id = DiscIDs.getValue(title);
        return verify(id) ? new Record(file, title, id) : null;
    }

    public static class Record {

        private final Path path;
        private Item item;

        @Expose private final String title;
        @Expose private final String id;

        private Record(Path path, String title, String id) {
            this.path = path;
            this.title = title;
            this.id = id;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }

        public String getTitle() {
            return title;
        }

        public Path getPath() {
            return path;
        }

        public Identifier getSoundEventID() {
            return new Identifier("musc", "music_disc." + getSoundName());
        }

        public Identifier getItemID() {
            return new Identifier("musc", getItemName());
        }

        public String getItemName() {
            return String.format("music_disc_%s", id.replace("@", "_"));
        }

        public String getSoundName() {
            return id.replace("@", ".");
        }

        public String getModelFileName() {
            return getItemName() + ".json";
        }

        public String getSoundFileName() {
            return getSoundName() + ".ogg";
        }
    }
}
