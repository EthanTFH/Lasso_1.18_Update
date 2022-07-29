//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso.util;

import java.util.HashSet;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import java.util.ArrayList;
import org.bukkit.entity.LeashHitch;
import org.bukkit.Location;
import org.bukkit.block.Block;
import java.util.Iterator;
import java.util.List;
import org.bukkit.entity.Player;
import com.spawnchunk.lasso.Lasso;
import org.bukkit.entity.Entity;
import java.util.Set;

public class LeashUtil
{
    private static final Set<String> LEASHABLE_MOBS;

    public static boolean isLeashable(final Entity entity) {
        final String entityType = entity.getType().toString();
        return LeashUtil.LEASHABLE_MOBS.contains(entityType.toLowerCase()) && Lasso.config.mobs.contains(entityType.toUpperCase());
    }

    public static boolean isHoldingLeash(final Player player) {
        final List<Entity> entities = (List<Entity>)player.getNearbyEntities(16.0, 16.0, 16.0);
        for (final Entity e : entities) {
            if (Lasso.nms.isLeashedToPlayer(e, player)) {
                return true;
            }
        }
        return false;
    }

    public static void leashEntityToPost(final Player player, final Block block) {
        final List<Entity> entities = (List<Entity>)player.getNearbyEntities(16.0, 16.0, 16.0);
        for (final Entity e : entities) {
            if (Lasso.nms.isLeashedToPlayer(e, player)) {
                final Location location = block.getLocation();
                Lasso.nms.leashEntityToPost(e, location.getBlockX(), location.getBlockY(), location.getBlockZ());
            }
        }
    }

    public static void leashAllEntitiesToPost(final Player player, final Block block) {
        final List<Entity> entities = (List<Entity>)player.getNearbyEntities(16.0, 16.0, 16.0);
        for (final Entity e : entities) {
            if (Lasso.nms.isLeashedToPlayer(e, player)) {
                final Location location = block.getLocation();
                Lasso.nms.leashEntityToPost(e, location.getBlockX(), location.getBlockY(), location.getBlockZ());
            }
        }
    }

    public static void unleashEntityFromPost(final Player player, final LeashHitch leashHitch) {
        final List<Entity> entities = (List<Entity>)leashHitch.getNearbyEntities(16.0, 16.0, 16.0);
        final List<Entity> hitched = new ArrayList<Entity>();
        for (final Entity e : entities) {
            if (Lasso.nms.isLeashedToHitch(e, leashHitch)) {
                hitched.add(e);
            }
        }
        if (!hitched.isEmpty()) {
            final Entity h = hitched.get(0);
            Lasso.nms.leashEntityToPlayer(h, player);
            if (hitched.size() == 1) {
                leashHitch.remove();
            }
        }
    }

    public static void unleashAllEntitiesFromPost(final Player player, final LeashHitch leashHitch) {
        final List<Entity> entities = (List<Entity>)leashHitch.getNearbyEntities(16.0, 16.0, 16.0);
        final List<Entity> hitched = new ArrayList<Entity>();
        for (final Entity e : entities) {
            if (Lasso.nms.isLeashedToHitch(e, leashHitch)) {
                hitched.add(e);
            }
        }
        if (!hitched.isEmpty()) {
            for (final Entity h : hitched) {
                Lasso.nms.leashEntityToPlayer(h, player);
            }
            leashHitch.remove();
        }
    }

