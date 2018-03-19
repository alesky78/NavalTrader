package it.spaghettisource.navaltrade.pathfinding;

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

			for (int y = 0; y<grid.getHeight(); y++) {
				for (int x = 0; x<grid.getWidth(); x++) {
					if(grid.getCell(x, y).isWall()){
						writer.write(WALL);
					}else{
						writer.write(EMPTY);
					}

					//next line if the last element but not for the last line of the grid
					//other way there is an extra empty line
					if(x == (grid.getWidth() -1) && y != (grid.getHeight() -1)){
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

			//int width,int height
			
			int lineCounter = 0;
			int columnCounter = 0;		
			String line;
			while((line = reader.readLine())!=null ){
				lineCounter++; 
				columnCounter = line.length();
			}
			
			log.info("lines:"+lineCounter+" columns:"+columnCounter);
			
			reader.close();

			Grid grid = new Grid(columnCounter, lineCounter);

			char actualChar;		

			reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream(file))));

			for (int y = 0; y<lineCounter; y++) {
				line = reader.readLine();	//read the next line
				log.info("content:>"+line+"< line number:"+y);
				for (int x = 0; x<columnCounter; x++) {
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
