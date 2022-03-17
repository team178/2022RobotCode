package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/*
Decimal	    Binary(pin/code)			        Action	
	        DIO7/do0	DIO8/do1	DIO9/do2		
0	        0	    	0		    0		    Blue Alliance Normal	
1	        0	    	0	    	1   		Blue Shoot	
2	        0		    1		    0	    	Blue Climb	
3	        0	    	1		    1	    	Red Alliance Normal	
4	        1		    0		    0   		Red Shoot	
5	        1		    0		    1	    	Red Climb	
6           1           1           0           Enforcers
*/

public class ArduinoLights extends SubsystemBase {

    protected boolean alliance;//false for blue alliance, true for red alliance
    protected DigitalOutput do0;
    protected DigitalOutput do1;
    protected DigitalOutput do2;

    public ArduinoLights(int d0, int d1, int d2){
        //sets the color of the alliance
        syncAlliance();
        
        //sets the pins used for boolean communication
        do0 = new DigitalOutput(d0);
        do1 = new DigitalOutput(d1);
        do2 = new DigitalOutput(d2);

        sendNormal();
    }

    public void sendNormal(){
        if(alliance){
            do0.set(false);
            do1.set(false);
            do2.set(false);
        }
        else{
            do0.set(false);
            do1.set(true);
            do2.set(true);
        }
    }

    public void shoot(){
        if(alliance){
            do0.set(false);
            do1.set(false);
            do2.set(true);
        }
        else{
            do0.set(true);
            do1.set(false);
            do2.set(false);
        }
    }

    public void climb(){
        if(alliance){
            do0.set(false);
            do1.set(true);
            do2.set(false);
        }
        else{
            do0.set(true);
            do1.set(false);
            do2.set(true);
        }
    }

    public void enforcers(){
        do0.set(true);
        do1.set(true);
        do2.set(false);
    }

    private void syncAlliance(){
        alliance = DriverStation.getAlliance().equals(Alliance.Blue);
    }

    @Override
  public void periodic() {
    // This method will be called once per scheduler run during simulation
    syncAlliance();
  }

}
