package com.theendercore.npctrader.screen

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

object CurrencyTextRenderer {
    fun render(matrices: MatrixStack, textRenderer: TextRenderer, currency: Long, x: Int, y: Int, color: Int) {
        render(matrices, textRenderer, currency, x, y, color, false)
    }

    fun render(
        matrices: MatrixStack, textRenderer: TextRenderer,
        currency: Long, x: Int, y: Int, color: Int, renderCurrency: Boolean,
    ) {
        var toRender = ""
        val added = Text.empty()
        val currencyString = currency.toString()
        if (currency < 1000) {
            toRender += currency
        }
        toRender = when (currency) {
            in 0..999 -> currencyString
            in 1000..999999 -> currencyString.dropLast(3) + "K"
            in 1000000..999999999 -> currencyString.dropLast(6) + "M"
            else -> currencyString.dropLast(9) + "B"
        }
        if (renderCurrency) {
            added.append(" ")
            added.append(Text.translatable("npctrader.currency.symbol"))
        }
        textRenderer.draw(matrices, Text.empty().append(toRender).append(added), x.toFloat(), y.toFloat(), color)
    }

}