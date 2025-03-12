package model;
import java.time.Instant;
import java.util.List;

public class Group {
    private String id;
    private String name;
    private List<String> members;
    private Instant createdAt;
    private Instant updatedAt;

    public Group() {}

    public Group(String id, String name, List<String> members, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getMembers() { return members; }
    public void setMembers(List<String> members) { this.members = members; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}

