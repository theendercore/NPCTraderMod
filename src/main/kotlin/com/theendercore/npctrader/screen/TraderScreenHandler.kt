//package com.theendercore.npctrader.screen
//
//import com.theendercore.npctrader.TRADER_SCREEN_HANDLER
//import com.theendercore.npctrader.entity.TraderEntity
//import net.minecraft.entity.player.PlayerEntity
//import net.minecraft.entity.player.PlayerInventory
//import net.minecraft.item.ItemStack
//import net.minecraft.screen.ScreenHandler
//
//class TraderScreenHandler(syncId: Int, player: PlayerEntity?, trader: TraderEntity?) : ScreenHandler(TRADER_SCREEN_HANDLER, syncId) {
//    constructor(syncId: Int, playerInventory: PlayerInventory?) : this(syncId, playerInventory?.player, null)
//
//    override fun transferSlot(player: PlayerEntity, index: Int): ItemStack? {
//        return null
//    }
//
//    override fun canUse(player: PlayerEntity): Boolean {
//        return false
//    }
//}