package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.Range;

@TeleOp (name = "TeleOp^3")
public class TeleOpRebirth extends LinearOpMode {
    // SEASON THREE WOOOOOOOOOOOOOO

    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);

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
        }
    }
}
