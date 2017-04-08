package com.fredtargaryen.enderhounds.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityEnderhoundElderly extends EntityEnderhound
{
    public EntityEnderhoundElderly(World world)
    {
        super(world);
        this.stage = GrowthStage.ELDERLY;
        switch(this.rand.nextInt(2))
        {
            case 0:
                this.power = Power.BALL;
                break;
            case 1:
                this.power = Power.DOPPEL;
                break;
        }
        switch(this.rand.nextInt(2))
        {
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

    public EntityEnderhoundElderly(EntityEnderhoundMature adult)
    {
        this(adult.world);
        this.power = adult.power;
        this.personality = adult.personality;
        if(this.personality == Personality.AGGRESSIVE)
        {
            this.personality = Personality.CUNNING;
        }
        this.setHealth(this.getMaxHealth() * adult.getHealth() / adult.getMaxHealth());
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    }

    protected void grow(){}
}
