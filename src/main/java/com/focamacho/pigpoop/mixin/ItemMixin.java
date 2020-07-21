package com.focamacho.pigpoop.mixin;

import com.focamacho.pigpoop.PigPoop;
import com.focamacho.pigpoop.api.IKnowHowToPoop;
import com.focamacho.pigpoop.config.ConfigHolder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow public abstract Item asItem();

    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    private void useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        if(this.asItem().equals(Items.GOLDEN_CARROT)) {
            if(entity instanceof IKnowHowToPoop) {
                AnimalEntity animal = (AnimalEntity) entity;
                if(animal.canEat() && !((IKnowHowToPoop)entity).getPoopItem().equals(PigPoop.golden_poop)) {
                    ((IKnowHowToPoop)entity).setPoopItem(PigPoop.golden_poop, ConfigHolder.infiniteGoldenPoop);
                    stack.decrement(1);
                    animal.lovePlayer(user);
                    info.setReturnValue(ActionResult.CONSUME);
                }
            }
        }
    }

}
