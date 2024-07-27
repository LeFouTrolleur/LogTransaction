package fr.lefoutrolleur.logtransaction.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public final class ItemBuilder {
    private final UUID randomUUID = UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4");

    private final ItemStack item;


    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.item = is;
    }

    public ItemBuilder(Material m, int amount) {
        item = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, short damage) {
        item = new ItemStack(m, amount, damage);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(item);
    }

    public ItemBuilder setDurability(int dur) {
        ItemMeta meta = item.getItemMeta();
        ((Damageable) meta).setDamage(dur);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder name(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        item.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        item.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setSkullOwner(OfflinePlayer owner) {
        try {
            SkullMeta SMeta = (SkullMeta) item.getItemMeta();
            assert SMeta != null;
            SMeta.setOwningPlayer(owner);
            item.setItemMeta(SMeta);
        } catch (ClassCastException ignored) {
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.addEnchant(ench, level, true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder unbreakable() {
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }
    public ItemBuilder lore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flag);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setTextureHead(String base64) {
        String decoded = new String(Base64.getDecoder().decode(base64));
        String url = decoded.substring("{\"textures\":{\"SKIN\":{\"url\":\"".length(), decoded.length() - "\"}}}".length());
        PlayerProfile profile = Bukkit.createProfile(randomUUID);
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        textures.setSkin(urlObject);
        profile.setTextures(textures);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setPlayerProfile(profile);
        item.setItemMeta(skullMeta);
        return this;
    }

    public ItemStack toItemStack() {
        return item;
    }





}
