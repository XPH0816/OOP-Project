package error;

public class NotAvaliable extends Exception {
    public NotAvaliable() {
        super("Not Avaliable");
    }

    public NotAvaliable(String message) {
        super(message);
    }
}