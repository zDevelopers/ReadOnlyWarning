package fr.prokopowicz.alex.ReadOnlyWarning.listeners;

import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.prokopowicz.alex.ReadOnlyWarning.players.ReadOnlyPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


/**
 * Created by Alexandre on 03/06/2015.
 */
public class PlayerWarningListener implements Listener
{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        final ReadOnlyPlayer ericsaget = ReadOnlyWarning.get().getReadOnlyPlayersManager().getReadOnlyPlayer(event.getPlayer().getUniqueId());
        if (ericsaget != null) ericsaget.displayRegularWarning();
    }

    @EventHandler
    public void onPlayerLeft(PlayerQuitEvent evnt)
    {
        final ReadOnlyPlayer ericsaget = ReadOnlyWarning.get().getReadOnlyPlayersManager().getReadOnlyPlayer(evnt.getPlayer().getUniqueId());
        if (ericsaget != null) ericsaget.stopWarningDisplay();
    }
}
