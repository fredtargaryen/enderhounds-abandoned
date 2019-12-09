package com.fredtargaryen.enderhounds.client.renderer;

import com.fredtargaryen.enderhounds.client.model.TeenageEnderhoundModel;
import com.fredtargaryen.enderhounds.entity.TeenageEnderhoundEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTeenageEnderhoundFactory implements IRenderFactory<TeenageEnderhoundEntity> {
    @Override
    public EntityRenderer<? super TeenageEnderhoundEntity> createRenderFor(EntityRendererManager erm) {
        return new RenderTeenageEnderhound(erm, new TeenageEnderhoundModel());
    }
}
