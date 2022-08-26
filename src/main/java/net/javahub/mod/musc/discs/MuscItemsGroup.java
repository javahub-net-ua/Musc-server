package net.javahub.mod.musc.discs;

import static net.javahub.mod.musc.Musc.MOD_ID;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class MuscItemsGroup implements ModInitializer {

    public static final ItemGroup MUSC = FabricItemGroupBuilder.create(
            new Identifier(MOD_ID, "group"))
            .icon(() -> new ItemStack(Blocks.JUKEBOX)).build();

}
