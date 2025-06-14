package ast.tipos;

public class TBasicos extends Tipo{
	private KindT tipo;

	public TBasicos(KindT t){
		this.tipo = t;
	}	
	
	@Override
	public String toString(){
		return tipo.toString();
	}
	
	public KindT getKindT() {
		return tipo;
	}

	@Override
	public int getTam() {
		if (tipo != KindT.EMPT)
			return 4;
		else return 0;
	}

	
}
