package de.canitzp.batterybox;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BatteryBoxBlockEntity extends BlockEntity {

    public static final BlockEntityType<BatteryBoxBlockEntity> TYPE = BlockEntityType.Builder.of(BatteryBoxBlockEntity::new, BatteryBoxBlock.INSTANCE).build(null);

    private final IEnergyStorage energyStorage = new IEnergyStorage() {
        private @Nullable IEnergyStorage getLocalStorage(){
            return BatteryBoxBlockEntity.this.getStack().getCapability(Capabilities.EnergyStorage.ITEM);
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            IEnergyStorage storage = this.getLocalStorage();
            return storage == null ? 0 : storage.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            IEnergyStorage storage = this.getLocalStorage();
            return storage == null ? 0 : storage.extractEnergy(maxExtract, simulate);
        }

        @Override
        public int getEnergyStored() {
            IEnergyStorage storage = this.getLocalStorage();
            return storage == null ? 0 : storage.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            IEnergyStorage storage = this.getLocalStorage();
            return storage == null ? 0 : storage.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            IEnergyStorage storage = this.getLocalStorage();
            return storage != null && storage.canExtract();
        }

        @Override
        public boolean canReceive() {
            IEnergyStorage storage = this.getLocalStorage();
            return storage != null && storage.canReceive();
        }
    };
    private final NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
    private final ItemStackHandler stackHandler = new ItemStackHandler(this.stacks){
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return stack.getCapability(Capabilities.EnergyStorage.ITEM) != null;
        }

        @Override
        protected void onContentsChanged(int slot) {
            BatteryBoxBlockEntity.this.setChanged();
        }
    };

    public BatteryBoxBlockEntity(BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }

    public ItemStack getStack(){
        return this.stacks.get(0);
    }

    public void setStack(ItemStack stack){
        this.stackHandler.setStackInSlot(0, stack);
    }

    public IItemHandler getItemHandler(Direction side){
        return this.stackHandler;
    }

    public IEnergyStorage getEnergyStorage(Direction side) {
        return this.energyStorage;
    }

    public InteractionResult use(Player player, InteractionHand hand, ItemStack heldStack){
        if(!this.getStack().isEmpty()){
            if(player.isShiftKeyDown()){
                if(heldStack.isEmpty()){
                    player.setItemInHand(hand, this.getStack());
                    this.setStack(ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                } else {
                    if (player.addItem(this.getStack())) {
                        this.setStack(ItemStack.EMPTY);
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                player.displayClientMessage(this.composeMessage(), true);
                if(heldStack.getCapability(Capabilities.EnergyStorage.ITEM) != null){
                    // Case if you right click with a battery, to charge the one in your hand up
                    return InteractionResult.PASS;
                }
            }
        } else if(!heldStack.isEmpty()) {
            ItemStack remainingStack = this.stackHandler.insertItem(0, heldStack, false);
            if(!remainingStack.equals(heldStack)){
                player.setItemInHand(hand, remainingStack);
                return InteractionResult.SUCCESS;
            }
        } else {
            player.displayClientMessage(this.composeMessage(), true);
        }
        return InteractionResult.PASS;
    }

    private Component composeMessage(){
        IEnergyStorage storage = this.getEnergyStorage(null);
        if(storage == null){
            return Component.translatable("block.batterybox.battery_box.empty");
        }
        float stateOfChargePercent = storage.getEnergyStored() / (1F * storage.getMaxEnergyStored()) * 100;

        Component stackName = this.getStack().getHoverName();

        MutableComponent c = Component.empty();
        c.append(Component.empty().append(stackName).withStyle(ChatFormatting.LIGHT_PURPLE));
        c.append(": ");
        c.append(Component.literal(String.format("%d/%d", storage.getEnergyStored(), storage.getMaxEnergyStored())));
        c.append(Component.literal(String.format(" (%.2f%%)", stateOfChargePercent)));

        if(storage.getEnergyStored() > 0 && !storage.canExtract()){
            c.append(Component.literal(" "));
            c.append(Component.translatable("block.batterybox.battery_box.charging_only").withStyle(ChatFormatting.RED));
        }

        return c;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if(tag.contains("stack", Tag.TAG_COMPOUND)){
            this.stacks.set(0, ItemStack.of(tag.getCompound("stack")));
        } else {
            this.stacks.set(0, ItemStack.EMPTY);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        CompoundTag stackTag = new CompoundTag();
        this.getStack().save(stackTag);
        tag.put("stack", stackTag);
    }

    public void dropContents(){
        Containers.dropContents(this.level, this.getBlockPos(), this.stacks);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BatteryBoxBlockEntity be) {
        if(level == null || level.isClientSide){
            return;
        }
        if(!be.getStack().isEmpty()){
            IEnergyStorage storage = be.getEnergyStorage(null);
            if(storage != null){
                if(storage.getEnergyStored() > 0){
                    Map<Direction, BatteryBoxBlockEntity> otherBatteries = new HashMap<>();
                    for (Direction direction : Direction.values()) {
                        BlockEntity otherBlockEntity = level.getBlockEntity(pos.relative(direction, 1));
                        if(otherBlockEntity != null){
                            // put other batteries on special list, so they are not prioritized
                            if(otherBlockEntity instanceof BatteryBoxBlockEntity){
                                otherBatteries.put(direction.getOpposite(), (BatteryBoxBlockEntity) otherBlockEntity);
                            } else {
                                BatteryBoxBlockEntity.transferEnergy(storage, otherBlockEntity, direction.getOpposite());
                                if(storage.getEnergyStored() <= 0){
                                    break;
                                }
                            }
                        }
                    }
                    if(!otherBatteries.isEmpty() && storage.getEnergyStored() > 0){
                        float stateOfCharge = storage.getEnergyStored() / (1F * storage.getMaxEnergyStored());
                        for (Map.Entry<Direction, BatteryBoxBlockEntity> entry : otherBatteries.entrySet()) {
                            IEnergyStorage otherStorage = entry.getValue().getEnergyStorage(null);
                            if (otherStorage != null && otherStorage.getEnergyStored() < otherStorage.getMaxEnergyStored()) {
                                float otherStateOfCharge = otherStorage.getEnergyStored() / (1F * otherStorage.getMaxEnergyStored());
                                if(stateOfCharge > otherStateOfCharge){
                                    float stateOfChargeDifference = stateOfCharge - otherStateOfCharge;
                                    int energyToTransfer = (int)((otherStorage.getMaxEnergyStored() * stateOfChargeDifference) / 2F); // cast to int is intentional
                                    int energyThatCanBeExtracted = storage.extractEnergy(energyToTransfer, true);
                                    if(energyThatCanBeExtracted > 0){
                                        int energyThatWasReceived = otherStorage.receiveEnergy(energyThatCanBeExtracted, false);
                                        storage.extractEnergy(energyThatWasReceived, false);
                                        be.setChanged();
                                    }
                                }
                            }
                            if(storage.getEnergyStored() <= 0){
                                break;
                            }
                        }
                    }
                }
                be.updateState(-1);
            } else {
                be.updateState(BatteryBoxBlock.CHARGE_PRESENT_EMPTY);
            }
        } else {
            be.updateState(BatteryBoxBlock.CHARGE_EMPTY);
        }
    }

    private static void transferEnergy(IEnergyStorage storage, BlockEntity otherBlockEntity, Direction side){
        IEnergyStorage otherStorage = otherBlockEntity.getLevel().getCapability(Capabilities.EnergyStorage.BLOCK, otherBlockEntity.getBlockPos(), side);
        if (otherStorage != null) {
            storage.extractEnergy(otherStorage.receiveEnergy(storage.extractEnergy(storage.getMaxEnergyStored(), true), false), false);
        }
    }

    private void updateState(int chargeLevelOrNegative) {
        if (chargeLevelOrNegative < 0) {
            chargeLevelOrNegative = this.getStateForCharge();
        }
        if (this.getBlockState().getValue(BatteryBoxBlock.CHARGE) != chargeLevelOrNegative) {
            this.level.setBlock(this.getBlockPos(), this.getBlockState().setValue(BatteryBoxBlock.CHARGE, chargeLevelOrNegative), 3);
            this.setChanged();
        }
    }

    private int getStateForCharge(){
        if(this.getStack().isEmpty()){
            return BatteryBoxBlock.CHARGE_EMPTY;
        }
        int stateOfCharge = Math.round((this.energyStorage.getEnergyStored() / (this.energyStorage.getMaxEnergyStored() * 1F)) * 100);
        if(stateOfCharge <= 0){
            return BatteryBoxBlock.CHARGE_PRESENT_EMPTY;
        }
        if(stateOfCharge <= 45){
            return BatteryBoxBlock.CHARGE_PRESENT_LOW;
        }
        if(stateOfCharge <= 90){
            return BatteryBoxBlock.CHARGE_PRESENT_HIGH;
        }
        return BatteryBoxBlock.CHARGE_PRESENT_FULL;
    }

}
