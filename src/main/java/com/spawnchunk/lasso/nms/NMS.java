//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso.nms;

import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;

public interface NMS
{
    boolean hasNoAI(final Entity p0);

    boolean isLeashed(final Entity p0);

    boolean isLeashedToPlayer(final Entity p0, final Player p1);

    boolean isLeashedToHitch(final Entity p0, final LeashHitch p1);

    boolean isLeashedToPost(final Entity p0, final int p1, final int p2, final int p3);

    void unleashEntityFromPost(final Entity p0, final Player p1);

    void unleashEntityFromPlayer(final Entity p0, final Player p1);

    void leashEntityToPost(final Entity p0, final int p1, final int p2, final int p3);

    void leashEntityToPlayer(final Entity p0, final Player p1);
}
