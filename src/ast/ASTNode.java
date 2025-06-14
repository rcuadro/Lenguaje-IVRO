package ast;

import java.util.ArrayList;

import ast.tipos.Tipo;

public abstract class ASTNode {
	private Tipo tipo;
	private ArrayList<ASTNode> link;
	private ASTNode prevNode;
	private int delta = -1;
	private int cont = -1;
	
    public abstract NodeKind nodeKind();
    public abstract String toString();
    public abstract void checkTipo();
    public abstract void bind();
    
    public void setCont(int c) {
    	cont = c;
    }
    
    public int getCont() {
    	return cont;
    }
    
    public void setPrevNode(ASTNode pN) {
    	prevNode = pN;
    }
    
    
    public int setDelta() {
    	if(delta == -1) {
	    	if(prevNode == null)
	    		delta = 0;
	    	else delta = prevNode.setDelta();
    	}
    	
    	return delta + getTamanyo();
    }
    
    
    public Tipo getTipo() {
    	return tipo;
    }
    
    public void setTipo(Tipo t) {
    	tipo = t;
    }
    
    public ArrayList<ASTNode> getLink() {
    	return link;
    }
    
    public void setLink(ArrayList<ASTNode> nodo) {
    	link = nodo;
    }
    
	public int getTamanyo(){
		if (tipo != null){
			return tipo.getTam();
		}
		return 0;
	}

    public int getDelta() {
    	if(delta == -1)
    		setDelta();
        return delta;
    }
	
    public boolean isGlobal() {
    	return false;
    }
    
    public void generaCodigo(){}
    
}
