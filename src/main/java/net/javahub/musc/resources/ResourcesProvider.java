package net.javahub.musc.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static net.javahub.musc.Musc.Config;
import static net.javahub.musc.Musc.LOGGER;
import static net.javahub.musc.resources.Template.ROOT;

public class ResourcesProvider {

    protected static final Gson GSON = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation().disableHtmlEscaping()
            .setPrettyPrinting().create();

    public static Path getResources() {
        try {
            List<Resource> resources = Resources.getResources();
            for (Resource resource : resources) resource.getResource();
            Path path = Path.of(Config.resources.pathToResources);
            pack(path);
            return path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void pack(Path dst) throws IOException {
        LOGGER.info("packing...");
        try (OutputStream os = Files.newOutputStream(dst, TRUNCATE_EXISTING, CREATE);
             ZipOutputStream zs = new ZipOutputStream(os);
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
    }
}
