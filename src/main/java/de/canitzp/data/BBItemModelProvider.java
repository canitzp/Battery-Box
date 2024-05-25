package de.canitzp.data;

import de.canitzp.BatteryBox;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class BBItemModelProvider extends ItemModelProvider {

    public BBItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), BatteryBox.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(BatteryBox.BATTERY_BOX.get()).getPath(), new ResourceLocation(BatteryBox.MODID, "block/battery_box_0"));
    }
}
