/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import frc.robot.commands.ConduireTeleOp;

/**
 * Add your docs here.
 */
public class BasePilotable extends Subsystem {

  // Creer les objets
  private CANSparkMax neod1 = new CANSparkMax(24, MotorType.kBrushless);
  private CANSparkMax neod2 = new CANSparkMax(25, MotorType.kBrushless);
  private CANSparkMax neog1 = new CANSparkMax(22, MotorType.kBrushless);
  private CANSparkMax neog2 = new CANSparkMax(23, MotorType.kBrushless);
  private SpeedControllerGroup neog = new SpeedControllerGroup(neog1, neog2);
  private SpeedControllerGroup neod = new SpeedControllerGroup(neod1, neod2);
  private DifferentialDrive drive = new DifferentialDrive(neog, neod);
  private PigeonIMU gyro = new PigeonIMU(3);
  private double[] ypr = new double[3];
  // Fonctions à initialiser
  /*
  * Mets le mode brake
  * Inverse le moteur
  */
  public BasePilotable() {

    neog1.getEncoder().setPositionConversionFactor(1 / 4.67 * 4 * Math.PI);// 4.67= rapport engrenage, 4"
    neog1.getEncoder().setVelocityConversionFactor(1 / 4.67 * 4 * Math.PI);
    neog1.setIdleMode(IdleMode.kCoast);
    neog2.setIdleMode(IdleMode.kCoast);
    neod1.setIdleMode(IdleMode.kCoast);
    neod2.setIdleMode(IdleMode.kCoast);
    neog1.setInverted(false);
    neod1.setInverted(false);

  
    
  }

  // Méthodes de la base pilotable
  /*
  Permet de conduire a la mannette 
  */
  public void conduire(double vx, double vz) {

    drive.arcadeDrive(-vx, vz);

  }
/*
Permet de conduire en autonomous
*/
  public void conduireAuto(double vx, double vz) {
    drive.arcadeDrive(vx, vz, false);
  }
  /*
  Permet de choisir le ramp selon l'action demandé
  */
  public void ramp(double rate) {
    neod1.setOpenLoopRampRate(rate);
    neod2.setOpenLoopRampRate(rate);
    neog1.setOpenLoopRampRate(rate);
    neog2.setOpenLoopRampRate(rate);
  }
  /*
  Reset tous les encodeurs
  */
  public void resetEncodeur() {
    neog1.getEncoder().setPosition(0);
    neog2.getEncoder().setPosition(0);
    neod1.getEncoder().setPosition(0);
    neod2.getEncoder().setPosition(0);
  }
/*
  retourne la position de l'encodeur
*/
  public double position() {

    return neog1.getEncoder().getPosition();

  }
  //retourne la vélocité du moteur neog1
    public double vitesse(){
    return neog1.getEncoder().getVelocity();
  }
/*
  Reset l'angle du gyro
*/
  public void resetGyro() {
    gyro.setYaw(0);

  }
/*
  retourne la position du gyro
*/
 public double positionGyro() {
    gyro.getYawPitchRoll(ypr);
    return -1 * ypr[0];

  }
public int getRotAxe(boolean x){
  if (x){
    return 4;
  }
  else {
    return 0;
  }
}

  
  @Override
  public void initDefaultCommand() {

    setDefaultCommand(new ConduireTeleOp());
  }
}

