package WoodPackage;

public class EventSubscriber {

	public Object o;
	public Class c ;
	public String methodName ;
	
	public EventSubscriber(Class c ,Object o, String methodName) {
		this.c = c ;
		this.o = o;
		this.methodName = methodName;
	}
	
	
	
}
