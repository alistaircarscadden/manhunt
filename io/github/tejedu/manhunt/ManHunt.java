/**
 * Man Hunt by tejedu.githib.io
 * Contact: awoh.g17@gmail.com
 * Do not steal my stuff
 */

package io.github.tejedu.manhunt;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import io.github.tejedu.store.IconMenu;
import net.md_5.bungee.api.ChatColor;

public class ManHunt extends JavaPlugin {
	public BukkitTask startGameTimer;
	public Game game;
	public ChatColor highlightColor;
	public IconMenu store;

	@Override
	public void onEnable() {
		configuration();
		this.getServer().getPluginManager().registerEvents(new CaptureListener(this), this);
		this.highlightColor = ChatColor.getByChar(this.getConfig().getString("preferences.chathighlight").charAt(0));
		this.getCommand("mh").setExecutor(new ManHuntCommandExecutor(this));
		queueGame();
	}

	public void configuration() {
		FileConfiguration config = this.getConfig();
		config.options().copyDefaults(true);
		this.saveConfig();
	}

	public void queueGame(int secondsDelay) {
		if (game != null) {
			game.cancelSurviveTimer();
			game = null;
		}
		startGameTimer = new StartGameTimer(this).runTaskLater(this, 20 * secondsDelay);
	}

	public void queueGame() {
		queueGame(this.getConfig().getInt("round.delay"));
	}

	public void createGame() {
		game = new Game(this);
	}
}
