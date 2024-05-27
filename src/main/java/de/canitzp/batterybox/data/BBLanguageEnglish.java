package de.canitzp.batterybox.data;

import de.canitzp.batterybox.BatteryBox;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class BBLanguageEnglish extends LanguageProvider {

    public BBLanguageEnglish(DataGenerator generator) {
        super(generator.getPackOutput(), BatteryBox.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add("tab.batterybox", "Battery Box");
        this.add(BatteryBox.BATTERY_BOX.get(), "Battery Box");
        this.add("block.batterybox.battery_box.description", "Insert any battery, to create a block version of it. Multiples can be put together to form a big battery. All sides are inputs and outputs.");
        this.add("block.batterybox.battery_box.empty", "Empty");
        this.add("block.batterybox.battery_box.charging_only", "Charging only!");
    }
}
