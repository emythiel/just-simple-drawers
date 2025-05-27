package dev.emythiel.justsimpledrawers.datagen;

import dev.emythiel.justsimpledrawers.JustSimpleDrawers;
import dev.emythiel.justsimpledrawers.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, JustSimpleDrawers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.UPGRADE_BASE.get());
        basicItem(ModItems.UPGRADE_T1.get());
        basicItem(ModItems.UPGRADE_T2.get());
        basicItem(ModItems.UPGRADE_T3.get());
        basicItem(ModItems.UPGRADE_T4.get());
    }
}
