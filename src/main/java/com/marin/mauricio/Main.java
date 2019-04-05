package com.marin.mauricio;

import com.google.gson.Gson;
import com.marin.mauricio.repository.DataRepository;
import com.marin.mauricio.repository.DataRepositoryFileImpl;
import com.marin.mauricio.repository.data.Instruction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        if(args.length < 2){
            System.out.println(String.format("Roomba Robot arguments expected 2, argument given %s", args.length));
            System.exit(0);
        }

        DataRepository repository = new DataRepositoryFileImpl();
        Instruction instruction = repository.readDataSource(args[0]);


        List<List<String>> bosCommands = new ArrayList<>();
        bosCommands.add(Arrays.asList("TR","A"));
        bosCommands.add(Arrays.asList("TL","B","TR","A"));
        bosCommands.add(Arrays.asList("TL","TL","A"));
        bosCommands.add(Arrays.asList("TR","B","TR","A"));
        bosCommands.add(Arrays.asList("TL","TL","A"));
        Robot robot = new Robot(instruction, bosCommands);
        robot.start();

        repository.writeResult(args[1], robot.getResult());
    }
}
