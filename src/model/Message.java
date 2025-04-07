package model;

import java.time.Instant;
import java.util.List;

public class Message {
    private String id;
    private String sender;
    private String content;
    private String conversation;
    private List<String> readBy;
    private List<String> attachments;
    private Instant createdAt;
    private Instant updatedAt;

    public Message() {}

    public Message(String id, String sender, String content, String conversation, List<String> readBy, List<String> attachments, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.conversation = conversation;
        this.readBy = readBy;
        this.attachments = attachments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getConversation() { return conversation; }
    public void setConversation(String conversation) { this.conversation = conversation; }

    @Override
	public String toString() {
		return "Message [id=" + id + ", sender=" + sender + ", content=" + content + ", conversation=" + conversation
				+ ", readBy=" + readBy + ", attachments=" + attachments + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

	public List<String> getReadBy() { return readBy; }
    public void setReadBy(List<String> readBy) { this.readBy = readBy; }

    public List<String> getAttachments() { return attachments; }
    public void setAttachments(List<String> attachments) { this.attachments = attachments; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}

