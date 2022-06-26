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

public class GoldenPoopItem extends PoopItem {

    private final FoodComponent food = new FoodComponent.Builder().alwaysEdible().hunger(2).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100),1.0F).build();

    public GoldenPoopItem(Settings settings) {
        super(settings);
    }

    @Override
    public FoodComponent getFoodComponent() {
        return this.food;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return applyPoop(context, ConfigHolder.goldenPoopMeal);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        appendSuperTooltip(stack, world, tooltip, context);
        if(ConfigHolder.goldenPoopMeal > 1) tooltip.add(new LiteralText(new TranslatableText("pigpoop.tooltip.golden_poop").getString().replace("%timesBetter%", Integer.toString(ConfigHolder.goldenPoopMeal))));
    }
}
