package com.theendercore.npctrader.networking.packet.s2c

import com.theendercore.npctrader.entity.IEntityCurrency
import io.netty.buffer.Unpooled
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.network.Packet
import net.minecraft.network.PacketByteBuf
import net.minecraft.network.listener.ClientPlayPacketListener

class SyncCurrencyS2CPacket(val currency: Int) : Packet<ClientPlayPacketListener?> {

    constructor(buf: PacketByteBuf): this(buf.readInt())
    constructor(player: PlayerEntity?): this((player as IEntityCurrency).getCurrency())
    override fun write(buf: PacketByteBuf) {
        buf.writeInt(currency)
    }

    fun toBuf(): PacketByteBuf {
        val buf = PacketByteBuf(Unpooled.buffer())
        buf.writeInt(currency)
        return buf
    }
    override fun apply(listener: ClientPlayPacketListener?) {
    }
}