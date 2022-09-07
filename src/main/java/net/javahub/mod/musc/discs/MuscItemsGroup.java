package net.javahub.mod.musc.discs;

import static net.javahub.mod.musc.Musc.CONFIG;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class MuscItemsGroup implements ModInitializer {

    public static final ItemGroup MUSC = FabricItemGroupBuilder.create(
            new Identifier(CONFIG.getModid(), "group"))
            .icon(() -> new ItemStack(Blocks.JUKEBOX)).build();

}
