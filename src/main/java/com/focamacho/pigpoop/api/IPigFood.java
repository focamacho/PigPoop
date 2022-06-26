package com.focamacho.pigpoop.api;

import net.minecraft.item.Item;

public interface IPigFood {

    /**
     * The item the entity will
     * poop after eating the food.
     * @return the item to poop.
     */
    Item getPoopItem();

    /**
     * The minimum time the entity will have
     * to poop after eating the item.
     * @return the minimum time, in ticks, to poop.
     */
    int getPoopMinTime();

    /**
     * The maximum time the entity will have
     * to poop after eating the item.
     * @return the minimum time, in ticks, to poop.
     */
    int getPoopMaxTime();

    /**
     * If the food will make the pig produce
     * the custom poop forever, or only
     * one time.
     * @return true if the pig will always produce
     * the poop defined by the food.
     */
    boolean isPoopInfinite();

}
