import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task{
    private int id;
    private String description;
    private StatusType status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private static final DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

     public Task(){
    }

    public Task (int id, String description, StatusType status, String createdAt, Status updatedAt){
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusType getStatus() { 
        return status; 
    }
    public void setStatus(StatusType status) {
        this.status = status; 
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.format(myFormatObj);
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt.format(myFormatObj);
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}