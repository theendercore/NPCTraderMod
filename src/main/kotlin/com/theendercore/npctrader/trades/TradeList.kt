package com.theendercore.npctrader.trades

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry


@Suppress("Unused")
class TradeList constructor(size: Int) : ArrayList<Trade?>(size) {
    fun toPacket(buf: PacketByteBuf) {
        buf.writeCollection( /* collection = */ this, /* writer = */ { buf2: PacketByteBuf, trade: Trade ->
            buf2.writeItemStack(trade.itemStack)
            buf2.writeInt(trade.price)
        } as ((PacketByteBuf, Trade?) -> Unit)?)
    }

    fun fromPacket(buf: PacketByteBuf): TradeList {
        return buf.readCollection({ size: Int -> TradeList(size) }) { buf2: PacketByteBuf ->
            val item = buf2.readItemStack()
            val l = buf2.readInt()
            Trade(item, l)
        } as TradeList
    }


    class Builder {
        private var trades: TradeList = TradeList(0)

        fun deserialize(jsonObj: JsonObject) {
            val tradeList: JsonArray = JsonHelper.getArray(jsonObj, "trades")
            for (trade in tradeList) {
                val tradeJsonObj = trade.asJsonObject
                val rawItemStack = JsonHelper.getString(tradeJsonObj, "item")
                val itemID = Identifier.tryParse(rawItemStack) ?: throw JsonParseException("Item Stack not Parable")
                val price = JsonHelper.getInt(tradeJsonObj, "price")
                trades.add(Trade(ItemStack(Registry.ITEM.get(itemID)), price))
            }
        }

        fun build() = trades.clone()
    }
}