package me.sufficient02.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class Manhunt extends JavaPlugin {

    public Player huntedPlayer;

    private static Manhunt instance;
    public static Manhunt getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getPluginManager().registerEvents(new ManhuntTrackListener(), this);
        getCommand("track").setExecutor(new ManhuntTrackCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public ItemStack getCompass() {
        if (huntedPlayer == null) {
            return null;
        }
        // create compass
        ItemStack compass = new ItemStack(Material.COMPASS, 1);
        ItemMeta compassMeta = compass.getItemMeta();
        if (compassMeta != null) {
            compassMeta.setDisplayName(huntedPlayer.getDisplayName() + " tracker");
            compassMeta.addEnchant(Enchantment.BINDING_CURSE, 1, true);
        }
        compass.setItemMeta(compassMeta);

        return compass;
    }

}
