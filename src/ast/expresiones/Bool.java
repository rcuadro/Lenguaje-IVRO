package ast.expresiones;

import ast.Programa;
import ast.tipos.KindT;
import ast.tipos.TBasicos;
import ast.tipos.Tipo;

public class Bool extends Constantes{
	private boolean b;
	
	public Bool(String bValue){
		if (bValue.equals("True")) {
			b = true;
		}
		else b = false;
		
		setIsConst(true);
		setValueBool(b);
	}

	@Override
	public String toString(){
		if (b) return "True";
		else return "False";
	}

	@Override
	public Tipo getTipo(){
		return new TBasicos(KindT.TBOO);
	}
	
	@Override
	public void checkTipo() {
		setTipo(this.getTipo());
	}

	@Override
	public void bind() {
		
	}
	
	public int getValue(){
		if (b) return 1;
		return 0;
	}

	public void generaCodigo(){
		Programa.codigo.println("\ti32.const " + getValue());
	}

}
