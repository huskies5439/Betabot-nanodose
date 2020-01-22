/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class ConduireTeleOp extends Command {
  public ConduireTeleOp() {
    requires(Robot.basePilotable);

    
  }

  // Called just before this Command runs the first time
  // Met le ramp a 4
  @Override
  protected void initialize() {
    Robot.basePilotable.ramp(2);
   
   
    

  }

  // Permet de conduire grace au joystick
  @Override
  protected void execute() {
   
   
    Robot.basePilotable.conduire(0.6* Robot.oi.joystick.getRawAxis(1), -0.40 * Robot.oi.joystick.getRawAxis(Robot.basePilotable.getRotAxe(Robot.rotAxeBool
    )));
  } 

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
