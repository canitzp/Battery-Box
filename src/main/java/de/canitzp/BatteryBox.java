package de.canitzp;

import de.canitzp.data.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(BatteryBox.MODID)
public class BatteryBox {

    public static final String MODID = "batterybox";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BatteryBox.MODID);
    public static final RegistryObject<Block> BATTERY_BOX = BLOCKS.register("battery_box", () -> BatteryBoxBlock.INSTANCE);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BatteryBox.MODID);
    public static final RegistryObject<Item> BATTERY_BOX_ITEM = ITEMS.register("battery_box", () -> new BlockItem(BATTERY_BOX.get(), new Item.Properties()));

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BatteryBox.MODID);
    public static final RegistryObject<BlockEntityType<?>> BATTERY_BOX_ENTITY_TYPE = BLOCK_ENTITY_TYPES.register("battery_box", () -> BatteryBoxBlockEntity.TYPE);

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("tab", BatteryBoxTab::create);

    public BatteryBox() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        TABS.register(bus);
    }

    @SubscribeEvent
    public static void dataGeneration(GatherDataEvent event){
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new BBBlockStateProvider(generator, helper));
        generator.addProvider(event.includeClient(), new BBItemModelProvider(generator, helper));
        generator.addProvider(true, new BBTagProvider(generator, lookupProvider, helper));
        generator.addProvider(true, new BBRecipeProvider(generator));
        generator.addProvider(true, new BBLanguageEnglish(generator));
        generator.addProvider(true, new BBLootTableSub(generator));
    }
}
