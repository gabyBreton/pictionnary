package projet.pictionnary.breton.model;

import java.io.Serializable;

/**
 *
 * @author Gabriel Breton - 43397
 */
public class DataTable implements Serializable {

    private final String name;
    private final int id;
    private final String open;
    private final String drawerName;
    private final String partnerName;

    public DataTable(String name, int id, String open, String drawerName,
                     String partnerName) {
        this.name = name;
        this.id = id;
        this.open = open;
        this.drawerName = drawerName;
        this.partnerName = partnerName;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String isOpen() {
        return open;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public String getPartnerName() {
        return partnerName;
    }
}
