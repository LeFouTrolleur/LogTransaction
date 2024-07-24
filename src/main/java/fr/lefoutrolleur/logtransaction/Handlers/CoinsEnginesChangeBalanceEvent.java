package fr.lefoutrolleur.logtransaction.Handlers;

import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import su.nightexpress.coinsengine.api.currency.Currency;
import su.nightexpress.coinsengine.api.event.ChangeBalanceEvent;


public class CoinsEnginesChangeBalanceEvent implements Listener {

    @EventHandler
    public void onChangeBalanceEvent(ChangeBalanceEvent event){
        Currency currency = event.getCurrency();
        Player player = event.getPlayer();
        if(player == null) return;
        float transaction_amount = (float) (event.getNewAmount() - event.getOldAmount());
        StringBuilder builder = new StringBuilder();
        if(transaction_amount > 0){
            builder.append("§a+ §f").append(transaction_amount);
        } else if(transaction_amount < 0){
            builder.append("§c- §f").append(-transaction_amount);
        }
        String message = builder.toString();
        DatabaseQuery database = LogTransaction.getDatabase(currency.getName());
        if(database == null){
            throw new NullPointerException(currency.getName() + " database not exists. Perhaps the currency has been created after the initialization of databases.");
        }
        database.saveData(event.getPlayer().getUniqueId(), message, System.currentTimeMillis());
    }
}
