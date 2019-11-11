package com.fredtargaryen.enderhounds.client.model;

import net.minecraft.client.renderer.entity.model.RendererModel;

/**
 * Contains the position and rotation of certain RendererModels, if they should change depending on whether the
 * Enderhound is attacking or not.
 */
public class RendererModelTransform {
    private float[] calmPosition;
    private float[] calmRotation;
    private float[] angryPosition;
    private float[] angryRotation;

    public RendererModelTransform(float[] calmPosition, float[] calmRotation, float[] angryPosition, float[] angryRotation) {
        this.calmPosition = calmPosition;
        this.calmRotation = calmRotation;
        this.angryPosition = angryPosition;
        this.angryRotation = angryRotation;
    }

    public static void setCalmTransform(RendererModel model, RendererModelTransform rmt) {
        model.setRotationPoint(rmt.calmPosition[0], rmt.calmPosition[1], rmt.calmPosition[2]);
        model.rotateAngleX = rmt.calmRotation[0];
        model.rotateAngleY = rmt.calmRotation[1];
        model.rotateAngleZ = rmt.calmRotation[2];
    }

    public static void setAngryTransform(RendererModel model, RendererModelTransform rmt) {
        model.setRotationPoint(rmt.angryPosition[0], rmt.angryPosition[1], rmt.angryPosition[2]);
        model.rotateAngleX = rmt.angryRotation[0];
        model.rotateAngleY = rmt.angryRotation[1];
        model.rotateAngleZ = rmt.angryRotation[2];
    }
}
