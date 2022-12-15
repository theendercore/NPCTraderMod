package com.theendercore.npctrader.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.theendercore.npctrader.CURRENCY
import com.theendercore.npctrader.LOGGER
import com.theendercore.npctrader.entity.TraderEntity
import com.theendercore.npctrader.id
import com.theendercore.npctrader.trades.Trade
import com.theendercore.npctrader.trades.TradeList
import com.theendercore.npctrader.trades.TradeManager
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.InventoryScreen.drawEntity
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.util.*


@Environment(EnvType.CLIENT)
class TraderScreen(private val trader: TraderEntity) : Screen(trader.name) {
    private val backgroundWidth = 256
    private val backgroundHeight = 166
    private var x = 0
    private var y = 0
    private val titleX = 6
    private val titleY = 6
    private var trades: TradeList? = TradeManager.getTrades(trader.type)
    private val tradeButtons: ArrayList<ShopButtonWidget> = ArrayList(0)
    private var closeButton: ButtonWidget? = null
    private var mouseX = 0
    private var mouseY = 0


    override fun init() {
        x = (this.width - this.backgroundWidth) / 2
        y = (this.height - this.backgroundHeight) / 2
        closeButton = (addDrawableChild(
            ButtonWidget(width - (width / 9),
                (height / 9),
                20,
                20,
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
        for (trade in trades!!) {
            if (i > 4) {
                j++
                i = 0
            }
            tradeButtons.add(
                ShopButtonWidget(
                    x + 6 + i * 25, y + 15 + j * 25, trade!!, textRenderer, itemRenderer
                )
            )
            i++
        }

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

        for (button in tradeButtons) {
            button.render(matrices, mouseX, mouseY, delta)
        }

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
            x + 225, y + 165, 55, (x + 225).toFloat() - mouseX, (y + 165 - 100).toFloat() - mouseY, this.trader
        )

        RenderSystem.setShader { GameRenderer.getPositionTexShader() }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, GUI_TEXTURE)

        val i = x
        val j = y
        this.drawTexture(matrices, i, j, 0, 0, backgroundWidth, backgroundHeight)


    }


    @Environment(EnvType.CLIENT)
    class ShopButtonWidget(
        x: Int,
        y: Int,
        private val trade: Trade,
        private val textRenderer: TextRenderer,
        private val itemRenderer: ItemRenderer,
    ) : ButtonWidget(x, y, 25, 25, trade.itemStack.name, { _: ButtonWidget? ->
        LOGGER.info("click")
    }) {
        override fun renderButton(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
            if (matrices != null) {
                if (isHovered) {
                    drawSelectionBox(matrices)
                } else {
                    this.drawBackground(matrices)
                }

                itemRenderer.renderGuiItemIcon(trade.itemStack, x + 3, y + 3)
                textRenderer.draw(
                    matrices, Text.of(trade.price.toString() + " $"), (x + 3).toFloat(), (y + 10).toFloat(), 0xffffff
                )

            }

        }
        override fun appendNarrations(builder: NarrationMessageBuilder) {}
        private fun drawBackground(matrices: MatrixStack) {
            RenderSystem.setShader { GameRenderer.getPositionTexShader() }
            RenderSystem.setShaderTexture(0, GUI_TEXTURE)
            matrices.push()
            matrices.translate(x.toDouble(), y.toDouble(), 0.0)
            drawTexture(matrices, 0, 0, 0.0f, 166.0f, 25, 25, 256, 256)
            matrices.pop()
        }

        private fun drawSelectionBox(matrices: MatrixStack) {
            RenderSystem.setShader { GameRenderer.getPositionTexShader() }
            RenderSystem.setShaderTexture(0, GUI_TEXTURE)
            matrices.push()
            matrices.translate(x.toDouble(), y.toDouble(), 0.0)
            drawTexture(matrices, 0, 0, 0.0f, 216.0f, 25, 25, 256, 256)
            matrices.pop()
        }
    }

    companion object {
        val GUI_TEXTURE = id("textures/gui/trader.png")
    }

}