package com.thecodingwombat;

enum MoveDirection implements Direction {
    RIGHT{
        @Override
        public int getDestinationIndex(final int BOARD_SIZE) {
            return 1;
        }

        @Override
        public int getHorMoveDistance() {
            return 1;
        }

        @Override
        public int getVerMoveDistance() {
            return 0;
        }

        @Override
        public boolean isValidMove(int index, final int BOARD_SIZE) {
            return (index + 1) % BOARD_SIZE != 0; }

        @Override
        public int getStartValue(int numOfTiles, int BOARD_SIZE) {
            return numOfTiles - 1;
        }

        @Override
        public boolean hasNext(int loopIndex, int numOfTiles) {
            return loopIndex >= 0;
        }

        @Override
        public int getNext(int loopIndex) {
            return --loopIndex;
        }
    },
    LEFT{
        @Override
        public int getDestinationIndex(final int BOARD_SIZE) {
            return -1;
        }

        @Override
        public int getHorMoveDistance() {
            return -1;
        }

        @Override
        public int getVerMoveDistance() {
            return 0;
        }

        @Override
        public boolean isValidMove(int index, int BOARD_SIZE) {
            return index % BOARD_SIZE != 0;
        }

        @Override
        public int getStartValue(int numOfTiles, int BOARD_SIZE) {
            return 1;
        }

        @Override
        public boolean hasNext(int loopIndex, int numOfTiles) {
            return loopIndex < numOfTiles;
        }

        @Override
        public int getNext(int loopIndex) {
            return ++loopIndex;
        }
    },
    UP{
        @Override
        public int getDestinationIndex(final int BOARD_SIZE) {
            return BOARD_SIZE;
        }

        @Override
        public int getHorMoveDistance() {
            return 0;
        }

        @Override
        public int getVerMoveDistance() {
            return 1;
        }

        @Override
        public boolean isValidMove(int index, int BOARD_SIZE) {
            return true;
        }

        @Override
        public int getStartValue(int numOfTiles, int BOARD_SIZE) {
            return numOfTiles - BOARD_SIZE - 1;
        }

        @Override
        public boolean hasNext(int loopIndex, int numOfTiles) {
            return loopIndex >= 0 ;
        }

        @Override
        public int getNext(int loopIndex) {
            return --loopIndex;
        }
    },
    DOWN{
        @Override
        public int getDestinationIndex(final int BOARD_SIZE) {
            return -BOARD_SIZE;
        }

        @Override
        public int getHorMoveDistance() {
            return 0;
        }

        @Override
        public int getVerMoveDistance() {
            return -1;
        }

        @Override
        public boolean isValidMove(int index, int BOARD_SIZE) {
            return true;
        }

        @Override
        public int getStartValue(int numOfTiles, int BOARD_SIZE) {
            return BOARD_SIZE;
        }

        @Override
        public boolean hasNext(int loopIndex, int numOfTiles) {
            return loopIndex < numOfTiles ;
        }

        @Override
        public int getNext(int loopIndex) {
            return ++loopIndex;
        }
    }
}