package net.wuxhiff.explsbottle.item;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.wuxhiff.explsbottle.ExplsBottle;
import net.wuxhiff.explsbottle.item.custom.*;


public class ExplsBottleItems {

    public static final Item EXPLS_BOTTLE_ITEM = registerItem("exploding_bottle",
            new ExplsBottleMain(new Item.Settings().maxCount(16).group(ItemGroup.COMBAT)));

    public static final Item POISON_BOTTLE_ITEM = registerItem("poison_bottle",
            new PoisonBottleMain(new Item.Settings().maxCount(16).group(ItemGroup.COMBAT)));

    public static final Item FIRE_BOTTLE_ITEM = registerItem("fire_bottle",
            new FireBottleMain(new Item.Settings().maxCount(16).group(ItemGroup.COMBAT)));

    public static final EntityType<ExplsBottleEntity> EXPLS_BOTTLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "expls_bottle_entity"),
            FabricEntityTypeBuilder.<ExplsBottleEntity>create(SpawnGroup.MISC, ExplsBottleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
    );

    public static final EntityType<PoisonBottleEntity> POISON_BOTTLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "poison_bottle_entity"),
            FabricEntityTypeBuilder.<PoisonBottleEntity>create(SpawnGroup.MISC, PoisonBottleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
    );

    public static final EntityType<FireBottleEntity> FIRE_BOTTLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "fire_bottle_entity"),
            FabricEntityTypeBuilder.<FireBottleEntity>create(SpawnGroup.MISC, FireBottleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
                    .trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
                    .build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
    );

    private static Item registerItem(String name, Item item) {
        //ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registry.ITEM, new Identifier(ExplsBottle.EXPLSBOTTLE_ID, name), item);
    }
    public static void registerItems(){
        ExplsBottle.LOGGER.debug("Register");
    }
}
