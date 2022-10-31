package net.javahub.musc.discs;

import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static net.javahub.musc.Musc.CONFIG;

public class MuscItems {

    public static void register(String id, SoundEvent sound) {
        Item.Settings settings = new Item.Settings()
                .rarity(Rarity.RARE)
                .maxCount(1)
                .group(MuscItemsGroup.MUSC_GROUP);
        Registry.register(Registry.ITEM,
            new Identifier(CONFIG.getModID(), id),
            new MuscDiscItem(14, sound, settings));
    }

    public static SoundEvent register(String name) {
        Identifier id = new Identifier(CONFIG.getModID(), name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void add(String id) {
        SoundEvent sound = register("music_disc." + id);
        register("music_disc_" + id, sound);
    }

}
