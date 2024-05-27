package de.canitzp.batterybox;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BatteryBoxBlock extends BaseEntityBlock {

    public static final MapCodec<BatteryBoxBlock> CODEC = simpleCodec(BatteryBoxBlock::new);
    public static final IntegerProperty CHARGE = IntegerProperty.create("charge", 0, 4);
    public static final int CHARGE_EMPTY = 0;
    public static final int CHARGE_PRESENT_EMPTY = 1;
    public static final int CHARGE_PRESENT_LOW = 2;
    public static final int CHARGE_PRESENT_HIGH = 3;
    public static final int CHARGE_PRESENT_FULL = 4;

    public static final BatteryBoxBlock INSTANCE = new BatteryBoxBlock();

    private BatteryBoxBlock() {
        this(Properties.of().mapColor(MapColor.METAL).strength(2.0F, 120.0F).sound(SoundType.STONE));
    }

    private BatteryBoxBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.getStateDefinition().any().setValue(CHARGE, CHARGE_EMPTY));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> text, TooltipFlag tooltipFlag) {
        text.add(Component.translatable("block.batterybox.battery_box.description").withStyle(ChatFormatting.GRAY));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BatteryBoxBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        ItemStack heldStack = player.getItemInHand(hand);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof BatteryBoxBlockEntity) {
            return ((BatteryBoxBlockEntity) blockEntity).use(player, hand, heldStack);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CHARGE);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, BatteryBoxBlockEntity.TYPE, BatteryBoxBlockEntity::tick);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return Math.max(1, state.getValue(CHARGE) * 3); // max is 12
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean flags) {
        if(!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof BatteryBoxBlockEntity) {
                ((BatteryBoxBlockEntity) be).dropContents();
            }
        }
        super.onRemove(state, level, pos, newState, flags);
    }
}
