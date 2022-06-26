package com.focamacho.pigpoop.api;

import net.minecraft.item.Item;

public interface IKnowHowToPoop {

    /**
     * Returns the remaining poop time
     * from an entity.
     * @return the remaining poop time, in ticks.
     */
    int getPoopTime();

    /**
     * Returns what food the entity
     * has eaten.
     * @return the item the entity has eaten,
     * or null if no item was eaten.
     */
    Item getFoodItem();

    /**
     * Set the item the entity
     * has eaten.
     * @param item the item to set
     *             as eaten.
     */
    void setFoodItem(Item item);

}