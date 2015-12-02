package paradas;

import java.awt.geom.Point2D;

public class ArestaTest implements Comparable<ArestaTest>{

    String verticeA, verticeB;
    int peso;

    public ArestaTest(String verticeA, String verticeB, int peso)
    {
        this.verticeA = verticeA;
        this.verticeB = verticeB;
        this.peso = peso;
    }
    public String getVerticeA()
    {
        return verticeA;
    }
    public String getVerticeB()
    {
        return verticeB;
    }
    public int getPeso()
    {
        return peso;
    }

    @Override
    public int compareTo(ArestaTest t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
