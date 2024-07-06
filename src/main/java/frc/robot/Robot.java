// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

// import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
// import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.XboxController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  TalonSRX left_main = new TalonSRX(1);//TODO: change device ID accordingly
  TalonSRX left_folw = new TalonSRX(2);//TODO: change device ID accordingly
  TalonSRX right_main = new TalonSRX(3);//TODO: change device ID accordingly
  TalonSRX right_folw = new TalonSRX(4);//TODO: change device ID accordingly

  // Field2d bot_Pose = new Field2d();

  // double trackWidth = Units.inchesToMeters(10);//TODO: adjust base on each robot
  // DifferentialDriveKinematics differentialDriveKinematics = new DifferentialDriveKinematics(trackWidth);
  // DifferentialDrivePoseEstimator poseEstimator;

  XboxController controller = new XboxController(0); //TODO: get the port right on Driver Station
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    boolean m1_invert = true, m2_invert = false,
    m3_invert = true, m4_invert = false; //TODO: MAKE SURE THEY DON'T FIGHT!
    left_main.setInverted(m1_invert);
    left_folw.setInverted(m2_invert);
    right_main.setInverted(m3_invert);
    right_folw.setInverted(m4_invert);

    left_main.configFactoryDefault();//NOTE: check if we need this? I remember we did. Not sure if it's spark max or this one
    left_folw.configFactoryDefault();
    right_main.configFactoryDefault();
    right_folw.configFactoryDefault();

    left_folw.follow(left_main);
    right_folw.follow(right_main);

    // poseEstimator = new DifferentialDrivePoseEstimator(differentialDriveKinematics, new Rotation2d(), kDefaultPeriod, kDefaultPeriod, null);
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double deadband = 0.1;
    double left_output = Math.abs(controller.getLeftY()) > deadband ? controller.getLeftY() : 0;
    double right_output = Math.abs(controller.getRightY()) > deadband ? controller.getRightY() : 0;
    left_main.set(ControlMode.PercentOutput, left_output);
    right_main.set(ControlMode.PercentOutput, right_output);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
