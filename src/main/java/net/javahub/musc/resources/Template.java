package net.javahub.musc.resources;

import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

class Template {
    public static final Path ROOT = Path.of(".musc");
    public static final Path ASSETS = Path.of(".musc", "assets", "musc");
    public static final Path LANG = Path.of(".musc", "assets", "musc", "lang");
    public static final Path SOUNDS = Path.of(".musc", "assets", "musc", "sounds", "records");
    public static final Path MODELS = Path.of(".musc", "assets", "musc", "models", "item");
    public static final Path TEXTURES = Path.of(".musc", "assets", "musc", "textures", "item");
    static {
        Stream.of(ROOT, ASSETS, LANG, SOUNDS, MODELS, TEXTURES).map(Path::toFile).forEach(File::mkdirs);
    }
}
