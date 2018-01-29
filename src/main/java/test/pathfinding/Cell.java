package test.pathfinding;

public class Cell implements Comparable<Cell>{

	private int x,y;
	private int step;
	boolean visited;

	public Cell(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.step = 0;
		visited = false;
	}
	
	public void reset() {
		step = 0;
		visited = false;	
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	/**
	 * less step is grather that high step
	 * 
	 */
	public int compareTo(Cell o) {
		return -1 * (step - o.step);			

	}
	
	public boolean equals(Object e) {
		return x==((Cell)e).x && y==((Cell)e).y;
	}
	
	public String toString() {
		return "x:"+x+" y:"+y+" step:"+step;
	}


}
