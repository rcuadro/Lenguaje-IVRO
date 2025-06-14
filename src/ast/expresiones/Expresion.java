package ast.expresiones;

import ast.ASTNode;
import ast.NodeKind;

public abstract class Expresion extends ASTNode {
	
	private boolean isConst = false;
	
	private boolean valueBool;
	private int valueInt;
    
    public abstract String toString();

    public NodeKind nodeKind() {
    	return NodeKind.EXPRESION;
    }

    public String getName(){
		return "";
	}
	
	public abstract void bind();
	public abstract void checkTipo();

	

    public void setIsConst(boolean b) {
    	isConst = b;
    }
    
    public boolean getIsConst() {
    	return isConst;
    }
    

	public void generaCodigo(){

	}
    
    
    
    public int getValueInt() {
    	return valueInt;
    }
    
    public void setValueInt(int value) {
    	valueInt = value;
    }
    
    public boolean getValueBool() {
    	return valueBool;
    }
    
    public void setValueBool(boolean value) {
    	valueBool = value;
    }

	public void calcularDirRelativa() {
		
	}
}
