package rta.phatnguyen.assignment4.Instance;

import java.util.Random;

/**
 * Created by entipi on 4/25/17.
 */

public class Cookie {
    public static int maxCookie = 10;
    public static int minCookie = 1;
    Random rand = new Random();
    public int cookie;

    public Cookie(int cookie) {
        this.cookie = cookie;
    }


    public int getCookie() {
        return cookie;
    }

    public void setCookie(int cookie) {
        this.cookie = cookie;
    }

    public int eatCookie(){
        int amount = rand.nextInt(cookie + 1);
        cookie = cookie - amount;
        return amount;
    }


    public void bakeCookie(){
        int amount = rand.nextInt((maxCookie - minCookie) + 1) + minCookie;
        cookie = cookie + amount;

    }

}
