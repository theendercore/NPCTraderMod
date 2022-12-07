package com.theendercore.npctrader.trades

import net.minecraft.network.PacketByteBuf

@Suppress( "Unused")
class EntityTradeList private constructor(size: Int) : ArrayList<EntityTrade?>(size) {
    fun toPacket(buf: PacketByteBuf) {
        buf.writeCollection( /* collection = */ this, /* writer = */ { buf2: PacketByteBuf, trade: EntityTrade ->
            buf2.writeItemStack(trade.item)
            buf2.writeLong(trade.price)
        } as ((PacketByteBuf, EntityTrade?) -> Unit)?)
    }

    companion object {
        fun fromPacket(buf: PacketByteBuf): EntityTradeList {
            return buf.readCollection({ size: Int -> EntityTradeList(size) }) { buf2: PacketByteBuf ->
                val item = buf2.readItemStack()
                val l = buf2.readLong()
                EntityTrade(item, l)
            } as EntityTradeList
        }
    }
}