package fr.lefoutrolleur.logtransaction.utils;

import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.text.DateFormat;
import java.util.*;

import static fr.lefoutrolleur.logtransaction.utils.MoreColor.*;

public class ItemsLib {

    public static final String SORT_ITEM_NAME = "Trier";

    public static ItemStack NEXT_PAGE = new ItemBuilder(Material.PLAYER_HEAD).name(colorize(DARK_SLATE_BLUE + "Page suivante")).lore("§7Cliquez pour voir à la prochaine page").setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjE1MzRjOWJlYTRhMTA3NDUxMjhmMGQ3ZDViZDhmYjE4NDhhYzgyYzc5MzMyM2JlNWMwNjEyYTkxZGQ1OGJiZCJ9fX0=").toItemStack();
    public static ItemStack PREVIOUS_PAGE = new ItemBuilder(Material.PLAYER_HEAD).name(colorize(CORNFLOWER_BLUE + "Page précédente")).lore("§7Cliquez pour voir la page précédente").setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDQ4OTIwNGQxYzk3YzY5MGYyNzBhOGQ2Y2YwNDEyMjgyODhjNDU3OTJjNjZjMTY1NDc4MWM4MzRkMjg3M2JhNSJ9fX0=").toItemStack();
    public static ItemStack EMPTY_TRANSACTION = new ItemBuilder(Material.PLAYER_HEAD).name("§cAucune Transaction trouvée").lore("§7Ce joueur n'a effectué aucune transaction!").setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTljZGI5YWYzOGNmNDFkYWE1M2JjOGNkYTc2NjVjNTA5NjMyZDE0ZTY3OGYwZjE5ZjI2M2Y0NmU1NDFkOGEzMCJ9fX0=").toItemStack();
    public static ItemStack BLACK_GLASS = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(" ").toItemStack();
    @Nullable
    public static ItemStack fromTransaction(Transaction transaction, int index, int max) {
        float amount = transaction.getTransaction();
        ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);
        Date date = new Date(transaction.getTimestamp());
        if(amount<0){
            builder.name(colorize(RED + "- " + WHITE + String.format("%.2f", Math.abs(amount))));
            builder.setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=");
        } else {
            builder.name(colorize(GREEN + "+ " + WHITE + String.format("%.2f", amount)));
            builder.setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19");
        }
        String currency_lore = "§2" + transaction.getCurrency();

        List<String> lore = new ArrayList<>();
        lore.add(colorize(DARK_SALMON + DateFormat.getDateInstance().format(date) + "  " + DateFormat.getTimeInstance().format(date)));
        lore.add(colorize(LIGHT_SEA_GREEN + "Avant: " + String.format("%.2f",transaction.getBeforeBalance())));
        lore.add(colorize(LIGHT_SKY_BLUE + "Après: " + String.format("%.2f",transaction.getAfterBalance())));
        if(!transaction.getSource().equals("null")) {
            lore.add("§dSource: §5" + transaction.getSource());
        }
        lore.add(currency_lore);
        lore.add("§8" + index + "/" + max);
        builder.lore(lore);
        return builder.toItemStack();
    }
    public static ItemStack getDateItemStack(long older, long newer, int number) {
        Date older_date = new Date(older);
        Date newer_date = new Date(newer);
        ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);
        builder.setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWQ5YjcxMmNkZjkxNDc3NTllYmE0ZDU1NzNjZGI2MzgzM2I0NGYzYWNlNGZjYjY2ZWEzYWIxNDcyMDdkYTNmZiJ9fX0=");
        builder.name(colorize(DARK_CYAN + "Dates"));
        builder.lore(colorize(DARK_SALMON + DateFormat.getDateInstance().format(older_date) + " " + DateFormat.getTimeInstance().format(older_date)), colorize(LIGHT_SEA_GREEN + DateFormat.getDateInstance().format(newer_date) + " " + DateFormat.getTimeInstance().format(newer_date)), "§7Nombre: " + number);
        return builder.toItemStack();
    }

    public static ItemStack getSortItemStack(int sortType) {
        ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);
        //
        builder.name(colorize(DARK_CYAN + SORT_ITEM_NAME));
        List<String> lore = new ArrayList<>();
        if(sortType == 0){
            lore.add(colorize(GOLD + ">> Trier par date (croissant)"));
            builder.setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGIyMjFjYjk2MDdjOGE5YmYwMmZlZjVkNzYxNGUzZWIxNjljYzIxOWJmNDI1MGZkNTcxNWQ1ZDJkNjA0NWY3In19fQ==");
        } else {
            lore.add(colorize(GRAY + ">> Trier par date (croissant)"));
        }
        if(sortType == 1){
            lore.add(colorize(GOLD+ ">> Trier par date (décroissant)"));
            builder.setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhhYWI2ZDlhMGJkYjA3YzEzNWM5Nzg2MmU0ZWRmMzYzMTk0Mzg1MWVmYzU0NTQ2M2Q2OGU3OTNhYjQ1YTNkMyJ9fX0=");
        } else {
            lore.add(colorize(GRAY + ">> Trier par date (décroissant)"));
        }
        if(sortType == 2){
            lore.add(colorize(GOLD+ ">> Trier par montant (croissant)"));
            builder.setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRhMDI3NDc3MTk3YzZmZDdhZDMzMDE0NTQ2ZGUzOTJiNGE1MWM2MzRlYTY4YzhiN2JjYzAxMzFjODNlM2YifX19");
        } else {
            lore.add(colorize(GRAY + ">> Trier par montant (croissant)"));
        }
        if(sortType == 3){
            lore.add(colorize(GOLD+ ">> Trier par montant (décroissant)"));
            builder.setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY3NDE2Y2U5ZTgyNmU0ODk5YjI4NGJiMGFiOTQ4NDNhOGY3NTg2ZTUyYjcxZmMzMTI1ZTAyODZmOTI2YSJ9fX0=");
        } else {
            lore.add(colorize(GRAY + ">> Trier par montant (décroissant)"));
        }
        builder.lore(lore);
        return builder.toItemStack();
    }
    public static ItemStack getModifyDate(String text) {
        return new ItemBuilder(Material.PAPER).name("§7Modifier la date du" + text).lore("§7Cliquez pour modifier la date").toItemStack();
    }
    public static ItemStack getModifyHours(String text) {
        return new ItemBuilder(Material.PAPER).name("§7Modifier les heures du" + text).lore("§7Cliquez pour modifier les heures").toItemStack();
    }
    public static ItemStack getPageNumberItemStack(int pageNumber) {
        ItemBuilder builder = new ItemBuilder(Material.PAPER);
        builder.name(colorize(DARK_CYAN + "Page " + pageNumber));
        return builder.toItemStack();
    }
    public static ItemStack getPlayerHeadItemStack(OfflinePlayer player, int totalTransactions) {
        ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);
        builder.name(colorize( GOLDENROD + "§l" + player.getName()));
        builder.setSkullOwner(player);
        builder.lore(colorize(MEDIUM_SEA_GREEN + "Transactions: " + totalTransactions));
        return builder.toItemStack();
    }
    private ItemsLib() { }
}
