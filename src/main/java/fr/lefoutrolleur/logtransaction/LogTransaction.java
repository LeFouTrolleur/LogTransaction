package fr.lefoutrolleur.logtransaction;

import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import fr.lefoutrolleur.logtransaction.commands.SaveTransactionCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi;

public final class LogTransaction extends JavaPlugin {

    public DatabaseQuery database;

    @Override
    public void onLoad() {
        // Plugin load logic
        log(Ansi.ansi().fg(Ansi.Color.WHITE).a("Loading ").fg(Ansi.Color.YELLOW).a("LogTransaction"));
        this.database = new DatabaseQuery(this);
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        database.init();
        getCommand("savetransaction").setExecutor(new SaveTransactionCommand());
        getCommand("retrievetransaction").setExecutor(new SaveTransactionCommand());
        log(Ansi.ansi().fg(Ansi.Color.YELLOW).a("LogTransaction").fg(Ansi.Color.WHITE).a(" is enabled"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
}
