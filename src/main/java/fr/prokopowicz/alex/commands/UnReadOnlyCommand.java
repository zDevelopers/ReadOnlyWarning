package fr.prokopowicz.alex.commands;

import fr.prokopowicz.alex.ReadOnlyWarning;
import fr.prokopowicz.alex.rawtypes.ReadOnlyPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


/**
 * Created by Alex on 27/08/2016.
 */
public class UnReadOnlyCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage("Player not specified");
            return true;
        }

        final OfflinePlayer player = ReadOnlyWarning.get().getServer().getOfflinePlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage("Player not found");
            return true;
        }

        final ReadOnlyPlayer roPlayer = ReadOnlyWarning.get().getReadOnlyPlayersManager().deleteReadOnlyPlayer(player.getUniqueId());

        if (roPlayer != null)
        {
            roPlayer.stopWarningDisplay();
            sender.sendMessage("Player successfully removed from read-only mode");
        }
        else
        {
            sender.sendMessage("This player is not in read-only mode.");
        }

        return true;
    }
}
