package frc.robot.commands.ArduinoLights;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArduinoLights;

public class climb extends CommandBase{
    private final ArduinoLights m_ArduinoLights;
    public climb(ArduinoLights lights){
        m_ArduinoLights = lights;
        addRequirements(lights);
    }

    @Override
    public void initialize(){}

    @Override
    public void execute() {}
    
    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished(){
        return false;
    }
}
