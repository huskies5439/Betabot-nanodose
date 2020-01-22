/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.commands.AvancerAutonomous;

public class TrajetCellule1 extends CommandGroup {
  /**
   * Trajet pour aller a la cellule 1
   */
  public TrajetCellule1() {
    addSequential(new AvancerAutonomous(72, RobotMap.kPAvancer, RobotMap.vMinAvancer));
    addSequential(new AvancerAutonomous(-101, RobotMap.kPAvancer, RobotMap.vMinAvancer));

  }
}