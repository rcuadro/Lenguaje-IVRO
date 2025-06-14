package ast.instruccion.funciones;

import java.util.ArrayList;

import ast.ASTNode;
import ast.Programa;
import ast.expresiones.Expresion;
import ast.expresiones.accesos.Acceso;
import ast.instruccion.Instruccion;
import ast.tipos.KindT;
import ast.tipos.TBasicos;


public class LlamadaFun extends Instruccion{
	private String iden;
	private ArrayList<Expresion> args;
	private boolean asigned = false;


	public LlamadaFun(String id){
		iden = id;
		args = new ArrayList<Expresion>();
	}
	
	public LlamadaFun(String id, Expresion e){
		iden = id;
		args = new ArrayList<Expresion>();
		args.add(e);
	}
	
	public LlamadaFun(String id, ArrayList<Expresion> a){
		iden = id;
		args = a;
	}

	@Override
	public String toString(){
		return 	"LLAMADA_FUN (" + iden + ", " + args.toString() + ")";	
	}

	public void bind() {
		ArrayList<ASTNode> nodo = Programa.searchListNode(iden);
		if (nodo != null){
			this.setLink(nodo);
		}
		else{
			System.out.println("ERROR: la funcion " + iden + " no está declarada en LLAMADAFUN" + this);
			Programa.setFin();
		}
		if (args != null){
			for(Expresion arg: args){
				arg.bind(); 
			}
		}
	}

	@Override
	public void checkTipo() {
		for(Expresion e: args){
			e.checkTipo();
		}
		
		ArrayList<ASTNode> nodo = getLink();
		boolean found = false;
		for(ASTNode n: nodo) {
			if ((n instanceof Funcion) && compareFunParams(((Funcion) n).getParams())){
				found = true;
				setTipo(n.getTipo());
				break;
			}
		}		
		if(!found) {
			System.out.println("ERROR en la llamada a la función " + iden + " Llamada " + this);
			Programa.setFin();
			return;
		}
	}
	
	private boolean compareFunParams(ArrayList<Parametro> params) {
    	if(params.size() != args.size()) 
    		return false;
    	
		for(int i = 0; i < args.size(); i++) {
			if(!(args.get(i).getTipo().equals(params.get(i).getTipoParam()))) {
				return false;
			}
		}
		
		for(int i = 0; i < args.size(); i++) {
			if (params.get(i).isRef()){	
				if (!(args.get(i) instanceof Acceso) || args.get(i).getIsConst()){ 
					System.out.println("ERROR: argumento no modificable pasado como referencia en función " + iden + " en LLAMADAFUN " + this);
					Programa.setFin();
					return false;
				}
			}
		}
		
    	return true;
    }
	
	public ArrayList<Expresion> getArgs() {
		return args;
	}

	public void setAsigned() {
		asigned = true;
	}
	
	
	public void generaCodigo(){
		int delta = 0;

		ArrayList<ASTNode> nodo = getLink();
		Funcion fun = null;
		for(ASTNode n: nodo) {
			if ((n instanceof Funcion) && compareFunParams(((Funcion) n).getParams())){
				fun = (Funcion) n;
				break;
			}
		}
		ArrayList<Parametro> funParams = fun.getParams();
		
		if("write".equals(fun.getIden())) {
			args.get(0).generaCodigo();
			Programa.codigo.println("\tcall $printi32");
			return;
		}
		else if("read".equals(fun.getIden())) {
			args.get(0).calcularDirRelativa();
			Programa.codigo.println("\tcall $readi32");
			Programa.codigo.println("\ti32.store");
			return;
		}
		
		int pos = 0;
		for(Expresion exp : args){
			delta += exp.getTipo().getTam();
			if (!(exp instanceof Acceso)){ 
				Programa.codigo.println("\tglobal.get $SP");
				Programa.codigo.println("\ti32.const " + delta);
				Programa.codigo.println("\ti32.add");
				exp.generaCodigo();

				Programa.codigo.println("\ti32.store");
			}
			else{
				if (!funParams.get(pos).isRef()){
					exp.calcularDirRelativa();
					Programa.codigo.println("\tglobal.get $SP");
					Programa.codigo.println("\ti32.const " + funParams.get(pos).getDelta() + 4);
					Programa.codigo.println("\ti32.add");
					Programa.codigo.println("\ti32.const " + (exp.getTipo().getTam()/4));
					Programa.codigo.println("\tcall $copyn");
					
				}
				else {
					delta -= exp.getTipo().getTam();
					delta += 4;
					int aux = (funParams.get(pos).getDelta() + 4);
					
					Programa.codigo.println("\tglobal.get $SP");
					Programa.codigo.println("\ti32.const " + aux);
					Programa.codigo.println("\ti32.add");
					
					exp.calcularDirRelativa();
					Programa.codigo.println("\ti32.store");

				}
			}			
			pos += 1;
		}
		
		Programa.codigo.println("\tcall $" + iden + "_" + fun.getCont());
		if(!this.asigned && (!(fun.getTipo().equals(new TBasicos(KindT.EMPT))))){
			Programa.codigo.println("\tdrop");
		}
		else if (!(fun.getTipo().equals(new TBasicos(KindT.EMPT)))){
			Programa.codigo.println("\ti32.load");
		}
		

	}
	
	
}
