package komrade.block_blocker.commands;

import komrade.block_blocker.Block_Blocker;
import komrade.block_blocker.CommandHandler;
import komrade.block_blocker.Msg;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;


public class BlockPlacement {

    public BlockPlacement(Block_Blocker plugin) {
        new CommandHandler("block_blocker", 1, false) {

            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {

                FileConfiguration config = plugin.getConfig();

                if (arguments[0].equalsIgnoreCase("placing")) {
                    if((boolean)config.get("status.block_placing")) {
                        config.set("status.block_placing", false);
                        plugin.saveConfig();
                        Msg.send(sender, (String)config.get("messages.placing_enabled"));
                    } else {
                        config.set("status.block_placing", true);
                        plugin.saveConfig();
                        Msg.send(sender, (String)config.get("messages.placing_disabled"));
                    }
                } else if (arguments[0].equalsIgnoreCase("breaking")) {
                    if((boolean)config.get("status.block_breaking")) {
                        config.set("status.block_breaking", false);
                        plugin.saveConfig();
                        Msg.send(sender, (String)config.get("messages.breaking_enabled"));
                    } else {
                        config.set("status.block_breaking", true);
                        plugin.saveConfig();
                        Msg.send(sender, (String)config.get("messages.breaking_disabled"));
                    }
                } else if (arguments[0].equalsIgnoreCase("all")) {
                    if((boolean)config.get("status.block_breaking") && (boolean)config.get("status.block_placing")) {
                        config.set("status.block_breaking", false);
                        config.set("status.block_placing", false);
                        plugin.saveConfig();
                        Msg.send(sender, (String)config.get("messages.all_enabled"));
                    } else {
                        config.set("status.block_breaking", true);
                        config.set("status.block_placing", true);
                        plugin.saveConfig();
                        Msg.send(sender, (String)config.get("messages.all_disabled"));
                    }
                }

                return true;
            }

            @Override
            public String getUsage() {
                return "/block [placing | breaking | all] [player (optional)]";
            }
        };
    }
}