package com.theendercore.npctrader.trades

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
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*
import javax.annotation.Nullable


object TradeManager : SimpleSynchronousResourceReloadListener {

    private val GSON = GsonBuilder().create()

    private val traders = ArrayList<EntityType<*>>()
    private val traderTrades = HashMap<EntityType<*>, EntityTradeList>()

    @Nullable
    fun getTrades(type: EntityType<*>): EntityTradeList? {
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

    private fun deserializeTrade(manager: ResourceManager, resource: Identifier) {
        manager.getResource(resource).ifPresent { rec ->
            try {
                BufferedReader(
                    InputStreamReader(
                        rec.inputStream,
                        StandardCharsets.UTF_8
                    )
                ).use { reader ->
                    val x = GSON.fromJson(
                        reader,
                        JsonObject::class.java
                    )
                    LOGGER.info(x.toString())
//                        builder.deserialize(rarity, x)
                }
            } catch (e: IOException) {
                LOGGER.error(e.toString())
            }
        }
    }

    override fun reload(manager: ResourceManager) {

        LOGGER.info("Reloading!")

        for (trader in this.traders) {
            val resource = id("trades/${EntityType.getId(trader).path}.json")

            LOGGER.info(resource.toString())

//            deserializeTrade(manager, resource)


        }

    }
}