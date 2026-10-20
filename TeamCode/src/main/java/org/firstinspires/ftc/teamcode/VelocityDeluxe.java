package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Utility;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@TeleOp(name = "Velocity Tester IIDX")
public class VelocityDeluxe extends LinearOpMode {

    public DcMotorEx shooterMotor;
    // my god there's two of them
    public DcMotorEx shooterMotor2;
    public Limelight3A limelight;

    public boolean toggle = false;
    public double currentVel = 1416;
    public double velPresetOff = 0;

    // LIMExLIGHT
    public double lastRecordedArea;
    public LLResult result;
    public boolean limetoggle = false;


    public void whereAmI() {
        if (result != null && result.isValid()) {
            Pose3D botpose = result.getBotpose();
            if (botpose != null) {
                double x = botpose.getPosition().x;
                double y = botpose.getPosition().y;
                telemetry.addData("MT1 Location", "(" + x + ", " + y + ")");
            }
        }
    }

    public void locatify() {
        limelight.pipelineSwitch(1);
        result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            double tx = result.getTx(); // How far left or right the target is (degrees)
            double ty = result.getTy(); // How far up or down the target is (degrees)
            double ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);
            lastRecordedArea = ta;
        } else {
            telemetry.addData("Limelight", "No Targets");
        }
        whereAmI();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        shooterMotor = hardwareMap.get(DcMotorEx.class, "shootMotor");
        shooterMotor2 = hardwareMap.get(DcMotorEx.class, "shootMotor2");
        shooterMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotor2.setDirection(DcMotorSimple.Direction.REVERSE);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.start();
        limelight.pipelineSwitch(0);
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
                locatify();
                shooterMotor.setVelocity(currentVel);
                shooterMotor2.setVelocity(currentVel);
            } else {
                limelight.pipelineSwitch(0);
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
            /* 以下のコードは、「コントローラ2」が動作する必要があります。
            translation: The following code requires "controller 2" for operation. */
//            if (gamepad2.aWasPressed()) {
//                limetoggle = !limetoggle;
//            }
//
//            if (limetoggle) {
//                locatify();
//            } else {
//                limelight.pipelineSwitch(0);
//            }

            // so what if i just stole the code from last season
            if (lastRecordedArea <= 0.5) {
                currentVel = 472;
            } else if (lastRecordedArea > 0.5 && lastRecordedArea < 2) {
                currentVel = 944;
            } else if (lastRecordedArea >= 2) {
                currentVel = 1416;
            }
        }
    }
}
