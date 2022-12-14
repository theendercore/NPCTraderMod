package com.theendercore.npctrader.screen

import com.theendercore.npctrader.CURRENCY
import com.theendercore.npctrader.entity.TraderEntity
import com.theendercore.npctrader.trades.TradeList
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.InventoryScreen.drawEntity
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.util.NarratorManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class TraderScreen(private val trader: TraderEntity) : Screen(NarratorManager.EMPTY) {
    private val backgroundWidth = 250
    private val backgroundHeight = 100
    private val x: Int = (this.width - this.backgroundWidth) / 2
    private val y: Int =(this.height - this.backgroundHeight) / 2
    private val traderName: Text = trader.name
    private val trades: TradeList? = trader.trades
    private var closeButton: ButtonWidget? = null
    private var open = true
    private var mouseX = 0
    private var mouseY = 0


    override fun init() {

        client!!.keyboard.setRepeatEvents(true)

        closeButton = (addDrawableChild(
            ButtonWidget(width - (width / 8),
                (height / 8),
                20,
                20,
                Text.of("X"),
                { _: ButtonWidget? -> close() },
                { _: ButtonWidget, matrices: MatrixStack, x: Int, y: Int ->
                    val text = Text.of("Close the menu")
                    drawTextWithShadow(
                        matrices, textRenderer, text, x - ((text.toString().length / 2) * 3.5).toInt(), y + 20, 0x9f9f9f
                    )
                })
        ) as ButtonWidget)

        updateButtons()
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        this.mouseX = mouseX
        this.mouseY = mouseY

        drawCenteredText(matrices, textRenderer, traderName, width / 3, height / 8, 0xffffff)
        drawCenteredText(
            matrices,
            textRenderer,
            Text.of("" + this.client?.player?.let { CURRENCY.get(it).getValue() }),
            width / 3 + 40,
            height / 8 + 20,
            0xffffff
        )

        if (trades != null) {
            for (trade in trades) {
                val xOne = width / 4
                val yOne = height / 2 + ((trades.indexOf(trade) - 1) * 15)
                itemRenderer.renderInGuiWithOverrides(trade?.itemStack, xOne - 15, yOne - 5)
                drawCenteredText(
                    matrices,
                    textRenderer,
                    Text.translatable("npctrader.currency.symbol").append(" " + trade?.price.toString()),
                    xOne + 15,
                    yOne,
                    0x006400
                )

            }
        }

        //Don't remove this u fool
        super.render(matrices, mouseX, mouseY, delta)
    }

    override fun renderBackground(matrices: MatrixStack?) {
        super.renderBackground(matrices)


        drawEntity(
            width / 2 + 51,
            height / 2 + 75,
            40,
            (x + 51).toFloat() - mouseX,
            (y + 75 - 50).toFloat() - mouseY,
            this.trader
        )
    }

    private fun updateButtons() {
        closeButton!!.visible = open
    }
}