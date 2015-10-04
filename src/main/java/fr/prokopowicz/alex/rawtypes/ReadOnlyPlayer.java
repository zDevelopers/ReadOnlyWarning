package fr.prokopowicz.alex.rawtypes;

import java.util.UUID;

/**
 * Represents a player currently in a read-only state.
 */
public class ReadOnlyPlayer {

	/**
	 * The player in a read-only state.
	 */
	private UUID playerID;

	/**
	 * The moderator who placed this player in a read-only state.
	 */
	private UUID moderatorID;

	/**
	 * Why this player is in a read-only state.
	 */
	private String reason;



	public ReadOnlyPlayer(UUID playerID, UUID moderatorID, String reason) {
		this.playerID = playerID;
		this.moderatorID = moderatorID;
		this.reason = reason;
	}


	/**
	 * Sends a warning message to this player about his state.
	 *
	 * @return {@code true} if the message was sent (i.e. the player is online).
	 */
	public boolean sendWarningMessage() {
		// TODO
		return true;
	}



	public UUID getPlayerID() {
		return playerID;
	}

	public UUID getModeratorID() {
		return moderatorID;
	}

	public String getReason() {
		return reason;
	}


	public void setModeratorID(UUID moderatorID) {
		this.moderatorID = moderatorID;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
