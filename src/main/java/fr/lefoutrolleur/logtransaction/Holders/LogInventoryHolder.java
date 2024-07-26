package fr.lefoutrolleur.logtransaction.Holders;

import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import fr.lefoutrolleur.logtransaction.utils.ItemBuilder;
import fr.lefoutrolleur.logtransaction.utils.ItemsLib;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static fr.lefoutrolleur.logtransaction.utils.ItemsLib.getSortItemStack;

public class LogInventoryHolder implements InventoryHolder {

    final Inventory inv;
    final String currency;
    final OfflinePlayer player;
    final List<Transaction> all_transactions;
    List<Transaction> showed_transactions;
    public final int MAX_TRANSACTIONS_PER_PAGE = 9*4;

    private boolean sortingRequired = true;
    int page = 0;

    /*
     * 0 = DATE_ASCENDING
     * 1 = DATE_DESCENDING
     * 2 = AMOUNT_ASCENDING
     * 3 = AMOUNT_DESCENDING
     */
    private int sortType = 0;

    public LogInventoryHolder(LogTransaction plugin, String currency, ArrayList<Transaction> transactions, OfflinePlayer player) {
        this.currency = currency;
        this.player = player;
        this.all_transactions = transactions;
        this.inv = plugin.getServer().createInventory(this,9*6,String.format("Logs - %s - %s",player.getName(),currency));
    }
    public void loadInventory(){
        ItemStack item = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(" ").toItemStack();
        for(int i = 0;i<9;++i) {
            inv.setItem(i,item);
        }
        refresh();
    }
    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
    void refresh(){
        if(sortingRequired) {
            Stream<Transaction> stream = all_transactions.stream();
            // Data flow issues -> Need improvements
            int min = page * MAX_TRANSACTIONS_PER_PAGE;
            int max = min + MAX_TRANSACTIONS_PER_PAGE;
            stream = stream.skip(min).limit(max - min);
            List<Transaction> sortedTransactions;
            if (sortType <= 1) {
                sortedTransactions = new ArrayList<>(stream.sorted(Comparator.comparingLong(Transaction::getTimestamp)).toList());
                if (sortType == 0) {
                    Collections.reverse(sortedTransactions);
                }
            } else {
                sortedTransactions = new ArrayList<>(stream.sorted((o1, o2) -> Float.compare(Math.abs(o1.getTransaction()), Math.abs(o2.getTransaction()))).toList());
                if (sortType == 3) {
                    Collections.reverse(sortedTransactions);
                }
            }
            this.showed_transactions = sortedTransactions;

            sortingRequired = false;
        }
        if(all_transactions.isEmpty()){
            inv.setItem(31, ItemsLib.EMPTY_TRANSACTION);
        } else {
            for(int i = 0;i<MAX_TRANSACTIONS_PER_PAGE;++i) {
                if(i >= showed_transactions.size()) break;
                Transaction transaction = showed_transactions.get(i);
                inv.setItem(i+9, ItemsLib.fromTransaction(transaction));
            }
            if(page > 0){
                inv.setItem(48, ItemsLib.PREVIOUS_PAGE);
            }
            if((page+1)*MAX_TRANSACTIONS_PER_PAGE < all_transactions.size()){
                inv.setItem(50, ItemsLib.NEXT_PAGE);
            }
            inv.setItem(6,getSortItemStack(sortType));
        }

    }
    public void nextPage(){
        page++;
        refresh();
    }
    public void previousPage(){
        page--;
        refresh();
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        if(sortType == this.sortType) return;
        sortingRequired = true;
        page = 0;
        this.sortType = sortType;
        refresh();
    }

}
