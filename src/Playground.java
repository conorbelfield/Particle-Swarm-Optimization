import jxl.Workbook;
import java.io.File;
import java.io.IOException;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;

public class Playground {

	  // number of particles in the swarm
	private static int numParticles = 30; 
	private static int DIMENSIONS = 30;

	// personal best acceleration coefficient
	private static double PHI_1 = 2.05;
	// global best acceleration coefficient
	private static double PHI_2 = 2.05;

	private static double K = 1.0;
	// constriction factor
	//public static double CONSTRICTION_FACTOR = 0.7298;
	
	private static double ROK_MIN_INIT_POS = 15.0;
	private static double ROK_MAX_INIT_POS = 30.0;
	private static double ROK_MIN_INIT_VEL = -2.0;
	private static double ROK_MAX_INIT_VEL = 2.0;
	
	private static double ACK_MIN_INIT_POS = 16.0;
	private static double ACK_MAX_INIT_POS = 32.0;
	private static double ACK_MIN_INIT_VEL = -2.0;
	private static double ACK_MAX_INIT_VEL = 4.0;
	
	private static double RAS_MIN_INIT_POS = 2.56;
	private static double RAS_MAX_INIT_POS = 5.12;
	private static double RAS_MIN_INIT_VEL = -2.0;
	private static double RAS_MAX_INIT_VEL = 4.0;
	
	public static void main(String[] args) {
		
		try  {
			String fileName = "E:\\Results.xls";
			WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			
			
			
			String[] arr = {"Global", "Ring", "Von Neumann", "Random"};
			
			for (int x = 0; x < 4; x++) {
				Label label = new Label(x, 0, arr[x]);
				sheet.addCell(label);
			}
			for (int i = 0; i < 4; i++) {
				for (int x = 1; x < 11; x++) {
					Function function = new Function("rok", DIMENSIONS, ROK_MIN_INIT_POS, ROK_MAX_INIT_POS,
							ROK_MIN_INIT_VEL, ROK_MAX_INIT_VEL, PHI_1, PHI_2, K);

					Swarm swarm = new Swarm(numParticles, function);
					swarm.createNbhds("ri");
					// Particle[] nbhd = swarm.getNbhds()[0];
					swarm.iterate(1000);
					System.out.println(swarm.getGlobalBestValue());
					System.out.println("gbest found on iteration: " + swarm.getIterationNumOfGlobalBest());
					System.out.println("gbest value: " + swarm.getGlobalBestValue());
					Number number = new Number(i, x, swarm.getGlobalBestValue());
					sheet.addCell(number);
				}
			}
			workbook.write();
			workbook.close();
		
		
		
		} catch(WriteException e) {
			System.out.println("Write Exception");
		}
		catch(IOException e) {
			System.out.println("IO Exception");
		}
	
		
	}
}
