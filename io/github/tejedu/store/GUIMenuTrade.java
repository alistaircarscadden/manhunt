package io.github.tejedu.store;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.tejedu.manhunt.ManHunt;

public class GUIMenuTrade extends GUIMenuOption {
	private List<ItemStack> price, product;

	public GUIMenuTrade() {
		price = new ArrayList<ItemStack>();
		product = new ArrayList<ItemStack>();
	}

	@Override
	public void onClick(ManHunt plugin, Player player) {
		Inventory inventory = player.getInventory();

		for (ItemStack itemStack : price) {
			if (!inventory.contains(itemStack)) {
				player.sendMessage("You are missing necessary items.");
				return;
			}
		}

		for (ItemStack itemStack : price) {
			inventory.remove(itemStack);
		}

		if (inventory.getSize() - inventory.getContents().length >= product.size()) {
			for (ItemStack itemStack : product) {
				inventory.addItem(itemStack);
			}
		}
	}

	public List<ItemStack> getPrice() {
		return price;
	}

	public void setPrice(List<ItemStack> price) {
		this.price = price;
	}

	public void addPrice(ItemStack price) {
		this.price.add(price);
	}

	public void removePrice(ItemStack price) {
		this.price.remove(price);
	}

	public List<ItemStack> getProduct() {
		return product;
	}

	public void setProduct(List<ItemStack> product) {
		this.product = product;
	}

	public void addProduct(ItemStack product) {
		this.product.add(product);
	}

	public void removeProduct(ItemStack product) {
		this.product.remove(product);
	}
}
