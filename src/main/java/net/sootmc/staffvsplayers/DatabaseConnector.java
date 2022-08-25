package net.sootmc.staffvsplayers;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseConnector {
    private Connection connection;
    private String killed;
    private String killer;

    public DatabaseConnector(String address, String database, String user, String password, Integer port) throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:mariadb://" + address + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false",
                user,
                password
        );
    }

    public List<Pair<String, String>> getKillers() throws SQLException {
        String formattedStmt = String.format("SELECT StaffvsPlayers.killerName, StaffvsPlayers.staffName FROM StaffvsPlayers where StaffvsPlayers.staffname in (%s)",
                StaffvsPlayers.staff.stream()
                        .map(v -> "?")
                        .collect(Collectors.joining(", ")));

        PreparedStatement stmt = this.connection.prepareStatement(formattedStmt);

        for (int i = 0; i < StaffvsPlayers.staff.size(); i++) {
            String username = StaffvsPlayers.staff.get(i);
            stmt.setString(i + 1, username);
        }
        
        stmt.execute();

        ResultSet result = stmt.getResultSet();
        List<Pair<String, String>> results = new ArrayList<>();

        while (result.next()) {
            killed = result.getString("staffName");
            killer = result.getString("killerName");

            results.add(
                    new Pair<>(killed, killer)
            );
        }

        return results;
    }

    public void setKiller(String killed, String killer) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement("INSERT INTO StaffvsPlayers (killerName, staffName) VALUES (?, ?)");

            stmt.setString(1, killer);
            stmt.setString(2, killed);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Close() throws SQLException {
        this.connection.close();
    }
}
