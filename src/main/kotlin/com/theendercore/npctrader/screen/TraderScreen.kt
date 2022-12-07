package com.theendercore.npctrader.screen

import com.theendercore.npctrader.trades.EntityTradeList
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.util.NarratorManager
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text

@Environment(EnvType.CLIENT)
class TraderScreen constructor(private val traderName: Text, private val trades: EntityTradeList) : Screen(NarratorManager.EMPTY) {
    private var closeButton: ButtonWidget? = null
    private var open = true

    override fun init() {

        client!!.keyboard.setRepeatEvents(true)

        closeButton = (addDrawableChild(
            ButtonWidget(
                width - (width/8),
                (height/8),
                20,
                20,
                Text.of("X")
            ) { _: ButtonWidget? -> close() }) as ButtonWidget)

        updateButtons()
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        drawCenteredText(matrices, textRenderer, traderName, width / 3, height/8, 0xffffff)
        for( x in trades){
            drawCenteredText(matrices, textRenderer, Text.of(x?.price.toString()), width / 2, height/2, 0x0fffff)
        }

        //Don't remove this u fool
        super.render(matrices, mouseX, mouseY, delta)
    }

    private fun updateButtons() {
        closeButton!!.visible = open
    }
}