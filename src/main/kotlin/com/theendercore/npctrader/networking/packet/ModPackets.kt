package com.theendercore.npctrader.networking.packet

import com.theendercore.npctrader.id
import net.minecraft.util.Identifier

object ModPackets {
    val START_TRADE: Identifier = id("start_trade")
    val SYNC_CURRENCY: Identifier = id("sync_currency")


}