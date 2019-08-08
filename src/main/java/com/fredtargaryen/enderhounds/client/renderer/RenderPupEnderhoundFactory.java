package com.fredtargaryen.enderhounds.client.renderer;

import com.fredtargaryen.enderhounds.client.model.PupEnderhoundModel;
import com.fredtargaryen.enderhounds.entity.PupEnderhoundEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderPupEnderhoundFactory implements IRenderFactory<PupEnderhoundEntity> {
    @Override
    public EntityRenderer<? super PupEnderhoundEntity> createRenderFor(EntityRendererManager erm) {
        return new RenderPupEnderhound(erm, new PupEnderhoundModel());
    }
}
