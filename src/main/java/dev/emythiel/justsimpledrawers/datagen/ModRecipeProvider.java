package dev.emythiel.justsimpledrawers.datagen;

import dev.emythiel.justsimpledrawers.registry.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        // Single Drawer Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SINGLE_DRAWER.get())
            .pattern(" I ")
            .pattern("IBI")
            .pattern(" I ")
            .define('I', Items.IRON_INGOT)
            .define('B', Items.BARREL)
            .unlockedBy("has_barrel", has(Items.BARREL)).save(recipeOutput);

        // Double Drawer Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DOUBLE_DRAWER.get())
            .pattern("IBI")
            .pattern(" I ")
            .pattern("IBI")
            .define('I', Items.IRON_INGOT)
            .define('B', Items.BARREL)
            .unlockedBy("has_barrel", has(Items.BARREL)).save(recipeOutput);

        // Quad Drawer Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.QUAD_DRAWER.get())
            .pattern("BIB")
            .pattern("III")
            .pattern("BIB")
            .define('I', Items.IRON_INGOT)
            .define('B', Items.BARREL)
            .unlockedBy("has_barrel", has(Items.BARREL)).save(recipeOutput);

        /*// Compacting Drawer Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COMPACTING_DRAWER.get())
            .pattern("GPG")
            .pattern("GBG")
            .pattern("BGB")
            .define('G', Items.GOLD_INGOT)
            .define('P', Items.PISTON)
            .define('B', Items.BARREL)
            .unlockedBy("has_barrel", has(Items.BARREL)).save(recipeOutput);

        // Controller Recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CONTROLLER.get())
            .pattern("GRG")
            .pattern("GBG")
            .pattern("GGG")
            .define('G', Items.GOLD_INGOT)
            .define('R', Items.REDSTONE_TORCH)
            .define('B', Items.BARREL)
            .unlockedBy("has_barrel", has(Items.BARREL)).save(recipeOutput);*/
    }
}
/*
" I ",
"IBI",
" I "
 */
