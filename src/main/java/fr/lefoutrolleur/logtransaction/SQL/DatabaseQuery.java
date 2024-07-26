package fr.lefoutrolleur.logtransaction.SQL;

import fr.lefoutrolleur.logtransaction.LogTransaction;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import static fr.lefoutrolleur.logtransaction.LogTransaction.log;
import static org.fusesource.jansi.Ansi.ansi;

public class DatabaseQuery {




    private final Sqliter sqliter;
    final String databaseName;
    final Logger logger;
    public DatabaseQuery(LogTransaction transaction) {
        this.databaseName = "LogTransaction.db";
        this.logger = transaction.getLogger();
        File databaseFile = new File(transaction.getDataFolder(),databaseName);
        if(!databaseFile.exists()) {
            logger.info("Creating database...");
            transaction.saveResource(databaseName, false);
        } else {
            logger.info("Initializing database...");
        }
        sqliter = new Sqliter(transaction.getDataFolder().getAbsolutePath() + "/" + databaseName);
    }

    public void init(){
        for (Currency currency : CoinsEngineAPI.getCurrencyManager().getCurrencies()) {
            if(!sqliter.haveTable(currency.getName())){
                logger.info("Creating "+currency.getName()+" table for database...");
                HashMap<String,String> tableData = new HashMap<>();
                tableData.put("UUID","TEXT NOT NULL");
                tableData.put("TRANS","BIGINT NOT NULL");
                tableData.put("TIME", "BIGINT NOT NULL");
                tableData.put("BEFORE","BIGINT");
                tableData.put("AFTER","BIGINT");
                sqliter.createTable(currency.getName(),tableData);
            }
        }
    }
    public void save(){
        sqliter.closeDb();
    }
    public void saveData(UUID uuid, float transaction, long time, String currency, float before, float after){
        log(ansi().a("Saving data for "+uuid+" "+transaction+" "+time));
        HashMap<String,String> data = new HashMap<>();
        data.put("UUID",uuid.toString());
        data.put("TRANS", String.valueOf(transaction));
        data.put("TIME",String.valueOf(time));
        data.put("BEFORE", String.valueOf(before));
        data.put("AFTER", String.valueOf(after));
        sqliter.insertData(currency,data);
    }
    public void saveData(Transaction transaction){
        saveData(transaction.getUuid(),transaction.getTransaction(),transaction.getTimestamp(), transaction.getCurrency(),transaction.getBeforeBalance(),transaction.getAfterBalance());
    }
    public ArrayList<Transaction> retrieveData(UUID uuid, String currency){
        ArrayList<HashMap<String,String>> data = sqliter.runQuery("SELECT * from " + currency + " where UUID='"+uuid.toString()+"';");
        ArrayList<Transaction> result = new ArrayList<>();
        for (HashMap<String,String> hashmap : data) {
            result.add(new Transaction(UUID.fromString(hashmap.get("UUID")),Float.parseFloat(hashmap.get("TRANS")),Long.parseLong(hashmap.get("TIME")),currency,Float.parseFloat(hashmap.get("BEFORE")),Float.parseFloat(hashmap.get("AFTER"))));
        }
        return result;
    }
}
