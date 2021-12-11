package huntingGame.sprites.agents;

import huntingGame.Main;
import huntingGame.sprites.Player;
import huntingGame.sprites.Sprite;

import javax.vecmath.Vector2d;
import java.util.ArrayList;

public class Agent extends Sprite {

    protected double maxSpeed;
    protected double viewRadius;

    protected boolean visible, alive;

    protected Vector2d velocity;
    protected Vector2d acceleration = new Vector2d();
    protected Vector2d steerForce = new Vector2d();
    protected Vector2d desiredVelocity;

    protected String behavior;

    protected double wanderAngle;

    public Agent(int x, int y) {
        super(x, y);
        this.desiredVelocity = new Vector2d();
        this.velocity = new Vector2d(1, 0);
        visible = true;
        alive = true;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean aliveStatus) {
        this.alive = aliveStatus;
    }

    private void applyForce(Vector2d force) {
        this.acceleration.add(force);
    }

    private void seek(Vector2d targetPosition) {
        desiredVelocity.sub(targetPosition, position);
        desiredVelocity.normalize();
        desiredVelocity.scale(maxSpeed);
        steerForce.sub(desiredVelocity, velocity);

        applyForce(steerForce);
    }

    private void flee(Vector2d targetPosition) {
        desiredVelocity.sub(position, targetPosition);

        desiredVelocity.normalize();
        desiredVelocity.scale(maxSpeed);
        steerForce.sub(desiredVelocity, velocity);
        applyForce(steerForce);
    }

    public void update(Sprite target) {
        chooseBehavior(target);
        switch (this.behavior) {
            case "SEEK":
                seek(target.getPosition());
                break;
            case "FLEE":
                flee(target.getPosition());
                break;
            case "WANDER":
                wander();
                break;
            default:
                break;
        }
        velocity.add(acceleration);
        velocity.scale(maxSpeed);
        position.add(velocity, position);
        acceleration.scale(0);
    }

    private void wander() {

        int circleRadius = 10;

        Vector2d circleCenter = (Vector2d) this.velocity.clone();
        circleCenter.normalize();
        circleCenter.scale(circleRadius);
        System.out.println(circleCenter);

        Vector2d displacement = new Vector2d(0, -1);
        displacement.scale(circleRadius);
        rotateVector(displacement, wanderAngle);
        System.out.println(displacement);

        double ANGLE_CHANGE = Math.PI / 12;
        wanderAngle += (Math.random() * ANGLE_CHANGE) - ANGLE_CHANGE * .5;

        Vector2d wanderForce = new Vector2d();
        wanderForce.add(circleCenter, displacement);
        this.seek(wanderForce);

        this.velocity = new Vector2d(0, 0);
    }

    public void checkEdges() {
        if (this.position.x < 0) {
            desiredVelocity = new Vector2d(this.maxSpeed, -this.velocity.y);
            steerForce.sub(desiredVelocity, velocity);
            applyForce(steerForce);
            if (this.position.x < -16) {
                this.setAlive(false);
                this.setVisible(false);
            }
        }
        if (this.position.y < 0) {
            desiredVelocity = new Vector2d(-this.position.x, this.maxSpeed);
            steerForce.sub(desiredVelocity, velocity);
            applyForce(steerForce);
            if (this.position.y < -16) {
                this.setAlive(false);
               this.setVisible(false);
            }
        }
        if (this.position.x > Main.SCREEN_WIDTH - 16) {
            desiredVelocity = new Vector2d(this.maxSpeed, this.velocity.y);
            steerForce.sub(desiredVelocity, velocity);
            applyForce(steerForce);
            if (this.position.y > Main.SCREEN_WIDTH) {
                this.setAlive(false);
                this.setVisible(false);
            }
        }
        if (this.position.y > Main.SCREEN_HEIGHT - 16) {
            desiredVelocity = new Vector2d(this.velocity.x, this.maxSpeed);
            steerForce.sub(desiredVelocity, velocity);
            applyForce(steerForce);
            if (this.position.y > Main.SCREEN_HEIGHT) {
                this.setAlive(false);
                this.setVisible(false);
            }
        }
    }

    public Sprite findTarget(ArrayList<Agent> agents, Player player) {
        Agent agent;
        double distanceToTarget = Double.MAX_VALUE;
        Vector2d desired = new Vector2d();
        Sprite target = null;
        for (Agent value : agents) {
            agent = value;
            if (!this.equals(agent) &&
                !(this instanceof Wolf && agent instanceof Wolf) &&
                !(this instanceof Deer && agent instanceof Deer)) {
                desired.sub(agent.position, this.position);
                if (desired.length() < distanceToTarget && desired.length() < this.viewRadius) {
                    distanceToTarget = desired.length();
                    target = agent;
                }
            }
        }
        desired.sub(player.getPosition(), this.position);
        if (desired.length() < distanceToTarget && desired.length() < this.viewRadius) {
            target = player;
        }

        return target;
    }

    private void chooseBehavior(Sprite target) {
        if (target == null) this.behavior = "WANDER";
        else {
            if (this instanceof Hare || this instanceof Deer) this.behavior = "FLEE";
            if (this instanceof Wolf) this.behavior = "SEEK";


        }
    }

    private void rotateVector(Vector2d vector, double angle) {
        double length = vector.length();
        vector.x = Math.cos(angle) * length;
        vector.y = Math.sin(angle) * length;
    }
}
