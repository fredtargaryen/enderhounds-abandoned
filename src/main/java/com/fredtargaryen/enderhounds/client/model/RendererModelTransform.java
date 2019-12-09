package com.fredtargaryen.enderhounds.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Contains the position and rotation of certain RendererModels, if they should change depending on whether the
 * Enderhound is attacking or not.
 */
public class RendererModelTransform {
    private float posx;
    private float posy;
    private float posz;
    private float rotx;
    private float roty;
    private float rotz;

    public RendererModelTransform(float x, float y, float z, float rx, float ry, float rz) {
        this.posx = x;
        this.posy = y;
        this.posz = z;
        this.rotx = rx;
        this.roty = ry;
        this.rotz = rz;
    }

    public void applyTo(RendererModel model) {
        model.rotationPointX = this.posx;
        model.rotationPointY = this.posy;
        model.rotationPointZ = this.posz;
        model.rotateAngleX = this.rotx;
        model.rotateAngleY = this.roty;
        model.rotateAngleZ = this.rotz;
    }

    public static void applyTransformsInHashMap(HashMap<RendererModel, RendererModelTransform> transforms) {
        Iterator<RendererModel> i = transforms.keySet().iterator();
        while(i.hasNext()) {
            RendererModel rm = i.next();
            transforms.get(rm).applyTo(rm);
        }
    }
}
