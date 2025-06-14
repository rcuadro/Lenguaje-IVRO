package ast.expresiones;
import java.util.ArrayList;

import ast.Programa;
import ast.tipos.*;

public class Array extends Expresion{
	private ArrayList<Expresion> exps;
	
	public Array(ArrayList<Expresion> e){
		exps = e;
	}
	
	public String toString(){
		return "ARRAY(" + exps.toString() + ")";
	}
	
	@Override
	public void bind() {
		for(Expresion e: exps){
			e.bind();
		}	
	}

	@Override
	public void checkTipo() {
		// Le pasamos al constructor tam 0 y tipo nulo. Al comprobar el tipado miramos 
		// si el tam es cero y en ese caso el tipado será correcto siempre que lo sea
		// el del array de la izq y su tam sea 0.
		if(exps.size() == 0) {
			setTipo(new TArray(null, exps.size()));
			return /*cri cri cri cri*/; 
		}
		
		exps.getFirst().checkTipo();
		Tipo t = exps.getFirst().getTipo();
		
		for(Expresion e: exps) {
			e.checkTipo();
			if( !(t.equals(e.getTipo())) ){
				System.out.println("ERROR: los tipos de los elementos no coinciden en ARRAY" + this);
				Programa.setFin();
				setTipo(null);
				return;
			}
		}

		setTipo(new TArray(t, exps.size()));
	}
	
	public void generaCodigo(){
		for(int i = ((TArray)getTipo()).getNumElemsArr() - 1; i >= 0; i--){
			/* NOTE
			 * generar código de la exp i-ésima
			 * establecer la posición de mem en la que se guarda: Programa.codigo.println("\ti32.const " + i*4);
			 * hacer store
			 */
			// Aquí solo guardamos en la pila los n elems del array, 
			// se meterán en mem en asignación.
			exps.get(i).generaCodigo();
			
		}
		
	}
}