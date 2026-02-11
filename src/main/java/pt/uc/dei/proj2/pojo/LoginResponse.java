package pt.uc.dei.proj2.pojo;

public class LoginResponse {
    public boolean success;
    public String message;
    public long userId;

    public LoginResponse() {} // required by some serializers

    public LoginResponse(boolean success, String message, long userId) {
        this.success = success;
        this.message = message;
        this.userId = userId;
    }
}