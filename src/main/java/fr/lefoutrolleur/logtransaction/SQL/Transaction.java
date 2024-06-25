package fr.lefoutrolleur.logtransaction.SQL;

import java.util.UUID;

public class Transaction {

    public long getId() {
        return id;
    }

    private final UUID uuid;
    private final String transaction;
    private final long timestamp;
    private final long id;
    public Transaction(long id,UUID uuid,String transaction, long timestamp) {
        this.id = id;
        this.uuid = uuid;
        this.transaction = transaction;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTransaction() {
        return transaction;
    }

    public UUID getUuid() {
        return uuid;
    }
}
