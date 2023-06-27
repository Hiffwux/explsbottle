package net.wuxhiff.explsbottle.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.wuxhiff.explsbottle.item.ExplsBottleItems;

import java.util.List;

public class HotPotatoEntity extends ThrownItemEntity {
    public HotPotatoEntity(EntityType<? extends ThrownItemEntity> entityType, World world){
        super(entityType, world);
    }

    public HotPotatoEntity(World world, LivingEntity owner) {
        super(ExplsBottleItems.HOT_POTATO_ENTITY, owner, world);
    }

    public HotPotatoEntity(World world, double x, double y, double z) {
        super(ExplsBottleItems.HOT_POTATO_ENTITY, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ExplsBottleItems.HOT_POTATO_ITEM;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.ASH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this,this.getOwner()), 5);
        int fate = Random.create().nextBetween(1,5);
        if (entity instanceof LivingEntity livingEntity) {
            if (fate == 1) {
                livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.HUNGER, 25 * 3, 2)));
            } else if (fate == 2) {
                livingEntity.setOnFireFor(2);
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleParameters();
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }



    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, hitResult.getPos().x, hitResult.getPos().y + 0.5, hitResult.getPos().z, 0.3F, false, Explosion.DestructionType.NONE);
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}
