package ast.instruccion;

import ast.ASTNode;
import ast.Programa;
import ast.tipos.Tipo;

public class Denote extends Creacion{

	private Tipo tipo;
	private String alias;
	
	public Denote(Tipo t, String a) {
		tipo = t;
		alias = a;
	}
	@Override
	public String toString() {
		return "DENOTE(" + tipo.toString() + ", " + alias.toString() + ")";
	}
	

    public void bind() {
        ASTNode nodo = Programa.searchNode(alias);
        if (nodo != null) {
        	System.out.println("ERROR: el identificador" + alias + "ya esta en uso.");	
 			Programa.setFin();	
        } 
        else {
            Programa.insertar(alias, this);
            tipo.bind();
        }
    }
	
    public void checkTipo(){
    	tipo = tipo.deshacerDenote();
    	setTipo(tipo);
    }

    public Tipo getTipo() {
        return tipo;
    }
    public String getNombre(){
        return alias;
    }

}

