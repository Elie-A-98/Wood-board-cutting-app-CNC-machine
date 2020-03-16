package WoodPackage;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.*;
import java.util.*;

public class Event implements EventInterface {

	public ArrayList <EventSubscriber> subs = new ArrayList <EventSubscriber> ();
	
	@Override
	public void Subscribe(Class c ,Object o , String methodName) {
		EventSubscriber es = new EventSubscriber (c,o,methodName);
		subs.add(es);
	}

	@Override
	public void Launch() {
		
		try {
			for ( int i = 0 ; i < subs.size() ; i ++ ){				
				subs.get(i).c.getMethod(subs.get(i).methodName,null).invoke(subs.get(i).o, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
