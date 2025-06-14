package ast.instruccion.condicional;

import java.util.ArrayList;

import ast.Programa;
import ast.expresiones.Expresion;
import ast.instruccion.Declaracion;
import ast.instruccion.Instruccion;
import ast.tipos.KindT;
import ast.tipos.TBasicos;

public class Condicional extends Instruccion{
	//condIf será null si estamos en un else.
	private Expresion condIf;
	private ArrayList<Instruccion> cpoCond;
	//nextElseIf será null si este es el último condicional.
	private Condicional nextElseIf;
	
	public Condicional(Expresion e, ArrayList<Instruccion> cI, Condicional c) {
		condIf = e;
		cpoCond = cI;
		nextElseIf = c;
	}
	
	//BARRA DE LA PRACTICA
	@Override
	public String toString() {
		String s = "";
		
		if(condIf == null) s = "(\n" + cpoToString() + "\n)";
		else s = "IF((" + condIf.toString() + "), (\n" + cpoToString() + "\n)";
		
		if(nextElseIf != null) s += "ELSE " + nextElseIf.toString();
		
		return s;
	}
	
	private String cpoToString() {
		String s = "";
		for(int i = 0; i < cpoCond.size(); i++) {
			s += "\t" + cpoCond.get(i).toString();
		}
		return s;
	}


	@Override
	public void checkTipo() {
		if(condIf != null) {
			condIf.checkTipo();
			if(!condIf.getTipo().equals(new TBasicos(KindT.TBOO))) {
				System.out.println("ERROR: se esperaba una expresión booleana en CONDICIONAL " + this);
				Programa.setFin();
			}
		}
		
		for(Instruccion inst : cpoCond) 
			inst.checkTipo();
		
		if(nextElseIf != null)	
			nextElseIf.checkTipo();
	}


	@Override
	public void bind() {
		if(condIf != null) 
			condIf.bind();
		
		Programa.abrirBloque();
		for(Instruccion inst : cpoCond) {
			inst.bind();
		}
		Programa.cerrarBloque();
		
		if(nextElseIf != null)
			nextElseIf.bind();
	}
	

	@Override
	public int maxMemoria(){
		int max = 0;
		int c = 0;

		for(Instruccion ins : cpoCond){
			if(ins instanceof Declaracion){
				c += ins.getTamanyo();
				max += ins.getTamanyo();
			}
			else if(ins.hasBlock()){
				int max1 = ins.maxMemoria();
				if(c + max1 > max){
					max = c + max1;
				}
			}
		}
		
		if(nextElseIf != null) {
			max = Math.max(max, nextElseIf.maxMemoria());
		}

		return max;
	} 
	
	public boolean isBlock(){
		return true;
	}

	public void generaCodigo(){
		//Si tenemos un if:
		if(condIf != null) {
			condIf.generaCodigo();
			Programa.codigo.println("\tif");
		}
		
		for(Instruccion inst : cpoCond)
			inst.generaCodigo();
		
		//Si tenemos un else if, que para wasm es un else{if()...else}
		if(nextElseIf != null) {
			Programa.codigo.println("\telse");
			nextElseIf.generaCodigo();
		}
		
		//Ponemos un end para cada if
		if(condIf != null)
			Programa.codigo.println("\tend");

	}
	
	
	
	

}
