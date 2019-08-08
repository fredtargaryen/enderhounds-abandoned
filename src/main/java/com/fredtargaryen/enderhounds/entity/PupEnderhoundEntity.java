package com.fredtargaryen.enderhounds.entity;

import com.fredtargaryen.enderhounds.EnderhoundsBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.world.World;

public class PupEnderhoundEntity extends EnderhoundEntity {
    public PupEnderhoundEntity(World world) {
        super(EnderhoundsBase.PUP_TYPE, world);
        this.stage = GrowthStage.PUP;
        this.personality = Personality.WEAK;
        this.power = Power.NONE;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        AbstractAttributeMap aam = this.getAttributes();
        aam.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
        aam.registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.5D);
    }

    protected void grow() {
        TeenageEnderhoundEntity eet = new TeenageEnderhoundEntity(this);
        eet.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        eet.refreshLeader();
        this.world.addEntity(eet);
        super.grow();
    }
}
