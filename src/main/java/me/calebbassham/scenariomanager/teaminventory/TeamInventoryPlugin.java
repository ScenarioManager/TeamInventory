package me.calebbassham.scenariomanager.teaminventory;

import me.calebbassham.scenariomanager.api.ScenarioManagerInstance;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TeamInventoryPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        TeamInventory teamManager = new TeamInventory();

        ScenarioManagerInstance.getScenarioManager().register(teamManager, this);
        Bukkit.getPluginCommand("teaminventory").setExecutor(teamManager.cmd());
    }
}
