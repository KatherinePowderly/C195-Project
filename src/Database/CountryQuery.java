package Database;

import Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class contains SQL operations made on the Country Collection.*/
public class CountryQuery {

    /** This method gets a list of Country Objects
     * @return ObservableList List containing Country Objects
     * @throws SQLException Catches SQLException and prints stacktrace.
     */
    public static ObservableList<Country> getCountries() throws SQLException {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM countries;";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                Country newCountry = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );

                countries.add(newCountry);
            }
            return countries;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /** This method gets a Country Object by Country Name
     * @param country String value of Country Name
     * @return Country Country Object
     * @throws SQLException Catches SQLException and prints stacktrace.
     */
    public static Country getCountryId(String country) throws SQLException {

        String queryStatement = "SELECT * FROM countries WHERE Country=?";

        DBQuery.setPreparedStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, country);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Country newCountry = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                return newCountry;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
