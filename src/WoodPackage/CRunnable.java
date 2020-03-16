package WoodPackage;

public abstract class CRunnable implements Runnable {
	
	boolean stop = false ;
	
	@Override
	public void run () {
		while (!stop){
			ToDo () ;
		}
	}
	
	public abstract void ToDo () ;
	
	public void Terminate () {
		stop = true ;
	}

}
