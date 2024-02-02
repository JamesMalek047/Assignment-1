import java.io.File;
import java.util.Scanner;

public class comparison {

    static int notOpen = 0;
	private void populateFromFile(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		int r = 0;
		int c;
		

		// while loop for reading the lot design
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			str = str.replaceAll("\\s", "");

			if (str.contains(SECTIONER)){
				break;
			}
			else if(str.length() == 0){
				continue;
			}
			else{
				String[] rowLine = str.split(",");
				for ( c = 0; c < rowLine.length; c++){
				lotDesign[r][c] = Util.getCarTypeByLabel(rowLine[c]);
				if (rowLine[c].equals("N")){
					notOpen++;
				}	 
				}
			
			}
			r++;

		}


		// while loop for reading occupancy data
		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			str = str.replaceAll("\\s", "");

			if (str.length() == 0){
				continue;
			}

			String[] carInfo = str.split(",");


			Car carP = new Car(Util.getCarTypeByLabel(carInfo[2]), carInfo[3]);
			
			carP.setType(Util.getCarTypeByLabel(carInfo[2]));
			if (canParkAt(Integer.parseInt(String.valueOf(carInfo[0])), Integer.parseInt(String.valueOf(carInfo[1])), carP) == true){
				park(Integer.parseInt(String.valueOf(carInfo[0])), Integer.parseInt(String.valueOf(carInfo[1])), carP);

			}
			else{
				System.out.println("Car " + carInfo[2] + "(" + carInfo[3] + ") cannot be parked at (" + carInfo[0] + "," + carInfo[1] + ")");

			}
		}

		scanner.close();
	}
    
}
