package werse.jarvis.werseTokens;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.Commands.CommandsHandler;
import werse.jarvis.werseTokens.Commands.SetDeafultAmout.SetDeafultAmoutOnJoin;
import werse.jarvis.werseTokens.ConsoleLogs.StartServerMessage;
import werse.jarvis.werseTokens.PlaceholderAPI.RegisterPlaceholder;

public final class WerseTokens extends JavaPlugin {

    @Override
    public void onEnable() {
        saveConfig();
        StartServerMessage.displayStartupMessage(this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new RegisterPlaceholder(this).register();
            getLogger().info("PlaceholderAPI zarejestrowano pomyślnie.");
        } else {
            getLogger().warning("PlaceholderAPI nie zostało znalezione. Placeholdery nie będą działać.");
        }

        getLogger().info("Plugin WerseTokens się włącza...");

        if (getCommand("tokens") == null) {
            getLogger().severe("Komenda /tokens nie została załadowana! Sprawdź plugin.yml.");
        } else {
            getLogger().info("Komenda /tokens załadowana poprawnie.");
            CommandsHandler commandsHandler = new CommandsHandler(this);
            getCommand("tokens").setExecutor(commandsHandler);
            getCommand("tokens").setTabCompleter(commandsHandler);
        }
        getServer().getPluginManager().registerEvents(new SetDeafultAmoutOnJoin(this),this);

    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin WerseTokens się wyłącza...");
    }
}
