//
// Decompiled by Procyon v0.5.36
//

package com.spawnchunk.lasso.nms;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.World;;
import org.bukkit.Location;
import org.bukkit.entity.LeashHitch;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class v1_18_R1 implements NMS
{
    @Override
    public boolean hasNoAI(final Entity entity){
        NBTEntity nbt = new NBTEntity(entity);

        return nbt.hasKey("NoAI") && nbt.getByte("NoAI") > 0;
    }

    @Override
    public boolean isLeashed(final Entity entity) {
        NBTEntity nbt = new NBTEntity(entity);

        return nbt.hasKey("Leashed") && nbt.getByte("Leashed") > 0 || nbt.hasKey("Leash");
    }

    @Override
    public boolean isLeashedToPlayer(final Entity entity, final Player player) {
        NBTEntity nbt = new NBTEntity(entity);

        if(this.isLeashed(entity) && nbt.hasKey("Leash")){
            NBTCompound leash = nbt.getCompound("Leash");
            if(leash.hasKey("UUID")){
                UUID uuid = leash.getUUID("UUID");
                return uuid == player.getUniqueId();
            }
        }
        return false;
    }

    @Override
    public boolean isLeashedToHitch(final Entity entity, final LeashHitch hitch) {
        final Location location = hitch.getLocation();
        return this.isLeashedToPost(entity, location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public boolean isLeashedToPost(final Entity entity, final int x, final int y, final int z) {
        NBTEntity nbt = new NBTEntity(entity);

        if(this.isLeashed(entity) && nbt.hasKey("Leash")){
            NBTCompound leash = nbt.getCompound("Leash");
            if(leash.hasKey("X") && leash.hasKey("Y") && leash.hasKey("Z")){
                final int x2 = leash.getInteger("X");
                final int y2 = leash.getInteger("Y");
                final int z2 = leash.getInteger("Z");
                return x == x2 && y == y2 && z == z2;
            }
        }
        return false;
    }

    @Override
    public void unleashEntityFromPost(final Entity entity, final Player player) {
        NBTEntity nbt = new NBTEntity(entity);

        if(this.isLeashed(entity) && nbt.hasKey("Leash")){
            NBTCompound leash = nbt.getCompound("Leash");

            if(leash.hasKey("X") && leash.hasKey("Y") && leash.hasKey("Z")){
                leash.removeKey("X");
                leash.removeKey("Y");
                leash.removeKey("Z");
                if(leash.hasKey("UUID")){
                    leash.setUUID("UUID",player.getUniqueId());
                }
                nbt.setByte("Leashed",(byte)1);
            }
        }
    }

    @Override
    public void unleashEntityFromPlayer(final Entity entity, final Player player) {
        NBTEntity nbt = new NBTEntity(entity);
        nbt.removeKey("Leash");
        nbt.setByte("Leashed", (byte)0);
    }

    @Override
    public void leashEntityToPost(final Entity entity, final int x, final int y, final int z) {
        final World world = entity.getWorld();
        final Block block = world.getBlockAt(x, y, z);
        final Material material = block.getType();
        final String material_key = material.getKey().getKey();
        if (material_key.equals("oak_fence") || material_key.equals("spruce_fence") || material_key.equals("birch_fence") || material_key.equals("jungle_fence") || material_key.equals("acacia_fence") || material_key.equals("dark_oak_fence") || material_key.equals("nether_brick_fence") || material_key.equals("crimson_fence") || material_key.equals("warped_fence")) {
            NBTEntity nbt = new NBTEntity(entity);

            NBTCompound leash;
            if(nbt.hasKey("Leash")){
                leash = nbt.getCompound("Leash");
                if(leash.hasKey("UUID")){
                    leash.removeKey("UUID");
                }
            }else{
                leash = nbt.addCompound("Leash");
            }
            leash.setInteger("X",x);
            leash.setInteger("Y",y);
            leash.setInteger("Z",z);
            nbt.setByte("Leashed",(byte)1);
        }
    }

    @Override
    public void leashEntityToPlayer(final Entity entity, final Player player) {
        NBTEntity nbt = new NBTEntity(entity);
        NBTCompound leash;

        if(nbt.hasKey("Leash")){
            leash = nbt.getCompound("Leash");
            if(leash.hasKey("X") && leash.hasKey("Y") && leash.hasKey("Z")) {
                leash.removeKey("X");
                leash.removeKey("Y");
                leash.removeKey("Z");
            }
        }else{
            leash = nbt.addCompound("Leash");
        }
        leash.setUUID("UUID", player.getUniqueId());
        nbt.setByte("Leashed", (byte)1);
        nbt.setByte("Leashed", (byte)1);
    }
}
