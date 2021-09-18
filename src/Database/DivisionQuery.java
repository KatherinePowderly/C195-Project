package Database;

import Model.Country;
import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionQuery {
    public static ObservableList<Division> getDivisions() throws SQLException {
        ObservableList<Division> divisions = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM first_level_divisions;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                Division newDivision = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("COUNTRY_ID")
                );

                divisions.add(newDivision);
            }
            return divisions;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Division getDivisionId(String division) throws SQLException {
        String queryStatement = "SELECT * FROM first_level_divisions WHERE Division=?";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, division);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Division newDivision = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("COUNTRY_ID")
                );
                return newDivision;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public static ObservableList<Division> getDivisionsByCountry(String country) throws SQLException {
        Country newCountry = CountryQuery.getCountryId(country);

        ObservableList<Division> divisions = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM first_level_divisions WHERE COUNTRY_ID=?;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setInt(1, newCountry.getCountryId());

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                Division newDivision = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("COUNTRY_ID")
                );

                divisions.add(newDivision);
            }
            return divisions;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
