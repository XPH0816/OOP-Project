package util;

import java.util.ArrayList;

import interfaces.Searchable;
import model.Invoice;

public class InvoiceList extends ArrayList<Invoice> implements Searchable<InvoiceList> {
    @Override
    public InvoiceList searchByUserID(int id) {
        InvoiceList result = new InvoiceList();
        for (Invoice invoice : this) {
            if (invoice.getUser().getID() == id) {
                result.add(invoice);
            }
        }
        return result;
    }

    public Invoice searchByID(int id) {
        for (Invoice invoice : this) {
            if (invoice.getID() == id) {
                return invoice;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if (this.size() == 0)
            return String.format("No invoice");
        String result = "";
        result += String.format("=============================================") + "\n";
        result += String.format(" InvoiceID %10s %22s", "UserID","User") + "\n";
        result += String.format("=============================================") + "\n";
        for (Invoice invoice : this) {
            result += String.format(" %9d %10s %22s", invoice.getID(), invoice.getUser().getID(),
                    invoice.getUser().getName()) + "\n";
        }
        result += String.format("=============================================") + "\n";
        return result;
    }
}
