package net.javahub.mod.musc.records;

import java.util.regex.Pattern;

class Record {

    private String name;
    private String soundEventID;
    private String itemID;

    Record(String fileName, String id) {
        if(id == null) id = getID(fileName);
        name = fileName;
        soundEventID = id.replace("@", ".");
        itemID = id.replace("@", "_");
    }

    private static String getID(String fileName) {
        if (!Pattern.matches(".* - .*", fileName))
            throw new IllegalArgumentException(fileName);
        String id = fileName.toLowerCase()
                .replaceAll(" - ", "@")
                .replaceAll("[,'\\(\\)]", "")
                .replaceAll("[ -]", "_");
        if(!id.matches("[[a-z_@]\\d]+$"))
            throw new IllegalArgumentException(fileName);
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSoundEventID() {
        return soundEventID;
    }

    public String getItemID() {
        return itemID;
    }

}
