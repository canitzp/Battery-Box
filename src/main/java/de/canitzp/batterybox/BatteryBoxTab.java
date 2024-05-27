package de.canitzp.batterybox;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BatteryBoxTab {

    public static final ItemStack ICON = BatteryBox.BATTERY_BOX_ITEM.get().getDefaultInstance();
    public static final Component TITLE = Component.translatable("tab." + BatteryBox.MODID);

    public static CreativeModeTab create() {
        return CreativeModeTab.builder()
                .icon(() -> ICON)
                .title(TITLE)
                .displayItems(BatteryBoxTab::addItemsToDisplay)
                .build();
    }
    private static void addItemsToDisplay(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output){
        output.accept(BatteryBox.BATTERY_BOX_ITEM.get().getDefaultInstance());
    }

}
