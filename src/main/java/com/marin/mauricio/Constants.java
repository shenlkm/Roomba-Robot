package com.marin.mauricio;

import java.util.*;

public class Constants {
    public static final String EMPTY_CELL = "null";
    public static final String CLEAN_CELL = "C";
    public static final String TURN_LEFT = "TL";
    public static final String TURN_RIGTH = "TR";
    public static final String ADVANCE = "A";
    public static final String BACK = "B";
    public static final String CLEAN = "C";
    public static final String NORTH = "N";
    public static final String SOUTH = "S";
    public static final String EAST = "E";
    public static final String WEST = "O";
    public static final int LOWER_LIMIT = 0;
    static final List<String> ORIENTATION = new ArrayList<String>(Arrays.asList(NORTH,EAST,SOUTH,WEST));
    static final Map<String, Integer> BATTERY_USAGE = new HashMap(){{ put(TURN_LEFT, 1); put(TURN_RIGTH, 1); put(ADVANCE, 2); put(BACK, 3); put(CLEAN, 5);}};
}
