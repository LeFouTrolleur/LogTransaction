package fr.lefoutrolleur.logtransaction;

import fr.lefoutrolleur.logtransaction.Handlers.CMIAPIChangeBalanceEvent;
import fr.lefoutrolleur.logtransaction.Handlers.CoinsEnginesChangeBalanceEvent;
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

    private static final HashMap<String,DatabaseQuery> databases = new HashMap<>();

    @Override
    public void onLoad() {
        log(Ansi.ansi().fg(Ansi.Color.WHITE).a("Loading ").fg(Ansi.Color.YELLOW).a("LogTransaction"));
        // Load Main money database
        databases.put("money", new DatabaseQuery(this,"money"));

        // Load all currencies databases
        CoinsEngineAPI.getCurrencyManager().getCurrencies().forEach(currency -> {
            databases.put(currency.getName(), new DatabaseQuery(this,currency.getName()));
        });
    }
    @Override
    public void onEnable() {
        // Initialize all databases
        databases.values().forEach(DatabaseQuery::init);

        // Register commands
        getCommand("retrievetransaction").setExecutor(new RetrievePlayerTransactionCommand());

        // Register Handlers
        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new CoinsEnginesChangeBalanceEvent(),this);
        manager.registerEvents(new CMIAPIChangeBalanceEvent(),this);

        log(Ansi.ansi().fg(Ansi.Color.YELLOW).a("LogTransaction").fg(Ansi.Color.WHITE).a(" is enabled"));
    }

    @Override
    public void onDisable() {
        // Save all databases
        databases.values().forEach(DatabaseQuery::save);
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
    public static DatabaseQuery getDatabase(String currency){
        return databases.get(currency);
    }
}
