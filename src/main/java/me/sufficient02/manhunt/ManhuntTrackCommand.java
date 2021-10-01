package me.sufficient02.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ManhuntTrackCommand  implements CommandExecutor {

    private static final Manhunt plugin = Manhunt.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("Only Player can execute the track command");
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Please specify a player you want to hunt!");
            return false;
        }

        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(ChatColor.RED + "Invalid player! Try again.");
            return false;
        }

        Player manhuntPlayer = Bukkit.getPlayer(args[0]);

        if (plugin.huntedPlayer != null) {
            sender.sendMessage(ChatColor.RED + "Cannot set hunted player! "
                    + plugin.huntedPlayer.getDisplayName() + " is currently being hunted");
            return false;
        }

        // set hunted player
        plugin.huntedPlayer = manhuntPlayer;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            plugin.resetPlayer(onlinePlayer);
            onlinePlayer.setGameMode(GameMode.SURVIVAL);

            // add compass
            //if (onlinePlayer != plugin.huntedPlayer) {
                plugin.setCompass(onlinePlayer);
            //}
        }

        Bukkit.broadcastMessage(ChatColor.GREEN + "The hunted player is " + plugin.huntedPlayer.getDisplayName()
                + "! Tracker set, Letsgooooo!");
        return true;
    }

}
