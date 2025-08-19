package werse.werseTokens.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import werse.werseTokens.GetConfig.ConfigData;
import werse.werseTokens.Logic.ReadDataFile;
import werse.werseTokens.Logic.SaveDataToFile;

import java.util.HashMap;

public class PlayerJoinEventHandler implements Listener {

    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SaveDataToFile saveDataToFile;
    private final ConfigData configData;
    private static String PREFIX;

    public PlayerJoinEventHandler(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.saveDataToFile = new SaveDataToFile(plugin);
        this.configData = new ConfigData(plugin);
        this.PREFIX = configData.getColoredPrefix();
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HashMap<String, Integer> updateData;
        updateData = readDataFile.readDataFromDatabase();

        if (updateData.get(player.getName()) == null) {
            Bukkit.getLogger().info("Wykryto brak gracza " + player.getName() + " w bazie danych tokenów ustawiam domyślną wartość");
            setDeafultAmount(player, 0);
        } else {
            Bukkit.getLogger().info("Znaleziono gracza " + player.getName() + " w bazie danych tokenów gracz posiada " + updateData.get(player.getName()) + " tokenów.");
        }
    }

    public void setDeafultAmount(Player player, int value) {
        HashMap<String, Integer> updateData = readDataFile.readDataFromDatabase();
        String user = player.getName();
        int newValue = value;

        updateData.put(user, newValue);

        player.sendMessage(PREFIX + "Saldo Konta ustawiono na " + newValue + " tokenów!");
        saveDataToFile.addTokensToDatabase(updateData);
    }
}
