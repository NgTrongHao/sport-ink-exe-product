package rubberduck.org.sportinksystem.core.domain.valueObject;

public record FullName(String firstName, String middleName, String lastName) {

    public FullName {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
    }

    public String getFullName() {
        return middleName == null || middleName.isEmpty() ? String.format("%s %s", firstName, lastName) : String.format("%s %s %s", firstName, middleName, lastName);
    }

}
