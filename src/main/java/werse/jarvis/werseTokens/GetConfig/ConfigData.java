package werse.jarvis.werseTokens.GetConfig;

import org.bukkit.plugin.java.JavaPlugin;

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
