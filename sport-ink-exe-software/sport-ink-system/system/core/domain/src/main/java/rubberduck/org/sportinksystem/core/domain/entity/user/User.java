package rubberduck.org.sportinksystem.core.domain.entity.user;

import rubberduck.org.sportinksystem.core.domain.entity.AbstractEntity;
import rubberduck.org.sportinksystem.core.domain.valueObject.Email;
import rubberduck.org.sportinksystem.core.domain.valueObject.FullName;
import rubberduck.org.sportinksystem.core.domain.valueObject.Role;
import rubberduck.org.sportinksystem.core.domain.valueObject.Username;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User extends AbstractEntity<UUID> {
    private Email email;
    private Username username;
    private FullName fullName;
    private String password;
    protected Set<Role> roles = new HashSet<>();
    private boolean enabled;
    private boolean verified;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * This method creates a new user
     *
     * @return User
     */
    public static User createNewUser(String email, String username, String firstName, String middleName, String lastName, String password) {
        return new User(
                new Email(email),
                new Username(username),
                new FullName(firstName, middleName, lastName),
                password
        );
    }

    /**
     * This method adds a role to the user
     */
    public void updateRole(Role role) {
        roles.add(role);
    }

    /**
     * This method removes a role from the user
     */
    public void removeRole(Role role) {
        roles.remove(role);
    }

    /**
     * This method checks if the user has a role
     *
     * @return boolean
     */
    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    // Constructors
    public User(Email email, Username username, FullName fullName, String password) {
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.enabled = true;
        this.verified = false;
    }

    public User(UUID id, String email, String username, FullName fullName, String password, boolean enabled, Set<Role> roles, boolean verified, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.email = new Email(email);
        this.username = new Username(username);
        this.fullName = fullName;
        this.password = password;
        this.enabled = enabled;
        this.roles = roles;
        this.verified = verified;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
