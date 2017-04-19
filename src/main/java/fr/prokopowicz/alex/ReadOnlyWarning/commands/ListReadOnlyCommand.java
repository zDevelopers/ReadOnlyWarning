package fr.prokopowicz.alex.ReadOnlyWarning.commands;

import fr.prokopowicz.alex.ReadOnlyWarning.Permissions;
import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.prokopowicz.alex.ReadOnlyWarning.players.ReadOnlyPlayer;
import fr.zcraft.zlib.components.commands.Command;
import fr.zcraft.zlib.components.commands.CommandException;
import fr.zcraft.zlib.components.commands.CommandInfo;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.rawtext.RawText;
import fr.zcraft.zlib.tools.commands.PaginatedTextView;
import fr.zcraft.zlib.tools.text.RawMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * Created by Alex on 27/08/2016.
 */
@CommandInfo (name = "list", usageParameters = "[player name]")
public class ListReadOnlyCommand extends Command
{
    @Override
    public void run() throws CommandException
    {
        Integer page = null;

        if (args.length == 0)
        {
            page = 1;
        }
        else if (args.length == 1 && args[0].startsWith("--page="))
        {
            try
            {
                page = Integer.valueOf(args[0].split("=")[1]);
            }
            catch (NumberFormatException ignored) {}
        }

        if (page != null)
        {
            final Collection<ReadOnlyPlayer> readOnlyPlayers = ReadOnlyWarning.get().getReadOnlyPlayersManager().getReadOnlyPlayers().values();

            if (readOnlyPlayers.size() == 0)
            {
                error(I.t("No read only player registered."));
            }
            else
            {
                new ROPlayersPagination()
                        .setData(readOnlyPlayers.toArray(new ReadOnlyPlayer[readOnlyPlayers.size()]))
                        .setCurrentPage(page)
                        .display(sender);
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
            sender.sendMessage(I.t("{gray}Sentenced by {0} on {1}", ReadOnlyWarning.get().getServer().getOfflinePlayer(readOnlyPomf.getModeratorID()).getName(), readOnlyPomf.getCreationDate()));

            sender.sendMessage("");
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
        return Permissions.LIST.grantedfTo(sender);
    }

    private class ROPlayersPagination extends PaginatedTextView<ReadOnlyPlayer>
    {
        @Override
        protected void displayHeader(CommandSender receiver)
        {
            if (receiver instanceof Player) receiver.sendMessage("");
            receiver.sendMessage(I.tn("{green}{bold}{0} player in read-only mode", "{green}{bold}{0} players in read-only mode", data().length));
        }

        @Override
        protected void displayItem(CommandSender receiver, ReadOnlyPlayer player)
        {
            final String name = ReadOnlyWarning.get().getServer().getOfflinePlayer(player.getPlayerID()).getName();

            RawMessage.send(receiver, new RawText(I.t("{gray}- {darkgreen}{0} {gray}({1})", name, player.getReason()))
                    .hover(new RawText(I.t("Click for details on {0}", name)))
                    .command(ListReadOnlyCommand.class, name)
            );
        }

        @Override
        protected String getCommandToPage(int page)
        {
            return super.getCommandToPage(page);
        }
    }
}
