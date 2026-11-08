package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.OTOSConfig;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

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
          c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
      }
    );

    public static OTOSConfig localizerConfig = new OTOSConfig(
            c -> {
                c.name.set("otos");
                c.linearScalar.set(86.85);
                c.angularScalar.set(0.9913);
                c.offset.set(new Pose(-4.33, 0, 90));
                c.linearUnit.set(DistanceUnit.INCH);
            }
    );

    public static Follower createFollower(HardwareMap h) {
        // return new Follower(Drivetrain, Localizer, Foresight);
        return null;
    }
}