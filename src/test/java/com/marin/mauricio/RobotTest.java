package com.marin.mauricio;


import com.google.gson.Gson;
import com.marin.mauricio.repository.DataRepository;
import com.marin.mauricio.repository.DataRepositoryFileImpl;
import com.marin.mauricio.repository.data.Point;
import com.marin.mauricio.repository.data.Position;
import com.marin.mauricio.repository.data.Result;
import javafx.geometry.Pos;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.marin.mauricio.repository.data.Instruction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class RobotTest {
    private static List<List<String>> bosCommands;
    private static DataRepository repositoryFile;

    @BeforeClass
    public static void setup(){
        repositoryFile =  new DataRepositoryFileImpl();
        bosCommands = new ArrayList<>();
        bosCommands.add(Arrays.asList("TR","A"));
        bosCommands.add(Arrays.asList("TL","B","TR","A"));
        bosCommands.add(Arrays.asList("TL","TL","A"));
        bosCommands.add(Arrays.asList("TR","B","TR","A"));
        bosCommands.add(Arrays.asList("TL","TL","A"));
    }

    public static Instruction getInstruction(String path){
        return repositoryFile.readDataSource(path);
    }

    @Test
    public void faceEastWhenTurnLeftThenFaceNorth(){
        Position current = new Position(0,0);
        current.setFacing("E");
        Instruction instruction = getInstruction("res/test.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("TL");

        Assert.assertEquals("N" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(24 , robot.getBattery());
    }

    @Test
    public void faceWestWhenTurnRightThenFaceNorth(){
        Position current = new Position(0,0);
        current.setFacing("O");
        Instruction instruction = getInstruction("res/test.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("TR");

        Assert.assertEquals("N" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(24 , robot.getBattery());
    }

    @Test
    public void onACellWhenCleanThenIsAddToList(){
        Position current = new Position(0,0);
        current.setFacing("O");
        Instruction instruction = getInstruction("res/test.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("C");

        boolean cleaned = false;
        for (Point cell : robot.getResult().getCleaned()) {
            cleaned = cell.compareTo(current) == 0;
        }
        Assert.assertTrue(cleaned);
    }

    @Test
    public void faceEstOnZeroZeroWhenAdvanceThenMoveWest(){
        Position current = new Position(0,0);
        current.setFacing("E");
        Instruction instruction = getInstruction("res/test.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("E" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(0 , robot.getCurrentPosition().getY());
        Assert.assertEquals(1 , robot.getCurrentPosition().getX());
        Assert.assertEquals(23 , robot.getBattery());
    }

    @Test
    public void faceWestOnZeroTwoWhenAdvanceThenExecuteBOSStepTwo(){
        Position current = new Position(0,2);
        current.setFacing("O");
        Instruction instruction = getInstruction("res/test.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("N" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(1 , robot.getCurrentPosition().getY());
        Assert.assertEquals(1 , robot.getCurrentPosition().getX());
        Assert.assertEquals(13 , robot.getBattery());
    }

    @Test
    public void faceWestOnZeroZeroWhenAdvanceThenExecuteBOSStepThree(){
        Position current = new Position(0,0);
        current.setFacing("O");
        Instruction instruction = getInstruction("res/test.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("S" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(1 , robot.getCurrentPosition().getY());
        Assert.assertEquals(1 , robot.getCurrentPosition().getX());
        Assert.assertEquals(9 , robot.getBattery());
    }


    @Test
    public void faceWestOnZeroOneWhenAdvanceThenExecuteBOSStepFour(){
        Position current = new Position(0,1);
        current.setFacing("O");
        Instruction instruction = getInstruction("res/test2.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("N" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(0 , robot.getCurrentPosition().getY());
        Assert.assertEquals(2 , robot.getCurrentPosition().getX());
        Assert.assertEquals(2 , robot.getBattery());
    }

    @Test
    public void faceWestOnZeroZeroWhenAdvanceThenExecuteBOSStepFive(){
        Position current = new Position(0,0);
        current.setFacing("O");
        Instruction instruction = getInstruction("res/test3.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("S" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(1 , robot.getCurrentPosition().getY());
        Assert.assertEquals(2 , robot.getCurrentPosition().getX());
        Assert.assertEquals(73 , robot.getBattery());
    }

    @Test
    public void faceWestOnZeroThreeWhenAdvanceThenExecuteAllTheBOS(){
        Position current = new Position(0,3);
        current.setFacing("O");
        Instruction instruction = getInstruction("res/test3.json");
        instruction.setStart(current);
        Robot robot = new Robot(instruction, bosCommands);

        robot.executeCommand("A");

        Assert.assertEquals("S" , robot.getCurrentPosition().getFacing());
        Assert.assertEquals(3 , robot.getCurrentPosition().getY());
        Assert.assertEquals(2 , robot.getCurrentPosition().getX());
        Assert.assertEquals(73 , robot.getBattery());
    }

    @Test
    public void faceSouthOnThreeOneWhenExecuteCommandsThenCompleteuccessful(){
        Instruction instruction = getInstruction("res/testMyQ.json");
        Robot robot = new Robot(instruction, bosCommands);
        Result result = getFullResult();

        robot.start();

        Assert.assertEquals("E" , robot.getResult().getEnd().getFacing());
        Assert.assertEquals(2 , robot.getResult().getEnd().getY());
        Assert.assertEquals(3 , robot.getResult().getEnd().getX());

        Assert.assertEquals(result.getVisited().size() , robot.getResult().getVisited().size());
        Assert.assertEquals(result.getCleaned().size() , robot.getResult().getCleaned().size());
        Assert.assertEquals(1040 , robot.getBattery());
    }

    private Result getFullResult() {
        Result result = new Result();
        result.setBattery(1040);
        Position end = new Position(3,2);
        end.setFacing("E");
        result.setEnd(end);
        result.setVisited(new ArrayList<Point>(
                Arrays.asList(
                        new Point(2,2),
                        new Point(3,0),
                        new Point(3,1),
                        new  Point(3,2))));
        result.setCleaned(new ArrayList<Point>(
                Arrays.asList(
                        new Point(2,2),
                        new Point(3,0),
                        new  Point(3,2))));
        return result;
    }
}
