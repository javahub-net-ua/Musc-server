package net.javahub.musc.resources;

import com.google.gson.GsonBuilder;
import net.javahub.musc.Musc;
import net.javahub.musc.records.Record;
import net.minecraft.client.realms.util.JsonUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.LOGGER;

// Mess. I'll fix it later :)
public class ResourceBuilder {

    private final Set<Record> RECORDS;
    private final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public ResourceBuilder(Set<Record> records) {
        this.RECORDS = records;
    }

    public Path getResources() {
        try {
            return buildResources();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path buildResources() throws IOException {
        Path path = Files.createTempDirectory("musc");
        Path root = Files.createDirectories(Path.of(path + "/assets/musc"));
        Path lang = Files.createDirectories(Path.of(root + "/lang"));
        Path sounds = Files.createDirectories(Path.of(root + "/sounds/musc"));
        Path models = Files.createDirectories(Path.of(root + "/models/item"));
        Path textures = Files.createDirectories(Path.of(root + "/textures/item"));
        genPackMCMeta(path);
        genPackIcon(path);
        genLangFile(lang);
        genSoundFiles(sounds);
        genSoundsFile(root);
        genModelFiles(models);
        genTextures(textures);
        return pack(path);
    }

    private void genPackIcon(Path path) {
        try (InputStream is = Musc.class.getClassLoader().getResourceAsStream("musc/pack.png")) {
            Files.copy(is, Path.of(path + "/pack.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void genPackMCMeta(Path path) throws IOException {
        try (Writer writer = Files.newBufferedWriter(Path.of(path + "/pack.mcmeta"))) {
            GSON.toJson(new PackMCMeta(), writer);
        }
    }


    private void genLangFile(Path lang) throws IOException {
        Map<String, String> records = RECORDS.stream()
                .collect(Collectors.toMap(
                        r -> String.format("item.musc.%s", r.getItemID()),
                        r -> "Music Disc"));
        Map<String, String> descriptions = RECORDS.stream()
                .collect(Collectors.toMap(
                        r -> String.format("item.musc.%s.description", r.getItemID()),
                        r -> r.getTitle()));
        try (Writer writer = Files.newBufferedWriter(Path.of(lang + "/lang.json"))) {
            GSON.toJson(new Lang(records, descriptions).RECORDS, writer);
        }
    }

    private void genSoundsFile(Path musc) throws IOException {
        Sounds sounds = new Sounds(RECORDS);
        try (Writer writer = Files.newBufferedWriter(Path.of(musc + "/sounds.json"))) {
            GSON.toJson(sounds.RECORDS, writer);
        }
    }

    private void genSoundFiles(Path musc) {
        RECORDS.forEach(r -> {
            try {
                Files.copy(r.getPath(), Path.of(String.format("%s/%s.mp3", musc.toFile(), r.getItemID())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void genModelFiles(Path models) {
        RECORDS.forEach(r -> {
            try (Writer writer = Files.newBufferedWriter(Path.of(String.format("%s/%s.json", models, r.getItemID())))) {
                Model model = new Model("musc:music_disc_" + r.getItemID());
                GSON.toJson(model, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void genTextures(Path textures) {
        RECORDS.forEach(r -> {
            try (InputStream is = Musc.class.getClassLoader().getResourceAsStream("musc/disc.png")) {
                Files.copy(is, Path.of(String.format("%s/%s.png", textures, r.getItemID())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Path pack(Path src) throws IOException {
        Path dst = getDestination();
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(dst))) {
            zs.setLevel(0);
            Files.walk(src)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(src.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return dst;
        }
    }

    private Path getDestination() throws IOException {
        return Objects.equals(CONFIG.resources.pathOverride, "") ?
                Files.createTempFile("musc", ".zip") :
                Files.createFile(Path.of(CONFIG.resources.pathOverride));
    }
}