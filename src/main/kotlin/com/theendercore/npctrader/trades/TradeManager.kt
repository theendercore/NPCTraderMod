package com.theendercore.npctrader.trades

import com.google.common.collect.ImmutableMap
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.theendercore.npctrader.LOGGER
import com.theendercore.npctrader.id
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.entity.EntityType
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import java.io.BufferedReader
import java.io.IOException
import java.util.*
import javax.annotation.Nullable
import java.util.HashMap

object TradeManager : SimpleSynchronousResourceReloadListener {

    private val GSON = GsonBuilder().create()
    private val traders = ArrayList<EntityType<*>>()
    private var traderTrades: Map<EntityType<*>, TradeList> = HashMap()

    @Nullable
    fun getTrades(type: EntityType<*>): TradeList? {
        return this.traderTrades[type]
    }

    override fun getFabricId(): Identifier {
        return id("trades")
    }

    fun registerTrader(type: EntityType<*>) {
        if (!traders.contains(type)) {
            traders.add(type)
        }
    }

    override fun reload(manager: ResourceManager) {
        val entityToResourceList = HashMap<EntityType<*>, TradeList>()
        for (trader in this.traders) {
            val resource = id("trades/${EntityType.getId(trader).path}.json")
            LOGGER.info(resource.toString())
            val builder = TradeList.Builder ()
            deserializeTrade(manager, resource, builder)
            entityToResourceList[trader] = builder.build() as TradeList
        }
        this.traderTrades = ImmutableMap.copyOf(entityToResourceList)
    }
    private fun deserializeTrade(manager: ResourceManager, resource: Identifier, builder: TradeList.Builder) {
        manager.getResource(resource).ifPresent { rec ->
            try {
                BufferedReader( rec.reader ).use { reader ->
                    val x = GSON.fromJson(
                        reader,
                        JsonObject::class.java
                    )
                    LOGGER.info(x.toString())
                    builder.deserialize(x)

                }
            } catch (e: IOException) {
                LOGGER.error(e.toString())
            }
        }
    }



}