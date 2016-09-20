package fr.prokopowicz.alex;

import fr.prokopowicz.alex.commands.ReadOnlyCommand;
import fr.prokopowicz.alex.commands.ReadOnlyInfosCommand;
import fr.prokopowicz.alex.commands.UnReadOnlyCommand;
import fr.prokopowicz.alex.listeners.PlayerInteractionListener;
import fr.prokopowicz.alex.listeners.PlayerWarningListener;
import fr.prokopowicz.alex.managers.ReadOnlyPlayersManager;
import fr.zcraft.zlib.components.i18n.I18n;
import fr.zcraft.zlib.core.ZLib;
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

        loadComponents(I18n.class, ROConfig.class);

        I18n.useDefaultPrimaryLocale();

        readOnlyPlayersManager = loadComponent(ReadOnlyPlayersManager.class);

        ZLib.registerEvents(new PlayerWarningListener());
        ZLib.registerEvents(new PlayerInteractionListener());

        getServer().getPluginCommand("ro").setExecutor(new ReadOnlyCommand());
        getServer().getPluginCommand("unro").setExecutor(new UnReadOnlyCommand());
        getServer().getPluginCommand("roinfos").setExecutor(new ReadOnlyInfosCommand());
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
