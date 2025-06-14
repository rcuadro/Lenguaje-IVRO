package ast.tipos;

import java.util.ArrayList;

import ast.ASTNode;
import ast.Programa;
import ast.instruccion.CreacionTupla;
import ast.instruccion.funciones.Funcion;


public class TTupla extends Tipo{
	ArrayList<Tipo> tipos;
	ArrayList<String> nombres;
	String iden;
	
	public TTupla(ArrayList<Tipo> ts, ArrayList<String> ns, String id) {
		tipos = ts;
		nombres = ns;
		iden = id;
	}
	
	
	public void checkTipo() {
		ArrayList<ASTNode> nodo = getLink();
		ASTNode nd = null;
		for(ASTNode n: nodo) {
			if (!(n instanceof Funcion)){
				nd = n;
				break;
			}
		}
		
		if(!(nd instanceof CreacionTupla)) {// || (nd instanceof CreacionTupla)) {// && !(nd.getTipo()).equals(this)) {
			System.out.println("ERROR: identificador en AId " + iden + " no se puede utilizar en este ámbito");
			Programa.setFin();
		}
		for(Tipo t : tipos) {
			t.checkTipo();
		}
		
		setTipo(this);
	}
	
	public void bind() {
		ArrayList<ASTNode> nodo = Programa.searchListNode(iden);
		setLink(nodo);
		for(Tipo t : tipos)
			t.bind();
	}


	@Override
	public String toString() {
		return "TIPOS_TUPLA(" + tipos + ")";
	}
	
	public String getIden() {
		return iden;
	}
	
	public Tipo getTipoCampo(String s) {
		for(int i = 0; i < nombres.size(); i++) {
			if(s.equals(nombres.get(i))) {
				return tipos.get(i);
			}
		}
		
		return null;
	}


	@Override
	public int getTam() {
		int tam = 0;
		for(Tipo t: tipos)
			 tam += t.getTam();
		return tam;
	}
	
	public int getTamHasta(String s) {
		int tam = 0;
		
		for(int i = 0; i < nombres.size(); i++) {
			if(!(s.equals(nombres.get(i)))) {
				tam += tipos.get(i).getTam();
			}
			else return tam;
		}
		
		return -1;
	}
	
}