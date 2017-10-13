import java.util.Random;

public class Particle {

	private Function function;
	private int dim;
	private double[] pos;
	private double[] vel;
	private double[] indivBestPos;
	public double valueOfIndivBest;
	
	private Random rand = new Random();
	
	public Particle(Function function) {
		
		this.function = function;
		this.dim = function.getDimensions();
		this.pos = this.randomValues(function.getMinInitPos(), function.getMaxInitPos());
		
		this.vel = this.randomValues(function.getMinInitVel(), function.getMaxInitVel());
	
		this.indivBestPos = this.pos;
		this.valueOfIndivBest = this.eval();
	}
	
	
	// used to initialize position/velocity. also used for the random vector when updating velocity
	// minInit and maxInit refer to the range of values that the entries of the vector can take
	public double[] randomValues(double minInitValue, double maxInitValue) {
		double[] randomVals = new double[this.dim];
		for(int i = 0; i < this.dim; i++) {
			randomVals[i] = minInitValue + rand.nextDouble()*(maxInitValue - minInitValue);
			
		}
		return randomVals;
	}
	
	// update velocity, position, and check if new individual best
	public void singleIteration(double[] globalBest) {
		this.updateVel(globalBest);
		this.updatePos();
		double valueOfCurrPos = this.eval();
		
		// if new value is more optimal, change the indivBests
		if (valueOfCurrPos < this.valueOfIndivBest) {
			this.valueOfIndivBest = valueOfCurrPos;
			this.indivBestPos = this.pos;
		}
	}
	
	// evaluate value of current position w.r.t. the function
	public double eval() {
		return this.function.eval(this.pos);
	}
	
	
	// update velocity using equation given in class
	public void updateVel(double[] globalBest) {
		
		// create two random vectors, values 0 to phi
		double[] vecU1 = this.randomValues(0.0, this.function.getPhi1());
		double[] vecU2 = this.randomValues(0.0, this.function.getPhi2());
		
		// for each entry in the velocity vector, update it
		for(int i = 0; i < this.dim; i++) {
			//System.out.println("Before: velocity " + vel[i] + " and position: " + pos[i]);
			// element-wise multiplication between random vector and (difference vector)
			// where difference vector = gBest/iBest vector - current Position vector
			vecU1[i] = vecU1[i] * (this.indivBestPos[i] - this.pos[i]);
			vecU2[i] = vecU2[i] * (globalBest[i] - this.pos[i]);	
			this.vel[i] = this.function.getConstrictionFactor() * (this.vel[i] + vecU1[i] + vecU2[i]);
			//System.out.println("After: velocity " + vel[i] + " and position: " + pos[i]);
		}
		//for(int i = 0; i < this.dim; i++) { System.out.println(i + ". " + vel[i]);}
		//		System.out.println("pos[0]: " + this.pos[0]);

	}
	
	// update position by adding the velocity vector
	public void updatePos() {
		double[] newPos = new double[this.dim];
		for(int i = 0; i < this.dim; i++) {
			
			newPos[i] = this.pos[i] + this.vel[i];
		}
		this.pos = newPos;
	}
	
	public String toString() {
		String pos = "[" + Double.toString(this.pos[0]);
		String vel = "[" + Double.toString(this.vel[0]);
		for(int i = 1; i < this.dim; i++) {
			pos += ", " + this.pos[i];
			vel += ", " + this.vel[i];
		}
		pos += "]";
		vel += "]";
		return "Position: " + pos + "\nVelocity: " + vel;
	}

	
	
	// Getters & Setters
	
	public double[] getPos() {
		return pos;
	}


	public void setPos(double[] position) {
		this.pos = position;
	}


	public double[] getVel() {
		return vel;
	}


	public void setVel(double[] velocity) {
		this.vel = velocity;
	}


	public double[] getIndivBestPos() {
		return indivBestPos;
	}


	public void setIndivBestPos(double[] individualBest) {
		this.indivBestPos = individualBest;
	}
	
	public double getValueOfIndivBest() {
		return valueOfIndivBest;
	}

	public void setValueOfIndivBest(double valueOfIndivBest) {
		this.valueOfIndivBest = valueOfIndivBest;
	}

}
