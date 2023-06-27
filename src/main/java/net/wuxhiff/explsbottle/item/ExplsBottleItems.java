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

    public static final Item FLY_BOTTLE_ITEM = registerItem("fly_bottle",
            new FlyBottleMain(new Item.Settings().maxCount(32).group(ItemGroup.COMBAT)));

    public static final Item BLAZE_BOTTLE_ITEM = registerItem("blaze_bottle",
            new BlazeBottleMain(new Item.Settings().maxCount(4).group(ItemGroup.COMBAT)));

    public static final Item HOT_POTATO_ITEM = registerItem("hot_potato",
            new HotPotatoMain(new Item.Settings().maxCount(64).group(ItemGroup.COMBAT)));

    public static final EntityType<ExplsBottleEntity> EXPLS_BOTTLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "expls_bottle_entity"),
            FabricEntityTypeBuilder.<ExplsBottleEntity>create(SpawnGroup.MISC, ExplsBottleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<PoisonBottleEntity> POISON_BOTTLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "poison_bottle_entity"),
            FabricEntityTypeBuilder.<PoisonBottleEntity>create(SpawnGroup.MISC, PoisonBottleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<FireBottleEntity> FIRE_BOTTLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "fire_bottle_entity"),
            FabricEntityTypeBuilder.<FireBottleEntity>create(SpawnGroup.MISC, FireBottleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<FlyBottleEntity> FLY_BOTTLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "fly_bottle_entity"),
            FabricEntityTypeBuilder.<FlyBottleEntity>create(SpawnGroup.MISC, FlyBottleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<BlazeBottleEntity> BLAZE_BOTTLE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "blaze_bottle_entity"),
            FabricEntityTypeBuilder.<BlazeBottleEntity>create(SpawnGroup.MISC, BlazeBottleEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<HotPotatoEntity> HOT_POTATO_ENTITY = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(ExplsBottle.EXPLSBOTTLE_ID, "hot_potato_entity"),
            FabricEntityTypeBuilder.<HotPotatoEntity>create(SpawnGroup.MISC, HotPotatoEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(4).trackedUpdateRate(10)
                    .build()
    );

    private static Item registerItem(String name, Item item) {
        //ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
        return Registry.register(Registry.ITEM, new Identifier(ExplsBottle.EXPLSBOTTLE_ID, name), item);
    }
    public static void registerItems(){
        ExplsBottle.LOGGER.debug("Register");
    }
}
