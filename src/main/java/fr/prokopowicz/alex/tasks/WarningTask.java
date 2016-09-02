package fr.prokopowicz.alex.tasks;

import fr.prokopowicz.alex.ReadOnlyWarning;
import fr.prokopowicz.alex.rawtypes.ReadOnlyPlayer;
import fr.zcraft.zlib.tools.text.Titles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class WarningTask extends BukkitRunnable
{
    private final ReadOnlyPlayer warnedPlayer;

    public WarningTask(ReadOnlyPlayer player)
    {
        this.warnedPlayer = player;
    }

    @Override
    public void run()
    {
        final Player player = ReadOnlyWarning.get().getServer().getPlayer(warnedPlayer.getPlayerID());

        if (player != null && player.isOnline())
        {
            player.sendMessage("");
            player.sendMessage(ChatColor.BOLD + "You've been placed in read only, asshole");
            player.sendMessage("Reason: " + warnedPlayer.getReason());
            player.sendMessage("Please explain yourself on the forum at https://forum.zcraft.fr/viewtopic.php?id=1980");
            player.sendMessage("");

            Titles.displayTitle(player, 10, 120, 10, ChatColor.RED + "READ ONLY (waa)", "");
        }
    }
}
