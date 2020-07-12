package com.focamacho.pigpoop.mixin;

import com.focamacho.pigpoop.item.GoldenPoopItem;
import com.focamacho.pigpoop.item.PoopItem;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getStack();

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        if(this.getStack().getItem() instanceof PoopItem || this.getStack().getItem() instanceof GoldenPoopItem) {
            Entity entity = ((Entity)(Object) this);
            if(entity.age >= 5800) {
                BlockPos pos = entity.world.getBlockState(entity.getBlockPos()) instanceof Fertilizable ? entity.getBlockPos() : entity.getBlockPos().down();
                if(BoneMealItem.useOnFertilizable(this.getStack(), entity.world, pos)) BoneMealItem.createParticles(entity.world, pos, 5);
                entity.remove();
            }
        }
    }

}
