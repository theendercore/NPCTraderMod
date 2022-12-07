package com.theendercore.npctrader.entity

import com.theendercore.npctrader.trades.EntityTradeList
import net.minecraft.text.Text

interface IPlayerTradeWithNPC {
    fun tradeWithNPC(name: Text?, trades: EntityTradeList?)
}