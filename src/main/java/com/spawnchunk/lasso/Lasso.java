//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso;

import java.util.logging.Level;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import com.spawnchunk.lasso.commands.LassoCommand;
import java.util.Objects;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import com.spawnchunk.lasso.listeners.PlayerListener;
import com.spawnchunk.lasso.nms.v1_18_R1;
import org.bukkit.Bukkit;
import com.spawnchunk.lasso.modules.Flags;
import com.spawnchunk.lasso.nms.NMS;
import com.spawnchunk.lasso.config.Config;
import java.util.logging.Logger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lasso extends JavaPlugin implements Listener
{
    public static Lasso plugin;
    public static Logger logger;
    public static Config config;
    public static NMS nms;
    public static boolean wg_enabled;
    public static boolean gp_enabled;

    public static void main(final String[] args) {
    }

    private static String getVersion() {
        final String packageName = Lasso.plugin.getServer().getClass().getPackage().getName();
        final String version = packageName.substring(packageName.lastIndexOf(46) + 1);
        Lasso.logger.info(String.format("Using NMS version %s", version));
        return version;
    }

    public void onLoad() {
        try {
            Flags.registerFlag();
        }
        catch (NoClassDefFoundError ignored) {
            Bukkit.getLogger().info("[Lasso] WorldGuard not installed");
            return;
        }
        Bukkit.getLogger().info("[Lasso] WorldGuard is installed");
    }

    public void onEnable() {
        Lasso.plugin = this;
        Lasso.logger = this.getLogger();
        final String version = getVersion();
        Lasso.nms = new v1_18_R1();
        if(this.getServer().getPluginManager().isPluginEnabled("WorldGuard")){
            Lasso.logger.info("Enabling WorldGuard Integration");
            Lasso.wg_enabled = true;
        }
        if(this.getServer().getPluginManager().isPluginEnabled("GriefPrevention")){
            Lasso.logger.info("Enabling GriefPrevention Integration");
            Lasso.gp_enabled = true;
        }
        Lasso.config = new Config();

        this.getServer().getPluginManager().registerEvents((Listener) new PlayerListener(), this);
        this.getCommand("lasso").setExecutor((CommandExecutor)new LassoCommand());
    }

    static {
        Lasso.wg_enabled = false;
        Lasso.gp_enabled = false;
    }
}
