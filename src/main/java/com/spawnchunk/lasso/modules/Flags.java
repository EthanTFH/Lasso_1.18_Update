//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso.modules;

import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.plugin.Plugin;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.WorldGuard;
import org.bukkit.Bukkit;
import com.sk89q.worldguard.protection.flags.StateFlag;

public class Flags
{
    public static StateFlag LASSO_FLAG;

    public static void registerFlag() {
        final Plugin wg = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (wg != null) {
            final FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
            try {
                final StateFlag flag = new StateFlag("lasso", true);
                registry.register((Flag)flag);
                Flags.LASSO_FLAG = flag;
            }
            catch (FlagConflictException e) {
                final Flag<?> existing = (Flag<?>)registry.get("lasso");
                if (existing instanceof StateFlag) {
                    Flags.LASSO_FLAG = (StateFlag)existing;
                }
            }
        }
    }
}
