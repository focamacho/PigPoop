package com.focamacho.pigpoop;

import com.focamacho.pigpoop.config.ConfigHolder;
import com.focamacho.pigpoop.item.GoldenPoopItem;
import com.focamacho.pigpoop.item.PoopItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PigPoop implements ModInitializer {

	public static Item poop;
	public static Item golden_poop;

	public static final ItemGroup creativeTab = FabricItemGroupBuilder.create(new Identifier("pigpoop")).icon(() -> new ItemStack(poop)).build();

	@Override
	public void onInitialize() {
		poop = new PoopItem(new Item.Settings().group(creativeTab));
		golden_poop = new GoldenPoopItem(new Item.Settings().group(creativeTab));

		Registry.register(Registry.ITEM, new Identifier("pigpoop", "poop"), poop);
		Registry.register(Registry.ITEM, new Identifier("pigpoop", "golden_poop"), golden_poop);

		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(poop, 1.0F);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(golden_poop, 1.0F);

		ConfigHolder.initConfig();
	}

}
