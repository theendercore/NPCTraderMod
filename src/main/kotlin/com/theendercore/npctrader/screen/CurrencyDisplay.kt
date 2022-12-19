package com.theendercore.npctrader.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.theendercore.npctrader.CURRENCY_ITEM
import com.theendercore.npctrader.id
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

object CurrencyDisplay {
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
        screen!!.drawTexture(matrices, x , y, 0, 0, 40, 16)

        itemRenderer.renderGuiItemIcon(CURRENCY_ITEM.defaultStack, x, y)
        textRenderer.draw(
            matrices,
            Text.of("" + currency),
            (x + 17).toFloat(),
            (y + 4).toFloat(),
            0x006400
        )
    }
}