package com.fredtargaryen.enderhounds;

import net.minecraft.util.ResourceLocation;

/**
 * When updating, change version number in here, mcmod.info and build.gradle.
 */
public class DataReference
{
    public static final String MODNAME = "Enderhounds";
    public static final String MODID = "ftenderhounds";
    public static final String VERSION = "0.1";

    public static final String CLIENTPROXYPATH = "com.fredtargaryen.enderhounds.proxy.ClientProxy";
    public static final String SERVERPROXYPATH = "com.fredtargaryen.enderhounds.proxy.ServerProxy";

    public static final double HERDRANGEXZ = 10;
    public static final double HERDRANGEY = 10;
    public static final double HERDRANGEMAXDIST = Math.sqrt(HERDRANGEXZ * HERDRANGEXZ + HERDRANGEY * HERDRANGEY);
    public static final double HERDRANGEFOLLOWDIST = HERDRANGEMAXDIST / 2;

    public static final ResourceLocation LEAD_CAP_LOCATION = new ResourceLocation(DataReference.MODID, "ILeadPackCapability");
}
