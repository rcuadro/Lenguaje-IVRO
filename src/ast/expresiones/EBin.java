package ast.expresiones;

import ast.Programa;
import ast.tipos.KindT;
import ast.tipos.TBasicos;
import ast.tipos.Tipo;

public class EBin extends Expresion {
   	private Expresion subExp1;
   	private Expresion subExp2;
   	private KindE expKind;

	public EBin(Expresion exp1, KindE eT, Expresion exp2) {
     	this.subExp1 = exp1;
     	this.subExp2 = exp2;
   		this.expKind = eT;
	}


	public String toString() {
		return "(" + expKind.toString() + " (" + subExp1.toString() + ", " + subExp2.toString() + "))"; 
	}


	@Override
	public void bind() {
		subExp1.bind();
		subExp2.bind();
	}


	@Override
	public void checkTipo() {
		subExp1.checkTipo();
		subExp2.checkTipo();
		Tipo t1 = subExp1.getTipo();
		Tipo t2 = subExp2.getTipo();
		
		switch(expKind){	
			case SUMA:
			case RSTA:
			case PROD:
			case DIV:
			case POT:
			case MOD:
				//CASO ACIERTO:
				if(t1.equals(t2) && t1.equals(new TBasicos(KindT.TINT))) {
					setTipo(new TBasicos(KindT.TINT));
					if(subExp1.getIsConst() && subExp2.getIsConst()) {
						setIsConst(true);
						operarConstantes();
					}
				}
				else{
					System.out.println("ERROR: fallo en expresión EBIN de tipo entero" + this);
					Programa.setFin();
				}
				break;

			case LESS:
			case GREA:
			case GEQ:
			case LEQ:
				//CASO ACIERTO:
				if(t1.equals(t2) && t1.equals(new TBasicos(KindT.TINT))) {
					setTipo(new TBasicos(KindT.TBOO));
					if(subExp1.getIsConst() && subExp2.getIsConst()) {
						setIsConst(true);
						operarConstantes();
					}
				}
				else{
					System.out.println("ERROR: fallo en expresión EBIN de tipo entero" + this);
					Programa.setFin();
				}
				break;
		
				
			case AND:
			case OR:
				//CASO ACIERTO:
				if(t1.equals(t2) && t1.equals(new TBasicos(KindT.TBOO))) {
					setTipo(new TBasicos(KindT.TBOO));
					if(subExp1.getIsConst() && subExp2.getIsConst()) {
						setIsConst(true);
						operarConstantes();
					}
				}
				else{
					System.out.println("ERROR: fallo en expresión EBIN de tipo booleano" + this);
					Programa.setFin();
				}
				break;
				
			case EQ:
			case NEQ:
				//CASO ACIERTO:
				if(t1.equals(t2)) {
					setTipo(new TBasicos(KindT.TBOO));
					if(subExp1.getIsConst() && subExp2.getIsConst()) {
						setIsConst(true);
						operarConstantes();
					}
				}
				else{
					System.out.println("ERROR: fallo en expresión EBIN de tipo booleano" + this);
					Programa.setFin();
				}
				break;
				
			default:
				System.out.println("DEFAULT DE EBIN " + expKind);
				break;
			
		}
	}
	
	private void operarConstantes(){
		switch(expKind){	
			case SUMA:
				setValueInt(subExp1.getValueInt() + subExp2.getValueInt());
				break;
			case RSTA:
				setValueInt(subExp1.getValueInt() - subExp2.getValueInt());
				break;
			case PROD:
				setValueInt(subExp1.getValueInt() * subExp2.getValueInt());
				break;
			case DIV:
				setValueInt(subExp1.getValueInt() / subExp2.getValueInt());
				break;
			case POT:
				setValueInt((int)Math.pow(subExp1.getValueInt(), subExp2.getValueInt()));
				break;
			case MOD:
				setValueInt(subExp1.getValueInt() % subExp2.getValueInt());
				break;
				
	
			case LESS:
				setValueBool(subExp1.getValueInt() < subExp2.getValueInt());
				break;
			case GREA:
				setValueBool(subExp1.getValueInt() > subExp2.getValueInt());
				break;
			case GEQ:
				setValueBool(subExp1.getValueInt() >= subExp2.getValueInt());
				break;
			case LEQ:
				setValueBool(subExp1.getValueInt() <= subExp2.getValueInt());
				break;
		
				
			case AND:
				setValueBool(subExp1.getValueBool() && subExp2.getValueBool());
				break;
			case OR:
				setValueBool(subExp1.getValueBool() || subExp2.getValueBool());
				break;
	
				
			case EQ:
				setValueBool(subExp1.getValueInt() == subExp2.getValueInt());
				break;
			case NEQ:
				setValueBool(subExp1.getValueInt() != subExp2.getValueInt());
				break;
				
			default:
				break;
			
		}
	}
	

	  public void generaCodigo() {
	    subExp1.generaCodigo();
	    subExp2.generaCodigo();
	    switch (expKind) {
	      	case SUMA:
	      		Programa.codigo.println("\ti32.add");
	      		break;
	      	case RSTA:
	      		Programa.codigo.println("\ti32.sub");
	      		break;
	      	case PROD:
	      		Programa.codigo.println("\ti32.mul");
	      		break;
	      	case DIV:
	      		Programa.codigo.println("\ti32.div_s");
	      		break;
	      	case MOD:
		        Programa.codigo.println("\ti32.rem_s");
		        break;
		        
	      	case LESS:
		        Programa.codigo.println("\ti32.lt_s");
		        break;
	      	case GREA:
		        Programa.codigo.println("\ti32.gt_s");
		        break;
	      	case GEQ:
		        Programa.codigo.println("\ti32.ge_s");
		        break;
	      	case LEQ:
		        Programa.codigo.println("\ti32.le_s");
		        break;
	      
	      	case AND:
	      		Programa.codigo.println("\ti32.and");
	      		break;
	      	case OR:
	      		Programa.codigo.println("\ti32.or");
	      		break;
	      		
	      	case EQ:
	      		Programa.codigo.println("\ti32.eq");
	      		break;
	      	case NEQ:
	      		Programa.codigo.println("\ti32.ne");
	      		break;
	      
	      default:
	    }
	  }
	
	
}
