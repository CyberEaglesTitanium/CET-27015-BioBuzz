package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

@TeleOp (name = "TeleOp^3")
public class TeleOpRebirth extends LinearOpMode {
    // SEASON THREE WOOOOOOOOOOOOOO

    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;

    public DcMotorEx shooterMotor;
    // my god there's two of them
    public DcMotorEx shooterMotor2;

    public boolean toggle = false;
    public double currentVel = 1416;
    public double velPresetOff = 0;

    // LIMExLIGHT
    public Limelight3A limelight;
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
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        backLeft.setDirection(DcMotorEx.Direction.REVERSE);

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
            double leftFrontPower;
            double rightFrontPower;
            double leftBackPower;
            double rightBackPower;

            // Gamepad movement code (obviously)
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            leftFrontPower = Range.clip(drive + turn + strafe, -1, 1);
            rightFrontPower = Range.clip(drive - turn - strafe, -1, 1);
            leftBackPower = Range.clip(drive + turn - strafe, -1, 1);
            rightBackPower = Range.clip(drive - turn + strafe, -1, 1);

            // Speed control buttons
            if (gamepad1.left_bumper) {
                leftFrontPower /= 2;
                leftBackPower /= 2;
                rightFrontPower /= 2;
                rightBackPower /= 2;
            }

            if (gamepad1.right_bumper) {
                leftFrontPower *= 1.8;
                leftBackPower *= 1.8;
                rightFrontPower *= 1.8;
                rightBackPower *= 1.8;
            }

            // Sets the power when gamepad
            frontLeft.setPower(leftFrontPower /= 1.8);
            frontRight.setPower(rightFrontPower /= 1.8);
            backLeft.setPower(leftBackPower /= 1.8);
            backRight.setPower(rightBackPower /= 1.8);

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
            if (lastRecordedArea <= 3) {
                currentVel = 1416;
            } else if (lastRecordedArea > 3 && lastRecordedArea < 10) {
                currentVel = 944;
            } else if (lastRecordedArea >= 10) {
                currentVel = 472;
            }
        }
    }
}
