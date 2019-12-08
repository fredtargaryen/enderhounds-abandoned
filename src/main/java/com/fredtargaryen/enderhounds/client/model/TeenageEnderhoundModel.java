package com.fredtargaryen.enderhounds.client.model;

import com.fredtargaryen.enderhounds.entity.TeenageEnderhoundEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

/**
 * TeenageEnderhoundModel - FredTargaryen
 * Created using Tabula 7.0.1
 */
public class TeenageEnderhoundModel extends EntityModel<TeenageEnderhoundEntity> {
    public RendererModel Body1;
    public RendererModel Body2;
    public RendererModel Body3;
    public RendererModel Leg_L_F1;
    public RendererModel Leg_R_F1;
    public RendererModel Leg_L_B1;
    public RendererModel Leg_R_B1;
    public RendererModel Snout;
    public RendererModel Ear_L;
    public RendererModel Ear_R;
    public RendererModel Jaw;
    public RendererModel Jaw_Membrane;
    public RendererModel Tail1;
    public RendererModel Tail2;
    public RendererModel Tail3;
    public RendererModel Head;
    public RendererModel Leg_L_F2;
    public RendererModel Leg_R_F2;
    public RendererModel Leg_L_B2;
    public RendererModel Leg_R_B2;
    public RendererModel Tooth1;
    public RendererModel Tooth2;
    public RendererModel Tooth3;
    public RendererModel Tooth4;
    public RendererModel Tooth5;
    public RendererModel Tooth6;
    public RendererModel Tooth7;
    public RendererModel Tooth8;
    public RendererModel Tooth9;
    public RendererModel Tooth10;
    public RendererModel Tooth11;
    public RendererModel Tooth12;
    public RendererModel Tooth13;
    public RendererModel Tooth14;
    public RendererModel Tooth15;
    public RendererModel Tooth16;

