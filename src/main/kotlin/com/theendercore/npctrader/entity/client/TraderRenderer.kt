package com.theendercore.npctrader.entity.client

import com.theendercore.npctrader.entity.TraderEntity
import com.theendercore.npctrader.id
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.util.Identifier
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer

class TraderRenderer(renderManager: EntityRendererFactory.Context?) :
    GeoEntityRenderer<TraderEntity?>(renderManager, TraderModel()) {

    override fun getTextureResource(instance: TraderEntity?): Identifier {
        return id("textures/entity/butcher.png")
    }
}