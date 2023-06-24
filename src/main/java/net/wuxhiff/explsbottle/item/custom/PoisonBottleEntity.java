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
        //List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, box, livingEntity -> true);
        for (Entity entity : hits) {
            double x = entity.getX();
            double y = entity.getEyeY();
            double z = entity.getZ();
            double d = this.squaredDistanceTo(entity);
            if (!(d < 16.0)) continue;
            if (entity instanceof LivingEntity livingEntity) {
                if (livingEntity.canTakeDamage()) {
                    livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 100 * 3, 3))); // applies a status effect
                    livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 100 * 3, 3)));
                    entity.setVelocity(entity.getVelocity().add(x, y, z + 2));
                }
            }
            if (entity instanceof PlayerEntity playerEntity){
                if (!playerEntity.isSpectator() && (!playerEntity.isCreative() || !playerEntity.getAbilities().flying)) {
                    playerEntity.setVelocity(playerEntity.getVelocity().add(x, y,z + 10));
                }
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Box box = this.getBoundingBox().expand(5.0, 5.0, 5.0);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof BlazeEntity ? 3 : 0;
        entity.damage(DamageSource.thrownProjectile(this,this.getOwner()), i);
        if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.NAUSEA, 60 * 3, 3))); // applies a status effect
            //livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 60 * 3, 2))); // applies a status effect
            //livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 60 * 3, 2)));
        }
        poisonDamaging(box);
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
        //List<Entity> hits = this.getWorld().getEntitiesByType(TypeFilter.instanceOf(Entity.class),box, entity -> true);
        //this.getWorld().createExplosion(this, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z, 1.25F, false, World.ExplosionSourceType.BLOCK);
        if (!this.getWorld().isClient) {
            poisonDamaging(box);
            this.getWorld().createExplosion(this, hitResult.getPos().x, hitResult.getPos().y + 1, hitResult.getPos().z, 0.5F, false, Explosion.DestructionType.BREAK);
            //safeExpls(this.getWorld(),this, hitResult.getPos().x, hitResult.getPos().y + 1, hitResult.getPos().z, 0.5F, false, Explosion.DestructionType.BREAK);
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}
