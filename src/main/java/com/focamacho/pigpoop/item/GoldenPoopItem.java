package com.focamacho.pigpoop.item;

import com.focamacho.pigpoop.config.ConfigHolder;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

import java.util.List;

public class GoldenPoopItem extends Item {

    private final FoodComponent food = new FoodComponent.Builder().alwaysEdible().hunger(2).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100),1.0F).build();

    public GoldenPoopItem(Settings settings) {
        super(settings);
    }

    @Override
    public FoodComponent getFoodComponent() {
        return food;
    }

    @Override
    public boolean isFood() {
        return true;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(BoneMealItem.useOnFertilizable(context.getStack(), context.getWorld(), context.getBlockPos())) {
            int maxTimes = ConfigHolder.goldenPoopMeal;

            while(maxTimes > 1) {
                --maxTimes;
                BoneMealItem.useOnFertilizable(new ItemStack(Items.BONE_MEAL), context.getWorld(), context.getBlockPos());
            }

            if(context.getWorld().isClient) BoneMealItem.createParticles(context.getWorld(), context.getBlockPos(), 10);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if(ConfigHolder.goldenPoopMeal > 1) tooltip.add(new LiteralText(new TranslatableText("pigpoop.tooltip.golden_poop").getString().replace("%timesBetter%", Integer.toString(ConfigHolder.goldenPoopMeal))));
    }
}
