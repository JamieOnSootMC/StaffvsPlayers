package net.sootmc.staffvsplayers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler implements CommandExecutor {

    private ResultSet rs;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getLabel().equals("getdeaths")) {
            if(sender instanceof Player player && player.hasPermission("svp.getdeaths")) {
                try {
                    List<Pair<String, String>> results = StaffvsPlayers.database.getKillers();

                    String message = results.stream()
                            .map(p -> String.format(ChatColor.GREEN + "%s " + ChatColor.WHITE + "killed " + ChatColor.RED + "%s", p.second(), p.first()))
                            .collect(Collectors.joining("\n"));
                    
                    player.sendMessage(ChatColor.RED + "-- Staff Deaths --");
                    player.sendMessage(message);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if(command.getLabel().equals("add")) {
            if(sender instanceof Player player && player.hasPermission("svp.staff")) {
                StaffvsPlayers.staff.add(player.getName());
                player.sendMessage(ChatColor.GREEN + "Added to Staff List");
            }
        }

        return true;
    }
}
