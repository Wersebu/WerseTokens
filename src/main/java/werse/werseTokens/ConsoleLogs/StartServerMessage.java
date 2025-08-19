package werse.werseTokens.ConsoleLogs;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class StartServerMessage {

    // Metoda konwertująca kolor HEX na kod ANSI
    public static String colorize(String hex, String message) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return String.format("\u001B[38;2;%d;%d;%dm%s\u001B[0m", r, g, b, message);
    }

    public static void displayStartupMessage(JavaPlugin plugin) {

        String version = plugin.getDescription().getVersion();

        // Użycie kolorowania znak po znaku
        Bukkit.getLogger().info(colorize("#FB08D7","╭───────────────────────────────────╮"));
        Bukkit.getLogger().info(colorize("#FB08D7","│                                   │"));
        Bukkit.getLogger().info(colorize("#FB08D7","│") + colorize("#73FD2F", " \\  " + colorize("#A8FE17", "  ") + colorize("#DDFF00","  " + colorize("#DDFF00","  /" + colorize("#DDFF00"," ‾‾|‾‾" +  colorize("#FB08D7","                  │"))))));
        Bukkit.getLogger().info(colorize("#FB08D7","│") + colorize("#3DFC46", "  \\ " + colorize("#DDFF00", " /") + colorize("#DDFF00","\\ " + colorize("#DDFF00"," / " + colorize("#DDFF00","   | " + colorize("#08FB85","  WerseTokens") + colorize("#FB08D7","      │"))))));
        Bukkit.getLogger().info(colorize("#FB08D7","│") + colorize("#08FB5D", "   \\" + colorize("#DDFF00", "/ ") + colorize("#DDFF00"," \\" + colorize("#DDFF00","/  " + colorize("#DDFF00","   | " + colorize("#FB08D7","                   │"))))));
        Bukkit.getLogger().info(colorize("#FB08D7","│                                   │"));
        Bukkit.getLogger().info(colorize("#FB08D7","╰───────────────────────────────────╯"));
        Bukkit.getLogger().info(colorize("#FB08D7","│" + colorize("#08F1FB","  Powered by xxWersebuxx")));
        Bukkit.getLogger().info(colorize("#FB08D7","│" + colorize("#08FBC0","  Version ") + version));
        Bukkit.getLogger().info(colorize("#FB08D7","╰───────────────────────────────────"));
        Bukkit.getLogger().info("");
    }
}
