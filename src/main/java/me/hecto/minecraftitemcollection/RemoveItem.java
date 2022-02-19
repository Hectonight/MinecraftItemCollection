package me.hecto.minecraftitemcollection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class RemoveItem implements CommandExecutor {

	public final String cmd = "itemsRemove";

	public final Properties properties = new Properties();



	@Override
	public boolean onCommand(@NotNull CommandSender commandSender,
							 @NotNull Command command, @NotNull String label, String[] args) {

		FileReader reader;
		try {
			reader = new FileReader("items.properties");
			properties.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		if(!properties.containsKey(args[0].toUpperCase())) {
			return false;
		}
		properties.setProperty(args[0].toUpperCase(), "false");
		try {
			properties.store(new FileWriter("items.properties"), new VariableStorage().propFileMsg);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		commandSender.getServer().broadcastMessage("Set item " + args[0] + " to false");
		return true;
	}

}
