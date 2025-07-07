package com.sg.shulkerbox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import java.util.List;

import org.lwjgl.glfw.GLFW;

public class SGShulkerboxClient implements ClientModInitializer {
	public static MinecraftClient client;
	public static final List<Item> shulkers = List.of(Items.SHULKER_BOX, Items.WHITE_SHULKER_BOX, Items.LIGHT_GRAY_SHULKER_BOX,
				Items.GRAY_SHULKER_BOX, Items.BLACK_SHULKER_BOX, Items.BROWN_SHULKER_BOX,
				Items.RED_SHULKER_BOX,
				Items.ORANGE_SHULKER_BOX,
				Items.YELLOW_SHULKER_BOX,
				Items.LIME_SHULKER_BOX,
				Items.GREEN_SHULKER_BOX,
				Items.CYAN_SHULKER_BOX,
				Items.LIGHT_BLUE_SHULKER_BOX,
				Items.BLUE_SHULKER_BOX,
				Items.PURPLE_SHULKER_BOX,
				Items.MAGENTA_SHULKER_BOX,
				Items.PINK_SHULKER_BOX);

	public static KeyBinding key;

	@Override
	public void onInitializeClient() {
		client = MinecraftClient.getInstance();
		

		key = KeyBindingHelper.registerKeyBinding(
				new KeyBinding("Open shulker preview", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, "Shulker Preview"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				ItemStack stack = client.player.getMainHandStack();
				while (key.wasPressed()) {
					if (shulkers.contains(stack.getItem())) {
						client.setScreen(new ShulkerPreviewScreen(stack, null));
					}
				}
			}
		});
	}
}