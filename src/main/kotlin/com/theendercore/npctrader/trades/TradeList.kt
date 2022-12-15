package com.theendercore.npctrader.trades

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry


@Suppress("Unused")
class TradeList constructor(size: Int) : ArrayList<Trade?>(size) {
    class Builder {
        private var trades: TradeList = TradeList(0)

        fun deserialize(jsonObj: JsonObject) {
            val tradeList: JsonArray = JsonHelper.getArray(jsonObj, "trades")
            for (trade in tradeList) {
                val tradeJsonObj = trade.asJsonObject
                val rawItemStack = JsonHelper.getString(tradeJsonObj, "item")
                val itemID = Identifier.tryParse(rawItemStack) ?: throw JsonParseException("Item Stack not Parable")
                val price = JsonHelper.getLong(tradeJsonObj, "price")
                trades.add(
                    Trade(ItemStack(Registry.ITEM.get(itemID)), price)
                )
            }
        }

        fun build() = trades.clone()
    }

}
