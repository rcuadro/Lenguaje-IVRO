package ast.expresiones.accesos;

import java.util.ArrayList;

import ast.ASTNode;
import ast.Programa;
import ast.instruccion.Declaracion;
import ast.instruccion.funciones.Funcion;
import ast.instruccion.funciones.Parametro;
import ast.tipos.KindT;
import ast.tipos.TBasicos;
import ast.tipos.Tipo;

public class AId extends Acceso{
	private String iden;
	
	public AId(String id) {
		iden = id;
	}
	
	@Override
	public String toString() {
		return iden;
	}
	
	@Override
	public void bind() {
		ArrayList<ASTNode> nodo = Programa.searchListNode(iden);
		boolean found = false;
		if(nodo != null) {
			for(ASTNode n: nodo) {
				if(!(n instanceof Funcion)) {
					setLink(nodo);
					found = true;
					if((n instanceof Declaracion) && ((Declaracion)n).getIsConst())
						setIsConst(true);
				}
			}
		}
		
		if(!found){	
			System.out.println("ERROR: identificador en AId " + iden + " no se puede utilizar en este ámbito");
			Programa.setFin();
		}
	}

	@Override
	public void checkTipo() {
		ArrayList<ASTNode> nodo = getLink();

		if(nodo == null)
			System.out.println(iden);
		for(ASTNode n: nodo) {
			if (n instanceof Declaracion || n instanceof Parametro){
				Tipo t = n.getTipo().deshacerDenote();
				setTipo(t);
				
				if(getIsConst()) {
					if(t.equals(new TBasicos(KindT.TBOO))) {
						setValueBool(((Declaracion)n).getValueBool());
					}
					else if(t.equals(new TBasicos(KindT.TINT))) {
						setValueInt(((Declaracion)n).getValueInt());
					}
				}
			}
		}
		
	}
	
	public String getIden() {
		return iden;
	}
	

	public void generaCodigo() {
		calcularDirRelativa();
		Programa.codigo.println("\ti32.load");
	}

	public void calcularDirRelativa(){
		ArrayList<ASTNode> nodo = getLink();
		ASTNode lnk = null;
		if(nodo != null) {
			for(ASTNode n: nodo) {
				if(!(n instanceof Funcion)) {
					lnk = n;
				}
			}
		}
		
		
		int dir = lnk.getDelta();
		if (lnk.isGlobal()){	
			dir += 4;
			Programa.codigo.println("\ti32.const " + dir);
		}
		else{
			
			if (lnk instanceof Parametro){

				Parametro param = (Parametro) lnk;

				Programa.codigo.println("\ti32.const " + dir);
				Programa.codigo.println("\tlocal.get $localsStart");
				Programa.codigo.println("\ti32.add");
				
				if (param.isRef()){
					Programa.codigo.println("\ti32.load");
				}

			}
			else{
				Programa.codigo.println("\ti32.const " + dir);
				Programa.codigo.println("\tlocal.get $localsStart");
        		Programa.codigo.println("\ti32.add");
			}
		}
	}

	
}

