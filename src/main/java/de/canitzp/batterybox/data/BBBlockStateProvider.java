package de.canitzp.batterybox.data;

import de.canitzp.batterybox.BatteryBox;
import de.canitzp.batterybox.BatteryBoxBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BBBlockStateProvider extends BlockStateProvider {

    public BBBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), BatteryBox.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        super.getVariantBuilder(BatteryBox.BATTERY_BOX.get())
                .partialState().with(BatteryBoxBlock.CHARGE, BatteryBoxBlock.CHARGE_EMPTY).addModels(new ConfiguredModel(this.models().cubeAll("battery_box_0", ResourceLocation.fromNamespaceAndPath(BatteryBox.MODID, "block/battery_box_0"))))
                .partialState().with(BatteryBoxBlock.CHARGE, BatteryBoxBlock.CHARGE_PRESENT_EMPTY).addModels(new ConfiguredModel(this.models().cubeAll("battery_box_1", ResourceLocation.fromNamespaceAndPath(BatteryBox.MODID, "block/battery_box_1"))))
                .partialState().with(BatteryBoxBlock.CHARGE, BatteryBoxBlock.CHARGE_PRESENT_LOW).addModels(new ConfiguredModel(this.models().cubeAll("battery_box_2", ResourceLocation.fromNamespaceAndPath(BatteryBox.MODID, "block/battery_box_2"))))
                .partialState().with(BatteryBoxBlock.CHARGE, BatteryBoxBlock.CHARGE_PRESENT_HIGH).addModels(new ConfiguredModel(this.models().cubeAll("battery_box_3", ResourceLocation.fromNamespaceAndPath(BatteryBox.MODID, "block/battery_box_3"))))
                .partialState().with(BatteryBoxBlock.CHARGE, BatteryBoxBlock.CHARGE_PRESENT_FULL).addModels(new ConfiguredModel(this.models().cubeAll("battery_box_4", ResourceLocation.fromNamespaceAndPath(BatteryBox.MODID, "block/battery_box_4"))));
    }
}
