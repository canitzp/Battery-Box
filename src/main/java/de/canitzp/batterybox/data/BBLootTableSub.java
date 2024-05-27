package de.canitzp.batterybox.data;

import de.canitzp.batterybox.BatteryBox;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;

public class BBLootTableSub extends LootTableProvider {

    public BBLootTableSub(DataGenerator generator) {
        super(generator.getPackOutput(), Set.of(), List.of());
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return List.of(new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK));
    }

    private static class BlockLoot extends BlockLootSubProvider {

        protected BlockLoot() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            List<Block> list = (List<Block>) BatteryBox.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList();
            return list;
        }

        @Override
        protected void generate() {
            this.dropSelf(BatteryBox.BATTERY_BOX.get());
        }

    }
}
