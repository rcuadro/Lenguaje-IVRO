package ast.expresiones;

import ast.Programa;
import ast.tipos.KindT;
import ast.tipos.TBasicos;
import ast.tipos.Tipo;

public class Int extends Constantes{
	private int n;
	
	public Int(String nValue){
		n = Integer.parseInt(nValue);
		setIsConst(true);
		setValueInt(n);
	}
	
	public String toString(){
		return Integer.toString(n);
	}

	@Override
	public Tipo getTipo() {
		return new TBasicos(KindT.TINT);
	}
	
	@Override
	public void checkTipo(){
		setTipo(getTipo());
	}

	@Override
	public void bind() {}
	
	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + n);
	}
}
