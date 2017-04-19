package fr.prokopowicz.alex.ReadOnlyWarning;

import fr.zcraft.zlib.components.configuration.Configuration;
import fr.zcraft.zlib.components.configuration.ConfigurationItem;
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
}
