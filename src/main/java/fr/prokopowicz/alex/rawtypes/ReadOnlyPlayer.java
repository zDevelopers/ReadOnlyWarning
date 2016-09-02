package fr.prokopowicz.alex.rawtypes;

import fr.prokopowicz.alex.ReadOnlyWarning;
import fr.prokopowicz.alex.tasks.WarningTask;
import org.bukkit.scheduler.BukkitRunnable;

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
     * The task used to send the warning messages about readonly mode, null if player offline
     */
    private BukkitRunnable warningTask = null;


    public ReadOnlyPlayer(UUID playerID, UUID moderatorID, String reason)
    {
        this.playerID = playerID;
        this.moderatorID = moderatorID;
        this.reason = reason;
    }


    /**
     * Starts the task sending a warning message to this player about his state.
     */
    public void displayRegularWarning()
    {
        stopWarningDisplay();

        warningTask = new WarningTask(this);
        warningTask.runTaskTimer(ReadOnlyWarning.get(), 2l, 20*60*10l);
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
        }
    }


    public UUID getPlayerID()
    {
        return playerID;
    }

    public UUID getModeratorID()
    {
        return moderatorID;
    }

    public String getReason()
    {
        return reason;
    }


    public void setModeratorID(UUID moderatorID)
    {
        this.moderatorID = moderatorID;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

}
