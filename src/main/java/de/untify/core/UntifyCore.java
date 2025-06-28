package de.untify.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UntifyCore extends JavaPlugin implements Listener {

    private Set<String> allowedCommands;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        allowedCommands = new HashSet<>();
        List<String> list = getConfig().getStringList("allowed-commands");
        for (String cmd : list) {
            allowedCommands.add(cmd.toLowerCase());
        }
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        if (message.length() < 2 || !message.startsWith("/")) {
            return;
        }
        String cmd = message.split(" ", 2)[0].substring(1).toLowerCase();
        if (!allowedCommands.contains(cmd)) {
            event.setCancelled(true);
            String blockedMsg = getConfig().getString("blocked-message", "Dieser Befehl ist deaktiviert.");
            event.getPlayer().sendMessage(blockedMsg);
        }
    }
}
