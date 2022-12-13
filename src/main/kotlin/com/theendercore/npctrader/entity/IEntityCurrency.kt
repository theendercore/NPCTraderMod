package com.theendercore.npctrader.entity

interface IEntityCurrency {
    fun getCurrency(): Int
    fun addCurrency(amount: Int)
    fun removeCurrency(amount: Int)
    fun setCurrency(amount: Int)
}