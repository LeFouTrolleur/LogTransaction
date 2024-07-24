package fr.lefoutrolleur.logtransaction.Handlers;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.events.CMIUserBalanceChangeEvent;
import fr.lefoutrolleur.logtransaction.LogTransaction;
import fr.lefoutrolleur.logtransaction.SQL.DatabaseQuery;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CMIAPIChangeBalanceEvent implements Listener {

    @EventHandler
    public void onChangeBalanceEvent(CMIUserBalanceChangeEvent event){
        CMIUser user = event.getUser();
        StringBuilder builder = new StringBuilder();
        float transaction_amount = (float) (event.getTo() - event.getFrom());
        if(transaction_amount > 0){
            builder.append("§a+ §f").append(transaction_amount);
        } else if(transaction_amount < 0){
            builder.append("§c- §f").append(-transaction_amount);
        }
        builder.append(" from ").append(event.getSource().getName());
        String message = builder.toString();
        DatabaseQuery database = LogTransaction.getDatabase("money");
        database.saveData(user.getUniqueId(), message,System.currentTimeMillis());
    }
}
