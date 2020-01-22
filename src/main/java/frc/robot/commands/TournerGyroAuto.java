/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class TournerGyroAuto extends Command {
  double erreur;
  double angle;
  double vitesse;
  double vMin;
  int compteur;

  double kP;

  public TournerGyroAuto(double angle, double kP, double vMin) {
    requires(Robot.basePilotable);
    this.angle = angle;
    this.kP = kP;
    this.vMin = vMin;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    //Robot.basePilotable.resetGyro();
    Robot.basePilotable.ramp(2);
  }

  // Permet de tourner droit précisément a un angle désiré
  @Override
  protected void execute() {
    erreur = angle - -Robot.basePilotable.positionGyro();

    if (Math.abs(erreur) < 1) {
      vitesse = 0;
      compteur++;
    } else {
      vitesse = kP * erreur + Math.signum(erreur) * vMin;
      compteur = 0;
    }
    SmartDashboard.putNumber("erreurTourner", erreur);
    SmartDashboard.putNumber("gyro", Robot.basePilotable.positionGyro());
    SmartDashboard.putNumber("vitesseTourner", vitesse);

    Robot.basePilotable.conduireAuto(0, vitesse);

  }

  // Si le robot ateint 9 fois l'angle désiré, la commande arrête 
  @Override
  protected boolean isFinished() {
    if (compteur > 9) {

      return true;
    } else {
      return false;
    }
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
