package fr.prokopowicz.alex.ReadOnlyWarning.players;


import fr.prokopowicz.alex.ReadOnlyWarning.Config;
import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.worker.WorkerCallback;
import fr.zcraft.zlib.core.ZLib;
import fr.zcraft.zlib.core.ZLibComponent;
import fr.zcraft.zlib.tools.PluginLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ReadOnlyPlayersManager extends ZLibComponent implements Listener
{
    private Map<UUID, ReadOnlyPlayer> readOnlyPlayers = new HashMap<>();


    @Override
    protected void onEnable()
    {
        ZLib.loadComponent(ReadOnlyPlayersIO.class);

        ReadOnlyPlayersIO.loadReadOnlyPlayers(new WorkerCallback<Map<UUID, ReadOnlyPlayer>>()
        {
            @Override
            public void finished(final Map<UUID, ReadOnlyPlayer> result)
            {
                readOnlyPlayers.putAll(result);
            }

            @Override
            public void errored(final Throwable exception)
            {
                PluginLogger.error("Unable to load read-only players from file", exception);
            }
        });
    }

    @Override
    protected void onDisable()
    {
        ReadOnlyPlayersIO.saveReadOnlyPlayers();
    }


    /**
     * Is that player in a read-only state?
     *
     * @param id The UUID of the player to check.
     *
     * @return {@code true} if this player is on a read-only state.
     */
    public boolean isReadOnly(UUID id)
    {
        return readOnlyPlayers.containsKey(id);
    }

    /**
     * Returns the ReadOnlyPlayer object for this player.
     *
     * @param id The UUID of the player.
     *
     * @return The {@link ReadOnlyPlayer} object; {@code null} if this player
     * isn't in a read-only state.
     */
    public ReadOnlyPlayer getReadOnlyPlayer(UUID id)
    {
        return readOnlyPlayers.get(id);
    }

    /**
     * Puts a player in a read-only state. If the player is already in such a
     * state, the moderators and reasons are updated.
     *
     * @param player    The player to put in a read-only state.
     * @param moderator The player who put this player in this state.
     * @param why       The reason.
     *
     * @return The {@link ReadOnlyPlayer} object just created and registered.
     */
    public ReadOnlyPlayer addReadOnlyPlayer(UUID player, UUID moderator, String why)
    {
        final ReadOnlyPlayer splotch = new ReadOnlyPlayer(player, moderator, why);
        readOnlyPlayers.put(player, splotch);

        ReadOnlyPlayersIO.saveReadOnlyPlayers();
        return splotch;
    }

    /**
     * Removes a player from this read-only state. The player will no longer be
     * locked.
     *
     * @param player The player.
     * @return The {@link ReadOnlyPlayer} object just removed, and no longer registered.
     */
    public ReadOnlyPlayer deleteReadOnlyPlayer(UUID player)
    {
        final ReadOnlyPlayer removed = readOnlyPlayers.remove(player);

        ReadOnlyPlayersIO.saveReadOnlyPlayers();
        return removed;
    }


    /**
     * Returns all the players currently on a read-only state.
     *
     * @return The payers.
     */
    public Map<UUID, ReadOnlyPlayer> getReadOnlyPlayers()
    {
        return new HashMap<>(readOnlyPlayers);
    }


    /* **  INTERACTION-RELATED LISTENERS  ** */

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (ReadOnlyWarning.get().getReadOnlyPlayersManager().isReadOnly(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent evnt)
    {
        if (evnt.getDamager() instanceof Player)
            if (ReadOnlyWarning.get().getReadOnlyPlayersManager().isReadOnly(evnt.getDamager().getUniqueId()))
                evnt.setCancelled(true);
    }

    @EventHandler
    public void onArmorStandManipulation(PlayerArmorStandManipulateEvent evt)
    {
        if (ReadOnlyWarning.get().getReadOnlyPlayersManager().isReadOnly(evt.getPlayer().getUniqueId()))
            evt.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent ev)
    {
        if (Config.BLOCK_CHAT.get() && ReadOnlyWarning.get().getReadOnlyPlayersManager().isReadOnly(ev.getPlayer().getUniqueId()))
        {
            ev.setCancelled(true);
            I.sendT(ev.getPlayer(), "{ce}You cannot chat while in read-only mode.");
        }
    }


    /* **  WARNING-MESSAGES-RELATED LISTENERS  ** */

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
