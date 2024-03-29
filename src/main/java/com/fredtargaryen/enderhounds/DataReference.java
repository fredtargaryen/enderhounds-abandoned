package com.fredtargaryen.enderhounds;

import net.minecraft.util.ResourceLocation;

/**
 * When updating, change version number in mods.toml and build.gradle.
 */
public class DataReference {
    public static final String MODNAME = "Enderhounds";
    public static final String MODID = "enderhounds";

    public static final double HERDRANGEXZ = 10;
    public static final double HERDRANGEY = 10;
    public static final double HERDRANGEMAXDIST = Math.sqrt(HERDRANGEXZ * HERDRANGEXZ + HERDRANGEY * HERDRANGEY);
    public static final double HERDRANGEFOLLOWDIST = HERDRANGEMAXDIST / 2;

    public static final ResourceLocation LEAD_CAP_LOCATION = new ResourceLocation(DataReference.MODID, "ileadpackcapability");
}
