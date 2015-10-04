package fr.prokopowicz.alex.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Alexandre on 03/06/2015.
 */
public class PlayerJoinTextListener implements Listener{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.getPlayer().sendMessage("Pomf");
        event.getPlayer().sendMessage("" + ChatColor.RED + ChatColor.UNDERLINE + "Splotch");
        event.getPlayer().sendMessage("Thanks for using ReadOnlyWarning V.-1 Please report any troubleshooting at 06.42.69.42.69");
    }
}
