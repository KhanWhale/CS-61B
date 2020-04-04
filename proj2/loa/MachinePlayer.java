/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;

import static loa.Piece.*;

/** An automated Player.
 *  @author Aniruddh Khanwale
 */
class MachinePlayer extends Player {

    /** A position-score magnitude indicating a win (for white if positive,
     *  black if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new MachinePlayer with no piece or controller (intended to produce
     *  a template). */
    MachinePlayer() {
        this(null, null);
    }

    /** A MachinePlayer that plays the SIDE pieces in GAME. */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
    }

    @Override
    String getMove() {
        Move choice = searchForMove();
        assert side() == getGame().getBoard().turn();
        int depth;
        choice = searchForMove();
        getGame().reportMove(choice);
        return choice.toString();
    }

    @Override
    Player create(Piece piece, Game game) {
        return new MachinePlayer(piece, game);
    }

    @Override
    boolean isManual() {
        return false;
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private Move searchForMove() {
        Board work = new Board(getBoard());
        int value;
        assert side() == work.turn();
        _foundMove = null;
        if (side() == WP) {
            value = findMove(work, chooseDepth(), true, 1, -INFTY, INFTY);
        } else {
            value = findMove(work, chooseDepth(), true, -1, -INFTY, INFTY);
        }
        return _foundMove;
    }

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove. */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {
        if (depth == 0 || board.gameOver()) {
            return staticValuation();
        } else if (sense == 1) {
            Board boardCopy = new Board(board);
            int maxSquareVal = Integer.MIN_VALUE;
            Move bestMove = null;
            for (Square sq : Square.ALL_SQUARES) {
                ArrayList<Move> sqMoves = board.legalMoves().get(sq);
                int moveAlpha = alpha;
                int maxMoveVal = Integer.MIN_VALUE;
                Move bestSqMove = null;
                for (Move mv : sqMoves) {
                    boardCopy.makeMove(mv);
                    int eval = findMove(board, depth - 1, false, -1, alpha, beta);
                    boardCopy.retract();
                    maxMoveVal = Math.max(maxMoveVal, eval);
                    moveAlpha = Math.max(moveAlpha, eval);
                    if (eval == maxMoveVal) {
                        bestSqMove = mv;
                    }
                    if (beta <= moveAlpha) {
                        break;
                    }
                }
                maxSquareVal = Math.max(maxSquareVal, maxMoveVal);
                if (maxMoveVal == maxSquareVal) {
                    bestMove = bestSqMove;
                }
                alpha = Math.max(alpha,moveAlpha);
                if (beta <= alpha) {
                    break;
                }
            }
            if (saveMove && bestMove != null) {
                _foundMove = bestMove;

            }
            return maxSquareVal;
        } else {
            Board boardCopy = new Board(board);
            int minSquareVal = Integer.MAX_VALUE;
            Move worstMove = null;
            for (Square sq : Square.ALL_SQUARES) {
                ArrayList<Move> sqMoves = board.legalMoves().get(sq);
                int moveBeta = beta;
                int minMoveVal = Integer.MAX_VALUE;
                Move worstSqMove = null;
                for (Move mv : sqMoves) {
                    boardCopy.makeMove(mv);
                    int eval = findMove(board, depth - 1, false, 1, alpha, beta);
                    boardCopy.retract();
                    minMoveVal = Math.min(minMoveVal, eval);
                    moveBeta = Math.min(moveBeta, eval);
                    if (eval == minMoveVal) {
                        worstSqMove = mv;
                    }
                    if (moveBeta <= alpha) {
                        break;
                    }
                }
                minSquareVal = Math.min(minSquareVal, minMoveVal);
                if (minMoveVal == minSquareVal) {
                    worstMove = worstSqMove;
                }
                beta = Math.min(beta,moveBeta);
                if (beta <= alpha) {
                    break;
                }
            }
            if (saveMove && worstMove != null) {
                _foundMove = worstMove;
            }
            return minSquareVal;
        }
    }

    /** Return a search depth for the current position. */
    private int chooseDepth() {
        return 3;  // FIXME
    }

    // FIXME: Other methods, variables here.
    private int staticValuation() {
       int numOpponent = getBoard().getRegionSizes(side().opposite()).size();
        int numMine = getBoard().getRegionSizes(side()).size();
        if (getBoard().gameOver()) {
            if (getBoard().winner() == side()) {
               return Integer.MAX_VALUE;
            } else if (getBoard().winner() == side().opposite()){
                return Integer.MIN_VALUE;
            } else {
                return 0;
            }
        }
        return numOpponent - numMine;
    }
    /** Used to convey moves discovered by findMove. */
    private Move _foundMove;

}
