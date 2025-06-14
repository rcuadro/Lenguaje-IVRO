package ast.instruccion.bucle;

import java.util.ArrayList;

import ast.Programa;
import ast.expresiones.Expresion;
import ast.instruccion.Declaracion;
import ast.instruccion.Instruccion;
import ast.tipos.TBasicos;
import ast.tipos.KindT;

public class While extends Bucle{
	private Expresion exp;
	private ArrayList<Instruccion> cpo;
	
	public While(Expresion e, ArrayList<Instruccion> c) {
		this.exp = e;
		this.cpo = c;
	}

	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < cpo.size(); i++){
			s += "\n\t" + cpo.get(i).toString();

			if (i != cpo.size() - 1) s += ",";
			
		}
		return "WHILE((" + exp.toString() + "), (" + s + ")\n)";
	}

	@Override
	public void checkTipo() {
		exp.checkTipo();
		if(!exp.getTipo().equals(new TBasicos(KindT.TBOO))){
			System.out.println("ERROR: se esperaba una expresión booleana en WHILE " + this);
			Programa.setFin();
		}
		else{
			for(Instruccion inst: cpo){
				inst.checkTipo();
			}
		}
	}

	@Override
	public void bind() {
		exp.bind();
		Programa.abrirBloque();
		for(Instruccion inst: cpo){
			inst.bind();
		}	
		Programa.print();
		Programa.cerrarBloque();
	}
	


	public void generaCodigo(){
       
        	Programa.codigo.println("\tblock");
        	Programa.codigo.println("\t  loop");

        	exp.generaCodigo();
        
        	Programa.codigo.println("\t i32.eqz");
        	Programa.codigo.println("\t br_if 1");
        
        	for (Instruccion ins : cpo){
            		ins.generaCodigo();
        	}

       		Programa.codigo.println("\t br 0");
        	Programa.codigo.println("\t end");
        	Programa.codigo.println("\tend");
    	}

	@Override
	public int maxMemoria(){
		int max = 0;
		int c = 0;
		for(Instruccion ins : cpo){
			if(ins instanceof Declaracion){
				c += ins.getTamanyo();
				max += ins.getTamanyo();
			}
			else if(ins.hasBlock()){
				int max1 = ins.maxMemoria();
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}
		return max;
	} 

}