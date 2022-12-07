package com.theendercore.npctrader.mixin;

import com.theendercore.npctrader.entity.IPlayerTradeWithNPC;
import com.theendercore.npctrader.screen.TraderScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements IPlayerTradeWithNPC {
    MinecraftClient client;

    public void tradeWithNPC(Text name) {
        client.setScreen(new TraderScreen(name));
    }
}
