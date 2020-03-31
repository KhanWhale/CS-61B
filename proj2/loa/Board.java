/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;


import java.util.*;
import java.util.regex.Pattern;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 *  @author Aniruddh Khanwale
 */
class Board {

    /** Default number of moves for each side that results in a draw. */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row][col]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        int boardIndex = 0;
        for (int i = 0; i < contents.length; i += 1) {
            for (int j = 0; j < contents[i].length; j += 1) {
                _board[boardIndex] = contents[i][j];
                boardIndex += 1;
            }
        }
        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;
    }


    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        } else {
            for (int i = 0; i < this._board.length; i += 1) {
                this._board[i] = board._board[i];
            }
            this._moves.addAll(board._moves);
            this._turn = board._turn;
            this._moveLimit = board._moveLimit;
            this._winnerKnown = board._winnerKnown;
            if (_winnerKnown) {
                this._winner = board._winner;
            }
            this._subsetsInitialized = board._subsetsInitialized;
            this._whiteRegionSizes.addAll(board._whiteRegionSizes);
            this._blackRegionSizes.addAll(board._blackRegionSizes);
        }
    }

    /** Return the contents of the square at SQ. */
    Piece get(Square sq) {
        int index = sq.index();
        return _board[index];
    }

    /** Set the square at SQ to V and set the side that is to move next
     *  to NEXT, if NEXT is not null. */
    void set(Square sq, Piece v, Piece next) {
        _board[sq.index()] = v;
        if (next != null) {
            _turn = next;
        }
    }

    /** Set the square at SQ to V, without modifying the side that
     *  moves next. */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /** Set limit on number of moves by each side that results in a tie to
     *  LIMIT, where 2 * LIMIT > movesMade(). */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /** Assuming isLegal(MOVE), make MOVE. This function assumes that
     *  MOVE.isCapture() will return false.  If it saves the move for
     *  later retraction, makeMove itself uses MOVE.captureMove() to produce
     *  the capturing move. */
    void makeMove(Move move) {
        assert isLegal(move);
        if (!move.isCapture()) {
            if (get(move.getTo()) == get(move.getFrom()).opposite()) {
                makeMove(move.captureMove());
                return;
            } else {
                set(move.getTo(), get(move.getFrom()), _turn.opposite());
                set(move.getFrom(), EMP);
                _moves.add(move);
                _subsetsInitialized = false;
            }
        } else {
            set(move.getTo(), get(move.getFrom()), _turn.opposite());
            set(move.getFrom(), EMP);
            _moves.add(move);
            _subsetsInitialized = false;
        }
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        assert movesMade() > 0;
        Move toRetract = _moves.remove(_moves.size() - 1);
        if (toRetract.isCapture()) {
            set(toRetract.getFrom(), get(toRetract.getTo()), _turn.opposite());
            set(toRetract.getTo(), get(toRetract.getFrom()).opposite());
        } else {
            set(toRetract.getFrom(), get(toRetract.getTo()), _turn.opposite());
            set(toRetract.getTo(), EMP);
        }
        _subsetsInitialized = false;
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff FROM - TO is a legal move for the player currently on
     *  move. */
    boolean isLegal(Square from, Square to) {
        if (get(from) != turn()) {
            return false;
        } else if (!from.isValidMove(to)) {
            return false;
        } else {
            int dir = from.direction(to);
            int piecesInLine = -1;
            for (Square mySq = from; mySq != null;
                 mySq = mySq.moveDest(dir, 1)) {
                if ((get(mySq) == BP) || (get(mySq)  == WP)) {
                    piecesInLine += 1;
                }
            }
            dir = (dir + 4) % 8;
            for (Square mySq = from; mySq != null;
                 mySq = mySq.moveDest(dir, 1)) {
                if ((get(mySq) == BP) || (get(mySq)  == WP)) {
                    piecesInLine += 1;
                }
            }
            if (from.distance(to) != piecesInLine) {
                return false;
            }
        }
        return !blocked(from, to);
    }

    /** Return true iff MOVE is legal for the player currently on move.
     *  The isCapture() property is ignored. */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo());
    }

    /** Return a sequence of all legal moves from this position. */
    HashMap<Square, ArrayList<Move>> legalMoves() {
        HashMap<Square, ArrayList<Move>> legalMoves = new HashMap<Square, ArrayList<Move>>();
        for (Square sq : ALL_SQUARES) {
            ArrayList<Move> sqMoves = new ArrayList<Move>();
            for (Square dest : ALL_SQUARES) {
                if (sq.isValidMove(dest) && isLegal(sq, dest)) {
                    sqMoves.add(Move.mv(sq, dest));
                }
            }
        }
        return legalMoves;
    }

    /** Return true iff the game is over (either player has all his
     *  pieces continguous or there is a tie). */
    boolean gameOver() {
        return winner() != null;
    }

    /** Return true iff SIDE's pieces are continguous. */
    boolean piecesContiguous(Piece side) {
        return getRegionSizes(side).size() == 1;
    }

    /** Return the winning side, if any.  If the game is not over, result is
     *  null.  If the game has ended in a tie, returns EMP. */
    Piece winner() {
        if (_winnerKnown) {
            return _winner;
        } else {
            if (piecesContiguous(BP) && piecesContiguous(WP)) {
                _winnerKnown = true;
                _winner = _turn.opposite();
                return _winner;
            } else if (piecesContiguous(WP)) {
                _winnerKnown = true;
                _winner = WP;
                return _winner;
            } else if (piecesContiguous(BP)) {
                _winnerKnown = true;
                _winner = BP;
                return _winner;
            } else if (movesMade() == _moveLimit) {
                _winnerKnown = true;
                _winner = EMP;
                return _winner;
            } else {
                return null;
            }
        }
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                Square mySq = sq(c, r);
                Piece myPiece = get(mySq);
                out.format("%s ", myPiece.abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return true if a move from FROM to TO is blocked by an opposing
     *  piece or by a friendly piece on the target square. */
    private boolean blocked(Square from, Square to) {
        int dir = from.direction(to);
        Piece myPiece = get(from);
        Square mySq = from;
        int steps = from.distance(to);
        while (steps != 0) {
            mySq = mySq.moveDest(dir, 1);
            if (mySq != null) {
                steps -= 1;
                if (steps == 0) {
                    return get(mySq) == myPiece;
                } else if (get(mySq) == myPiece.opposite()) {
                    return true;
                }
            } else {
                break;
            }
        }
        return mySq != null;
    }

    /** Return the size of the as-yet unvisited cluster of squares
     *  containing P at and adjacent to SQ.  VISITED indicates squares that
     *  have already been processed or are in different clusters.  Update
     *  VISITED to reflect squares counted. */
    private int numContig(Square sq, boolean[][] visited, Piece p) {
        if (visited[sq.row()][sq.col()]) {
            return 0;
        } else {
            Stack<Square> toProcess = new Stack<>();
            int num =  1;
            Square[] adj = sq.adjacent();
            for (int i = 0; i < adj.length; i += 1) {
                if (get(adj[i]) == p) {
                    if (!visited[adj[i].row()][adj[i].col()]) {
                        toProcess.push(adj[i]);
                    }
                }
            }
            visited[sq.row()][sq.col()] = true;
            while (!toProcess.empty()) {
                num += numContig(toProcess.pop(), visited, p);
            }
            return num;
        }
    }


    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();
        boolean [][] whiteVisited = new boolean[BOARD_SIZE][BOARD_SIZE];
        boolean [][] blackVisited = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i += 1) {
            for (int j = 0; j < BOARD_SIZE; j += 1) {
                Square mySq = sq(j, i);
                if (get(mySq) == WP && !whiteVisited[i][j]) {
                    _whiteRegionSizes.add(
                            numContig(mySq, whiteVisited, WP));
                    blackVisited[i][j] = true;
                } else if (get(mySq) == BP && !blackVisited[i][j]) {
                    _blackRegionSizes.add(
                            numContig(mySq, blackVisited, BP));
                    whiteVisited[i][j] = true;
                } else {
                    whiteVisited[i][j] = true;
                    blackVisited[i][j] = true;
                }
            }

        }
        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = true;
    }

    /** Return the sizes of all the regions in the current union-find
     *  structure for side S. */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }

    /** The standard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** Current contents of the board.  Square S is at _board[S.index()]. */
    private final Piece[] _board = new Piece[BOARD_SIZE  * BOARD_SIZE];

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** Limit on number of moves before tie is declared.  */
    private int _moveLimit;
    /** True iff the value of _winner is known to be valid. */
    private boolean _winnerKnown;
    /** Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     *  in progress).  Use only if _winnerKnown. */
    private Piece _winner;

    /** True iff subsets computation is up-to-date. */
    private boolean _subsetsInitialized;

    /** List of the sizes of contiguous clusters of pieces, by color. */
    private final ArrayList<Integer>
        _whiteRegionSizes = new ArrayList<>(),
        _blackRegionSizes = new ArrayList<>();

}
