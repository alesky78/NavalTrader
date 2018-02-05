package test.pathfinding;

import it.spaghettisource.navaltrader.graphic.Point;

public class Cell implements Comparable<Cell>{

	private Point cooridnate;
    private boolean wall;	//a wall cell will be not returned as adjacent becouse cannot be used
	
    //VARIABLE FOR ALGORITHM

	boolean visited;	//for all A star and BreadthFirstSearch and Dijkstra
	private int step;	//for BreadthFirstSearch
	
	//for A star  Dijkstra
	private Cell previous;
    private double gCosts;	
    private boolean open;
    private int nodeCost;	//INFO: is extremely important to have a node cost of 100 because in this way we don't lost the decimal part during the comparison between the nodes
    						// check the calculatehCosts and compareTo methods.... if we lost decimal part compare will not able to recognise exactly better nodes

    //for A star
    private double hCosts;
        
	public Cell(int x, int y) {
		super();
		cooridnate = new Point(x, y);
		this.step = 0;
		gCosts = 0;
		hCosts = 0;		
		visited = false;
		open = false;
		wall = false;
		nodeCost = 100;
	}
	
	public void reset() {
		this.step = 0;
		gCosts = 0;
		hCosts = 0;		
		visited = false;
		open = false;	
	}

	public int getX() {
		return cooridnate.getX();
	}

	public void setX(int x) {
		cooridnate.setX(x);
	}

	public int getY() {
		return cooridnate.getY();
	}

	public void setY(int y) {
		cooridnate.setY(y);
	}

	public boolean isVisited() {
		return visited;
	}
	
	public boolean isWall() {
		return wall;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
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

	public int getNodeCost() {
		return nodeCost;
	}

	public double getgCosts() {
		return gCosts;
	}

	public void calculategCosts(Cell cell) {
		gCosts = cell.getgCosts() + nodeCost;
	}
	

	public double gethCosts() {
		return hCosts;
	}
	
	public void calculatehCosts(Cell cell) {
		hCosts = Math.hypot(cooridnate.getX() - cell.cooridnate.getX(), cooridnate.getY() - cell.cooridnate.getY())*nodeCost;	
    }

    public double getfCosts() {
        return gCosts + hCosts;
    }
	
    private int absolute(int a) {
        return a > 0 ? a : -a;
    }
    
	/**
	 * less is f more is good the node
	 */
	public int compareTo(Cell o) {
		return  (int)(getfCosts() - o.getfCosts()); 	

	}
	
	public boolean equals(Object e) {
		return cooridnate.equals(e);
	}
	
	public String toString() {
		return "{x:"+cooridnate.getX()+" y:"+cooridnate.getY()+" step:"+step +" f:"+getfCosts()+" g:"+getgCosts()+" h:"+gethCosts()+"}";
	}


}
