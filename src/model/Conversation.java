package model;
import java.time.Instant;
import java.util.List;

public class Conversation {
    private String id;
    private List<String> participants;
    private boolean isGroup;
    private Instant createdAt;
    private Instant updatedAt;

    public Conversation() {
        this.isGroup = false;
    }

    public Conversation(String id, List<String> participants, boolean isGroup, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.participants = participants;
        this.isGroup = isGroup;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<String> getParticipants() { return participants; }
    public void setParticipants(List<String> participants) { this.participants = participants; }

    public boolean isGroup() { return isGroup; }
    public void setGroup(boolean isGroup) { this.isGroup = isGroup; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
