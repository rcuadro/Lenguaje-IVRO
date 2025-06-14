package ast.instruccion;

import ast.expresiones.Expresion;

public class CampoTupla {
	private String iden;
	private Expresion exp;
	
	public CampoTupla(String id) {
		iden = id;
		exp = null;
	}
	
	public CampoTupla(String id, Expresion e) {
		iden = id;
		exp = e;
	}
	
	protected String getIden() {
		return iden;
	}
	
	protected Expresion getExp() {
		return exp;
	}
	
}
