package com.fredtargaryen.enderhounds.client.model;

import com.fredtargaryen.enderhounds.entity.PupEnderhoundEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

/**
 * PupEnderhoundModel - FredTargaryen
 * Created using Tabula 7.0.1
 */
public class PupEnderhoundModel extends EntityModel<PupEnderhoundEntity> {
    public RendererModel Head;
    public RendererModel Body1;
    public RendererModel Body2;
    public RendererModel Body3;
    public RendererModel Ear_L;
    public RendererModel Ear_R;
    public RendererModel Leg_L_F;
    public RendererModel Leg_R_F;
    public RendererModel Leg_L_B;
    public RendererModel Leg_R_B;
    public RendererModel Tail;
    public RendererModel Snout;
    public RendererModel Tooth_T;
    public RendererModel Tooth_B;
    public RendererModel Jaw;
    public RendererModel Jaw_Membrane;

    public static RendererModelTransform Body2_RMT = new RendererModelTransform(
            new float[] {0F, 11.7F, 0F},
            new float[] {0F, 0F, 0F},
            new float[] {0F, 8.7F, 0F},
            new float[] {0F, 0F, 0F}
    );

    public static RendererModelTransform Ear_L_RMT = new RendererModelTransform(
            new float[] {2.5F, 6.7F, -5F},
            new float[] {0F, 0.7853982F, 0F},
            new float[] {2.5F, 7.7F, -5F},
            new float[] {-0.83775804F, 0.34906585F, 0F}
    );

    public static RendererModelTransform Ear_R_RMT = new RendererModelTransform(
            new float[] {-2.5F, 6.7F, -5F},
            new float[] {0F, -0.7853982F, 0F},
            new float[] {-2.5F, 7.7F, -5F},
            new float[] {-0.83775804F, -0.34906585F, 0F}
    );

    public static RendererModelTransform Tail_RMT = new RendererModelTransform(
            new float[] {0F, 10.2F, 5F},
            new float[] {0.7853982F, 0F, 0F},
            new float[] {0F, 10.2F, 5F},
            new float[] {1.5707963F, 0F, 0F}
    );

    public static RendererModelTransform Tooth_T_RMT = new RendererModelTransform(
            new float[] {0F, 8.7F, -12F},
            new float[] {0F, 0F, 0F},
            new float[] {0F, 8.7F, -13F},
            new float[] {0F, 0F, 0F}
    );

    public static RendererModelTransform Tooth_B_RMT = new RendererModelTransform(
            new float[] {0F, 9.7F, -12F},
            new float[] {0F, 0F, 0F},
            new float[] {0F, 12.7F, -13F},
            new float[] {0F, 0F, 0F}
    );

    public static RendererModelTransform Jaw_RMT = new RendererModelTransform(
            new float[] {0F, 9.2F, -9F},
            new float[] {0F, 0F, 0F},
            new float[] {0F, 13.2F, -9F},
            new float[] {0F, 0F, 0F}
    );

    public static RendererModelTransform Jaw_Membrane_RMT = new RendererModelTransform(
            new float[] {0F, 11.7F, -4F},
            new float[] {0F, 0F, 0F},
            new float[] {0F, 10.7F, -9F},
            new float[] {0F, 0F, 0F}
    );

    public PupEnderhoundModel() {
        textureWidth = 64;
        textureHeight = 32;

        Head = new RendererModel(this, 18, 15);
        Head.addBox(-2.5F, -4F, -4F, 5, 4, 4);
        Head.setTextureSize(64, 32);
        Head.setRotationPoint(0F, 9.7F, -5F);
        setRotation(Head, 0F, 0F, 0F);

        Body1 = new RendererModel(this, 0, 0);
        Body1.addBox(-3F, -3F, -2F, 6, 6, 4);
        Body1.setTextureSize(64, 32);
        Body1.setRotationPoint(0F, 11.7F, -4F);
        setRotation(Body1, 0F, 0F, 0F);

        Body2 = new RendererModel(this, 20, 0);
        Body2.addBox(-3F, -3F, -2F, 6, 6, 4);
        Body2.setTextureSize(64, 32);

        Body3 = new RendererModel(this, 40, 0);
        Body3.addBox(-3F, -3F, -2F, 6, 6, 4);
        Body3.setTextureSize(64, 32);
        Body3.setRotationPoint(0F, 11.7F, 4F);
        setRotation(Body3, 0F, 0F, 0F);

        Ear_L = new RendererModel(this, 58, 10);
        Ear_L.addBox(-1F, -6F, -2F, 1, 6, 2);
        Ear_L.setTextureSize(64, 32);

        Leg_L_F = new RendererModel(this, 56, 19);
        Leg_L_F.addBox(-1F, 0F, -1F, 2, 10, 2);
        Leg_L_F.setTextureSize(64, 32);
        Leg_L_F.setRotationPoint(1.9F, 14F, -4.9F);
        setRotation(Leg_L_F, 0F, 0F, 0F);

        Leg_R_F = new RendererModel(this, 56, 19);
        Leg_R_F.addBox(-1F, 0F, -1F, 2, 10, 2);
        Leg_R_F.setTextureSize(64, 32);
        Leg_R_F.setRotationPoint(-1.9F, 14F, -4.9F);
        setRotation(Leg_R_F, 0F, 0F, 0F);

        Leg_L_B = new RendererModel(this, 56, 19);
        Leg_L_B.addBox(-1F, 0F, -1F, 2, 10, 2);
        Leg_L_B.setTextureSize(64, 32);
        Leg_L_B.setRotationPoint(1.9F, 14F, 4.9F);
        setRotation(Leg_L_B, 0F, 0F, 0F);

        Leg_R_B = new RendererModel(this, 56, 19);
        Leg_R_B.addBox(-1F, 0F, -1F, 2, 10, 2);
        Leg_R_B.setTextureSize(64, 32);
        Leg_R_B.setRotationPoint(-1.9F, 14F, 4.9F);
        setRotation(Leg_R_B, 0F, 0F, 0F);

        Tail = new RendererModel(this, 45, 20);
        Tail.addBox(-1F, -1F, -1F, 2, 10, 2);
        Tail.setTextureSize(64, 32);
        setRotation(Tail, 0.7853982F, 0F, 0F);

        Snout = new RendererModel(this, 0, 17);
        Snout.addBox(-2.5F, -0.5F, -4F, 5, 1, 4);
        Snout.setTextureSize(64, 32);
        setRotation(Snout, 0F, 0F, 0F);

        Ear_R = new RendererModel(this, 51, 10);
        Ear_R.addBox(0F, -6F, -2F, 1, 6, 2);
        Ear_R.setTextureSize(64, 32);

        Tooth_T = new RendererModel(this, 62, 31);
        Tooth_T.addBox(-0.5F, 0F, 0F, 1, 1, 0);
        Tooth_T.setTextureSize(64, 32);
        setRotation(Tooth_T, 0F, 0F, 0F);

        Tooth_B = new RendererModel(this, 62, 31);
        Tooth_B.addBox(-0.5F, -1F, 0F, 1, 1, 0);
        Tooth_B.setTextureSize(64, 32);
        setRotation(Tooth_B, 0F, 0F, 0F);

        Jaw = new RendererModel(this, 0, 22);
        Jaw.addBox(-2.5F, -0.5F, -4F, 5, 1, 4);
        Jaw.setTextureSize(64, 32);
        Jaw.setRotationPoint(0F, 9.2F, -9F);
        setRotation(Jaw, 0F, 0F, 0F);

        Jaw_Membrane = new RendererModel(this, 34, 28);
        Jaw_Membrane.addBox(-2.5F, -2F, 0F, 5, 4, 0);
        Jaw_Membrane.setTextureSize(64, 32);
        setRotation(Jaw_Membrane, 0F, 0F, 0F);
    }

