package org.firstinspires.ftc.teamcode.auto.red;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.commands.Commands.waitUntil;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.utils.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Shoot!!")
public class ShootNParkRed extends OpMode {
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(56, 8, 90);
    private final Pose path1 = poseFactory.of(56, 24, 90);
    private final Pose point2Start = poseFactory.of(56, 24, 90);
    private final Pose point2 = poseFactory.of(10, 90, 0);
    private final Pose point2Control1 = poseFactory.of(19, 33, 0);

    // motors
    public DcMotorEx shooterMotor1;
    public DcMotorEx shooterMotor2;
    public Servo shooterServo;

    public DcMotor turretTransfer;

    public DcMotorEx intakeMotor;

    // servos
    public Servo flowerRamp1;
    public Servo flowerRamp2;

    // the timer!!
    private Timer commandTimer = new Timer();

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.curve(point2Start, point2Control1, point2).linear(point2Start, point2);
    }

    Command launch = sequential(
            instant(() -> {
                flowerRamp1.setPosition(0.2215);
                flowerRamp2.setPosition(0.7785);
            }),
            waitMs(1000),
            sequential(
                    instant(() -> {
                        shooterMotor1.setVelocity(1416);
                        shooterMotor2.setVelocity(1416);
                    }),
                    waitUntil(() -> shooterMotor1.getVelocity() >= 1350 && shooterMotor2.getVelocity() >= 1350),
                    instant(() -> {
                        turretTransfer.setPower(1);
                    }),
                    waitMs(200),
                    instant(() -> {
                        intakeMotor.setPower(1);
                    }),
                    waitMs(1500),
                    instant(() -> {
                            shooterMotor1.setVelocity(0);
                            shooterMotor2.setVelocity(0);
                            intakeMotor.setPower(0);
                            turretTransfer.setPower(0);
                    })
            ),
            waitMs(200),
            instant(() -> {
                flowerRamp1.setPosition(0.5);
                flowerRamp2.setPosition(0.5);
            })
    );

    private Command polRamp = instant(() -> shooterServo.setPosition(1));
    private Command nectRamp = instant(() -> shooterServo.setPosition(0));

    private Command routine() {
        return sequential(
                follow(follower, path1()),
                polRamp,
                launch,
                nectRamp,
                parallel(
                        follow(follower, path2())
                )
        );
    }

    @Override
    public void init() {
        shooterMotor1 = hardwareMap.get(DcMotorEx.class, "shootMotor");
        shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shootMotor2");
        shooterMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterServo = hardwareMap.get(Servo.class, "shooterServo");

        turretTransfer = hardwareMap.get(DcMotor.class, "turretTransfer");
        turretTransfer.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        flowerRamp1 = hardwareMap.get(Servo.class, "flowerRamp1");
        flowerRamp2 = hardwareMap.get(Servo.class, "flowerRamp2");

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
