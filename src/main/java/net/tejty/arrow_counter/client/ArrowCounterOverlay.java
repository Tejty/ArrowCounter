package net.tejty.arrow_counter.client;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.tejty.arrow_counter.ArrowCounter;
import net.tejty.arrow_counter.config.ArrowCounterClientConfigs;
import net.tejty.arrow_counter.config.ConfiguredValues;
import net.tejty.arrow_counter.util.ProjectileUtils;

import java.util.HashMap;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public class ArrowCounterOverlay implements IGuiOverlay {
    private static final HashMap<ConfiguredValues.Style, ResourceLocation> BACKGROUNDS = new HashMap<>();

    public static ArrowCounterOverlay HUD_INSTANCE;

    public static void init() {
        HUD_INSTANCE = new ArrowCounterOverlay();
        BACKGROUNDS.put(ConfiguredValues.Style.BLUE, ResourceLocation.fromNamespaceAndPath(ArrowCounter.MODID, "textures/blue.png"));
        BACKGROUNDS.put(ConfiguredValues.Style.BLACK, ResourceLocation.fromNamespaceAndPath(ArrowCounter.MODID, "textures/black.png"));
        BACKGROUNDS.put(ConfiguredValues.Style.BAR, ResourceLocation.fromNamespaceAndPath(ArrowCounter.MODID, "textures/bar.png"));
        BACKGROUNDS.put(ConfiguredValues.Style.SLOT, ResourceLocation.fromNamespaceAndPath(ArrowCounter.MODID, "textures/slot.png"));
    }

    public void render(ForgeGui gui, GuiGraphics graphics, float partialTick, int screenWidth, int screenHeight) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        ItemStack weapon = ProjectileUtils.weaponFromPLayer(player);

        if (!weapon.isEmpty()) {
            ItemStack nextProjectile = player.getProjectile(weapon);
            Predicate<ItemStack> supportedProjectiles = ProjectileUtils.getAllSupportedProjectiles(weapon);

            int count = ProjectileUtils.count(player.getInventory(), supportedProjectiles, nextProjectile);

            Component text = Component.literal(String.valueOf(count));
            boolean infinite = ProjectileUtils.isInfinite(nextProjectile, weapon, player) || player.isCreative();
            drawOverlay(graphics, screenWidth, screenHeight, text, nextProjectile, infinite);
        }
    }

    private void drawOverlay(GuiGraphics graphics, int screenWidth, int screenHeight, Component text, ItemStack icon, boolean infinite) {
        if (infinite) {
            text = Component.literal("∞").withStyle(ChatFormatting.LIGHT_PURPLE);
        }
        if (icon.isEmpty()) {
            icon = new ItemStack(Items.BARRIER);
            text = Component.empty();
        }

        ConfiguredValues.Position pos = ConfiguredValues.getPosition();
        int x = 0;
        int y = 0;

        ConfiguredValues.Style style = ConfiguredValues.getStyle();
        boolean isUnder = ConfiguredValues.getUnder();

        Font font = Minecraft.getInstance().font;
        int textWidth = font.width(text);

        int width = isUnder ? 24 : 28 + textWidth;
        int height = 24;

        int offset = ConfiguredValues.getOffset();

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

        ResourceLocation background = BACKGROUNDS.get(style);

        graphics.setColor(1, 1, 1, ConfiguredValues.getBackgroundOpacity());
        if (isUnder) {
            graphics.blit(background, x, y, width, height, 0, 0, width, height, 32, 32);
        }
        else {
            graphics.blit(background, x, y, height / 2, height, 0, 0, height / 2, height, 32, 32);
            graphics.blit(background, x + width - height / 2, y, height / 2, height, (int)(height / 2), 0, height / 2, height, 32, 32);
            graphics.blit(background, x + height / 2, y, width - height, height, (int)(height / 4), 0, height / 2, height, 32, 32);
        }
        graphics.setColor(1, 1, 1, 1);

        graphics.renderItem(icon, x + 3, y + 3);

        graphics.drawString(font, text, x + (isUnder ? width / 2 - textWidth / 2 : height - 2), y + (isUnder ? 20 : (height - font.lineHeight) / 2), 0xFFFFFF, true);
    }
}
