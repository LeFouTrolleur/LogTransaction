package fr.lefoutrolleur.logtransaction.Handlers;

import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.fusesource.jansi.Ansi;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;
import su.nightexpress.coinsengine.api.event.ChangeBalanceEvent;

import static fr.lefoutrolleur.logtransaction.LogTransaction.log;


public class CoinsEngineChangeBalanceEvent implements Listener {

    @EventHandler
    public void onChangeBalanceEvent(ChangeBalanceEvent event){
        Currency currency = event.getCurrency();
        Player player = event.getPlayer();
        if(player == null) return;
        float transaction_amount = (float) (event.getNewAmount() - event.getOldAmount());
        DatabaseQuery database = LogTransaction.getDatabase();
        if(database == null){
            throw new NullPointerException(currency.getName() + " database not exists. Perhaps the currency has been created after the initialization of databases.");
        }
        Transaction transaction = new Transaction(player.getUniqueId(), transaction_amount, System.currentTimeMillis(),currency.getName());
        // DEBUG
        database.saveData(transaction);
    }
}
