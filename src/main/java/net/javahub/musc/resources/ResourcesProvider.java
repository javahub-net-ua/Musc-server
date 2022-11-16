package net.javahub.musc.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static net.javahub.musc.Musc.CONFIG;
import static net.javahub.musc.Musc.LOGGER;
import static net.javahub.musc.resources.Template.ROOT;

public class ResourcesProvider {

    protected static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .disableHtmlEscaping().setPrettyPrinting().create();

    public static Path getResources() {
        try {
            for (Resource resource : Resources.getResources()) resource.getResource();
            return pack();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path pack() throws IOException {
        LOGGER.info("packing...");
        Path dst = Path.of(CONFIG.resources().pathToResources);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(dst,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE));
             Stream<Path> stream = Files.walk(ROOT)) {
            zs.setLevel(0);
            for (Path p: stream.filter(p -> !Files.isDirectory(p)).collect(Collectors.toSet())) {
                ZipEntry zipEntry = new ZipEntry(ROOT.relativize(p).toString());
                zs.putNextEntry(zipEntry);
                Files.copy(p, zs);
                Files.delete(p);
                zs.closeEntry();
            }
        }
        return dst;
    }
}
