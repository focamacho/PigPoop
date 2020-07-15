package com.focamacho.pigpoop;

import com.focamacho.pigpoop.config.ConfigHolder;
import com.focamacho.pigpoop.config.PigPoopConfig;
import com.focamacho.pigpoop.item.GoldenPoopItem;
import com.focamacho.pigpoop.item.PoopItem;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PigPoop implements ModInitializer {

	public static final Item poop = new PoopItem(new Item.Settings().group(ItemGroup.MISC));
	public static final Item golden_poop = new GoldenPoopItem(new Item.Settings().group(ItemGroup.MISC));

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("pigpoop", "poop"), poop);
		Registry.register(Registry.ITEM, new Identifier("pigpoop", "golden_poop"), golden_poop);

		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(poop, 1.0F);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(golden_poop, 1.0F);

		AutoConfig.register(PigPoopConfig.class, JanksonConfigSerializer::new);
		ConfigHolder.initConfig();
	}

}
