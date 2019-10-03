

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
//import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import javax.swing.*;
import java.awt.*;

public class main extends javax.swing.JFrame{

     private LDLC list = new LDLC();
     private Nodo actual = null;
     private reproductor player;
     private Short x = 0;
     private DefaultListModel lista_modelo = new DefaultListModel();
     private String ultimaLista = "vacio";
     private boolean cambios = false;
     protected boolean detenido = false;

     private JFrame frame ;

     private JPanel panel ; // pa q se usaría?

     private JLabel labelvar ; //label que espero varie según la canción a elegir

     private JButton botonInsertar ;
     private JButton botonEliminar ;
     private JButton botonSalir ;

     private JButton botonAnt ;
     private JButton botonSig ;
     private JButton botonPlayPause ;
     private JButton botonDetener ;

     private JLabel nombre ; //este cambiará según el nombre del archivo


     public main(){

          setTitle("Reproductor de musica mp3");
          setResizable(false);
          gui();
          setLocationRelativeTo(null);


          lista_can.addMouseListener(new MouseAdapter() {
               public void mouseClicked(MouseEvent evt) {
                    JList lista = (JList) evt.getSource();
                    if (evt.getClickCount() == 2) {
                         int index = lista.locationToIndex(evt.getPoint());
                         if (index != -1) {
                              actual = list.get_cancion(index);
                              x = 0;
                              playActionPerformed(null);
                         }
                    }
               }
          });

          try {
               BufferedReader tec = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\config"));
               String aux = tec.readLine();
               if (aux.equals("Si")) {
                    aux = tec.readLine();
                    if (!aux.equals("vacio")) {
                         cargarLista(aux);
                    }
               } else {
                    cargarListaInicio.setSelected(false);
               }
          } catch (Exception e) {
          }

          addWindowListener(new java.awt.event.WindowAdapter() {
               @Override
               public void windowClosing(java.awt.event.WindowEvent evt) {
                    if (!list.empty() && cambios) {
                         int opcion = JOptionPane.showConfirmDialog(null, "¿Guardar cambios?");
                         if (opcion == JOptionPane.YES_OPTION) {
                              if (ultimaLista.equals("vacio")) {
                                   ultimaLista = crearArchivoLista();
                              }
                              if (ultimaLista == null) {
                                   ultimaLista = "vacio";
                              } else {
                                   guardarLista(ultimaLista);
                              }
                         }
                    }
                    try {
                         BufferedWriter bw = new BufferedWriter(
                                 new FileWriter(System.getProperty("user.dir") + "\\config"));
                         if (cargarListaInicio.isSelected()) {
                              bw.write("Si\r\n");
                              bw.write(ultimaLista + "\r\n");
                         } else {
                              bw.write("No\r\n");
                         }
                         bw.close();
                    } catch (Exception e) {
                    }
                    System.exit(0);
               }
          });
          player = new reproductor(this);
     }

     public void cargarLista(String ruta) {
          try {
               FileInputStream fis = new FileInputStream(new File(ruta));
               BufferedReader tec = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
               String input[];
               tec.readLine();

               while (tec.ready()) {
                    input = tec.readLine().split("<");
                    System.out.println(input[0] + " , " + input[1]);
                    list.insertar(input[0], input[1]);
                    lista_modelo.addElement(input[0]);
               }
               ultimaLista = ruta;
               cambios = false;
          } catch (FileNotFoundException ex) {
               JOptionPane.showMessageDialog(null, "Ha ocurrido un error\nal cargar la lista!!!", "alerta", 1);
          } catch (IOException ex) {
               JOptionPane.showMessageDialog(null, "Ha ocurrido un error!!!", "alerta", 1);
          }
          lista_can.setModel(lista_modelo);
     }

     public void guardarLista(String dir) {
          try {
               BufferedWriter tec = new BufferedWriter(new FileWriter(dir));
               tec.write("\r\n");

               Nodo aux = list.inicio;
               while (aux != null) {
                    tec.append(aux.nombre + "<" + aux.direccion + "\r\n");
                    aux = aux.sig;
               }

               tec.close();
               cambios = false;
          } catch (Exception e) {
          }
     }

