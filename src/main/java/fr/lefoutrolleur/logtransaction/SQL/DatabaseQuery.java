package fr.lefoutrolleur.logtransaction.SQL;

import fr.lefoutrolleur.logtransaction.LogTransaction;
import su.nightexpress.coinsengine.api.CoinsEngineAPI;
import su.nightexpress.coinsengine.api.currency.Currency;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


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
            deleteExcessiveData(currency.getName(), 2, TimeUnit.DAYS);
        }
    }
    public void save(){
        sqliter.closeDb();
    }
    public void saveData(UUID uuid, float transaction, long time, String currency, float before, float after){
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
        if(!sqliter.haveTable(currency)){
            throw new NullPointerException("Table "+currency+" doesn't exists. Perhaps the currency has been created after the initialization of the database.");
        }
        ArrayList<HashMap<String,String>> data = sqliter.runQuery("SELECT * from " + currency + " where UUID='"+uuid.toString()+"';");
        ArrayList<Transaction> result = new ArrayList<>();
        for (HashMap<String,String> hashmap : data) {
            result.add(new Transaction(UUID.fromString(hashmap.get("UUID")),Float.parseFloat(hashmap.get("TRANS")),Long.parseLong(hashmap.get("TIME")),currency,Float.parseFloat(hashmap.get("BEFORE")),Float.parseFloat(hashmap.get("AFTER"))));
        }
        return result;
    }
    private void deleteExcessiveData(String currency, int limit, TimeUnit timeUnit){
        long ms = timeUnit.toMillis(limit);
        long lim = System.currentTimeMillis() - ms;
        sqliter.runQuery("DELETE FROM " + currency + " WHERE TIME < " + lim);
    }
}
