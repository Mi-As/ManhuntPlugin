package me.sufficient02.manhunt;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class ManhuntLockItem implements Listener {

    private static final Manhunt plugin = Manhunt.getInstance();

    @EventHandler
    public void lockClick(InventoryClickEvent event) {
        Player clickedPlayer = (Player) event.getWhoClicked();
        // if (event.getSlot() == plugin.COMPASS_POSITION &&
        //        plugin.huntedPlayer != null && clickedPlayer != plugin.huntedPlayer) {
        if (event.getSlot() == plugin.COMPASS_POSITION) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player droppedPlayer = event.getPlayer();
        int handSlot = droppedPlayer.getInventory().getHeldItemSlot();
        // if (handSlot == plugin.COMPASS_POSITION &&
        //        plugin.huntedPlayer != null && droppedPlayer != plugin.huntedPlayer) {
        if (handSlot == plugin.COMPASS_POSITION) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player swappedPlayer = event.getPlayer();
        int handSlot = swappedPlayer.getInventory().getHeldItemSlot();
        // if (handSlot == plugin.COMPASS_POSITION &&
        //        plugin.huntedPlayer != null && swappedPlayer != plugin.huntedPlayer) {
        if (handSlot == plugin.COMPASS_POSITION) {
            event.setCancelled(true);
        }
    }
}
