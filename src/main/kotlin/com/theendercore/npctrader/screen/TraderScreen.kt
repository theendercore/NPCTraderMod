package com.theendercore.npctrader.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.theendercore.npctrader.CURRENCY
import com.theendercore.npctrader.entity.TraderEntity
import com.theendercore.npctrader.id
import com.theendercore.npctrader.networking.NPCTraderNetworking.BUY_FROM_TRADER
import com.theendercore.npctrader.screen.CurrencyBarDisplay.render
import com.theendercore.npctrader.trades.Trade
import com.theendercore.npctrader.trades.TradeList
import com.theendercore.npctrader.trades.TradeManager
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ingame.InventoryScreen.drawEntity
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.text.NumberFormat
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
    private var tradeButtons: ArrayList<ShopButtonWidget> = ArrayList(0)
    private var closeButton: ButtonWidget? = null
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
                    ShopButtonWidget(
                        x + 5 + i * 35, y + 17 + j * 35, trade!!, textRenderer, itemRenderer
                    )
                )
            )
            i++
        }

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

        render(
            matrices,
            itemRenderer, textRenderer, CURRENCY[client!!.player!!].getValue(), x + backgroundWidth, y, this
        )

        //Don't remove this u fool
        super.render(matrices, mouseX, mouseY, delta)
    }

    override fun renderBackground(matrices: MatrixStack) {
        super.renderBackground(matrices)
        drawEntity(
            x + 225, y + 165, 55, (x + 225).toFloat() - mouseX, (y + 165 - 100).toFloat() - mouseY, this.trader
        )
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, GUI_TEXTURE)

        this.drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight)
    }


    @Environment(EnvType.CLIENT)
    class ShopButtonWidget(
        x: Int, y: Int, private val trade: Trade,
        private val textRenderer: TextRenderer,
        private val itemRenderer: ItemRenderer,
    ) : ButtonWidget(x, y, 32, 32, trade.itemStack.name, { _: ButtonWidget ->
        RenderSystem.setShaderTexture(0, GUI_TEXTURE)
        ClientPlayNetworking.send(BUY_FROM_TRADER, trade.toBuf())
    }, { _: ButtonWidget, matrices: MatrixStack, _: Int, _: Int ->
        textRenderer.draw(
            matrices,
            Text.of(NumberFormat.getIntegerInstance().format(100000000)),
            x.toFloat(),
            y.toFloat(),
            0x9f9f9f
        )
    }) {
        override fun renderButton(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
            val v = if (active && this.isHovered) 198f else 166f

            this.drawBackground(matrices, v)

            itemRenderer.renderGuiItemIcon(trade.itemStack, x + 8, y + 8)
            matrices.translate(0.0, 0.0, (zOffset + 200.0f).toDouble())
            CurrencyTextRenderer.render(matrices, textRenderer, trade.price, x + 2, y + 23, 0xffffff, true)
            matrices.translate(0.0, 0.0, (zOffset - 200.0f).toDouble())
        }

        override fun appendNarrations(builder: NarrationMessageBuilder) {}
        private fun drawBackground(matrices: MatrixStack, v: Float) {
            RenderSystem.setShaderTexture(0, GUI_TEXTURE)
            drawTexture(matrices, x, y, 0.0f, v, 32, 32, 256, 256)
        }
    }

    companion object {
        val GUI_TEXTURE = id("textures/gui/trader.png")
    }

}