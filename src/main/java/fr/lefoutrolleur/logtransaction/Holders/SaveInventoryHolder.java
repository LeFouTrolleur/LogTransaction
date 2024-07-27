package fr.lefoutrolleur.logtransaction.Holders;

import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import fr.lefoutrolleur.logtransaction.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static fr.lefoutrolleur.logtransaction.utils.ItemsLib.*;

public class SaveInventoryHolder implements InventoryHolder {
    final Inventory inv;
    final OfflinePlayer player;
    final String currency;
    ArrayList<Transaction> transactions;
    final Player sender;
    LogTransaction plugin;
    @Override
    public @NotNull Inventory getInventory() {
        return inv;

    }
    public SaveInventoryHolder(LogTransaction plugin, String currency, OfflinePlayer target, List<Transaction> transactions, Player sender) {
        this.inv = plugin.getServer().createInventory(this, 9*3, String.format("Logs - %s - %s",target.getName(),currency));
        this.player = target;
        this.currency = currency;
        this.plugin = plugin;
        this.sender = sender;
        this.transactions = new ArrayList<>(transactions);
        loadInventory();
    }
    public void loadInventory(){

        inv.setItem(13, getDateItemStack(transactions.getLast().getTimestamp(), transactions.getFirst().getTimestamp(), transactions.size()));
        inv.setItem(22, new ItemBuilder(Material.EMERALD).name("§aLancer la sauvegarde").toItemStack());
        for(int i = 0;i<9*3;++i) {
            if(inv.getItem(i) == null){
                inv.setItem(i,BLACK_GLASS);
            }
        }
    }
    public void save(){
        File folder = new File(plugin.getDataFolder(),"/saves");
        if(!folder.exists()){
            folder.mkdir();
        }
        Date date = new Date();
        String filename = (player.getName() + "_" + currency + "_" + DateFormat.getDateInstance().format(date) + "_" + DateFormat.getTimeInstance().format(date) + ".txt").replace(":",".");
        File file = new File(folder,filename);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            long now = System.currentTimeMillis();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.format("Date de la sauvegarde: %s",DateFormat.getDateInstance().format(new Date())) + " | Heure de la sauvegarde: " + DateFormat.getTimeInstance().format(new Date()) + "| Devise: " + currency +" | Demande de " +  sender.getName()+ " | Joueur: " + player.getName() + "\n");
            for(Transaction t : transactions){
                Date transaction_date = new Date(t.getTimestamp());
                writer.write(String.format("[%s] %s | Avant: %s | Après: %s | Montant: %s", DateFormat.getDateInstance().format(transaction_date),DateFormat.getTimeInstance().format(transaction_date),t.getBeforeBalance(),t.getAfterBalance(),t.getTransaction()) + "\n");
            }
            writer.write("Temps de la sauvegarde: " + (System.currentTimeMillis() - now) + "ms");
            writer.close();
            sender.sendMessage("§aSauvegarde effectuée","§7Fichier : /saves/" + file.getName());
            plugin.getLogger().info("Sauvegarde de " + player.getName() + " effectuée par " + sender.getName());
        } catch (IOException e) {
            sender.sendMessage("§cImpossible de sauvegarder les logs. Plus d'information dans la console.");
            throw new RuntimeException(e);
        }
    }

    public OfflinePlayer getPlayer() {
        return player;
    }
}
