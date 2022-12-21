package com.theendercore.npctrader.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.theendercore.npctrader.CURRENCY_ITEM
import com.theendercore.npctrader.id
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack

object CurrencyBarDisplay {
    val CURRENCY_BACKGROUND = id("textures/gui/currency.png")
    fun render(
        matrices: MatrixStack,
        itemRenderer: ItemRenderer,
        textRenderer: TextRenderer,
        currency: Long,
        x: Int,
        y: Int,
        screen: Screen?,
    ) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, CURRENCY_BACKGROUND)
        screen!!.drawTexture(matrices, x, y, 0, 0, 40, 16)

        itemRenderer.renderGuiItemIcon(CURRENCY_ITEM.defaultStack, x, y)
        CurrencyTextRenderer.render(matrices, textRenderer, currency, (x + 17), (y + 4), 0x006400)
    }
}