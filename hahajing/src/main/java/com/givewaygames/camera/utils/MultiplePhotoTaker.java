package com.givewaygames.camera.utils;

import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import java.util.ArrayList;

public abstract class MultiplePhotoTaker {
    int currentIdx = -1;

    public static class IntegerMultiplePhotoTaker extends MultiplePhotoTaker {
        ArrayList<Integer> filtersToUse;

        public IntegerMultiplePhotoTaker(ArrayList<Integer> filters) {
            this.filtersToUse = new ArrayList(filters);
        }

        public boolean isRunning() {
            return this.currentIdx >= 0 && this.filtersToUse != null && this.currentIdx < this.filtersToUse.size();
        }

        public int getNextProgram(int currentIdx) {
            if (isRunning()) {
                return ((Integer) this.filtersToUse.get(currentIdx)).intValue();
            }
            return -1;
        }

        public void start(CameraFilterActivity cfa, ArrayList<Integer> filtersToUse) {
            this.filtersToUse = filtersToUse;
            start(cfa);
        }
    }

    public static class ProgramMultiplePhotoTaker extends MultiplePhotoTaker {
        ArrayList<GLProgram> programsToUse;

        public boolean isRunning() {
            return this.currentIdx >= 0 && this.programsToUse != null && this.currentIdx < this.programsToUse.size();
        }

        public int getNextProgram(int currentIdx) {
            if (isRunning()) {
                return ((GLProgram) this.programsToUse.get(currentIdx)).getTag();
            }
            return -1;
        }

        public void start(CameraFilterActivity cfa, ArrayList<GLProgram> programsToUse) {
            this.programsToUse = programsToUse;
            start(cfa);
        }
    }

    public abstract int getNextProgram(int i);

    public abstract boolean isRunning();

    public void start(CameraFilterActivity cfa) {
        this.currentIdx = 0;
        onPhotoTaken(cfa);
    }

    public void onPhotoTaken(CameraFilterActivity cfa) {
        int program = getNextProgram(this.currentIdx);
        if (program >= 0) {
            cfa.takePhotoOfFilter(program);
            this.currentIdx++;
        }
    }
}
