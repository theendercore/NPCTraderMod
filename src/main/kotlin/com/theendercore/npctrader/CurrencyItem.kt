package com.theendercore.npctrader

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class CurrencyItem constructor(settings: FabricItemSettings) : Item(settings) {
    override fun inventoryTick(stack: ItemStack, world: World?, entity: Entity, slot: Int, selected: Boolean) {
        if (entity is PlayerEntity){
            CURRENCY.get(entity).addCurrency(stack.count.toLong())
            stack.decrement(stack.count)
        }
    }
}