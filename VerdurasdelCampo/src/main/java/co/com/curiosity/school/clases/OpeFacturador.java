package co.com.curiosity.school.clases;

import co.com.curiosity.school.reglasnegocio.RNFacturador;

public class OpeFacturador {

    private int codigo;
    private double vlrUnitario;
    private double cant;
    private double vlrDcto;
    private double vlrPagar;
    private double subTotal;
    private String error;
    private String[] productos;

    public  OpeFacturador(){
        codigo = 0;
        vlrUnitario = 0;
        cant = 0;
        vlrDcto = -1;
        vlrPagar = 0;
        subTotal = 0;
        error = "";
    }

    public double getVlrDcto() {
        return vlrDcto;
    }

    public double getVlrPagar() {
        return vlrPagar;
    }

    public double getSubTotal() {
        return subTotal;
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

    public void setVlrUnitario(double vlrUnitario) {
        this.vlrUnitario = vlrUnitario;
    }

    public void setCant(double cant) {
        this.cant = cant;
    }

    private boolean validar(){

        if(codigo <= 0){
            error = "Producto inexistente";
            return false;
        }
        if(vlrUnitario <= 0){
            error = "Debe ingresar un valor unitario superior a cero";
            return  false;
        }
        if(cant <= 0){
            error = "La cantidad debe ser superior a cero";
            return false;
        }
        return  true;
    }

    public boolean procesar() throws Exception{
        try{

            if(!validar()){
                return false;
            }

            RNFacturador facturador = new RNFacturador();

            facturador.setCodigo(codigo);

            if(!facturador.consultarDto()){
                error = facturador.getError();
                return false;
            }

            subTotal = cant * vlrUnitario;
            vlrDcto = subTotal * facturador.getPorcDscto();
            vlrPagar = subTotal - vlrDcto;

            return true;

        }
        catch (Exception ex){
            throw ex;
        }
    }

    public boolean consultarPtosOpe() throws Exception{
        try{

            RNFacturador facturador = new RNFacturador();

            if(!facturador.consultarPtos()){
                error = facturador.getError();
                return  false;
            }
            productos = facturador.getProductos();
            return true;
        }
        catch (Exception ex){
            throw ex;
        }
    }


}
