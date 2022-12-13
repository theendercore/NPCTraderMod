package com.theendercore.npctrader

import com.theendercore.npctrader.entity.*
import com.theendercore.npctrader.entity.client.TraderRenderer
import com.theendercore.npctrader.networking.packet.ModPackets
import com.theendercore.npctrader.networking.packet.ModPacketsC2S
import com.theendercore.npctrader.networking.packet.s2c.SyncCurrencyS2CPacket
import com.theendercore.npctrader.trades.TradeManager
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.resource.ResourceManagerHelper
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.item.ItemGroup
import net.minecraft.item.SpawnEggItem
import net.minecraft.network.PacketByteBuf
import net.minecraft.resource.ResourceType
import net.minecraft.server.network.ServerPlayerEntity
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
val CURRENCY = CurrencyItem(FabricItemSettings().group(ItemGroup.MISC))


@Suppress("unused")
fun onInitialize() {
    LOGGER.info("main init!")

    GeckoLib.initialize()
    Registry.register(Registry.ITEM, id("butcher_spawn_egg"), BUTCHER_SPAWN_EGG)
    Registry.register(Registry.ITEM, id("currency"), CURRENCY)

    FabricDefaultAttributeRegistry.register(TraderEntities.BUTCHER, TraderEntity.setAttributes())
    TradeManager.registerTrader(TraderEntities.BUTCHER)
    ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(TradeManager)

    ServerTickEvents.END_SERVER_TICK.register {
        sync(it.playerManager.playerList)
    }
}


@Suppress("unused")
fun onClientInitialize() {
    LOGGER.info(":gun: Client")
    EntityRendererRegistry.register(TraderEntities.BUTCHER) { c: EntityRendererFactory.Context? -> TraderRenderer(c) }

    ModPacketsC2S.register()
}

fun sync(players: List<ServerPlayerEntity?>) {
    players.forEach { player: ServerPlayerEntity? ->
        ServerPlayNetworking.send(
            player, ModPackets.SYNC_CURRENCY, PacketByteBuf(SyncCurrencyS2CPacket(player).toBuf())
        )
    }
}
