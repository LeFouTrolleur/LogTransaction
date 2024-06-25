package fr.lefoutrolleur.logtransaction.commands;

import fr.lefoutrolleur.logtransaction.Builder.TransactionMessagesBuilder;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static fr.lefoutrolleur.logtransaction.LogTransaction.sendError;
import static fr.lefoutrolleur.logtransaction.LogTransaction.sendMessage;

public class RetrievePlayerTransactionCommand implements CommandExecutor, TabCompleter {

    final String permission = "logtransaction.command.admin.retrieveplayer";
    DatabaseQuery database;
    public RetrievePlayerTransactionCommand() {
        this.database = LogTransaction.getInstance().database;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(!player.hasPermission(permission)) {
                return false;
            }
        }
        if(args.length >= 1){
            int page = 0;
            if(args.length == 2){
                page = Integer.parseInt(args[1]);
            }
            String playerName = args[0];
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
            UUID uuid = player.getUniqueId();
            ArrayList<Transaction> transactions = database.retrieveData(uuid);
            sendMessage(sender, "Chargement du joueur : " + player.getName());
            if(transactions.isEmpty()){
                sendError(sender, "Aucune transaction n'a été trouvé pour ce joueur");
            } else {
                TransactionMessagesBuilder builder = new TransactionMessagesBuilder(transactions, page);
                if(sender instanceof Player){
                    builder.sendToPlayer((Player) sender);
                } else {
                    builder.sendToSender(sender);
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(player.hasPermission(permission)){
                if(args.length == 1){
                    if(!args[0].isEmpty()){
                        ArrayList<String> list = new ArrayList<>();
                        list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).toList());
                        list.addAll(Arrays.stream(Bukkit.getOfflinePlayers()).map(OfflinePlayer::getName).toList());
                        return StringUtil.copyPartialMatches(args[0], list, new ArrayList<>());
                    }
                    return List.of("<player>");
                } else if(args.length == 2){
                    if(args[1].isEmpty()){
                        return List.of("(page)");
                    }
                }
            }
        }
        return null;
    }
}
