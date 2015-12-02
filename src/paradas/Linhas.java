package paradas;

/**
 *
 * @author Elisiandro
 */
public class Linhas {
    private double id;
    private String nome;
    private String codigo;
    private String tipo;    

    public Linhas(double id, String nome, String codigo, String tipo) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.tipo = tipo;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.id) ^ (Double.doubleToLongBits(this.id) >>> 32));
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
        final Linhas other = (Linhas) obj;
        if (Double.doubleToLongBits(this.id) != Double.doubleToLongBits(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Linhas{" + "id=" + id + ", nome=" + nome + ", codigo=" + codigo + ", tipo=" + tipo + '}';
    }
    
    
    
}
