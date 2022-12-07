package com.theendercore.npctrader.entity

interface IEntityCurrency {
    fun getCurrency(): Long
    fun addCurrency(amount: Long?)
    fun removeCurrency(amount: Long?)
}