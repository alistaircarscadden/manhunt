package io.github.tejedu.manhunt;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ManHuntCoin {
	private static String displayName = "Capture Receipt";
	private static String[] lore = { "" + ChatColor.RESET + ChatColor.ITALIC + "Proof of capturing somebody" };
	private static Enchantment enchantment = Enchantment.FIRE_ASPECT;

	public static ItemStack getItemStack(int amount, String ... owner) {
		ItemStack itemstack = new ItemStack(Material.PAPER, amount);
		
		itemstack.addUnsafeEnchantment(enchantment, 1);
		List<String> itemLore = new ArrayList<String>();
		for (String line : lore)
			itemLore.add(line);
		
		if (owner.length > 0)
			itemLore.add("" + ChatColor.RESET + ChatColor.ITALIC + "Capturer: " + ChatColor.BLUE + owner[0]);
		
		ItemMeta itemMeta = itemstack.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.setLore(itemLore);
		itemMeta.setDisplayName(displayName);
		
		itemstack.setItemMeta(itemMeta);
		itemstack.addUnsafeEnchantment(enchantment, 1);
		
		return itemstack;
	}

	public static boolean isManHuntCoin(ItemStack itemstack) {
		if (itemstack.getItemMeta().getDisplayName().equals(displayName)
				&& itemstack.getItemMeta().hasEnchant(enchantment))
			return true;
		return false;
	}

}
