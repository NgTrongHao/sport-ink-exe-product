package rubberduck.org.sportinksystem.core.domain.valueObject;

import java.util.regex.Pattern;

public class Phone {

    private static final Pattern PHONE_PATTERN = Pattern.compile("\\(\\+(\\d+)\\)\\s*([0-9\\-\\s]+)");

    private final Integer countryCode;

    private final String number;

    public Phone(Integer countryCode, String number) {
        this.countryCode = countryCode;
        this.number = number;
    }

    public static Phone fromString(String phone) {
        var matcher = PHONE_PATTERN.matcher(phone);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid phone format");
        }
        return new Phone(Integer.parseInt(matcher.group(1)), matcher.group(2));
    }

    public String phoneNumber() {
        return String.format("(+%d) %s", countryCode, number);
    }

}
