package me.hecto.minecraftitemcollection;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ItemsLeft implements CommandExecutor, Listener {

	public final String cmd = "itemsLeft";


	@Override
	public boolean onCommand(@NotNull CommandSender commandSender,
							 @NotNull Command command, @NotNull String label, String[] args) {
		ArrayList<String> falses = new ArrayList<>();
		Properties properties = new Properties();
		try {
			properties.load(new FileReader("items.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object obj : properties.keySet()) {
			String key = obj.toString();
			if (properties.getProperty(key).equals("false")) {
				falses.add(key);
			}
		}

		TextComponent component = new TextComponent("Unobtained Items");
		component.setUnderlined(true);
		component.setBold(true);
		component.setColor(ChatColor.AQUA);
		component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.join(", ", falses)));
		component.setHoverEvent(new HoverEvent((HoverEvent.Action.SHOW_TEXT),
				new Text("Copies a list of items that have not been collected")));

		commandSender.spigot().sendMessage(component);


		return true;
	}


}
