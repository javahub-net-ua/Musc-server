package net.javahub.musc.resources;

import java.util.ArrayList;
import java.util.List;

class Resources {

    private static final List<Resource> RESOURCES = new ArrayList<>(){{
        add(new IconPNG());
        add(new MuscJSON());
        add(new PackMCMeta());
        add(new LangFiles());
        add(new Models());
        add(new SoundsFile());
        add(new SoundFiles());
    }};

    public static List<Resource> getResources() {
        return RESOURCES;
    }
}
