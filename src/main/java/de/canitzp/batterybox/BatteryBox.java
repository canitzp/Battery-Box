package de.canitzp.batterybox;

import de.canitzp.batterybox.data.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
@Mod(BatteryBox.MODID)
public class BatteryBox {

    public static final String MODID = "batterybox";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, BatteryBox.MODID);
    public static final Supplier<BatteryBoxBlock> BATTERY_BOX = BLOCKS.register("battery_box", () -> BatteryBoxBlock.INSTANCE);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, BatteryBox.MODID);
    public static final Supplier<BlockItem> BATTERY_BOX_ITEM = ITEMS.register("battery_box", () -> new BlockItem(BATTERY_BOX.get(), new Item.Properties()));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BatteryBox.MODID);
    public static final Supplier<BlockEntityType<BatteryBoxBlockEntity>> BATTERY_BOX_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("battery_box", () -> BatteryBoxBlockEntity.TYPE);

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final Supplier<CreativeModeTab> TAB = TABS.register("tab", BatteryBoxTab::create);

    public BatteryBox(IEventBus bus, ModContainer modContainer) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        TABS.register(bus);

        bus.addListener(this::registerCapabilities);
    }

    @SubscribeEvent
    public static void dataGeneration(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new BBBlockStateProvider(generator, helper));
        generator.addProvider(event.includeClient(), new BBItemModelProvider(generator, helper));
        generator.addProvider(true, new BBTagProvider(generator, lookupProvider, helper));
        generator.addProvider(true, new BBRecipeProvider(generator, lookupProvider));
        generator.addProvider(true, new BBLanguageEnglish(generator));
        generator.addProvider(true, new BBLootTableSub(generator, lookupProvider));
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, BATTERY_BOX_ENTITY_TYPE.get(), BatteryBoxBlockEntity::getEnergyStorage);
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, BATTERY_BOX_ENTITY_TYPE.get(), BatteryBoxBlockEntity::getItemHandler);
    }
}
