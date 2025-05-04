package net.tejty.arrow_counter.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.tejty.arrow_counter.ArrowCounter;
import net.tejty.arrow_counter.config.ConfiguredValues;
import net.tejty.arrow_counter.util.ProjectileUtils;

import javax.swing.text.TabExpander;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class ArrowCounterOverlay implements IGuiOverlay {
    private static final ResourceLocation BACKGROUND =
            ResourceLocation.fromNamespaceAndPath(ArrowCounter.MODID, "textures/background.png");

    public static ArrowCounterOverlay HUD_INSTANCE;

    public static void init() {
        HUD_INSTANCE = new ArrowCounterOverlay();
    }

    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        ItemStack weapon = ProjectileUtils.weaponFromPLayer(player);

        if (!weapon.isEmpty()) {
            ItemStack nextProjectile = player.getProjectile(weapon);
            Predicate<ItemStack> supportedProjectiles = ProjectileUtils.weapon(weapon).getAllSupportedProjectiles();

            int count = ProjectileUtils.count(player.getInventory(), supportedProjectiles, nextProjectile);

            Component text = Component.literal(String.valueOf(count));
            drawOverlay(graphics, screenWidth, screenHeight, text, nextProjectile);
        }
    }

    private void drawOverlay(GuiGraphics graphics, int screenWidth, int screenHeight, Component text, ItemStack icon) {
        ConfiguredValues.Position pos = ConfiguredValues.getPosition();
        int x = 0;
        int y = 0;

        Font font = Minecraft.getInstance().font;
        int textWidth = font.width(text);

        int width = 24;
        int height = 24;

        int offset = 10;

        int hotbarWidth = 182;

        switch (pos) {
            case TOP_LEFT -> {
                x = offset;
                y = offset;
            }
            case TOP_RIGHT -> {
                x = screenWidth - width - offset;
                y = offset;
            }
            case BOTTOM_LEFT -> {
                x = offset;
                y = screenHeight - height - offset;
            }
            case BOTTOM_RIGHT -> {
                x = screenWidth - width - offset;
                y = screenHeight - height - offset;
            }
            case MIDDLE_NEXT -> {
                x = screenWidth / 2 + offset;
                y = screenHeight / 2 - height / 2;
            }
            case MIDDLE_UNDER -> {
                x = screenWidth / 2 - width / 2;
                y = screenHeight / 2 + offset;
            }
            case HOTBAR_LEFT -> {
                x = screenWidth / 2 - hotbarWidth / 2 - width - offset;
                y = screenHeight - height - offset;
            }
            case HOTBAR_RIGHT -> {
                x = screenWidth / 2 + hotbarWidth / 2 + offset;
                y = screenHeight - height - offset;
            }
        }

        graphics.setColor(1, 1, 1, 0.7F);
        graphics.blit(BACKGROUND, x, y, width, height, 0, 0, width, height, 32, 32);
        graphics.setColor(1, 1, 1, 1);

        // TODO barrier when no ammo
        graphics.renderItem(icon, x + 4, y + 4);

        // TODO creative and infinite enchantment "∞"
        graphics.drawString(font, text, x + width / 2 - textWidth / 2, y + 19, 0xFFFFFF, true);
    }
}
