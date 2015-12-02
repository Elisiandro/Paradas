package paradas;

/**
 *
 * @author Elisiandro
 */
public class Paradas {
    private double id;
    private String codigo;
    private String latitude;
    private String longitude;
    private String terminal;

    public Paradas(double id, String codigo, String latitude, String longitude, String terminal) {
        this.id = id;
        this.codigo = codigo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.terminal = terminal;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.id) ^ (Double.doubleToLongBits(this.id) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paradas other = (Paradas) obj;
        if (Double.doubleToLongBits(this.id) != Double.doubleToLongBits(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paradas{" + "id=" + id + ", codigo=" + codigo + ", latitude=" + latitude + ", longitude=" + longitude + ", terminal=" + terminal + '}';
    }
    
    
    
}
