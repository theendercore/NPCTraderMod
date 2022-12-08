package com.theendercore.npctrader

import com.theendercore.npctrader.entity.IEntityCurrency
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class CurrencyItem constructor(settings: FabricItemSettings) : Item(settings) {
    override fun inventoryTick(stack: ItemStack, world: World?, entity: Entity, slot: Int, selected: Boolean) {
        (entity as IEntityCurrency).addCurrency((stack.count.toLong()))
        stack.decrement(stack.count)
    }
}