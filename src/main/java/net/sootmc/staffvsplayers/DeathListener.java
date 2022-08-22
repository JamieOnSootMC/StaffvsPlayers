package net.sootmc.staffvsplayers;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

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
}
