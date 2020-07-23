package com.focamacho.pigpoop.api;

import net.minecraft.item.Item;

public interface IPigFood {

    Item getPoopItem();
    int getPoopMinTime();
    int getPoopMaxTime();
    boolean isPoopInfinite();
    void setPoopItem();

}
