package com.marin.mauricio.repository;

import com.marin.mauricio.repository.data.Instruction;
import com.marin.mauricio.repository.data.Result;

import java.util.List;

public interface DataRepository{
    public Instruction readDataSource(String source);
    public boolean checkCommands(List<String> commands);
    public void writeResult(String filename, Result result);

}