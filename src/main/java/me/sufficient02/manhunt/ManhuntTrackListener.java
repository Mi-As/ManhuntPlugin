package me.sufficient02.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class ManhuntTrackListener implements Listener {

    private static final Manhunt plugin = Manhunt.getInstance();
    private boolean updatePosition = true;

    // --------- HUNTED PLAYER EVENTS ---------
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player movePlayer = e.getPlayer();

        if (plugin.huntedPlayer == null || movePlayer != plugin.huntedPlayer || !updatePosition) {
            return;
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            plugin.updateCompassLocation(onlinePlayer);
        }
    }

    /*@EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        Player clickPlayer = e.getPlayer();
        Action action = e.getAction();

        //if (plugin.huntedPlayer == null || clickPlayer == plugin.huntedPlayer) {
        //    return;
        //}

        // update compass position
        boolean isRightClick = (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK);
        boolean isCompass = (clickPlayer.getInventory().getItemInMainHand().getType() == Material.COMPASS);
        if (isRightClick && isCompass && updatePosition) {
            plugin.updateCompassLocation(clickPlayer);
            clickPlayer.sendMessage("Tracker updated!");
        }
    }*/

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player deadPlayer = e.getEntity().getPlayer();
        if (plugin.huntedPlayer != null && deadPlayer == plugin.huntedPlayer) {
            plugin.huntedPlayer = null;
            Bukkit.broadcastMessage(ChatColor.RED + "Hunted player died, reset trackers...");

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.setGameMode(GameMode.ADVENTURE);
            }
        }
    }

    // --------- ALL PLAYERS EVENTS ---------

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {

        if (plugin.huntedPlayer == null) {
            return;
        }
        updatePosition = (e.getPlayer().getWorld().getEnvironment() ==
                plugin.huntedPlayer.getWorld().getEnvironment());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player joinPlayer = e.getPlayer();
        if (plugin.huntedPlayer == null) {
            plugin.resetPlayer(joinPlayer);
            joinPlayer.sendMessage(ChatColor.GREEN + "Welcome to Manhunt :)");
            joinPlayer.setGameMode(GameMode.ADVENTURE);
        } else {
            joinPlayer.sendMessage(ChatColor.GREEN + "Welcome back! Game is still running");
            joinPlayer.setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player respawnPlayer = e.getPlayer();
        if (plugin.huntedPlayer == null || respawnPlayer == plugin.huntedPlayer) {
            return;
        }
        respawnPlayer.setGameMode(GameMode.SURVIVAL);

        // set compass // todo updatePosition hier auch beachten
        plugin.setCompass(respawnPlayer);
    }

    @EventHandler
    public void lockCompass(InventoryClickEvent e) {
        Player clickedPlayer = (Player) e.getWhoClicked();
        if (plugin.huntedPlayer == null || clickedPlayer == plugin.huntedPlayer) {
            return;
        }

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem != null && clickedItem.getType() == Material.COMPASS) {
            e.setCancelled(true);
        }
    }

}
