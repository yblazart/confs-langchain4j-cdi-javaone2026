package org.acme;

import org.wildfly.mcp.api.Tool;
import org.wildfly.mcp.api.ToolArg;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

public class DiceRoller {

    private static Logger logger = Logger.getLogger(DiceRoller.class.getName());

    @Tool(description = "Roll a number of dices and return the results")
    String roll(@ToolArg(description = "The number of dice") int numberOfDice) {
        logger.info("Dice rolled: " + numberOfDice + " dice ");
        int[] result = new int[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            result[i] = new Random().nextInt(1, 7);
            logger.info("Dice: " + i + " give " + result[i]);
        }
        return Arrays.toString(result);
    }
}