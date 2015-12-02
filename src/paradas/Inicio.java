/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paradas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

        Grafo grafo = new Grafo();
        for (LinhaParada linhaParada : listaLinhaParada) {
            grafo.adicionaArestas(linhaParada.getIdLinha(), linhaParada.getIdParada());
        }
                
        for (Paradas parada : listaParada) {            
            grafo.adicionaVertices(new Vertice<>(parada));            
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
                
                listaParada.add(new Paradas(id, codigo, latitude , longitude, terminal));
                
            }

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
                
                listaCoordenada.add(new Coordenadas(id, latitude , longitude, idLinha));
                
            }

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
