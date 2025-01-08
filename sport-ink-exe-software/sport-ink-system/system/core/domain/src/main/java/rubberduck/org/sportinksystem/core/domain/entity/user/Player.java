package rubberduck.org.sportinksystem.core.domain.entity.user;

import rubberduck.org.sportinksystem.core.domain.valueObject.*;

public class Player extends User {
    private Phone phoneNumber;
    private String address;
    private String city;
    private String country;
    private String profilePicture;
    private String coverPicture;
    private String bio;
    private String preferredSport;

    /**
     * This method creates a player profile
     *
     * @return Player
     */
    public static Player createPlayerProfile(User user, Phone phoneNumber, String address, String city, String country, String profilePicture, String coverPicture, String bio, String preferredSport) {
        if (!user.getRoles().isEmpty()) {
            throw new IllegalStateException("User must be guest to create a player profile");
        }
        Player player = new Player(user.getEmail(), user.getUsername(), user.getFullName(), user.getPassword(), phoneNumber, address, city, country, profilePicture, coverPicture, bio, preferredSport);
        player.getRoles().add(Role.PLAYER);
        return player;
    }

    // Constructors
    public Player(Email email, Username username, FullName fullName, String password) {
        super(email, username, fullName, password);
    }

    protected Player(Email email, Username username, FullName fullName, String password, Phone phoneNumber, String address, String city, String country, String profilePicture, String coverPicture, String bio, String preferredSport) {
        super(email, username, fullName, password);
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.profilePicture = profilePicture;
        this.coverPicture = coverPicture;
        this.bio = bio;
        this.preferredSport = preferredSport;
    }

    // Getters and Setters
    public Phone getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Phone phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPreferredSport() {
        return preferredSport;
    }

    public void setPreferredSport(String preferredSport) {
        this.preferredSport = preferredSport;
    }
}
