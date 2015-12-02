package paradas;

import java.awt.geom.Point2D;

public class Aresta {

    private Vertice verticeInicial, verticeFinal;
    private double peso;

    public Aresta(Vertice verticeInicial, Vertice verticeFinal) {
        this.setVerticeInicial(verticeInicial);
        this.setVerticeFinal(verticeFinal);
    }

    public Aresta(Vertice verticeInicial, Vertice verticeFinal, double peso) {
        this.setVerticeInicial(verticeInicial);
        this.setVerticeFinal(verticeFinal);
        this.setPeso(peso);
    }

    public Vertice getVerticeInicial() {
        return verticeInicial;
    }

    public void setVerticeInicial(Vertice verticeInicial) {
        this.verticeInicial = verticeInicial;

        if (isVerticesPointType()) {
            calculaDistancia();
        }
    }

    public Vertice getVerticeFinal() {
        return verticeFinal;
    }

    public void setVerticeFinal(Vertice verticeFinal) {
        this.verticeFinal = verticeFinal;

        if (isVerticesPointType()) {
            calculaDistancia();
        }
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (!isVerticesPointType()) {
            this.peso = peso;
        } else {
            calculaDistancia();
        }
    }

    private double calculaDistancia() {
        Point2D.Double valorInicial;
        Point2D.Double valorFinal;

        valorInicial = (Point2D.Double) this.verticeInicial.getValor();
        valorFinal = (Point2D.Double) this.verticeFinal.getValor();

        double distancia = Math.sqrt((Math.pow(
                (valorInicial.getX() - valorFinal.getX()), 2))
                + (Math.pow(
                        (valorInicial.getY() - valorFinal.getY()), 2))
        );

        this.peso = distancia;

        return distancia;
    }

    private boolean isVerticesPointType() {
        return this.verticeInicial != null
                && this.verticeFinal != null
                && this.verticeInicial.getValor() instanceof Point2D.Double
                && this.verticeFinal.getValor() instanceof Point2D.Double;
    }
}
