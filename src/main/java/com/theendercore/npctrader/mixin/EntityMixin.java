package com.theendercore.npctrader.mixin;


import com.theendercore.npctrader.entity.IEntityData;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityData {
    private long currency = 0L;

    @Override
    public long getCurrency() {
        return currency;
    }

    @Override
    public void addCurrency(Long amount) {
        this.currency += amount;
    }

    @Override
    public void removeCurrency(Long amount) {
        this.currency -= amount;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void writeCurrency(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        nbt.putLong("Currency", currency);
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void readCurrency(NbtCompound nbt, CallbackInfo ci) {
        currency = nbt.getLong("Currency");
    }
}
