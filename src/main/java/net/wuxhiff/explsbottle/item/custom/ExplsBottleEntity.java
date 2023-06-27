package net.wuxhiff.explsbottle.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.wuxhiff.explsbottle.item.ExplsBottleItems;

import java.util.List;
import java.util.function.Predicate;

import static net.wuxhiff.explsbottle.item.ExplsBottleItems.EXPLS_BOTTLE_ITEM;

public class ExplsBottleEntity extends ThrownItemEntity {
    public ExplsBottleEntity(EntityType<? extends ThrownItemEntity> entityType, World world){
        super(entityType, world);
    }

    public ExplsBottleEntity(World world, LivingEntity owner) {
        super(ExplsBottleItems.EXPLS_BOTTLE_ENTITY, owner, world);
    }

    public ExplsBottleEntity(World world, double x, double y, double z) {
        super(ExplsBottleItems.EXPLS_BOTTLE_ENTITY, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ExplsBottleItems.EXPLS_BOTTLE_ITEM;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof BlazeEntity ? 3 : 1;
        entity.damage(DamageSource.thrownProjectile(this,this.getOwner()), i);
//        if (entity instanceof LivingEntity livingEntity) { // checks if entity is an instance of LivingEntity (meaning it is not a boat or minecart)
//            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 3, 0))); // applies a status effect
//            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 3, 2))); // applies a status effect
//            livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 3, 1))); // applies a status effectplays a sound for the entity hit only
//        }
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
        /*Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, box, livingEntity -> true);
        for (LivingEntity livingEntity : list) {
            double d = this.squaredDistanceTo(livingEntity);
            if (!(d < 16.0)) continue;
            if (livingEntity.canTakeDamage()) {
                livingEntity.addStatusEffect((new StatusEffectInstance(StatusEffects.POISON, 20 * 3, 2)));
            }
        }*/
        //this.getWorld().createExplosion(this, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z, 1.25F, false, World.ExplosionSourceType.BLOCK);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z, 1.5F, false, Explosion.DestructionType.BREAK);
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

}
