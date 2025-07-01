package com.sg.shulkerbox;

import java.util.List;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ShulkerPreviewScreen extends Screen {
    private static final Identifier SHULKER_BACKGROUND = Identifier.of("sg-shulkerbox", "shulker_box_background.png");
    private final int backgroundWidth = 176;
    private final int backgroundHeight = 78;

    private final Text name;
    private final List<ItemStack> inventory;
    private final Screen parent;

    private int x = 0;
    private int y = 0;

    public ShulkerPreviewScreen(ItemStack shulker, Screen parent) {
        super(Text.literal("ShulkerPreviewScreen"));
        this.name = shulker.getCustomName();
        this.inventory = shulker.get(DataComponentTypes.CONTAINER).stream().toList();
        this.parent = parent;

    }

    @Override
    protected void init() {
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderBackground(context, mouseX, mouseY, delta);
        this.renderItems(context, this.inventory, this.x + 8, this.y + 18);
        int selectedSlot = getSlot(mouseX, mouseY);
        if (-1 < selectedSlot && selectedSlot < this.inventory.size()
                && !this.inventory.get(selectedSlot).isOf(Items.AIR)) {
            this.renderTooltip(context, this.inventory.get(selectedSlot), mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, SHULKER_BACKGROUND, this.x, this.y, 0.0F, 0.0F,
                this.backgroundWidth, this.backgroundHeight, 256, 256);
        Text shulkerTitle = name;
        if(shulkerTitle == null){
            shulkerTitle = Text.literal("");
        }
        context.drawText(textRenderer, shulkerTitle, this.x + 8, this.y + 6, 0xFF35393d, false);
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    private void renderItems(DrawContext context, List<ItemStack> inventory, int x, int y) {
        int baseX = x;
        int count = 0;
        for (ItemStack item : inventory) {
            count++;
            context.drawItem(item, x, y);
            context.drawStackOverlay(textRenderer, item, x, y);
            x += 18;
            if (count % 9 == 0) {
                x = baseX;
                y += 18;
            }
        }
    }

    private int getSlot(int i, int j) {
        int x = this.x + 7;
        int y = this.y + 17;

        int slotX = (i - x) / 18;
        int slotY = (j - y) / 18;

        if (i < x || j < y || i > x + 9 * 18 - 1 || j > y + 3 * 18 - 1) {
            return -1;
        }

        return slotX + slotY * 9;
    }

    private void renderTooltip(DrawContext context, ItemStack stack, int x, int y) {
        context.drawItemTooltip(textRenderer, stack, x, y);
    }
}
