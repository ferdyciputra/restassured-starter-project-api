package api.Custom;

public enum ResourcesPathAPI {
    CreateUsers("/api/users"),
    Login("/api/login");

    private final String path;

    ResourcesPathAPI(String path){
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}