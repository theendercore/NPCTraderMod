package com.theendercore.npctrader.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.theendercore.npctrader.networking.NPCTraderNetworking
import com.theendercore.npctrader.trades.Trade
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.text.NumberFormat

@Environment(EnvType.CLIENT)
class TraderShopButton(
    x: Int, y: Int, private val trade: Trade,
    private val textRenderer: TextRenderer,
    private val itemRenderer: ItemRenderer,
) : ButtonWidget(x, y, 32, 32, trade.itemStack.name, { _: ButtonWidget ->
    RenderSystem.setShaderTexture(0, TraderScreen.GUI_TEXTURE)
    ClientPlayNetworking.send(NPCTraderNetworking.BUY_FROM_TRADER, trade.toBuf())
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
        RenderSystem.setShaderTexture(0, TraderScreen.GUI_TEXTURE)
        drawTexture(matrices, x, y, 0.0f, v, 32, 32, 256, 256)
    }
}