package com.fredtargaryen.enderhounds.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityEnderhoundPup extends EntityEnderhound
{
    public EntityEnderhoundPup(World world)
    {
        super(world);
        this.stage = GrowthStage.PUP;
        this.personality = Personality.WEAK;
        this.power = Power.NONE;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.5D);
    }

    protected void grow()
    {
        new EntityEnderhoundTeenage(this);
        super.grow();
    }
}
