package com.theendercore.npctrader.entity.client

import com.theendercore.npctrader.entity.TraderEntity
import com.theendercore.npctrader.id
import net.minecraft.util.Identifier
import software.bernie.geckolib3.core.event.predicate.AnimationEvent
import software.bernie.geckolib3.model.AnimatedGeoModel
import software.bernie.geckolib3.model.provider.data.EntityModelData

class TraderModel : AnimatedGeoModel<TraderEntity?>() {
    override fun getModelResource(instance: TraderEntity?): Identifier {
        return id("geo/butcher.geo.json")
    }

    override fun getTextureResource(instance: TraderEntity?): Identifier {
        return id("textures/entity/butcher.png")
    }

    override fun getAnimationResource(animatable: TraderEntity?): Identifier {
        return id("animations/ultimate.animation.json")
    }

    override fun setCustomAnimations(entity: TraderEntity?, instanceId: Int, customPredicate: AnimationEvent<*>) {
        super.setCustomAnimations(entity, instanceId, customPredicate)
        val head = animationProcessor.getBone("Head")
        val extraData = customPredicate.getExtraDataOfType(EntityModelData::class.java)[0] as EntityModelData
        if (head != null) {
            head.rotationX = extraData.headPitch * (Math.PI.toFloat() / 180f)
            head.rotationY = extraData.netHeadYaw * (Math.PI.toFloat() / 180f)
        }
    }

}