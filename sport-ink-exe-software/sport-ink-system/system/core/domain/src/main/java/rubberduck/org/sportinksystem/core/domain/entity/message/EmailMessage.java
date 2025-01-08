package rubberduck.org.sportinksystem.core.domain.entity.message;

import java.util.Map;

public class EmailMessage {
    private String email;
    private String name;
    private String subject;
    private String template;
    private Map<String, Object> variables;

    public EmailMessage() {
    }

    public EmailMessage(String email, String name, String subject, String template, Map<String, Object> variables) {
        this.email = email;
        this.name = name;
        this.subject = subject;
        this.template = template;
        this.variables = variables;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
