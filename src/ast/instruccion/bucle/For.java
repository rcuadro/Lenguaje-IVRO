package ast.instruccion.bucle;

import java.util.ArrayList;

import ast.Programa;
import ast.expresiones.Expresion;
import ast.instruccion.Asignacion;
import ast.instruccion.Declaracion;
import ast.instruccion.Instruccion;
import ast.tipos.TBasicos;
import ast.tipos.KindT;

public class For extends Bucle{
	private Declaracion dec;
	private Expresion exp;
	private ArrayList<Instruccion> cpo;
	private Asignacion asig;
	
	public For(Declaracion d, Expresion e, Asignacion a, ArrayList<Instruccion> c) {
		this.dec = d;
		this.exp = e;
		
		this.asig = a;
		this.cpo = c;
	}

	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < cpo.size(); i++){
			s += "\n\t" + cpo.get(i).toString();
			if (i != cpo.size() - 1) s += ", ";

		}
		return "FOR((" + dec.toString() + ", " + exp.toString() + ", " + asig.toString() + "), (" + s + "))";
	}

	@Override
	public void checkTipo() {
		dec.checkTipo();
		exp.checkTipo();
		asig.checkTipo();
		
		if(!dec.getTipo().equals(new TBasicos(KindT.TINT)) || !asig.getTipo().equals(new TBasicos(KindT.TINT))){
			System.out.println("ERROR: se esperaba una declaración y asignación entera en FOR " + this);
			Programa.setFin();
		}
		else if(!exp.getTipo().equals(new TBasicos(KindT.TBOO))) {
			System.out.println("ERROR: se esperaba una expresión booleana en FOR " + this);
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
		Programa.abrirBloque();
		dec.bind();
		exp.bind();
		asig.bind();
		for(Instruccion inst : cpo){
			inst.bind();
		}
		Programa.print();
		Programa.cerrarBloque();
	}


	public void generaCodigo(){

        	dec.generaCodigo();

        	Programa.codigo.println("\tblock");
        	Programa.codigo.println("\t loop");

        	exp.generaCodigo();
        
        	Programa.codigo.println(" i32.eqz");
        	Programa.codigo.println(" br_if 1");
        
        	for (Instruccion ins : cpo){
         	   ins.generaCodigo();
        	}

        	asig.generaCodigo();

        	Programa.codigo.println("\t br 0");
        	Programa.codigo.println("\t end");
        	Programa.codigo.println("\tend");

	}

	@Override
	public int maxMemoria(){
		int max = 0 + dec.getTamanyo();
		int c = 0;
		
		for(Instruccion inst : cpo){
			if(inst instanceof Declaracion){
				c += inst.getTamanyo();
				max += inst.getTamanyo();
			}
			else if(inst.hasBlock()){
				int max1 = inst.maxMemoria();
				if(c + max1 > max){
					max = c + max1;
				}
			}
		}
		return max;
	} 
	
}
