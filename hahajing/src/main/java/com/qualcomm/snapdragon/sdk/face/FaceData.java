package com.qualcomm.snapdragon.sdk.face;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import com.google.ads.AdSize;

public class FaceData {
    private static final String TAG = "QCFace";
    private final int NOT_PROCESSED = FacialProcessingConstants.FP_NOT_PROCESSED;
    public Chin chin;
    private int eyeHorizontalGazeAngle;
    private int eyeVerticalGazeAngle;
    private boolean isMirrored = false;
    private int lEyeBlink;
    public Ears leftEar;
    public Point leftEye;
    public Eyes leftEyeObj;
    public Eyebrows leftEyebrow;
    public Point mouth;
    public Mouth mouthObj;
    public Nose nose;
    private int personId;
    private int pitch;
    private int rEyeBlink;
    private int recognitionConfidenceValue;
    public Rect rect;
    public Ears rightEar;
    public Point rightEye;
    public Eyes rightEyeObj;
    public Eyebrows rightEyebrow;
    private int roll;
    private int rotationAngleDegrees = 0;
    private final int[][] sinCosValues;
    private int smileDegree;
    private int yaw;

    public class Chin {
        public Point center;
        public Point left;
        public Point right;

        Chin(Point mLeft, Point mRight, Point mCenter) {
            this.left = mLeft;
            this.right = mRight;
            this.center = mCenter;
        }
    }

    public class Ears {
        public Point bottom;
        public Point top;

        Ears(Point mTop, Point mBottom) {
            this.top = mTop;
            this.bottom = mBottom;
        }
    }

    public class Eyebrows {
        public Point bottom;
        public Point left;
        public Point right;
        public Point top;

        Eyebrows(Point mTop, Point mBottom, Point mLeft, Point mRight) {
            this.top = mTop;
            this.bottom = mBottom;
            this.left = mLeft;
            this.right = mRight;
        }
    }

    public class Eyes {
        public Point bottom;
        public Point centerPupil;
        public Point left;
        public Point right;
        public Point top;

        Eyes(Point mLeft, Point mRight, Point mTop, Point mBottom, Point mCenterPupil) {
            this.left = mLeft;
            this.right = mRight;
            this.top = mTop;
            this.bottom = mBottom;
            this.centerPupil = mCenterPupil;
        }
    }

    public class Mouth {
        public Point left;
        public Point lowerLipBottom;
        public Point lowerLipTop;
        public Point right;
        public Point upperLipBottom;
        public Point upperLipTop;

        Mouth(Point mLeft, Point mRight, Point mUpperLipTop, Point mUpperLipBottom, Point mLowerLipTop, Point mLowerLipBottom) {
            this.left = mLeft;
            this.right = mRight;
            this.upperLipTop = mUpperLipTop;
            this.upperLipBottom = mUpperLipBottom;
            this.lowerLipTop = mLowerLipTop;
            this.lowerLipBottom = mLowerLipBottom;
        }
    }

    public class Nose {
        public Point noseBridge;
        public Point noseCenter;
        public Point noseLowerLeft;
        public Point noseLowerRight;
        public Point noseMiddleLeft;
        public Point noseMiddleRight;
        public Point noseTip;
        public Point noseUpperLeft;
        public Point noseUpperRight;

        Nose(Point mNoseBridge, Point mNoseCenter, Point mNoseTip, Point mNoseLowerLeft, Point mNoseLowerRight, Point mNoseMiddleLeft, Point mNoseMiddleRight, Point mNoseUpperLeft, Point mNoseUpperRight) {
            this.noseBridge = mNoseBridge;
            this.noseCenter = mNoseCenter;
            this.noseTip = mNoseTip;
            this.noseLowerLeft = mNoseLowerLeft;
            this.noseLowerRight = mNoseLowerRight;
            this.noseMiddleLeft = mNoseMiddleLeft;
            this.noseMiddleRight = mNoseMiddleRight;
            this.noseUpperLeft = mNoseUpperLeft;
            this.noseUpperRight = mNoseUpperRight;
        }
    }

