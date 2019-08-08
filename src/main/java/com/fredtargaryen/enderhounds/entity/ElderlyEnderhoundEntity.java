package com.fredtargaryen.enderhounds.entity;

import com.fredtargaryen.enderhounds.EnderhoundsBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.world.World;

public class ElderlyEnderhoundEntity extends EnderhoundEntity {
    public ElderlyEnderhoundEntity(EntityType type, World world) {
        super(type, world);
        this.stage = GrowthStage.ELDERLY;
        switch(this.rand.nextInt(2)) {
            case 0:
                this.power = Power.BALL;
                break;
            case 1:
                this.power = Power.DOPPEL;
                break;
        }
        switch(this.rand.nextInt(2)) {
            case 0:
                this.personality = Personality.WEAK;
                break;
            case 1:
                this.personality = Personality.CUNNING;
                break;
        }
        this.setHealth(this.getMaxHealth());
        this.stepHeight = 2.5F;
    }

    public ElderlyEnderhoundEntity(MatureEnderhoundEntity adult) {
        this(EnderhoundsBase.ELDERLY_TYPE, adult.world);
        this.power = adult.power;
        this.personality = adult.personality;
        if(this.personality == Personality.AGGRESSIVE) {
            this.personality = Personality.CUNNING;
        }
        this.setHealth(this.getMaxHealth() * adult.getHealth() / adult.getMaxHealth());
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        AbstractAttributeMap aam = this.getAttributes();
        aam.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
        aam.registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    }

    protected void grow(){}
}
