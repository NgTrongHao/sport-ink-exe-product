package rubberduck.org.sportinksystemalt.shared.service.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
public class EmailMessage {
    private String email;
    private String name;
    private String subject;
    private String template;
    private Map<String, Object> variables;

    public EmailMessage(String email, String name, String subject, String template, Map<String, Object> variables) {
        this.email = email;
        this.name = name;
        this.subject = subject;
        this.template = template;
        this.variables = variables;
    }

}

