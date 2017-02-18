package io.github.tejedu.manhunt;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SurviveTimer extends BukkitRunnable {
	private ManHunt plugin;

	public SurviveTimer(JavaPlugin plugin) {
		this.plugin = (ManHunt)plugin;
	}

	@Override
	public void run() {
		if (plugin.game != null)
			plugin.game.survive();
	}
}
