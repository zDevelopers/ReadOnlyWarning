package fr.prokopowicz.alex.ReadOnlyWarning.players;

import fr.prokopowicz.alex.ReadOnlyWarning.Config;
import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.prokopowicz.alex.ReadOnlyWarning.tasks.WarningTask;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.tools.text.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.UUID;


/**
 * Represents a player currently in a read-only state.
 */
public class ReadOnlyPlayer
{
    /**
     * The player in a read-only state.
     */
    private UUID playerID;

    /**
     * The moderator who placed this player in a read-only state.
     */
    private UUID moderatorID;

    /**
     * Why this player is in a read-only state.
     */
    private String reason;

    /**
     * When the sanction was added.
     */
    private Date creationDate;


    /**
     * The task used to send the warning messages about readonly mode, null if player offline
     */
    private transient BukkitRunnable warningTask = null;


    public ReadOnlyPlayer(final UUID playerID, final UUID moderatorID, final String reason)
    {
        this(playerID, moderatorID, reason, new Date());
    }

    public ReadOnlyPlayer(final UUID playerID, final UUID moderatorID, final String reason, final Date creationDate)
    {
        this.playerID = playerID;
        this.moderatorID = moderatorID;
        this.reason = reason;
        this.creationDate = creationDate;
    }


    /**
     * Starts the task sending a warning message to this player about his state.
     */
    public void displayRegularWarning()
    {
        stopWarningDisplay();

        warningTask = new WarningTask(this);
        warningTask.runTaskTimer(ReadOnlyWarning.get(), 2L, 20 * 60 * Config.WARNING_INTERVAL.get());

        ActionBar.sendPermanentMessage(getPlayerID(), I.t("{red}Read-only {gray}- {yellow}Read the chat"));
    }

    /**
     * Stops the task sending a warning message to this player about his state.
     */
    public void stopWarningDisplay()
    {
        if (warningTask != null)
        {
            warningTask.cancel();
            warningTask = null;

            ActionBar.removeMessage(getPlayerID());
        }
    }


    public UUID getPlayerID()
    {
        return playerID;
    }

    public OfflinePlayer getPlayer()
    {
        return Bukkit.getOfflinePlayer(playerID);
    }

    public UUID getModeratorID()
    {
        return moderatorID;
    }

    public OfflinePlayer getModerator()
    {
        return Bukkit.getOfflinePlayer(moderatorID);
    }

    public String getReason()
    {
        return reason;
    }

    public Date getCreationDate()
    {
        return creationDate;
    }

    public void setReason(final String reason)
    {
        this.reason = reason;
    }
}
