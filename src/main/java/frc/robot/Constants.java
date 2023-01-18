// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class PCM {
    // arm
    public static int ARM_PISTON_LEFT = 0;
    public static int ARM_PISTON_RIGHT = 0;

    // grabber
    public static int GRABBER_PISTON_LEFT = 0;
    public static int GRABBER_PISTON_RIGHT = 0;
    public static int GRABBER_PISTON_LATCH = 0;    

    // conveyor

    // chassis
    
  }

  public static class CAN {
    public static int ARM_MOTOR_LEFT = 0;
    public static int ARM_MOTOR_RIGHT = 0;
  }

  public static class DIO {
    public static int ITEM_SENSOR = 0;
  }

  public static final class ARM {

    public static final int FLOOR_POSITION = 0;
    public static final int CONVEYOR_POSITION = 0;
    public static final int SHELF_POSITION = 0;
    public static final int LOW_CUBE_POSITION = 0;
    public static final int LOW_CONE_POSITION = 0;
    public static final int MEDIUM_CUBE_POSITION = 0;
    public static final int MEDIUM_CONE_POSITION = 0;
    public static final int HIGH_CUBE_POSITION = 0;
    public static final int HIGH_CONE_POSITION = 0;

    public static final int KP = 0;
    public static final int KI = 0;
    public static final int KD = 0;
    


  }


}
