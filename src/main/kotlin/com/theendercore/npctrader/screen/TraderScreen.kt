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
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class TraderScreen(private val trader: TraderEntity) : Screen(trader.name) {
    private val GUI_TEXTURE = id("textures/gui/trader.png")
    private val backgroundWidth = 256
    private val backgroundHeight = 166
    private var x = 0
    private var y = 0
    private val titleX = 6
    private val titleY = 6
    private val trades: TradeList? = TradeManager.getTrades(trader.type)
    private var closeButton: ButtonWidget? = null
    private var open = true
    private var mouseX = 0
    private var mouseY = 0


    override fun init() {
        x = (this.width - this.backgroundWidth) / 2
        y = (this.height - this.backgroundHeight) / 2
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

        textRenderer.draw(matrices, title, x + titleX.toFloat(), y + titleY.toFloat(), 4210752)
        textRenderer.draw(
            matrices,
            Text.of("" + this.client?.player?.let { CURRENCY.get(it).getValue() }),
            x + this.backgroundWidth.toFloat(),
            y + titleY.toFloat(),
            0xffffff
        )

        /*
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
                }*/

        //Don't remove this u fool
        super.render(matrices, mouseX, mouseY, delta)
    }

    override fun renderBackground(matrices: MatrixStack?) {
        super.renderBackground(matrices)

        drawEntity(
            x + 225,
            y + 165,
            55,
            (x + 225).toFloat() - mouseX,
            (y + 165 - 100).toFloat() - mouseY,
            this.trader
        )

        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, GUI_TEXTURE)

        val i = x
        val j = y
        this.drawTexture(matrices, i, j, 0, 0, backgroundWidth, backgroundHeight)


    }

    private fun updateButtons() {
        closeButton!!.visible = open
    }
}