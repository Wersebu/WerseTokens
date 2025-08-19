package werse.werseTokens.PlaceholderAPI;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import werse.werseTokens.Logic.ReadDataFile;

import java.util.HashMap;

public class RegisterPlaceholder extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;

    public RegisterPlaceholder(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "wersetokens";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getPluginMeta().getAuthors().getFirst();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null || params == null) {
            return null;
        }

        if (params.equalsIgnoreCase("amount")) {
            HashMap<String, Integer> tokenData = readDataFile.readDataFromDatabase();
            String playerName = player.getName();

            int tokens = tokenData.getOrDefault(playerName, 0);
            return String.valueOf(tokens);
        }
        return null;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null || params == null) {
            return null;
        }

        if (params.equalsIgnoreCase("amount")) {
            HashMap<String, Integer> tokenData = readDataFile.readDataFromDatabase();
            String playerName = player.getName();

            int tokens = tokenData.getOrDefault(playerName, 0);
            return String.valueOf(tokens);
        }
        return null;
    }
}
