package net.sootmc.staffvsplayers;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public final class StaffvsPlayers extends JavaPlugin {
    private static StaffvsPlayers instance;
    public static DatabaseConnector database;
    public static List<String> staff = new ArrayList<>();

    @Override
    public void onEnable() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        instance = this;

        saveDefaultConfig();

        try {
            database = new DatabaseConnector(
                    this.getConfig().getString("sql.address"),
                    this.getConfig().getString("sql.database"),
                    this.getConfig().getString("sql.username"),
                    this.getConfig().getString("sql.password"),
                    this.getConfig().getInt("sql.port")
            );

            getLogger().info("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
            getLogger().warning("Unable to connect to database, disabling!");
            getServer().getPluginManager().disablePlugin(this);
        }

        getCommand("add").setExecutor(new CommandHandler());
        getCommand("getdeaths").setExecutor(new CommandHandler());

        getServer().getPluginManager().registerEvents(new DeathListener(), instance);

        getLogger().info("Plugin has started!");
        getLogger().info("Made by Jamie!");
    }

    @Override
    public void onDisable() {
        try {
            database.Close();
            getLogger().info("Database connection closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        getLogger().info("Plugin has disabled!");
    }

    public static StaffvsPlayers getInstance() {
        return instance;
    }
}
