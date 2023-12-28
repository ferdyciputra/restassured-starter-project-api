package api.PojoClass.ApiCreateUsers.RequestBody;

public class RequestApiCreateUsers {
    public String name;
    public String job;

    public RequestApiCreateUsers(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public RequestApiCreateUsers(){
        this("Ferdy", "QA Engineer");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
