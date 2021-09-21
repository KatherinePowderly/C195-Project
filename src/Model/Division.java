package Model;

/**This class is an object constructor used to create Division objects and get/set their values.*/
public class Division {
    private int divisionId;
    private String division;
    private int countryId;

    /** @param divisionId Int value of Division ID
     * @param division String value of Division Name
     * @param countryId Int value of Price
     */
    public Division (int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /** Gets Division ID
     * @return divisionId Integer value of Division ID*/
    public int getDivisionId() {
        return divisionId;
    }

    /** Sets Division ID
     * @param divisionId Integer value of Division ID*/
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** Gets Division
     * @return division String value of Division */
    public String getDivision() {
        return division;
    }

    /** Sets Division
     * @param division String value of Division*/
    public void setDivision(String division) {
        this.division = division;
    }

    /** Gets Country ID
     * @return countryId Integer value of Country ID*/
    public int getCountryId() {
        return countryId;
    }

    /** Sets Country ID
     * @param countryId Integer value of Country ID*/
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
}