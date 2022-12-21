package net.javahub.musc.lootTables;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.javahub.musc.records.RecordBuilder;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;

import java.util.Objects;

import static net.javahub.musc.Musc.CONFIG;

public class MuscLootTableProvider {

    private static Identifier getIdentifier(RecordBuilder.Record record) {
        String id = CONFIG.mobBindings.get(record.getTitle());
        Identifier identifier = null;
        if (Objects.nonNull(id)) identifier = new Identifier(id.split(":")[0], id.split(":")[1]);
        return Objects.nonNull(identifier) ? identifier: new Identifier("");
    }

    private static float getChance(RecordBuilder.Record record) {
        String id = CONFIG.mobBindings.get(record.getTitle());
        float chance = 0.0f;
        if (Objects.nonNull(id) && id.split(":").length == 3) chance = Float.parseFloat(id.split(":")[2]);
        return chance;
    }

    public static void modifyLootTables(RecordBuilder.Record record) {
        Item item = record.getItem();
        Identifier identifier = getIdentifier(record);
        float chance = getChance(record);
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if(identifier.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceWithLootingLootCondition.builder(chance, chance))
                        .conditionally(EntityPropertiesLootCondition.builder(
                                LootContext.EntityTarget.KILLER_PLAYER,
                                new EntityPredicate.Builder().build()))
                        .with(ItemEntry.builder(item))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
