package fr.prokopowicz.alex.commands;

import fr.prokopowicz.alex.Permissions;
import fr.prokopowicz.alex.ReadOnlyWarning;
import fr.prokopowicz.alex.rawtypes.ReadOnlyPlayer;
import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.rawtext.RawText;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Alex on 27/08/2016.
 */
@CommandInfo (name = "list", usageParameters = "[player name]")
public class ListReadOnlyCommand extends Command
{
    @Override
    public void run() throws CommandException
    {
        if (args.length == 0)
        {
            final Collection<ReadOnlyPlayer> readOnlyPlayers = ReadOnlyWarning.get().getReadOnlyPlayersManager().getReadOnlyPlayers().values();

            if (readOnlyPlayers.size() == 0)
            {
                error(I.t("No read only player registered."));
            }
            else
            {
                sender.sendMessage("");
                sender.sendMessage(I.tn("{green}{bold}{0} player in read-only mode", "{green}{bold}{0} players in read-only mode", readOnlyPlayers.size()));

                for (ReadOnlyPlayer player : readOnlyPlayers)
                {
                    final String name = ReadOnlyWarning.get().getServer().getOfflinePlayer(player.getPlayerID()).getName();

                    send(new RawText(I.t("{gray}- {darkgreen}{0} {gray}({1})", name, player.getReason()))
                            .hover(new RawText(I.t("Click for details on {0}", name)))
                            .command(ListReadOnlyCommand.class, name)
                    );
                }
            }
        }
        else
        {
            final OfflinePlayer player = ReadOnlyWarning.get().getServer().getOfflinePlayer(args[0]);
            if (player == null) throwInvalidArgument("Player name is wrong");

            final ReadOnlyPlayer readOnlyPomf = ReadOnlyWarning.get().getReadOnlyPlayersManager().getReadOnlyPlayer(player.getUniqueId());
            if (readOnlyPomf == null) error(I.t("Player is not in the read-only list"));

            sender.sendMessage("");

            if (!(sender instanceof Player) || !Permissions.REMOVE.grantedfTo(sender))
            {
                sender.sendMessage(I.t("{darkgreen}{bold}Read-only on {0}", player.getName()));
            }
            else
            {
                send(new RawText(I.t("{darkgreen}{bold}Read-only on {0}", player.getName()))
                                .then(" ")
                                .then(I.t("(remove)"))
                                    .color(ChatColor.RED)
                                    .command(RemoveReadOnlyCommand.class, player.getName())
                                    .hover(new RawText(I.t("Click to remove the read-only mode")))
                                .build()
                );
            }

            sender.sendMessage(I.t("{green}Reason: {white}{0}", readOnlyPomf.getReason()));
            sender.sendMessage(I.t("{gray}Sentenced by {0}", ReadOnlyWarning.get().getServer().getOfflinePlayer(readOnlyPomf.getModeratorID()).getName()));

            sender.sendMessage("");
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
        return Permissions.LIST.grantedfTo(sender);
    }
}
