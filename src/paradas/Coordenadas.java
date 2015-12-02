package paradas;

/**
 *
 * @author Elisiandro
 */
public class Coordenadas {
    private double id;
    private String latitude;
    private String longitude;
    private double  idLinha;

    public Coordenadas(double id, String latitude, String longitude, double idLinha) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.idLinha = idLinha;
    }

    
    
    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
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

    public double getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(double idLinha) {
        this.idLinha = idLinha;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.id) ^ (Double.doubleToLongBits(this.id) >>> 32));
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
        final Coordenadas other = (Coordenadas) obj;
        if (Double.doubleToLongBits(this.id) != Double.doubleToLongBits(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coordenadas{" + "id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", idLinha=" + idLinha + '}';
    }
    
    
}
