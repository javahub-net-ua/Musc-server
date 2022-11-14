package net.javahub.musc.resources;

import java.io.File;
import java.nio.file.Path;

enum Template {
    ROOT (Path.of(".musc")),
    MUSC (Path.of(ROOT + File.separator + "assets" + File.separator + "musc")),
    LANG (Path.of(ROOT + File.separator + "lang")),
    SOUNDS (Path.of(ROOT + File.separator + "sounds" + File.separator + "records")),
    MODELS (Path.of(ROOT + File.separator + "models" + File.separator + "item"));

    Template(Path path) {

    }
}
