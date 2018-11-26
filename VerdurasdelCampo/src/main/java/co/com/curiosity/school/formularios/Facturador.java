package co.com.curiosity.school.formularios;

import co.com.curiosity.school.clases.OpeFacturador;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;

import javax.swing.*;
import java.awt.event.*;

public class Facturador extends JFrame {
    private JButton btnregistrar;
    private JComboBox cboProducto;
    private JTextField txtCantidad;
    private JLabel lblValorUnitario;
    private JLabel lblVlrDscto;
    private JLabel lblVlrPagra;
    private JButton btnTerminar;
    private JButton btnlimpiar;
    private JPanel pnlFactura;

    public Facturador(){
        LlenarCombo();
        setSize(400,300);
        setTitle("Verduras del Campo");
        add(pnlFactura);

        cboProducto.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                consultarPrecio(cboProducto.getSelectedIndex());
            }
        });
        btnregistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesar();
            }
        });
    }

    private void LlenarCombo(){
        try{
            OpeFacturador fact = new OpeFacturador();

            if(!fact.consultarPtosOpe()){
                JOptionPane.showMessageDialog(pnlFactura,fact.getError());
                return;
            }

            for(int i = 0; i < fact.getProductos().length; i++){
                this.cboProducto.addItem(fact.getProductos()[i]);
            }

            /*this.cboProducto.addItem("Seleccione");
            this.cboProducto.addItem("Aguacate");
            this.cboProducto.addItem("Zanahoria");
            this.cboProducto.addItem("Papa Nevada");
            this.cboProducto.addItem("Cebolla Huevo");
            this.cboProducto.addItem("Tomate Aliño");
            this.cboProducto.addItem("Pera");*/

        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(pnlFactura,ex.getMessage());
        }
    }

    private void consultarPrecio(int codigo){

        if(codigo > 0) {

            switch (codigo) {
                case 1:
                    this.lblValorUnitario.setText("$4.200");
                    break;
                case 2:
                    this.lblValorUnitario.setText("$2.300");
                    break;
                case 3:
                    this.lblValorUnitario.setText("$2.100");
                    break;
                case 4:
                    this.lblValorUnitario.setText("$2.800");
                    break;
                case 5:
                    this.lblValorUnitario.setText("$2.700");
                    break;
                case 6:
                    this.lblValorUnitario.setText("$5.500");
                    break;
                default:
                    this.lblValorUnitario.setText("$0");
            }
        }
        else{
            this.lblValorUnitario.setText("0");
        }
    }

    private boolean validar(){
        if(this.cboProducto.getSelectedIndex() <= 0){
            JOptionPane.showMessageDialog(pnlFactura,"Debe seleccionar un producto");
            return false;
        }
        if("".equals(this.txtCantidad.getText())){
            JOptionPane.showMessageDialog(pnlFactura,"Debe ingresar la cantidad");
            return false;
        }
        return true;
    }

    private void procesar(){
        try{

            if(!validar()){
                return;
            }

            int codigo = 0;
            double cant = 0;
            double vlrUnitario = 0;

            OpeFacturador facturador = new OpeFacturador();

            codigo = this.cboProducto.getSelectedIndex();
            vlrUnitario = Double.parseDouble(this.lblValorUnitario.getText().replace("$","").replace(".",""));
            cant = Double.parseDouble(this.txtCantidad.getText());

            facturador.setCodigo(codigo);
            facturador.setVlrUnitario(vlrUnitario);
            facturador.setCant(cant);

            if(!facturador.procesar()){
                JOptionPane.showMessageDialog(pnlFactura,facturador.getError());
                return;
            }

            this.lblVlrDscto.setText(Double.toString(facturador.getVlrDcto()));
            this.lblVlrPagra.setText(Double.toString(facturador.getVlrPagar()));

            JOptionPane.showMessageDialog(pnlFactura,"Gracias por su Compra");


        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(pnlFactura,ex.getMessage());
        }
    }

}
