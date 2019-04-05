package com.marin.mauricio;

import com.marin.mauricio.repository.data.Instruction;
import com.marin.mauricio.repository.data.Point;
import com.marin.mauricio.repository.data.Position;
import com.marin.mauricio.repository.data.Result;
import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Robot {

    private List<List<String>> BOSCommands;
    private List<String[]> map;
    private List<String> commands;

    private Position currentPosition;
    private Result result;

    private int battery;
    private int BOSIteration;

    private static int limitOnY;
    private static int limitOnX;

    public Robot(Instruction instruction, List<List<String>> bossStrategy){
        initialize(instruction);
        setUpBOS(bossStrategy);
    }

    public void start(){
        execute(commands);
        List<Point> visited = result.getVisited().stream().filter(distinctByKey(Point::getKey)).sorted().collect(Collectors.toList());
        List<Point> cleaned = result.getCleaned().stream().filter(distinctByKey(Point::getKey)).sorted().collect(Collectors.toList());
        result.setVisited(visited);
        result.setCleaned(cleaned);
        result.setEnd(currentPosition);
        result.setBattery(battery);
    }

    public Result getResult() {
        return result;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public int getBattery() {
        return battery;
    }

    private void setUpBOS(List<List<String>> bossStrategy) {
        BOSIteration = 0;
        BOSCommands = bossStrategy;
    }

    private void initialize(Instruction instruction) {
        result = new Result();
        commands = instruction.getCommands();
        map = instruction.getMap();
        limitOnY = map.size();
        limitOnX = map.get(Constants.LOWER_LIMIT).length;
        currentPosition = instruction.getStart();
        battery = instruction.getBattery();
        result.getVisited().add(currentPosition.getPoint());
    }

    public void execute(List<String> commandList){
        int index = 0;
        while (index < commandList.size() && battery >= Constants.BATTERY_USAGE.get(commandList.get(index))){
            executeCommand(commandList.get(index));
            index++;
        }
    }

    public void executeCommand(@NotNull String command)
    {
        switch (command){
            case Constants.TURN_RIGTH:
            case Constants.TURN_LEFT:
                turn(command);
                break;
            case Constants.ADVANCE:
                advance();
                break;
            case Constants.BACK:
                move(false, 3);
                break;
            case Constants.CLEAN:
                clean();
                break;
        }
    }

    private void clean() {
        result.getCleaned().add(currentPosition.getPoint());
        reduceBatter(5);
    }

    private void advance() {
        move(true, 2);
        BOSIteration = 0;
    }

    private void move(boolean b, int i) {
        tryToMove(b);
        reduceBatter(i);
    }

    private void turn(@NotNull String command) {
        currentPosition.setFacing(getNewFacing(command));
        reduceBatter(1);
    }

    private void reduceBatter(int x){
        battery=battery-x;
    }

    private void tryToMove(boolean forward) {
        int direction = forward ? 1 : -1;
        int currentX = currentPosition.getX();
        int currentY = currentPosition.getY();
        int newX;
        int newY;
        switch (currentPosition.getFacing()){
            case Constants.NORTH:
                newY = currentY - direction;
                moveOnY(currentX, newY < Constants.LOWER_LIMIT, newY);
                break;
            case Constants.SOUTH:
                newY = currentY + direction;
                moveOnY(currentX, newY >= limitOnY, newY);
                break;
            case Constants.EAST:
                newX = currentX + direction;
                moveOnX(currentY, newX >= limitOnX, newX);
                break;
            case Constants.WEST:
                newX = currentX - direction;
                moveOnX(currentY, newX < Constants.LOWER_LIMIT, newX);
                break;
        }
        result.getVisited().add(currentPosition.getPoint());
    }

    private void moveOnY(int currentX, boolean firstCondition, int i) {
        if (firstCondition || isObstacle(currentX, i)) {
            backOffStrategy();
        } else {
            currentPosition.setY(i);
        }
    }

    private void moveOnX(int currentY, boolean firstCondition, int i) {
        if (firstCondition || isObstacle(i, currentY)) {
            backOffStrategy();
        } else {
            currentPosition.setX(i);
        }
    }

    private boolean isObstacle(int x, int y){
        if(y < limitOnY && y >= Constants.LOWER_LIMIT && x < limitOnX  && x >= Constants.LOWER_LIMIT){
            String cell = map.get(y)[x];
            return Constants.EMPTY_CELL.equals(cell) || Constants.CLEAN_CELL.equals(cell);
        }
        return true;
    }

    private void backOffStrategy() {
        if(BOSIteration < BOSCommands.size()){
            BOSIteration++;
            execute(BOSCommands.get(BOSIteration -1));
        }
    }

    private String getNewFacing(String command) {
        int newIndex = Constants.LOWER_LIMIT;
        int index = Constants.ORIENTATION.indexOf(currentPosition.getFacing());
        if(Constants.TURN_LEFT.equals(command)){
            newIndex = (index-1 < Constants.LOWER_LIMIT) ? 3 : index-1;
        } else if(Constants.TURN_RIGTH.equals(command)){
            newIndex = (index+1 > 3) ? Constants.LOWER_LIMIT : index+1;
        }
        return Constants.ORIENTATION.get(newIndex);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}