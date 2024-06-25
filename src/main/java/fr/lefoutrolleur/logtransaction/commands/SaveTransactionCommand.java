package fr.lefoutrolleur.logtransaction.commands;

import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.fusesource.jansi.Ansi;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static fr.lefoutrolleur.logtransaction.LogTransaction.sendError;

public class SaveTransactionCommand implements CommandExecutor, TabCompleter {


    DatabaseQuery database;
    final String permission = "logTransaction.command.admin.save";
    public SaveTransactionCommand() {
        this.database = LogTransaction.getInstance().database;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(!player.hasPermission(permission)){
                return false;
            }
        }
        if(args.length == 2){
            String playerName = args[0];
            String transactionText = args[1];
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
            if(player.hasPlayedBefore() || player.isOnline()){
                database.saveData(player.getUniqueId(), transactionText,System.currentTimeMillis());
            } else {
                sendError(sender, "Player not found");
            }
        } else {
            sendError(sender, "Usage : /save <player> <transaction>");
        }
        return true;
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
                        return List.of("<transaction>");
                    }
                }
            }
        }
        return null;
    }
}
