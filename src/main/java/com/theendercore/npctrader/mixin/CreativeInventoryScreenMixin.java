package com.theendercore.npctrader.mixin;

import com.theendercore.npctrader.NPCTraderKt;
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

import static com.theendercore.npctrader.NPCTraderKt.CURRENCY;


@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeScreenHandler> {
    @Shadow
    private static int selectedTab;

    public CreativeInventoryScreenMixin(CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Shadow
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (selectedTab == ItemGroup.INVENTORY.getIndex()) {
            assert client != null;
            assert client.player != null;
            int i = this.x + 135;
            int j = this.y + 35;
            this.itemRenderer.renderGuiItemIcon(NPCTraderKt.CURRENCY_ITEM.getDefaultStack(), i, j);
            this.textRenderer.drawWithShadow(matrices,
                    Text.of("" + CURRENCY.get(this.client.player).getValue()),
                    i + 17,
                    j+ 4,
                    0x006400);
        }
    }
}
