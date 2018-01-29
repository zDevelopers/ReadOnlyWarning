package fr.prokopowicz.alex.ReadOnlyWarning.commands;

import fr.prokopowicz.alex.ReadOnlyWarning.Permissions;
import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.prokopowicz.alex.ReadOnlyWarning.players.ReadOnlyPlayer;
import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.commands.WithFlags;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.rawtext.RawText;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by Alex on 27/08/2016.
 */
@CommandInfo (name = "remove", usageParameters = "<player name>")
@WithFlags({"confirm"})
public class RemoveReadOnlyCommand extends Command
{
    @Override
    public void run() throws CommandException
    {
        if (args.length == 0) throwInvalidArgument(I.t("Player not specified"));

        final OfflinePlayer player = ReadOnlyWarning.get().getServer().getOfflinePlayer(args[0]);
        if (player == null) error(I.t("Player not found"));

        final ReadOnlyPlayer roPlayer = ReadOnlyWarning.get().getReadOnlyPlayersManager().getReadOnlyPlayer(player.getUniqueId());

        if (roPlayer != null)
        {
            if (hasFlag("confirm"))
            {
                ReadOnlyWarning.get().getReadOnlyPlayersManager().deleteReadOnlyPlayer(roPlayer.getPlayerID());
                success(I.t("Player successfully removed from read-only mode"));
            }
            else
            {
                final String playerName = player.getName();

                info("");
                warning(I.t("You're about to remove the read-only mode of {0}. Are you sure about that?", playerName));

                if (sender instanceof Player)
                {
                    send(
                        new RawText(" » ")
                                .color(ChatColor.GREEN)
                            .then(I.t("Remove read-only mode"))
                                .style(ChatColor.BOLD, ChatColor.GREEN)
                                .hover(new RawText(I.t("Click to remove the read-only mode of {0}", playerName)))
                                .command(RemoveReadOnlyCommand.class, playerName, "--confirm")
                            .build()
                    );
                }
                else
                {
                    info(I.t("If so, please execute this command:"));
                    info(build(playerName, "--confirm"));
                }
                info("");
            }
        }
        else
        {
            error(I.t("This player is not in read-only mode."));
        }
    }

    @Override
    protected List<String> complete()
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
        return Permissions.REMOVE.grantedTo(sender);
    }
}
