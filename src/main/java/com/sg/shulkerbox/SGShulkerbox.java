package com.sg.shulkerbox;


import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;



public class SGShulkerbox implements ModInitializer {
	public static final String MOD_ID = "sg-shulkerbox";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final Identifier HUD_LAYER = Identifier.of(SGShulkerbox.MOD_ID, "compass-layer");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		// HudElementRegistry.attachElementBefore(VanillaHudElements.HELD_ITEM_TOOLTIP, HUD_LAYER, SGShulkerbox::render);
	}
	// private static void render(DrawContext ctx, RenderTickCounter tickCounter) {
	// 	MinecraftClient client = MinecraftClient.getInstance();
	// 	ClientPlayerEntity player = client.player;
	// 	Identifier texture = Identifier.of("minecraft", "textures/block/deepslate.png");
		
	// 	ctx.drawTexture(RenderPipelines.GUI_TEXTURED, texture, 90, 90, 0, 0, 16, 16, 16, 16);
	// 	TextRenderer renderer = client.textRenderer;
	// 	BlockPos pos = player.getBlockPos();
	// 	PlayerInventory inv = client.player.getInventory();
	// 	boolean hasCompass = inv.contains(is -> {
	// 		return (is.getItem().toString().endsWith("compass") || is.getItem() instanceof CompassItem);
	// 	});
	// 	float degrees = MathHelper.wrapDegrees(player.getYaw());
	// 	if (degrees < 0) {
	// 		degrees += 360;
	// 	}
	// 	int facing = Math.round(degrees / 45);
	// 	List<String> direction = Arrays.asList("S", "SW", "W", "NW", "N", "NE", "E", "SE", "S");
		
	// 	if (hasCompass) {
	// 		String displayFacing = direction.get(facing);
	// 		String displayCoords = pos.toShortString();
	// 		String displayBiome = client.world.getBiome(pos).getIdAsString().replaceAll("minecraft:", "").replaceAll("_", " ");

	// 		ctx.drawText(renderer, String.format("%s %s", displayFacing, displayCoords), 10, 10,
	// 				0xFFFFFFFF, false);
	// 		ctx.drawText(renderer, String.format("%s%s", displayBiome.substring(0, 1).toUpperCase(), displayBiome.substring(1)), 10, 20,
	// 				0xFFFFFFFF, false);
	// 	}
	// }
}