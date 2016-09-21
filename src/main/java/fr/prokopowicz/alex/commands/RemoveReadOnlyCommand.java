package fr.prokopowicz.alex.commands;

import fr.prokopowicz.alex.Permissions;
import fr.prokopowicz.alex.ReadOnlyWarning;
import fr.prokopowicz.alex.rawtypes.ReadOnlyPlayer;
import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alex on 27/08/2016.
 */
@CommandInfo (name = "remove", usageParameters = "<player name>")
public class RemoveReadOnlyCommand extends Command
{
    @Override
    public void run() throws CommandException
    {
        if (args.length == 0) throwInvalidArgument(I.t("Player not specified"));

        final OfflinePlayer player = ReadOnlyWarning.get().getServer().getOfflinePlayer(args[0]);
        if (player == null) error(I.t("Player not found"));

        final ReadOnlyPlayer roPlayer = ReadOnlyWarning.get().getReadOnlyPlayersManager().deleteReadOnlyPlayer(player.getUniqueId());

        if (roPlayer != null)
        {
            roPlayer.stopWarningDisplay();
            success(I.t("Player successfully removed from read-only mode"));
        }
        else
        {
            error(I.t("This player is not in read-only mode."));
        }
    }

    @Override
    protected List<String> complete() throws CommandException
    {
        if (args.length == 1)
        {
            final List<String> roPlayers = new ArrayList<>();
            for (ReadOnlyPlayer readOnlyPlayer : ReadOnlyWarning.get().getReadOnlyPlayersManager().getReadOnlyPlayers().values())
                roPlayers.add(Bukkit.getOfflinePlayer(readOnlyPlayer.getPlayerID()).getName());

            return getMatchingSubset(roPlayers, args[0]);
        }
        else return null;
    }

    @Override
    public boolean canExecute(CommandSender sender)
    {
        return Permissions.REMOVE.grantedfTo(sender);
    }
}
