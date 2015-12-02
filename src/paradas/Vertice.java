package paradas;

import java.util.ArrayList;

public class Vertice<T> {

    public static int ID = 0;

    private ArrayList<Aresta> arestas;
    private T valor;
    private int index;

    public Vertice(T vertice) {
        this.index = Vertice.ID++;
        this.valor = vertice;

        arestas = new ArrayList<Aresta>();
    }

    public ArrayList<Aresta> getArestas() {
        return arestas;
    }

    public void adicionaArestas(Aresta arestas) {
        this.arestas.add(arestas);
    }

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public int getSize() {
        return this.arestas.size();
    }

    public int getIndex() {
        return this.index;
    }

    public ArrayList<Vertice<T>> getCorners() {
        ArrayList<Vertice<T>> adjs = new ArrayList<Vertice<T>>();

        for (Aresta aresta : this.getArestas()) {
            if (!this.equals(aresta.getVerticeInicial())) {
                adjs.add(aresta.getVerticeInicial());
            } else {
                adjs.add(aresta.getVerticeFinal());
            }
        }

        return adjs;
    }

    public Double getPeso(Vertice<T> vertice) {
        Double peso = Double.POSITIVE_INFINITY;

        for (Aresta aresta : this.getArestas()) {
            if (aresta.getVerticeInicial().equals(vertice) || aresta.getVerticeFinal().equals(vertice)) {
                peso = aresta.getPeso();
            }
        }

        return peso;
    }
}
