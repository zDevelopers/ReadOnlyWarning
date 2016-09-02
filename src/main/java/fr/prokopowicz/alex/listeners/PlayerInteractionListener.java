package fr.prokopowicz.alex.listeners;

import fr.prokopowicz.alex.ReadOnlyWarning;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;


/**
 * Created by Alex on 28/08/2016.
 */
public class PlayerInteractionListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (ReadOnlyWarning.get().getReadOnlyPlayersManager().isReadOnly(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(EntityDamageByEntityEvent evnt)
    {
        if (evnt.getDamager() instanceof Player)
            if (ReadOnlyWarning.get().getReadOnlyPlayersManager().isReadOnly(evnt.getDamager().getUniqueId()))
                evnt.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerArmorStandManipulateEvent evt)
    {
        if (ReadOnlyWarning.get().getReadOnlyPlayersManager().isReadOnly(evt.getPlayer().getUniqueId()))
            evt.setCancelled(true);
    }
}
