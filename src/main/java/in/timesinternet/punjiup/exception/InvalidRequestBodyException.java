package in.timesinternet.punjiup.exception;
public class InvalidRequestBodyException extends  RuntimeException{
    public InvalidRequestBodyException(String message){
        super(message);
    }
}