    @Override
    public void render(PupEnderhoundEntity pup, float limbSwingTime, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(pup, limbSwingTime, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.setRotationAngles(pup, limbSwingTime, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if(pup.getAttackTarget() == null) {
            RendererModelTransform.setCalmTransform(Body2, Body2_RMT);
            RendererModelTransform.setCalmTransform(Ear_L, Ear_L_RMT);
            RendererModelTransform.setCalmTransform(Ear_R, Ear_R_RMT);
            RendererModelTransform.setCalmTransform(Tail, Tail_RMT);
            RendererModelTransform.setCalmTransform(Tooth_T, Tooth_T_RMT);
            RendererModelTransform.setCalmTransform(Tooth_B, Tooth_B_RMT);
            RendererModelTransform.setCalmTransform(Jaw, Jaw_RMT);
            RendererModelTransform.setCalmTransform(Jaw_Membrane, Jaw_Membrane_RMT);
        }
        else {
            //Arch the back, like a cat
            RendererModelTransform.setAngryTransform(Body2, Body2_RMT);
            //Fold back ears
            RendererModelTransform.setAngryTransform(Ear_L, Ear_L_RMT);
            RendererModelTransform.setAngryTransform(Ear_R, Ear_R_RMT);
            //Straighten tail
            RendererModelTransform.setAngryTransform(Tail, Tail_RMT);
            //Show teeth
            RendererModelTransform.setAngryTransform(Tooth_T, Tooth_T_RMT);
            RendererModelTransform.setAngryTransform(Tooth_B, Tooth_B_RMT);
            //Open up jaws
            RendererModelTransform.setAngryTransform(Jaw, Jaw_RMT);
            RendererModelTransform.setAngryTransform(Jaw_Membrane, Jaw_Membrane_RMT);
        }
        Body1.render(scale);
        Head.render(scale);
        Body2.render(scale);
        Body3.render(scale);
        Ear_L.render(scale);
        Leg_L_F.render(scale);
        Leg_R_F.render(scale);
        Leg_L_B.render(scale);
        Leg_R_B.render(scale);
        Tail.render(scale);
        Snout.render(scale);
        Ear_R.render(scale);
        Tooth_T.render(scale);
        Tooth_B.render(scale);
        Jaw.render(scale);
        Jaw_Membrane.render(scale);
    }

    /**
     * Order of rotation for RendererModels is Z first, then Y, then X; all relative to the RendererModel
     * I don't like it but c'est la vie
     */
    private void setRotation(RendererModel model, float x, float y, float z) {
        model.rotateAngleZ = z;
        model.rotateAngleY = y;
        model.rotateAngleX = x;
    }

    /**
     * @param scale Scale of the model; set to 1 / 16.0F.
     */
    @Override
    public void setRotationAngles(PupEnderhoundEntity e, float limbSwingTime, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.setRotationAngles(e, limbSwingTime, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        Leg_L_F.rotateAngleX = (float) (Math.sin(limbSwingTime * 0.6662F) * 0.524 * limbSwingAmount);
        Leg_R_F.rotateAngleX = -Leg_L_F.rotateAngleX;
        Leg_L_B.rotateAngleX = Leg_L_F.rotateAngleX;
        Leg_R_B.rotateAngleX = Leg_R_F.rotateAngleX;
    }
}
