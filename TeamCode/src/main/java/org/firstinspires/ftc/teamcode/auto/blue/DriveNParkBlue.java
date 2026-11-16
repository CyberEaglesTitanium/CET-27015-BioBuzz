package org.firstinspires.ftc.teamcode.auto.blue;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;


@Autonomous(name = "Park Here! (Blue)")
public class DriveNParkBlue extends OpMode {
    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(84, 134, 270);
    private final Pose path1 = poseFactory.of(132, 52, 180);
    private final Pose path1Control1 = poseFactory.of(123, 122, 0);

    public Path path1() {
        return Paths.curve(start, path1Control1, path1).linear(start, path1);
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(start); // to heck with your NullPointerException
    }

    @Override
    public void start(){
        schedule(follow(follower, path1()));
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
