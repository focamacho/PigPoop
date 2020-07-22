package com.focamacho.pigpoop.mixin;

import com.focamacho.pigpoop.PigPoop;
import com.focamacho.pigpoop.api.IKnowHowToPoop;
import com.focamacho.pigpoop.api.IPigFood;
import com.focamacho.pigpoop.config.ConfigHolder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity implements IKnowHowToPoop, ItemSteerable, Saddleable {

    private int poopTime;
    private Item foodItem;
    private int minPoopTime;
    private int maxPoopTime;

    protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void setFoodItem(Item poopItem) {
        this.foodItem = poopItem;
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("RETURN"))
    public void onInit(CallbackInfo callbackReference) {
        if(foodItem == null) {
            minPoopTime = ConfigHolder.minPoopTime;
            maxPoopTime = ConfigHolder.maxPoopTime;
        } else {
            minPoopTime = ((IPigFood)foodItem).getPoopMinTime();
            maxPoopTime = ((IPigFood)foodItem).getPoopMaxTime();
        }
        resetPoopTime();
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        if (!this.world.isClient && this.isAlive() && !this.isBaby() && --this.poopTime <= 0) {
            this.playSound(SoundEvents.BLOCK_HONEY_BLOCK_BREAK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            if(foodItem == null) this.dropItem(PigPoop.poop);
            else if(foodItem == Items.GOLDEN_CARROT) {
                this.dropItem(PigPoop.golden_poop);
                if(!ConfigHolder.infiniteGoldenPoop) this.foodItem = null;
            }
            else {
                this.dropItem(((IPigFood)foodItem).getPoopItem());
                if(!((IPigFood)foodItem).isPoopInfinite()) this.foodItem = null;
            }
            resetPoopTime();
        }
    }

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo callbackInfo) {
        this.foodItem = ItemStack.fromTag(tag.getCompound("foodItem")).getItem();
        if(this.foodItem == Items.AIR) this.foodItem = null;
    }

    @Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo callbackInfo) {
        CompoundTag cTag = new CompoundTag();
        tag.put("foodItem", new ItemStack(foodItem).toTag(cTag));
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        if(canEat() && getFoodItem() == null) {
            if (player.getStackInHand(hand).getItem().equals(Items.GOLDEN_CARROT)) {
                setFoodItem(Items.GOLDEN_CARROT);
                minPoopTime = ConfigHolder.minPoopTime;
                maxPoopTime = ConfigHolder.maxPoopTime;
                player.getStackInHand(hand).decrement(1);
                lovePlayer(player);
                resetPoopTime();
                info.setReturnValue(ActionResult.CONSUME);
            } else if (player.getStackInHand(hand).getItem() instanceof IPigFood) {
                IPigFood food = (IPigFood) player.getStackInHand(hand).getItem();
                setFoodItem(player.getStackInHand(hand).getItem());
                minPoopTime = food.getPoopMinTime();
                maxPoopTime = food.getPoopMaxTime();
                player.getStackInHand(hand).decrement(1);
                lovePlayer(player);
                resetPoopTime();
                info.setReturnValue(ActionResult.CONSUME);
            }
        }
    }

    private void resetPoopTime() {
        this.poopTime = this.random.nextInt(maxPoopTime - minPoopTime) + minPoopTime;
    }

    @Override
    public Item getFoodItem() {
        return foodItem;
    }

    @Override
    public int getPoopTime() {
        return poopTime;
    }

}
