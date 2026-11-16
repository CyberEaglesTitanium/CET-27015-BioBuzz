package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Pose;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.OTOSConfig;
import com.pedropathing.revhub.localizers.OTOSLocalizer;
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
                c.linearScalar.set(1.0);
                c.angularScalar.set(0.9913);
                c.offset.set(new Pose(-4.33, 0, Math.toRadians(90)));
                c.linearUnit.set(DistanceUnit.INCH);
            }
    );

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.4703038761620589);
                Controller secondaryTranslationalForward = Controller.proportional(0.00017376471549086456);
                Controller primaryTranslationalLateral = Controller.proportional(1.615796459395537);
                Controller secondaryTranslationalLateral = Controller.proportional(0.5969936168700928);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.014888497137650508));
                c.brake.set(Controller.proportionalFeedforward(0.012655222567002931));

                c.headingFeedback.set(Controller.proportional(1.0520963471223169));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.0271556565880443, -0.010259933694354934));

                c.linearBrakeCoefficients.set(Matrix.diag(0.09417810235300897, 0.030083670332909578));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0005288777378745336, 6.045512674739049E-4));

                c.maxAchievableForwardVelocity.set(69.9362204278123);
                c.maxAchievableStrafeVelocity.set(47.13856911848553);
                c.naturalForwardDeceleration.set(33.199794348733256);
                c.naturalStrafeDeceleration.set(86.54726683149329);
            }
    );

    public static Follower createFollower(HardwareMap h) {
        return new Follower(
                new OTOSLocalizer(h, localizerConfig),
                new Mecanum(h, driveConfig),
                new Foresight(foresightConfig)
        );
        // return null;
    }
}