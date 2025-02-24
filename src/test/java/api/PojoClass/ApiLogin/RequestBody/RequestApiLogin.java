package api.PojoClass.ApiLogin.RequestBody;

public class RequestApiLogin {
    public String email;
    public String password;

    public RequestApiLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RequestApiLogin(){
        this("eve.holt@reqres.in","cityslicka");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
