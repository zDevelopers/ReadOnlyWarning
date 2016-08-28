package fr.prokopowicz.alex.listeners;

import fr.prokopowicz.alex.ReadOnlyWarning;
import fr.prokopowicz.alex.rawtypes.ReadOnlyPlayer;
import fr.zcraft.zlib.tools.runners.RunTask;
import fr.zcraft.zlib.tools.text.Titles;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

/**
 * Created by Alexandre on 03/06/2015.
 */
public class PlayerWarningListener implements Listener{
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        final UUID cot = event.getPlayer() .getUniqueId();
        RunTask.timer(new Runnable() {
            @Override
            public void run()
            {
                ReadOnlyPlayer ericsaget = ReadOnlyWarning.getInstance() .getReadOnlyPlayersManager() .getReadOnlyPlayer(cot);
                Player pomme = ReadOnlyWarning.getInstance() .getServer() .getPlayer(cot);
                if (ericsaget != null && pomme != null && pomme .isOnline())
                {
                    pomme.sendMessage("");
                    pomme.sendMessage(ChatColor.BOLD + "You've been placed in read only, asshole");
                    pomme.sendMessage("Reason: " + ericsaget.getReason());
                    pomme.sendMessage("Please explain yourself on the forum at https://forum.zcraft.fr/viewtopic.php?id=1980");
                    pomme.sendMessage("");

                    Titles.displayTitle(pomme, 10, 120, 10, ChatColor.RED + "READ ONLY (waa)", "");
                }
            }
        }, 2L, 10*60*20);
    }
}
