package ast.expresiones;

import ast.Programa;
import ast.tipos.TPuntero;
import ast.tipos.Tipo;

public class NewMem extends Expresion{
	
	private Tipo tipo;
	
	public NewMem(Tipo t){
		this.tipo = t;
	}

	@Override
	public String toString() {
		return "NewMem (" + tipo.toString() + ")";
	}

	@Override
	public void checkTipo() {
		tipo = tipo.deshacerDenote();
		tipo.checkTipo();
		setTipo(new TPuntero(tipo));
	}

	@Override
	public void bind() {
		tipo.bind();
	}

	@Override
	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + tipo.getTam());
		Programa.codigo.println("\tcall $reserveHeap");
		Programa.codigo.println("\tglobal.get $NP");
	}
}
