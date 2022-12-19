package com.theendercore.npctrader.networking

import com.theendercore.npctrader.CURRENCY
import com.theendercore.npctrader.id
import com.theendercore.npctrader.trades.Trade
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.MinecraftServer
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.Identifier

@Suppress("unused")
object NPCTraderNetworking {
    val BUY_FROM_TRADER: Identifier = id("start_trade")

    fun registerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(BUY_FROM_TRADER) { _: MinecraftServer, player: ServerPlayerEntity, handler: ServerPlayNetworkHandler, buf: PacketByteBuf, _: PacketSender ->
            val trade = Trade(buf)

            if(!CURRENCY.get(player).tryRemove(trade.price)){
                player.sendMessage(Text.of("not enough money"))
                return@registerGlobalReceiver
            }
            player.giveItemStack(trade.itemStack)
        }
    }

    fun registerS2C() {
//        ClientPlayNetworking.registerGlobalReceiver(START_TRADE) { _: MinecraftClient, _: ClientPlayNetworkHandler, _: PacketByteBuf, _: PacketSender ->
//        }
    }
}