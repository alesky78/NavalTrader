package test.pathfinding;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GridUtils {

	
	public static void convertToFile(String path, Grid grid ) throws IOException{
		File file = new File(path);
		FileWriter writer = new FileWriter(file);
		
		int size = grid.getSize();
		
		for (int y = 0; y<size; y++) {
			for (int x = 0; x<size; x++) {
				if(grid.getCell(x, y).isWall()){
					writer.write("W");
				}else{
					writer.write(" ");
				}
				
				//next line if the last element but not for the last line of the grid
				if(x == (size -1) && y != (size -1)){
					writer.write("\n");
				}
				
			}	
		}
		
		writer.close();
		
	}
	
}
