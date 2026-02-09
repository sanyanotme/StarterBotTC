package org.firstinspires.ftc.teamcode;

public class PIDController {
    private double kP, kI, kD;
    private double integralSum = 0;
    private double lastError = 0;
    private long lastTime = 0;

    public PIDController(double p, double i, double d) {
        kP = p;
        kI = i;
        kD = d;
        lastTime = System.nanoTime();
    }

    public double update(double target, double current) {
        double error = target - current;

        long now = System.nanoTime();
        double dt = (now - lastTime) / 1e9;
        lastTime = now;

        integralSum += error * dt;
        double derivative = (error - lastError) / dt;
        lastError = error;

        return (kP * error) + (kI * integralSum) + (kD * derivative);
    }
}