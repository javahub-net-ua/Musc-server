package net.javahub.musc.resources;

import com.google.gson.annotations.SerializedName;
import net.javahub.musc.records.Record;

import java.io.Serializable;
import java.util.*;

import static net.javahub.musc.Musc.CONFIG;

class PackMCMeta implements Serializable {
    @SerializedName("pack")
    final Map<String, String> PACK = new LinkedHashMap<>();

    PackMCMeta() {
        PACK.put("pack_format", CONFIG.resources.pack_version);
        PACK.put("description", CONFIG.resources.description);
    }
}

class Lang implements Serializable {
    final SortedMap<String, String> RECORDS = new TreeMap<>();

    Lang(Map records, Map descriptions) {
        this.RECORDS.putAll(records);
        this.RECORDS.putAll(descriptions);
        this.RECORDS.put("itemGroup.musc", CONFIG.getName());
    }
}

class Sounds {
    final Map<String, Map<String, Object>> RECORDS = new LinkedHashMap<>();
    Sounds (Set<Record> records) {
        SortedSet<String> soundEventIDs = new TreeSet<>();
        records.forEach(r -> soundEventIDs.add(r.getSoundEventID()));
        for (String soundEventID : soundEventIDs) {
            Map<String, String> m1 = new LinkedHashMap<>();
            m1.put("name", "musc/" + soundEventID);
            m1.put("stream", "true");

            List<Map<String, String>> l1 = new ArrayList<>();
            l1.add(m1);

            Map<String, Object> m2 = new LinkedHashMap<>();
            m2.put("category", "record");
            m2.put("sounds", l1);

            RECORDS.put("musc.music_disc." + soundEventID, m2);
        }
    }
}

class Model implements Serializable {
    final String parent = "musc:item/generated";
    final Map<String, String> textures = new HashMap<>();
    Model(String itemID) {
        textures.put("layer0", itemID);
    }
}