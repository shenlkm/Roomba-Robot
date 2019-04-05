package com.marin.mauricio.repository;

import com.google.gson.Gson;
import com.marin.mauricio.repository.data.Instruction;
import com.marin.mauricio.repository.data.Result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataRepositoryFileImpl implements DataRepository{

    @Override
    public Instruction readDataSource(String source) {

        Gson gson = new Gson();
        try{
            BufferedReader bufferedReader = new BufferedReader( new FileReader(source));
            Instruction instruction = gson.fromJson(bufferedReader, Instruction.class);
            return instruction;
        }
        catch (IOException e){
            System.out.println("File not found");
        }
        return null;
    }

    @Override
    public void writeResult(String filename, Result result) {
        Gson gson = new Gson();

        try {
            File file = new File(filename);
            String data = gson.toJson(result);
            Files.write(Paths.get(file.getPath()), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}