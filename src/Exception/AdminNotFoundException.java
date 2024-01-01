package Exception;

public class AdminNotFoundException extends Exception {

    public AdminNotFoundException(){
        super("Admin not Founnd");
    }
    public AdminNotFoundException(String message) {
        super(message);
    }
}
