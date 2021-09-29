package me.sufficient02.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class ManhuntTrackListener implements Listener {

    private static final Manhunt plugin = Manhunt.getInstance();

    /*@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player movePlayer = e.getPlayer();

        if (plugin.huntedPlayer == null || movePlayer != plugin.huntedPlayer) {
            return;
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer != plugin.huntedPlayer ) {
                onlinePlayer.setCompassTarget(plugin.huntedPlayer.getLocation());
            }
        }
    }*/

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Action action = e.getAction();

        if (plugin.huntedPlayer == null || player == plugin.huntedPlayer) {
            return;
        }

        // update compass position
        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) &&
                player.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
            player.setCompassTarget(plugin.huntedPlayer.getLocation());
            player.sendMessage("Tracker updated!");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (plugin.huntedPlayer == null) {
            e.getPlayer().sendMessage(ChatColor.GREEN + "Welcome to Manhunt :)");
            e.getPlayer().getInventory().clear();
        } else {
            e.getPlayer().sendMessage(ChatColor.GREEN + "Welcome back! Game is still running");
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (plugin.huntedPlayer == null) {
            return;
        }

        if (e.getEntity().getPlayer() == plugin.huntedPlayer) {
            plugin.huntedPlayer = null;
            Bukkit.broadcastMessage(ChatColor.RED + "Hunted player died, reset trackers...");

        } else {
            e.getEntity().getPlayer().setCompassTarget(plugin.huntedPlayer.getLocation());
        }
    }
}
