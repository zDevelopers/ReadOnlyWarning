package prokopowicz.alex;

import org.bukkit.plugin.java.JavaPlugin;
import prokopowicz.alex.managers.ReadOnlyPlayersManager;

/**
 * Created by Alexandre on 03/06/2015.
 */
public class ReadOnlyWarning extends JavaPlugin {

	private static ReadOnlyWarning instance;

	private ReadOnlyPlayersManager readOnlyPlayersManager;

	@Override
	public void onEnable() {
		instance = this;

		// TODO Managers
	}


	public ReadOnlyPlayersManager getReadOnlyPlayersManager() {
		return readOnlyPlayersManager;
	}

	public static ReadOnlyWarning getInstance() {
		return instance;
	}
}
