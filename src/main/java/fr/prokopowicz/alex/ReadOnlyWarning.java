package fr.prokopowicz.alex;

import fr.prokopowicz.alex.listeners.PlayerJoinTextListener;
import fr.prokopowicz.alex.managers.ReadOnlyPlayersManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Alexandre on 03/06/2015.
 */
public class ReadOnlyWarning extends JavaPlugin {

	private static ReadOnlyWarning instance;

	private ReadOnlyPlayersManager readOnlyPlayersManager;

	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(new PlayerJoinTextListener(), this);
		// TODO Managers
	}


	public ReadOnlyPlayersManager getReadOnlyPlayersManager() {
		return readOnlyPlayersManager;
	}

	public static ReadOnlyWarning getInstance() {
		return instance;
	}
}
