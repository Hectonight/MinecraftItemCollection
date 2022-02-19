package me.hecto.minecraftitemcollection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ItemIgnore implements CommandExecutor {

	public final String cmd = "itemsIgnore";

	@Override
	public boolean onCommand(@NotNull CommandSender commandSender,
							 @NotNull Command command, @NotNull String label, String[] args) {

		Properties properties = new Properties();
		try {
			properties.load(new FileReader("items.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}


		if (properties.containsKey(args[0].toUpperCase())) {
			properties.setProperty(args[0].toUpperCase(), "null");
			try {
				properties.store(new FileWriter("items.properties"), new VariableStorage().propFileMsg);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

			commandSender.getServer().broadcastMessage("Ignoring " + args[0]);
			return true;
		}
		return false;
	}
}