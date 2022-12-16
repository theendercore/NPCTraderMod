package com.theendercore.npctrader.networking

import com.theendercore.npctrader.CURRENCY
import com.theendercore.npctrader.LOGGER
import com.theendercore.npctrader.id
import com.theendercore.npctrader.trades.Trade
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Identifier

@Suppress("unused")
object NPCTraderNetworking {
    val BUY_FROM_TRADER: Identifier = id("start_trade")

    fun registerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(BUY_FROM_TRADER) { _: MinecraftServer, player: ServerPlayerEntity, _: ServerPlayNetworkHandler, buf: PacketByteBuf, _: PacketSender ->
            val trade = Trade(buf)
            LOGGER.info("Packet received : " + trade.itemStack.name)
            player.giveItemStack(trade.itemStack)
            CURRENCY.get(player).remove(trade.price)
            player.playSound(SoundEvents.ENTITY_VILLAGER_TRADE, 1f, 1f)
        }
    }

    fun registerS2C() {
//        ClientPlayNetworking.registerGlobalReceiver(START_TRADE) { _: MinecraftClient, _: ClientPlayNetworkHandler, _: PacketByteBuf, _: PacketSender ->
//        }
    }
}