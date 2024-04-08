package models.pojo;

public class CreateUserResponseModel {

    String name, job, id, createdAt;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

}