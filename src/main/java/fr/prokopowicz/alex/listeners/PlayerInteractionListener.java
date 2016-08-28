package fr.prokopowicz.alex.listeners;

import fr.prokopowicz.alex.ReadOnlyWarning;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Alex on 28/08/2016.
 */
public class PlayerInteractionListener implements Listener {
    @EventHandler
    public void onPlayerInteract (PlayerInteractEvent event){
        if (ReadOnlyWarning.getInstance() .getReadOnlyPlayersManager() .isReadOnly(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }
}
