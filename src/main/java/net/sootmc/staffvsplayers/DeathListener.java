package net.sootmc.staffvsplayers;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(!event.getEntity().hasPermission("svp.staff")) {
            event.setDeathMessage(event.getEntity().getName() + "has died!");
        } else {
            event.setDeathMessage(ChatColor.BOLD + "" + ChatColor.BLUE + event.getEntity().getName() + ChatColor.RESET + " has been ELIMINATED by " + ChatColor.RED + event.getEntity().getKiller().getName());
            StaffvsPlayers.database.setKiller(event.getEntity().getName(), event.getEntity().getKiller().getName());
        }

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if(!player.hasPermission("svp.staff")) {
            return;
        }

        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(ChatColor.GOLD + "Soot" + ChatColor.RED + "MC" + ChatColor.RESET + " | " + ChatColor.RED + "You have died! Please make sure you stay in spectator as to not interfere with the rest of the game!");
    }
}
