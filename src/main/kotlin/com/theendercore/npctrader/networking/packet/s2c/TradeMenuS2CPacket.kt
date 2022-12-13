package com.theendercore.npctrader.networking.packet.s2c

import io.netty.buffer.Unpooled
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.listener.ClientPlayPacketListener

class TradeMenuS2CPacket(val currency: Int = 69, val trader: Int) : Packet<ClientPlayPacketListener?> {

    constructor(buf: PacketByteBuf) : this(buf.readInt(), buf.readInt())

    override fun write(buf: PacketByteBuf) {
        buf.writeInt(currency); buf.writeInt(trader)
    }

    fun toBuf(): PacketByteBuf {
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeInt(currency)
        buf.writeInt(trader)
        return buf
    }

    override fun apply(listener: ClientPlayPacketListener?) {

    }
}