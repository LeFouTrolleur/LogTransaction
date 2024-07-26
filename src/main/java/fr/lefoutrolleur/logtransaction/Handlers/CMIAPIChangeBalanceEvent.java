package fr.lefoutrolleur.logtransaction.Handlers;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.events.CMIUserBalanceChangeEvent;
import fr.lefoutrolleur.logtransaction.Holders.LogInventoryHolder;
import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.fusesource.jansi.Ansi;

import static fr.lefoutrolleur.logtransaction.LogTransaction.log;

@Deprecated(forRemoval = true)
public class CMIAPIChangeBalanceEvent implements Listener {

    @EventHandler
    public void onChangeBalanceEvent(CMIUserBalanceChangeEvent event){
        CMIUser user = event.getUser();
        log(Ansi.ansi().a(user.getBalance()));
        log(Ansi.ansi().a(event.getTo()));
        log(Ansi.ansi().a(event.getFrom()));
        float transaction_amount = (float) (event.getTo() - event.getFrom());
        // DEBUG
        LogTransaction.log(Ansi.ansi().a("CMIAPIChangeBalanceEvent: " + transaction_amount));

        DatabaseQuery database = LogTransaction.getDatabase();
        Transaction transaction = new Transaction(user.getUniqueId(),transaction_amount,System.currentTimeMillis(), "money");
        database.saveData(transaction);
    }
}