    FaceData() {
        r4 = new int[3][];
        int[] iArr = new int[]{1, iArr};
        iArr = new int[]{-1, iArr};
        iArr = new int[]{-1, iArr};
        this.sinCosValues = r4;
        this.leftEyeObj = new Eyes(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.rightEyeObj = new Eyes(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.leftEar = new Ears(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.rightEar = new Ears(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.mouthObj = new Mouth(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.leftEyebrow = new Eyebrows(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.rightEyebrow = new Eyebrows(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.nose = new Nose(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.chin = new Chin(new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED), new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED));
        this.mouth = new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED);
        this.leftEye = new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED);
        this.rightEye = new Point(FacialProcessingConstants.FP_NOT_PROCESSED, FacialProcessingConstants.FP_NOT_PROCESSED);
    }

    protected void initializeFaceObj(boolean isMirrored, int rotationAngle) {
        this.smileDegree = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.lEyeBlink = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rEyeBlink = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.eyeHorizontalGazeAngle = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.eyeVerticalGazeAngle = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rect = null;
        this.mouth.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouth.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEye.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEye.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEye.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEye.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        initializeContourObjects();
        this.roll = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.pitch = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.yaw = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.isMirrored = isMirrored;
        this.rotationAngleDegrees = rotationAngle;
        this.personId = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.recognitionConfidenceValue = FacialProcessingConstants.FP_NOT_PROCESSED;
    }

    private void initializeContourObjects() {
        this.leftEyeObj.left.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.left.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.right.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.right.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.top.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.top.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.bottom.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.bottom.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.centerPupil.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyeObj.centerPupil.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.left.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.left.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.right.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.right.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.top.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.top.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.bottom.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.bottom.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.centerPupil.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyeObj.centerPupil.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseBridge.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseBridge.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseCenter.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseCenter.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseTip.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseTip.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseLowerLeft.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseLowerLeft.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseLowerRight.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseLowerRight.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseMiddleLeft.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseMiddleLeft.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseMiddleRight.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseMiddleRight.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseUpperLeft.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseUpperLeft.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseUpperRight.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.nose.noseUpperRight.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.left.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.left.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.right.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.right.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.upperLipTop.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.upperLipTop.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.upperLipBottom.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.upperLipBottom.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.lowerLipTop.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.lowerLipTop.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.lowerLipBottom.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.mouthObj.lowerLipBottom.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEar.top.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEar.top.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEar.bottom.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEar.bottom.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEar.top.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEar.top.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEar.bottom.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEar.bottom.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.chin.center.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.chin.center.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.chin.left.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.chin.left.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.chin.right.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.chin.right.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyebrow.top.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyebrow.top.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyebrow.bottom.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyebrow.bottom.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyebrow.left.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyebrow.left.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyebrow.right.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.leftEyebrow.right.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyebrow.top.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyebrow.top.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyebrow.bottom.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyebrow.bottom.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyebrow.left.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyebrow.left.y = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyebrow.right.x = FacialProcessingConstants.FP_NOT_PROCESSED;
        this.rightEyebrow.right.y = FacialProcessingConstants.FP_NOT_PROCESSED;
    }

    protected void setSmileValue(int smile) {
        this.smileDegree = smile;
    }

    public int getSmileValue() {
        return this.smileDegree;
    }

    protected void setBlinkValues(int leftEyeBlink, int rightEyeBlink) {
        this.lEyeBlink = leftEyeBlink;
        this.rEyeBlink = rightEyeBlink;
    }

    public int getLeftEyeBlink() {
        return this.lEyeBlink;
    }

    public int getRightEyeBlink() {
        return this.rEyeBlink;
    }

    protected void setEyeGazeAngles(int hGazeAngle, int vGazeAngle) {
        this.eyeHorizontalGazeAngle = hGazeAngle;
        this.eyeVerticalGazeAngle = vGazeAngle;
    }

    public int getEyeHorizontalGazeAngle() {
        return this.eyeHorizontalGazeAngle;
    }

    public int getEyeVerticalGazeAngle() {
        return this.eyeVerticalGazeAngle;
    }

    public PointF getEyeGazePoint() {
        if (this.eyeHorizontalGazeAngle == FacialProcessingConstants.FP_NOT_PROCESSED) {
            return null;
        }
        if (this.eyeHorizontalGazeAngle > 90 || this.eyeHorizontalGazeAngle < -90 || this.eyeVerticalGazeAngle > 90 || this.eyeVerticalGazeAngle < -90) {
            Log.e(TAG, "Gaze angles out of range Hor: " + this.eyeHorizontalGazeAngle + " Ver: " + this.eyeVerticalGazeAngle);
            return null;
        }
        float xraw = (float) Math.sin((double) ((float) ((((double) ((float) this.eyeHorizontalGazeAngle)) / 180.0d) * 3.141592653589793d)));
        float yraw = (float) (-Math.sin((double) ((float) ((((double) ((float) this.eyeVerticalGazeAngle)) / 180.0d) * 3.141592653589793d))));
        float rawRadians = (float) Math.atan((double) (yraw / xraw));
        if (xraw < 0.0f) {
            rawRadians = (float) (((double) rawRadians) + 3.141592653589793d);
        }
        float length = (float) (Math.sqrt((double) ((xraw * xraw) + (yraw * yraw))) / 0.6046d);
        if (xraw == 0.0f) {
            rawRadians = 1.5707964f;
            if (yraw < 0.0f) {
                rawRadians = (float) (((double) 1070141403) + 3.141592653589793d);
            }
        } else {
            rawRadians = (float) Math.atan((double) (yraw / xraw));
            if (xraw < 0.0f) {
                rawRadians = (float) (((double) rawRadians) + 3.141592653589793d);
            }
        }
        PointF p = new PointF();
        p.x = (float) (Math.cos((double) rawRadians) * ((double) length));
        p.y = (float) (Math.sin((double) rawRadians) * ((double) length));
        return p;
    }

    protected void setYaw(int yaw) {
        this.yaw = yaw;
    }

    protected void setPitch(int pitch) {
        this.pitch = pitch;
    }

    protected void setRoll(int roll) {
        this.roll = roll;
    }

    public int getYaw() {
        return this.yaw;
    }

    public int getRoll() {
        return this.roll;
    }

    public int getPitch() {
        return this.pitch;
    }

    protected void doRotationNMirroring(int prevWidth, int prevHeight) {
        if (this.isMirrored) {
            doMirroring(prevWidth);
        }
        if (this.rotationAngleDegrees != 0) {
            doRotation(prevWidth, prevHeight);
        }
    }

    private void doRotation(int prevWidth, int prevHeight) {
        Point temp = new Point(0, 0);
        int xCenter = prevWidth / 2;
        int yCenter = prevHeight / 2;
        int cos = 1;
        int sin = 0;
        int xDiff = 0;
        int yDiff = 0;
        switch (this.rotationAngleDegrees) {
            case AdSize.LARGE_AD_HEIGHT /*90*/:
                sin = this.sinCosValues[0][0];
                cos = this.sinCosValues[0][1];
                xDiff = 0 - xCenter;
                yDiff = prevHeight - yCenter;
                temp.x = (xDiff * cos) - (yDiff * sin);
                temp.y = (xDiff * sin) + (yDiff * cos);
                xDiff = temp.x + xCenter;
                yDiff = temp.y + yCenter;
                break;
            case 180:
                sin = this.sinCosValues[1][0];
                cos = this.sinCosValues[1][1];
                break;
            case 270:
                sin = this.sinCosValues[2][0];
                cos = this.sinCosValues[2][1];
                xDiff = (prevWidth - 1) - xCenter;
                yDiff = 0 - yCenter;
                temp.x = (xDiff * cos) - (yDiff * sin);
                temp.y = (xDiff * sin) + (yDiff * cos);
                xDiff = temp.x + xCenter;
                yDiff = temp.y + yCenter;
                break;
        }
        if (this.leftEyeObj != null) {
            rotatePoint(this.leftEyeObj.centerPupil, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEyeObj.top, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEyeObj.bottom, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEyeObj.left, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEyeObj.right, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyeObj.centerPupil, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyeObj.top, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyeObj.bottom, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyeObj.left, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyeObj.right, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.mouthObj.left, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.mouthObj.right, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.mouthObj.upperLipTop, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.mouthObj.upperLipBottom, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.mouthObj.lowerLipTop, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.mouthObj.lowerLipBottom, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseBridge, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseCenter, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseLowerLeft, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseLowerRight, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseMiddleLeft, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseMiddleRight, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseUpperLeft, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseUpperRight, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.nose.noseTip, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.chin.center, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.chin.left, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.chin.right, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEar.top, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEar.bottom, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEar.top, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEar.bottom, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEyebrow.top, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEyebrow.bottom, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEyebrow.left, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.leftEyebrow.right, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyebrow.top, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyebrow.bottom, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyebrow.left, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
            rotatePoint(this.rightEyebrow.right, sin, cos, temp, xCenter, yCenter, xDiff, yDiff);
        }
        if (this.leftEye != null) {
            this.leftEye.x -= xCenter;
            this.leftEye.y -= yCenter;
            temp.x = (this.leftEye.x * cos) - (this.leftEye.y * sin);
            temp.y = (this.leftEye.x * sin) + (this.leftEye.y * cos);
            this.leftEye.x = (temp.x + xCenter) - xDiff;
            this.leftEye.y = (temp.y + yCenter) - yDiff;
            this.rightEye.x -= xCenter;
            this.rightEye.y -= yCenter;
            temp.x = (this.rightEye.x * cos) - (this.rightEye.y * sin);
            temp.y = (this.rightEye.x * sin) + (this.rightEye.y * cos);
            this.rightEye.x = (temp.x + xCenter) - xDiff;
            this.rightEye.y = (temp.y + yCenter) - yDiff;
            this.mouth.x -= xCenter;
            this.mouth.y -= yCenter;
            temp.x = (this.mouth.x * cos) - (this.mouth.y * sin);
            temp.y = (this.mouth.x * sin) + (this.mouth.y * cos);
            this.mouth.x = (temp.x + xCenter) - xDiff;
            this.mouth.y = (temp.y + yCenter) - yDiff;
        }
        int left = this.rect.left - xCenter;
        int top = this.rect.top - yCenter;
        temp.x = (left * cos) - (top * sin);
        temp.y = (left * sin) + (top * cos);
        left = (temp.x + xCenter) - xDiff;
        top = (temp.y + yCenter) - yDiff;
        int right = this.rect.right - xCenter;
        int bottom = this.rect.bottom - yCenter;
        temp.x = (right * cos) - (bottom * sin);
        temp.y = (right * sin) + (bottom * cos);
        right = (temp.x + xCenter) - xDiff;
        bottom = (temp.y + yCenter) - yDiff;
        if (left < right) {
            this.rect.left = left;
            this.rect.right = right;
        } else {
            this.rect.left = right;
            this.rect.right = left;
        }
        if (top < bottom) {
            this.rect.top = top;
            this.rect.bottom = bottom;
        } else {
            this.rect.top = bottom;
            this.rect.bottom = top;
        }
        switch (this.rotationAngleDegrees) {
            case AdSize.LARGE_AD_HEIGHT /*90*/:
                this.roll += this.rotationAngleDegrees;
                if (this.roll > 180) {
                    this.roll = (this.roll - 180) - 180;
                    break;
                }
                break;
            case 180:
                if (this.roll > 0) {
                    this.roll -= this.rotationAngleDegrees;
                    break;
                } else {
                    this.roll += this.rotationAngleDegrees;
                    break;
                }
            case 270:
                this.roll -= 90;
                if (this.roll < -179) {
                    this.roll = (-179 - this.roll) + 179;
                    break;
                }
                break;
        }
        int tempInt;
        switch (this.rotationAngleDegrees) {
            case AdSize.LARGE_AD_HEIGHT /*90*/:
                tempInt = -this.eyeHorizontalGazeAngle;
                this.eyeHorizontalGazeAngle = this.eyeVerticalGazeAngle;
                this.eyeVerticalGazeAngle = tempInt;
                return;
            case 180:
                this.eyeHorizontalGazeAngle = -this.eyeHorizontalGazeAngle;
                this.eyeVerticalGazeAngle = -this.eyeVerticalGazeAngle;
                return;
            case 270:
                tempInt = this.eyeHorizontalGazeAngle;
                this.eyeHorizontalGazeAngle = -this.eyeVerticalGazeAngle;
                this.eyeVerticalGazeAngle = tempInt;
                return;
            default:
                return;
        }
    }

    private void rotatePoint(Point dataPoint, int sin, int cos, Point temp, int xCenter, int yCenter, int xDiff, int yDiff) {
        dataPoint.x -= xCenter;
        dataPoint.y -= yCenter;
        temp.x = (dataPoint.x * cos) - (dataPoint.y * sin);
        temp.y = (dataPoint.x * sin) + (dataPoint.y * cos);
        dataPoint.x = (temp.x + xCenter) - xDiff;
        dataPoint.y = (temp.y + yCenter) - yDiff;
    }

    private void doMirroring(int width) {
        int tempInt;
        if (this.yaw != FacialProcessingConstants.FP_NOT_PROCESSED) {
            this.yaw = -this.yaw;
            this.roll = -this.roll;
        }
        if (this.eyeHorizontalGazeAngle != FacialProcessingConstants.FP_NOT_PROCESSED) {
            this.eyeHorizontalGazeAngle = -this.eyeHorizontalGazeAngle;
        }
        if (this.lEyeBlink != FacialProcessingConstants.FP_NOT_PROCESSED) {
            tempInt = this.lEyeBlink;
            this.lEyeBlink = this.rEyeBlink;
            this.rEyeBlink = tempInt;
        }
        if (!(this.leftEye == null || this.leftEye.x == FacialProcessingConstants.FP_NOT_PROCESSED)) {
            this.leftEye.x = (width - this.leftEye.x) - 1;
            this.rightEye.x = (width - this.rightEye.x) - 1;
            this.mouth.x = (width - this.mouth.x) - 1;
            Point temp = this.leftEye;
            this.leftEye = this.rightEye;
            this.rightEye = temp;
        }
        if (this.leftEyeObj != null) {
            mirrorPoint(this.leftEyeObj.top, width);
            mirrorPoint(this.leftEyeObj.bottom, width);
            mirrorPoint(this.leftEyeObj.left, width);
            mirrorPoint(this.leftEyeObj.right, width);
            mirrorPoint(this.leftEyeObj.centerPupil, width);
            mirrorPoint(this.rightEyeObj.top, width);
            mirrorPoint(this.rightEyeObj.bottom, width);
            mirrorPoint(this.rightEyeObj.left, width);
            mirrorPoint(this.rightEyeObj.right, width);
            mirrorPoint(this.rightEyeObj.centerPupil, width);
            mirrorPoint(this.mouthObj.left, width);
            mirrorPoint(this.mouthObj.right, width);
            mirrorPoint(this.mouthObj.upperLipTop, width);
            mirrorPoint(this.mouthObj.upperLipBottom, width);
            mirrorPoint(this.mouthObj.lowerLipTop, width);
            mirrorPoint(this.mouthObj.lowerLipBottom, width);
            mirrorPoint(this.nose.noseBridge, width);
            mirrorPoint(this.nose.noseCenter, width);
            mirrorPoint(this.nose.noseTip, width);
            mirrorPoint(this.nose.noseLowerLeft, width);
            mirrorPoint(this.nose.noseLowerRight, width);
            mirrorPoint(this.nose.noseMiddleLeft, width);
            mirrorPoint(this.nose.noseMiddleRight, width);
            mirrorPoint(this.nose.noseUpperLeft, width);
            mirrorPoint(this.nose.noseUpperRight, width);
            mirrorPoint(this.leftEyebrow.top, width);
            mirrorPoint(this.leftEyebrow.bottom, width);
            mirrorPoint(this.leftEyebrow.left, width);
            mirrorPoint(this.leftEyebrow.right, width);
            mirrorPoint(this.rightEyebrow.top, width);
            mirrorPoint(this.rightEyebrow.bottom, width);
            mirrorPoint(this.rightEyebrow.left, width);
            mirrorPoint(this.rightEyebrow.right, width);
            mirrorPoint(this.leftEar.top, width);
            mirrorPoint(this.leftEar.bottom, width);
            mirrorPoint(this.rightEar.top, width);
            mirrorPoint(this.rightEar.bottom, width);
            mirrorPoint(this.chin.center, width);
            mirrorPoint(this.chin.left, width);
            mirrorPoint(this.chin.right, width);
        }
        tempInt = (width - this.rect.left) - 1;
        this.rect.left = (width - this.rect.right) - 1;
        this.rect.right = tempInt;
    }

    private void mirrorPoint(Point dataPoint, int width) {
        dataPoint.x = (width - dataPoint.x) - 1;
    }

    protected void normalizeCoordinates(float scaleX, float scaleY) {
        if (!(this.leftEye == null || this.leftEye.x == FacialProcessingConstants.FP_NOT_PROCESSED)) {
            this.leftEye.x = (int) (((float) this.leftEye.x) * scaleX);
            this.leftEye.y = (int) (((float) this.leftEye.y) * scaleY);
            this.rightEye.x = (int) (((float) this.rightEye.x) * scaleX);
            this.rightEye.y = (int) (((float) this.rightEye.y) * scaleY);
            this.mouth.x = (int) (((float) this.mouth.x) * scaleX);
            this.mouth.y = (int) (((float) this.mouth.y) * scaleY);
        }
        if (this.leftEyeObj != null) {
            normalizeMouthObj(scaleX, scaleY);
            normalizeNoseObj(scaleX, scaleY);
            normalizeLeftEyeObj(scaleX, scaleY);
            normalizeRightEyeObj(scaleX, scaleY);
            normalizeLeftEyeBrowObj(scaleX, scaleY);
            normalizeRightEyeBrowObj(scaleX, scaleY);
            normalizeLeftEarObj(scaleX, scaleY);
            normalizeRightEarObj(scaleX, scaleY);
            normalizeChinCoordinates(scaleX, scaleY);
        }
        this.rect.left = (int) (((float) this.rect.left) * scaleX);
        this.rect.top = (int) (((float) this.rect.top) * scaleY);
        this.rect.right = (int) (((float) this.rect.right) * scaleX);
        this.rect.bottom = (int) (((float) this.rect.bottom) * scaleY);
    }

    private void normalizeChinCoordinates(float scaleX, float scaleY) {
        this.chin.left.x = (int) (((float) this.chin.left.x) * scaleX);
        this.chin.left.y = (int) (((float) this.chin.left.y) * scaleY);
        this.chin.right.x = (int) (((float) this.chin.right.x) * scaleX);
        this.chin.right.y = (int) (((float) this.chin.right.y) * scaleY);
        this.chin.center.x = (int) (((float) this.chin.center.x) * scaleX);
        this.chin.center.y = (int) (((float) this.chin.center.y) * scaleY);
    }

    private void normalizeRightEarObj(float scaleX, float scaleY) {
        this.rightEar.top.x = (int) (((float) this.rightEar.top.x) * scaleX);
        this.rightEar.top.y = (int) (((float) this.rightEar.top.y) * scaleY);
        this.rightEar.bottom.x = (int) (((float) this.rightEar.bottom.x) * scaleX);
        this.rightEar.bottom.y = (int) (((float) this.rightEar.bottom.y) * scaleY);
    }

    private void normalizeLeftEarObj(float scaleX, float scaleY) {
        this.leftEar.top.x = (int) (((float) this.leftEar.top.x) * scaleX);
        this.leftEar.top.y = (int) (((float) this.leftEar.top.y) * scaleY);
        this.leftEar.bottom.x = (int) (((float) this.leftEar.bottom.x) * scaleX);
        this.leftEar.bottom.y = (int) (((float) this.leftEar.bottom.y) * scaleY);
    }

    private void normalizeRightEyeBrowObj(float scaleX, float scaleY) {
        this.rightEyebrow.left.x = (int) (((float) this.rightEyebrow.left.x) * scaleX);
        this.rightEyebrow.left.y = (int) (((float) this.rightEyebrow.left.y) * scaleY);
        this.rightEyebrow.right.x = (int) (((float) this.rightEyebrow.right.x) * scaleX);
        this.rightEyebrow.right.y = (int) (((float) this.rightEyebrow.right.y) * scaleY);
        this.rightEyebrow.top.x = (int) (((float) this.rightEyebrow.top.x) * scaleX);
        this.rightEyebrow.top.y = (int) (((float) this.rightEyebrow.top.y) * scaleY);
        this.rightEyebrow.bottom.x = (int) (((float) this.rightEyebrow.bottom.x) * scaleX);
        this.rightEyebrow.bottom.y = (int) (((float) this.rightEyebrow.bottom.y) * scaleY);
    }

    private void normalizeLeftEyeBrowObj(float scaleX, float scaleY) {
        this.leftEyebrow.left.x = (int) (((float) this.leftEyebrow.left.x) * scaleX);
        this.leftEyebrow.left.y = (int) (((float) this.leftEyebrow.left.y) * scaleY);
        this.leftEyebrow.right.x = (int) (((float) this.leftEyebrow.right.x) * scaleX);
        this.leftEyebrow.right.y = (int) (((float) this.leftEyebrow.right.y) * scaleY);
        this.leftEyebrow.top.x = (int) (((float) this.leftEyebrow.top.x) * scaleX);
        this.leftEyebrow.top.y = (int) (((float) this.leftEyebrow.top.y) * scaleY);
        this.leftEyebrow.bottom.x = (int) (((float) this.leftEyebrow.bottom.x) * scaleX);
        this.leftEyebrow.bottom.y = (int) (((float) this.leftEyebrow.bottom.y) * scaleY);
    }

    private void normalizeRightEyeObj(float scaleX, float scaleY) {
        this.rightEyeObj.left.x = (int) (((float) this.rightEyeObj.left.x) * scaleX);
        this.rightEyeObj.left.y = (int) (((float) this.rightEyeObj.left.y) * scaleY);
        this.rightEyeObj.right.x = (int) (((float) this.rightEyeObj.right.x) * scaleX);
        this.rightEyeObj.right.y = (int) (((float) this.rightEyeObj.right.y) * scaleY);
        this.rightEyeObj.top.x = (int) (((float) this.rightEyeObj.top.x) * scaleX);
        this.rightEyeObj.top.y = (int) (((float) this.rightEyeObj.top.y) * scaleY);
        this.rightEyeObj.bottom.x = (int) (((float) this.rightEyeObj.bottom.x) * scaleX);
        this.rightEyeObj.bottom.y = (int) (((float) this.rightEyeObj.bottom.y) * scaleY);
        this.rightEyeObj.centerPupil.x = (int) (((float) this.rightEyeObj.centerPupil.x) * scaleX);
        this.rightEyeObj.centerPupil.y = (int) (((float) this.rightEyeObj.centerPupil.y) * scaleY);
    }

    private void normalizeLeftEyeObj(float scaleX, float scaleY) {
        this.leftEyeObj.left.x = (int) (((float) this.leftEyeObj.left.x) * scaleX);
        this.leftEyeObj.left.y = (int) (((float) this.leftEyeObj.left.y) * scaleY);
        this.leftEyeObj.right.x = (int) (((float) this.leftEyeObj.right.x) * scaleX);
        this.leftEyeObj.right.y = (int) (((float) this.leftEyeObj.right.y) * scaleY);
        this.leftEyeObj.top.x = (int) (((float) this.leftEyeObj.top.x) * scaleX);
        this.leftEyeObj.top.y = (int) (((float) this.leftEyeObj.top.y) * scaleY);
        this.leftEyeObj.bottom.x = (int) (((float) this.leftEyeObj.bottom.x) * scaleX);
        this.leftEyeObj.bottom.y = (int) (((float) this.leftEyeObj.bottom.y) * scaleY);
        this.leftEyeObj.centerPupil.x = (int) (((float) this.leftEyeObj.centerPupil.x) * scaleX);
        this.leftEyeObj.centerPupil.y = (int) (((float) this.leftEyeObj.centerPupil.y) * scaleY);
    }

    private void normalizeNoseObj(float scaleX, float scaleY) {
        this.nose.noseBridge.x = (int) (((float) this.nose.noseBridge.x) * scaleX);
        this.nose.noseBridge.y = (int) (((float) this.nose.noseBridge.y) * scaleY);
        this.nose.noseCenter.x = (int) (((float) this.nose.noseCenter.x) * scaleX);
        this.nose.noseCenter.y = (int) (((float) this.nose.noseCenter.y) * scaleY);
        this.nose.noseTip.x = (int) (((float) this.nose.noseTip.x) * scaleX);
        this.nose.noseTip.y = (int) (((float) this.nose.noseTip.y) * scaleY);
        this.nose.noseLowerLeft.x = (int) (((float) this.nose.noseLowerLeft.x) * scaleX);
        this.nose.noseLowerLeft.y = (int) (((float) this.nose.noseLowerLeft.y) * scaleY);
        this.nose.noseLowerRight.x = (int) (((float) this.nose.noseLowerRight.x) * scaleX);
        this.nose.noseLowerRight.y = (int) (((float) this.nose.noseLowerRight.y) * scaleY);
        this.nose.noseMiddleLeft.x = (int) (((float) this.nose.noseMiddleLeft.x) * scaleX);
        this.nose.noseMiddleLeft.y = (int) (((float) this.nose.noseMiddleLeft.y) * scaleY);
        this.nose.noseMiddleRight.x = (int) (((float) this.nose.noseMiddleRight.x) * scaleX);
        this.nose.noseMiddleRight.y = (int) (((float) this.nose.noseMiddleRight.y) * scaleY);
        this.nose.noseUpperLeft.x = (int) (((float) this.nose.noseUpperLeft.x) * scaleX);
        this.nose.noseUpperLeft.y = (int) (((float) this.nose.noseUpperLeft.y) * scaleY);
        this.nose.noseUpperRight.x = (int) (((float) this.nose.noseUpperRight.x) * scaleX);
        this.nose.noseUpperRight.y = (int) (((float) this.nose.noseUpperRight.y) * scaleY);
    }

    private void normalizeMouthObj(float scaleX, float scaleY) {
        this.mouthObj.left.x = (int) (((float) this.mouthObj.left.x) * scaleX);
        this.mouthObj.left.y = (int) (((float) this.mouthObj.left.y) * scaleY);
        this.mouthObj.right.x = (int) (((float) this.mouthObj.right.x) * scaleX);
        this.mouthObj.right.y = (int) (((float) this.mouthObj.right.y) * scaleY);
        this.mouthObj.lowerLipBottom.x = (int) (((float) this.mouthObj.lowerLipBottom.x) * scaleX);
        this.mouthObj.lowerLipBottom.y = (int) (((float) this.mouthObj.lowerLipBottom.y) * scaleY);
        this.mouthObj.lowerLipTop.x = (int) (((float) this.mouthObj.lowerLipTop.x) * scaleX);
        this.mouthObj.lowerLipTop.y = (int) (((float) this.mouthObj.lowerLipTop.y) * scaleY);
        this.mouthObj.upperLipBottom.x = (int) (((float) this.mouthObj.upperLipBottom.x) * scaleX);
        this.mouthObj.upperLipBottom.y = (int) (((float) this.mouthObj.upperLipBottom.y) * scaleY);
        this.mouthObj.upperLipTop.x = (int) (((float) this.mouthObj.upperLipTop.x) * scaleX);
        this.mouthObj.upperLipTop.y = (int) (((float) this.mouthObj.upperLipTop.y) * scaleY);
    }

    public int getPersonId() {
        return this.personId;
    }

    public int getRecognitionConfidence() {
        return this.recognitionConfidenceValue;
    }

    protected void setPersonId(int personId) {
        this.personId = personId;
    }

    protected void setRecognitionConfidence(int recognitionConfidenceValue) {
        this.recognitionConfidenceValue = recognitionConfidenceValue;
    }
}
