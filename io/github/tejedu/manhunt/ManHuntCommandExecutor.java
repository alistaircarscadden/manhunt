package io.github.tejedu.manhunt;

import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ManHuntCommandExecutor implements CommandExecutor {

	private ManHunt plugin;

	public ManHuntCommandExecutor(ManHunt plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mh")) {
			if (args.length == 0) {
				cmdVerify(sender);
			} else {
				String[] argsSubset = Arrays.copyOfRange(args, 1, args.length);
				if (args[0].equalsIgnoreCase("target"))
					cmdTarget(sender, argsSubset);
				else if (args[0].equalsIgnoreCase("start"))
					cmdStart(sender, argsSubset);
				else if (args[0].equalsIgnoreCase("help"))
					cmdHelp(sender, argsSubset);
				else if (args[0].equalsIgnoreCase("test"))
					cmdTest(sender, argsSubset);
				else if (args[0].equalsIgnoreCase("mhc"))
					cmdMhc(sender, argsSubset);
			}
		}
		return true;
	}

	private void cmdHelp(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("Here's a list of different subjects to get help about.");
			sender.sendMessage("Use the command '/mh help <subject>' to get further information.");
			sender.sendMessage(
					plugin.highlightColor + "info" + ChatColor.RESET + ": info about the various Man Hunt commands");
			sender.sendMessage(plugin.highlightColor + "commands" + ChatColor.RESET
					+ ": info about the various Man Hunt commands");
			/* TODO finish help menu */
		}
	}

	private void cmdVerify(CommandSender sender) {
		if (sender instanceof Player && plugin.game != null && (Player) sender == plugin.game.target) {
			plugin.game.targetVerified = true;
			sender.sendMessage("If you survive for " + plugin.getConfig().getInt("round.maxlength")
					+ " seconds you will recieve the prize!");
		}
	}

	private void cmdStart(CommandSender sender, String[] args) {
		/* TODO create new start command */
	}

	private void cmdTarget(CommandSender sender, String[] args) {
		if (plugin.game != null && plugin.game.target instanceof Player) {
			sender.sendMessage("The target is " + plugin.highlightColor + ChatColor.ITALIC
					+ plugin.game.target.getDisplayName() + ChatColor.RESET + "!");
		} else {
			sender.sendMessage("There is currently no target.");
		}
	}

	private void cmdTest(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Prize prize = new Prize();
			Inventory inventory = player.getInventory();
			if (inventory.firstEmpty() != -1) {
				inventory.addItem(prize);
			} else {
				player.getWorld().dropItem(player.getLocation(), prize);
			}
			System.out.println(prize.getType().name());

		}
	}

	private void cmdMhc(CommandSender sender, String[] args) {
		ItemStack is;
		if (args.length > 0) {
			is = ManHuntCoin.getItemStack(Integer.parseInt(args[0]));
		} else {
			is = ManHuntCoin.getItemStack(1, "aeae");
		}
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.getInventory().addItem(is);
		}
	}
}
