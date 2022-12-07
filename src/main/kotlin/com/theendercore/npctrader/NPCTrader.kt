package com.theendercore.npctrader

import com.theendercore.npctrader.entity.TraderEntities
import com.theendercore.npctrader.entity.TraderEntity
import com.theendercore.npctrader.entity.client.TraderRenderer
import com.theendercore.npctrader.trades.TradeManager
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.item.ItemGroup
import net.minecraft.item.SpawnEggItem
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import software.bernie.geckolib3.GeckoLib


const val MODID: String = "npctrader"

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger(MODID)

fun id(path: String): Identifier = Identifier(MODID, path)

@JvmField
val TRADER_SPAWN_EGG =
    SpawnEggItem(TraderEntities.TRADER, 0xffffff, 0x000000, FabricItemSettings().group(ItemGroup.MISC).maxCount(3))

val DOLLAR = DollarItem(FabricItemSettings().group(ItemGroup.MISC))




@Suppress("unused")
fun onInitialize() {
    LOGGER.info("help!")

    GeckoLib.initialize()
    Registry.register(Registry.ITEM, id("trader_spawn_egg"), TRADER_SPAWN_EGG)
    Registry.register(Registry.ITEM, id("dollar"), DOLLAR)

    FabricDefaultAttributeRegistry.register(TraderEntities.TRADER, TraderEntity.setAttributes())
    TradeManager.registerTrader(TraderEntities.TRADER)
}






@Suppress("unused")
fun onClientInitialize() {
    LOGGER.info(":gun: Client")

    EntityRendererRegistry.register(TraderEntities.TRADER) { c: EntityRendererFactory.Context? -> TraderRenderer(c) }
}
