package edu.byu.cs.tweeter.model.net.response;

public class RegisterResponse extends Response {
    public RegisterResponse(String message) { super(false, message); }
    public RegisterResponse() { super(true, null); }

}
