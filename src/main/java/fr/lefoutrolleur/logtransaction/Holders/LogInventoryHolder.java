package fr.lefoutrolleur.logtransaction.Holders;

import com.google.common.collect.Lists;
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

import java.util.*;
import java.util.stream.Collectors;

import static fr.lefoutrolleur.logtransaction.utils.ItemsLib.*;

public class LogInventoryHolder implements InventoryHolder {

    final Inventory inv;
    final String currency;
    final OfflinePlayer player;
    final List<Transaction> all_transactions;
    List<Transaction> showed_transactions;
    private final HashMap<Integer,List<Transaction>> sorted_transactions = new HashMap<>();
    public final int MAX_TRANSACTIONS_PER_PAGE = 9*4;

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
        for(int i = 0;i<9;++i) {
            inv.setItem(i,BLACK_GLASS);
        }
        inv.setItem(4, ItemsLib.getPlayerHeadItemStack(player, all_transactions.size()));
        if(!all_transactions.isEmpty()){
            inv.setItem(2, new ItemBuilder(Material.MAP).name("§aSauvegarder").lore("§7Cliquez pour sauvegarder").toItemStack());
        }
        refresh();
    }
    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
    void refresh(){
        int min = page * MAX_TRANSACTIONS_PER_PAGE;
        int max = Math.min(min + MAX_TRANSACTIONS_PER_PAGE, all_transactions.size());
        List<Transaction> transactionsList = switch (sortType) {
            case 0 -> sorted_transactions.getOrDefault(0,Lists.reverse(getSortedTransactionsByDate(all_transactions)));
            case 1 -> sorted_transactions.getOrDefault(1,getSortedTransactionsByDate(all_transactions));
            case 2 -> sorted_transactions.getOrDefault(2,getSortedTransactionsByAmount(all_transactions));
            case 3 -> sorted_transactions.getOrDefault(3,Lists.reverse(getSortedTransactionsByAmount(all_transactions)));
            default -> new ArrayList<>();
        };
        if(sorted_transactions.get(sortType) == null || sorted_transactions.get(sortType).isEmpty()){
            sorted_transactions.put(sortType, transactionsList);
        }
        this.showed_transactions = transactionsList.subList(min,max);

        if(all_transactions.isEmpty()){
            inv.setItem(31, ItemsLib.EMPTY_TRANSACTION);
        } else {
            for(int i = 0;i<MAX_TRANSACTIONS_PER_PAGE;++i) {
                if(i < showed_transactions.size()) {
                    Transaction transaction = showed_transactions.get(i);
                    inv.setItem(i+9, ItemsLib.fromTransaction(transaction,i+(page*MAX_TRANSACTIONS_PER_PAGE)+1,all_transactions.size()));
                } else {
                    inv.setItem(i+9, new ItemStack(Material.AIR));
                }
            }
            inv.setItem(48, page > 0 ? ItemsLib.PREVIOUS_PAGE : new ItemStack(Material.AIR));
            inv.setItem(50, (page + 1) * MAX_TRANSACTIONS_PER_PAGE < all_transactions.size() ? ItemsLib.NEXT_PAGE : new ItemStack(Material.AIR));
            inv.setItem(6, getSortItemStack(sortType));
            inv.setItem(49, getPageNumberItemStack(page + 1));
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
        page = 0;
        this.sortType = sortType;
        refresh();
    }
    public List<Transaction> getSortedTransactionsByDate(List<Transaction> transactions){
        return transactions.stream()
                .sorted(Comparator.comparingLong(Transaction::getTimestamp))
                .collect(Collectors.toList());
    }
    public List<Transaction> getSortedTransactionsByAmount(List<Transaction> transactions){
        return transactions.stream()
                .sorted(((o1, o2) -> Float.compare(Math.abs(o1.getTransaction()),Math.abs(o2.getTransaction()))))
                .collect(Collectors.toList());
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public String getCurrency() {
        return currency;
    }

    public int getPage() {
        return page;
    }

    public List<Transaction> getAll_transactions() {
        return all_transactions;
    }
}
