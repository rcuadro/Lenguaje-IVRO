package ast.instruccion;

import java.util.ArrayList;

import ast.ASTNode;
import ast.Programa;
import ast.expresiones.Expresion;
import ast.tipos.TTupla;
import ast.tipos.Tipo;

public class Declaracion extends Creacion{
	private Tipo tipo;
	private String iden;
	private Expresion exp;
	private boolean es_cte;
	private boolean isGlob = false;
	
	public Declaracion(Tipo t, String id){
		tipo = t;
		iden = id;
		
		exp = null;
		es_cte = false;
	}
	
	public Declaracion(Tipo t, String id, Expresion e) {
		tipo = t;
		iden = id;
		exp = e;
		
		es_cte = false;
	}
	
	public Declaracion(Tipo t, String id, Expresion e, boolean b) {
		tipo = t;
		iden = id;
		exp = e;
		es_cte = b;
		
	}

	@Override
	public String toString(){
		if(exp == null)
			return "DECL(" + tipo.toString() + ", " + iden.toString() + ")";
		else {
			if(!es_cte) {
				return "DECL_Y_ASIG(" + tipo.toString() + ", " + iden.toString() 
					+ ", " + exp.toString() + ")";
			}
			else {
				return "DECL_Y_ASIG(CTE " + tipo.toString() + ", " + iden.toString() 
				+ ", " + exp.toString() + ")";
			}
		}
	}

	@Override
	public void checkTipo() {
		tipo = tipo.deshacerDenote();
		tipo.checkTipo();
		if(exp != null) {
			exp.checkTipo();
			Tipo tExp = exp.getTipo();
			if(!tipo.equals(tExp)) {
				System.out.println("ERROR: fallo en una declaración" + this);
				Programa.setFin();
				return;
			}
		}
		setTipo(tipo);		
	}

	@Override
	public void bind() {
		tipo.bind();
		ASTNode node = Programa.searchNode(iden);
		if (node == null){
			Programa.insertar(iden, this);
			
			if(Programa.getSize() == 1) {
				isGlob = true;
			}
		}
		else{
			System.out.println("ERROR: el identificador " + iden + " está en uso en " + this);
			Programa.setFin();
		}
		
		if (exp != null){
			exp.bind();
			if(es_cte && !exp.getIsConst()) {
				System.out.println("ERROR: en la declaración de la constante " + iden + ". La expresión asignada no es constante en " + this);
				Programa.setFin();
			}	
		}
	}
	
    public boolean getIsConst() {
    	return es_cte;
    }
    
    public int getValueInt() {
    	return exp.getValueInt();
    }
    
    public boolean getValueBool() {
    	return exp.getValueBool();
    }
   
    public String getIden() {
    	return iden;
    }
    
    @Override
    public boolean isGlobal() {
    	return isGlob;
    }
    
    public Expresion getExp() {
    	return exp;
    }

    @Override
	public void generaCodigo(){
		/*
			Generar código de una declaración que no se asigna es no hacer nada. 
			El espacio de memoria ya está reservado, y no importa lo que se asigne  
			porque el programador debe saber que es basura.
			
			Si se asigna algo, entonces:
			- Tipos de tamaño 4:
				Se genera el codigo de exp y este se guarda en la dir relativa del acceso
				
			- Tipos de mayor tamaño:
				Exactamente igual a como lo hacemos en asignación. De hecho es también lo mismo 
				en el caso anterior.
				
			** Caso especial: Declaración de tuplas sin asignación pero con valores iniciales.
				Aquí habría que inicializar los campos al valor que se indique en creaciónTupla.
		*/
		if(exp != null){
			if(tipo.getTamanyo() == 4) {
				this.calcularDirRelativa();
				exp.generaCodigo();
				Programa.codigo.println("\ti32.store");
			}
			else if(tipo.getTamanyo() > 4){
				exp.generaCodigo();
				
				for(int i = 0; i < tipo.getTamanyo()/4; i++) {
					this.calcularDirRelativa();
					Programa.codigo.println("\ti32.const " + 4*i);
					Programa.codigo.println("\ti32.add");
					Programa.codigo.println("\tcall $invStore");
				}
			}			
		}
		else {
			if(tipo instanceof TTupla) {
				ArrayList<ASTNode> nodo = tipo.getLink();
				CreacionTupla cT = null;
				for(ASTNode n: nodo) {
					if ((n instanceof CreacionTupla)){
						cT = (CreacionTupla) n;
						break;
					}
				}
				
				cT.generaCodigo();
				
				for(int i = 0; i < tipo.getTamanyo()/4; i++) {
					this.calcularDirRelativa();
					Programa.codigo.println("\ti32.const " + 4*i);
					Programa.codigo.println("\ti32.add");
					Programa.codigo.println("\tcall $invStore");
				}
			}
			
		}
	}
	

	public void calcularDirRelativa(){
		Programa.codigo.println("\ti32.const " + getDelta());
		
		if (isGlob) Programa.codigo.println("\ti32.const " + 4);
		else Programa.codigo.println("\tlocal.get $localsStart");
		
    	Programa.codigo.println("\ti32.add");
	}

    
}