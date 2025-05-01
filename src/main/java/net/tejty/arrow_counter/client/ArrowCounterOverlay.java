package net.tejty.arrow_counter.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.tejty.arrow_counter.ArrowCounter;
import net.tejty.arrow_counter.util.ProjectileUtils;

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
        ItemStack stack = ProjectileUtils.weaponFromPLayer(player);
        // TODO offhand
        // TODO crossbow fireworks
        if (!stack.isEmpty()) {
            ItemStack nextProjectile = player.getProjectile(stack);
            Predicate<ItemStack> supportedProjectiles = ProjectileUtils.weapon(stack).getAllSupportedProjectiles();

            int count = ProjectileUtils.count(player.getInventory(), supportedProjectiles, nextProjectile);

            graphics.setColor(1, 1, 1, 0.7F);
            graphics.blit(BACKGROUND, 10, 20, 24, 24, 0, 0, 24, 24, 32, 32);
            graphics.setColor(1, 1, 1, 1);

            // TODO creative and infinite enchantment "∞"
            graphics.drawString(Minecraft.getInstance().font, Component.literal(String.valueOf(count)), 20, 40, 0xFFFFFF, true);

            graphics.renderFakeItem(nextProjectile, 14, 24);
        }
    }
}
