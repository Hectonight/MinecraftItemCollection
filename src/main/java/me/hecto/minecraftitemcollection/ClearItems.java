package me.hecto.minecraftitemcollection;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class ClearItems implements CommandExecutor {

	public final String cmd = "itemsClear";

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


		for (Object obj : Collections.list(properties.keys())) {
			properties.setProperty(obj.toString(), "false");
		}
		try {
			properties.store(new FileWriter("items.properties"), new VariableStorage().propFileMsg);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		commandSender.getServer().broadcastMessage("Cleared all item history");
		return true;


	}

}