package alex;

import constructorAst.ClaseLexica;
import constructorAst.Main;

public class ALexOperations {
  private AnalizadorLexicoExp alex;
  public ALexOperations(AnalizadorLexicoExp alex) {
   this.alex = alex;   
  }
  
//PALABRAS_RESERVADAS:TIPOS
  public UnidadLexica unidadTipoInt() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.TINT, alex.lexema()); 
  } 
  public UnidadLexica unidadTipoBool() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.TBOO, alex.lexema()); 
  } 
  public UnidadLexica unidadElemBool() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EBOO, alex.lexema()); 
  } 
  public UnidadLexica unidadTuple() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.TUPL); 
  } 
  
//PALABRAS_RESERVADAS:ALIAS_Y_CONSTANTES
  public UnidadLexica unidadDenote() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DENO); 
  } 
  public UnidadLexica unidadAs() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.AS); 
  } 
  public UnidadLexica unidadCte() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CTE); 
  } 

//PALABRAS_RESERVADAS:CONDICIONALES  
  public UnidadLexica unidadIf() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IF); 
  } 
  public UnidadLexica unidadElse() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ELSE); 
  } 

//PALABRAS_RESERVADAS:BUCLES
  public UnidadLexica unidadFor() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FOR); 
  } 
  public UnidadLexica unidadWhile() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.WHIL); 
  } 
  public UnidadLexica unidadRepeat() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.REPE); 
  } 
  public UnidadLexica unidadUntil() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.UNTL); 
  } 

//PALABRAS_RESERVADAS:SWITCH-CASE  
  public UnidadLexica unidadIncase() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CASE); 
  } 
  public UnidadLexica unidadEquals() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EQLS); 
  } 
  public UnidadLexica unidadOtherwise() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.OTWS); 
  } 
  
//PALABRAS_RESERVADAS:ENTRADA/SALIDA
  public UnidadLexica unidadRead() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.READ, alex.lexema()); 
  } 
  public UnidadLexica unidadWrite() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.WRTE, alex.lexema()); 
  }

//PALABRAS_RESERVADAS:OTRAS
  public UnidadLexica unidadEmpty() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EMPT, alex.lexema()); 
  } 
  public UnidadLexica unidadReturn() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.RET); 
  } 
  public UnidadLexica unidadNewMem() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.NMEM); 
  } 
  public UnidadLexica unidadDelete() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DEL); 
  } 
  public UnidadLexica unidadMain() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MAIN, alex.lexema()); 
  } 

  
//SIMBOLOS:COMPARACION_Y_ASIGNACION
  public UnidadLexica unidadEq() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EQ); 
  } 
  public UnidadLexica unidadNot() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.NOT); 
  } 
  public UnidadLexica unidadNeq() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.NEQ); 
  } 
  public UnidadLexica unidadLesser() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LESS); 
  } 
  public UnidadLexica unidadGreater() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.GREA); 
  } 
  public UnidadLexica unidadGeq() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.GEQ); 
  } 
  public UnidadLexica unidadLeq() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LEQ); 
  } 
  public UnidadLexica unidadAsig() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ASIG); 
  } 

//SIMBOLOS:CON_APERTURA_Y_CIERRE
  public UnidadLexica unidadLlaveApert() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LAPE); 
  } 
  public UnidadLexica unidadLlaveCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.LCIE); 
  } 
  public UnidadLexica unidadCorchApert() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CAPE); 
  } 
  public UnidadLexica unidadCorchCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.CCIE); 
  } 
  public UnidadLexica unidadParentApert() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PAPE); 
  } 
  public UnidadLexica unidadParentCierre() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PCIE); 
  } 

//SIMBOLOS:PUNTUACION
  public UnidadLexica unidadDosPuntos() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DOSP); 
  } 
  public UnidadLexica unidadPyC() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PYC); 
  } 
  public UnidadLexica unidadComa() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.COMA); 
  } 
  public UnidadLexica unidadPunto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PTO); 
  } 

//SIMBOLOS:PUNTEROS
  public UnidadLexica unidadInterrogacion() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.INTR); 
  } 
  public UnidadLexica unidadArroba() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.ROBA); 
  }
  
//SIMBOLOS:ARITMETICOLOGICOS
  public UnidadLexica unidadSuma() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.SUMA); 
  }
  public UnidadLexica unidadResta() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.RSTA); 
  }
  public UnidadLexica unidadProducto() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.PROD); 
  }
  public UnidadLexica unidadDivision() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.DIV); 
  }
  public UnidadLexica unidadPotencia() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.POT); 
  }
  public UnidadLexica unidadModulo() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.MOD); 
  }
  
  public UnidadLexica unidadAnd() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.AND); 
  }
  public UnidadLexica unidadOr() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.OR); 
  }
  
//SIMBOLOS:FLECHAS
  public UnidadLexica unidadEntonces() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.THEN); 
  }
  public UnidadLexica unidadFlecha() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.FLCH); 
  }  
  
  
  
  public UnidadLexica unidadId() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.IDEN, alex.lexema()); 
  } 
  public UnidadLexica unidadElemInt() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EINT, alex.lexema()); 
  } 

  
  
  public UnidadLexica unidadEof() {
     return new UnidadLexica(alex.fila(),alex.columna(),ClaseLexica.EOF); 
  } 
  
  
  public void error() {
    System.err.println("***"+alex.fila()+", "+alex.columna()+" Caracter inesperado: "+alex.lexema());
  }
}
