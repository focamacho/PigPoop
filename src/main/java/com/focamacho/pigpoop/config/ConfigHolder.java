package com.focamacho.pigpoop.config;

import com.focamacho.sealconfig.SealConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;

public class ConfigHolder {

    private static final SealConfig sealConfig = new SealConfig();
    public static PigPoopConfig config;

    public static int minPoopTime;
    public static int maxPoopTime;
    public static int poopMeal;
    public static int goldenPoopMeal;
    public static boolean infiniteGoldenPoop;

    public static void initConfig() {
        config = sealConfig.getConfig(new File(FabricLoader.getInstance().getConfigDir().toFile(), "pigpoop.json"), PigPoopConfig.class);

        minPoopTime = config.minPoopTime;
        maxPoopTime = config.maxPoopTime;
        poopMeal = config.poopMeal;
        goldenPoopMeal = config.goldenPoopMeal;
        infiniteGoldenPoop = config.infiniteGoldenPoop;
    }

}
