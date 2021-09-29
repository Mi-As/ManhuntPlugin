package me.sufficient02.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ManhuntTrackCommand  implements CommandExecutor {

    private static final Manhunt plugin = Manhunt.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 1) {
            sendMessageToSender(sender, "Please specify a player you want to hunt: /track playername");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sendMessageToSender(sender, "Invalid player! Try again.");
            return false;
        }

        if (plugin.huntedPlayer != null) {
            sendMessageToSender(sender, "Cannot set hunted player! "
                    + plugin.huntedPlayer.getDisplayName() + " is currently being hunted");
            return false;
        }

        // set hunted player
        plugin.huntedPlayer = player;

        // get compass
        ItemStack compass = plugin.getCompass();
        if (compass == null) {
            System.out.println("An internal error occurred, cannot create compass");
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            // clear inventory
            onlinePlayer.getInventory().clear();

            // add compass
            if (onlinePlayer != plugin.huntedPlayer ) {
                onlinePlayer.getInventory().setItem(8, compass);
                onlinePlayer.setCompassTarget(plugin.huntedPlayer.getLocation());
                onlinePlayer.updateInventory();
            }

        }
        Bukkit.broadcastMessage(ChatColor.GREEN + "The hunted player is " + player.getDisplayName()
                + "! Tracker set, Letsgooooo!");
        return true;
    }

    private void sendMessageToSender(CommandSender sender, String msg) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(msg);
        } else {
            System.out.println(msg);
        }
    }
}
