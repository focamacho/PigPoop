package com.focamacho.pigpoop.config;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ConfigHolder {

    public static PigPoopConfig config;

    public static int minPoopTime;
    public static int maxPoopTime;
    public static int poopMeal = 2;
    public static int goldenPoopMeal = 20;
    public static boolean infiniteGoldenPoop = false;

    public static void initConfig() {
        config = AutoConfig.getConfigHolder(PigPoopConfig.class).getConfig();

        minPoopTime = config.minPoopTime;
        maxPoopTime = config.maxPoopTime;
        poopMeal = config.poopMeal;
        goldenPoopMeal = config.goldenPoopMeal;
        infiniteGoldenPoop = config.infiniteGoldenPoop;
    }

}
