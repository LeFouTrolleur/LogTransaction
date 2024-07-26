package fr.lefoutrolleur.logtransaction.utils;

import fr.lefoutrolleur.logtransaction.Holders.LogInventoryHolder;
import fr.lefoutrolleur.logtransaction.SQL.Transaction;
import org.bukkit.Material;
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

    @Nullable
    public static ItemStack fromTransaction(Transaction transaction) {
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

        builder.lore(colorize(DARK_SALMON + DateFormat.getDateInstance().format(date) + "  " + DateFormat.getTimeInstance().format(date)), currency_lore);
        return builder.toItemStack();
    }
    public static ItemStack getSortItemStack(int sortType) {
        ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);
        builder.setTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhhYWI2ZDlhMGJkYjA3YzEzNWM5Nzg2MmU0ZWRmMzYzMTk0Mzg1MWVmYzU0NTQ2M2Q2OGU3OTNhYjQ1YTNkMyJ9fX0=");
        builder.name(colorize(DARK_CYAN + SORT_ITEM_NAME));
        List<String> lore = new ArrayList<>();
        lore.add(colorize((sortType == 0 ? GOLD : GRAY) + "Trier par date (croissant)"));
        lore.add(colorize((sortType == 1 ? GOLD : GRAY) + "Trier par date (décroissant)"));
        lore.add(colorize((sortType == 2 ? GOLD : GRAY) + "Trier par nombre (croissant)"));
        lore.add(colorize((sortType == 3 ? GOLD : GRAY) + "Trier par nombre (décroissant)"));
        builder.lore(lore);
        return builder.toItemStack();
    }
    private ItemsLib() { }
}
