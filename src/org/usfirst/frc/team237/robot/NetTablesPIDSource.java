package org.usfirst.frc.team237.robot;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class NetTablesPIDSource implements PIDSource {
	NetworkTable table;
	public enum direction {
		x,
		y;
	}
	protected direction m_direction = direction.x; 
	protected PIDSourceType m_pidSource = PIDSourceType.kDisplacement;
	public void setDirection(direction dir){
		m_direction = dir; 
	}
	public NetTablesPIDSource(){
		table = NetworkTable.getTable("GRIP/Vision");
		if (table != null) {
			System.out.print("Aquired Table");
		}
	}
	private int getLargestAreaIndex(){
		double[] areas = table.getNumberArray("area", new double[0]);
		double largest = 0.0; 
		int indexOfLargest = 0; 
		for (int i = 0; i<areas.length; i++){
			if(areas[i] > largest){
				largest = areas[i];
				indexOfLargest = i;
			}
		}
		return indexOfLargest; 
	}
	public double getCenterX(){
		int index = getLargestAreaIndex(); 
		double[] centerX = table.getNumberArray("centerX", new double[0]);
		if (centerX.length == 0) {
			return 0.0;
		}
		System.out.println("Array Exists");
		SmartDashboard.putNumber("Center X", centerX[index]);
		return centerX[index]; 
	}
	public double getCenterY(){
		int index = getLargestAreaIndex(); 
		double[] centerY = table.getNumberArray("centerY", new double[0]);
		//SmartDashboard.putNumber("Center Y", centerY[index]);
		if (centerY.length == 0) {
			return 0.0;
		}
		return centerY[index]; 
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		// TODO Auto-generated method stub
		m_pidSource = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		// TODO Auto-generated method stub
		
		return m_pidSource;
	}
	
	@Override
	public double pidGet() {
		// TODO Auto-generated method stub
		if (m_direction == direction.x){
			//System.out.print(getCenterX());
			//System.out.print("\n");
			//SmartDashboard.putNumber("Center X", getCenterX());
			return getCenterX();
		} else if (m_direction == direction.y){
			return getCenterY();
		}
		return 0;
	}

}
