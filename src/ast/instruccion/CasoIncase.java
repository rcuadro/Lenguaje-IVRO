package ast.instruccion;

import java.util.ArrayList;

public class CasoIncase {
	private Integer caso;
	private ArrayList<Instruccion> cpoCase;
	
	public CasoIncase(Integer c, ArrayList<Instruccion> cC) {
		caso = c;
		cpoCase = cC;
	}
	
	protected Integer getCaso() {
		return caso;
	}

	protected ArrayList<Instruccion> getCpoCase() {
		return cpoCase;
	}
}
