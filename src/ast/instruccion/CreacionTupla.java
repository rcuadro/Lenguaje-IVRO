package ast.instruccion;

import java.util.ArrayList;

import ast.ASTNode;
import ast.Programa;
import ast.tipos.TTupla;
import ast.tipos.Tipo;

public class CreacionTupla extends Creacion{
	private String iden;
	private ArrayList<Declaracion> campos;
	private TTupla tipo;

	public CreacionTupla(String id, ArrayList<Tipo> ts, ArrayList<CampoTupla> cT){
		campos = new ArrayList<Declaracion>();
		ArrayList<String> nombres = new ArrayList<String>();
		for(CampoTupla c: cT)
			nombres.add(c.getIden());
		
		tipo = new TTupla(ts, nombres, id);
		
		this.iden = id;
		for(int i = 0; i < cT.size(); i++){
			CampoTupla cT_i = cT.get(i);
			Declaracion aux = new Declaracion(ts.get(i), cT_i.getIden(), cT_i.getExp());
			campos.add(aux);
		}
	}

	@Override
	public String toString(){
		return "CREACION_TUPLA("+ iden + ", " + tipo + "(" + campos.toString() + "))";
	}

	@Override
	public void checkTipo() {
		// NOTE No falta el deshacerDenote() porque las tuplas directamente se declaran con el nombre que se quiera
		tipo.checkTipo();
		for(Declaracion d: campos)
			d.checkTipo();
		
		setTipo(tipo);
	}

	@Override
	public void bind() {
		for(Declaracion d: campos)
			d.bind();

		//NOTE Recordar que searchNode devuelve sólo el Nodo asociado a cualquier cosa que no
		// sea una función, de lo que sólo puede haber uno.
		ASTNode node = Programa.searchNode(iden);
		if (node == null){
			Programa.insertar(iden, this);
		}
		else{
			System.out.println("ERROR: el identificador " + iden + " está en uso en " + this);
			Programa.setFin();
		}

		tipo.bind();
		
	}

	@Override
	public void generaCodigo() {
		for(Declaracion d: campos.reversed()) {
			if(d.getExp() != null)
				d.getExp().generaCodigo();
			else {
				for(int i = 0; i < d.getTamanyo()/4; i++)
					Programa.codigo.println("\ti32.const 0");
			}
		}
	}
}

