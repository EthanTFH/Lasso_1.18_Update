//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import com.spawnchunk.lasso.Lasso;
import com.spawnchunk.lasso.util.PermissionUtil;
import org.bukkit.entity.LeashHitch;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import com.spawnchunk.lasso.util.LeashUtil;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.Listener;

public class PlayerListener implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getInventory().getItemInMainHand();
        final Action action = event.getAction();
        final Block block = event.getClickedBlock();
        if (block != null) {
            final Material material = block.getType();
            final String material_key = material.getKey().getKey();
            if ((material_key.equals("oak_fence") || material_key.equals("spruce_fence") || material_key.equals("birch_fence") || material_key.equals("jungle_fence") || material_key.equals("acacia_fence") || material_key.equals("dark_oak_fence") || material_key.equals("nether_brick_fence") || material_key.equals("crimson_fence") || material_key.equals("warped_fence")) && action == Action.RIGHT_CLICK_BLOCK && LeashUtil.isHoldingLeash(player) && (item.getType() == Material.AIR || item.getType() == Material.LEAD)) {
                if (player.isSneaking()) {
                    LeashUtil.leashEntityToPost(player, block);
                }
                else {
                    LeashUtil.leashAllEntitiesToPost(player, block);
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entity = event.getRightClicked();
        final ItemStack item = player.getInventory().getItemInMainHand();
        final Material material = item.getType();
        if (entity instanceof LeashHitch) {
            if (material == Material.LEAD) {
                final LeashHitch leashHitch = (LeashHitch) entity;
                final Location location = leashHitch.getLocation();
                final Block block = leashHitch.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                LeashUtil.leashEntityToPost(player, block);
                event.setCancelled(true);
            } else if (material == Material.AIR) {
                final LeashHitch leashHitch = (LeashHitch) entity;
                if (player.isSneaking()) {
                    LeashUtil.unleashEntityFromPost(player, leashHitch);
                } else {
                    LeashUtil.unleashAllEntitiesFromPost(player, leashHitch);
                }
                event.setCancelled(true);
            }
        }
        else if (LeashUtil.isLeashable(entity) && material == Material.LEAD) {
            if (PermissionUtil.canLasso(player, entity)) {
                if (!Lasso.nms.isLeashed(entity)) {
                    if (LeashUtil.takeLeash(player)) {
                        Lasso.nms.leashEntityToPlayer(entity, player);
                    }
                    event.setCancelled(true);
                }
            }
            else {
                event.setCancelled(true);
            }
        }
        player.updateInventory();
    }
}
