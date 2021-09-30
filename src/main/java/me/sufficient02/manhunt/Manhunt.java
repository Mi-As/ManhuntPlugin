package me.sufficient02.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;

public final class Manhunt extends JavaPlugin {

    public Player huntedPlayer;
    public int COMPASS_POSITION = 8;

    private static Manhunt instance;
    public static Manhunt getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(new ManhuntTrackListener(), this);
        Objects.requireNonNull(getCommand("track")).setExecutor(new ManhuntTrackCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // --------- COMPASS HELP FUNCTIONS ---------

    public void setCompass(Player player) {
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();

        if (compassMeta == null) { return; }
        compassMeta.setDisplayName(huntedPlayer.getDisplayName() + " tracker");
        compassMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        // Set Loadstone
        compassMeta.setLodestoneTracked(false);
        compassMeta.setLodestone(huntedPlayer.getLocation());

        compass.setItemMeta(compassMeta);
        player.getInventory().setItem(COMPASS_POSITION, compass);
        player.updateInventory();
    }

    public void updateCompassLocation(Player player) {

        if (this.huntedPlayer == null) { return; }

        ItemStack compass = player.getInventory().getItem(COMPASS_POSITION);

        if (compass == null) {
            System.out.println("Error, no compass found! Cannot update compass location!");
            return;
        }
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();

        if (compassMeta == null) { return; }
        compassMeta.setLodestoneTracked(false);
        compassMeta.setLodestone(this.huntedPlayer.getLocation());

        compass.setItemMeta(compassMeta);
        player.updateInventory();
    }

    // --------- PLAYER HELP FUNCTIONS ---------

    public void resetPlayer(Player player) {
        // TODO: search for better solution player = new Player() ?
        Location spawn = Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation();
        player.getInventory().clear();

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(0);

        player.setCompassTarget(spawn);
        player.teleport(spawn);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }

}
