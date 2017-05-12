package com.givewaygames.gwgl.utils.gl;

public class GLBlockUntilTrue extends GLPiece {
    ITrue truth;

    public interface ITrue {
        void draw(int i, long j);

        boolean isTrue();
    }

    public static class GLPieceInitialized implements ITrue {
        GLPiece piece;

        public GLPieceInitialized(GLPiece piece) {
            this.piece = piece;
        }

        public boolean isTrue() {
            return this.piece.isInitialized();
        }

        public void draw(int pass, long time) {
        }
    }

    public GLBlockUntilTrue(ITrue truth) {
        this.truth = truth;
    }

    public boolean blockNextPieces() {
        return !this.truth.isTrue();
    }

    public boolean onInitialize() {
        return true;
    }

    public boolean draw(int pass, long time) {
        if (!this.truth.isTrue()) {
            this.truth.draw(pass, time);
        }
        return true;
    }

    public int getNumPasses() {
        return 0;
    }
}
