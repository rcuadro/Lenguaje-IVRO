package ast.expresiones;

import ast.Programa;
import ast.tipos.*;

public class EUn extends Expresion{
	private Expresion subExp;
   	private KindE expKind;

	public EUn(Expresion exp, KindE eT) {
     	this.subExp = exp;
   		this.expKind = eT;
	}


	public String toString() {
		return "(" + expKind.toString() + " (" + subExp.toString() + "))"; 
	}


	@Override
	public void bind() {
		subExp.bind();		
	}

	
	@Override
	public void checkTipo() {
		subExp.checkTipo();
		Tipo t = subExp.getTipo();
	
		switch(expKind){	
			case NOT:
				//CASO ACIERTO:
				if ((t instanceof TBasicos) && ((TBasicos) t).getKindT() == KindT.TBOO){
					setTipo(subExp.getTipo());
					if(subExp.getIsConst()) {
						setValueBool(!subExp.getValueBool());
						setIsConst(subExp.getIsConst());	
					}
				}
				else{
					System.out.println("ERROR: fallo en tipo EUN" + this);
					Programa.setFin();
				}
				break;
		
			default:
				System.out.println("DEFAULT DE EUN " + expKind);
				break;
		}
			
	}
	

	public void generaCodigo(){
		switch (expKind) {
	        case NOT:
        	 	subExp.generaCodigo();
				Programa.codigo.println("\ti32.load");
	          	Programa.codigo.println("\ti32.eqz");
				Programa.codigo.println("\tif (result i32)");
				Programa.codigo.println("\ti32.const 1");
				Programa.codigo.println("\telse");
				Programa.codigo.println("\ti32.const 0");
				Programa.codigo.println("\tend");
         		break;
         	default:
         		break;
		}
	}
	
	public void calcularDirRelativa(){
		switch (expKind) {
	        case NOT:
        	 	subExp.calcularDirRelativa();
         		break;
	        default:
	        	break;
		}
	}
}

