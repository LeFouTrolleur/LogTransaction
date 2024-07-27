package fr.lefoutrolleur.logtransaction.Handlers;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.events.CMIUserBalanceChangeEvent;
import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static fr.lefoutrolleur.logtransaction.LogTransaction.log;

public class CMIChangeBalanceEvent implements Listener {

    @EventHandler
    public void onChangeBalanceEvent(CMIUserBalanceChangeEvent event){
        CMIUser user = event.getUser();
        float transaction_amount = (float) (event.getTo() - event.getFrom());
        if(transaction_amount == 0f) return;
        DatabaseQuery database = LogTransaction.getDatabase();
        Transaction transaction = new Transaction(event.getUser().getUniqueId(), transaction_amount, System.currentTimeMillis(),DatabaseQuery.MONEY, (float) event.getFrom(), (float) event.getTo(), event.getSource() == null ? "Serveur" : event.getSource().getName());
        database.saveData(transaction);
    }
}
