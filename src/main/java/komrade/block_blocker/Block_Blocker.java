package komrade.block_blocker;

import komrade.block_blocker.commands.BlockPlacement;
import komrade.block_blocker.events.BlockEvents;
import komrade.block_blocker.utils.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Block_Blocker extends JavaPlugin {
    private static Block_Blocker instance;
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getLogger().info("Block Blocker is being enabled, wait...");

        Bukkit.getLogger().info("Loading config system");
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new BlockEvents(this), this);

        Bukkit.getLogger().info("Loading commands");
        new BlockPlacement(this);

        Bukkit.getLogger().info("Done! Thanks for using me <3");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("Block Blocker is being disabled. Goodbye!");
    }

    public static Block_Blocker getInstance() {
        return instance;
    }
}
