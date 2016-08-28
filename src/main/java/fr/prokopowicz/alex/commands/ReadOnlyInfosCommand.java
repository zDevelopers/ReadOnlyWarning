package fr.prokopowicz.alex.commands;

import fr.prokopowicz.alex.ReadOnlyWarning;
import fr.prokopowicz.alex.managers.ReadOnlyPlayersManager;
import fr.prokopowicz.alex.rawtypes.ReadOnlyPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Alex on 27/08/2016.
 */
public class ReadOnlyInfosCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length==0){
            for (ReadOnlyPlayer player : ReadOnlyWarning .getInstance() .getReadOnlyPlayersManager() .getReadOnlyPlayers().values()){
                sender.sendMessage(ReadOnlyWarning .getInstance() .getServer() .getOfflinePlayer(player.getPlayerID ()) .getName() + ": " + player.getReason());
            }
        }
        else {
            OfflinePlayer player = ReadOnlyWarning.getInstance() .getServer() .getOfflinePlayer(args [0]);
            if (player == null)
            {
                sender.sendMessage("Player name is wrong");
                return true;
            }

            ReadOnlyPlayer pomf = ReadOnlyWarning.getInstance() .getReadOnlyPlayersManager() .getReadOnlyPlayer(player.getUniqueId());
            if (pomf == null)
            {
                sender.sendMessage("Player is not on ReadOnly list");
                return true;
            }

            sender.sendMessage("Name: " + player.getName());
            sender.sendMessage("Reason: " + pomf.getReason());
            sender.sendMessage("Sentenced by: " + ReadOnlyWarning .getInstance() .getServer() .getOfflinePlayer(pomf.getModeratorID()).getName());
        }
        return true;
    }
}
