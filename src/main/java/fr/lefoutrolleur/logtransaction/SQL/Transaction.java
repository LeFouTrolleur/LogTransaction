package fr.lefoutrolleur.logtransaction.SQL;

import java.util.UUID;

public class Transaction {



    private final UUID uuid;
    private final String transaction;
    private final long timestamp;
    private final long id;
    private final String currency;
    public Transaction(long id,UUID uuid,String transaction, long timestamp, String currency) {
        this.id = id;
        this.uuid = uuid;
        this.transaction = transaction;
        this.timestamp = timestamp;
        this.currency = currency;
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
    public long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }
}
