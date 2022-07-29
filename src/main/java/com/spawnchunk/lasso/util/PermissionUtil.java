//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso.util;

import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import com.spawnchunk.lasso.modules.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.spawnchunk.lasso.Lasso;
import com.spawnchunk.lasso.config.Config;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PermissionUtil
{
    public static boolean canLasso(final Player player, final Entity entity) {
        if ((isNPC(entity) || isEntityProtected(player, entity)) && !player.isOp()) {
            return false;
        }
        return (!Config.use_perms && LeashUtil.isLeashable(entity))|| player.isOp();
    }

    public static boolean isNPC(final Entity entity) {
        if (entity.hasMetadata("NPC")) {
            if (Config.debug) {
                Lasso.logger.info("Entity is NPC");
            }
            return true;
        }
        if (Lasso.nms.hasNoAI(entity)) {
            if (Config.debug) {
                Lasso.logger.info("Entity has NoAI");
            }
            return true;
        }
        return false;
    }

    public static boolean isEntityProtected(final Player player, final Entity entity) {
        if (Lasso.wg_enabled) {
            final LocalPlayer localPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
            final Location loc = BukkitAdapter.adapt(entity.getLocation());
            final RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            final RegionQuery query = container.createQuery();
            final boolean flag = query.testState(loc, localPlayer, new StateFlag[] { Flags.LASSO_FLAG });
            if (!flag) {
                if (Config.debug) {
                    Lasso.logger.info("Entity is Flag Protected");
                }
                return true;
            }
        }
        if (Lasso.gp_enabled) {
            final String flag2 = GriefPrevention.instance.allowBuild(player, entity.getLocation());
            if (flag2 != null && !flag2.isEmpty()) {
                if (Config.debug) {
                    Lasso.logger.info("Entity is Claim Protected");
                }
                return true;
            }
        }
        return false;
    }
}
