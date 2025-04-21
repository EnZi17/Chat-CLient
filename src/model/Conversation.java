package model;

import java.time.Instant;
import java.util.List;

public class Conversation {
    private String id;
    private List<String> participants;
    private boolean isGroup;
    private String name; // Thêm thuộc tính name
    private Instant createdAt;
    private Instant updatedAt;

    public Conversation() {
        this.isGroup = false;
    }

    public Conversation(String id, List<String> participants, boolean isGroup, String name, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.participants = participants;
        this.isGroup = isGroup;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<String> getParticipants() { return participants; }
    public void setParticipants(List<String> participants) { this.participants = participants; }

    public boolean isGroup() { return isGroup; }
    public void setGroup(boolean isGroup) { this.isGroup = isGroup; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Conversation [id=" + id + ", participants=" + participants + ", isGroup=" + isGroup + 
               ", name=" + name + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
