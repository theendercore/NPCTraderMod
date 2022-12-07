package com.theendercore.npctrader.entity

import net.minecraft.block.BlockState
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import software.bernie.geckolib3.core.IAnimatable
import software.bernie.geckolib3.core.PlayState
import software.bernie.geckolib3.core.builder.AnimationBuilder
import software.bernie.geckolib3.core.builder.ILoopType
import software.bernie.geckolib3.core.controller.AnimationController
import software.bernie.geckolib3.core.event.predicate.AnimationEvent
import software.bernie.geckolib3.core.manager.AnimationData
import software.bernie.geckolib3.core.manager.AnimationFactory
import java.util.*

@Suppress("DEPRECATION")
class TraderEntity constructor(entityType: EntityType<out PassiveEntity?>?, world: World?, profession: String) :
    PassiveEntity(entityType, world), IAnimatable {

    private val aFactory = AnimationFactory(this)
    private val name: Text = Text.translatable("npctrader.trader.$profession.name")


    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(1, WanderAroundPointOfInterestGoal(this, 1.0, true))
        goalSelector.add(2, WanderAroundFarGoal(this, 1.0))
        goalSelector.add(3, LookAroundGoal(this))
        goalSelector.add(4, LookAtEntityGoal(this, PlayerEntity::class.java, 8F))
    }

    override fun createChild(world: ServerWorld, entity: PassiveEntity): PassiveEntity? {

        return null
    }

    private fun <E : IAnimatable?> predicate(event: AnimationEvent<E>): PlayState {
        if (event.isMoving) {
            event.controller.setAnimation(AnimationBuilder().addAnimation("animation.trader.walk",
                ILoopType.EDefaultLoopTypes.LOOP as ILoopType))
            return PlayState.CONTINUE
        }
        event.controller.setAnimation(AnimationBuilder().clearAnimations())
        return PlayState.CONTINUE
    }

    override fun registerControllers(animationData: AnimationData) {
        animationData.addAnimationController(AnimationController(this, "controller", 0F, this::predicate))
    }

    override fun getFactory(): AnimationFactory {
        return this.aFactory
    }

    override fun getAmbientSound(): SoundEvent? {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT
    }

    override fun getHurtSound(source: DamageSource?): SoundEvent? {
        return SoundEvents.ENTITY_VILLAGER_HURT
    }

    override fun getDeathSound(): SoundEvent? {
        return SoundEvents.ENTITY_VILLAGER_DEATH
    }

    override fun playStepSound(pos: BlockPos?, state: BlockState?) {
        playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f)
    }

    override fun interactMob(player: PlayerEntity, hand: Hand?): ActionResult? {
        if (world.isClient) {
            (player as IPlayerTradeWithNPC).tradeWithNPC(this.name)
//            player.client.setScreen(TraderScreen(player))
//            val optionalInt =
//                player.openHandledScreen(SimpleNamedScreenHandlerFactory({ syncId: Int, _: PlayerInventory?, playerE: PlayerEntity ->
//                    TraderScreenHandler(
//                        syncId, playerE, this
//                    )
//                }, Text.of("hi")))
        }
        player.playSound(SoundEvents.ENTITY_VILLAGER_TRADE, 1F, 1F)
        return ActionResult.success(world.isClient)
    }

    companion object {
        fun setAttributes(): DefaultAttributeContainer.Builder? {
            return createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
        }
    }
}