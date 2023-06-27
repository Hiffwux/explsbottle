package net.wuxhiff.explsbottle.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.wuxhiff.explsbottle.item.ExplsBottleItems;

import java.util.List;

public class FlyBottleEntity extends ThrownItemEntity {

    public FlyBottleEntity(EntityType<? extends ThrownItemEntity> entityType, World world){
        super(entityType, world);
    }

    public FlyBottleEntity(World world, LivingEntity owner) {
        super(ExplsBottleItems.FLY_BOTTLE_ENTITY, owner, world);
    }

    public FlyBottleEntity(World world, double x, double y, double z) {
        super(ExplsBottleItems.FLY_BOTTLE_ENTITY, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ExplsBottleItems.FLY_BOTTLE_ITEM;
    }

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.BUBBLE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    private void flying(Box box, HitResult result){
        List<LivingEntity> list = this.getWorld().getEntitiesByClass(LivingEntity.class, box, livingEntity -> true);
        Vec3d bombPos = result.getPos();
        for (LivingEntity livingEntity : list) {
            Vec3d livingEntityPos = livingEntity.getPos();
            Vec3d newVector = livingEntityPos.subtract(bombPos).normalize().multiply(2);
            livingEntity.setVelocity(newVector);
        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Box box = this.getBoundingBox().expand(8.0, 8.0, 8.0);
        Entity entity = entityHitResult.getEntity();
        int i = entity instanceof BlazeEntity ? 10 : 1;
        entity.damage(DamageSource.thrownProjectile(this,this.getOwner()), i);
        if (entity instanceof LivingEntity livingEntity) {
            flying(box, entityHitResult);
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
        Box box = this.getBoundingBox().expand(8.0, 8.0, 8.0);
        flying(box, hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, hitResult.getPos().x, hitResult.getPos().y + 0.25, hitResult.getPos().z, 0.35F, false, Explosion.DestructionType.BREAK);
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }
}