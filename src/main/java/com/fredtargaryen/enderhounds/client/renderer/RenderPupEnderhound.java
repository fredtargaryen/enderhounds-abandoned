package com.fredtargaryen.enderhounds.client.renderer;

import com.fredtargaryen.enderhounds.DataReference;
import com.fredtargaryen.enderhounds.client.model.PupEnderhoundModel;
import com.fredtargaryen.enderhounds.entity.PupEnderhoundEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderPupEnderhound extends MobRenderer<PupEnderhoundEntity, PupEnderhoundModel> {
    private static final ResourceLocation texture = new ResourceLocation(DataReference.MODID,"textures/models/pup.png");

    private Random rnd = new Random();

    //TODO Correct float?
    public RenderPupEnderhound(EntityRendererManager erm, PupEnderhoundModel model) { super(erm, model, 0.5F); }

    /**
     * Actually renders the *Enderhound*
     */
    public void doRender(PupEnderhoundEntity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
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
    protected ResourceLocation getEntityTexture(PupEnderhoundEntity hound) {
        return texture;
    }
}
