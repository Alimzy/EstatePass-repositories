package alimzyGatePass.exceptions;

public class ResidentAlreadyExistsException extends RuntimeException{
    public ResidentAlreadyExistsException(String residentExistException) {
        super(residentExistException);
    }
}
