package com.thecodingwombat;

interface Direction {
    int getDestinationIndex(final int BOARD_SIZE);
    int getHorMoveDistance();
    int getVerMoveDistance();
    int getStartValue(int numOfTiles, final int BOARD_SIZE);
    boolean hasNext(int loopIndex, int numOfTiles);
    int getNext(int loopIndex);
    boolean isValidMove(int index, final int BOARD_SIZE);
}