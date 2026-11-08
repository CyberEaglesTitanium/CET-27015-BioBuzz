package org.firstinspires.ftc.teamcode.auto;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Moving Forward (physically)")
public class MovingForward extends OpMode {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();

    // SO THIS IS HOW YOU DO POSES NOW...!!
    private final Pose startPose = p.of(24, 24, 0);
    private final Pose startEndControlPose = p.of(72, 2, 0);
    private final Pose endPose = p.of(120, 24, 0);
    private final Pose enderPose = p.of(120, 120, 90);

    private Path straightLinePath() {
        return Paths.curve(startPose, startEndControlPose, endPose).linear(startPose, endPose);
    }

    private Path otherLinePath() {
        return Paths.line(endPose, enderPose).linear(endPose, enderPose);
    }

    private Command routine() {
        return sequential(
                follow(follower, straightLinePath()),
                follow(follower, otherLinePath())
        );
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(startPose); // to heck with your NullPointerException
    }

    @Override
    public void start() {
        schedule(follow(follower, straightLinePath()));
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
