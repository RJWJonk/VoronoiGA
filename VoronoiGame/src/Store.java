
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s080440
 */
public class Store {

    //coordinates of store
    public double x;
    public double y;

    //weight, once we add the feature
    private double weight;

    public int owner; //player 0 or player 1

    public Store(double x, double y, int owner) {
        Random random = new Random();
        this.x = x + (random.nextDouble()-0.5f)/100;
        this.y = y + (random.nextDouble()-0.5f)/100;
        this.owner = owner;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
