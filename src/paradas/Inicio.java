/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paradas;

import java.awt.Desktop;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.Stack;

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

    static String latInicial;
    static String lonInicial;
    static String latFinal;
    static String lonFinal;
    
    static Dijkstra<Double> dijk;
    
    static Inicio obj;
    static FileWriter arq;

    public static void main(String[] args) throws IOException {
        
        Scanner acan = new Scanner(System.in);
        StringBuilder strBuider = new StringBuilder();
        
        strBuider.append(String.format("#########################################%n"));
        strBuider.append(String.format("#              DATA POA                 #%n"));
        strBuider.append(String.format("#      Insira os dados correntamente    #%n"));
        strBuider.append(String.format("#########################################%n"));
        
        System.out.println(strBuider.toString());
        System.out.print("Digite a parada inicial: ");
        int paradaInicial = acan.nextInt(); //833
        
        System.out.print("Digite a parada Final: ");
        int paradaFinal = acan.nextInt();//218
        
        //Os Arquivos estao na raiz do projeto        
        String caminhoLinhas = "linhas.csv";
        obj = new Inicio();
        obj.runLinhas(caminhoLinhas);

        String caminhoParadas = "paradas.csv";        
        obj.runParadas(caminhoParadas);

        String caminhoLinhasParada = "paradalinha.csv";        
        obj.runLinhasParadas(caminhoLinhasParada);

        String caminhoCoordenadas = "coordenadas.csv";        
        obj.runCoordenadas(caminhoCoordenadas);

               
        

        //Lista as paradas e monta os vertices
        Grafo grafo = new Grafo();
        for (Paradas parada : listaParada) {
            double lat = Double.parseDouble(parada.getLatitude().substring(0, 10).replace(",", "."));
            double lon = Double.parseDouble(parada.getLongitude().substring(0, 10).replace(",", "."));
            //grafo.adicionaVertices(new Point2D.Double(lat, lon));
            
            if (paradaInicial == parada.getId())               
            {
                latInicial = parada.getLatitude();
                lonInicial = parada.getLongitude();                
                System.err.println("Id Parada Inicial: " + parada.getId());
                System.err.println("Parada Inicial Latitude: " + latInicial);
                System.err.println("Parada Inicial Longitude: " + lonInicial);
            }            
            if (paradaFinal == parada.getId())               
            {
                latFinal = parada.getLatitude();
                lonFinal = parada.getLongitude();
                System.err.println("Id Parada Final: " + parada.getId());
                System.err.println("Parada Final Latitude: " + latFinal);
                System.err.println("Parada Final Longitude: " + lonFinal);
            }            
        }
        
        
        
        //Filtra as linhas que passa nas paradas INICIAL e FINAL que o usuario escolheu
        _listaLinhaParada = new ArrayList<>();
        for (LinhaParada lp : listaLinhaParada) {
            if (paradaFinal == lp.getIdParada() || paradaInicial == lp.getIdParada()) {                
                _listaLinhaParada.add(lp);
            }
        }
        
        //Filtra todas as coordenadas da linha de onibus que o usuario escolheu.
        //int linhaEscolhida = 128036;
        strBuider = new StringBuilder();        
        strBuider.append(String.format("#########################################%n"));        
        strBuider.append(String.format("LINHAS:                                  %n"));        
        String xxx= "";        
        for (LinhaParada linhaParada : _listaLinhaParada) {
            if (xxx.isEmpty())
                xxx += linhaParada.getIdLinha();
            else
                xxx += ";"+linhaParada.getIdLinha(); 
        }        
        
        strBuider.append(xxx+"\n");        
        strBuider.append(String.format("#########################################%n"));
        
        System.out.print(strBuider.toString());
        System.out.print("Escolha a Linha de onibus acima: ");
        int linhaEscolhida = acan.nextInt();
        
        _listaCoordenada = new ArrayList<>();
        Collections.sort(listaCoordenada);
        int count = 0;
        for (Coordenadas coordenada : listaCoordenada) {
            if (linhaEscolhida == coordenada.getIdLinha()) {
                double lat = Double.parseDouble(coordenada.getLatitude().substring(0, 10).replace(",", "."));
                double lon = Double.parseDouble(coordenada.getLongitude().substring(0, 10).replace(",", "."));
                int idCoordenada = (int)coordenada.getId();
                _listaCoordenada.add(coordenada);                
                //grafo.adicionaVertices(new Point2D.Double(lat, lon));
                //grafo.adicionaVertices(idCoordenada);
                grafo.adicionaVertices(count);
                count++;
                //grafo.adicionaArestas(  ,  );
            }
        }
        int nTot = count;
        count = 0;
        for (Coordenadas coordenada : _listaCoordenada) {
            double lat = Double.parseDouble(coordenada.getLatitude().substring(0, 10).replace(",", "."));
            double lon = Double.parseDouble(coordenada.getLongitude().substring(0, 10).replace(",", "."));                
            
            if (count==0)
                grafo.adicionaArestas(0, 0, 0.0);
            else
            {                
                Coordenadas coo = _listaCoordenada.get(count-1);                
                double firstLatToRad = Math.toRadians(Double.parseDouble(coordenada.getLatitude().substring(0,10).replace(",", ".")) );//firstLatitude);
                double secondLatToRad = Math.toRadians(Double.parseDouble(coo.getLatitude().substring(0,10).replace(",", ".")));//secondLatitude);
                double deltaLongitudeInRad = Math.toRadians(Double.parseDouble(coo.getLongitude().substring(0,10).replace(",", "."))- Double.parseDouble(coordenada.getLongitude().substring(0,10).replace(",", ".")));//secondLongitude- firstLongitude);
                double distancia = (Math.acos(((Math.cos(firstLatToRad) * Math.cos(secondLatToRad) * Math.cos(deltaLongitudeInRad)) + (Math.sin(firstLatToRad) * Math.sin(secondLatToRad)))) * 6378.137);
                System.out.println("Linha: "+ coo.getIdLinha() + " Aresta: ["+ (count-1) +" a "+ count +"] ditancia: " + distancia);                
                //grafo.adicionaArestas((int)coordenada.getId(), (int)coo.getId(), distancia);
                grafo.adicionaArestas(count-1, count, distancia);
            }            
            count++;
        }
        
        System.err.println(nTot);
        
        
        dijk = new Dijkstra<Double>();
        Stack<Vertice<Double>> path = dijk.calculaCaminhoMaisCurto(grafo.getVertices(0), grafo.getVertices(nTot-1));

        if (path == null) {
            System.out.println("Caminho inexistente!");
        } else {
            StringBuilder strBldr = new StringBuilder();

            while (!path.isEmpty()) {
                strBldr.append(path.pop().getIndex());
                strBldr.append(", ");
            }

            strBldr.delete(strBldr.lastIndexOf(", "), strBldr.length());

            System.out.print("\nRota mais curta (pontos percorridos): ");
            System.out.print(strBldr.toString());
            System.out.print("\nDistancia percorrida (em metros): ");
            System.out.println(dijk.getCustoTotal());
            System.out.println();
        }

        System.out.println("Fim");

        obj.criaExecutaHtml(latInicial, lonInicial, latFinal, lonFinal);

    }

    public void criaExecutaHtml(String latitudeInicial, String longitudeInicial, String latitudeFinal, String longitudeFinal) {

        String key = "AIzaSyBVfPTBCkT4nZkhPSrY7eEizc1Gv0BaPTw";
        String arquivo = "";

        arquivo += "<!DOCTYPE html>";
        arquivo += "<html>";
        arquivo += "  <head>";
        arquivo += "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">";
        arquivo += "    <meta charset=\"utf-8\">";
        arquivo += "    <title>Travel modes in directions</title>";
        arquivo += "    <style>";
        arquivo += "      html, body {";
        arquivo += "        height: 100%;";
        arquivo += "        margin: 0;";
        arquivo += "        padding: 0;";
        arquivo += "      }";
        arquivo += "      #map {";
        arquivo += "        height: 100%;";
        arquivo += "      }";
        arquivo += "      table, td, th {";
        arquivo += "          border: 1px solid black;";
        arquivo += "      }";
        arquivo += "#floating-panel {";
        arquivo += "  position: absolute;";
        arquivo += "  top: 10px;";
        arquivo += "  left: 25%;";
        arquivo += "  z-index: 5;";
        arquivo += "  background-color: #fff;";
        arquivo += "  padding: 5px;";
        arquivo += "  border: 1px solid #999;";
        arquivo += "  text-align: center;";
        arquivo += "  font-family: 'Roboto','sans-serif';";
        arquivo += "  line-height: 30px;";
        arquivo += "  padding-left: 10px;";
        arquivo += "}";
        arquivo += "";
        arquivo += "    </style>";
        arquivo += "  </head>";
        arquivo += "  <body>";
        
        arquivo += "<table>";
        //arquivo += "<table style=\"width:100%\">";
        arquivo += "  <tr>";
        arquivo += "    <td>Parada</td>";
        arquivo += "    <td>Linha de Onibus</td> ";
        arquivo += "    <td>Distancia</td>";
        arquivo += "  </tr>";
        arquivo += "  <tr>";
        arquivo += "    <td>xxx</td>";
        arquivo += "    <td>xxx</td> ";
        arquivo += "    <td>xxx</td>";
        arquivo += "  </tr>";
        arquivo += "</table>";        
        arquivo += "    <div id=\"floating-panel\">";
        arquivo += "    <b>Opcao: </b>";
        arquivo += "    <select id=\"mode\">";
        arquivo += "      <option value=\"TRANSIT\">Onibus</option>";
        //arquivo +="      <option value=\"DRIVING\">Driving</option>";
        arquivo += "      <option value=\"WALKING\">Mais Rapido</option>";
        //arquivo +="      <option value=\"BICYCLING\">Bicycling</option>";
        arquivo += "    </select>";
        arquivo += "    </div>";
        arquivo += "    <div id=\"map\"></div>";
        arquivo += "    <script>";
        arquivo += "function initMap() {";
        arquivo += "  var directionsDisplay = new google.maps.DirectionsRenderer;";
        arquivo += "  var directionsService = new google.maps.DirectionsService;";
        arquivo += "  var map = new google.maps.Map(document.getElementById('map'), {";
        arquivo += "    zoom: 4,";
        arquivo += "    center: {lat: 37.77, lng: -122.447}";
        arquivo += "  });";
        arquivo += "  directionsDisplay.setMap(map);";
        arquivo += "";
        arquivo += "  calculateAndDisplayRoute(directionsService, directionsDisplay);";
        arquivo += "  document.getElementById('mode').addEventListener('change', function() {";
        arquivo += "    calculateAndDisplayRoute(directionsService, directionsDisplay);";
        arquivo += "  });";
        arquivo += "}";
        arquivo += "function calculateAndDisplayRoute(directionsService, directionsDisplay) {";
        arquivo += "  var selectedMode = document.getElementById('mode').value;";
        arquivo += "  directionsService.route({";
        arquivo += "    origin: {lat: " + latitudeInicial.substring(0, 10).replace(",", ".") + " , lng: " + longitudeInicial.substring(0, 10).replace(",", ".") + "},  ";
        arquivo += "    destination: {lat: " + latitudeFinal.substring(0, 10).replace(",", ".") + "  , lng: " + longitudeFinal.substring(0, 10).replace(",", ".") + " },      ";
        arquivo += "    travelMode: google.maps.TravelMode[selectedMode]";
        arquivo += "  }, function(response, status) {";
        arquivo += "    if (status == google.maps.DirectionsStatus.OK) {";
        arquivo += "      directionsDisplay.setDirections(response);";
        arquivo += "    } else {";
        arquivo += "      window.alert('Directions request failed due to ' + status);";
        arquivo += "    }";
        arquivo += "  });";
        arquivo += "}";
        arquivo += "    </script>";
        arquivo += "    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyBVfPTBCkT4nZkhPSrY7eEizc1Gv0BaPTw&callback=initMap\" async defer></script>";
        arquivo += "  </body>";
        arquivo += "</html> ";

        FileWriter fileW;

        try {
            File file = new File("mapa.html");
            fileW = new FileWriter(file);
            fileW.write(arquivo);
            fileW.close();

            Desktop desk = Desktop.getDesktop();
            if (desk.isSupported(Desktop.Action.OPEN)) {
                desk.open(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
