package me.hecto.minecraftitemcollection;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


public final class MinecraftItemCollection extends JavaPlugin implements Listener {

    public final Properties properties = new Properties();

    public final String propFileMsg = "Item Properties";



    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Custom Plugin has started");
        getServer().getPluginManager().registerEvents(this,this);
        ItemsLeft guiLeft = new ItemsLeft();
        ItemsObtained guiObtained = new ItemsObtained();
        ItemIgnore ignore = new ItemIgnore();
        AddItem addItem = new AddItem();
        RemoveItem removeItem = new RemoveItem();
        ClearItems clearItems = new ClearItems();
        ItemScore itemScore = new ItemScore();


        Objects.requireNonNull(this.getCommand(addItem.cmd)).setExecutor(addItem);
        Objects.requireNonNull(this.getCommand(removeItem.cmd)).setExecutor(removeItem);
        Objects.requireNonNull(this.getCommand(clearItems.cmd)).setExecutor(clearItems);
        Objects.requireNonNull(this.getCommand(itemScore.cmd)).setExecutor(itemScore);
        Objects.requireNonNull(this.getCommand(guiLeft.cmd)).setExecutor(guiLeft);
        Objects.requireNonNull(this.getCommand(guiObtained.cmd)).setExecutor(guiObtained);
        Objects.requireNonNull(this.getCommand(ignore.cmd)).setExecutor(ignore);




        try {
            FileReader reader = new FileReader("items.properties");
            properties.load(reader);
        } catch (IOException e) {
            for(Material material: Material.values()) {
                if (!material.name().contains("AIR=") | material.name().contains("STAIR")){
                properties.setProperty(material.toString(), "false");}
            }
            try {
                properties.store(new FileWriter("items.properties"), propFileMsg);

            } catch (IOException ex) {ex.printStackTrace();}

        }

    }

    public void propertyCheck() {
        try {
            FileReader reader = new FileReader("items.properties");
            properties.load(reader);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    @EventHandler
    public void onItemPickUp(EntityPickupItemEvent event) throws IOException {
        propertyCheck();
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Material material = event.getItem().getItemStack().getType();
        if (properties.get(material.name()).equals("false")){
            setTrue(material);
            getServer().broadcastMessage(ChatColor.GREEN + "A " + material.name() + " has been collected");
        }
    }


    @EventHandler
    public void onCloseInv(InventoryCloseEvent event) throws IOException {
        propertyCheck();
        for (ItemStack itemStack: event.getPlayer().getInventory().getContents()){
            if (itemStack == null){
                continue;
            }
            Material material = itemStack.getType();
            if (properties.get(material.name()).equals("false")){
                setTrue(material);
                getServer().broadcastMessage(ChatColor.GREEN + "A " + material.name() + " has been collected");
            }
        }
    }


    public void setTrue(Material material) throws IOException {
        properties.setProperty(material.name(), "true");
        properties.store(new FileWriter("items.properties"), propFileMsg);
    }


}
