package net.tejty.arrow_counter.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;

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

    public static int count(Inventory inventory, Predicate<ItemStack> supportedProjectiles, ItemStack nextProjectile) {
        int count = 0;

        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack testStack = inventory.getItem(i);
            if (supportedProjectiles.test(testStack) || testStack.is(nextProjectile.getItem())) {
                // TODO (configurable) only items of the one current type
                count += testStack.getCount();
            }
        }

        return count;
    }
}
