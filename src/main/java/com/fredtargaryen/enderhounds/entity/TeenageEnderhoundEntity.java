package com.fredtargaryen.enderhounds.entity;

import com.fredtargaryen.enderhounds.EnderhoundsBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.world.World;

public class TeenageEnderhoundEntity extends EnderhoundEntity {
    public TeenageEnderhoundEntity(EntityType type, World world) {
        super(type, world);
        this.stage = GrowthStage.TEENAGE;
        switch(this.rand.nextInt(3)) {
            case 2:
                this.personality = Personality.AGGRESSIVE;
                break;
            case 1:
                this.personality = Personality.CUNNING;
                break;
            default:
                this.personality = Personality.WEAK;
                break;
        }
        switch(this.rand.nextInt(2)) {
            case 1:
                this.power = Power.BALL;
                break;
            case 0:
                this.power = Power.DOPPEL;
                break;
        }
        this.setHealth(this.getMaxHealth());
        this.stepHeight = 2.0F;
    }

    public TeenageEnderhoundEntity(PupEnderhoundEntity pup) {
        this(EnderhoundsBase.TEENAGE_TYPE, pup.world);
        this.setHealth(this.getMaxHealth() * pup.getHealth() / pup.getMaxHealth());
        this.leader = pup.leader;
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        AbstractAttributeMap aam = this.getAttributes();
        aam.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15);
        aam.registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    protected void grow() {
        new MatureEnderhoundEntity(this);
        super.grow();
    }
}
