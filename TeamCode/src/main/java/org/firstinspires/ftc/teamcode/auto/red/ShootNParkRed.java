package org.firstinspires.ftc.teamcode.auto.red;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;

public class ShootNParkRed extends OpMode {
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(56, 30, 90);
    private final Pose point2Start = poseFactory.of(56, 30, 90);
    private final Pose point2 = poseFactory.of(10, 90, 0);
    private final Pose point2Control1 = poseFactory.of(19, 33, 0);

    // motors
    public DcMotorEx shooterMotor1;
    public DcMotorEx shooterMotor2;

    public DcMotor turretTransfer;

    public DcMotorEx intakeMotor;

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2).linear(point2Start, point2);
    }

    private Command routine() {
        return sequential(
                follow(follower, path1()),
                // put shooter testing here!!
                follow(follower, path2())
        );
    }

//    Command launch = Command.build()
//            .setStart(() -> {
//                shooterMotor1.setVelocity(1416);
//                shooterMotor2.setVelocity(1416);
//            })
//            .setExecute(() -> {
//                intakeMotor.setPower(1);
//                turretTransfer.setPower(1);
//            })
//            .setDone()
//            .setEnd()
//            .requiring();

    @Override
    public void init() {
        shooterMotor1 = hardwareMap.get(DcMotorEx.class, "shootMotor1");
        shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shootMotor2");
        shooterMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor2.setDirection(DcMotorSimple.Direction.REVERSE);

        Scheduler.reset();

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(start); // to heck with your NullPointerException
    }

    @Override
    public void start() {
        schedule(routine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
