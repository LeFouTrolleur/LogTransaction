package fr.lefoutrolleur.logtransaction.Handlers.Inventory;

import fr.lefoutrolleur.logtransaction.Holders.SaveInventoryHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static fr.lefoutrolleur.logtransaction.Holders.SaveInventoryHolder.*;


public class SaveInventoryHandler implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getHolder() == null) return;
        if(event.getCurrentItem() == null) return;
        if (!(event.getClickedInventory().getHolder() instanceof SaveInventoryHolder holder)) return;
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        if(item.getType() == Material.EMERALD){
            holder.save();
        }
    }
}
