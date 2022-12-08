package com.theendercore.npctrader.entity

import com.theendercore.npctrader.trades.TradeList
import net.minecraft.text.Text

interface IPlayerTradeWithNPC {
    fun tradeWithNPC(name: Text?, trades: TradeList?)
}