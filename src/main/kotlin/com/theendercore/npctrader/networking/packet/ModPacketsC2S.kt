package com.theendercore.npctrader.networking.packet

import com.theendercore.npctrader.entity.IEntityCurrency
import com.theendercore.npctrader.entity.TraderEntity
import com.theendercore.npctrader.networking.packet.s2c.SyncCurrencyS2CPacket
import com.theendercore.npctrader.networking.packet.s2c.TradeMenuS2CPacket
import com.theendercore.npctrader.screen.TraderScreen
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.network.PacketByteBuf

object ModPacketsC2S {
    fun register() {
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.START_TRADE) { c: MinecraftClient, h: ClientPlayNetworkHandler, buf: PacketByteBuf, send: PacketSender ->
            startTrade(c, buf)
        }
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.SYNC_CURRENCY) { c: MinecraftClient, h: ClientPlayNetworkHandler, buf: PacketByteBuf, send: PacketSender ->
            syncCurrency(c, buf)
        }
    }

    private fun startTrade(client: MinecraftClient, buf: PacketByteBuf) {
        val traderMenuPacket = TradeMenuS2CPacket(buf)
        client.execute {
            client.setScreen(
                TraderScreen(
                    client.world?.getEntityById(traderMenuPacket.trader) as TraderEntity,
                    traderMenuPacket.currency
                )
            )
        }
    }

    private fun syncCurrency(client: MinecraftClient, buf: PacketByteBuf) {
        val syncCurrPack = SyncCurrencyS2CPacket(buf)
        client.execute {
            val player = client.player
            if (player != null){
                (player as IEntityCurrency).setCurrency(syncCurrPack.currency)
            }
        }
    }
}