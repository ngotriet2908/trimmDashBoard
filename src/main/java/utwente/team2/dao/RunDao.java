package utwente.team2.dao;

import utwente.team2.DatabaseInitialiser;
import utwente.team2.model.*;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum RunDao {

    instance;

    public User getUserTotalStats(String username, User user) {
        try {
            String query = "SELECT COUNT(*) AS total_runs, SUM(r.duration) AS total_time, SUM(r.distance) AS total_distance, SUM(r.steps) AS total_steps " +
                    "FROM run AS r " +
                    "WHERE r.username = ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            // should be only one row
            while (resultSet.next()) {
                user.setTotalRuns(resultSet.getInt("total_runs"));
                user.setTotalTime(resultSet.getInt("total_time"));
                user.setTotalDistance(resultSet.getInt("total_distance"));
                user.setTotalSteps(resultSet.getInt("total_steps"));
            }

            return user;
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public User getUserTotalStats(String username) {
        return getUserTotalStats(username, new User());
    }

    public List<Run> getUserRunsList(String username) {
        try {
            String query = "SELECT r.id, r.date, r.name, r.distance, r.duration, r.steps " +
                    "FROM run AS r " +
                    "WHERE r.username = ? " +
                    "ORDER BY r.date DESC";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            List<Run> runOverviewList = new ArrayList<>();

            // multiple rows
            while (resultSet.next()) {
                Run runOverviewModel = new Run();

                runOverviewModel.setId(resultSet.getInt("id"));
                runOverviewModel.setDate(resultSet.getDate("date"));
                runOverviewModel.setName(resultSet.getString("name"));
                runOverviewModel.setDistance(resultSet.getInt("distance"));
                runOverviewModel.setDuration(resultSet.getInt("duration"));
                runOverviewModel.setSteps(resultSet.getInt("steps"));

                runOverviewList.add(runOverviewModel);
            }

            return runOverviewList;

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public String getUsername(int runID) {
        try {
            String query = "SELECT r.username " +
                    "FROM run AS r " +
                    "WHERE r.id = ? " +
                    "ORDER BY r.date DESC";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runID);

            ResultSet resultSet = statement.executeQuery();


            // multiple rows
            if (resultSet.next()) {
                return resultSet.getString(1);
            }

            return null;

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public User getUserRunsList(String username, User user) {
        user.setRunsList(getUserRunsList(username));
        return user;
    }


    public String getLayout(int runId) {
        int current = getCurrentLayout(runId);

        try {
            String query = "SELECT l.layout " +
                    "FROM layout AS l " +
                    "WHERE l.run_id = ? " +
                    "AND l.lid = ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);
            statement.setInt(2, current);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return "{\"layout\": \"unavailable\"}";
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }


    public String getDefaultLayout(int runId) {
        try {
            String query = "SELECT r.default_layout " +
                    "FROM run AS r " +
                    "WHERE r.id = ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                return "{\"layout\": \"unavailable\"}";
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public LayoutOptions getLayoutName(int runId) {
        try {
            String query = "SELECT l.name, l.lid " +
                    "FROM layout AS l " +
                    "WHERE l.run_id = ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);

            ResultSet resultSet = statement.executeQuery();

            List<LayoutData> layoutData = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                int lid = resultSet.getInt(2);
                layoutData.add(new LayoutData(name, lid));
            }

            return new LayoutOptions(layoutData, getCurrentLayout(runId));
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public int getCurrentLayout(int runId) {
        try {
            String query = "SELECT r.current_layout " +
                    "FROM run AS r " +
                    "WHERE r.id = ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 1;
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return 1;
    }

    public BigDecimal getStepLength(int runId) {
        try {
            String query = "SELECT r.distance, r.steps " +
                    "FROM run AS r " +
                    "WHERE r.id = ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String distance = resultSet.getString(1);
                String steps = resultSet.getString(2);

                if (distance == null) {
                    return BigDecimal.valueOf(1.9);
                } else {
                    return BigDecimal.valueOf(Double.parseDouble(distance) / Double.parseDouble(steps));
                }
            } else {
                return BigDecimal.valueOf(1.88);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public boolean saveCurrentLayout(int runId, int current) {

        try {
            String query = "UPDATE run " +
                    " SET current_layout = ?" +
                    " WHERE id = ?";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);

            statement.setInt(1, current);
            statement.setInt(2, runId);

            int resultSet = statement.executeUpdate();

            return resultSet > 0;
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return false;
    }

    public boolean saveLayoutName(int runId, int layout_id, String name) {

        try {
            String query = "UPDATE layout " +
                    " SET name = ? " +
                    " WHERE lid = ? " +
                    "AND run_id = ?";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);

            statement.setString(1, name);
            statement.setInt(2, layout_id);
            statement.setInt(3, runId);

            int resultSet = statement.executeUpdate();

            return resultSet > 0;
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return false;
    }

    public boolean saveLayout(int runId, String layout) {

        int current = getCurrentLayout(runId);

        try {
            String query = "UPDATE layout " +
                    " SET layout = ?" +
                    " WHERE lid = ? " +
                    " AND run_id = ?";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);

            statement.setString(1, layout);
            statement.setInt(3, runId);
            statement.setInt(2, current);

            int resultSet = statement.executeUpdate();

            return resultSet > 0;
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return false;
    }

    public String getShoes(int runId) {
        try {
            String query = "SELECT s.brand || ' ' || s.model as shoes_name " +
                    "FROM run AS r, shoes AS s " +
                    "WHERE r.id =  ? " +
                    "AND r.shoes_id = s.id ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);
            ResultSet resultSet = statement.executeQuery();

            // multiple rows
            if (resultSet.next()) {
                String shoesName = resultSet.getString("shoes_name");
                if (shoesName != null) {
                    return shoesName;
                } else {
                    return "Mixed/No Shoes";
                }
            }

            return "Mixed/No Shoes";
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public Run getRun(int runId) {
        String shoesName = getShoes(runId);

        try {
            String query = "SELECT r.id, r.date, r.name, r.distance, r.duration, r.steps " +
                    "FROM run AS r " +
                    "WHERE r.id =  ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);
            ResultSet resultSet = statement.executeQuery();

            // multiple rows
            if (resultSet.next()) {
                Run runOverviewModel = new Run();

                runOverviewModel.setId(resultSet.getInt("id"));
                runOverviewModel.setDate(resultSet.getDate("date"));
                runOverviewModel.setName(resultSet.getString("name"));
                runOverviewModel.setDistance(resultSet.getInt("distance"));
                runOverviewModel.setDuration(resultSet.getInt("duration"));
                runOverviewModel.setSteps(resultSet.getInt("steps"));
                runOverviewModel.setShoes(shoesName);

                return runOverviewModel;
            }

            return null;

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public boolean isUsersRun(String username, int runId) {
        try {
            String query = "SELECT r.id " +
                    "FROM run AS r " +
                    "WHERE r.id = ? AND r.username = ?";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);
            statement.setString(2, username);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return false;
    }

    public Note getNote(int runId) {
        try {
            String query = "SELECT description " +
                    "FROM run " +
                    "WHERE id =  ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);
            statement.setInt(1, runId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Note("note", resultSet.getString(1), "note");
            }

            return new Note("note", "", "note");
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return null;
    }

    public boolean saveNote(int runId, String text) {
        try {
            String query = "UPDATE run " +
                    " SET description = ?" +
                    " WHERE id = ? ";

            PreparedStatement statement = DatabaseInitialiser.getCon().prepareStatement(query);

            statement.setString(1, text);
            statement.setInt(2, runId);

            int resultSet = statement.executeUpdate();

            return resultSet > 0;
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return false;
    }
}
