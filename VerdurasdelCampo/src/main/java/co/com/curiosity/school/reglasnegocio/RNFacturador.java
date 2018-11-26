package co.com.curiosity.school.reglasnegocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class RNFacturador {

    private int codigo;
    private double porcDscto;
    private String error;
    private BufferedReader objbrReader;
    private String[] productos;

    public RNFacturador() {
        codigo = 0;
        porcDscto = -1;
        error = "";
    }

    public double getPorcDscto() {
        return porcDscto;
    }

    public String getError() {
        return error;
    }

    public String[] getProductos() {
        return productos;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    private boolean validar() {
        if (codigo <= 0) {
            error = "Producto inexistente";
            return false;
        }
        return true;
    }

    private Scanner leerPlano(String nombrePlano) throws IOException {
        try {
            File objFile = new File(System.getProperty("user.dir"));
            String path = objFile.getAbsolutePath() + "\\" + nombrePlano;

            File objFileDesc = new File(path);

            FileReader objfReader = new FileReader(objFileDesc);
            objbrReader = new BufferedReader(objfReader);

            Scanner leer = new Scanner(objFileDesc);

            int cantlineas = 0;
            cantlineas = Integer.parseInt(Long.toString(objbrReader.lines().count()));

            if (cantlineas == 0) {
                return null;
            }
            return leer;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public boolean consultarDto() throws Exception {
        try {

            if (!validar()) {
                return false;
            }

            Scanner plano = leerPlano("Descuentos.txt");

            if (plano == null) {
                porcDscto = 0;
                return true;
            }

            String linea;
            String[] vecLineas;
            while (plano.hasNextLine()) {
                linea = plano.nextLine();
                vecLineas = linea.split(":");

                if (codigo == Integer.parseInt(vecLineas[0])) {
                    porcDscto = Double.parseDouble(vecLineas[1]);
                    break;
                }
            }
            objbrReader.close();
            objbrReader = null;

            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }


    public boolean consultarPtos() throws Exception {
        try {

            Scanner plano = leerPlano("Productos.txt");

            if(plano == null){
                error = "El archivo de productos no tiene registros";
                return false;
            }

            String registros = "";
            while (plano.hasNextLine()){
                registros = registros + plano.nextLine() + ",";
            }

            productos = registros.split(",");
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
