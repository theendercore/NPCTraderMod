package com.theendercore.npctrader.screen


import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.Selectable
import net.minecraft.client.gui.widget.ElementListWidget
import net.minecraft.client.util.Window
import net.minecraft.client.util.math.MatrixStack

class ShopMenu(client: MinecraftClient, width: Int, height: Int, top: Int, bottom: Int, itemHeight: Int) :
    ElementListWidget<ShopMenu.ShopEntry>(client, width, height, top, bottom, itemHeight) {
    init{

    }
    override fun render(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
        val window: Window = client!!.window
        val d: Double = window.scaleFactor
        RenderSystem.enableScissor(
            (top * d).toInt(),
            ((window.scaledHeight - bottom - 166) * d).toInt(),
            (256 / 2 * d).toInt(),
            (166 * d).toInt()
        )
        super.render(matrices, mouseX, mouseY, delta)
        RenderSystem.disableScissor()

    }

    class ShopEntry : Entry<ShopEntry>() {
        override fun render(
            matrices: MatrixStack?,
            index: Int,
            y: Int,
            x: Int,
            entryWidth: Int,
            entryHeight: Int,
            mouseX: Int,
            mouseY: Int,
            hovered: Boolean,
            tickDelta: Float,
        ) {
            TODO("Not yet implemented")
        }

        override fun children(): MutableList<out Element> {
            TODO("Not yet implemented")
        }

        override fun selectableChildren(): MutableList<out Selectable> {
            TODO("Not yet implemented")
        }
    }

}