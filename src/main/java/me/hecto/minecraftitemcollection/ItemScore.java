package me.hecto.minecraftitemcollection;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ItemScore implements CommandExecutor {

	public final String cmd = "itemsScore";

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

		if (command.getName().equalsIgnoreCase(cmd)){
			int count = 0;
			for(Material material: Material.values()){
				if (properties.getProperty(material.name()).equals("true")){
					count++;
				}
			}
			commandSender.sendMessage("You have gotten " + count + " items");
			return true;
		}

		return false;
	}
}