     public String crearArchivoLista() {
          String n = JOptionPane.showInputDialog("digite el nombre de la lista");
          if (n == null || n.isEmpty()) {
               return null;
          }

          JFileChooser chooser = new JFileChooser();
          chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
          int seleccion = chooser.showOpenDialog(this);
          File ruta;

          if (seleccion == JFileChooser.APPROVE_OPTION) {
               ruta = chooser.getSelectedFile();
          } else {
               return null;
          }
          File save = new File(ruta.getAbsolutePath() + "\\" + n + ".lis");
          if (save.exists()) {
               save.delete();
          }
          return save.getAbsolutePath();
     }

     }

     public void gui(){

          frame = new JFrame("Reproductor MP3");
          frame.setVisible(true);
          frame.setSize( 500, 500);
          frame.setResizable(true);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setLayout(null);

          /** BOTONES DE MI MENÚ **/
          botonInsertar = new JButton(" INSERTAR ");
          botonInsertar.setBounds(00, 00, 120, 30);
          frame.add(botonInsertar);

          botonEliminar = new JButton("ELIMINAR");
          botonEliminar.setBounds(00, 31, 120, 30);
          frame.add(botonEliminar);

          botonSalir = new JButton("SALIR");
          botonSalir.setBounds(00, 62, 120, 30);
          frame.add(botonSalir);

          /** BOTONES DE MI REPRODUCTOR **/
          botonAnt = new JButton(" ⧏ ");
          botonAnt.setBounds(100, 400, 80, 40);
          frame.add(botonAnt);

          botonSig = new JButton(" ⧐ ");
          botonSig.setBounds(400, 400, 80, 40);
          frame.add(botonSig);

          botonPlayPause = new JButton(" ▮▮ ▶ ");
          botonPlayPause.setBounds(300, 400, 80, 40);
          frame.add(botonPlayPause);

          botonDetener = new JButton(" ◾ ");
          botonDetener.setBounds(200, 400, 80, 40);
          frame.add(botonDetener);

     }

     private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setFileFilter(new FileNameExtensionFilter("Archivo MP3", "mp3", "mp3"));
          fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
          fileChooser.setMultiSelectionEnabled(true);
          int seleccion = fileChooser.showOpenDialog(this);

          if (seleccion == JFileChooser.APPROVE_OPTION) {
               File files[] = fileChooser.getSelectedFiles();
               boolean noMp3 = false, repetidos = false;
               cambios = true;

               for (File file : files) {
                    String name = file.getName();
                    if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".mp3")) {
                         noMp3 = true;
                         continue;
                    }
                    if (list.buscar(file.getName(), file.getPath())) {
                         repetidos = true;
                         continue;
                    }
                    list.insertar(file.getName(), file.getPath());
                    System.out.println(file.getName());
                    System.out.println(file.getPath());
                    lista_modelo.addElement(file.getName());
                    lista_can.setModel(lista_modelo);
               }
               if (noMp3) {
                    JOptionPane.showMessageDialog(null, "Se encontro archivo(s) no mp3", "alerta", 0);
               }
               if (repetidos) {
                    JOptionPane.showMessageDialog(null, "Se encontraron repetidos", "alerta", 0);
               }
          }
     }

     private void playActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playActionPerformed
          detenido = true;
          if (list.empty()) {
               JOptionPane.showMessageDialog(null, "no hay canciones", "alerta", 1);
          } else {
               if (actual == null) {
                    actual = list.inicio;
               }
               try {
                    if (x == 0) {
                         player.control.open(new URL("file:///" + actual.direccion));
                         player.control.play();
                         System.out.println("se inicia");
                         nombre_can.setText(actual.nombre);
                         jSlider1.setEnabled(true);
                         jSlider2.setEnabled(true);
                         x = 1;
                         play.setIcon(new ImageIcon(getClass().getResource("/iconos/pausa.png")));
                    } else {
                         if (x == 1) {
                              player.control.pause();
                              System.out.println("se pausa!!!");
                              x = 2;
                              play.setIcon(new ImageIcon(getClass().getResource("/iconos/play.png")));
                         } else {
                              player.control.resume();
                              System.out.println("se continua!!!");
                              x = 1;
                              play.setIcon(new ImageIcon(getClass().getResource("/iconos/pausa.png")));
                         }
                    }
               } catch (BasicPlayerException ex) {
                    JOptionPane.showMessageDialog(null, "error al abrir\nla cancion!!!", "alerta", 1);
                    x = 0;
               } catch (MalformedURLException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "error al abrir la direccion\nde la cancion!!!", "alerta", 1);
                    x = 0;
               }
          }
          detenido = false;
     }

     private void detenerActionPerformed(java.awt.event.ActionEvent evt) {
          detenido = true;
          play.setIcon(new ImageIcon(getClass().getResource("/iconos/play.png")));
          try {
               player.control.stop();
               x = 0;
               jSlider1.setEnabled(false);
               jSlider2.setEnabled(false);
          } catch (BasicPlayerException ex) {
               Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
          }
          detenido = false;
     }

     protected void eventoSiguiente(){
          siguienteActionPerformed(null);
     }

     private void anteriorActionPerformed(java.awt.event.ActionEvent evt) {
          if (actual == null) {
               return;
          }

          switch (tipo_reproduccion.getSelectedIndex()) {
               case 0:
                    if (actual.ant == null) {
                         return;
                    }
                    actual = actual.ant;
                    break;

               case 1:
                    if (actual.sig == null) {
                         return;
                    }
                    actual = actual.sig;
                    break;

               default:
                    int index = (int) (Math.random() * list.size);
                    actual = list.get_cancion(index);
                    break;
          }

          x = 0;
          playActionPerformed(evt);
     }

     private void siguienteActionPerformed(java.awt.event.ActionEvent evt) {
          if (actual == null) {
               return;
          }

          switch (tipo_reproduccion.getSelectedIndex()) {
               case 0:
                    if (actual.sig == null) {
                         return;
                    }
                    actual = actual.sig;
                    break;

               case 1:
                    if (actual.ant == null) {
                         return;
                    }
                    actual = actual.ant;
                    break;

               default:
                    int index = (int) (Math.random() * list.size);
                    actual = list.get_cancion(index);
                    break;
          }

          x = 0;
          playActionPerformed(evt);
     }//GEN-LAST:event_siguienteActionPerformed

     private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
          if (list.empty()) {
               return;
          }
          int q = list.index(actual);
          if (q == -1) {
               JOptionPane.showMessageDialog(null, "ha ocurrido un\nerror inesperado!!!", "alerta", 1);
          } else {
               lista_modelo.remove(q);
               list.borrar(actual);
               detenerActionPerformed(evt);
               if (list.empty()) {
                    actual = null;
                    nombre_can.setText("...");
               } else {
                    if (list.size == 1) {
                         actual = list.inicio;
                    } else {
                         if (actual.sig == null) {
                              actual = actual.ant;
                         } else {
                              actual = actual.sig;
                         }
                    }
                    nombre_can.setText(actual.nombre);
               }
          }
          cambios = true;
     }//GEN-LAST:event_eliminarActionPerformed

     private void cargar_listaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargar_listaActionPerformed
          JFileChooser chooser = new JFileChooser();
          chooser.setFileFilter(new FileNameExtensionFilter("archivo lis", "lis"));
          int seleccion = chooser.showOpenDialog(this);

          if (seleccion == JFileChooser.APPROVE_OPTION) {
               detenerActionPerformed(evt);
               //list.clear();
               lista_modelo.clear();
               actual = list.inicio;

               String name = chooser.getSelectedFile().getName();
               if (name.length() < 4 || !name.substring(name.length() - 4, name.length()).equalsIgnoreCase(".lis")) {
                    JOptionPane.showMessageDialog(null, "no es una lista", "alerta", 0);
                    return;
               }
               cargarLista(chooser.getSelectedFile().getPath());
          }
     }//GEN-LAST:event_cargar_listaActionPerformed

     private void guardar_listaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_listaActionPerformed
          if (list.empty()) {
               JOptionPane.showMessageDialog(null, "no hay canciones!!!", "alerta", 1);
               return;
          }
          guardarLista(crearArchivoLista());
     }


     public static void main(String[] args){
          java.awt.EventQueue.invokeLater(new Runnable() {
               public void run() {
                    new main().setVisible(true);
               }
          });
     }
}