package fr.prokopowicz.alex.ReadOnlyWarning.commands;

import fr.prokopowicz.alex.ReadOnlyWarning.Permissions;
import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.prokopowicz.alex.ReadOnlyWarning.players.ReadOnlyPlayer;
import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import org.apache.commons.lang.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


/**
 * Created by Alex on 27/08/2016.
 */
@CommandInfo (name = "add", usageParameters = "<player name> <reason>")
public class AddReadOnlyCommand extends Command
{
    @Override
    public void run() throws CommandException
    {
        if (args.length < 2) throwInvalidArgument(I.t("Nickname and reason expected."));

        final OfflinePlayer player = ReadOnlyWarning.get().getServer().getOfflinePlayer(args[0]);
        if (player == null) error(I.t("Player not found"));

        final String motif = StringUtils.join(args, ' ', 1, args.length);

        ReadOnlyPlayer roPlayer = ReadOnlyWarning.get().getReadOnlyPlayersManager().addReadOnlyPlayer(
                player.getUniqueId(),
                sender instanceof Player ? ((Player) sender).getUniqueId() : null,
                motif
        );

        if (player.isOnline())
        {
            roPlayer.displayRegularWarning();
        }

        success(I.t("Player successfully placed in ReadOnly mode"));
    }

    @Override
    protected List<String> complete() throws CommandException
    {
        if (args.length == 1) return getMatchingPlayerNames(args[0]);
        else return null;
    }

    @Override
    public boolean canExecute(CommandSender sender)
    {
        return Permissions.ADD.grantedTo(sender);
    }
}
