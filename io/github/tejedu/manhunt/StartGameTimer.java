package io.github.tejedu.manhunt;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class StartGameTimer extends BukkitRunnable {
	private ManHunt plugin;

	public StartGameTimer(JavaPlugin plugin) {
		this.plugin = (ManHunt) plugin;
	}

	@Override
	public void run() {
		plugin.createGame();
	}
}
