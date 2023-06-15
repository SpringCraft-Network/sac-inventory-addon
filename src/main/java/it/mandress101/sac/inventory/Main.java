package it.mandress101.sac.inventory;

import it.mandress101.sac.api.checks.enums.CheckType;
import it.mandress101.sac.api.events.SACAlertEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onFlag(SACAlertEvent e) {
        List<String> includedChecks = getConfig().getStringList("included-checks");
        CheckType type = e.getCheck().getType();

        if(includedChecks.contains(type.name())) {
            ItemStack[] items = e.getProtocolPlayer().getPlayer().getInventory().getStorageContents();

            for (ItemStack item : items) {
                if(item.getType() == Material.WRITTEN_BOOK)
                    e.getProtocolPlayer().getPlayer().getInventory().remove(item);
            }
        }
    }
}
