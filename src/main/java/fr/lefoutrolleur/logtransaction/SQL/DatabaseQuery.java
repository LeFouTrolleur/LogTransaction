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

import static fr.lefoutrolleur.logtransaction.LogTransaction.log;


public class DatabaseQuery {




    private final Sqliter sqliter;
    final String databaseName;
    final Logger logger;
    public static final String MONEY = "money";
    private final String UUID_TABLE_NAME = "UUID";
    private final String TRANSACTION_NAME = "TRANS";
    private final String TIME_TABLE_NAME = "TIME";
    private final String BEFORE_TABLE_NAME = "BEFORE";
    private final String AFTER_TABLE_NAME = "AFTER";
    private final String SOURCE_TABLE_NAME = "SOURCE";
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
            loadTable(currency.getName());
        }
        loadTable(MONEY);
    }
    private void loadTable(String table){
        if(!sqliter.haveTable(table)){
            logger.info("Creating "+table+" table for database...");
            HashMap<String,String> tableData = new HashMap<>();
            tableData.put(UUID_TABLE_NAME,"TEXT NOT NULL");
            tableData.put(TRANSACTION_NAME,"BIGINT NOT NULL");
            tableData.put(TIME_TABLE_NAME, "BIGINT NOT NULL");
            tableData.put(BEFORE_TABLE_NAME,"BIGINT NOT NULL");
            tableData.put(AFTER_TABLE_NAME,"BIGINT NOT NULL");
            tableData.put(SOURCE_TABLE_NAME,"TEXT");
            sqliter.createTable(table,tableData);
        }
        deleteExcessiveData(table, 2, TimeUnit.DAYS);
    }
    public void save(){
        sqliter.closeDb();
    }
    public void saveData(UUID uuid, float transaction, long time, String currency, float before, float after, String source){
        if(!sqliter.haveTable(currency)){
            loadTable(currency);
        }
        HashMap<String,String> data = new HashMap<>();
        data.put(UUID_TABLE_NAME,uuid.toString());
        data.put(TRANSACTION_NAME, String.valueOf(transaction));
        data.put(TIME_TABLE_NAME,String.valueOf(time));
        data.put(BEFORE_TABLE_NAME, String.valueOf(before));
        data.put(AFTER_TABLE_NAME, String.valueOf(after));
        data.put(SOURCE_TABLE_NAME, source);
        sqliter.insertData(currency,data);
    }
    public void saveData(Transaction transaction){
        saveData(transaction.getUuid(),transaction.getTransaction(),transaction.getTimestamp(), transaction.getCurrency(),transaction.getBeforeBalance(),transaction.getAfterBalance(),transaction.getSource());
    }
    public ArrayList<Transaction> retrieveData(UUID uuid, String currency){
        if(!sqliter.haveTable(currency)){
            loadTable(currency);
        }
        ArrayList<HashMap<String,String>> data = sqliter.runQuery("SELECT * from " + currency + " where UUID='"+uuid.toString()+"';");
        ArrayList<Transaction> result = new ArrayList<>();
        for (HashMap<String,String> hashmap : data) {
            result.add(new Transaction(UUID.fromString(hashmap.get(UUID_TABLE_NAME)),Float.parseFloat(hashmap.get(TRANSACTION_NAME)),Long.parseLong(hashmap.get(TIME_TABLE_NAME)),currency,Float.parseFloat(hashmap.get(BEFORE_TABLE_NAME)),Float.parseFloat(hashmap.get(AFTER_TABLE_NAME)),hashmap.get(SOURCE_TABLE_NAME)));
        }
        return result;
    }
    private void deleteExcessiveData(String currency, int limit, TimeUnit timeUnit){
        long ms = timeUnit.toMillis(limit);
        long lim = System.currentTimeMillis() - ms;
        sqliter.runQuery("DELETE FROM " + currency + " WHERE " + TIME_TABLE_NAME + " < " + lim);
    }
}
