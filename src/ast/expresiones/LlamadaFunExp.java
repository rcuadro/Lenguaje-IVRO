package ast.expresiones;

import ast.Programa;
import ast.instruccion.funciones.LlamadaFun;

public class LlamadaFunExp extends Expresion{

	private LlamadaFun llam;
	
	public LlamadaFunExp(LlamadaFun ll) {
		llam = ll;
	}
	
	@Override
	public String toString() {
		return llam.toString();
	}

	@Override
	public void bind() {
		llam.bind();
		setLink(llam.getLink());
	}

	@Override
	public void checkTipo() {
		llam.checkTipo();
		setTipo(llam.getTipo());
	}

	
	public void generaCodigo() {
		llam.setAsigned();
		llam.generaCodigo();
	}

	public void calcularDirRelativa(){
		int dir = llam.getDelta();
		dir += 4;
		Programa.codigo.println("\ti32.const " + dir);
			
	}
}
