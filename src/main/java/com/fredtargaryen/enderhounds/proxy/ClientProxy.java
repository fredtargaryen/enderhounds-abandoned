package com.fredtargaryen.enderhounds.proxy;

import com.fredtargaryen.enderhounds.client.renderer.entity.RenderEnderhound;
import com.fredtargaryen.enderhounds.client.renderer.entity.RenderPupFactory;
import com.fredtargaryen.enderhounds.entity.EntityEnderhound;
import com.fredtargaryen.enderhounds.entity.EntityEnderhoundPup;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy
{
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderhoundPup.class, new RenderPupFactory());
    }

    public void registerModels()
    {
        ItemModelMesher m = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        //TODO
        //m.register(EnderhoundsBase.armour, 0, new ModelResourceLocation(DataReference.MODID + ":" + EnderhoundsBase.armour.getName(), "inventory"));
    }
}
