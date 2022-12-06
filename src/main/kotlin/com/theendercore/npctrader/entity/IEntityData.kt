package com.theendercore.npctrader.entity

interface IEntityData {
    fun getCurrency(): Long
    fun addCurrency(amount: Long?)
    fun removeCurrency(amount: Long?)
}