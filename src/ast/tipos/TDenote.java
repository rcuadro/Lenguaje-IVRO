package ast.tipos;

import java.util.ArrayList;

import ast.ASTNode;
import ast.Programa;
import ast.instruccion.CreacionTupla;
import ast.instruccion.Denote;

public class TDenote extends Tipo{
	private String alias;

	public TDenote (String t) {
		alias = t;
	}
	@Override
	public String toString(){
		return alias;
	}
	
	@Override 
	public void bind(){		
		ArrayList<ASTNode> nodo = Programa.searchListNode(alias);
		boolean found = false;
		
		for(ASTNode n: nodo) {
			if((n instanceof Denote) || (n instanceof CreacionTupla)){
				found = true;
			}
		}
		
		if(!found){
			System.out.println("ERROR no existe un denote o una tupla con alias " + alias + " en TDenote " + this);
			Programa.setFin();
		}
		else{
			setLink(nodo);
		}
	}
	
	@Override
	public void checkTipo(){
		ArrayList<ASTNode> nodo = getLink();
		boolean found = false;
		
		for(ASTNode n: nodo) {
			if((n instanceof Denote) || (n instanceof CreacionTupla)){
				found = true;
			}
		}
		
		if(!found){
			System.out.println("ERROR en TipoNombre " + this);
			Programa.setFin();
		}
	}
	
	@Override
	public Tipo deshacerDenote() {
		ArrayList<ASTNode> nodo = getLink();
		
		for(ASTNode n: nodo) {
			if(n instanceof Denote)
				return ((Denote)n).getTipo();
			else if(n instanceof CreacionTupla) {
				return ((CreacionTupla)n).getTipo();
			}
		}
		
		return null;
	}
	
	@Override
	public int getTam() {
		ArrayList<ASTNode> nodo = getLink();
		
		for(ASTNode n: nodo) {
			if(n instanceof Denote)
				return ((Denote)n).getTipo().getTam();
			else if(n instanceof CreacionTupla) {
				return ((CreacionTupla)n).getTipo().getTam();
			}
		}
		
		return 0;
	}
}

