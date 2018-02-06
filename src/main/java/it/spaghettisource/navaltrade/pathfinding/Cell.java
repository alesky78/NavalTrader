package it.spaghettisource.navaltrade.pathfinding;

import it.spaghettisource.navaltrader.geometry.Point;

public class Cell implements Comparable<Cell>{

	private Point coordinate;
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
		coordinate = new Point(x, y);
		step = 0;
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

	public Point getCoordinate() {
		return coordinate;
	}

	public int getX() {
		return coordinate.getX();
	}

	public int getY() {
		return coordinate.getY();
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
		hCosts = Math.hypot(coordinate.getX() - cell.coordinate.getX(), coordinate.getY() - cell.coordinate.getY())*nodeCost;	
		//hCosts = ((absolute(x - cell.x) + absolute(y - cell.y)))*nodeCost;
    }

    public double getfCosts() {
        return gCosts + hCosts;
    }
    
	/**
	 * less is f more is good the node
	 */
	public int compareTo(Cell o) {
		return  (int)(getfCosts() - o.getfCosts()); 	

	}
	
	public boolean equals(Object obj){
		if(obj==null){
			return false;
		}else if(!(obj instanceof Cell)){
			return false;
		}else{
			return coordinate.equals(((Cell)obj).coordinate);
		}	
	}	
	
	public String toString() {
		return "{x:"+coordinate.getX()+" y:"+coordinate.getY()+" step:"+step +" f:"+getfCosts()+" g:"+getgCosts()+" h:"+gethCosts()+"}";
	}


}
