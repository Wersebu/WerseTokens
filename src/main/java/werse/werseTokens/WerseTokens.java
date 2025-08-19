package werse.werseTokens;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import werse.werseTokens.Commands.CommandsHandler;
import werse.werseTokens.Commands.PlayerJoinEventHandler;
import werse.werseTokens.ConsoleLogs.StartServerMessage;
import werse.werseTokens.PlaceholderAPI.RegisterPlaceholder;

public final class WerseTokens extends JavaPlugin {

    @Override
    public void onEnable() {
        saveConfig();
        StartServerMessage.displayStartupMessage(this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new RegisterPlaceholder(this).register();
            getLogger().info("PlaceholderAPI Hook enabled");
        } else {
            getLogger().warning("PlaceholderAPI no detected placeholders will no work!");
        }

        if (getCommand("tokens") == null) {
            getLogger().severe("Komenda /tokens nie została załadowana! Sprawdź plugin.yml.");
        } else {
            getLogger().info("Komenda /tokens załadowana poprawnie.");
            CommandsHandler commandsHandler = new CommandsHandler(this);
            getCommand("tokens").setExecutor(commandsHandler);
            getCommand("tokens").setTabCompleter(commandsHandler);
        }
        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(this),this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin WerseTokens się wyłącza...");
    }
}
