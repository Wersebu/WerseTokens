package werse.jarvis.werseTokens.GetConfig;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorChat {

    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String applyColors(String message) {
        message = translateHexColors(message);
        message = ChatColor.translateAlternateColorCodes('&', message);
        return message;
    }

    private static String translateHexColors(String message) {
        Matcher matcher = HEX_COLOR_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hexCode = matcher.group(1);
            String color = ChatColor.of("#" + hexCode).toString();
            matcher.appendReplacement(buffer, color);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }
}
