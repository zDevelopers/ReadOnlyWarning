package fr.prokopowicz.alex.managers;


import fr.prokopowicz.alex.rawtypes.ReadOnlyPlayer;
import fr.zcraft.zlib.core.ZLibComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ReadOnlyPlayersManager extends ZLibComponent
{
    private Map<UUID, ReadOnlyPlayer> readOnlyPlayers = new HashMap<>();


    @Override
    protected void onEnable()
    {
        loadPlayers();
    }

    @Override
    protected void onDisable()
    {
        savePlayers();
    }


    /**
     * Loads the players from the configuration file.
     */
    private void loadPlayers()
    {
        // TODO
    }

    /**
     * Saves the players to the configuration file.
     */
    public void savePlayers()
    {
        // TODO
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
        return readOnlyPlayers.remove(player);
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
}
