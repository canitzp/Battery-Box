package de.canitzp.batterybox.data;

import de.canitzp.batterybox.BatteryBox;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class BBRecipeProvider extends RecipeProvider {

    public BBRecipeProvider(DataGenerator generator, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generator.getPackOutput(), lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BatteryBox.BATTERY_BOX.get())
                .define('g', Tags.Items.NUGGETS_GOLD)
                .define('r', Items.REPEATER)
                .define('c', Tags.Items.INGOTS_COPPER)
                .define('b', Items.BARREL)
                .define('i', Items.IRON_BLOCK)
                .define('s', Items.SMOOTH_STONE)
                .pattern("grg")
                .pattern("cbc")
                .pattern("isi")
                .unlockedBy("has_smooth_stone", has(Tags.Items.INGOTS_COPPER))
                .save(consumer);
    }
}
