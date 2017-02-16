package me.mathiaseklund.stylenames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {

	private static Messages instance = new Messages();

	public static Messages getInstance() {
		return instance;
	}

	public void msg(Player player, String msg) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", player.getName())));
	}

	public void cmsg(CommandSender sender, String msg) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", sender.getName())));
	}

	public void brdcst(String msg) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
}
