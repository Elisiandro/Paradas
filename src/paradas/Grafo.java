package paradas;

import java.util.ArrayList;

public class Grafo<T> {

    private ArrayList<Vertice<T>> vertices;
    private ArrayList<Aresta> arestas;

    public Grafo() {
        this.vertices = new ArrayList<Vertice<T>>();
        this.arestas = new ArrayList<Aresta>();
    }

    public ArrayList<Vertice<T>> getVertices() {
        return this.vertices;
    }

    public Vertice<T> getVertices(int index) {
        if (this.vertices.size() - 1 < index) {
            return null;
        }

        return this.vertices.get(index);
    }

    public void adicionaVertices(T vertice) {
        this.vertices.add(new Vertice<T>(vertice));
    }

    public ArrayList<Aresta> getArestas() {
        return this.arestas;
    }

    public void adicionaArestas(int indexVerticeInicial, int indexVerticeFinal, double custo) {
        Vertice<T> inicio = getVertices(indexVerticeInicial);
        Vertice<T> fim = getVertices(indexVerticeFinal);

        Aresta aresta = new Aresta(inicio, fim, custo);

        inicio.adicionaArestas(aresta);
        fim.adicionaArestas(aresta);

        this.arestas.add(aresta);
    }

    public void adicionaArestas(int indexVerticeInicial, int indexVerticeFinal) {
        this.adicionaArestas(indexVerticeInicial, indexVerticeFinal, 0);
    }

}
