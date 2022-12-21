package net.javahub.musc.discs;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.javahub.musc.records.RecordBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class MuscItems {

    private static int getComparatorOutput(Identifier id) {
        return (int) (14 * Math.sin(id.hashCode()));
    }

    private static Item registerItem(Identifier id, SoundEvent sound) {
        Item.Settings settings = new Item.Settings()
                .rarity(Rarity.RARE).maxCount(1);
        int c = getComparatorOutput(id);
        MuscDiscItem disc = new MuscDiscItem(c, sound, settings);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(entries -> entries.add(disc));
        return Registry.register(Registries.ITEM, id, disc);
    }

    private static SoundEvent registerSoundEvent(Identifier id) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerRecord(RecordBuilder.Record record) {
        SoundEvent soundEvent = registerSoundEvent(record.getSoundEventID());
        record.setItem(registerItem(record.getItemID(), soundEvent));
    }
}