    public static void giveLeash(final Player player) {
        final ItemStack item = new ItemStack(Material.LEAD, 1);
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            player.getInventory().setItemInMainHand(item);
            player.updateInventory();
        }
        else {
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    public static boolean takeLeash(final Player player) {
        final PlayerInventory pi = player.getInventory();
        final ItemStack item = pi.getItemInMainHand();
        if (item.getType() == Material.LEAD) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                return true;
            }
            final int amount = item.getAmount();
            if (amount == 1) {
                item.setAmount(0);
                pi.setItemInMainHand(new ItemStack(Material.AIR, 1));
                player.updateInventory();
                return true;
            }
            if (amount > 1) {
                item.setAmount(amount - 1);
                pi.setItemInMainHand(item);
                player.updateInventory();
                return true;
            }
        }
        return false;
    }

    static {
        (LEASHABLE_MOBS = new HashSet<String>()).add("bat");
        LeashUtil.LEASHABLE_MOBS.add("bee");
        LeashUtil.LEASHABLE_MOBS.add("blaze");
        LeashUtil.LEASHABLE_MOBS.add("cat");
        LeashUtil.LEASHABLE_MOBS.add("cave_spider");
        LeashUtil.LEASHABLE_MOBS.add("chicken");
        LeashUtil.LEASHABLE_MOBS.add("cod");
        LeashUtil.LEASHABLE_MOBS.add("cow");
        LeashUtil.LEASHABLE_MOBS.add("creeper");
        LeashUtil.LEASHABLE_MOBS.add("dolphin");
        LeashUtil.LEASHABLE_MOBS.add("donkey");
        LeashUtil.LEASHABLE_MOBS.add("drowned");
        LeashUtil.LEASHABLE_MOBS.add("elder_guardian");
        LeashUtil.LEASHABLE_MOBS.add("ender_dragon");
        LeashUtil.LEASHABLE_MOBS.add("enderman");
        LeashUtil.LEASHABLE_MOBS.add("endermite");
        LeashUtil.LEASHABLE_MOBS.add("evoker");
        LeashUtil.LEASHABLE_MOBS.add("fox");
        LeashUtil.LEASHABLE_MOBS.add("ghast");
        LeashUtil.LEASHABLE_MOBS.add("giant");
        LeashUtil.LEASHABLE_MOBS.add("guardian");
        LeashUtil.LEASHABLE_MOBS.add("hoglin");
        LeashUtil.LEASHABLE_MOBS.add("horse");
        LeashUtil.LEASHABLE_MOBS.add("husk");
        LeashUtil.LEASHABLE_MOBS.add("illusioner");
        LeashUtil.LEASHABLE_MOBS.add("iron_golem");
        LeashUtil.LEASHABLE_MOBS.add("llama");
        LeashUtil.LEASHABLE_MOBS.add("magma_cube");
        LeashUtil.LEASHABLE_MOBS.add("mule");
        LeashUtil.LEASHABLE_MOBS.add("mushroom_cow");
        LeashUtil.LEASHABLE_MOBS.add("ocelot");
        LeashUtil.LEASHABLE_MOBS.add("panda");
        LeashUtil.LEASHABLE_MOBS.add("parrot");
        LeashUtil.LEASHABLE_MOBS.add("phantom");
        LeashUtil.LEASHABLE_MOBS.add("pig");
        LeashUtil.LEASHABLE_MOBS.add("piglin");
        LeashUtil.LEASHABLE_MOBS.add("pig_zombie");
        LeashUtil.LEASHABLE_MOBS.add("pillager");
        LeashUtil.LEASHABLE_MOBS.add("player");
        LeashUtil.LEASHABLE_MOBS.add("polar_bear");
        LeashUtil.LEASHABLE_MOBS.add("pufferfish");
        LeashUtil.LEASHABLE_MOBS.add("rabbit");
        LeashUtil.LEASHABLE_MOBS.add("ravager");
        LeashUtil.LEASHABLE_MOBS.add("salmon");
        LeashUtil.LEASHABLE_MOBS.add("sheep");
        LeashUtil.LEASHABLE_MOBS.add("shulker");
        LeashUtil.LEASHABLE_MOBS.add("silverfish");
        LeashUtil.LEASHABLE_MOBS.add("skeleton");
        LeashUtil.LEASHABLE_MOBS.add("skeleton_horse");
        LeashUtil.LEASHABLE_MOBS.add("slime");
        LeashUtil.LEASHABLE_MOBS.add("snowman");
        LeashUtil.LEASHABLE_MOBS.add("spider");
        LeashUtil.LEASHABLE_MOBS.add("squid");
        LeashUtil.LEASHABLE_MOBS.add("stray");
        LeashUtil.LEASHABLE_MOBS.add("strider");
        LeashUtil.LEASHABLE_MOBS.add("trader_llama");
        LeashUtil.LEASHABLE_MOBS.add("tropical_fish");
        LeashUtil.LEASHABLE_MOBS.add("turtle");
        LeashUtil.LEASHABLE_MOBS.add("vex");
        LeashUtil.LEASHABLE_MOBS.add("villager");
        LeashUtil.LEASHABLE_MOBS.add("vindicator");
        LeashUtil.LEASHABLE_MOBS.add("wandering_trader");
        LeashUtil.LEASHABLE_MOBS.add("witch");
        LeashUtil.LEASHABLE_MOBS.add("wither");
        LeashUtil.LEASHABLE_MOBS.add("wither_skeleton");
        LeashUtil.LEASHABLE_MOBS.add("wolf");
        LeashUtil.LEASHABLE_MOBS.add("zoglin");
        LeashUtil.LEASHABLE_MOBS.add("zombie");
        LeashUtil.LEASHABLE_MOBS.add("zombie_horse");
        LeashUtil.LEASHABLE_MOBS.add("zombie_villager");
        LeashUtil.LEASHABLE_MOBS.add("zombified_piglin");
    }
}
