package ast.instruccion;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Map.Entry;

import ast.Programa;
import ast.expresiones.accesos.Acceso;
import ast.tipos.TBasicos;
import ast.tipos.KindT;

public class Incase extends Instruccion{
	private Acceso acc;
	//condIf será null si estamos en un else.
	private TreeMap<Integer, ArrayList<Instruccion>> casos;
	private ArrayList<Instruccion> bloqOtws;
	
	public Incase(Acceso a, ArrayList<CasoIncase> cI) {
		acc = a;
		
		casos = new TreeMap<Integer, ArrayList<Instruccion>>();
		for(int i = 0; i < cI.size(); i++){
			CasoIncase cI_i = cI.get(i);
			if(cI_i.getCaso() == null) {
				bloqOtws = cI_i.getCpoCase();
			}
			else {
				casos.put(cI_i.getCaso(), cI_i.getCpoCase());
			}
		}
	}
	
	
	@Override
	public String toString() {
		String s = "";
		for (Entry<Integer, ArrayList<Instruccion>> caso : casos.entrySet()) {
			s +=  "\n\tCASO(" + caso.getKey() + ", " + caso.getValue().toString() + ")";
		}
		if(bloqOtws == null)
			s += "\n\tOTHERWISE(" + bloqOtws.toString() + ")";
		
		return "INCASE((" + acc.toString() + "),(" + s + "\n)" ;
				
	}


	@Override
	public void checkTipo() {
		acc.checkTipo();
		if(!(acc.getTipo()).equals(new TBasicos(KindT.TINT))) {
			System.out.println("ERROR: mal tipado en INCASE. Se espera un entero." + this);
			Programa.setFin();
		}
		
		for(Entry<Integer, ArrayList<Instruccion>> c : casos.entrySet()) {
			for(Instruccion inst : c.getValue()) {
				inst.checkTipo();
			}
		}
		
		for(Instruccion inst: bloqOtws) {
			inst.checkTipo();
		}
		
	}

	@Override
	public void bind() {
		acc.bind();
		
		for(Entry<Integer, ArrayList<Instruccion>> c : casos.entrySet()) {
			Programa.abrirBloque();
			for(Instruccion inst : c.getValue()) {
				inst.bind();
			}
			Programa.cerrarBloque();
		}
		
		Programa.abrirBloque();
		for(Instruccion inst: bloqOtws) {
			inst.bind();
		}
		Programa.cerrarBloque();
	}
	
	@Override
	public void generaCodigo() {
		HashSet<Integer> keys = new HashSet<Integer>(casos.keySet());
		
		Integer minim = null;
		Integer maxim = null;
		for(Integer i:keys) {
			if(minim == null && maxim == null) {
				minim = i;
				maxim = i;
			}
			else {
				if(i < minim)
					minim = i;
				if(i > maxim)
					maxim = i;
			}
		}
		
		int minimo = minim.intValue();
		int maximo = maxim.intValue();
		int numCasos = maximo - minimo + 2;
		
		int cont = 0;
		int casoDefault = casos.size();
		
		//Escribimos los bloques:
		for(int i = 0; i < casoDefault + 2; i++) {
			Programa.codigo.println("\tblock");
		}
		
		acc.generaCodigo();
		Programa.codigo.println("i32.const " + minimo);
		Programa.codigo.println("i32.sub");
		Programa.codigo.println("\tbr_table");
		
		for(int i = 0; i < numCasos; i++) {
			if(keys.contains(Integer.valueOf(i + minimo))) {
				Programa.codigo.println("\t" + cont);
				cont++;
			}
			else Programa.codigo.println("\t" + casoDefault);
		}
		
		for(ArrayList<Instruccion> insts: casos.values()) {
			Programa.codigo.println("\tend");
			for(Instruccion inst: insts)
				inst.generaCodigo();
			Programa.codigo.println("br " + casoDefault);
			casoDefault--;
		}
		Programa.codigo.println("\tend");
		
		
		if(bloqOtws != null) {
			for(Instruccion inst: bloqOtws)
				inst.generaCodigo();
		}
		Programa.codigo.println("\tbr 0");
		Programa.codigo.println("\tend");
			
		/*
		(module
		  ;; import the browser console object, you'll need to pass this in from JavaScript
		  (import "console" "log" (func $log (param i32)))
		
		  ;; create a global variable and initialize it to 0
		  (global $i (mut i32) (i32.const 0))
		
		  (func
		    ;;i32.const 0    ;; Coloca un valor en la pila para `br_table`
		    block
		      block
		        block
		          i32.const 0  ;; Obtener un índice de la pila (o i32.const X si es fijo)
		          br_table 0 1  ;; Ejecutar el salto según el índice en la pila
		        end
		        ;; Target para (br 0)
		        i32.const 100
		        call $log
		        br 1
		      end
		      ;; Target para (br 1)
		      i32.const 101
		      call $log
		      br 0
		    end
		    i32.const 4
		    call $log
		  )
		  
		  (start 1) ;; run the first function automatically
		)
		*/
	}

}
