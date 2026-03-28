package alimzyGatePass.exceptions;

public class ResidentDoNotExistException extends RuntimeException{
    public ResidentDoNotExistException(String residentDoesNotExist) {
        super(residentDoesNotExist);
    }
}
