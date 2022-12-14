package com.theendercore.npctrader.networking.packet

import com.theendercore.npctrader.id
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier

@Suppress("unused")
object NPCTraderNetworking {
    val START_TRADE: Identifier = id("start_trade")

    fun registerC2S() {
        ClientPlayNetworking.registerGlobalReceiver(START_TRADE) { _: MinecraftClient, _: ClientPlayNetworkHandler, _: PacketByteBuf, _: PacketSender ->
        }
    }
}