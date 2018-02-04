package test.pathfinding;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GridUtils {

	static Log log = LogFactory.getLog(GridUtils.class.getName());
	
	private final static char WALL = 'W';
	private final static char EMPTY = ' ';	


	public static void convertToFile(File file, Grid grid ){

		try {

			FileWriter writer = new FileWriter(file);

			int size = grid.getSize();

			for (int y = 0; y<size; y++) {
				for (int x = 0; x<size; x++) {
					if(grid.getCell(x, y).isWall()){
						writer.write(WALL);
					}else{
						writer.write(EMPTY);
					}

					//next line if the last element but not for the last line of the grid
					//other way there is an extra empty line
					if(x == (size -1) && y != (size -1)){
						writer.write("\n");
					}

				}	
			}

			writer.close();

		}catch (Exception e) {
			log.error("error saving the grid",e);
		}

	}

	public static Grid loadFromFile(File file){

		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))));

			int lineCounter = 0;
			while(reader.readLine()!=null ){
				lineCounter++; 
			}
			reader.close();

			Grid grid = new Grid(lineCounter);


			String line = null;
			char actualChar;		

			reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))));

			for (int y = 0; y<lineCounter; y++) {
				line = reader.readLine();	//read the next line
				for (int x = 0; x<lineCounter; x++) {
					actualChar = line.charAt(x);
					if(actualChar==WALL) {
						grid.getCell(x, y).setWall(true);
					}
				}	
			}	

			reader.close();
			return grid;

		}catch (Exception e) {
			log.error("error loading the grid",e);
			return null;
		}

	}	

}
