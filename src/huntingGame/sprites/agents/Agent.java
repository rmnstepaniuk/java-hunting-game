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
        this.acceleration.setX(force.getX());
        this.acceleration.setY(force.getY());
    }

    private void seek(Vector2d targetPosition) {
        this.desiredVelocity.sub(targetPosition, this.position);
        this.desiredVelocity.normalize();
        this.desiredVelocity.scale(this.maxSpeed);
        this.steerForce.sub(this.desiredVelocity, this.velocity);

        applyForce(this.steerForce);
    }

    private void flee(Vector2d targetPosition) {
        this.desiredVelocity.sub(this.position, targetPosition);

        this.desiredVelocity.normalize();
        this.desiredVelocity.scale(this.maxSpeed);
        this.steerForce.sub(this.desiredVelocity, this.velocity);
        applyForce(this.steerForce);
    }

    private void wander() {

        int wanderRadius = 10;

        Vector2d wanderVector = (Vector2d) this.velocity.clone();
        wanderVector.normalize();
        wanderVector.scale(wanderRadius);

        this.wanderAngle += this.velocity.angle(wanderVector);

        Vector2d displacement = new Vector2d(0, -1);
        displacement.scale(wanderRadius);
        rotateVector(displacement, this.wanderAngle);

        double ANGLE_CHANGE = Math.PI / 9;
        this.wanderAngle += (Math.random() * ANGLE_CHANGE) - ANGLE_CHANGE * .5;

        this.steerForce.add(wanderVector, displacement);
        applyForce(this.steerForce);
    }

    private void contain() {
        Vector2d desired;
        if (this.position.x < 25 || this.position.x > Main.SCREEN_WIDTH - this.width - 25) {
            desired = new Vector2d(-this.velocity.x, this.velocity.y);
            this.steerForce.sub(desired, this.velocity);
            applyForce(steerForce);
        }
        if (this.position.y < 25 || this.position.y > Main.SCREEN_HEIGHT - this.width - 25) {
            desired = new Vector2d(this.velocity.x, -this.velocity.y);
            this.steerForce.sub(desired, this.velocity);
            applyForce(steerForce);
        }
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
            case "CONTAIN":
                contain();
                break;
            default:
                break;
        }
        this.velocity.add(this.acceleration);
        if (this.velocity.length() > this.maxSpeed) {
            this.velocity.normalize();
            this.velocity.scale(this.maxSpeed);
        }
        this.position.add(this.velocity, this.position);
    }

    public boolean closeToEdges() {
        return this.position.x < 25 || this.position.x > Main.SCREEN_WIDTH - this.width - 25 ||
                this.position.y < 25 || this.position.y > Main.SCREEN_HEIGHT - this.width - 25;
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
        if (this instanceof Wolf) {
            if (target == null) {
                if (this.closeToEdges()) this.behavior = "CONTAIN";
                else this.behavior = "WANDER";
            }
            else this.behavior = "SEEK";
        }
        if (this instanceof Hare) {
            if (target == null) {
                if (this.closeToEdges()) this.behavior = "CONTAIN";
                else this.behavior = "WANDER";
            }
            else this.behavior = "FLEE";
        }
        if (this instanceof Deer) {
            if (target == null) {
                if (this.closeToEdges()) this.behavior = "CONTAIN";
                else this.behavior = "WANDER";
            }
            else this.behavior = "FLEE";
        }
    }

    private void rotateVector(Vector2d vector, double angle) {
        double length = vector.length();
        vector.x = Math.cos(angle) * length;
        vector.y = Math.sin(angle) * length;
    }
}
