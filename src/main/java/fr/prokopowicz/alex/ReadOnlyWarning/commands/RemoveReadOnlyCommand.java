package fr.prokopowicz.alex.ReadOnlyWarning.commands;

import fr.prokopowicz.alex.ReadOnlyWarning.Permissions;
import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.prokopowicz.alex.ReadOnlyWarning.players.ReadOnlyPlayer;
import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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
            final List<String> roPlayers = ReadOnlyWarning.get()
                    .getReadOnlyPlayersManager().getReadOnlyPlayers().values().stream()
                    .map(readOnlyPlayer -> Bukkit.getOfflinePlayer(readOnlyPlayer.getPlayerID()).getName())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

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
