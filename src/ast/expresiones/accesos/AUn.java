package ast.expresiones.accesos;

import ast.Programa;
import ast.tipos.*;

public class AUn extends Acceso{
	private Acceso acc;
	private KindA kindAcc;
	
	public AUn(Acceso a,  KindA kAcc){
		acc = a;
		kindAcc = kAcc;
	}
	
	
	@Override
	public String toString() {
		return kindAcc.toString() + "(" + acc.toString() + ")";
	}


	@Override
	public void bind() {
		acc.bind();
	}


	@Override
	public void checkTipo() {
		acc.checkTipo();
		Tipo t1 = acc.getTipo();
		
		switch(kindAcc){	
			case KindA.ROBA:
				//CASO ACIERTO:
				if(t1 instanceof TPuntero) {
					setTipo(t1.getTipo());
				}
				else{
					System.out.println("ERROR: fallo en acceso AUN de tipo puntero" + this);
					Programa.setFin();
				}
				break;
			default:
				System.out.println("DEFAULT DE AUN " + kindAcc);
				break;
		}
	}
	
	public void generaCodigo(){
		switch (kindAcc) {
	        case ROBA:
        	 	acc.calcularDirRelativa();
        	 	Programa.codigo.println("\ti32.load");
        	 	Programa.codigo.println("\ti32.load");
         		break;
         		
         	default:
         		break;
		}
	}
	
	public void calcularDirRelativa(){
		switch (kindAcc) {
	        case ROBA:
        	 	acc.calcularDirRelativa();
        	 	Programa.codigo.println("\ti32.load");
        	 	
         		break;
        	
        	default:
        		break;
      		}
    	}
}