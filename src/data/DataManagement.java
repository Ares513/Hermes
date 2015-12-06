package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.team1ofus.apollo.Cell;
import com.team1ofus.apollo.HashCell;
import com.team1ofus.apollo.TILE_TYPE;

import core.BootstrapperConstants;
import core.DebugManagement;
import core.SEVERITY_LEVEL;
import events.IUIManagementInteractionListener;
import pathing.CellPoint;
import pathing.PathCell;


public class DataManagement implements IUIManagementInteractionListener {
	ArrayList<HashCell> cells;
	private boolean upgradeNeeded; //boolean indicating if a save is needed on launch.
	public DataManagement() {
		DebugManagement.writeNotificationToLog("Launching with directory " + BootstrapperConstants.APP_FILE_DIRECTORY);
		cells = new ArrayList<HashCell>();
		//process: load blank map into memory, then save it

		try {
			
			cells = loadAllCells();
			if(upgradeNeeded) {
				saveAllCells(cells);
				
			}
		} catch (ClassNotFoundException | IOException e) {
			DebugManagement.writeLineToLog(SEVERITY_LEVEL.CORRUPTED, "Data does not match serialization version or file does not exist.");
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		//make a placeholder map.
//		if(cells.size() == 0) {
//			
//		}
	}
	/*
	 * Create a temporary map.
	 */
	private HashCell makePlaceholderMap() {
		DebugManagement.writeLineToLog(SEVERITY_LEVEL.CRITICAL, "No HashCell files found!");
		String uuid = UUID.randomUUID().toString();
		return new HashCell(50, 50, uuid, uuid);
		
	}
	private ArrayList<HashCell> loadAllCells() throws ClassNotFoundException, IOException {
		ArrayList<Cell> output = new ArrayList<Cell>();
			List<Path> filteredPaths = getAvailableOldCells(BootstrapperConstants.APP_FILE_DIRECTORY);
			DebugManagement.writeNotificationToLog("Out of date Cell objects: " + filteredPaths.toString());
			for(Path p : filteredPaths) {
				
				output.add(loadCell(p));
				upgradeNeeded = true;
			}

			
			//now we do the conversion to a HashCell- one time automatic conversion
			ArrayList<HashCell> hashOutput = new ArrayList<HashCell>();
			
			for(Cell c : output) {
				HashCell working = new HashCell(c.getWidth(), c.getHeight(), c.getID(), c.getDisplayName());
				for(int i=0; i<c.getTile().length; i++) {
					for(int j=0; j<c.getTile()[i].length; j++) {
						if(c.getTile()[i][j].getType() == TILE_TYPE.WALL) {
							//do nothing. no sense in storing walls
						} else {
							working.setTile(i, j, c.getTile()[i][j].getType());
							//put it in the HashCell
						}
					}
				}
				hashOutput.add(working);
				DebugManagement.writeNotificationToLog("!! Converted old cell " + c.getID() + " to HashCell format. !!");
				
 			}
			List<Path> filteredHashPaths = getAvailableCells(BootstrapperConstants.APP_FILE_DIRECTORY);
			for(Path p : filteredHashPaths) {
				hashOutput.add(loadHashCell(p));
				if(hashOutput.size() == 0) {
					//there are no maps that can be loaded.
					hashOutput.add(makePlaceholderMap());
				}
			}
			return hashOutput;
		
	}
	private void saveAllCells(ArrayList<HashCell> cellsToSave) throws ClassNotFoundException, IOException {
		for(HashCell c : cellsToSave) {
				DebugManagement.writeNotificationToLog("Saving " + c.getID());
				saveCell(BootstrapperConstants.APP_FILE_DIRECTORY + File.separator + c.getID().replace(".map", "").replace(".cell", "") + ".cell", c);

		}
	}
	private Cell loadCell(Path target) throws IOException, ClassNotFoundException {
			DebugManagement.writeNotificationToLog("Loading Cell " + target);
		
			FileInputStream in = new FileInputStream(target.toString());
			
			ObjectInputStream objIn = new ObjectInputStream(in);
			
			Cell result = (Cell) objIn.readObject();
				
			objIn.close();
			in.close();
			File f = target.toFile();
			DebugManagement.writeNotificationToLog("Backing up file before deletion.");
			
			File dir = new File(target.getParent() + File.separator + "Backup of cell " + result.getID());
			if(!dir.exists()) {
				dir.mkdir();
			}
			File combined = new File(dir.getPath() + File.separator + result.getID() + "_backup" + ".map");
			
			Files.copy(target, combined.toPath(), StandardCopyOption.REPLACE_EXISTING);
			DebugManagement.writeNotificationToLog("Backed up!");
			DebugManagement.writeNotificationToLog("!!! DELETED OLD CELL !!!");
			f.delete();
			if(result.getID().contains(".map")) {
				DebugManagement.writeNotificationToLog("Updating display name for cell " + result.getID());
			}
			result.setID(target.getFileName().toString());
			return result;
			
			//The default.map file isn't located.
			// TODO Auto-generated catch block		
	}
	private HashCell loadHashCell(Path target) throws IOException, ClassNotFoundException {
		DebugManagement.writeNotificationToLog("Loading HashCell "+ target);
		FileInputStream in = new FileInputStream(target.toString());
		DebugManagement.writeNotificationToLog("Input stream loaded.");
		ObjectInputStream objIn = new ObjectInputStream(in);
		DebugManagement.writeNotificationToLog("Object stream loaded.");
		
		HashCell result = (HashCell) objIn.readObject();
		DebugManagement.writeNotificationToLog("Object stream loaded object successfully.");
			
		objIn.close();
		in.close();
		result.setID(target.getFileName().toString());
		
		return result;
		
		//The default.map file isn't located.
		// TODO Auto-generated catch block		
}
	private void saveCell(String pathWithNameAndExtension, HashCell cellToWrite) throws IOException, ClassNotFoundException {
		DebugManagement.writeNotificationToLog("Initializing save.");
		DebugManagement.writeNotificationToLog("ID " + cellToWrite.getID().toString());
		DebugManagement.writeNotificationToLog("Listed Locations " + cellToWrite.getListedLocations().toString());
		DebugManagement.writeNotificationToLog("Entry points " + cellToWrite.getEntryPoints().toString());
		FileOutputStream out = new FileOutputStream(pathWithNameAndExtension);
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		
		objOut.writeObject(cellToWrite);
		objOut.close();
	}
	private List<Path> getAvailableOldCells(String directoryPathWithNameAndExtension) throws IOException {
		List<Path> output = new ArrayList<Path>();
		File file = new File(BootstrapperConstants.APP_FILE_DIRECTORY);
		
		File[] listOfFiles = file.listFiles();
		for(File f : listOfFiles) {
			if(f.isFile()) {
				String fileName = f.getName();
				if(fileName.contains(".map")) {
					output.add(f.toPath());
				}
			}
		}
		return output;
	}
	private List<Path> getAvailableCells(String directoryPathWithNameAndExtension) throws IOException {
		List<Path> output = new ArrayList<Path>();
		File file = new File(BootstrapperConstants.APP_FILE_DIRECTORY);
		
		File[] listOfFiles = file.listFiles();
		for(File f : listOfFiles) {
			if(f.isFile()) {
				String fileName = f.getName();
				if(fileName.contains(".cell")) {
					output.add(f.toPath());
				}
			}
		}
		return output;
	}

	public ArrayList<HashCell> getCells() {
		return cells;
	}
	@Override
	public void onPathReady(CellPoint first, CellPoint second) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onWindowReady(ArrayList<PathCell> loaded) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onFindRequestReady(CellPoint first, String filter) {
		// TODO Auto-generated method stub
		
	}
}
