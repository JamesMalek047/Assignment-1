import java.io.File;
import java.util.Scanner;

/**
 * @author James Malek
 * @author Melanie Malek
 */
public class ParkingLot {
	/**
	 * The delimiter that separates values
	 */
	private static final String SEPARATOR = ",";

	/**
	 * The delimiter that separates the parking lot design section from the parked
	 * car data section
	 */
	private static final String SECTIONER = "###";

	/**
	 * Instance variable for storing the number of rows in a parking lot
	 */
	private int numRows;

	/**
	 * Instance variable for storing the number of spaces per row in a parking lot
	 */
	private int numSpotsPerRow;

	/**
	 * Instance variable (two-dimensional array) for storing the lot design
	 */
	private CarType[][] lotDesign;

	/**
	 * Instance variable (two-dimensional array) for storing occupancy information
	 * for the spots in the lot
	 */
	private Car[][] occupancy;

	/**
	 * Constructs a parking lot by loading a file
	 * 
	 * @param strFilename is the name of the file
	 */
	public ParkingLot(String strFilename) throws Exception {

		if (strFilename == null) {
			System.out.println("File name cannot be null.");
			return;
		}

		// determine numRows and numSpotsPerRow; you can do so by
		// writing your own code or alternatively completing the
		// private calculateLotDimensions(...) that I have provided
		calculateLotDimensions(strFilename);
		lotDesign = new CarType[numRows][numSpotsPerRow];
		occupancy = new Car[numRows][numSpotsPerRow];

		// instantiate the lotDesign and occupancy variables!
		// WRITE YOUR CODE HERE!

		// populate lotDesign and occupancy; you can do so by
		// writing your own code or alternatively completing the
		// private populateFromFile(...) that I have provided
		populateFromFile(strFilename);
	}

	/**
	 * Parks a car (c) at a give location (i, j) within the parking lot.
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @param c is the car to be parked
	 */
	public void park(int i, int j, Car c) {
		// WRITE YOUR CODE HERE!
		occupancy[i][j] = c;
	}

	/**
	 * Removes the car parked at a given location (i, j) in the parking lot
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the car removed; the method returns null when either i or j are out
	 *         of range, or when there is no car parked at (i, j)
	 */
	public Car remove(int i, int j) {
		// WRITE YOUR CODE HERE!
		// Checks if indices are whithin valid range and if there is no car parked at
		// (i, j)
		if (i < 0 || i >= occupancy.length || j < 0 || j >= occupancy[i].length || occupancy[i][j] == null) {
			return null;
		}
		// Remove and return the car parked at (i, j)
		Car carToRemove = occupancy[i][j];
		occupancy[i][j] = null;
		return carToRemove;
	}

	/**
	 * Checks whether a car (which has a certain type) is allowed to park at
	 * location (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return true if car c can park at (i, j) and false otherwise
	 */
	public boolean canParkAt(int i, int j, Car c) {
		// WRITE YOUR CODE HERE!
		boolean canPark = false;

		// Test to ensure that the car cannot be parked at an out-of-bound position
		if (i < 0 || i >= numRows || j < 0 || j >= numSpotsPerRow) {
			// Indices out of bounds
			return false;
		}

		// Testing to see if a car is already parked at this location or if no car is allowed to park there (NA)
		if (occupancy[i][j] != null || lotDesign[i][j] == CarType.NA) { 
			canPark = false;
		// Tests for parking rules for each type of car
		} else if (c.getType() == CarType.ELECTRIC) { 
			canPark = true;
		} else if (c.getType() == CarType.SMALL && lotDesign[i][j] != CarType.ELECTRIC) {
			canPark = true;
		} else if (c.getType() == CarType.LARGE && lotDesign[i][j] == CarType.LARGE) {
			canPark = true;
		} else if (c.getType() == CarType.REGULAR
				&& (lotDesign[i][j] == CarType.REGULAR || lotDesign[i][j] == CarType.LARGE)) {
			canPark = true;
		}

		return canPark;
	}

