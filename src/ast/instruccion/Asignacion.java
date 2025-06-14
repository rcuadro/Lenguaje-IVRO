package ast.instruccion;

import ast.Programa;
import ast.expresiones.Expresion;
import ast.expresiones.accesos.Acceso;
import ast.tipos.*;

public class Asignacion extends Instruccion{
	private Acceso acc;
	private Expresion exp;
	
	public Asignacion(Acceso a, Expresion e){
		acc = a;
		exp = e;
	}

	@Override
	public String toString(){
		return "ASIG(" + acc.toString() + ", " + exp.toString() + ")";
	}

	@Override
	public void checkTipo() {
		acc.checkTipo();
		exp.checkTipo();
		
		Tipo t1 = acc.getTipo();
		Tipo t2 = exp.getTipo();

		if(t1.equals(t2) && !acc.getIsConst()) {
			setTipo(t1);
		}
		else{
			System.out.println("ERROR: fallo en asignación" + this);
			Programa.setFin();
		}
		
	}

	@Override
	public void bind() {
		acc.bind();
		exp.bind();
	}
	
	

	public void generaCodigo(){
		if(acc.getTamanyo() == 4) {
			acc.calcularDirRelativa();
			exp.generaCodigo();
			Programa.codigo.println("\ti32.store");
		}
		else {
			exp.generaCodigo();
			
			for(int i = 0; i < acc.getTipo().getTamanyo()/4; i++) {
				acc.calcularDirRelativa();
				Programa.codigo.println("\ti32.const " + 4*i);
				Programa.codigo.println("\ti32.add");
				Programa.codigo.println("\tcall $invStore");
			}
			
			
			/*
			
			block
				;; Generamos los n elems
				;; Generamos la dir relativa
				;; Ponemos una n
				;; Ponemos otra n solo si n > 0
				loop
					br_if 1 ;; si n = 0, si no sigue
					i32.const 1
					i32.sub 
					;; Tenemos la nueva n arriba
					call $prepStore
					br_if 1
					i32.store
					br 0
				end
			end
			
			
			*/
		}
		
		
	}

}