package ast.instruccion;

import ast.expresiones.accesos.Acceso;
import ast.tipos.TPuntero;

public class Delete extends Instruccion{
	private Acceso acc;

	public Delete(Acceso a){
		acc = a;
	}
	
	@Override
	public String toString(){
		return "DELETE (" + acc.toString() + ")"; 

	}

	//Quizá podemos solucionar esto haciendo que cada vez que se le asigne a un puntero memoria dinámica, 
	// se actualice un valor booleano en TPuntero que indica si apunta o no al heap.
	@Override
	public void checkTipo() {
		acc.checkTipo();
		if (!(acc.getTipo() instanceof TPuntero)) {
			System.out.println("ERROR mal tipado DELETE " + this); 
		}
	}

	
	@Override
	public void bind() {
		acc.bind();
	}
}

