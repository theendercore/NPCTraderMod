package com.theendercore.npctrader.trades

import io.netty.buffer.Unpooled
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf

class Trade(val itemStack: ItemStack, val price: Long){

    constructor(buf: PacketByteBuf): this(buf.readItemStack(), buf.readLong())
    fun toBuf(): PacketByteBuf {
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeItemStack(itemStack)
        buf.writeLong(price)
        return buf
    }

}


