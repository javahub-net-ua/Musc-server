package net.javahub.mod.musc.discs;

import static net.javahub.mod.musc.Musc.MOD_ID;

import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class MuscItems {

    public static void register(String id, SoundEvent sound) {
        Item.Settings settings = new Item.Settings()
                .rarity(Rarity.RARE)
                .maxCount(1)
                .group(MuscItemsGroup.MUSC);
        Registry.register(Registry.ITEM,
            new Identifier(MOD_ID, id),
            new MuscDiscItem(14, sound, settings));
    }

    public static SoundEvent register(String name) {
        Identifier id = new Identifier(MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void add() {
        for (int i = 0; i < 10; i++) {
            SoundEvent sound = register("music_disc." + i);
            register("music_disc_" + i, sound);
        }
    }

}
