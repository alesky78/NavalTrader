package test.pathfinding;

public class Cell implements Comparable<Cell>{

	private int x,y;
	private int step;
	boolean visited;

	//for A star
	private Cell previous;
    private int gCosts;
    private int hCosts;	
    private boolean open;    
	
	public Cell(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.step = 0;
		gCosts = 0;
		hCosts = 0;		
		visited = false;
		open = false;
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
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Cell getPrevious() {
		return previous;
	}

	public void setPrevious(Cell previous) {
		this.previous = previous;
	}

	public int getgCosts() {
		return gCosts;
	}

	public void setgCosts(int gCosts) {
		this.gCosts = gCosts;
	}

	public int gethCosts() {
		return hCosts;
	}
	
    public void sethCosts(int hCosts) {
		this.hCosts = hCosts;
	}

	public void calculatehCosts(Cell cell) {
        this.sethCosts((absolute(x - cell.x) + absolute(y - cell.y)));
    }

    public int getfCosts() {
        return gCosts + hCosts;
    }
	
    private int absolute(int a) {
        return a > 0 ? a : -a;
    }
    
	/**
	 * less is f more is good the node
	 */
	public int compareTo(Cell o) {
		return  getfCosts() - o.getfCosts();			

	}
	
	public boolean equals(Object e) {
		return x==((Cell)e).x && y==((Cell)e).y;
	}
	
	public String toString() {
		return "{x:"+x+" y:"+y+" step:"+step +" f:"+getfCosts()+" g:"+getgCosts()+" h:"+gethCosts()+"}";
	}


}
