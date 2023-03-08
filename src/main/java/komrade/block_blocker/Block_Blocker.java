package komrade.block_blocker;

import komrade.block_blocker.commands.BlockCommand;
import komrade.block_blocker.events.BlockEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;

public final class Block_Blocker extends JavaPlugin {
    private static Block_Blocker instance;
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Block Blocker] " + ChatColor.WHITE + "Enabling! Please wait...");

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Block Blocker] " + ChatColor.WHITE + "Loading config");
        saveDefaultConfig();

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Block Blocker] " + ChatColor.WHITE + "Loading events");
        Bukkit.getPluginManager().registerEvents(new BlockEvents(this), this);

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Block Blocker] " + ChatColor.WHITE + "Loading commands");
        new BlockCommand(this);

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "[Block Blocker] " + ChatColor.WHITE + "Done! Thanks for trusting me -w-");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info( Color.fromRGB(195, 99, 255) + "Block Blocker " + Color.WHITE + " is being disabled. Goodbye!");
    }

    public static Block_Blocker getInstance() {
        return instance;
    }
}