    public TeenageEnderhoundModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.Body1 = new RendererModel(this, 0, 13);
        this.Body1.setRotationPoint(0.0F, 10.0F, -5.0F);
        this.Body1.addBox(-3.5F, -3.5F, -2.5F, 7, 7, 5, 0.0F);
        this.Tooth8 = new RendererModel(this, 24, 61);
        this.Tooth8.setRotationPoint(-3.0F, 6.0F, -14.0F);
        this.Tooth8.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
        this.Body3 = new RendererModel(this, 0, 37);
        this.Body3.setRotationPoint(0.0F, 10.0F, 5.0F);
        this.Body3.addBox(-3.5F, -3.5F, -2.5F, 7, 7, 5, 0.0F);
        this.Ear_R = new RendererModel(this, 44, 0);
        this.Ear_R.mirror = true;
        this.Ear_R.setRotationPoint(-3.0F, 2.5F, -7.0F);
        this.Ear_R.addBox(0.0F, -7.0F, -3.0F, 1, 7, 3, 0.0F);
        this.Tooth9 = new RendererModel(this, 24, 61);
        this.Tooth9.setRotationPoint(3.0F, 10.0F, -14.0F);
        this.Tooth9.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
        this.Tooth10 = new RendererModel(this, 24, 61);
        this.Tooth10.setRotationPoint(3.0F, 10.0F, -16.0F);
        this.Tooth10.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
        this.Leg_L_F1 = new RendererModel(this, 24, 17);
        this.Leg_L_F1.setRotationPoint(2.7F, 13.4F, -6.0F);
        this.Leg_L_F1.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Leg_L_F1, 0.2617993877991494F, 0.0F, 0.0F);
        this.Tooth4 = new RendererModel(this, 24, 62);
        this.Tooth4.setRotationPoint(1.0F, 6.0F, -17.0F);
        this.Tooth4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 0, 0.0F);
        this.Tooth15 = new RendererModel(this, 24, 61);
        this.Tooth15.setRotationPoint(-3.0F, 10.0F, -15.0F);
        this.Tooth15.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
        this.Leg_R_B1 = new RendererModel(this, 24, 41);
        this.Leg_R_B1.setRotationPoint(-2.7F, 13.4F, 6.1F);
        this.Leg_R_B1.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Leg_R_B1, 0.2617993877991494F, 0.0F, 0.0F);
        this.Tooth16 = new RendererModel(this, 24, 61);
        this.Tooth16.setRotationPoint(-3.0F, 10.0F, -13.0F);
        this.Tooth16.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
        this.Tooth2 = new RendererModel(this, 24, 61);
        this.Tooth2.setRotationPoint(3.0F, 6.0F, -15.0F);
        this.Tooth2.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
        this.Leg_L_B1 = new RendererModel(this, 24, 41);
        this.Leg_L_B1.setRotationPoint(2.7F, 13.4F, 6.1F);
        this.Leg_L_B1.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Leg_L_B1, 0.2617993877991494F, 0.0F, 0.0F);
        this.Tooth11 = new RendererModel(this, 24, 62);
        this.Tooth11.setRotationPoint(2.0F, 10.0F, -17.0F);
        this.Tooth11.addBox(0.0F, 0.0F, 0.0F, 1, 2, 0, 0.0F);
        this.Jaw = new RendererModel(this, 0, 7);
        this.Jaw.setRotationPoint(0.0F, 12.0F, -12.0F);
        this.Jaw.addBox(-3.0F, 0.0F, -5.0F, 6, 1, 5, 0.0F);
        this.Ear_L = new RendererModel(this, 44, 0);
        this.Ear_L.setRotationPoint(3.5F, 2.5F, -7.0F);
        this.Ear_L.addBox(-1.5F, -7.0F, -3.0F, 1, 7, 3, 0.0F);
        this.Tooth1 = new RendererModel(this, 24, 61);
        this.Tooth1.setRotationPoint(3.0F, 6.0F, -13.0F);
        this.Tooth1.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
        this.Tail2 = new RendererModel(this, 8, 52);
        this.Tail2.setRotationPoint(0.0F, 10.6F, 9.2F);
        this.Tail2.addBox(-1.0F, -1.0F, -1.0F, 2, 10, 2, 0.0F);
        this.setRotateAngle(Tail2, 0.39269908169872414F, 0.0F, 0.0F);
        this.Tooth12 = new RendererModel(this, 24, 62);
        this.Tooth12.setRotationPoint(0.0F, 10.0F, -17.0F);
        this.Tooth12.addBox(0.0F, 0.0F, 0.0F, 1, 2, 0, 0.0F);
        this.Tooth13 = new RendererModel(this, 24, 62);
        this.Tooth13.setRotationPoint(-2.0F, 10.0F, -17.0F);
        this.Tooth13.addBox(0.0F, 0.0F, 0.0F, 1, 2, 0, 0.0F);
        this.Tooth6 = new RendererModel(this, 24, 62);
        this.Tooth6.setRotationPoint(-3.0F, 6.0F, -17.0F);
        this.Tooth6.addBox(0.0F, 0.0F, 0.0F, 1, 2, 0, 0.0F);
        this.Leg_R_F1 = new RendererModel(this, 24, 17);
        this.Leg_R_F1.setRotationPoint(-2.7F, 13.4F, -6.0F);
        this.Leg_R_F1.addBox(-1.0F, -1.0F, -1.0F, 2, 6, 2, 0.0F);
        this.setRotateAngle(Leg_R_F1, 0.2617993877991494F, 0.0F, 0.0F);
        this.Tail1 = new RendererModel(this, 0, 58);
        this.Tail1.setRotationPoint(0.0F, 8.0F, 7.0F);
        this.Tail1.addBox(-1.0F, -1.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Tail1, 0.7853981633974483F, 0.0F, 0.0F);
        this.Leg_R_F2 = new RendererModel(this, 32, 16);
        this.Leg_R_F2.setRotationPoint(-2.7F, 18.5F, -5.0F);
        this.Leg_R_F2.addBox(-1.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F);
        this.setRotateAngle(Leg_R_F2, -0.2617993877991494F, 0.0F, 0.0F);
        this.Leg_L_B2 = new RendererModel(this, 32, 40);
        this.Leg_L_B2.setRotationPoint(2.7F, 18.5F, 7.1F);
        this.Leg_L_B2.addBox(-1.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F);
        this.setRotateAngle(Leg_L_B2, -0.2617993877991494F, 0.0F, 0.0F);
        this.Tooth3 = new RendererModel(this, 24, 61);
        this.Tooth3.setRotationPoint(3.0F, 6.0F, -17.0F);
        this.Tooth3.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
        this.Leg_L_F2 = new RendererModel(this, 32, 16);
        this.Leg_L_F2.setRotationPoint(2.7F, 18.5F, -5.0F);
        this.Leg_L_F2.addBox(-1.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F);
        this.setRotateAngle(Leg_L_F2, -0.2617993877991494F, 0.0F, 0.0F);
        this.Tooth7 = new RendererModel(this, 24, 61);
        this.Tooth7.setRotationPoint(-3.0F, 6.0F, -16.0F);
        this.Tooth7.addBox(0.0F, 0.0F, -0.1F, 0, 2, 1, 0.0F);
        this.Head = new RendererModel(this, 22, 0);
        this.Head.setRotationPoint(0.0F, 7.0F, -7.0F);
        this.Head.addBox(-3.0F, -5.0F, -5.0F, 6, 5, 5, 0.0F);
        this.Body2 = new RendererModel(this, 0, 25);
        this.Body2.setRotationPoint(0.0F, 10.0F, 0.0F);
        this.Body2.addBox(-3.5F, -3.5F, -2.5F, 7, 7, 5, 0.0F);
        this.Leg_R_B2 = new RendererModel(this, 32, 40);
        this.Leg_R_B2.setRotationPoint(-2.7F, 18.5F, 7.1F);
        this.Leg_R_B2.addBox(-1.0F, -1.0F, -1.0F, 2, 7, 2, 0.0F);
        this.setRotateAngle(Leg_R_B2, -0.2617993877991494F, 0.0F, 0.0F);
        this.Tooth5 = new RendererModel(this, 24, 62);
        this.Tooth5.setRotationPoint(-1.0F, 6.0F, -17.0F);
        this.Tooth5.addBox(0.0F, 0.0F, 0.0F, 1, 2, 0, 0.0F);
        this.Jaw_Membrane = new RendererModel(this, 26, 59);
        this.Jaw_Membrane.setRotationPoint(0.0F, 9.0F, -12.0F);
        this.Jaw_Membrane.addBox(-3.0F, -2.0F, 0.0F, 6, 5, 0, 0.0F);
        this.Snout = new RendererModel(this, 0, 0);
        this.Snout.setRotationPoint(0.0F, 4.0F, -12.0F);
        this.Snout.addBox(-3.0F, 0.0F, -5.0F, 6, 2, 5, 0.0F);
        this.Tail3 = new RendererModel(this, 16, 58);
        this.Tail3.setRotationPoint(0.0F, 19.0F, 13.0F);
        this.Tail3.addBox(-1.0F, -1.0F, -1.0F, 2, 4, 2, 0.0F);
        this.setRotateAngle(Tail3, 0.7853981633974483F, 0.0F, 0.0F);
        this.Tooth14 = new RendererModel(this, 24, 61);
        this.Tooth14.setRotationPoint(-3.0F, 10.0F, -17.0F);
        this.Tooth14.addBox(0.0F, 0.0F, 0.0F, 0, 2, 1, 0.0F);
    }

    @Override
    public void render(TeenageEnderhoundEntity teen, float limbSwingTime, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(teen, limbSwingTime, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.setRotationAngles(teen, limbSwingTime, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if(teen.getAttackTarget() != null) {
            this.Tooth1.render(scale);
            this.Tooth2.render(scale);
            this.Tooth3.render(scale);
            this.Tooth4.render(scale);
            this.Tooth5.render(scale);
            this.Tooth6.render(scale);
            this.Tooth7.render(scale);
            this.Tooth8.render(scale);
            this.Tooth9.render(scale);
            this.Tooth10.render(scale);
            this.Tooth11.render(scale);
            this.Tooth12.render(scale);
            this.Tooth13.render(scale);
            this.Tooth14.render(scale);
            this.Tooth15.render(scale);
            this.Tooth16.render(scale);
            this.Jaw_Membrane.render(scale);
        }
        this.Head.render(scale);
        this.Snout.render(scale);
        this.Jaw.render(scale);
        this.Body1.render(scale);
        this.Body2.render(scale);
        this.Body3.render(scale);
        this.Ear_L.render(scale);
        this.Ear_R.render(scale);
        this.Leg_L_F1.render(scale);
        this.Leg_R_B1.render(scale);
        this.Leg_L_B1.render(scale);
        this.Leg_R_F1.render(scale);
        this.Leg_R_F2.render(scale);
        this.Leg_L_B2.render(scale);
        this.Leg_L_F2.render(scale);
        this.Leg_R_B2.render(scale);
        this.Tail1.render(scale);
        this.Tail2.render(scale);
        this.Tail3.render(scale);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(RendererModel RendererModel, float x, float y, float z) {
        RendererModel.rotateAngleX = x;
        RendererModel.rotateAngleY = y;
        RendererModel.rotateAngleZ = z;
    }
}
