package com.theendercore.npctrader.mixin;

import com.theendercore.npctrader.screen.CurrencyBarWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.theendercore.npctrader.NPCTraderKt.CURRENCY;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {

    private static CurrencyBarWidget currencyBar;

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        int i = this.titleX + 33;
        int j = this.titleY + 55;
        assert client != null;
        assert this.client.player != null;
        currencyBar = new CurrencyBarWidget(i, j, client, CURRENCY.get(this.client.player).getValue(), this);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        assert client != null;
        assert client.player != null;
        currencyBar.render(matrices, mouseX, mouseY, delta);
    }


    @Shadow
    public void refreshRecipeBook() {

    }

    @Shadow
    public RecipeBookWidget getRecipeBookWidget() {
        return null;
    }

    @Shadow
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

    }
}
