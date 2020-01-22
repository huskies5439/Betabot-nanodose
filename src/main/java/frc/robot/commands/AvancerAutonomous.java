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
//créer les variables
public class AvancerAutonomous extends Command {
  private int compteur;
  private double erreurAvancer;
  private double vitesseAvancer;
  private double distance;
  private double vMinAvancer;
  private double kPAvancer;
  private double erreurRotation;
  private double angleInitial;

  public AvancerAutonomous(double distance, double kPAvancer, double vMinAvancer) {
    requires(Robot.basePilotable);
    this.distance = distance;
    this.kPAvancer = kPAvancer;
    this.vMinAvancer = vMinAvancer;

  }

  /*
   * définie l'angle initial désative le mode ramp reset l'encodeur
   */
  @Override
  protected void initialize() {
    angleInitial = Robot.basePilotable.positionGyro();
    Robot.basePilotable.ramp(2);
    Robot.basePilotable.resetEncodeur();

  }

  /*
   * Permet d'avancer droit en mode autonoumous Envoie des données sur smart 
   * ashboard
   */
  @Override
  protected void execute() {
    
    erreurAvancer = distance - Robot.basePilotable.position();
    erreurRotation = angleInitial - Robot.basePilotable.positionGyro();

    if (Math.abs(erreurAvancer) < 2) {
      compteur++;
    } else {
      vitesseAvancer = kPAvancer * erreurAvancer + Math.signum(erreurAvancer) * vMinAvancer;
      compteur = 0;
    }
    SmartDashboard.putNumber("erreurAvancer", erreurAvancer);
    SmartDashboard.putNumber("encoder", Robot.basePilotable.position());
    SmartDashboard.putNumber("vitesseAvancer", vitesseAvancer);
    SmartDashboard.putNumber("erreurRotation", erreurRotation);
    Robot.basePilotable.conduireAuto(vitesseAvancer, erreurRotation * -0.005);

  }

  /*
   * Si le compteur est supérieur a 10, la fonction se finie, sinon, elle 
   * ontinue
   */
  @Override
  protected boolean isFinished() {

    if (compteur > 10) {

      return true;
    } else {
      return false;
    }

  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
