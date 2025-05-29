package dev.emythiel.justsimpledrawers.datagen;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import dev.emythiel.justsimpledrawers.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, JustSimpleDrawers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.SINGLE_DRAWER.get())
            .add(ModBlocks.DOUBLE_DRAWER.get())
            .add(ModBlocks.QUAD_DRAWER.get());
            //.add(ModBlocks.COMPACTING_DRAWER.get())
            //.add(ModBlocks.CONTROLLER.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
            .add(ModBlocks.SINGLE_DRAWER.get())
            .add(ModBlocks.DOUBLE_DRAWER.get())
            .add(ModBlocks.QUAD_DRAWER.get());
            //.add(ModBlocks.COMPACTING_DRAWER.get())
            //.add(ModBlocks.CONTROLLER.get());
    }
}
