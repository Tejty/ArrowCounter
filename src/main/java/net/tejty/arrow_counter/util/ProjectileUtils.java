package net.tejty.arrow_counter.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.tejty.arrow_counter.config.ConfiguredValues;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class ProjectileUtils {
    public static ItemStack weaponFromPLayer(Player player) {
        if (player.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
            return player.getMainHandItem();
        }
        if (player.getOffhandItem().getItem() instanceof ProjectileWeaponItem) {
            return player.getOffhandItem();
        }
        return ItemStack.EMPTY;
    }

    public static @Nullable ProjectileWeaponItem weapon(ItemStack stack) {
        if (stack.getItem() instanceof ProjectileWeaponItem weapon) {
            return weapon;
        }
        return null;
    }

    public static boolean isInfinite(ItemStack nextProjectile, ItemStack weapon, Player player) {
        if (nextProjectile.getItem() instanceof ArrowItem arrow) {
            ProjectileWeaponItem weapon_item = weapon(weapon);
            if (weapon_item != null) {
                return arrow.isInfinite(nextProjectile, weapon, player);
            }
        }
        return false;
    }

    public static Predicate<ItemStack> getAllSupportedProjectiles(ItemStack weapon) {
        ProjectileWeaponItem weapon_item = weapon(weapon);
        if (weapon_item != null) {
            return weapon_item.getAllSupportedProjectiles();
        }
        return null;
    }

    public static int count(Inventory inventory, Predicate<ItemStack> supportedProjectiles, ItemStack nextProjectile) {
        int count = 0;

        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack testStack = inventory.getItem(i);
            if ((((!ConfiguredValues.getStrictCheck()) && supportedProjectiles.test(testStack))) || testStack.is(nextProjectile.getItem())) {
                count += testStack.getCount();
            }
        }

        return count;
    }
}
