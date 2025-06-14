package errors;

import alex.UnidadLexica;
import constructorAst.Main;

public class GestionErroresExp {
   public void errorLexico(int fila, int columna, String lexema) {
     System.out.println("ERROR LÉXICO en la fila "+fila+" columna "+columna+": Caracter inesperado: "+lexema); 
     Main.errLexSint = true;
     //System.exit(1);
   }  
   public void errorSintactico(UnidadLexica unidadLexica) {
     if (unidadLexica.lexema() != null) {
       System.out.println("ERROR SINTÁCTICO en la fila "+unidadLexica.fila()+" columna "+unidadLexica.columna()+": Elemento inesperado \""+unidadLexica.lexema()+"\"");
       Main.errLexSint = true;
     } else {
       System.out.println("ERROR SINTÁCTICO en la fila "+unidadLexica.fila()+" columna "+unidadLexica.columna()+": Elemento inesperado");
       Main.errLexSint = true;
     }
     //System.exit(1);
   }
}
