package fr.prokopowicz.alex.ReadOnlyWarning.players;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.prokopowicz.alex.ReadOnlyWarning.ReadOnlyWarning;
import fr.zcraft.zlib.components.worker.Worker;
import fr.zcraft.zlib.components.worker.WorkerCallback;
import fr.zcraft.zlib.components.worker.WorkerRunnable;
import fr.zcraft.zlib.tools.FileUtils;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ReadOnlyPlayersIO extends Worker
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private static final File SAVE_TO_FILE = new File(ReadOnlyWarning.get().getDataFolder(), "read_only.json");

    static void saveReadOnlyPlayers()
    {
        final Collection<ReadOnlyPlayer> players = ReadOnlyWarning.get().getReadOnlyPlayersManager().getReadOnlyPlayers().values();

        submitQuery(new WorkerRunnable<Void>()
        {
            @Override
            public Void run() throws Throwable
            {
                final JsonArray playersJson = new JsonArray();
                players.forEach(player -> playersJson.add(GSON.toJsonTree(player)));

                final JsonObject dump = new JsonObject();
                dump.add("read_only", playersJson);

                FileUtils.writeFile(SAVE_TO_FILE, GSON.toJson(dump));
                return null;
            }
        });
    }

    static void loadReadOnlyPlayers(final WorkerCallback<Map<UUID, ReadOnlyPlayer>> callback)
    {
        submitQuery(new WorkerRunnable<Map<UUID, ReadOnlyPlayer>>()
        {
            @Override
            public Map<UUID, ReadOnlyPlayer> run()
            {
                final Map<UUID, ReadOnlyPlayer> importedPlayers = new HashMap<>();
                final JsonObject dump = GSON.fromJson(FileUtils.readFile(SAVE_TO_FILE), JsonObject.class);

                final JsonArray jsonPlayers = dump.getAsJsonArray("read_only");

                if (jsonPlayers == null || jsonPlayers.size() == 0)
                    return importedPlayers; // Nothing saved

                jsonPlayers.forEach(jsonPlayer ->
                {
                    final ReadOnlyPlayer roPlayer = GSON.fromJson(jsonPlayer, ReadOnlyPlayer.class);
                    importedPlayers.put(roPlayer.getPlayerID(), roPlayer);
                });

                return importedPlayers;
            }
        }, callback);
    }

    static void backupFile()
    {
        submitQuery(new WorkerRunnable<Void>()
        {
            @Override
            public Void run()
            {
                FileUtil.copy(SAVE_TO_FILE, new File(ReadOnlyWarning.get().getDataFolder(), "read_only." + new Date().getTime() + ".json"));
                return null;
            }
        });
    }
}
