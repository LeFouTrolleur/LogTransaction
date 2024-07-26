package fr.lefoutrolleur.logtransaction.utils;

/**
 * More colors.
 *
 * @author Krille
 */
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.Validate;

public enum MoreColor {

    BLACK("0"), DARK_BLUE("1"), DARK_GREEN("2"), DARK_AQUA("3"), DARK_RED("4"), DARK_PURPLE("5"), GOLD("6"), GRAY("7"),
    DARK_GRAY("8"), BLUE("9"), GREEN("a"), AQUA("b"), RED("c"), LIGHT_PURPLE("d"), YELLOW("e"), WHITE("f"), MAGIC("k"),
    BOLD("l"), STRIKETHROUGH("m"), UNDERLINE("n"), ITALIC("o"), RESET("r"), ABSOLUTE_ZERO("s0", "0048BA"),
    ACID_GREEN("s1", "B0BF1A"), AERO("s2", "7CB9E8"), ALICE_BLUE("s3", new Color(240, 248, 255)),
    ANTIQUE_WHITE("s4", new Color(250, 235, 215)),
    AMETHYST("s5", "9966CC"),
    AQUAMARINE("s6", new Color(127, 255, 212)),
    AZURE("s7", new Color(240, 255, 255)),
    BEIGE("s8", new Color(245, 245, 220)),
    BISQUE("s9", new Color(255, 228, 196)),
    BLANCHED_ALMOND("t0", new Color(255, 235, 205)),
    BLUE_VIOLET("t1", new Color(138, 43, 226)),
    BROWN("t2", new Color(165, 42, 42)),
    BURLYWOOD("t3", new Color(222, 184, 135)),
    CADET_BLUE("t4", new Color(95, 158, 160)),
    CHARTREUSE("t5", new Color(127, 255, 0)),
    CHOCOLATE("t6", new Color(210, 105, 30)),
    CORAL("t7", new Color(255, 127, 80)),
    CORNFLOWER_BLUE("t8", new Color(100, 149, 237)),
    CORNSILK("t9", new Color(255, 248, 220)),
    CRIMSON("u0", new Color(220, 20, 60)),
    CYAN("u1", new Color(0, 255, 255)),
    DARK_CYAN("u2", new Color(0, 139, 139)),
    DARK_GOLDENROD("u3", new Color(184, 134, 11)),
    DARK_KHAKI("u4", new Color(189, 183, 107)),
    DARK_MAGENTA("u5", new Color(139, 0, 139)),
    DARK_OLIVE_GREEN("u6", new Color(85, 107, 47)),
    DARK_ORANGE("u7", new Color(255, 140, 0)),
    DARK_ORCHID("u8", new Color(153, 50, 204)),
    DARK_SALMON("u9", new Color(233, 150, 122)),
    DARK_SEA_GREEN("v0", new Color(143, 188, 143)),
    DARK_SLATE_BLUE("v1", new Color(72, 61, 139)),
    DARK_SLATE_GRAY("v2", new Color(47, 79, 79)),
    DARK_TURQUOISE("v3", new Color(0, 206, 209)),
    DARK_VIOLET("v4", new Color(148, 0, 211)),
    DEEP_PINK("v5", new Color(255, 20, 147)),
    DEEP_SKYBLUE("v6", new Color(0, 191, 255)),
    DIM_GRAY("v7", new Color(105, 105, 105)),
    DODGER_BLUE("v8", new Color(30, 144, 255)),
    FIREBRICK("v9", new Color(178, 34, 34)),
    FLORAL_WHITE("w0", new Color(255, 250, 240)),
    FOREST_GREEN("w1", new Color(34, 139, 34)),
    FUCHSIA("w2", new Color(255, 0, 255)),
    GAINSBORO("w3", new Color(220, 220, 220)),
    GHOST_WHITE("w4", new Color(248, 248, 255)),
    GOLDENROD("w5", new Color(218, 165, 32)),
    GREEN_YELLOW("w6", new Color(173, 255, 47)),
    HONEYDEW("w7", new Color(240, 255, 240)),
    HOT_PINK("w8", new Color(255, 105, 180)),
    INDIAN_RED("w9", new Color(205, 92, 92)),
    INDIGO("x0", new Color(75, 0, 130)),
    IVORY("x1", new Color(255, 255, 240)),
    KHAKI("x2", new Color(240, 230, 140)),
    LAVENDER("x3", new Color(230, 230, 250)),
    LAVENDER_BLUSH("x4", new Color(255, 240, 245)),
    LAWN_GREEN("x5", new Color(124, 252, 0)),
    LEMON_CHIFFON("x6", new Color(255, 250, 205)),
    LIGHT_BLUE("x7", new Color(173, 216, 230)),
    LIGHT_CORAL("x8", new Color(240, 128, 128)),
    LIGHT_GOLDENROD_YELLOW("x9", new Color(250, 250, 210)),
    LIGHT_GREY("y0", new Color(211, 211, 211)),
    LIGHT_PINK("y1", new Color(255, 182, 193)),
    LIGHT_SALMON("y2", new Color(255, 160, 122)),
    LIGHT_SEA_GREEN("y3", new Color(32, 178, 170)),
    LIGHT_SKY_BLUE("y4", new Color(135, 206, 250)),
    LIGHT_SLATE_GRAY("y5", new Color(119, 136, 153)),
    LIGHT_STEEL_BLUE("y6", new Color(176, 196, 222)),
    LIGHT_YELLOW("y7", new Color(255, 255, 224)),
    LINEN("y8", new Color(250, 240, 230)),
    MAROON("y9", new Color(128, 0, 0)),
    MEDIUM_AQUAMARINE("z0", new Color(102, 205, 170)),
    MEDIUM_BLUE("z1", new Color(0, 0, 205)),
    MEDIUM_ORCHID("z2", new Color(186, 85, 211)),
    MEDIUM_PURPLE("z3", new Color(147, 112, 219)),
    MEDIUM_SEA_GREEN("z4", new Color(60, 179, 113)),
    MEDIUM_SPRING_GREEN("z5", new Color(0, 250, 154)),
    MEDIUM_TURQUOISE("z6", new Color(72, 209, 204)),
    MEDIUM_VIOLET_RED("z7", new Color(199, 21, 133)), MIDNIGHT_BLUE("z8", new Color(25, 25, 112)),
    MINTCREAM("z9", new Color(245, 255, 250)), MISTYROSE("h0", new Color(255, 228, 225)),
    MOCCASIN("h1", new Color(255, 228, 181)), NAVAJO_WHITE("h2", new Color(255, 222, 173)),
    NAVY("h3", new Color(0, 0, 128)), OLD_LACE("h4", new Color(253, 245, 230)), OLIVE("h5", new Color(128, 128, 0)),
    OLIVE_DRAB("h6", new Color(107, 142, 35)), ORANGE("h7", new Color(255, 165, 0)),
    ORANGERED("h8", new Color(255, 69, 0)), ORCHID("h9", new Color(218, 112, 214)),
    PALE_GOLDENROD("i0", new Color(238, 232, 170)), PALE_GREEN("i1", new Color(152, 251, 152)),
    PALE_TURQUOISE("i2", new Color(175, 238, 238)), PALE_VIOLETRED("i3", new Color(219, 112, 147)),
    PAPAYAWHIP("i4", new Color(255, 239, 213)),
    PEACH_PUFF("i5", new Color(255, 218, 185)),
    PERU("i6", new Color(205, 133, 63)),
    PINK("i7", new Color(255, 192, 203)),
    PLUM("i8", new Color(221, 160, 221)),
    POWDER_BLUE("i9", new Color(176, 224, 230)), PURPLE("j0", new Color(128, 0, 128)),
    ROSY_BROWN("j1", new Color(188, 143, 143)), ROYAL_BLUE("j2", new Color(65, 105, 225)),
    SADDLE_BROWN("j3", new Color(139, 69, 19)), SALMON("j4", new Color(250, 128, 114)),
    SANDY_BROWN("j5", new Color(244, 164, 96)), SEA_GREEN("j6", new Color(46, 139, 87)),
    SEA_SHELL("j7", new Color(255, 245, 238)), SIENNA("j8", new Color(160, 82, 45)),
    SILVER("j9", new Color(192, 192, 192)), SKY_BLUE("p0", new Color(135, 206, 235)),
    SLATE_BLUE("p1", new Color(106, 90, 205)), SLATE_GRAY("p2", new Color(112, 128, 144)),
    SNOW("p3", new Color(255, 250, 250)), SPRING_GREEN("p4", new Color(0, 255, 127)),
    STEEL_BLUE("p5", new Color(70, 130, 180)),
    TAN("p6", new Color(210, 180, 140)),
    TEAL("p7", new Color(0, 128, 128)),
    THISTLE("p8", new Color(216, 191, 216)), TOMATO("p9", new Color(255, 99, 71)),
    TURQUOISE("q0", new Color(64, 224, 208)), VIOLET("q1", new Color(238, 130, 238)),
    WHEAT("q2", new Color(245, 222, 179)), WHITE_SMOKE("q3", new Color(245, 245, 245)),
    YELLOW_GREEN("q4", new Color(154, 205, 50));

