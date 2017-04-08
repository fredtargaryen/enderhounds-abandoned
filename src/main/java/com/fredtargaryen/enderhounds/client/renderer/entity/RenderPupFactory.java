package com.fredtargaryen.enderhounds.client.renderer.entity;

import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import com.fredtargaryen.enderhounds.entity.EntityEnderhoundPup;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderPupFactory implements IRenderFactory<EntityEnderhoundPup>
{
    @Override
    public Render<? super EntityEnderhoundPup> createRenderFor(RenderManager rm)
    {
        return new RenderEnderhound(rm, EntityEnderhound.GrowthStage.PUP, 0.5F);
    }
}
