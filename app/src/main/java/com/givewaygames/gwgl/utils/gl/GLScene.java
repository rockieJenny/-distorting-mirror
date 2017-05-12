package com.givewaygames.gwgl.utils.gl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GLScene extends ArrayList<GLPiece> {
    private static final long serialVersionUID = 46564399842270480L;

    public GLPiece getInstanceOf(Class<? extends GLPiece> type, int idx) {
        int i = 0;
        Iterator<GLPiece> it = iterator();
        while (it.hasNext()) {
            GLPiece piece = (GLPiece) it.next();
            if (type.isInstance(piece)) {
                if (i == idx) {
                    return piece;
                }
                i++;
            }
        }
        return null;
    }

    public List<GLPiece> getAllInstancesOf(Class<? extends GLPiece> type) {
        List<GLPiece> pieces = new ArrayList();
        Iterator<GLPiece> it = iterator();
        while (it.hasNext()) {
            GLPiece piece = (GLPiece) it.next();
            if (type.isInstance(piece)) {
                pieces.add(piece);
            }
        }
        return pieces;
    }
}
