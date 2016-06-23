package com.fredtargaryen.enderhounds.client.model.entity;

import com.fredtargaryen.enderhounds.entity.EntityEnderhoundPup;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEnderhoundPup extends ModelBase
{
    //fields
    ModelRenderer Body1;
    ModelRenderer Head;
    ModelRenderer Body2;
    ModelRenderer Body3;
    ModelRenderer Ear_L;
    ModelRenderer Leg_L_F;
    ModelRenderer Leg_R_F;
    ModelRenderer Leg_L_B;
    ModelRenderer Leg_R_B;
    ModelRenderer Tail;
    ModelRenderer Snout;
    ModelRenderer Ear_R;
    ModelRenderer Tooth_T;
    ModelRenderer Tooth_B;
    ModelRenderer Jaw;
    ModelRenderer Jaw_Membrane;
  
    public ModelEnderhoundPup()
    {
        textureWidth = 64;
        textureHeight = 32;

        Body1 = new ModelRenderer(this, 0, 0);
        Body1.addBox(-3F, -3F, -2F, 6, 6, 4);
        Body1.setRotationPoint(0F, 11.7F, -4F);
        Body1.setTextureSize(64, 32);
        Body1.mirror = true;
        setRotation(Body1, 0F, 0F, 0F);
        Head = new ModelRenderer(this, 18, 15);
        Head.addBox(-2.5F, -4F, -4F, 5, 4, 4);
        Head.setRotationPoint(0F, 9.7F, -5F);
        Head.setTextureSize(64, 32);
        Head.mirror = true;
      setRotation(Head, 0F, 0F, 0F);
      Body2 = new ModelRenderer(this, 20, 0);
      Body2.addBox(-3F, -3F, -2F, 6, 6, 4);
      Body2.setRotationPoint(0F, 11.7F, 0F);
      Body2.setTextureSize(64, 32);
      Body2.mirror = true;
      setRotation(Body2, 0F, 0F, 0F);
      Body3 = new ModelRenderer(this, 40, 0);
      Body3.addBox(-3F, -3F, -2F, 6, 6, 4);
      Body3.setRotationPoint(0F, 11.7F, 4F);
      Body3.setTextureSize(64, 32);
      Body3.mirror = true;
      setRotation(Body3, 0F, 0F, 0F);
      Ear_L = new ModelRenderer(this, 60, 0);
      Ear_L.addBox(0F, -6F, -2F, 0, 6, 2);
      Ear_L.setRotationPoint(2.5F, 5.7F, -5F);
      Ear_L.setTextureSize(64, 32);
      Ear_L.mirror = true;
      setRotation(Ear_L, 0F, 0.1745329F, 0F);
      Leg_L_F = new ModelRenderer(this, 56, 19);
      Leg_L_F.mirror = true;
      Leg_L_F.addBox(-1F, 0F, -1F, 2, 10, 2);
      Leg_L_F.setRotationPoint(1.9F, 14.0F, -4.9F);
      Leg_L_F.setTextureSize(64, 32);
      Leg_L_F.mirror = true;
      setRotation(Leg_L_F, 0.0F, 0F, 0F);
      Leg_L_F.mirror = false;
      Leg_R_F = new ModelRenderer(this, 56, 19);
      Leg_R_F.addBox(-1F, 0F, -1F, 2, 10, 2);
      Leg_R_F.setRotationPoint(-1.9F, 14.0F, -4.9F);
      Leg_R_F.setTextureSize(64, 32);
      Leg_R_F.mirror = true;
      setRotation(Leg_R_F, 0.0F, 0F, 0F);
      Leg_L_B = new ModelRenderer(this, 56, 19);
      Leg_L_B.mirror = true;
      Leg_L_B.addBox(-1F, 0F, -1F, 2, 10, 2);
      Leg_L_B.setRotationPoint(1.9F, 14F, 4.9F);
      Leg_L_B.setTextureSize(64, 32);
      Leg_L_B.mirror = true;
      setRotation(Leg_L_B, 0.0F, 0F, 0F);
      Leg_L_B.mirror = false;
      Leg_R_B = new ModelRenderer(this, 56, 19);
      Leg_R_B.addBox(-1F, 0F, -1F, 2, 10, 2);
      Leg_R_B.setRotationPoint(-1.9F, 14F, 4.9F);
      Leg_R_B.setTextureSize(64, 32);
      Leg_R_B.mirror = true;
      setRotation(Leg_R_B, 0.0F, 0F, 0F);
      Tail = new ModelRenderer(this, 45, 20);
      Tail.addBox(-1F, -1F, -1F, 2, 10, 2);
      Tail.setRotationPoint(0F, 10.2F, 5F);
      Tail.setTextureSize(64, 32);
      Tail.mirror = true;
      setRotation(Tail, 0.7853982F, 0F, 0F);
      Snout = new ModelRenderer(this, 0, 17);
      Snout.addBox(-2.5F, -0.5F, -4F, 5, 1, 4);
      Snout.setRotationPoint(0F, 8.2F, -9F);
      Snout.setTextureSize(64, 32);
      Snout.mirror = true;
      setRotation(Snout, 0F, 0F, 0F);
      Ear_R = new ModelRenderer(this, 60, 0);
      Ear_R.mirror = true;
      Ear_R.addBox(0F, -6F, -2F, 0, 6, 2);
      Ear_R.setRotationPoint(-2.5F, 5.7F, -5F);
      Ear_R.setTextureSize(64, 32);
      Ear_R.mirror = true;
      setRotation(Ear_R, 0F, -0.1745329F, 0F);
      Ear_R.mirror = false;
      Tooth_T = new ModelRenderer(this, 62, 31);
      Tooth_T.addBox(-0.5F, 0F, 0F, 1, 1, 0);
      Tooth_T.setRotationPoint(0F, 8.7F, -12F);
      Tooth_T.setTextureSize(64, 32);
      Tooth_T.mirror = true;
      setRotation(Tooth_T, 0F, 0F, 0F);
      Tooth_B = new ModelRenderer(this, 62, 31);
      Tooth_B.addBox(-0.5F, -1F, 0F, 1, 1, 0);
      Tooth_B.setRotationPoint(0F, 9.7F, -12F);
      Tooth_B.setTextureSize(64, 32);
      Tooth_B.mirror = true;
      setRotation(Tooth_B, 0F, 0F, 0F);
      Jaw = new ModelRenderer(this, 0, 22);
      Jaw.addBox(-2.5F, -0.5F, -4F, 5, 1, 4);
      Jaw.setRotationPoint(0F, 9.2F, -9F);
      Jaw.setTextureSize(64, 32);
      Jaw.mirror = true;
      setRotation(Jaw, 0F, 0F, 0F);
      Jaw_Membrane = new ModelRenderer(this, 34, 28);
      Jaw_Membrane.addBox(-2.5F, -2F, 0F, 5, 4, 0);
      Jaw_Membrane.setRotationPoint(0F, 11.7F, -4F);
      Jaw_Membrane.setTextureSize(64, 32);
      Jaw_Membrane.mirror = true;
      setRotation(Jaw_Membrane, 0F, 0F, 0F);
      }

    /**
     * Changes points and angles based on entity state, before telling each body part to render.
     */
      public void render(Entity entity, float time, float walkSpeed, float whatever, float f3, float f4, float f5)
      {
        super.render(entity, time, walkSpeed, whatever, f3, f4, f5);
        setRotationAngles(time, walkSpeed, whatever, f3, f4, f5, entity);
        EntityEnderhoundPup pup = (EntityEnderhoundPup) entity;
        if(pup.getAttackTarget() == null)
        {
          Body2.setRotationPoint(0F, 11.7F, 0F);
          setRotation(Ear_L, 0F, 0.1745329F, 0F);
          setRotation(Tail, 0.7853982F, 0F, 0F);
          setRotation(Ear_R, 0F, -0.1745329F, 0F);
          Tooth_T.setRotationPoint(0F, 8.7F, -12F);
          Tooth_B.setRotationPoint(0F, 9.7F, -12F);
          Jaw.setRotationPoint(0F, 9.2F, -9F);
          Jaw_Membrane.setRotationPoint(0F, 11.7F, -4F);
        }
        else
        {
          Body2.setRotationPoint(0F, 8.7F, 0F);
            //I originally set Z to - and + 1.3962634F (80 degrees)
            //Which worked in Techne... but not here
            //So trying X 40 degrees and Z 40 degrees right now
          setRotation(Ear_L, -0.6981317F, 1.5707963F, -0.6981317F);
          setRotation(Ear_R, 0.6981317F, -1.5707963F, 0.6981317F);
          setRotation(Tail, 1.5707963F, 0F, 0F);
          Tooth_T.setRotationPoint(0F, 8.7F, -13F);
          Tooth_B.setRotationPoint(0F, 12.7F, -13F);
          Jaw.setRotationPoint(0F, 13.2F, -9F);
          Jaw_Membrane.setRotationPoint(0F, 10.7F, -9F);
        }
        Body1.render(f5);
        Head.render(f5);
        Body2.render(f5);
        Body3.render(f5);
        Ear_L.render(f5);
        Leg_L_F.render(f5);
        Leg_R_F.render(f5);
        Leg_L_B.render(f5);
        Leg_R_B.render(f5);
        Tail.render(f5);
        Snout.render(f5);
        Ear_R.render(f5);
        Tooth_T.render(f5);
        Tooth_B.render(f5);
        Jaw.render(f5);
        Jaw_Membrane.render(f5);
      }

      private void setRotation(ModelRenderer model, float x, float y, float z)
      {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
      }

    /**
     *
     * @param time Time spent moving
     * @param walkSpeed Speed the entity is walking at
     * @param whatever Override handleRotationFloat to decide this value
     * @param rotationYaw Rotation about Y axis (left-right)
     * @param rotationPitch Rotation about X axis (forwards-backwards)
     * @param scale Scale of the model; set to 1 / 16.0F.
     * @param entity
     */
      public void setRotationAngles(float time, float walkSpeed, float whatever, float rotationYaw, float rotationPitch, float scale, Entity entity)
      {
        super.setRotationAngles(time, walkSpeed, whatever, rotationYaw, rotationPitch, scale, entity);
        Leg_L_F.rotateAngleX = (float) (Math.sin(time * 0.6662F) * 0.524 * walkSpeed);
        Leg_R_F.rotateAngleX = -Leg_L_F.rotateAngleX;
        Leg_L_B.rotateAngleX = Leg_L_F.rotateAngleX;
        Leg_R_B.rotateAngleX = Leg_R_F.rotateAngleX;
      }
}
