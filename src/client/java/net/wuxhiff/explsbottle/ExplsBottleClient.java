package net.wuxhiff.explsbottle;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.wuxhiff.explsbottle.item.ExplsBottleItems;

public class ExplsBottleClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ExplsBottleItems.EXPLS_BOTTLE_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ExplsBottleItems.POISON_BOTTLE_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ExplsBottleItems.FIRE_BOTTLE_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ExplsBottleItems.FLY_BOTTLE_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ExplsBottleItems.BLAZE_BOTTLE_ENTITY, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ExplsBottleItems.HOT_POTATO_ENTITY, FlyingItemEntityRenderer::new);
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}