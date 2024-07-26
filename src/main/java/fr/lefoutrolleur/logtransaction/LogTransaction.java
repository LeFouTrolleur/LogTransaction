package fr.lefoutrolleur.logtransaction;

import fr.lefoutrolleur.logtransaction.Handlers.CMIAPIChangeBalanceEvent;
import fr.lefoutrolleur.logtransaction.Handlers.CoinsEngineChangeBalanceEvent;
import fr.lefoutrolleur.logtransaction.Handlers.Inventory.LogInventoryHandler;
import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import fr.lefoutrolleur.logtransaction.commands.RetrievePlayerTransactionCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;

import java.util.HashMap;

public final class LogTransaction extends JavaPlugin {

    static DatabaseQuery database;
    @Override
    public void onLoad() {

        database = new DatabaseQuery(this);

        log(Ansi.ansi().fg(Ansi.Color.WHITE).a("Loading ").fg(Ansi.Color.YELLOW).a("LogTransaction"));
    }
    @Override
    public void onEnable() {

        // Initialize database
        database.init();

        // Register commands
        getCommand("retrievetransaction").setExecutor(new RetrievePlayerTransactionCommand(database));

        // Register Handlers
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new CoinsEngineChangeBalanceEvent(),this);
        manager.registerEvents(new CMIAPIChangeBalanceEvent(),this);
        manager.registerEvents(new LogInventoryHandler(),this);


        log(Ansi.ansi().fg(Ansi.Color.YELLOW).a("LogTransaction").fg(Ansi.Color.WHITE).a(" is enabled"));
    }

    @Override
    public void onDisable() {
        // Save all databases
        database.save();
    }

    public static LogTransaction getInstance(){
        return getPlugin(LogTransaction.class);
    }
    public static void log(Ansi ansi){
        getInstance().getLogger().info(ansi.reset().toString());
    }
    public static void sendError(CommandSender sender, String message){
        if(sender instanceof Player player){
            player.sendMessage("§c" + message);
        } else {
            log(Ansi.ansi().fg(Ansi.Color.RED).a(message));
        }
    }
    public static void sendMessage(CommandSender sender, String message){
        if(sender instanceof Player player){
            player.sendMessage("§a" + message);
        } else {
            log(Ansi.ansi().fg(Ansi.Color.GREEN).a(message));
        }
    }
    public static DatabaseQuery getDatabase(){
        return database;
    }
}
