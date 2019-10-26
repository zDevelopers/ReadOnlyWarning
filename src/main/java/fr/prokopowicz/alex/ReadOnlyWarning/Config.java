package fr.prokopowicz.alex.ReadOnlyWarning;

import fr.zcraft.zlib.components.configuration.Configuration;
import fr.zcraft.zlib.components.configuration.ConfigurationItem;
import fr.zcraft.zlib.components.configuration.ConfigurationList;
import fr.zcraft.zlib.components.configuration.ConfigurationSection;

import java.util.Locale;

import static fr.zcraft.zlib.components.configuration.ConfigurationItem.item;
import static fr.zcraft.zlib.components.configuration.ConfigurationItem.section;


public class Config extends Configuration
{
    static public final ConfigurationItem<Locale> LOCALE = item("lang", Locale.ENGLISH);

    static public final WarningMessageSection WARNING_MESSAGE = section("warning_message", WarningMessageSection.class);
    static public class WarningMessageSection extends ConfigurationSection
    {
        public final ConfigurationItem<String> HEADER = item("header", "");
        public final ConfigurationItem<String> MESSAGE = item("message", "");
        public final ConfigurationItem<String> LINK = item("link", "");
        public final ConfigurationItem<String> FOOTER = item("footer", "");
    }
    
    static public final ConfigurationItem<Boolean> BLOCK_CHAT = item("block_chat", false);

    static public final ConfigurationItem<Long> WARNING_INTERVAL = item("warning_interval", 10L);

    static public final CommandsSection COMMANDS = section("commands", CommandsSection.class);
    static public class CommandsSection extends ConfigurationSection
    {
        public final ConfigurationList<String> READONLY_ENABLED = list("readonly_enabled", String.class);
        public final ConfigurationList<String> READONLY_DISABLED = list("readonly_disabled", String.class);
    }
}
