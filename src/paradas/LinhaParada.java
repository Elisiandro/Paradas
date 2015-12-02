
package paradas;

/**
 *
 * @author Elisiandro
 */
public class LinhaParada {
    private int idLinha;
    private int idParada;

    public LinhaParada(int idLinha, int idParada) {
        this.idLinha = idLinha;
        this.idParada = idParada;
    }
    
    
    public int getIdLinha() {
        return idLinha;
    }

    public void setIdLinha(int idLinha) {
        this.idLinha = idLinha;
    }

    public int getIdParada() {
        return idParada;
    }

    public void setIdParada(int idParada) {
        this.idParada = idParada;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.idLinha;
        hash = 37 * hash + this.idParada;
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
        final LinhaParada other = (LinhaParada) obj;
        if (this.idLinha != other.idLinha) {
            return false;
        }
        if (this.idParada != other.idParada) {
            return false;
        }
        return true;
    }

   
    

    @Override
    public String toString() {
        return "LinhaParada{" + "idLinha=" + idLinha + ", idParada=" + idParada + '}';
    }


    
    
}
