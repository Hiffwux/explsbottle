package net.wuxhiff.explsbottle.item.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.wuxhiff.explsbottle.item.ExplsBottleItems;

import java.util.List;

public class PoisonBottleEntity extends ThrownItemEntity {
    public PoisonBottleEntity(EntityType<? extends ThrownItemEntity> entityType, World world){
        super(entityType, world);
    }

    public PoisonBottleEntity(World world, LivingEntity owner) {
        super(ExplsBottleItems.POISON_BOTTLE_ENTITY, owner, world);
    }

    public PoisonBottleEntity(World world, double x, double y, double z) {
        super(ExplsBottleItems.POISON_BOTTLE_ENTITY, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ExplsBottleItems.POISON_BOTTLE_ITEM;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.WITCH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    private void poisonDamaging(Box box){
        List<Entity> hits = this.getWorld().getEntitiesByType(TypeFilter.instanceOf(Entity.class),box, entity -> true);
        for (Entity entity : hits) {
            double d = this.squaredDistanceTo(entity);
            if (!(d < 16.0)) continue;
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.canTakeDamage()) {
                    livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 100 * 3, 3))); // applies a status effect
                    livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 100 * 3, 3)));

                }
            }

        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Box box = this.getBoundingBox().expand(5.0, 5.0, 5.0);
        Entity entity = entityHitResult.getEntity();
        entity.damage(DamageSource.thrownProjectile(this,this.getOwner()), 3);
        if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.NAUSEA, 60 * 3, 3))); // applies a status effect
        }
        //poisonDamaging(box);
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

    private void safeExpls(World world, Entity entity,double x, double y, double z, float power, boolean createFire, Explosion.DestructionType destructionType){
        CustomExplosion explosion = new CustomExplosion(world, entity,null, null, x, y, z, power, createFire, destructionType);
        explosion.collectBlocksAndDamageEntities();
        explosion.affectWorld(true);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        Box box = this.getBoundingBox().expand(5.0, 5.0, 5.0);
        if (!this.getWorld().isClient) {
            poisonDamaging(box);
            this.getWorld().createExplosion(this, hitResult.getPos().x, hitResult.getPos().y + 0.5, hitResult.getPos().z, 0.65F, false, Explosion.DestructionType.BREAK);
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}
