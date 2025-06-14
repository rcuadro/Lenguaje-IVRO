package constructorAst;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoExp;
import ast.Programa;

public class Main {
	public static boolean errLexSint = false;
	
	public static void main(String[] args) throws Exception {
	   	Reader input = new InputStreamReader(new FileInputStream(args[0]));
	   	
		AnalizadorLexicoExp alex = new AnalizadorLexicoExp(input);
		AnalizadorSintacticoExp asint = new AnalizadorSintacticoExp(alex);
		
		Programa p = (Programa) asint.parse().value;
		if(errLexSint) {
			System.out.println("\n\nSe ha producido un error durante el análisis léxico y sintáctico por lo que se ha detenido la compilación. Por favor, comprueba los errores mostrados arriba.");
			System.exit(1);
			return;
		}
		
		System.out.println("\n\n----------AST----------\n");		
		System.out.println(p);
	 
		try{
			System.out.println("\n\n----------BINDING----------\n");
			p.bind();
			if (p.getFin() == 1){
				System.out.println("ERROR EN BINDING.");
				System.exit(1);
			}
			
			System.out.println("\n\n----------TYPING----------\n");
			p.checkTipo();
			if (p.getFin() == 1){
				System.out.println("ERROR EN TIPADO.");
				System.exit(1);
			}
			
			System.out.println("\n\n----------CODE GENERATION----------\n");
			p.generaCodigo();
			if (p.getFin() == 1){
				System.out.println("ERROR EN GENERACION CODIGO");
				System.exit(1);
			}
		}catch(Exception e){
			System.out.println("Error while parsing..." + e.getMessage());
			e.printStackTrace(System.out);
		}
	}
}   
   
