package ast.instruccion;

import ast.Programa;
import ast.expresiones.Expresion;
import ast.tipos.KindT;
import ast.tipos.TBasicos;

public class Return extends Instruccion{
	private Expresion exp;
	
	public Return(Expresion e){
		exp = e;
	}

	@Override
	public String toString(){
		if(exp == null)
			return "RETURN(EMPTY)";
		else 
			return "RETURN (" + exp.toString() + ")";
	}

	@Override
	public void bind(){
		if (exp == null)
			return;
		
		exp.bind();
		if (Programa.getSize() > 2){
			System.out.println("ERROR: se ha encontrado un RETURN en el interior de un subbloque de código.  " + this);
			Programa.setFin(); 
		}
	}

	@Override 
	public void checkTipo(){
		if(exp == null)
			setTipo(new TBasicos(KindT.EMPT));
		else {
			exp.checkTipo();
			setTipo(exp.getTipo());
		}
	}
	
	@Override
	public void generaCodigo(){
		Programa.codigo.println("\tglobal.get $SP");
		Programa.codigo.println("\ti32.const " + 4);
		Programa.codigo.println("\ti32.sub");
		
		if (exp.getTipo() instanceof TBasicos){
			exp.generaCodigo();
		}
		else exp.calcularDirRelativa();
		
		Programa.codigo.println("\ti32.store");
		
	
		Programa.codigo.println("\tglobal.get $SP");
		Programa.codigo.println("\ti32.const " + 4);
		Programa.codigo.println("\ti32.sub");
		Programa.codigo.println("\tcall $freeStack"); 
	
	    Programa.codigo.println("\treturn");
	}	
}
