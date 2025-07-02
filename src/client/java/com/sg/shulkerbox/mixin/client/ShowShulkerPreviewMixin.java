package com.sg.shulkerbox.mixin.client;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.sg.shulkerbox.ShulkerPreviewScreen;
import com.sg.shulkerbox.SGShulkerbox;
import com.sg.shulkerbox.SGShulkerboxClient;

@Mixin(HandledScreen.class)
public class ShowShulkerPreviewMixin {
	@Shadow @Nullable protected Slot focusedSlot;

    @Inject(at = @At("HEAD"), method = "keyPressed")
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (SGShulkerboxClient.key.matchesKey(keyCode, scanCode) && this.focusedSlot != null) {
            ItemStack stack = this.focusedSlot.getStack();
            SGShulkerbox.LOGGER.info("stack " +stack.getItemName().getString());
            if (SGShulkerboxClient.shulkers.contains(stack.getItem())) {
                SGShulkerboxClient.client.setScreen(new ShulkerPreviewScreen(stack, SGShulkerboxClient.client.currentScreen));
            }
        }
    }
}