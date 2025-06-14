package ast.tipos;


import ast.Programa;
import ast.expresiones.Expresion;

public class TArray extends Tipo{

	private Tipo tipo;
	private Expresion expTam;
	private int tam;

	public TArray (Tipo t, Expresion e){
		this.tipo = t;
		this.expTam = e;
		tam = -1;
	}
	
	public TArray (Tipo t, int n){
		tipo = t;
		expTam = null;
		tam = n;
	}
	
	@Override
	public String toString(){
		/*if(expTam != null)
			return "TIPO_ARRAY(" + tipo.toString() + " ," + expTam.toString() + ")";
		else*/
		return "TIPO_ARRAY(" + tipo.toString() + ", " + tam + ")";
	}
	
	@Override
	public Tipo getTipo() {
		return tipo;
	}
	
	@Override
	public void bind() {
		tipo.bind();
		if(expTam != null)
			expTam.bind();
	}
	
	@Override
	public void checkTipo() {
		tipo = tipo.deshacerDenote();
		tipo.checkTipo();
		if(expTam != null) {
			expTam.checkTipo();
			if(!expTam.getTipo().equals(new TBasicos(KindT.TINT))) {
				System.out.println("ERROR: el tamaño de un array debe ser un valor int en TARRAY" + this);
				Programa.setFin();
			}
			else if(!expTam.getIsConst()) {	
				System.out.println("ERROR: el tamaño de un array debe ser declarado con una expresión formada por constantes en TARRAY" + this);
				Programa.setFin();
			}
			else {
				tam = expTam.getValueInt();
			}
		}
		
		setTipo(this);
		
	}

	@Override
	public int getTam() {
		return tam * tipo.getTam();
	}
	
	public int getTamElemsArr() {
		return tipo.getTam();
	}
	
	public int getNumElemsArr() {
		return tam;
	}
	
}
