package javaSocket;

import java.util.Random;

public class FailingModel {
    public static boolean IsFailed()
    {
        Random rand = new Random();

        // Obtain a number between [0 - 49].
        int n = rand.nextInt(80);
        return n<=1;
    }
}
