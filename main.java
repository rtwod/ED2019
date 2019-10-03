import javax.swing.*;
import java.awt.*;

public class main{

     private JFrame frame ;
     private JPanel panel ;

     private JButton botonInsertar ;
     private JButton botonEliminar ;
     private JButton botonSalir ;

     private JButton botonAnt ;
     private JButton botonSig ;
     private JButton botonPlayPause ;
     private JButton botonDetener ;

     private JLabel nombre ; //este cambiará según el nombre del archivo


     public main(){
          gui();
     }

     public void gui(){

          frame = new JFrame("Reproductor MP3");
          frame.setVisible(true);
          frame.setSize( 500, 500);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setLayout(null);

          botonInsertar = new JButton("INSERTAR");
          boton.setBounds(20, 90, 60, 20);
          frame.add(boton);


     }

     public static void main(String[] args){
          new main();
     }

}