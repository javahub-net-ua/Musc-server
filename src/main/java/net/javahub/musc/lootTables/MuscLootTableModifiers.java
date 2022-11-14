package net.javahub.musc.lootTables;

public class MuscLootTableModifiers {

    private static void modifyLootTables() {
//        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
//            if(CREEPER_ID.equals(id)) {
//                LootPool.Builder poolBuilder = LootPool.builder()
//                        .rolls(ConstantLootNumberProvider.create(1))
//                        .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER,
//                                new EntityPredicate.Builder().equipment(EntityEquipmentPredicate.Builder.create()
//                                        .mainhand(ItemPredicate.Builder.create().items(Items.GOLDEN_AXE).build()).build()).build()))
//                        .with(ItemEntry.builder(ModItems.EGGPLANT))
//                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.1f, 1.0f)).build());
//                tableBuilder.pool(poolBuilder.build());
//            }
//        });
    }
}
