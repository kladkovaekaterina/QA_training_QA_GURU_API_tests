package models.pojo;

public class UpdateUserResponseModel {

    String name, job, updatedAt;

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

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}