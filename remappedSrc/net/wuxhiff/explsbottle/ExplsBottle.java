package net.wuxhiff.explsbottle;

import net.fabricmc.api.ModInitializer;

import net.wuxhiff.explsbottle.item.ExplsBottleItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplsBottle implements ModInitializer {
    public static final String EXPLSBOTTLE_ID = "explsbottle";
    public static final Logger LOGGER = LoggerFactory.getLogger(EXPLSBOTTLE_ID);

    @Override
    public void onInitialize() {
        ExplsBottleItems.registerItems();
        LOGGER.info("Hello Fabric world!");
    }
}