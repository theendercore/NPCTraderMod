package com.theendercore.npctrader.mixin;

import com.theendercore.npctrader.screen.CurrencyBarWidget;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen.CreativeScreenHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeScreenHandler> {
    @Shadow
    private static int selectedTab;

    private static CurrencyBarWidget currencyBar;

    public CreativeInventoryScreenMixin(CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Shadow
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {

        int i = this.x + 135;
        int j = this.y + 35;
        assert client != null;
        assert this.client.player != null;
        currencyBar = new CurrencyBarWidget(i, j, client, this.client.player, this);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (selectedTab == ItemGroup.INVENTORY.getIndex()) {
            assert client != null;
            assert client.player != null;
            currencyBar.render(matrices, mouseX, mouseY, delta);
        }
    }
}
