/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;

public class TrajetCellule2Droite extends CommandGroup {
  /**
   * Trajet pour aller a la cellule 2 dpuis la base droite
   */
  public TrajetCellule2Droite() {
    addSequential(new AvancerAutonomous(50, RobotMap.kPAvancer, RobotMap.vMinAvancer));
    addSequential(new TournerGyroAuto(-90,RobotMap.kPTourner, RobotMap.VMinTourner));
    addSequential(new AvancerAutonomous(172, RobotMap.kPAvancer, RobotMap.vMinAvancer));
    addSequential(new TournerGyroAuto(0, RobotMap.kPTourner, RobotMap.VMinTourner));
    addSequential(new AvancerAutonomous(23, RobotMap.kPAvancer, RobotMap.vMinAvancer));
  }
}
