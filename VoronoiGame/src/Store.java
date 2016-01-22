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
    public float x;
    public float y;

    //weight, once we add the feature
    private float weight;

    public int owner; //player 0 or player 1

    public Store(float x, float y, int owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
