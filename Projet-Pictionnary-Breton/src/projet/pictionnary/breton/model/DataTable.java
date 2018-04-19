package projet.pictionnary.breton.model;

import java.io.Serializable;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class DataTable implements Serializable {

    private final String name;
    private final int id;
    private final String status;
    private final String drawer;
    private final String partner;

    public DataTable(String name, int id, String status, String drawer,
                     String partner) {
        this.name = name;
        this.id = id;
        this.status = status;
        this.drawer = drawer;
        this.partner = partner;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getDrawer() {
        return drawer;
    }

    public String getPartner() {
        return partner;
    }
}
