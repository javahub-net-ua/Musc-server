package net.javahub.musc.resources;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

class Lang implements Serializable {
    Map<String, String> records = new HashMap<>();
    Map<String, String> descriptions = new HashMap<>();
    String group = "";

    Lang(Map records, Map descriptions) {
        this.records = records;
        this.descriptions = descriptions;
    }
}

class Model implements Serializable {
    String parent = "musc:item/generated";
    Map<String, String> textures = new HashMap<>();
    Model(String itemID) {
        textures.put("layer0", itemID);
    }
}