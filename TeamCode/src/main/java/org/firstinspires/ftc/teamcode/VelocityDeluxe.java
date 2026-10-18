package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Velocity Tester IIDX")
public class VelocityDeluxe extends LinearOpMode {

    public DcMotorEx shooterMotor;
    // my god there's two of them
    public DcMotorEx shooterMotor2;
    public Limelight3A limelight;

    public boolean toggle = false;
    public double currentVel = 1416;
    public double velPresetOff = 0;

    public double lastRecordedArea;
    public LLResult result;

    @Override
    public void runOpMode() throws InterruptedException {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shootMotor");
        shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shootMotor2");
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addLine("[>^<]");
            telemetry.addData("CURRENT SHOOTER MOTOR (#1) VELOCITY", shooterMotor.getVelocity());
            telemetry.addData("CURRENT SHOOTER MOTOR (#1) RPM", shooterMotor.getVelocity() * 60 / 23.6);
            telemetry.addLine();
            telemetry.addData("CURRENT SHOOTER MOTOR (#2) VELOCITY", shooterMotor2.getVelocity());
            telemetry.addData("CURRENT SHOOTER MOTOR (#2) RPM", shooterMotor2.getVelocity() * 60 / 23.6);
            telemetry.addLine();
            telemetry.addData("TARGET SHOOTER VELOCITY", currentVel);
            telemetry.addData("TARGET SHOOTER RPM", currentVel * 60 / 23.6);
            telemetry.addLine();
            telemetry.addData("toggle", toggle);
            telemetry.update();

            if (toggle) {
                shooterMotor.setVelocity(currentVel);
                shooterMotor2.setVelocity(currentVel);
            } else if (!toggle) {
                shooterMotor.setVelocity(0);
                shooterMotor2.setVelocity(0);
            }

            if (gamepad1.bWasPressed()) {
                toggle = !toggle;
            }

            if (gamepad1.dpadUpWasPressed() && currentVel < 2360) {
                currentVel += 236;
            } else if (gamepad1.dpadDownWasPressed() && currentVel > 0) {
                currentVel -= 236;
            }  if (gamepad1.yWasPressed() && currentVel < 2360) {
                currentVel += 47.2;
            } else if (gamepad1.aWasPressed() && currentVel > 0) {
                currentVel -= 47.2;
            } else if (gamepad1.xWasPressed()) {
                currentVel = velPresetOff;
            }

            // TODO: future home of limelight
        }
    }
}
