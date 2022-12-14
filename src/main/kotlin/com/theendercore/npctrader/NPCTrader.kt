package com.theendercore.npctrader

import com.theendercore.npctrader.data.CurrencyComponent
import com.theendercore.npctrader.entity.*
import com.theendercore.npctrader.entity.client.TraderRenderer
import com.theendercore.npctrader.trades.TradeManager
import dev.onyxstudios.cca.api.v3.component.ComponentKey
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemGroup
import net.minecraft.item.SpawnEggItem
import net.minecraft.resource.ResourceType
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
val BUTCHER_SPAWN_EGG =
    SpawnEggItem(TraderEntities.BUTCHER, 0xffffff, 0xff0000, FabricItemSettings().group(ItemGroup.MISC).maxCount(3))

@JvmField
val CURRENCY_ITEM = CurrencyItem(FabricItemSettings().group(ItemGroup.MISC))


@JvmField
val CURRENCY: ComponentKey<CurrencyComponent> = ComponentRegistry.getOrCreate(
    id("currency"),
    CurrencyComponent::class.java
)

@Suppress("unused")
fun onInitialize() {
    LOGGER.info("main init!")

    GeckoLib.initialize()
    Registry.register(Registry.ITEM, id("butcher_spawn_egg"), BUTCHER_SPAWN_EGG)
    Registry.register(Registry.ITEM, id("currency"), CURRENCY_ITEM)

    FabricDefaultAttributeRegistry.register(TraderEntities.BUTCHER, TraderEntity.setAttributes())
    TradeManager.registerTrader(TraderEntities.BUTCHER)
    ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(TradeManager)
}


@Suppress("unused")
fun onClientInitialize() {
    LOGGER.info(":gun: Client")
    EntityRendererRegistry.register(TraderEntities.BUTCHER) { c: EntityRendererFactory.Context? -> TraderRenderer(c) }
}


@Suppress("unused")
fun registerEntityComponentFactories(registry: EntityComponentFactoryRegistry) {
    registry.registerForPlayers(CURRENCY, { p: PlayerEntity -> CurrencyComponent(p) }, RespawnCopyStrategy.ALWAYS_COPY)
}

