package werse.jarvis.werseTokens.GetConfig;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileReader;

public class ConfigData {
    private final JavaPlugin plugin;
    private static String prefix;

    public ConfigData(JavaPlugin plugin) {
        this.plugin = plugin;
        this.prefix = plugin.getConfig().getString("PREFIX");
    }

    public String getColoredPrefix() {
        return ColorChat.applyColors(prefix);
    }
}
