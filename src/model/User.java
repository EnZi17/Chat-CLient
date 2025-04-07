package model;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private boolean isOnline;
    private Instant lastOnline;
    private ArrayList<String> friends; 

    public User() {
        this.avatar = "";
        this.isOnline = false;
        this.lastOnline = null;
    }

    public User(String id, String username, String email, String password, String avatar, boolean isOnline, Instant lastOnline, ArrayList<String> friends) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.isOnline = isOnline;
        this.lastOnline = lastOnline;
        this.friends = friends;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public boolean isOnline() { return isOnline; }
    @Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", avatar="
				+ avatar + ", isOnline=" + isOnline + ", lastOnline=" + lastOnline + ", friends=" + friends + "]";
	}

	public void setOnline(boolean isOnline) { this.isOnline = isOnline; }

    public Instant getLastOnline() { return lastOnline; }
    public void setLastOnline(Instant lastOnline) { this.lastOnline = lastOnline; }

    public ArrayList<String> getFriends() { return friends; }
    public void setFriends(ArrayList<String> friends) { this.friends = friends; }
}

