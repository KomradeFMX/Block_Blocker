package komrade.block_blocker;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class CommandHandler extends BukkitCommand implements CommandExecutor {
    private List<String> delayedPlayers = null;
    private int delay = 0;
    private final int minArguments;
    private final int maxArguments;
    private final boolean playerOnly;

    public CommandHandler(String command) {
        this(command, 0);
    }

    public CommandHandler(String command, boolean playerOnly) {
        this(command, 0, playerOnly);
    }

    public CommandHandler(String command, int requiredArguments) {
        this(command, requiredArguments, requiredArguments);
    }

    public CommandHandler(String command, int minArguments, int maxArguments) {
        this(command, minArguments, maxArguments, false);
    }

    public CommandHandler(String command, int requiredArguments, boolean playerOnly) {
        this(command, requiredArguments, requiredArguments, playerOnly);
    }

    public CommandHandler(String command, int minArguments, int maxArguments, boolean playerOnly) {
        super(command);

        this.minArguments = minArguments;
        this.maxArguments = maxArguments;
        this.playerOnly = playerOnly;

        CommandMap commandMap = getCommandMap();
        if(commandMap != null) {
            commandMap.register(command, this);
        }
    }

    public CommandMap getCommandMap() {
        try {
            if(Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);

                return (CommandMap) field.get(Bukkit.getPluginManager());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CommandHandler enableDelay(int delay) {
        this.delay = delay;
        this.delayedPlayers = new ArrayList<>();

        return this;
    }

    public void removePlay(Player player) {
        this.delayedPlayers.remove(player.getName());
    }

    public void sendUsage(CommandSender sender) {
        Msg.send(sender, getUsage());
    }

    public boolean execute(CommandSender sender, String alias, String[] arguments) {
        if(arguments.length < minArguments || arguments.length > maxArguments && maxArguments != -1) {
            sendUsage(sender);
            Bukkit.getLogger().info("Poto !args length");
            return true;
        }

        if(playerOnly && !(sender instanceof Player)) {
            Msg.send(sender, "&cUse this command as a player in-game!");
            return true;
        }

        String permission = getPermission();
        if(permission != null && !sender.hasPermission(permission)) {
            Msg.send(sender, "&cYou are not allowed to use this command!");
            return true;
        }

        if(delayedPlayers != null && sender instanceof  Player) {
            Player player = (Player) sender;
            if(delayedPlayers.contains(player.getName())) {
                Msg.send(sender, "&cPlease wait before using the command again.");
                return true;
            }
            delayedPlayers.add(player.getName());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Block_Blocker.getInstance(), () -> {
                delayedPlayers.remove(player.getName());
            }, 20L + delay);
        }

        if(!onCommand(sender, arguments)) {
            sendUsage(sender);
            Bukkit.getLogger().info("Poto !onCommand");
            return true;
        }

        return true;
    }

    public boolean onCommand(CommandSender sender, Command command, String alias, String [] arguments) {
        return this.onCommand(sender, arguments);
    }

    public abstract boolean onCommand(CommandSender sender, String [] arguments);

    public abstract  String getUsage();
}
