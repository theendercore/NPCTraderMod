package com.theendercore.npctrader.screen

import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

object CurrencyTextRenderer {
    fun render(matrices: MatrixStack, client: MinecraftClient, currency: Long, x: Int, y: Int, color: Int) {
        render(matrices, client, currency, x, y, color, false)
    }

    fun render(
        matrices: MatrixStack, client: MinecraftClient,
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
            in 1000..999999 -> currencyString.dropLast(3) + "," + currencyString.dropLast(2).last() + "K"
            in 1000000..999999999 -> currencyString.dropLast(6) + "," + currencyString.dropLast(5).last() + "M"
            else -> currencyString.dropLast(9) + "," + currencyString.dropLast(8).last() + "B"
        }

        var i: Float = (x + (5 - toRender.length) * 4).toFloat()

        if (renderCurrency) {
            added.append(" ")
            added.append(Text.translatable("npctrader.currency.symbol"))
            i = x.toFloat()
        }
        client.textRenderer.draw(
            matrices,
            Text.empty().append(toRender).append(added),
            i,
            y.toFloat(),
            color
        )
    }

}