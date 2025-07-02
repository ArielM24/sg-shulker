package com.sg.shulkerbox.mixin.client;

import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sg.shulkerbox.ShulkerCapacityBarRenderer;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

@Mixin(DrawContext.class)
public class ShowShulkerCapacityMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemBar(Lnet/minecraft/item/ItemStack;II)V"), method = "drawStackOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V")
    private void renderShulkerItemOverlay(TextRenderer textRenderer, ItemStack stack, int x, int y,
            String stackCountText, CallbackInfo info) {
        if (stack == null) {
            return;
        }
        if (stack.getItemName() == null) {
            return;
        }
        boolean isShulker = stack.getItemName().getString().contains("Shulker Box");
        if (!isShulker) {
            return;
        }
        ShulkerCapacityBarRenderer barRenderer = new ShulkerCapacityBarRenderer(stack, x, y) ;
        barRenderer.renderOptional((DrawContext)(Object)this);
    }
}
