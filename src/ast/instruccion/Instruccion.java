package ast.instruccion;

import ast.ASTNode;
import ast.NodeKind;

public abstract class Instruccion extends ASTNode{

	public abstract String toString ();
	
	@Override
	public NodeKind nodeKind(){
		return NodeKind.INSTRUCCION;
	}
	
	public void generaCodigo() {
		
	}
	
	public boolean hasBlock() {
		return false;
	}
	
	public int maxMemoria(){
		return 0;
	}

}
