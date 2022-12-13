package com.theendercore.npctrader.entity

import com.theendercore.npctrader.id
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

object TraderEntities {
    var BUTCHER: EntityType<TraderEntity> = Registry.register<EntityType<*>, EntityType<TraderEntity>>(Registry.ENTITY_TYPE,
        id("butcher"),
        FabricEntityTypeBuilder.create<TraderEntity>(
            SpawnGroup.CREATURE
        ) { entityType: EntityType<TraderEntity?>?, world: World? -> TraderEntity(entityType, world) }
            .dimensions(EntityDimensions(0.6f, 2f, true)).build())
}