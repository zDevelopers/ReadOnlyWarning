package fr.prokopowicz.alex.commands;

import fr.prokopowicz.alex.ReadOnlyWarning;
import fr.prokopowicz.alex.rawtypes.ReadOnlyPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/**
 * Created by Alex on 27/08/2016.
 */
public class ReadOnlyCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args)
    {
        if (args.length < 2)
        {
            sender.sendMessage("Syntax error : nickname and reason expected.");
            return true;
        }

        final OfflinePlayer player = ReadOnlyWarning.get().getServer().getOfflinePlayer(args[0]);
        if (player == null)
        {
            sender.sendMessage("Player not found");
            return true;
        }

        String motif = "";
        for (int i = 1; i < args.length; i++)
        {
            motif = motif + args[i] + " ";
        }

        ReadOnlyPlayer roPlayer = ReadOnlyWarning.get().getReadOnlyPlayersManager().addReadOnlyPlayer(
                player.getUniqueId(),
                sender instanceof Player ? ((Player) sender).getUniqueId() : null,
                motif
        );

        if (player.isOnline())
        {
            roPlayer.displayRegularWarning();
        }

        sender.sendMessage("Player successfully placed in ReadOnly mode");

        return true;
    }
}
