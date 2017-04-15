package com.fredtargaryen.enderhounds.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityEnderhoundTeenage extends EntityEnderhound
{
    public EntityEnderhoundTeenage(World world)
    {
        super(world);
        this.stage = GrowthStage.TEENAGE;
        switch(this.rand.nextInt(3))
        {
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
        switch(this.rand.nextInt(2))
        {
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

    public EntityEnderhoundTeenage(EntityEnderhoundPup pup)
    {
        this(pup.world);
        this.setHealth(this.getMaxHealth() * pup.getHealth() / pup.getMaxHealth());
        this.leader = pup.leader;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    protected void grow()
    {
        new EntityEnderhoundMature(this);
        super.grow();
    }
}
