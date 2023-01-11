package com.theendercore.npctrader.screen

import com.mojang.blaze3d.systems.RenderSystem
import com.theendercore.npctrader.networking.NPCTraderNetworking
import com.theendercore.npctrader.trades.Trade
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.util.math.MatrixStack

@Environment(EnvType.CLIENT)
class ShopButton(
    x: Int, y: Int, private val trade: Trade,
    private val client: MinecraftClient
) : ButtonWidget(x, y, 32, 32, trade.itemStack.name, {
    ClientPlayNetworking.send(NPCTraderNetworking.BUY_FROM_TRADER, trade.toBuf())
}) {

    override fun renderButton(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        val v = if (active && this.isHovered) 198f else 166f

        this.drawBackground(matrices, v)

        client.itemRenderer.renderGuiItemIcon(trade.itemStack, x + 8, y + 8)
        matrices.translate(0.0, 0.0, (zOffset + 200.0f).toDouble())
        CurrencyTextRenderer.render(matrices, client, trade.price, x + 2, y + 23, 0xffffff, true)
        matrices.translate(0.0, 0.0, (zOffset - 200.0f).toDouble())
    }

    override fun appendNarrations(builder: NarrationMessageBuilder) {}
    private fun drawBackground(matrices: MatrixStack, v: Float) {
        RenderSystem.setShaderTexture(0, TraderScreen.GUI_TEXTURE)
        drawTexture(matrices, x, y, 0.0f, v, 32, 32, 256, 256)
    }
}