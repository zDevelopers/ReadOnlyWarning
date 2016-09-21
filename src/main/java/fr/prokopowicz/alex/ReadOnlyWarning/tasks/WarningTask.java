package fr.prokopowicz.alex.ReadOnlyWarning.tasks;

import fr.prokopowicz.alex.ReadOnlyWarning.Config;
import fr.prokopowicz.alex.ReadOnlyWarning.players.ReadOnlyPlayer;
import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.zcraft.zlib.components.i18n.I;
import fr.zcraft.zlib.components.rawtext.RawText;
import fr.zcraft.zlib.tools.PluginLogger;
import fr.zcraft.zlib.tools.text.RawMessage;
import fr.zcraft.zlib.tools.text.Titles;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.ChatPaginator;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class WarningTask extends BukkitRunnable
{
    private final ReadOnlyPlayer warnedPlayer;

    public WarningTask(ReadOnlyPlayer player)
    {
        this.warnedPlayer = player;
    }

    @Override
    public void run()
    {
        final Player player = ReadOnlyWarning.get().getServer().getPlayer(warnedPlayer.getPlayerID());

        if (player != null && player.isOnline())
        {
            final String header  = ChatColor.translateAlternateColorCodes('&', Config.WARNING_MESSAGE.HEADER.get());
            final String message = ChatColor.translateAlternateColorCodes('&', Config.WARNING_MESSAGE.MESSAGE.get());
            final String link    = Config.WARNING_MESSAGE.LINK.get();
            final String footer  = ChatColor.translateAlternateColorCodes('&', Config.WARNING_MESSAGE.FOOTER.get());

            final String moderatorName = warnedPlayer.getModeratorID() != null ? Bukkit.getOfflinePlayer(warnedPlayer.getModeratorID()).getName() : I.t("{red}console{reset}");


            player.sendMessage("");
            if (!header.trim().isEmpty()) player.sendRawMessage(header);

            sendSplitText(player, I.t("You've been placed in read-only by {darkgreen}{0}{reset}, because of the following:", moderatorName));
            player.sendMessage("");

            for (final String line : splitText(warnedPlayer.getReason()))
            {
                player.sendMessage(centerText(ChatColor.GOLD + line));
            }

            if (!message.trim().isEmpty())
            {
                player.sendMessage("");

                if (link.isEmpty())
                {
                    sendSplitText(player, message);
                }
                else
                {
                    try
                    {
                        for (final String line : splitText(message))
                        {
                            RawMessage.send(player, new RawText(line).uri(link).hover(new RawText(I.t("Open the link: {0}", link))).build());
                        }
                    }
                    catch (URISyntaxException e)
                    {
                        PluginLogger.error("Invalid link: {0}", link);
                        sendSplitText(player, message);
                    }
                }
            }

            if (!footer.trim().isEmpty()) player.sendRawMessage(footer);


            Titles.displayTitle(player, 10, 120, 10, I.t("{red}Read only"), I.t("{yellow}Why? Please read the chat."));
        }
    }

    private String centerText(String text)
    {
        final int shift = (ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH / 2) - (ChatColor.stripColor(text).length() / 2);

        return StringUtils.repeat(" ", shift) + text;
    }

    private List<String> splitText(final String text)
    {
        final List<String> lines = new ArrayList<>();
        String line = "";

        for (final String word : text.split(" "))
        {
            if (line.length() >= ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH)
            {
                lines.add(line.trim());
                line = ChatColor.getLastColors(line);
            }

            line += word + " ";
        }

        lines.add(line.trim());

        return lines;
    }

    private void sendSplitText(final Player player, final String text)
    {
        for (final String line : splitText(text))
            player.sendMessage(line);
    }
}
