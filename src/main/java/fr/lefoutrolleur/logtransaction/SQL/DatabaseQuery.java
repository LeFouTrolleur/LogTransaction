package fr.lefoutrolleur.logtransaction.SQL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DatabaseQuery {



    public static final DatabaseQuery INSTANCE = new DatabaseQuery();

    private final Sqliter sqliter;

    private DatabaseQuery() {
        sqliter = new Sqliter("LogTransaction");
    }

    public void init(){
        if(!sqliter.haveTable("data")){
            HashMap<String,String> tableData = new HashMap<>();
            tableData.put("UUID","TEXT NOT NULL");
            tableData.put("TEXT","TEXT NOT NULL");
            tableData.put("TIME", "INTEGER NOT NULL");

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
            result.add(new Transaction(Long.parseLong(hashmap.get("ID")),UUID.fromString(hashmap.get("UUID")),hashmap.get("TEXT"),Long.parseLong(hashmap.get("TIME"))));
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
