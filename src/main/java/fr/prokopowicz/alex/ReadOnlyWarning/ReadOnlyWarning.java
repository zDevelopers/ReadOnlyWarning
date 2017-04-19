package fr.prokopowicz.alex.ReadOnlyWarning;

import fr.prokopowicz.alex.ReadOnlyWarning.commands.AddReadOnlyCommand;
import fr.prokopowicz.alex.ReadOnlyWarning.commands.ListReadOnlyCommand;
import fr.prokopowicz.alex.ReadOnlyWarning.commands.RemoveReadOnlyCommand;
import fr.prokopowicz.alex.ReadOnlyWarning.players.ReadOnlyPlayersManager;
import fr.zcraft.zlib.components.commands.Commands;
import fr.zcraft.zlib.components.i18n.I18n;
import fr.zcraft.zlib.core.ZPlugin;


/**
 * Created by Alexandre on 03/06/2015.
 */
public class ReadOnlyWarning extends ZPlugin
{
    private static ReadOnlyWarning instance;

    private ReadOnlyPlayersManager readOnlyPlayersManager;

    @Override
    public void onEnable()
    {
        instance = this;

        saveDefaultConfig();

        loadComponents(I18n.class, Config.class, Commands.class);

        I18n.useDefaultPrimaryLocale();
        if (Config.LOCALE.isDefined()) I18n.setPrimaryLocale(Config.LOCALE.get());

        I18n.setFallbackLocale(Config.LOCALE.getDefaultValue());

        readOnlyPlayersManager = loadComponent(ReadOnlyPlayersManager.class);

        Commands.register("readonly", AddReadOnlyCommand.class, RemoveReadOnlyCommand.class, ListReadOnlyCommand.class);
        Commands.registerShortcut("readonly", AddReadOnlyCommand.class, "ro");
        Commands.registerShortcut("readonly", RemoveReadOnlyCommand.class, "unro");
        Commands.registerShortcut("readonly", ListReadOnlyCommand.class, "roinfos");
    }


    public ReadOnlyPlayersManager getReadOnlyPlayersManager()
    {
        return readOnlyPlayersManager;
    }

    public static ReadOnlyWarning get()
    {
        return instance;
    }
}
