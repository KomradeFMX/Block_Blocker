package komrade.block_blocker.events;

import komrade.block_blocker.Block_Blocker;
import komrade.block_blocker.Msg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEvents implements Listener {
    private final Block_Blocker plugin;
    public BlockEvents(Block_Blocker plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        FileConfiguration config = plugin.getConfig();

        if((boolean) config.get("status.block_placing")) {
            event.setCancelled(true);
            Msg.send(event.getPlayer(), "You are not allowed to place blocks.");
            // Agarrá la pala
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        FileConfiguration config = plugin.getConfig();

        if((boolean) config.get("status.block_breaking")) {
            event.setCancelled(true);
            Msg.send(event.getPlayer(), "You are not allowed to break blocks.");
            // Agarrá la pala
        }
    }
}