package ast.instruccion.funciones;

import java.util.ArrayList;

import ast.ASTNode;
import ast.NodeKind;
import ast.Programa;
import ast.tipos.Tipo;

public class Parametro extends ASTNode{
	private Tipo tipo;
	private String iden;
	private boolean ref;

	public Parametro(Tipo t, String id){
		this.tipo = t;
		this.iden = id;
		this.ref = false;
		
	}
	
	public Parametro(Tipo t, String id, boolean r){
		this.tipo = t;
		this.iden = id;
		this.ref = r;
		
	}

	public String toString(){
		if (ref){
			return "REF(" + tipo.toString() + ", " + iden + ")";
		}
		else{
			return "(" + tipo.toString() + ", " + iden + ")";
		}
		
	}
	
	public Tipo getTipoParam() {
		return tipo;
	}

	public void checkTipo() {
		tipo = tipo.deshacerDenote();
		tipo.checkTipo();
		setTipo(tipo);
	}

	@Override
	public NodeKind nodeKind() {
		return NodeKind.PARAMETRO;
	}

	@Override
	public void bind() {
		tipo.bind();
		
		ArrayList<ASTNode> nodo = Programa.searchListNode(iden);

		boolean foundVar = false;
		if(nodo != null) {
			for(ASTNode n: nodo) {
				if(!(n instanceof Funcion)) {
					foundVar = true;
					break;
				}
			}
		}
		
		if (nodo == null || !foundVar){
			Programa.insertar(iden, this);
			//this.setLink(nodo);
		}
		else{
			System.out.println("ERROR: el identificador " + iden + "ya está en uso en PARAMETRO" + this);
			Programa.setFin();
		}
	}
	
	public boolean isRef() {
		return ref;
	}

}
