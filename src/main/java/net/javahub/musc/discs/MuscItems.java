package net.javahub.musc.discs;

import net.javahub.musc.records.Record;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class MuscItems {

    private static Item registerItem(Identifier id, SoundEvent sound) {
        Item.Settings settings = new Item.Settings()
                .rarity(Rarity.RARE).maxCount(1).group(ItemGroup.MISC);
        MuscDiscItem disc = new MuscDiscItem(14, sound, settings);
        return Registry.register(Registry.ITEM, id, disc);
    }

    private static SoundEvent registerSoundEvent(Identifier id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static Item registerRecord(Record record) {
        System.out.println(record.getTitle() + " " + record.getItemName());
        SoundEvent soundEvent = registerSoundEvent(record.getSoundEventID());
        return registerItem(record.getItemID(), soundEvent);
    }
}
