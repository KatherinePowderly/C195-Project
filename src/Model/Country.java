package Model;

/**This class is an object constructor used to create Country objects and get/set their values.*/
public class Country {

    private int countryId;
    private String country;

    /** @param countryId Int value of Country ID
     * @param country String value of Country
     */
    public Country (int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /** Gets Country ID
     * @return countryID Integer value of Country ID*/
    public int getCountryId() {
        return countryId;
    }

    /** Sets Country ID
     * @param countryId String value of Country ID*/
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** Gets Country
     * @return country String value of Country*/
    public String getCountry() {
        return country;
    }

    /** Sets Country
     * @param country String value of Country*/
    public void setCountry(String country) {
        this.country = country;
    }
}
