package com.marin.mauricio.repository;

import com.marin.mauricio.repository.data.Instruction;
import com.marin.mauricio.repository.data.Result;

public interface DataRepository{
    public Instruction readDataSource(String source);
    public void writeResult(String filename, Result result);

}