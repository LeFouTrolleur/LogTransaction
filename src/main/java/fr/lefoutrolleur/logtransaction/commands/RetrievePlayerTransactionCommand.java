package fr.lefoutrolleur.logtransaction.commands;

import fr.lefoutrolleur.logtransaction.Holders.LogInventoryHolder;
import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static fr.lefoutrolleur.logtransaction.LogTransaction.log;
import static fr.lefoutrolleur.logtransaction.LogTransaction.sendError;

public class RetrievePlayerTransactionCommand implements CommandExecutor, TabCompleter {

    final String permission = "logtransaction.command.admin.retrieveplayer";
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player player)){
            sendError(sender,"Seul un joueur peut exécuter cette commande");
            return false;
        }
        if(!player.hasPermission(permission)) return false;
        if(args.length >= 2){
            String playerName = args[0];
            String currency = args[1];
            OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);
            UUID uuid = target.getUniqueId();
            if(CoinsEngineAPI.getCurrencyManager().getCurrencies().stream().map(Currency::getName).noneMatch(currency::equals) && !currency.equals(DatabaseQuery.MONEY)){
                sendError(sender,"Cette devise n'existe pas");
                return false;
            }
            DatabaseQuery database = LogTransaction.getDatabase();
            ArrayList<Transaction> transactions = database.retrieveData(uuid,currency);
            LogInventoryHolder logInventoryHolder = new LogInventoryHolder(LogTransaction.getInstance(),currency,transactions,target);
            player.openInventory(logInventoryHolder.getInventory());
            logInventoryHolder.loadInventory();
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission(permission)){
                if(args.length == 1) {
                    if (!args[0].isEmpty()) {
                        ArrayList<String> list = new ArrayList<>();
                        list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
                        list.addAll(Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList());
                        return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
                    }
                    return List.of("<player>");
                } else if(args.length == 2){
                    List<String> list = new ArrayList<>(CoinsEngineAPI.getCurrencyManager().getCurrencies().stream().map(Currency::getName).toList());
                    list.add(DatabaseQuery.MONEY);
                    return StringUtil.copyPartialMatches(args[1], list, new ArrayList<>());
                }
            }
        }
        return null;
    }
}
