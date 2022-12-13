package com.theendercore.npctrader.mixin;

import com.theendercore.npctrader.entity.IEntityCurrency;
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
        this.textRenderer.drawWithShadow(matrices, Text.translatable("npctrader.currency.symbol").append(" " + ((IEntityCurrency) client.player).getCurrency()), (float) this.titleX + 35, (float) this.titleY + 60, 0x0fff0f);
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
