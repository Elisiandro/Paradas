/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paradas;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bica
 */
public class Inicio {

    static List<Coordenadas> listaCoordenada;
    static List<Linhas> listaLinha;
    static List<Paradas> listaParada;
    static List<LinhaParada> listaLinhaParada;

    static List<Coordenadas> _listaCoordenada;
    static List<Linhas> _listaLinha;
    static List<Paradas> _listaParada;
    static List<LinhaParada> _listaLinhaParada;

    static Inicio obj;

    public static void main(String[] args) {

        //Os Arquivos estao na raiz do projeto        
        String caminhoLinhas = "linhas.csv";
        obj = new Inicio();
        obj.runLinhas(caminhoLinhas);

        String caminhoParadas = "paradas.csv";
        obj = new Inicio();
        obj.runParadas(caminhoParadas);

        String caminhoLinhasParada = "paradalinha.csv";
        obj = new Inicio();
        obj.runLinhasParadas(caminhoLinhasParada);

        String caminhoCoordenadas = "coordenadas.csv";
        obj = new Inicio();
        obj.runCoordenadas(caminhoCoordenadas);

        int paradaInicial = 883;
        int paradaFinal = 889;

        //Lista as paradas e monta os vertices
        Grafo grafo = new Grafo();
        for (Paradas parada : listaParada) {
            double lat = Double.parseDouble(parada.getLatitude().substring(0, 10).replace(",", "."));
            double lon = Double.parseDouble(parada.getLongitude().substring(0, 10).replace(",", "."));
            grafo.adicionaVertices(new Point2D.Double(lat, lon));
        }

        //Filtra as linhas que passa nas paradas INICIAL e FINAL que o usuario escolheu
        _listaLinhaParada = new ArrayList<>();
        for (LinhaParada lp : listaLinhaParada) {
            if (paradaFinal == lp.getIdParada() || paradaInicial == lp.getIdParada()) {
                _listaLinhaParada.add(lp);
            }
        }

        //Filtra todas as coordenadas da linha de onibus que o usuario escolheu.
        int linhaEscolhida = 128036;
        _listaCoordenada = new ArrayList<>();
        for (Coordenadas coordenada : listaCoordenada) {
            if (linhaEscolhida == coordenada.getIdLinha()) {
                double lat = Double.parseDouble(coordenada.getLatitude().substring(0, 10).replace(",", "."));
                double lon = Double.parseDouble(coordenada.getLongitude().substring(0, 10).replace(",", "."));
                _listaCoordenada.add(coordenada);
            }
        }

        System.out.println("Fim");
    }

    public void criaExecutaHtml() {
        
        String key = "AIzaSyBVfPTBCkT4nZkhPSrY7eEizc1Gv0BaPTw";
        String arquivo = "";
        arquivo += "<!DOCTYPE html>";
        arquivo += "<html>";
        arquivo += "<head>";
        arquivo += "<style type='text/css'>";
        arquivo += "html, body { height: 100%; margin: 0; padding: 0; }";
        arquivo += "#map { height: 100%; }";
        arquivo += "</style>";
        arquivo += "</head>";
        arquivo += "<body>";
        arquivo += "<div id='map'></div>";
        arquivo += "<script type='text/javascript'>";
        arquivo += "var map;";
        arquivo += "var sevilla = new google.maps.LatLng(37.377222, -5.986944);";
        arquivo += "var buenos_aires = new google.maps.LatLng(-34.608333, -58.371944);";
        arquivo += "var distancia = google.maps.geometry.spherical.computeDistanceBetween(sevilla, buenos_aires);";
        arquivo += "function initMap() {";
        arquivo += "map = new google.maps.Map(document.getElementById('map'), {";
        arquivo += "center: {lat: -34.397, lng: 150.644},";
        arquivo += "zoom: 8";
        arquivo += "});";
        arquivo += "}";
        arquivo += "</script>";
        arquivo += "<script async defer";
        arquivo += "src='https://maps.googleapis.com/maps/api/js?key="+key+"&callback=initMap'>";
        arquivo += "</script>";
        arquivo += "</body>";
        arquivo += "</html>";

    }

    public void runLinhas(String arquivoCSV) {

        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";
        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            br.readLine();
            listaLinha = new ArrayList();
            while ((linha = br.readLine()) != null) {

                String[] obj = linha.split(csvDivisor);

                double id = Double.parseDouble(obj[0]);
                String nome = obj[1];
                String codigo = obj[2];
                String tipo = obj[3];

                listaLinha.add(new Linhas(id, nome, codigo, tipo));

            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void runParadas(String arquivoCSV) {

        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";
        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            br.readLine();
            listaParada = new ArrayList();
            while ((linha = br.readLine()) != null) {

                String[] obj = linha.split(csvDivisor);

                double id = Double.parseDouble(obj[0]);
                String codigo = obj[1];
                String longitude = obj[2];
                String latitude = obj[3];
                String terminal = obj[4];

                listaParada.add(new Paradas(id, codigo, latitude, longitude, terminal));

            }
            br.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    public void runLinhasParadas(String arquivoCSV) {

        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";
        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            br.readLine();
            listaLinhaParada = new ArrayList();
            while ((linha = br.readLine()) != null) {

                String[] obj = linha.split(csvDivisor);

                int idLinha = Integer.parseInt(obj[0]);
                int idParada = Integer.parseInt(obj[1]);

                listaLinhaParada.add(new LinhaParada(idLinha, idParada));

            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void runCoordenadas(String arquivoCSV) {

        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ";";
        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            br.readLine();
            listaCoordenada = new ArrayList();
            while ((linha = br.readLine()) != null) {

                String[] obj = linha.split(csvDivisor);

                double id = Double.parseDouble(obj[0]);
                String latitude = obj[1];
                String longitude = obj[2];
                double idLinha = Double.parseDouble(obj[3]);

                listaCoordenada.add(new Coordenadas(id, latitude, longitude, idLinha));

            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
