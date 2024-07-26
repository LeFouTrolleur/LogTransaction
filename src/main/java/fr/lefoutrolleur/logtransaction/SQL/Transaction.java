package fr.lefoutrolleur.logtransaction.SQL;

import java.util.UUID;

public class Transaction {



    private final UUID uuid;
    private final float transaction;
    private final long timestamp;
    private final String currency;
    private final float before;
    private final float after;
    public Transaction(UUID uuid,float transaction, long timestamp, String currency, float before, float after) {
        this.uuid = uuid;
        this.transaction = transaction;
        this.timestamp = timestamp;
        this.currency = currency;
        this.before = before;
        this.after = after;
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

    public float getBeforeBalance() {
        return before;
    }
    public float getAfterBalance() {
        return after;
    }
}
