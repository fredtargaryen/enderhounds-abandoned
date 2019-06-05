package com.fredtargaryen.enderhounds.entity;

import com.fredtargaryen.enderhounds.EnderhoundsBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.world.World;

public class EntityEnderhoundMature extends EntityEnderhound {
    public EntityEnderhoundMature(EntityType type, World world) {
        super(type, world);
        this.stage = GrowthStage.MATURE;
        switch(this.rand.nextInt(3)) {
            case 0:
                this.personality = Personality.AGGRESSIVE;
                break;
            case 1:
                this.personality = Personality.CUNNING;
                break;
            case 2:
                this.personality = Personality.WEAK;
                break;
        }
        switch(this.rand.nextInt(2)) {
            case 0:
                this.power = Power.DOPPEL;
                break;
            case 1:
                this.power = Power.BALL;
                break;
        }
        this.setHealth(this.getMaxHealth());
        this.stepHeight = 3.0F;
    }

    public EntityEnderhoundMature(EntityEnderhoundTeenage teen) {
        this(EnderhoundsBase.MATURE_TYPE, teen.world);
        this.personality = teen.personality;
        this.power = teen.power;
        this.setHealth(this.getMaxHealth() * teen.getHealth() / teen.getMaxHealth());
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        AbstractAttributeMap aam = this.getAttributeMap();
        aam.getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        aam.getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.5D);
    }

    protected void grow() {
        new EntityEnderhoundElderly(this);
        super.grow();
    }
}
