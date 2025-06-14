package ast.tipos;

import ast.ASTNode;
import ast.NodeKind;

public abstract class Tipo extends ASTNode{

	public abstract String toString();

	@Override
	public NodeKind nodeKind(){
		return NodeKind.TIPO;
	}
	
	@Override
	public void checkTipo() {
		setTipo(this);
	}
	
	@Override
	public void bind() {
		
	}
	
	@Override
	public boolean equals(Object o){
		if (this == null || o == null){
			return false;
		}
		if (o.toString().equals(this.toString())){
			return true;
		}
		return false;
	}

	public Tipo deshacerDenote() {
		return this;
	}
	
	public abstract int getTam();
	
}