package werse.jarvis.werseTokens.Commands.SetDeafultAmout;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import werse.jarvis.werseTokens.Logic.ReadDataFile;

import java.util.HashMap;

public class SetDeafultAmoutOnJoin implements Listener {

    private final JavaPlugin plugin;
    private final ReadDataFile readDataFile;
    private final SetDeafultAmount setDeafultAmount;

    public SetDeafultAmoutOnJoin(JavaPlugin plugin) {
        this.plugin = plugin;
        this.readDataFile = new ReadDataFile(plugin);
        this.setDeafultAmount = new SetDeafultAmount(plugin);
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        HashMap<String, Integer> updateData;
        updateData = readDataFile.readDataFromDatabase();

        if (updateData.get(player.getName()) == null) {
            Bukkit.getLogger().info("Wykryto brak gracza " + player.getName() + " w bazie danych tokenów ustawiam domyślną wartość");
            setDeafultAmount.setDeafultAmount(player, 0);
        } else {
            Bukkit.getLogger().info("Znaleziono gracza " + player.getName() + " w bazie danych tokenów gracz posiada " + updateData.get(player.getName()) + " tokenów.");
        }
    }
}
