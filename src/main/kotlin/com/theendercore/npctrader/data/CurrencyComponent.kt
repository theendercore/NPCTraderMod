package com.theendercore.npctrader.data

import com.theendercore.npctrader.CURRENCY
import dev.onyxstudios.cca.api.v3.component.Component
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound

class CurrencyComponent(private val provider: PlayerEntity) : Component, AutoSyncedComponent {
    private var currency: Long = 0

    override fun readFromNbt(tag: NbtCompound) {
        currency = tag.getLong("Currency")
    }

    override fun writeToNbt(tag: NbtCompound) {
        tag.putLong("Currency", currency)
    }

    fun getValue(): Long {
        return currency
    }

    private fun set(currency: Long) {
        this.currency = currency

        //Update Client
        if (!provider.world.isClient) {
            CURRENCY.sync(provider)
        }
    }
    fun add(currency: Long) {
        set(this.currency+currency)
    }
    fun remove(currency: Long) {
        set(this.currency-currency)
    }
}