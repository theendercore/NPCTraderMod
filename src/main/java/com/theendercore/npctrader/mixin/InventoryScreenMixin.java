package com.theendercore.npctrader.mixin;

import com.theendercore.npctrader.screen.CurrencyBarDisplay;
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

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "drawForeground", at = @At("TAIL"))
    public void drawForeground(MatrixStack matrices, int mouseX, int mouseY, CallbackInfo ci) {
        assert client != null;
        assert client.player != null;
        int thisX = this.titleX + 33;
        int thisY = this.titleY + 55;
        CurrencyBarDisplay.INSTANCE.render(matrices, this.itemRenderer, this.textRenderer, CURRENCY.get(this.client.player).getValue(), thisX, thisY, this);
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
