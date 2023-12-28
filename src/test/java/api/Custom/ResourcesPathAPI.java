package api.Custom;

public enum ResourcesPathAPI {
    CreateUsers("/api/users");

    private final String path;

    ResourcesPathAPI(String path){
        this.path = path;
    }

    @Override
    public String toString() {
        return this.path;
    }
}