package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static MecanumConfig driveConfig = new MecanumConfig(
      c -> {
          c.frontLeftName.set("frontLeft");
          c.frontRightName.set("frontRight");
          c.backLeftName.set("backLeft");
          c.backRightName.set("backRight");

          c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
          c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
          c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
          c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
      }
    );

    public static Follower createFollower(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
    }
}