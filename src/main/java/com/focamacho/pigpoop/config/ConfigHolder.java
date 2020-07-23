package com.focamacho.pigpoop.config;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

public class ConfigHolder {

    public static PigPoopConfig config;

    public static int minPoopTime;
    public static int maxPoopTime;
    public static int poopMeal;
    public static int goldenPoopMeal;
    public static boolean infiniteGoldenPoop;

    public static void initConfig() {
        config = AutoConfig.getConfigHolder(PigPoopConfig.class).getConfig();

        minPoopTime = config.minPoopTime;
        maxPoopTime = config.maxPoopTime;
        poopMeal = config.poopMeal;
        goldenPoopMeal = config.goldenPoopMeal;
        infiniteGoldenPoop = config.infiniteGoldenPoop;
    }

}
