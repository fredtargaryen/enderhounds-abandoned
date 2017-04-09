package com.fredtargaryen.enderhounds.client.renderer.entity;

import com.fredtargaryen.enderhounds.DataReference;
import com.fredtargaryen.enderhounds.client.model.entity.ModelEnderhoundPup;
import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderEnderhound extends RenderLiving<EntityEnderhound>
{
    private ResourceLocation[] textures = {new ResourceLocation(DataReference.MODID,"textures/models/pup.png")};
                                            //new ResourceLocation(DataReference.MODID+":textures/entity/teenage.png"),
                                            //new ResourceLocation(DataReference.MODID+":textures/entity/mature.png")},
                                            //new ResourceLocation(DataReference.MODID+":textures/entity/elderly.png")};

    private static ModelBase[] models = {new ModelEnderhoundPup()};//, new ModelEnderhoundTeenage(), new ModelEnderhoundMature(), new ModelEnderhoundElderly()};
    private Random rnd = new Random();

    public RenderEnderhound(RenderManager p_i46153_1_, EntityEnderhound.GrowthStage stage, float p_i46153_3)
    {
        super(p_i46153_1_, models[stage.ordinal()], p_i46153_3);
    }

    /**
     * Actually renders the *Enderhound*
     */
    public void doRender(EntityEnderhound entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        if (entity.isShaking())
        {
            double d3 = 0.02D;
            x += this.rnd.nextGaussian() * d3;
            z += this.rnd.nextGaussian() * d3;
        }
        super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityEnderhound hound)
    {
        return textures[hound.stage.ordinal()];
    }
}
