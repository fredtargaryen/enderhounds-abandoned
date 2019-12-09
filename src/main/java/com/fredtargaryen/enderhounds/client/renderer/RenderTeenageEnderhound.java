package com.fredtargaryen.enderhounds.client.renderer;

import com.fredtargaryen.enderhounds.DataReference;
import com.fredtargaryen.enderhounds.client.model.PupEnderhoundModel;
import com.fredtargaryen.enderhounds.client.model.TeenageEnderhoundModel;
import com.fredtargaryen.enderhounds.entity.PupEnderhoundEntity;
import com.fredtargaryen.enderhounds.entity.TeenageEnderhoundEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderTeenageEnderhound extends MobRenderer<TeenageEnderhoundEntity, TeenageEnderhoundModel> {
    private static final ResourceLocation texture = new ResourceLocation(DataReference.MODID,"textures/models/pup.png");

    private Random rnd = new Random();

    //TODO Correct float?
    public RenderTeenageEnderhound(EntityRendererManager erm, TeenageEnderhoundModel model) { super(erm, model, 0.5F); }

    /**
     * Actually renders the *Enderhound*
     */
    public void doRender(TeenageEnderhoundEntity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
        if (entity.isShaking()) {
            double d3 = 0.02D;
            x += this.rnd.nextGaussian() * d3;
            z += this.rnd.nextGaussian() * d3;
        }
        super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(TeenageEnderhoundEntity hound) {
        return texture;
    }
}
