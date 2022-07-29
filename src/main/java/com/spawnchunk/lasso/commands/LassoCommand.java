//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso.commands;

import org.bukkit.entity.Player;
import com.spawnchunk.lasso.Lasso;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class LassoCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("lasso")) {
            if (args.length != 1) {
                return false;
            }
            if (args[0].equalsIgnoreCase("reload") && this.hasPermission(sender, "lasso.reload")) {
                Lasso.config.reloadConfig();
                sender.sendMessage("Lasso reloaded");
                return true;
            }
        }
        sender.sendMessage("Unknown command. Type \"/help\" for help.");
        return true;
    }

    private boolean hasPermission(final CommandSender sender, final String permission) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            return player.hasPermission(permission) || player.isOp();
        }
        return true;
    }
}
