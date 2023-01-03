package com.theendercore.npctrader.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.theendercore.npctrader.CURRENCY
import com.theendercore.npctrader.entity.TraderEntity
import com.theendercore.npctrader.id
import com.theendercore.npctrader.trades.TradeList
import com.theendercore.npctrader.trades.TradeManager
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.InventoryScreen.drawEntity
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.util.*


@Environment(EnvType.CLIENT)
class TraderScreen(private val trader: TraderEntity) : Screen(trader.name) {
    private val backgroundWidth = 256
    private val backgroundHeight = 166
    private var x = 0
    private var y = 0
    private val titleX = 90f
    private val titleY = 3f
    private var trades: TradeList? = TradeManager.getTrades(trader.type)
    private var tradeButtons: ArrayList<TraderShopButton> = ArrayList(0)
    private var closeButton: ButtonWidget? = null
    private var currencyBarWidget: CurrencyBarWidget? = null
    private var mouseX = 0
    private var mouseY = 0


    override fun init() {
        x = (this.width - this.backgroundWidth) / 2
        y = (this.height - this.backgroundHeight) / 2
        closeButton = (addDrawableChild(
            ButtonWidget(width - (width / 9),
                (height / 9),
                16,
                16,
                Text.of("X"),
                { _: ButtonWidget? -> close() },
                { _: ButtonWidget, matrices: MatrixStack, x: Int, y: Int ->
                    drawCenteredTextWithShadow(
                        matrices, textRenderer, Text.of("Close the menu").asOrderedText(), x, y + 20, 0x9f9f9f
                    )
                })
        ) as ButtonWidget)
        var j = 0
        var i = 0
        tradeButtons = ArrayList(0)
        for (trade in trades!!) {
            if (i > 4) {
                j++
                i = 0
            }
            tradeButtons.add(
                addDrawableChild(
                    TraderShopButton(
                        x + 5 + i * 35, y + 17 + j * 35, trade!!, client!!, itemRenderer
                    )
                )
            )
            i++
        }
        currencyBarWidget = CurrencyBarWidget(x + backgroundWidth, y, client!!, CURRENCY[client!!.player!!].getValue(), this)

    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        this.mouseX = mouseX
        this.mouseY = mouseY

        textRenderer.draw(
            matrices,
            Text.translatable("npctrader.trader.shop"),
            x + titleX,
            y + titleY,
            0x9f9f9f
        )

        matrices.translate(0.0, 0.0, (zOffset + 200.0f).toDouble())
        textRenderer.draw(matrices, title, x + 198f, y + 124.5f, 0x9f9f9f)
        matrices.translate(0.0, 0.0, (zOffset - 200.0f).toDouble())

        currencyBarWidget?.render(matrices, mouseX, mouseY, delta)

        //Don't remove this u fool
        super.render(matrices, mouseX, mouseY, delta)
    }

    override fun renderBackground(matrices: MatrixStack) {
        super.renderBackground(matrices)
        drawEntity(
            x + 210, y + 165, 55, (x + 210).toFloat() - mouseX, (y + 165 - 100).toFloat() - mouseY, this.trader
        )
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, GUI_TEXTURE)

        this.drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }

    override fun shouldPause(): Boolean {
        return false
    }

    companion object {
        val GUI_TEXTURE = id("textures/gui/trader.png")
    }


}