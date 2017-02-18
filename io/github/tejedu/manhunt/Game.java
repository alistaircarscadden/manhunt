package io.github.tejedu.manhunt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import net.md_5.bungee.api.ChatColor;

public class Game {

	public Player target;
	public boolean targetVerified;
	public ManHunt plugin;
	public BukkitTask surviveTimer;

	public Game(JavaPlugin plugin) {
		this.plugin = (ManHunt) plugin;
		setupNewGame();
	}

	public void setupNewGame() {
		// Create a list of all the players who have permission manhunt.basic
		List<Player> players = new ArrayList<Player>();
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.hasPermission("manhunt.basic")) {
				if (plugin.getConfig().getBoolean("round.survivalplayersonly")) {
					if (player.getGameMode() == GameMode.SURVIVAL) {
						// If it can only be survival players, and this one IS a
						// survival player
						players.add(player);
					}
				} else {
					// It does not HAVE to be survival players, add all players
					players.add(player);
				}
			}
		}

		// If there are not enough players (determined by configuration) a new
		// game is queued, and the current startup is cancelled
		if (players.size() < plugin.getConfig().getInt("round.minplayers")) {
			plugin.queueGame();
			return;
		}

		// This decides who is going to be the target, and messages the server
		// about the new game
		Collections.shuffle(players);
		plugin.getServer()
				.broadcastMessage("Man Hunt has started! " + plugin.highlightColor + ChatColor.ITALIC
						+ players.get(0).getDisplayName() + ChatColor.RESET
						+ " is the target! Find and right-click them to earn a reward!");
		target = players.get(0);
		targetVerified = false;

		// Tells the target they must verify themselves to earn a prize
		target.sendMessage("In order to earn a prize for surviving enter " + plugin.highlightColor + "/mh"
				+ ChatColor.RESET + " into the chat.");

		// Creates the timer that will count down until the round ends
		surviveTimer = new SurviveTimer(plugin).runTaskLater(plugin, 20 * plugin.getConfig().getInt("round.maxlength"));
	}

	public void capture(Player capturer) {
		surviveTimer.cancel();

		// Give prize
		Prize prize = new Prize();
		givePrize(capturer, prize);

		// Tell capturer, target, and server what happened
		capturer.sendMessage("You captured " + plugin.highlightColor + ChatColor.ITALIC + target.getDisplayName()
				+ ChatColor.RESET + "!");
		target.sendMessage("You were captured by " + plugin.highlightColor + ChatColor.ITALIC
				+ capturer.getDisplayName() + ChatColor.RESET + "!");
		Bukkit.broadcastMessage("" + plugin.highlightColor + ChatColor.ITALIC + capturer.getDisplayName()
				+ ChatColor.RESET + " captured " + plugin.highlightColor + ChatColor.ITALIC + target.getDisplayName()
				+ ChatColor.RESET + " and won " + prize.getAmount() + " " + prize.getName() + "!");

		plugin.queueGame();
	}

	public void survive() {
		if (!targetVerified) {
			target.sendMessage("You survived but did not verify that you were not AFK!");
			plugin.queueGame();
			return;
		}

		// Give prize
		Prize prize = new Prize();
		givePrize(target, prize);

		// Tell the survivor, and server what happened
		target.sendMessage("You survived!");
		Bukkit.broadcastMessage("" + plugin.highlightColor + ChatColor.ITALIC + target.getDisplayName()
				+ ChatColor.RESET + " survived and won " + prize.getAmount() + " " + prize.getName() + "!");

		plugin.queueGame();
	}

	private void givePrize(Player player, Prize prize) {
		Inventory inventory = player.getInventory();
		if (inventory.firstEmpty() != -1)
		{
			inventory.addItem(prize);
		}
		else
		{
			player.getWorld().dropItem(player.getLocation(), prize);
		}
	}

	public void cancelSurviveTimer() {
		if (surviveTimer != null) {
			surviveTimer.cancel();
		}
	}
	
	// returns time left in either only minutes left or, if under 120 seconds, seconds left
	// examples: 
	// 	if 125 seconds left, it would return '2 minutes' 
	public String getSurviveTimer() {
		String timeLeft;
		if(surviveTimer > 120) {
			surviveTimer = surviveTimer / 60;
			timeleft = surviveTimer + " minutes ";
			return timeLeft;
		}
		else {
			timeleft = surviveTimer + " seconds ";
			return timeLeft;
		}
}
