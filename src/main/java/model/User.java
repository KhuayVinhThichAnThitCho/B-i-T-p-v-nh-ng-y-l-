package model;

public class User {
    private int id;
    private String username;
    private String email;
    private String passwordHash;

    public User() {}

    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }

    public String getEmail() { return email; }
    public void setEmail(String e) { this.email = e; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String p) { this.passwordHash = p; }
}