    private static final char COLOR_CHAR = '\u00A7';
    private static final Pattern HEX_PATTERN = Pattern.compile("<(#[a-fA-F0-9]{6})>");
    private static final Map<String, MoreColor> BY_HEX = Maps.newHashMap();
    private static final Map<String, MoreColor> BY_KEY = Maps.newHashMap();
    private static final List<String> NEW_COLOR_KEYS = Lists.newArrayList();

    private final String key;
    private final String toString;
    private final String hexValue;

    private MoreColor(String key, String hexValue) {
        this.key = key.toLowerCase();

        if (MoreColor.isOld(key)) {
            this.hexValue = "";
            this.toString = new String(new char[] { COLOR_CHAR, key.charAt(0) });
        } else {
            this.hexValue = hexValue;
            this.toString = new String(this.getFormattedHexValue());
        }
    }

    private MoreColor(String key, Color color) {
        this(key, String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
    }

    private MoreColor(String key) {
        this(key, "");
    }

    /**
     * Gets the formatted (non-raw) hex value of the MoreColor
     *
     * @return The formatted (non-raw) hex value
     */
    public String getFormattedHexValue() {
        return "<#" + this.hexValue + ">";
    }

    /**
     * Checks if color is a old ("default") color or new "custom" color.
     *
     * @return Whether color is old
     */
    public boolean isOld() {
        return MoreColor.isOld(this.key);
    }

    /**
     * Checks if color is a new "custom" color or a old ("default") color.
     *
     * @return Whether color is new
     */
    public boolean isNew() {
        return !MoreColor.isOld(this.key);
    }

    /**
     * Gets the color code key of the MoreColor
     *
     * @return The color code key
     */
    public String getKey() {
        return this.key;
    }

    @Override
    public String toString() {
        return toString;
    }

    /**
     * Gets the color represented by the specified hex color value
     *
     * @param hex Hex value to check
     * @return Associative MoreColor with the given hex, or null if it doesn't exist
     */
    public static MoreColor getByHex(@Nonnull String hex) {
        return BY_HEX.get(hex);
    }

    /**
     * Gets the color represented by the specified color key (or "color code")
     *
     * @param key Key to check
     * @return Associative MoreColor with the given key, or null if it doesn't exist
     */
    public static MoreColor getByKey(String key) {
        Validate.notNull(key, "Key cannot be null");
        Validate.isTrue(key.length() > 0, "Key must have at least one char");

        return BY_KEY.get(key);
    }

    /**
     * Checks if a color code key belongs to a "old" color.
     *
     * @param key The color code key to check
     * @return Whether it is old
     */
    public static boolean isOld(String key) {
        return key.equalsIgnoreCase("0") || key.equalsIgnoreCase("1") || key.equalsIgnoreCase("2")
                || key.equalsIgnoreCase("3") || key.equalsIgnoreCase("4") || key.equalsIgnoreCase("5")
                || key.equalsIgnoreCase("6") || key.equalsIgnoreCase("7") || key.equalsIgnoreCase("8")
                || key.equalsIgnoreCase("9") || key.equalsIgnoreCase("a") || key.equalsIgnoreCase("b")
                || key.equalsIgnoreCase("c") || key.equalsIgnoreCase("d") || key.equalsIgnoreCase("e")
                || key.equalsIgnoreCase("f") || key.equalsIgnoreCase("k") || key.equalsIgnoreCase("l")
                || key.equalsIgnoreCase("m") || key.equalsIgnoreCase("n") || key.equalsIgnoreCase("o")
                || key.equalsIgnoreCase("r");
    }

    private static String color(String textToColorize) {
        if (MoreColor.serverSupportsHex()) {
            Pattern hexPattern = MoreColor.HEX_PATTERN;
            Matcher matcher = hexPattern.matcher(textToColorize);

            while (matcher.find()) {
                final net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor
                        .of(matcher.group().substring(1, matcher.group().length() - 1));
                final String before = textToColorize.substring(0, matcher.start());
                final String after = textToColorize.substring(matcher.end());
                textToColorize = before + hexColor + after;
                matcher = hexPattern.matcher(textToColorize);
            }
        }

        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', textToColorize);
    }

    /**
     * Translates a string using an alternate color code character into a string
     * that uses the internal MoreColor.COLOR_CODE color code key character. The
     * alternate color code character will only be replaced if it is immediately
     * followed by color code key.
     *
     * @param alternateCharacter The alternate color code character to replace
     * @param textToTranslate    Text containing the alternate color code character
     * @return Text containing the MoreColor.COLOR_CODE color code character
     */
    public static String colorize(char alternateCharacter, String textToTranslate) {
        String translated = textToTranslate;
        String text = translated.toLowerCase();

        for (Map.Entry<String, MoreColor> color : BY_KEY.entrySet()) {
            String colorKey = (alternateCharacter + color.getValue().getKey());

            if (text.contains(colorKey))
                translated = translated.replace(colorKey, color.getValue().toString);
        }

        return MoreColor.color(translated);
    }

    /**
     * Translates a string using default alternate color code character (&) to a
     * string that uses the internal MoreColor.COLOR_CODE color code key character.
     * The alternate color code character will only be replaced if it is immediately
     * followed by color code key.
     *
     * @param textToTranslate Text containing the alternate color code character
     * @return Text containing the MoreColor.COLOR_CODE color code character
     */
    public static String colorize(String textToTranslate) {
        return MoreColor.colorize('&', textToTranslate);
    }

    /**
     * Checks if hex colors are supported for the currently used minecraft version.
     *
     * @return Whether hex-colors is supported
     */
    public static boolean serverSupportsHex() {
        try {
            net.md_5.bungee.api.ChatColor.of(Color.BLACK);
            return true;
        } catch (NoSuchMethodError ignore) {
            return false;
        }
    }

    static {
        for (MoreColor color : values()) {
            BY_HEX.put(color.hexValue, color);
            BY_KEY.put(color.key, color);

            if (color.isNew())
                NEW_COLOR_KEYS.add(color.key);
        }
    }
}
