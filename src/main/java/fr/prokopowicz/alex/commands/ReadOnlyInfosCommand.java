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
        return false;
    }
}
