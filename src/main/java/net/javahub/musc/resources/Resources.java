package net.javahub.musc.resources;

import com.google.gson.*;
import net.javahub.musc.Musc;
import net.javahub.musc.records.Record;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.LOGGER;
import static net.javahub.musc.resources.Template.*;

public class Resources {

    private Set<Record> records;
    private Template template;
    private final static Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .disableHtmlEscaping().setPrettyPrinting().create();


    public Path of(Set<Record> records) {
        this.records = records;
        try {
            genPackIcon();
            genPackMCMeta();
            genMuscJson();
            genSoundsFile();
            genLangFile();
            genSoundFiles();
            genModelFiles();
            return pack();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void genPackIcon() throws IOException {
        try (InputStream is = Musc.class.getClassLoader()
                .getResourceAsStream("assets" + File.separator + "musc" + File.separator + "icon.png")) {
            if (is != null) {
                Files.copy(is, Path.of(ROOT + File.separator + "pack.png"), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private void genPackMCMeta() throws IOException {
        try (Writer writer = Files.newBufferedWriter(Path.of(ROOT + File.separator + "pack.mcmeta"))) {
            JsonObject json = new JsonObject();
            JsonObject pack = new JsonObject();
            pack.addProperty("pack_format", CONFIG.resources().packFormat);
            pack.addProperty("description", CONFIG.resources().description);
            json.add("pack", pack);
            GSON.toJson(json, writer);
        }
    }

    private void genMuscJson() throws IOException {
        try (Writer writer = Files.newBufferedWriter(Path.of(ROOT + File.separator + "musc.json"))) {
            GSON.toJson(records, writer);
        }
    }

    private void genSoundsFile() throws IOException {
        JsonObject json = new JsonObject();
        for (Record record : records) {
            JsonArray sounds = new JsonArray();
            JsonObject sound = new JsonObject();
            sound.addProperty("name", "musc:records/" + record.getSoundName());
            sound.addProperty("stream", "true");
            sounds.add(sound);

            JsonObject info = new JsonObject();
            info.addProperty("category", "record");
            info.add("sounds", sounds);

            json.add("music_disc." + record.getSoundName(), info);
        }
        try (Writer writer = Files.newBufferedWriter(Path.of(MUSC + File.separator + "sounds.json"))) {
            GSON.toJson(json, writer);
        }
    }

    private void genLangFile() throws IOException {
        for (Map.Entry<String,String> translation : CONFIG.resources().translations.entrySet()) {
            JsonObject json = new JsonObject();
            records.forEach(record -> {
                json.addProperty(String.format("item.musc.%s", record.getItemName()), translation.getValue());
                json.addProperty(String.format("item.musc.%s.desc", record.getItemName()), record.getTitle());
            });
            try (Writer writer = Files.newBufferedWriter(Path.of(
                    LANG + String.format("%s%s.json", File.separator, translation.getKey())))) {
                GSON.toJson(json, writer);
            }
        }
    }

    private void genSoundFiles() throws IOException {
        for (Record record : records) {
            Files.copy(record.getPath(),
                    Path.of(SOUNDS + File.separator + record.getSoundFileName()),
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void genModelFiles() throws IOException {
        for (Record record: records) {
            try (Writer writer = Files.newBufferedWriter(
                    Path.of(MODELS + File.separator + record.getModelFileName()))) {
                JsonObject json = new JsonObject();
                json.addProperty("parent", "item/generated");

                JsonObject texture = new JsonObject();
                texture.addProperty("layer0", "musc:item/disc");
                json.add("textures",texture);
                GSON.toJson(json, writer);
            }
        }
    }

    private Path pack() throws IOException {
        LOGGER.info("packing...");
        Path dst = Path.of(CONFIG.resources().pathToResources);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(dst,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE));
             Stream<Path> stream = Files.walk(ROOT)) {
            zs.setLevel(0);
            stream.filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(ROOT.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            Files.delete(path);
                            zs.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        return dst;
    }
}