	/**
	 * @return the total capacity of the parking lot excluding spots that cannot be
	 *         used for parking (i.e., excluding spots that point to CarType.NA)
	 */
	public int getTotalCapacity() {
		// WRITE YOUR CODE HERE!
		int totalCapacity = 0;
		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[i].length; j++) {
				if (lotDesign[i][j] != CarType.NA) {
					totalCapacity++;
				}
			}
		}
		return totalCapacity;
	}

	/**
	 * @return the total occupancy of the parking lot (i.e., the total number of
	 *         cars parked in the lot)
	 */
	public int getTotalOccupancy() {
		// WRITE YOUR CODE HERE!
		int totalOccupancy = 0;
		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[i].length; j++) {
				if (occupancy[i][j] != null) {
					totalOccupancy++;
				}
			}
		}
		return totalOccupancy;
	}

	/**
	 * @return the total amount of rows
	 */
	public int getNumRows() {
		return this.numRows;
	}

	/**
	 * @return the total amount of spots per rows
	 */
	public int getnumSpotsPerRow() {
		return this.numSpotsPerRow;
	}

	private void calculateLotDimensions(String strFilename) throws Exception {
		Scanner scanner = new Scanner(new File(strFilename));
		while (scanner.hasNext()) {
			String str = scanner.nextLine();

			if (str.contains(SECTIONER)) {
				break;
			}
			else if (str.length() == 0) {
				continue;
			} else {
				numRows++;
				String[] columns = str.split(SEPARATOR);
				numSpotsPerRow = columns.length;

			}

		}
		scanner.close();
	}

	static int notForUse = 0;

	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));
		// Reads through the file to generate the number of rows
		int rowNumber = 0;
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			str = str.replaceAll("\\s", "");
			if (str.contains(SECTIONER)) {
				break;
			} 
			else if (str.length() == 0) {
				continue;
			} 
			else {
				String[] row = str.split(SEPARATOR);
				for (int positionInRow = 0; positionInRow < row.length; positionInRow++) {
					lotDesign[rowNumber][positionInRow] = Util.getCarTypeByLabel(row[positionInRow]);
					if (row[positionInRow].equals("N")) {
						notForUse++;
					}
				}
			}
			rowNumber++;
		}
		
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			str = str.replaceAll("\\s", "");
			if (str.length() == 0) {
				continue;
			}
			String[] carRegistry = str.split(SEPARATOR); //Create an array of type String []
			Car carIdea = new Car(Util.getCarTypeByLabel(carRegistry[2]), carRegistry[3]);
			carIdea.setType(Util.getCarTypeByLabel(carRegistry[2]));

			int rowIndex = Integer.parseInt((carRegistry[0]));
			int columnIndex = Integer.parseInt((carRegistry[1]));

			if (canParkAt(rowIndex, columnIndex, carIdea)) {
				park(rowIndex, columnIndex, carIdea);
			} else {
				System.out.println("Car " + carRegistry[2] + "(" + carRegistry[3] + ") cannot be parked at (" + carRegistry[0] + "," + carRegistry[1] + ")");
			}
		}
		scanner.close();
	}

	/**
	 * Produce string representation of the parking lot
	 * 
	 * @return String containing the parking lot information
	 */
	public String toString() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("==== Lot Design ====").append(System.lineSeparator());

		for (int i = 0; i < lotDesign.length; i++) {
			for (int j = 0; j < lotDesign[0].length; j++) {
				buffer.append((lotDesign[i][j] != null) ? Util.getLabelByCarType(lotDesign[i][j])
						: Util.getLabelByCarType(CarType.NA));
				if (j < numSpotsPerRow - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(System.lineSeparator());
		}

		buffer.append(System.lineSeparator()).append("==== Parking Occupancy ====").append(System.lineSeparator());

		for (int i = 0; i < occupancy.length; i++) {
			for (int j = 0; j < occupancy[0].length; j++) {
				buffer.append(
						"(" + i + ", " + j + "): " + ((occupancy[i][j] != null) ? occupancy[i][j] : "Unoccupied"));
				buffer.append(System.lineSeparator());
			}
		}
		return buffer.toString();
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the file to process. Next, it creates an instance of
	 * ParkingLot. Finally, it prints to the standard output information about the
	 * instance of the ParkingLot just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 * @throws Exception
	 */

	public static void main(String args[]) throws Exception {

		StudentInfo.display();

		System.out.print("Please enter the name of the file to process: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		ParkingLot lot = new ParkingLot(strFilename);

		System.out.println("Total number of parkable spots (capacity): " + lot.getTotalCapacity());

		System.out.println("Number of cars currently parked in the lot: " + lot.getTotalOccupancy());

		System.out.print(lot);
	}
}