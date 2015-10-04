package fr.prokopowicz.alex;

import fr.prokopowicz.alex.listeners.PlayerJoinTextListener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Alexandre on 03/06/2015.
 */
public class ReadOnlyWarning extends JavaPlugin{
    public void onEnable () {
        getServer().getPluginManager().registerEvents(new PlayerJoinTextListener(), this);
    }
}
