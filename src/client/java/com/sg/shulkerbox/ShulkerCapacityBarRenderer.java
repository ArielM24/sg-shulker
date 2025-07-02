package com.sg.shulkerbox;

import org.apache.commons.lang3.math.Fraction;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;

public class ShulkerCapacityBarRenderer {
    ItemStack stack;
    int stackX;
    int stackY;

    private static final int FULL_ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 1.0F, 0.33F, 0.33F);
    private static final int ITEM_BAR_COLOR = ColorHelper.fromFloats(1.0F, 0.44F, 0.53F, 1.0F);

    private Fraction capacity;

    private int xBackgroundStart;
    private int yBackgroundStart;
    private int xBackgroundEnd;
    private int yBackgroundEnd;

    private int xCapacityStart;
    private int yCapacityStart;
    private int xCapacityEnd;
    private int yCapacityEnd;

    public ShulkerCapacityBarRenderer(ItemStack stack, int x, int y) {
        this.stack = stack;
        this.stackX = x;
        this.stackY = y;
        this.capacity = getShulkerCapacity(stack);
    }

    protected void calculatePositions() {
        int step = (int) (13 * capacity.floatValue());
        int shadowHeight = 1;

        xBackgroundStart = stackX + 2;
        yBackgroundStart = stackY + 13;

        xBackgroundEnd = xBackgroundStart + 13;
        yBackgroundEnd = yBackgroundStart + 1 + shadowHeight;
        xCapacityStart = xBackgroundStart;
        yCapacityStart = yBackgroundStart;
        xCapacityEnd = xBackgroundStart + step;
        yCapacityEnd = yCapacityStart + 1;

    }

    protected void render(DrawContext context) {
        context.fill(RenderPipelines.GUI, xBackgroundStart, yBackgroundStart, xBackgroundEnd, yBackgroundEnd,
                Colors.BLACK);

        int colour = capacity.compareTo(Fraction.ONE) == 0 ? FULL_ITEM_BAR_COLOR : ITEM_BAR_COLOR;
        context.fill(RenderPipelines.GUI, xCapacityStart, yCapacityStart, xCapacityEnd, yCapacityEnd,
                ColorHelper.fullAlpha(colour));
    }

    public void renderOptional(DrawContext context) {
        if (canDisplay()) {
            calculatePositions();
            render(context);
        }
    }

    protected boolean canDisplay() {
        return capacity.compareTo(Fraction.ZERO) == 1;
    }

    private static Fraction getShulkerCapacity(ItemStack shulker) {
        boolean isShulker = shulker.getItemName().getString().contains("Shulker Box");
        if (!isShulker) {
            return Fraction.ZERO;
        }
        ContainerComponent containerComponent = shulker.get(DataComponentTypes.CONTAINER);
        Iterable<ItemStack> itemIterable = containerComponent.iterateNonEmpty();
        Fraction maxItems = Fraction.getFraction(1728, 1);
        Fraction numItems = Fraction.ZERO;
        for (ItemStack itemStack : itemIterable) {
            numItems = numItems.add(getItemCountEquivalent(itemStack)); // Adjust by max stack size of item
        }
        return numItems.divideBy(maxItems);
    }

    private static Fraction getItemFraction(ItemStack itemStack) {
        return Fraction.getFraction(itemStack.getCount(), itemStack.getMaxCount());
    }

    private static Fraction getItemCountEquivalent(ItemStack itemStack) {
        return getItemFraction(itemStack).multiplyBy(Fraction.getFraction(64, 1));
    }
}
