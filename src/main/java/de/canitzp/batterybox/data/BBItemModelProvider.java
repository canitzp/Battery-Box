package de.canitzp.batterybox.data;

import de.canitzp.batterybox.BatteryBox;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BBItemModelProvider extends ItemModelProvider {

    public BBItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator.getPackOutput(), BatteryBox.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(BatteryBox.BATTERY_BOX.get()).getPath(), new ResourceLocation(BatteryBox.MODID, "block/battery_box_0"));
    }
}
