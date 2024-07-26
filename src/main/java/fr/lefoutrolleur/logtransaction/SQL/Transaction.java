package fr.lefoutrolleur.logtransaction.SQL;

import java.util.UUID;

public class Transaction {



    private final UUID uuid;
    private final float transaction;
    private final long timestamp;
    private final String currency;
    public Transaction(UUID uuid,float transaction, long timestamp, String currency) {
        this.uuid = uuid;
        this.transaction = transaction;
        this.timestamp = timestamp;
        this.currency = currency;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getTransaction() {
        return transaction;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getCurrency() {
        return currency;
    }
}
