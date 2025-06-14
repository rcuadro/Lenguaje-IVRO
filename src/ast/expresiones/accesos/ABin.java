package ast.expresiones.accesos;

import java.util.ArrayList;

import ast.ASTNode;
import ast.Programa;
import ast.expresiones.Expresion;
import ast.instruccion.Declaracion;
import ast.tipos.*;

public class ABin extends Acceso {
	private Acceso acc;
	private Expresion pos;
	private KindA kindAcc;
	
	
	public ABin(Acceso a, KindA kAcc, Expresion p){
		acc = a;
		pos = p;
		kindAcc = kAcc;
	}
	
	
	@Override
	public String toString() {
		return kindAcc.toString() + "(" + acc.toString() + ", " + pos.toString() + ")";
	}


	@Override
	public void bind() {
		acc.bind();
		pos.bind();
	}


	@Override
	public void checkTipo() {
		acc.checkTipo();
		Tipo t1 = acc.getTipo();
		
		switch(kindAcc){	
			case KindA.CORCHS:
				pos.checkTipo();
				Tipo t2 = pos.getTipo();
				if(t1 instanceof TArray && t2.equals(new TBasicos(KindT.TINT))) {
					setTipo(t1.getTipo());
				}
				else{
					System.out.println("ERROR: fallo en acceso ABIN de tipo array" + this);
					Programa.setFin();
				}
				break;
				
			case KindA.PTO:
				Declaracion aux = null;
				ArrayList<ASTNode> nodo = acc.getLink();
				if(nodo != null) {
					for(ASTNode n: nodo) {
						if(n instanceof Declaracion) {
							aux = (Declaracion)n;
							break;
						}
					}
				}
				
				if(aux != null && t1 instanceof TTupla && aux.getTipo() instanceof TTupla && pos instanceof AId ) {
					Tipo t3 = ((TTupla)aux.getTipo()).getTipoCampo(((AId)pos).getIden());
					if(t3 != null) {
						setTipo(t3);
					}
					else {
						System.out.println("ERROR: fallo en expresión ABIN de tipo tupla: no existe ese campo en la tupla "+ this);
						Programa.setFin();
					}
				}
				else{
					System.out.println("ERROR: fallo en expresión ABIN de tipo tupla" + this);
					Programa.setFin();
				}
				break;
			default:
				System.out.println("DEFAULT DE ABIN " + kindAcc);
				break;
			
		}
	}

	
	@Override
	public void generaCodigo() {		
		calcularDirRelativa();
		Programa.codigo.println("\ti32.load");
	}
	
	@Override
	public void calcularDirRelativa(){
		
		switch(kindAcc){	
		case KindA.CORCHS:
			int tamArray = acc.getTamanyo();
			int tamElems = ((TArray)acc.getTipo()).getTamElemsArr();
			
			// Comprobamos que la posición no se salga del array por arriba.
			pos.generaCodigo();
			Programa.codigo.println("\ti32.const " + (tamArray - tamElems));
			Programa.codigo.println("\ti32.ge_s");
			Programa.codigo.println("\tif");
			Programa.codigo.println("\ti32.const 1");
			Programa.codigo.println("\tcall $exception");
			Programa.codigo.println("\tend");
			
			// Comprobamos que la posición no se salga del array por abajo.
			pos.generaCodigo();
			Programa.codigo.println("\ti32.const 0");
			Programa.codigo.println("\ti32.lt_s");
			Programa.codigo.println("\tif");
			Programa.codigo.println("\ti32.const 1");
			Programa.codigo.println("\tcall $exception");
			Programa.codigo.println("\tend");
			
			acc.calcularDirRelativa();
			pos.generaCodigo();
			Programa.codigo.println("\ti32.const " + tamElems);
			
			Programa.codigo.println("\ti32.mul");
			Programa.codigo.println("\ti32.add");
			break;
			
		case KindA.PTO:
			int aux = ((TTupla)acc.getTipo()).getTamHasta(((AId) pos).getIden());
			if(aux == -1) //Por si algo del binding se hace mal
				System.out.println("Se ha intentado acceder a un campo inexistente en una tupla.");

			acc.calcularDirRelativa();
			Programa.codigo.println("\ti32.const " + aux);
			Programa.codigo.println("\ti32.add");
				
			break;
			
		default:
			System.out.println("Calculo de Dir Relativa Default de ABin. Algo anda mal...");
			break;
		
		}	
	}

}