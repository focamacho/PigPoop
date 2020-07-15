package com.focamacho.pigpoop.mixin;

import com.focamacho.pigpoop.PigPoop;
import com.focamacho.pigpoop.api.IKnowHowToPoop;
import com.focamacho.pigpoop.config.ConfigHolder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity implements IKnowHowToPoop, ItemSteerable, Saddleable {

    private int poopTime;
    private Item poopItem;

    protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void setPoopItem(Item poopItem) {
        this.poopItem = poopItem;
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/World;)V", at = @At("RETURN"))
    public void onInit(CallbackInfo callbackReference) {
        this.poopTime = this.random.nextInt(ConfigHolder.maxPoopTime - ConfigHolder.minPoopTime) + ConfigHolder.minPoopTime;
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        if (!this.world.isClient && this.isAlive() && !this.isBaby() && --this.poopTime <= 0) {
            this.playSound(SoundEvents.BLOCK_HONEY_BLOCK_BREAK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            if(poopItem == null || poopItem == Items.AIR) this.dropItem(PigPoop.poop);
            else {
                this.dropItem(poopItem);
                if(!ConfigHolder.infiniteGoldenPoop) this.poopItem = Items.AIR;
            }
            this.poopTime = this.random.nextInt(ConfigHolder.maxPoopTime - ConfigHolder.minPoopTime) + ConfigHolder.minPoopTime;
        }
    }

    @Inject(method = "readCustomDataFromTag", at = @At("HEAD"))
    public void readCustomDataFromTag(CompoundTag tag, CallbackInfo callbackInfo) {
        this.poopItem = ItemStack.fromTag(tag.getCompound("poopItem")).getItem();
    }

    @Inject(method = "writeCustomDataToTag", at = @At("HEAD"))
    public void writeCustomDataToTag(CompoundTag tag, CallbackInfo callbackInfo) {
        CompoundTag cTag = new CompoundTag();
        tag.put("poopItem", new ItemStack(poopItem).toTag(cTag));
    }

    @Override
    public Item getPoopItem() {
        return poopItem == null || poopItem.equals(Items.AIR) ? PigPoop.poop : poopItem;
    }

    @Override
    public int getPoopTime() {
        return poopTime;
    }
}
