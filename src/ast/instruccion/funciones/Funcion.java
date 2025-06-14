package ast.instruccion.funciones;

import java.util.ArrayList;

import ast.ASTNode;
import ast.Programa;
import ast.instruccion.Declaracion;
import ast.instruccion.Instruccion;
import ast.instruccion.Return;
import ast.tipos.KindT;
import ast.tipos.TBasicos;
import ast.tipos.Tipo;

public class Funcion extends Instruccion{
	private String iden;
	private Tipo tipo;
	private ArrayList<Parametro> params;
	private ArrayList<Instruccion> cuerpo;

	public Funcion(String id, Tipo t, ArrayList<Parametro> a, ArrayList<Instruccion> cpo){
		this.iden = id;
		this.tipo = t;
		this.params = a;
		this.cuerpo = cpo;
		
		if(id.equals("main"))
			Programa.addMain();
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i = 0; i < cuerpo.size();i++){
			s += "\n\t\t" + cuerpo.get(i).toString();
			if(i < cuerpo.size() - 1) s += ",";
		}
		return "FUN(" + tipo.toString() + " " + iden.toString() + ", (" + params.toString() + "), ("
			 + s + "\n\t)\n)";
	}

	@Override
	public void checkTipo() {
		tipo = tipo.deshacerDenote();
		setTipo(tipo);
		
		for(Parametro arg: params){
			arg.checkTipo();
		}
		for(Instruccion inst: cuerpo){
			inst.checkTipo();
			if((inst instanceof Return) && !(tipo.equals(inst.getTipo()))){
				System.out.println("ERROR: el tipo de la función y el valor devuelto no coinciden en RETURN " + this);
				Programa.setFin();
			}
			
		}
	}

	@Override
	public void bind() {
		ASTNode nodo = Programa.searchNode(iden, params);
		tipo.bind();
		
		if (nodo == null){
			Programa.insertar(iden, this);
			Programa.abrirBloque();
			
			if(params != null) {
				for(Parametro p: params){
					p.bind();
				}
			}

			int numReturn = 0;
			for(Instruccion ins: cuerpo){
				if (ins instanceof Return){
					numReturn += 1;
					if (numReturn > 1){
						System.out.println("ERROR: sobran returns en la funcion " + iden + " en FUNCION " + this);
						Programa.setFin();
					}
					else{
						ArrayList<ASTNode> aux = new ArrayList<ASTNode>();
						aux.add(this);
						ins.setLink(aux);
					}	
				}
				ins.bind();
			}
			
			if (numReturn == 0 && !tipo.equals(new TBasicos(KindT.EMPT))){
				System.out.println("ERROR: falta return en la funcion " + iden + " en FUNCION " + this);
				Programa.setFin();
			}
			
			Programa.print();
			Programa.cerrarBloque();
		}
		else{
			System.out.println("ERROR: el identificador " + iden + " ya está en uso con los parametros " + params + " en FUNCION " + this);
			Programa.setFin();
		}
		
		if(!"write".equals(iden) && !"read".equals(iden) && !"main".equals(iden))
			iden = iden + "_" + getCont();

	}

	public ArrayList<Parametro> getParams() {
		return params;
	}
	
	public String getIden() {
		return iden;
	}
	
	
	@Override
	public void generaCodigo(){

		int tam = maxMemoria() + 4;

    	Programa.codigo.print("(func $" + iden);
    	if (! tipo.equals(new TBasicos(KindT.EMPT))){
    		tam += 4;
       		Programa.codigo.print(" (result i32)");
    	}
   		Programa.codigo.println("");
    	Programa.codigo.println("\t(local $localsStart i32)");
    	Programa.codigo.println("\t(local $temp i32)");
    	
    	Programa.codigo.println("\ti32.const " + tam);
    	Programa.codigo.println("\tcall $reserveStack"); 
    
    	Programa.codigo.println("\tlocal.set $temp");
    	Programa.codigo.println("\tglobal.get $MP");
    	Programa.codigo.println("\tlocal.get $temp");
    	Programa.codigo.println("\ti32.store");
    	Programa.codigo.println("\tglobal.get $MP");
    	Programa.codigo.println("\ti32.const 4");
    	Programa.codigo.println("\ti32.add");
    	Programa.codigo.println("\tlocal.set $localsStart\n"); 
    	
    	boolean hasRet = false;
    	for (Instruccion instruccion: cuerpo){
		    instruccion.generaCodigo(); 
		    if (instruccion instanceof Return){
		    	hasRet = true;
		    	break;
		    }
		    Programa.codigo.println("");
    	}
    	
    	if(!hasRet && !tipo.equals(new TBasicos(KindT.EMPT))) {
    		Programa.codigo.println("\tglobal.get $SP");
    		Programa.codigo.println("\ti32.const " + 4);
    		Programa.codigo.println("\ti32.sub");
    	}
    	if(!hasRet)
    		Programa.codigo.println("call $freeStack");
    	
		Programa.codigo.println(")");

	}

	@Override
	public int maxMemoria(){
		int max = 0;
		int c = 0;

		for(Parametro p : params){
			c += p.getTamanyo();
			max += p.getTamanyo();
		}
		
		for(Instruccion inst : cuerpo){
			if(inst instanceof Declaracion){
				c += inst.getTamanyo();
				max += inst.getTamanyo();
			}
			else if(inst.hasBlock()){
				int max1 = inst.maxMemoria();
				if(c+max1 > max){
					max = c + max1;
				}
			}
		}

		return max;
	} 

	@Override
	public boolean hasBlock(){
		return true;
	}
	
}