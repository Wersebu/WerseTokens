package werse.jarvis.werseTokens.GetConfig;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorChat {

    // Wzorzec do znajdowania kolorów hex w formacie &#RRGGBB
    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    /**
     * Przetwarza tekst, zamieniając kody kolorów (hex i Minecraft) na odpowiednie kolory.
     *
     * @param message wiadomość do przetworzenia
     * @return przetworzona wiadomość z kolorami
     */
    public static String applyColors(String message) {
        // Obsługa kolorów hex (&#RRGGBB)
        message = translateHexColors(message);

        // Obsługa kolorów Minecraft (&a, &b, etc.)
        message = ChatColor.translateAlternateColorCodes('&', message);

        return message;
    }

    /**
     * Zamienia kody hex w formacie &#RRGGBB na odpowiednie kody kolorów.
     *
     * @param message wiadomość do przetworzenia
     * @return wiadomość z zamienionymi kolorami hex
     */
    private static String translateHexColors(String message) {
        Matcher matcher = HEX_COLOR_PATTERN.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hexCode = matcher.group(1);
            String color = ChatColor.of("#" + hexCode).toString(); // Używamy ChatColor.of dla koloru hex
            matcher.appendReplacement(buffer, color);
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }
}
