package com.averylg.marchmadness.commands;

import com.averylg.marchmadness.sockets.MCWebSocketClient;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class TriggerCommands implements CommandExecutor {

    private MCWebSocketClient client;
    public TriggerCommands(MCWebSocketClient client) {
        this.client = client;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("mm")) {

            ((Player) sender).sendMessage("hehe command works");
            String villager1Name = args[0];
            String villager2Name = args[1];
            List<Villager> pair = spawnVillagerPair(villager1Name, villager2Name);
            this.client.setVillagerPair(pair);
            return true;
        }
        return false;
    }

    public Villager spawnVillager(String name, int x, int y, int z) {
        Villager viwwagew = (Villager) Bukkit.getWorld("world").spawnEntity(
                new Location(Bukkit.getWorld("world"), x, y, z),
                EntityType.VILLAGER
        );
        viwwagew.setCustomNameVisible(true);
        viwwagew.setCustomName(name);
        return viwwagew;
    }

    public List<Villager> spawnVillagerPair(String v1Name, String v2Name) {
        List<Villager> pair = client.getVillagerPair();
        pair.add(spawnVillager(v1Name, -37, 63, -35));
        pair.add(spawnVillager(v2Name, -39, 63, -35));
        return pair;
    }

}
