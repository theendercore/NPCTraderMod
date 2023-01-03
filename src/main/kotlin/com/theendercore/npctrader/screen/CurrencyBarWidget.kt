package com.theendercore.npctrader.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.theendercore.npctrader.id
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.Drawable
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.Selectable
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

class CurrencyBarWidget(
    private val x: Int,
    private val y: Int,
    val client: MinecraftClient,
    val currency: Long,
    val screen: Screen,
) :
    DrawableHelper(), Drawable, Element, Selectable {

    private val BACKGROUND = id("textures/gui/currency.png")
    private val width: Int = 64
    private val height: Int = 16
    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, BACKGROUND)
        drawTexture(matrices, x, y, 0, 0, width, height)
        CurrencyTextRenderer.render(matrices, client, currency, (x + 17), (y + 4), 0x006400)
        if (isHovered(mouseX, mouseY)) {
            renderTooltip(matrices)
        }

    }

    override fun appendNarrations(builder: NarrationMessageBuilder?) {}

    override fun getType(): Selectable.SelectionType {
        return Selectable.SelectionType.FOCUSED
    }

    private fun renderTooltip(matrices: MatrixStack?) {
        screen.renderTooltip(matrices, Text.of(currency.toString()), x, y + height + 10)
    }

    private fun isHovered(mouseX: Int, mouseY: Int): Boolean {
        return (mouseX >= x && mouseY >= y && mouseX < (x + width) && mouseY < (y + height))
    }
}