package com.marin.mauricio;


import com.marin.mauricio.repository.DataRepository;
import com.marin.mauricio.repository.DataRepositoryFileImpl;
import com.marin.mauricio.repository.data.Position;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.marin.mauricio.repository.data.Instruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RobotTest {

    private static Instruction instruction;
    private static List<List<String>> bosCommands;

    @BeforeClass
    public static void setup(){
        DataRepository repositoryFile =  new DataRepositoryFileImpl();
        instruction = repositoryFile.readDataSource("res/test.json");
        bosCommands = new ArrayList<>();
        bosCommands.add(Arrays.asList("TR","A"));
        bosCommands.add(Arrays.asList("TL","B","TR","A"));
        bosCommands.add(Arrays.asList("TL","TL","A"));
        bosCommands.add(Arrays.asList("TR","B","TR","A"));
        bosCommands.add(Arrays.asList("TL","TL","A"));
    }

    @Test
    public void faceEastWhenTurnLeftThenFaceNorth(){
        Position current = new Position(0,0);
        current.setFacing("E");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("TL");

        Assert.assertEquals("N" , robot.getCurrentPosition().getFacing());
    }

    @Test
    public void faceWestWhenTurnRightThenFaceNorth(){
        Position current = new Position(0,0);
        current.setFacing("O");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("TR");

        Assert.assertEquals("N" , robot.getCurrentPosition().getFacing());
    }

    @Test
    public void faceEstOnZeroZeroWhenAdvanceThenMoveWest(){
        Position current = new Position(0,0);
        current.setFacing("E");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("E" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(0 , robot.getCurrentPosition().getY());
        Assert.assertEquals(1 , robot.getCurrentPosition().getX());
    }

    @Test
    public void faceWestOnZeroTwoWhenAdvanceExecuteBOSStepTwo(){
        Position current = new Position(0,2);
        current.setFacing("O");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("N" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(1 , robot.getCurrentPosition().getY());
        Assert.assertEquals(1 , robot.getCurrentPosition().getX());
    }

    @Test
    public void faceWestOnZeroZeroWhenAdvanceExecuteBOSStepThree(){
        Position current = new Position(0,0);
        current.setFacing("O");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("S" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(1 , robot.getCurrentPosition().getY());
        Assert.assertEquals(1 , robot.getCurrentPosition().getX());
    }
}
