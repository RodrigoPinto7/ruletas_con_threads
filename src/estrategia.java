
public class estrategia {
	int nuevomonto;
	
	public int nuevomonto(int id, int monto) {
		nuevomonto=0;
		if(id==1) {
			nuevomonto = monto*2;
		}
		if(id==2) {
			nuevomonto = monto+1;
		}
		if(id==3) {
			nuevomonto = monto+2;
		}
		return nuevomonto;
	}

}
