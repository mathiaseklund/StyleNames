package me.mathiaseklund.stylenames;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StyleCommand implements CommandExecutor {

	Messages msg = Messages.getInstance();
	Main plugin = Main.getMain();

	/**
	 * 
	 * @param player
	 * @param name
	 * @return returns TRUE if player has permissiont to use color or no
	 *         colorcodes are present.
	 */
	public boolean colorCheck(Player player, String name) {
		if (name.contains("&0") || name.contains("&1") || name.contains("&2") || name.contains("&3") || name.contains("&4") || name.contains("&5") || name.contains("&6") || name.contains("&7")
				|| name.contains("&8") || name.contains("&9") || name.contains("&a") || name.contains("&b") || name.contains("&c")) {
			if (player.hasPermission("style.color") || player.isOp()) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param player
	 * @param name
	 * @return returns true if player has permission to use formatting or no
	 *         formatting is present.
	 */
	public boolean formatCheck(Player player, String name) {
		if (name.contains("&l") || name.contains("&m") || name.contains("&n") || name.contains("&o") || name.contains("&r")) {
			if (player.hasPermission("style.format") || player.isOp()) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	// This entire plugin can be written in a much better way, I'm just a bit
	// lazy atm.
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				msg.msg(player, plugin.config.getString("message.command.usage"));
			} else if (args.length == 1) {
				if (player.hasPermission("style.use") || player.isOp()) {
					String name = args[0].replaceAll("&k", "");
					// ncName is the written name in supplied string with no
					// color codes.
					String ncName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name));
					/**
					 * compare ncName to the senders name and proceed if it
					 * matches.
					 */
					if (ncName.equals(player.getName())) {
						/**
						 * Check if string has any color codes and if user is
						 * allowed to use them. returns true if they are allowed
						 * to use them or if there aren't any.
						 */
						if (colorCheck(player, name)) {
							if (formatCheck(player, name)) {
								name = ChatColor.translateAlternateColorCodes('&', name + "&r");
								player.setDisplayName(name);
								msg.msg(player, plugin.config.getString("message.command.changed.self").replaceAll("%name", name));
							} else {
								msg.msg(player, plugin.config.getString("message.command.noperm.format"));
							}
						} else {
							msg.msg(player, plugin.config.getString("message.command.noperm.color"));
						}
					} else {
						msg.msg(player, plugin.config.getString("message.command.illegal"));
					}
				} else {
					msg.msg(player, plugin.config.getString("message.command.noperm.use"));
				}
			} else if (args.length == 2) {
				if (player.hasPermission("style.other") || player.isOp()) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target != null) {
						String name = args[0].replaceAll("&k", "");
						// ncName is the written name in supplied string with no
						// color codes.
						String ncName = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name));
						/**
						 * compare ncName to the senders name and proceed if it
						 * matches.
						 */
						if (ncName.equals(target.getName())) {
							/**
							 * Check if string has any color codes and if user
							 * is allowed to use them. returns true if they are
							 * allowed to use them or if there aren't any.
							 */
							if (colorCheck(player, name)) {
								if (formatCheck(player, name)) {

									name = ChatColor.translateAlternateColorCodes('&', name + "&r");
									target.setDisplayName(name);
									msg.msg(target, plugin.config.getString("message.command.changed.self").replaceAll("%name", name));
									msg.msg(player, plugin.config.getString("message.command.changed.other").replaceAll("%name", name).replaceAll("%player", target.getName()));

								} else {
									msg.msg(player, plugin.config.getString("message.command.noperm.format"));
								}
							} else {
								msg.msg(player, plugin.config.getString("message.command.noperm.color"));
							}
						} else {
							msg.msg(player, plugin.config.getString("message.command.illegal"));
						}
					} else {
						msg.msg(player, plugin.config.getString("message.command.notarget"));
					}
				} else {
					msg.msg(player, plugin.config.getString("message.command.noperm.other"));
				}

			}
		}
		return false;
	}
}
