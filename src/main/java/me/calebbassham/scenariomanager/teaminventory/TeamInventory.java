package me.calebbassham.scenariomanager.teaminventory;

import me.calebbassham.scenariomanager.api.SimpleScenario;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class TeamInventory extends SimpleScenario {

    private HashMap<Team, Inventory> inventories;
    private CommandExecutor cmd = new Cmd();

    @Override
    public void onScenarioStart() {
        inventories = new HashMap<>();
    }

    @Override
    public void onScenarioStop() {
        inventories = null;
    }

    public CommandExecutor cmd() {
        return cmd;
    }

    private class Cmd implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!isEnabled()) {
                sendMessage(sender, "This command cannot be used right now because the scenario is not enabled.");
                return true;
            }

            if (!(sender instanceof Player)) {
                sendMessage(sender, "Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;

            if (!scenarioManager.getGamePlayerProvider().isGamePlayer(player)) {
                sendMessage(sender, "You must be playing the game to use this command.");
                return true;
            }

            Team team = scenarioManager.getTeamProvider().getPlayerTeam(player);
            if (team == null) {
                sendMessage(sender, "You are not on a team.");
                return true;
            }

            Inventory inv;
            if (inventories.containsKey(team)) {
                inv = inventories.get(team);
            } else {
                inv = Bukkit.createInventory(null, 54, getInventoryName(team));
                inventories.put(team, inv);
            }

            player.openInventory(inv);
            return true;
        }
    }

    private String getInventoryName(Team team) {
        return ChatColor.translateAlternateColorCodes('&', "&aTeam Inventory");
    }
}
