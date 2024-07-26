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

import static fr.lefoutrolleur.logtransaction.LogTransaction.sendError;

public class RetrievePlayerTransactionCommand implements CommandExecutor, TabCompleter {

    final String permission = "logtransaction.command.admin.retrieveplayer";
    final DatabaseQuery database;
    public RetrievePlayerTransactionCommand(DatabaseQuery database){
        this.database = database;
    }
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
            if(CoinsEngineAPI.getCurrencyManager().getCurrencies().stream().map(Currency::getName).noneMatch(currency::equals)){
                sendError(sender,"Cette devise n'existe pas");
                return false;
            }
            ArrayList<Transaction> transactions = database.retrieveData(uuid,currency);
            LogInventoryHolder logInventoryHandler = new LogInventoryHolder(LogTransaction.getInstance(),currency,transactions,target);
            player.openInventory(logInventoryHandler.getInventory());
            logInventoryHandler.loadInventory();
            return true;
        }
        /*if(args.length >= 2){
            int page = 0;
            if(args.length == 3){
                page = Integer.parseInt(args[2])-1;
            }
            String playerName = args[0];
            String currency = args[1];

            ArrayList<Transaction> transactions = database.retrieveData(uuid);
            if(transactions.isEmpty()){
                sendError(sender, "Aucune transaction n'a été trouvé pour ce joueur");
            } else {
                TransactionMessagesBuilder builder = new TransactionMessagesBuilder(transactions, page);
                if(sender instanceof Player player){
                    int maxPage = transactions.size() / 10 + 1;
                    player.sendMessage("§e--------§6<<<<§2Page §a" + (page + 1) + " §2/ §a" + maxPage + "§6>>>>§e--------");
                    builder.sendToPlayer(player);
                    TextComponent message = Component.text("         ");
                    if(page != 0){
                        TextComponent previousPage = Component.text("<< Page " + page)
                                .color(TextColor.color(144, 0, 71))
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"/rt " + playerName + " " + page));
                        message = message.append(previousPage);
                        if(page+1 != maxPage){
                            message = message.append(Component.text(" | "));
                        }
                    } else {
                        message = message.append(Component.text("            "));
                    }
                    if(page+1 != maxPage){
                        TextComponent nextPage = Component.text("Page " + (page+2) + " >>")
                                .color(TextColor.color(0, 143, 144))
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND,"/rt " + playerName + " " + (page+2)));
                        message = message.append(nextPage);
                    }
                    player.sendMessage(message);
                } else {
                    builder.sendToSender(sender);
                }
            }
        }*/
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
                    return StringUtil.copyPartialMatches(args[1], list, new ArrayList<>());
                }
            }
        }
        return null;
    }
}
