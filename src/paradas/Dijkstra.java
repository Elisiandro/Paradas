package paradas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Dijkstra<T> {

    private Set<Vertice<T>> naoVisitados;
    private Map<Vertice<T>, Vertice<T>> anteriores;
    private Map<Vertice<T>, Double> custos;
    private Stack<Vertice<T>> caminhoMaisCurto;
    private double custoTotal;

    private void init() {
        this.naoVisitados = new HashSet<Vertice<T>>();
        this.anteriores = new HashMap<Vertice<T>, Vertice<T>>();
        this.custos = new HashMap<Vertice<T>, Double>();
        this.caminhoMaisCurto = new Stack<Vertice<T>>();
        this.custoTotal = 0;
    }

    public Stack<Vertice<T>> calculaCaminhoMaisCurto(Vertice<T> verticeInicial, Vertice<T> verticeFinal) {

        this.init();

        if (verticeInicial == null || verticeFinal == null) {
            return null;
        } else if (verticeInicial.equals(verticeFinal)) {
            this.caminhoMaisCurto.add(verticeFinal);
        } else {
            this.naoVisitados.add(verticeInicial);
            this.custos.put(verticeInicial, 0.0);

            while (this.naoVisitados.size() > 0) {
                Vertice<T> vertice = getMinimo(this.naoVisitados);

                this.naoVisitados.remove(vertice);
                procuraMenorCusto(vertice);
            }

            if (this.anteriores.get(verticeFinal) == null) {
                return null;
            }

            Vertice<T> ultimoVertice = verticeFinal;
            this.caminhoMaisCurto.push(ultimoVertice);

            while (this.anteriores.get(ultimoVertice) != null) {
                this.custoTotal += ultimoVertice.getPeso(this.anteriores.get(ultimoVertice));

                ultimoVertice = this.anteriores.get(ultimoVertice);
                this.caminhoMaisCurto.push(ultimoVertice);
            }
        }

        return this.caminhoMaisCurto;
    }

    private Vertice<T> getMinimo(Set<Vertice<T>> vertice) {
        Vertice<T> minimo = null;

        for (Vertice<T> Vertice : vertice) {
            if (minimo == null || (getMenorPeso(Vertice) < getMenorPeso(minimo))) {
                minimo = Vertice;
            }
        }

        return minimo;
    }

    private double getMenorPeso(Vertice<T> vertice) {
        Double distancia = this.custos.get(vertice);
        return (distancia == null ? Double.POSITIVE_INFINITY : distancia);
    }

    private void procuraMenorCusto(Vertice<T> Vertice) {
        List<Vertice<T>> VerticesAdjacentes = Vertice.getCorners();

        for (Vertice<T> destino : VerticesAdjacentes) {
            double peso = Vertice.getPeso(destino);

            if (getMenorPeso(destino) > (getMenorPeso(Vertice) + peso)) {
                this.custos.put(destino, getMenorPeso(Vertice) + peso);
                this.anteriores.put(destino, Vertice);
                this.naoVisitados.add(destino);
            }
        }
    }

    public double getCustoTotal() {
        return this.custoTotal;
    }

    public Stack<Vertice<T>> getCaminhoMaisCurto() {
        return this.caminhoMaisCurto;
    }
}
