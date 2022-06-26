package com.focamacho.pigpoop.config;

import com.focamacho.sealconfig.relocated.blue.endless.jankson.Comment;

public class PigPoopConfig {

    @Comment(value = "The minimum and maximum time a pig can produce poop(in ticks)")
    int minPoopTime = 6000;
    int maxPoopTime = 12000;

    @Comment(value = "How many times is a normal poop better than a bone meal?")
    int poopMeal = 2;

    @Comment(value = "How many times is a golden poop better than a bone meal?")
    int goldenPoopMeal = 20;

    @Comment(value = "Infinite golden poop (The pig will always produce golden poop after being fed with golden carrot instead of just one)")
    boolean infiniteGoldenPoop = false;

}
