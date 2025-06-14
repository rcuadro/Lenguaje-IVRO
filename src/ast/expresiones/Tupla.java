package ast.expresiones;

import java.util.ArrayList;

import ast.tipos.TTupla;
import ast.tipos.Tipo;


public class Tupla extends Expresion{
private ArrayList<Expresion> exps;
	
	public Tupla(ArrayList<Expresion> e){
		exps = e;
	}
	
	public String toString(){
		return "TUPLA(" + exps.toString() + ")";
	}

	@Override
	public void bind() {
		for(Expresion e: exps){
			e.bind();
		}	
	}

	@Override
	public void checkTipo() {
		ArrayList<Tipo> tipos = new ArrayList<Tipo>();
		for(Expresion e: exps) {
			e.checkTipo();
			tipos.add(e.getTipo());
		}
		
		setTipo(new TTupla(tipos, null, null));
		
	}
	
	public void generaCodigo(){
		for(Expresion e: exps.reversed()){
			/* NOTE
			 * generar código de la exp i-ésima
			 * establecer la posición de mem en la que se guarda: Programa.codigo.println("\ti32.const " + i*4);
			 * hacer store
			 */
			// Aquí solo guardamos en la pila los n elems de la tupla, 
			// se meterán en mem en asignación.
			e.generaCodigo();
			
		}
		
	}
	
	
}

