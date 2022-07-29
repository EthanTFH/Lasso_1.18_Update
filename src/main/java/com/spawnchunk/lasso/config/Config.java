//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso.config;

import java.util.logging.Level;
import com.spawnchunk.lasso.Lasso;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
    private FileConfiguration fc;
    private static final int config_version = 3;
    public static boolean debug = false;
    public static boolean use_perms;
    public List<String> mobs;

    public Config() {
        this.mobs = new ArrayList<String>();
        this.fc = Lasso.plugin.getConfig();
        this.parseConfig();
    }

    public void parseConfig() {
        final int version = this.fc.contains("configVersion") ? this.fc.getInt("configVersion") : 0;
        if (version < 3) {
            this.upgradeConfig();
        }
        if (this.fc.contains("debug")) {
            Config.debug = this.fc.getBoolean("debug");
        }
        if (this.fc.contains("lasso.perms")) {
            Config.use_perms = this.fc.getBoolean("lasso.perms");
        }
        if (this.fc.contains("lasso.mobs")) {
            this.mobs = (List<String>)this.fc.getStringList("lasso.mobs");
        }
    }

    private void upgradeConfig() {
        Lasso.logger.log(Level.WARNING, "Upgrading config file to the latest version");
        this.fc.options().copyDefaults(true);
        this.fc.set("configVersion", (Object)3);
        Lasso.plugin.saveConfig();
    }

    public void reloadConfig() {
        if (Config.debug) {
            Lasso.logger.info("Reloading configuration");
        }
        Lasso.plugin.reloadConfig();
        this.fc = Lasso.plugin.getConfig();
        this.parseConfig();
    }

    static {
        Config.debug = false;
        Config.use_perms = false;
    }
}
