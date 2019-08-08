package com.fredtargaryen.enderhounds.proxy;

import com.fredtargaryen.enderhounds.client.renderer.RenderPupEnderhoundFactory;
import com.fredtargaryen.enderhounds.entity.PupEnderhoundEntity;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {
    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(PupEnderhoundEntity.class, new RenderPupEnderhoundFactory());
    }
}
