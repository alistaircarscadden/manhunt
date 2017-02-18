package io.github.tejedu.store;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.tejedu.manhunt.ManHunt;

public class GUIMenuOption {
	private String name;
	private ItemStack icon;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}
	
	public void setIcon(Material iconMaterial) {
		this.icon = new ItemStack(iconMaterial, 1);
	}

	public GUIMenuOption() {
	}

	public void onClick(ManHunt plugin, Player player) {

	}
}
