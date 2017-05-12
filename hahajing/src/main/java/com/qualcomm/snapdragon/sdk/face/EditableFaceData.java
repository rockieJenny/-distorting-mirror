package com.qualcomm.snapdragon.sdk.face;

public class EditableFaceData extends FaceData {
    public EditableFaceData(boolean isMirrored, int rotationAngle) {
        initializeFaceObj(isMirrored, rotationAngle);
    }

    public void setBlinkValues(int leftEyeBlink, int rightEyeBlink) {
        super.setBlinkValues(leftEyeBlink, rightEyeBlink);
    }

    public void setEyeGazeAngles(int hGazeAngle, int vGazeAngle) {
        super.setEyeGazeAngles(hGazeAngle, vGazeAngle);
    }

    public void setPitch(int pitch) {
        super.setPitch(pitch);
    }

    public void setRoll(int roll) {
        super.setRoll(roll);
    }

    public void setSmileValue(int smile) {
        super.setSmileValue(smile);
    }

    public void setYaw(int yaw) {
        super.setYaw(yaw);
    }
}
