package fr.lefoutrolleur.logtransaction.Builder;

import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static fr.lefoutrolleur.logtransaction.LogTransaction.log;

public class TransactionMessagesBuilder {


    private final ArrayList<Transaction> transactions;
    int page;
    public TransactionMessagesBuilder(ArrayList<Transaction> transactions, int page) {
        this.transactions = transactions;
        this.page = page;
    }

    public void sendToPlayer(Player player){
        for(int i = 0; i < 10; i++){
            int n = i + page * 10;
            if(n < transactions.size()){
                Transaction transaction = transactions.get(n);
                Date date = new Date(transaction.getTimestamp());
                player.sendMessage("§9" + DateFormat.getDateInstance().format(date) + "  " + DateFormat.getTimeInstance().format(date) + " §f-> §a" + transaction.getTransaction() + "§f | ID -> §b" + transaction.getId());
            } else {
                break;
            }
        }
    }
    public void sendToSender(CommandSender sender){
        for(int i = 0; i < 10; i++){
            int n = i + page * 10;
            if(n <= transactions.size()){
                Transaction transaction = transactions.get(n);
                Date date = new Date(transaction.getTimestamp());
                log(Ansi.ansi().fg(Ansi.Color.GREEN).a(DateFormat.getDateInstance().format(date) + " -> " + transaction.getTransaction() + " | ID -> " + transaction.getId()));
            } else {
                break;
            }
        }
    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
