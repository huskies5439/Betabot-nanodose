/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// Je suis gentil

package frc.robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AvancerAutonomous;
import frc.robot.commands.TournerGyroAuto;
import frc.robot.commands.TrajetCellule1;
import frc.robot.commands.TrajetCellule2Droite;
import frc.robot.commands.TrajetCellule2Gauche;
import frc.robot.subsystems.BasePilotable;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI oi = new OI();
  public static BasePilotable basePilotable = new BasePilotable();
  Command autonomousCommand;
 
  SendableChooser<String> chooser = new SendableChooser<>();
  public Preferences prefs;
  public static boolean rotAxeBool;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    prefs = Preferences.getInstance();

    Robot.basePilotable.resetEncodeur();

    chooser.setDefaultOption("TrajetCellule1", "TrajetCellule1");
    chooser.addOption("TrajetCellule2Gauche", "TrajetCellule2Gauche");
    chooser.addOption("TrajetCellule2Droite", "TrajetCellule2Droite");
    chooser.addOption("Avancer Calibration", "Avancer Calibration");
    chooser.addOption("Tourner Calibration", "Tourner Calibration");
    SmartDashboard.putData("Auto Mode", chooser);

    

  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    SmartDashboard.putNumber("encoder", Robot.basePilotable.position());
    SmartDashboard.putNumber("gyro", Robot.basePilotable.positionGyro());
    SmartDashboard.putNumber("Velocity", Robot.basePilotable.vitesse());
    SmartDashboard.putNumber("Joystick X value", Robot.oi.joystick.getX());
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    Robot.basePilotable.resetGyro();

    String command = chooser.getSelected();
    if (command == "TrajetCellule1") {
      autonomousCommand = new TrajetCellule1();
    }
    else if (command == "TrajetCellule2Gauche"){
      autonomousCommand = new TrajetCellule2Gauche();
    }
    else if (command == "TrajetCellule2Droite"){
      autonomousCommand = new TrajetCellule2Droite();
    }
    else if (command.equals("Avancer Calibration")) {
      autonomousCommand = new AvancerAutonomous(prefs.getDouble("distance", 0), prefs.getDouble("kPAvancer", 0),
          prefs.getDouble("vMinAvancer", 0));
    } else if (command.equals("Tourner Calibration")) {
      autonomousCommand = new TournerGyroAuto(prefs.getDouble("cibleAngle", 0), prefs.getDouble("kPTourner", 0),
          prefs.getDouble("vMinTourner", 0));
    }

    if (command != null) {
      autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autonomousCommand !=null){
      autonomousCommand.cancel();
    }
    Robot.basePilotable.resetEncodeur();
    Robot.basePilotable.resetGyro();
    rotAxeBool = prefs.getBoolean("2 joysticks", true);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
   
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
