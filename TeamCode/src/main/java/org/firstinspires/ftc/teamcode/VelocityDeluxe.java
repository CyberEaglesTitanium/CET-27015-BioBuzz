package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Velocity Tester IIDX")
public class VelocityDeluxe extends LinearOpMode {

    public DcMotorEx shooterMotor;
    public boolean toggle = false;
    public double currentVel = 1416;
    public double velPreset1 = 2360;
    public double velPreset2 = 1888;
    public double velPreset3 = 1416;
    public double velPreset4 = 944;
    public double velPreset5 = 472;
    public double velPresetOff = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shootMotor");
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("CURRENT SHOOTER MOTOR VELOCITY", shooterMotor.getVelocity());
            telemetry.update();

            if (toggle) {
                shooterMotor.setPower(1);
                shooterMotor.setVelocity(currentVel);
            }

            if (gamepad1.aWasPressed()) {
                toggle = true;
            } else if (gamepad1.bWasPressed()) {
                toggle = false;
            }

            if (gamepad1.dpadUpWasPressed()) {
                currentVel = velPreset1;
            } else if (gamepad1.dpadLeftWasPressed()) {
                currentVel = velPreset2;
            } else if (gamepad1.dpadRightWasPressed()) {
                currentVel = velPreset3;
            } else if (gamepad1.dpadDownWasPressed()) {
                currentVel = velPreset4;
            } else if (gamepad1.xWasPressed()) {
                currentVel = velPreset5;
            } else if (gamepad1.yWasPressed()) {
                currentVel = velPresetOff;
            }
        }
    }
}
