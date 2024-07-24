package fr.lefoutrolleur.logtransaction.SQL;

import fr.lefoutrolleur.logtransaction.LogTransaction;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DatabaseQuery {




    private final Sqliter sqliter;
    final String currency;
    final String databaseName;

    public DatabaseQuery(LogTransaction transaction, String currency) {
        this.currency = currency;
        this.databaseName = "LogTransaction_"+currency+".db";
        if(!new File(transaction.getDataFolder(),databaseName).exists()) {
            transaction.saveResource(databaseName, false);
        }
        sqliter = new Sqliter(transaction.getDataFolder().getAbsolutePath() + "/" + databaseName);
    }

    public void init(){
        if(!sqliter.haveTable("data")){
            HashMap<String,String> tableData = new HashMap<>();
            tableData.put("UUID","TEXT NOT NULL");
            tableData.put("TEXT","TEXT NOT NULL");
            tableData.put("TIME", "BIGINT NOT NULL");
            sqliter.createTable("data",tableData);
        }
    }
    public void save(){
        sqliter.closeDb();
    }
    public void saveData(UUID uuid, String transaction, long time){
        HashMap<String,String> data = new HashMap<>();
        data.put("UUID",uuid.toString());
        data.put("TEXT",transaction);
        data.put("TIME",String.valueOf(time));
        sqliter.insertData("data",data);
    }
    public void saveData(Transaction transaction){
        saveData(transaction.getUuid(),transaction.getTransaction(),transaction.getTimestamp());
    }
    public ArrayList<Transaction> retrieveData(UUID uuid){
        ArrayList<HashMap<String,String>> data = sqliter.runQuery("SELECT * from data where UUID='"+uuid.toString()+"';");
        ArrayList<Transaction> result = new ArrayList<>();
        for (HashMap<String,String> hashmap : data) {
            result.add(new Transaction(Long.parseLong(hashmap.get("ID")),UUID.fromString(hashmap.get("UUID")),hashmap.get("TEXT"),Long.parseLong(hashmap.get("TIME")),currency));
        }
        return result;
    }
    public void removeData(Transaction transaction){
        HashMap<String,String> data = new HashMap<>();
        data.put("ID",String.valueOf(transaction.getId()));
        data.put("UUID",transaction.getUuid().toString());
        data.put("TEXT",transaction.getTransaction());
        data.put("TIME",String.valueOf(transaction.getTimestamp()));
        sqliter.deleteData("data",data);
    }
}
