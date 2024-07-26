package fr.lefoutrolleur.logtransaction.Handlers.Inventory;

import fr.lefoutrolleur.logtransaction.Holders.LogInventoryHolder;
import fr.lefoutrolleur.logtransaction.utils.ItemsLib;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import static fr.lefoutrolleur.logtransaction.utils.ItemsLib.SORT_ITEM_NAME;

public class LogInventoryHandler implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getHolder() == null) return;
        if(event.getCurrentItem() == null) return;
        if (!(event.getClickedInventory().getHolder() instanceof LogInventoryHolder holder)) return;
        ItemStack item = event.getCurrentItem();
        event.setCancelled(true);
        if(item.getItemMeta().getDisplayName().equals(ItemsLib.NEXT_PAGE.getItemMeta().getDisplayName())){
            holder.nextPage();
        } else if(item.getItemMeta().getDisplayName().equals(ItemsLib.PREVIOUS_PAGE.getItemMeta().getDisplayName())){
            holder.previousPage();
        }
        int sortType = holder.getSortType();
        if(item.getItemMeta().getDisplayName().endsWith(SORT_ITEM_NAME)){
            if(sortType == 3){
                sortType = 0;
            } else sortType++;
            holder.setSortType(sortType);
        }
    }
}