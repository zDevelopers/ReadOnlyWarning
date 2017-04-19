/*
 * Copyright or © or Copr. AmauryCarrade (2015)
 * 
 * http://amaury.carrade.eu
 * 
 * This software is governed by the CeCILL-B license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-B
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info". 
 * 
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability. 
 * 
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or 
 * data to be ensured and,  more generally, to use and operate it in the 
 * same conditions as regards security. 
 * 
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-B license and that you accept its terms.
 */
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

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ReadOnlyPlayersIO extends Worker
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
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
            public Map<UUID, ReadOnlyPlayer> run() throws Throwable
            {
                final Map<UUID, ReadOnlyPlayer> importedPlayers = new HashMap<>();
                final JsonObject dump = GSON.fromJson(FileUtils.readFile(SAVE_TO_FILE), JsonObject.class);

                dump.getAsJsonArray("read_only").forEach(jsonPlayer ->
                {
                    final ReadOnlyPlayer roPlayer = GSON.fromJson(jsonPlayer, ReadOnlyPlayer.class);
                    importedPlayers.put(roPlayer.getPlayerID(), roPlayer);
                });

                return importedPlayers;
            }
        }, callback);
    }
}
