package com.theendercore.npctrader.screen

import com.theendercore.npctrader.trades.TradeList
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.util.NarratorManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class TraderScreen constructor(private val traderName: Text, private val trades: TradeList) :
    Screen(NarratorManager.EMPTY){
    private var closeButton: ButtonWidget? = null
    private var open = true

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
                        matrices, textRenderer, text, x - ((text.toString().length/2)* 3.5).toInt(), y + 20, 0x9f9f9f
                    )
                })
        ) as ButtonWidget)

        updateButtons()
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        drawCenteredText(matrices, textRenderer, traderName, width / 3, height / 8, 0xffffff)
        for (trade in trades) {
            val xOne = width / 4
            val yOne = height / 2 + ((trades.indexOf(trade) - 1) * 15)
            itemRenderer.renderInGuiWithOverrides(trade?.itemStack, xOne-15, yOne-5)
            drawCenteredText(
                matrices,
                textRenderer,
                Text.translatable("npctrader.currency.symbol").append(" " + trade?.price.toString()),
                xOne+15,
                yOne,
                0x0fffff
            )

        }

        //Don't remove this u fool
        super.render(matrices, mouseX, mouseY, delta)
    }

    private fun updateButtons() {
        closeButton!!.visible = open
    }
}