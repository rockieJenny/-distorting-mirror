package com.qualcomm.snapdragon.sdk.face;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.ArrayList;
import java.util.EnumSet;

public class FacialProcessing {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$qualcomm$snapdragon$sdk$face$FacialProcessing$FEATURE_LIST = null;
    private static final int CONFIG_DOWNSCALE_FACTOR = 2;
    private static final int NUM_POINTS_IN_FACE_CONTOUR = 40;
    private static final int NUM_POINTS_IN_PARTS = 12;
    private static final int PREVIEW_FRAME_HEIGHT = 480;
    private static final int PREVIEW_FRAME_WIDTH = 480;
    private static final int START_LOC_LEFTEYE_IN_PARTS = 0;
    private static final int START_LOC_MOUTH_IN_PARTS = 4;
    private static final int START_LOC_RIGHTEYE_IN_PARTS = 2;
    private static final String TAG = "Facial_Processing";
    private static ArrayList<FaceData> faceDataObjInventory;
    private static int facialprocHandle = 0;
    private static int featuresSupported = 0;
    private static boolean isAllZeroFlag = false;
    private static FacialProcessing myInstance = null;
    private static Point tempPoint;
    private static int totalNumPeople = 0;
    private final int MAX_REGISTERED_USERS = 1000;
    private boolean isMirrored = false;
    private int maxNumFaceDetected = 0;
    private int presentMode = FP_MODES.FP_MODE_VIDEO.getValue();
    private int previewFrameHeight = 480;
    private int previewFrameWidth = 480;
    private int rotationAngleDegrees = PREVIEW_ROTATION_ANGLE.ROT_0.getValue();
    private float scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
    private float scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
    private int userPreferredMode = FP_MODES.FP_MODE_VIDEO.getValue();

    public enum FEATURE_LIST {
        FEATURE_FACIAL_PROCESSING(1),
        FEATURE_FACIAL_RECOGNITION(2);
        
        private int value;

        private FEATURE_LIST(int value) {
            this.value = value;
        }

        protected int getValue() {
            return this.value;
        }
    }

    public enum FP_DATA {
        FACE_RECT,
        FACE_COORDINATES,
        FACE_CONTOUR,
        FACE_SMILE,
        FACE_GAZE,
        FACE_BLINK,
        FACE_ORIENTATION,
        FACE_IDENTIFICATION
    }

    public enum FP_MODES {
        FP_MODE_VIDEO(0),
        FP_MODE_STILL(1);
        
        private int value;

        private FP_MODES(int value) {
            this.value = value;
        }

        protected int getValue() {
            return this.value;
        }
    }

    protected static class Log {
        static final boolean LOG_ENABLED = true;

        protected Log() {
        }

        protected static void i(String tag, String text) {
            android.util.Log.i(tag, text);
        }

        protected static void d(String tag, String text) {
            android.util.Log.d(tag, text);
        }

        protected static void e(String tag, String text) {
            android.util.Log.e(tag, text);
        }

        protected static void v(String tag, String text) {
            android.util.Log.v(tag, text);
        }
    }

    public enum PREVIEW_ROTATION_ANGLE {
        ROT_0(0),
        ROT_90(90),
        ROT_180(180),
        ROT_270(270);
        
        private int value;

        private PREVIEW_ROTATION_ANGLE(int value) {
            this.value = value;
        }

        protected int getValue() {
            return this.value;
        }
    }

    private native int addPerson(int i, int i2);

    private native void config(int i, int i2, int i3, int i4);

    private native int create();

    private native void deinitialize();

    private native int deserializeAlbum(int i, int i2, byte[] bArr);

    private native void destroy(int i);

    private native int[] getCompleteInfos(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7);

    private native int getFaceFeature(int i, int i2);

    private native int getNumFaces(int i);

    private native int getNumberOfPeople(int i);

    private native int[] identifyPerson(int i, int i2);

    private native int initialize();

    private static native int isLibrarySupported();

    private native int removePerson(int i, int i2);

    private native int resetAlbum(int i);

    private native byte[] serializeAlbum(int i);

    private native int setConfidenceValue(int i);

    private native void setFrame(int i, byte[] bArr);

    private native void setMode(int i, int i2);

    private native int updatePerson(int i, int i2, int i3);

    static /* synthetic */ int[] $SWITCH_TABLE$com$qualcomm$snapdragon$sdk$face$FacialProcessing$FEATURE_LIST() {
        int[] iArr = $SWITCH_TABLE$com$qualcomm$snapdragon$sdk$face$FacialProcessing$FEATURE_LIST;
        if (iArr == null) {
            iArr = new int[FEATURE_LIST.values().length];
            try {
                iArr[FEATURE_LIST.FEATURE_FACIAL_PROCESSING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[FEATURE_LIST.FEATURE_FACIAL_RECOGNITION.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$com$qualcomm$snapdragon$sdk$face$FacialProcessing$FEATURE_LIST = iArr;
        }
        return iArr;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    private static boolean isSupported() {
        try {
            System.loadLibrary("mmcamera_faceproc");
            try {
                System.loadLibrary("facialproc_jni");
                return true;
            } catch (Exception e) {
                android.util.Log.e(TAG, "Failed to load facialproc_jni. Make sure that libfacialproc_jni.so is included in your project.");
                facialprocHandle = 0;
                return false;
            } catch (UnsatisfiedLinkError e2) {
                e2.printStackTrace();
                android.util.Log.e(TAG, "Failed to load facialproc_jni. Make sure that libfacialproc_jni.so is included in your project.");
                facialprocHandle = 0;
                return false;
            }
        } catch (Exception e3) {
            android.util.Log.e(TAG, "Base library load failed. The device doesn't have the required library.");
            facialprocHandle = 0;
            return false;
        } catch (UnsatisfiedLinkError e4) {
            android.util.Log.e(TAG, "Base library load failed. The device doesn't have the required library.");
            facialprocHandle = 0;
            return false;
        }
    }

    public static boolean isFeatureSupported(FEATURE_LIST featureId) {
        if (!isSupported()) {
            return false;
        }
        switch ($SWITCH_TABLE$com$qualcomm$snapdragon$sdk$face$FacialProcessing$FEATURE_LIST()[featureId.ordinal()]) {
            case 1:
            case 2:
                return true;
            default:
                facialprocHandle = 0;
                android.util.Log.e(TAG, "featureId passed is not valid");
                return false;
        }
    }

    private FacialProcessing() throws InstantiationException {
        int value;
        int i = featuresSupported;
        if (isFeatureSupported(FEATURE_LIST.FEATURE_FACIAL_PROCESSING)) {
            value = FEATURE_LIST.FEATURE_FACIAL_PROCESSING.getValue();
        } else {
            value = 0;
        }
        featuresSupported = value | i;
        if (featuresSupported == 0) {
            Log.e(TAG, "No features supported, libraries missing");
            throw new InstantiationException("Required libraries missing");
        }
        if ((FEATURE_LIST.FEATURE_FACIAL_PROCESSING.getValue() & featuresSupported) == 0) {
            facialprocHandle = 0;
        }
        boolean isSupported = false;
        if (isLibrarySupported() == 0) {
            isSupported = true;
        }
        if (isSupported) {
            Log.e(TAG, "Library Found Successfully");
        } else {
            Log.e(TAG, "Library Not Found");
        }
        initialize();
        facialprocHandle = create();
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.userPreferredMode = FP_MODES.FP_MODE_VIDEO.getValue();
        this.presentMode = FP_MODES.FP_MODE_VIDEO.getValue();
        this.rotationAngleDegrees = PREVIEW_ROTATION_ANGLE.ROT_0.getValue();
        faceDataObjInventory = new ArrayList();
        if (facialprocHandle != 0) {
            config(facialprocHandle, this.previewFrameWidth, this.previewFrameHeight, 2);
        } else {
            Log.e(TAG, "Handle creation failed");
            throw new InstantiationException("Handle creation failed");
        }
    }

    public static FacialProcessing getInstance() {
        if (myInstance == null) {
            try {
                myInstance = new FacialProcessing();
                return myInstance;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            }
        }
        Log.e(TAG, "Instance already in use");
        return null;
    }

    public void normalizeCoordinates(int surfaceWidth, int surfaceHeight) {
        if (this.rotationAngleDegrees == 0 || this.rotationAngleDegrees == 180) {
            this.scaleX = ((float) surfaceWidth) / ((float) this.previewFrameWidth);
            this.scaleY = ((float) surfaceHeight) / ((float) this.previewFrameHeight);
            return;
        }
        this.scaleX = ((float) surfaceWidth) / ((float) this.previewFrameHeight);
        this.scaleY = ((float) surfaceHeight) / ((float) this.previewFrameWidth);
    }

    public boolean setFrame(byte[] yuvData, int frameWidth, int frameHeight, boolean isMirrored, PREVIEW_ROTATION_ANGLE rotationAngle) {
        if (facialprocHandle == 0 || myInstance == null) {
            return false;
        }
        if (yuvData.length != ((frameWidth * frameHeight) * 3) / 2 || yuvData.length == 0) {
            android.util.Log.e(TAG, "Length of the frame does not match the dimensions passed");
            return false;
        }
        this.isMirrored = isMirrored;
        if (rotationAngle != null) {
            this.rotationAngleDegrees = rotationAngle.getValue();
            if (this.presentMode != this.userPreferredMode) {
                this.presentMode = this.userPreferredMode;
                setMode(facialprocHandle, this.presentMode);
            }
            if (!(frameHeight == this.previewFrameHeight && frameWidth == this.previewFrameWidth)) {
                this.previewFrameHeight = frameHeight;
                this.previewFrameWidth = frameWidth;
                config(facialprocHandle, this.previewFrameWidth, this.previewFrameHeight, 2);
            }
            setFrame(facialprocHandle, yuvData);
            return true;
        }
        Log.e(TAG, "setFrame(): Rotation Angle equals NULL");
        return false;
    }

    public boolean setBitmap(Bitmap bitmap) {
        if (facialprocHandle == 0 || myInstance == null || bitmap == null) {
            return false;
        }
        setMode(facialprocHandle, FP_MODES.FP_MODE_STILL.getValue());
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        Log.v(TAG, "Bitmap dimension wxh " + bitmapWidth + "x" + bitmapHeight);
        return setFrame(bitmapToYuv(bitmap), bitmapWidth, bitmapHeight, false, PREVIEW_ROTATION_ANGLE.ROT_0);
    }

    private byte[] bitmapToYuv(Bitmap bmp) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        byte[] yuv = new byte[(((width * height) * 3) / 2)];
        for (int i = 0; i < height / 2; i++) {
            for (int j = 0; j < width / 2; j++) {
                int doubleI = i << 1;
                int doubleJ = j << 1;
                int yIndex = (doubleI * width) + doubleJ;
                int color = bmp.getPixel(doubleJ, doubleI);
                yuv[yIndex] = (byte) ((((((((color >>> 16) & 255) * 66) + (((color >>> 8) & 255) * 129)) + ((color & 255) * 25)) + 128) >>> 8) + 16);
                color = bmp.getPixel(doubleJ + 1, doubleI);
                int i2 = yIndex + 1;
                yuv[i2] = (byte) ((((((((color >>> 16) & 255) * 66) + (((color >>> 8) & 255) * 129)) + ((color & 255) * 25)) + 128) >>> 8) + 16);
                color = bmp.getPixel(doubleJ, doubleI + 1);
                i2 = yIndex + width;
                yuv[i2] = (byte) ((((((((color >>> 16) & 255) * 66) + (((color >>> 8) & 255) * 129)) + ((color & 255) * 25)) + 128) >>> 8) + 16);
                color = bmp.getPixel(doubleJ + 1, doubleI + 1);
                i2 = (yIndex + width) + 1;
                yuv[i2] = (byte) ((((((((color >>> 16) & 255) * 66) + (((color >>> 8) & 255) * 129)) + ((color & 255) * 25)) + 128) >>> 8) + 16);
            }
        }
        return yuv;
    }

    public int getNumFaces() {
        if (facialprocHandle == 0 || myInstance == null) {
            return 0;
        }
        int[] facesData = getCompleteInfos(facialprocHandle, true, false, false, false, false, true, false);
        if (facesData == null || facesData.length == 0) {
            Log.v(TAG, "getFaceData: No info");
            return 0;
        }
        Log.e(TAG, "Face data length = " + facesData.length);
        return facesData.length / 14;
    }

    public FaceData[] getFaceData() {
        FaceData[] faceDataArr = null;
        if (!(facialprocHandle == 0 || myInstance == null)) {
            try {
                faceDataArr = getFaceData(EnumSet.of(FP_DATA.FACE_BLINK, new FP_DATA[]{FP_DATA.FACE_COORDINATES, FP_DATA.FACE_GAZE, FP_DATA.FACE_IDENTIFICATION, FP_DATA.FACE_ORIENTATION, FP_DATA.FACE_SMILE, FP_DATA.FACE_RECT}));
            } catch (Exception e) {
            }
        }
        return faceDataArr;
    }

    public FaceData[] getFaceData(EnumSet<FP_DATA> dataSet) throws IllegalArgumentException {
        if (dataSet == null) {
            throw new IllegalArgumentException();
        } else if (facialprocHandle == 0 || myInstance == null) {
            Log.v(TAG, "getFaceData: Invalid handle");
            return null;
        } else if (getNumFaces(facialprocHandle) == 0 || dataSet == null) {
            Log.v(TAG, "getFaceData: No faces");
            return null;
        } else {
            int[] facesData = getCompleteInfos(facialprocHandle, true, dataSet.contains(FP_DATA.FACE_COORDINATES), dataSet.contains(FP_DATA.FACE_CONTOUR), dataSet.contains(FP_DATA.FACE_ORIENTATION), dataSet.contains(FP_DATA.FACE_SMILE), dataSet.contains(FP_DATA.FACE_GAZE), dataSet.contains(FP_DATA.FACE_BLINK));
            if (facesData == null || facesData.length == 0) {
                Log.v(TAG, "getFaceData: No info");
                return null;
            }
            int i;
            for (int i2 : facesData) {
                Log.e(TAG, "Values" + i2);
            }
            int numElemsPerFace = 12;
            if (dataSet.contains(FP_DATA.FACE_COORDINATES)) {
                numElemsPerFace = 12 + 24;
            }
            if (dataSet.contains(FP_DATA.FACE_CONTOUR)) {
                numElemsPerFace += 80;
            }
            if (dataSet.contains(FP_DATA.FACE_ORIENTATION)) {
                numElemsPerFace += 3;
            }
            if (dataSet.contains(FP_DATA.FACE_SMILE)) {
                numElemsPerFace++;
            }
            if (dataSet.contains(FP_DATA.FACE_GAZE)) {
                numElemsPerFace += 2;
            }
            if (dataSet.contains(FP_DATA.FACE_BLINK)) {
                numElemsPerFace += 2;
            }
            ArrayList<FaceData> faceDataList = new ArrayList();
            int numFacesInFrame = facesData.length / numElemsPerFace;
            if (numFacesInFrame > this.maxNumFaceDetected) {
                int addFaces = numFacesInFrame - this.maxNumFaceDetected;
                this.maxNumFaceDetected = numFacesInFrame;
                for (i = 0; i < addFaces; i++) {
                    faceDataObjInventory.add(new FaceData());
                }
            }
            int j = 0;
            i = 0;
            while (i < facesData.length / numElemsPerFace) {
                try {
                    FaceData face = (FaceData) faceDataObjInventory.get(i);
                    face.initializeFaceObj(this.isMirrored, this.rotationAngleDegrees);
                    face.rect = new Rect(facesData[j], facesData[j + 1], facesData[j] + facesData[j + 2], facesData[j + 1] + facesData[j + 3]);
                    j += 4;
                    if (dataSet.contains(FP_DATA.FACE_COORDINATES)) {
                        face.leftEye.x = facesData[j + 0];
                        face.leftEye.y = facesData[(j + 0) + 1];
                        face.rightEye.x = facesData[j + 2];
                        face.rightEye.y = facesData[(j + 2) + 1];
                        face.mouth.x = facesData[j + 4];
                        face.mouth.y = facesData[(j + 4) + 1];
                        j += 24;
                    }
                    if (dataSet.contains(FP_DATA.FACE_CONTOUR)) {
                        int k = j;
                        face.leftEyeObj.centerPupil.x = facesData[k];
                        face.leftEyeObj.centerPupil.y = facesData[k + 1];
                        face.leftEyeObj.right.x = facesData[k + 2];
                        face.leftEyeObj.right.y = facesData[k + 3];
                        face.leftEyeObj.left.x = facesData[k + 4];
                        face.leftEyeObj.left.y = facesData[k + 5];
                        face.leftEyeObj.top.x = facesData[k + 6];
                        face.leftEyeObj.top.y = facesData[k + 7];
                        face.leftEyeObj.bottom.x = facesData[k + 8];
                        face.leftEyeObj.bottom.y = facesData[k + 9];
                        k += 10;
                        face.rightEyeObj.centerPupil.x = facesData[k];
                        face.rightEyeObj.centerPupil.y = facesData[k + 1];
                        face.rightEyeObj.right.x = facesData[k + 2];
                        face.rightEyeObj.right.y = facesData[k + 3];
                        face.rightEyeObj.left.x = facesData[k + 4];
                        face.rightEyeObj.left.y = facesData[k + 5];
                        face.rightEyeObj.top.x = facesData[k + 6];
                        face.rightEyeObj.top.y = facesData[k + 7];
                        face.rightEyeObj.bottom.x = facesData[k + 8];
                        face.rightEyeObj.bottom.y = facesData[k + 9];
                        k += 10;
                        face.nose.noseBridge.x = facesData[k];
                        face.nose.noseBridge.y = facesData[k + 1];
                        face.nose.noseCenter.x = facesData[k + 2];
                        face.nose.noseCenter.y = facesData[k + 3];
                        face.nose.noseTip.x = facesData[k + 4];
                        face.nose.noseTip.y = facesData[k + 5];
                        face.nose.noseLowerLeft.x = facesData[k + 6];
                        face.nose.noseLowerLeft.y = facesData[k + 7];
                        face.nose.noseLowerRight.x = facesData[k + 8];
                        face.nose.noseLowerRight.y = facesData[k + 9];
                        face.nose.noseMiddleLeft.x = facesData[k + 10];
                        face.nose.noseMiddleLeft.y = facesData[k + 11];
                        face.nose.noseMiddleRight.x = facesData[k + 12];
                        face.nose.noseMiddleRight.y = facesData[k + 13];
                        face.nose.noseUpperLeft.x = facesData[k + 14];
                        face.nose.noseUpperLeft.y = facesData[k + 15];
                        face.nose.noseUpperRight.x = facesData[k + 16];
                        face.nose.noseUpperRight.y = facesData[k + 17];
                        k += 18;
                        face.mouthObj.left.x = facesData[k];
                        face.mouthObj.left.y = facesData[k + 1];
                        face.mouthObj.right.x = facesData[k + 2];
                        face.mouthObj.right.y = facesData[k + 3];
                        face.mouthObj.upperLipTop.x = facesData[k + 4];
                        face.mouthObj.upperLipTop.y = facesData[k + 5];
                        face.mouthObj.lowerLipBottom.x = facesData[k + 6];
                        face.mouthObj.lowerLipBottom.y = facesData[k + 7];
                        face.mouthObj.upperLipBottom.x = facesData[k + 8];
                        face.mouthObj.upperLipBottom.y = facesData[k + 9];
                        face.mouthObj.lowerLipTop.x = facesData[k + 10];
                        face.mouthObj.lowerLipTop.y = facesData[k + 11];
                        k += 12;
                        face.leftEyebrow.top.x = facesData[k];
                        face.leftEyebrow.top.y = facesData[k + 1];
                        face.leftEyebrow.bottom.x = facesData[k + 2];
                        face.leftEyebrow.bottom.y = facesData[k + 3];
                        face.leftEyebrow.right.x = facesData[k + 4];
                        face.leftEyebrow.right.y = facesData[k + 5];
                        face.leftEyebrow.left.x = facesData[k + 6];
                        face.leftEyebrow.left.y = facesData[k + 7];
                        k += 8;
                        face.rightEyebrow.top.x = facesData[k];
                        face.rightEyebrow.top.y = facesData[k + 1];
                        face.rightEyebrow.bottom.x = facesData[k + 2];
                        face.rightEyebrow.bottom.y = facesData[k + 3];
                        face.rightEyebrow.right.x = facesData[k + 4];
                        face.rightEyebrow.right.y = facesData[k + 5];
                        face.rightEyebrow.left.x = facesData[k + 6];
                        face.rightEyebrow.left.y = facesData[k + 7];
                        k += 8;
                        face.chin.center.x = facesData[k];
                        face.chin.center.y = facesData[k + 1];
                        face.chin.left.x = facesData[k + 2];
                        face.chin.left.y = facesData[k + 3];
                        face.chin.right.x = facesData[k + 4];
                        face.chin.right.y = facesData[k + 5];
                        k += 6;
                        face.leftEar.bottom.x = facesData[k];
                        face.leftEar.bottom.y = facesData[k + 1];
                        face.rightEar.bottom.x = facesData[k + 2];
                        face.rightEar.bottom.y = facesData[k + 3];
                        face.leftEar.top.x = facesData[k + 4];
                        face.leftEar.top.y = facesData[k + 5];
                        face.rightEar.top.x = facesData[k + 6];
                        face.rightEar.top.y = facesData[k + 7];
                        k += 8;
                        j += 80;
                    }
                    if (dataSet.contains(FP_DATA.FACE_ORIENTATION)) {
                        face.setPitch(facesData[j]);
                        face.setYaw(facesData[j + 1]);
                        face.setRoll(facesData[j + 2]);
                        j += 3;
                    }
                    if (dataSet.contains(FP_DATA.FACE_SMILE)) {
                        face.setSmileValue(facesData[j]);
                        j++;
                    }
                    if (dataSet.contains(FP_DATA.FACE_GAZE)) {
                        Log.e(TAG, "Gaze angles = (" + facesData[j] + "," + facesData[j + 1] + ")");
                        face.setEyeGazeAngles(facesData[j], facesData[j + 1]);
                        j += 2;
                    }
                    if (dataSet.contains(FP_DATA.FACE_BLINK)) {
                        face.setBlinkValues(facesData[j], facesData[j + 1]);
                        j += 2;
                    }
                    if (dataSet.contains(FP_DATA.FACE_IDENTIFICATION)) {
                        int[] faceRecogData = identifyPerson(facialprocHandle, i);
                        if (faceRecogData == null) {
                            face.setPersonId(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
                            face.setRecognitionConfidence(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
                            Log.e(TAG, "identifyPersonEnum: faceRecogData[] equals NULL");
                        } else if (faceRecogData[0] == -1) {
                            face.setPersonId(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
                            face.setRecognitionConfidence(FacialProcessingConstants.FP_PERSON_NOT_REGISTERED);
                        } else {
                            face.setPersonId(faceRecogData[0]);
                            face.setRecognitionConfidence(faceRecogData[2]);
                        }
                    }
                    face.doRotationNMirroring(this.previewFrameWidth, this.previewFrameHeight);
                    face.normalizeCoordinates(this.scaleX, this.scaleY);
                    faceDataList.add(face);
                    i++;
                } catch (Exception e) {
                    e.printStackTrace();
                    faceDataList.clear();
                    return null;
                }
            }
            return (FaceData[]) faceDataList.toArray(new FaceData[faceDataList.size()]);
        }
    }

    public int addPerson(int faceIndex) throws IllegalArgumentException {
        if (getAlbumPersonCount() == 1000) {
            return -5;
        }
        FaceData[] faceDataArray = getFaceData();
        if (faceDataArray != null) {
            int numFaces = faceDataArray.length;
            if (faceIndex < 0 || faceIndex > numFaces - 1) {
                Log.e(TAG, "addPerson(): Invalid Face Index");
                throw new IllegalArgumentException();
            } else if (identifyPerson(facialprocHandle, faceIndex)[0] == -1) {
                int personId = addPerson(facialprocHandle, getFaceFeature(facialprocHandle, faceIndex));
                if (personId == -1) {
                    Log.e(TAG, "addPerson(): Adding a person failed internally");
                    return -2;
                }
                Log.d(TAG, "addPerson(): User image added successfully");
                return personId;
            } else {
                Log.e(TAG, "addPerson(): Face already exists");
                return -3;
            }
        }
        Log.e(TAG, "addPerson(): No face detected");
        return -1;
    }

    public int updatePerson(int personId, int faceIndex) throws IllegalArgumentException {
        FaceData[] faceDataArray = getFaceData();
        if (faceDataArray == null) {
            Log.e(TAG, "updatePerson(): No face detected");
            return -1;
        } else if (personId < 0) {
            Log.e(TAG, "updatePerson(): Invalid personId");
            throw new IllegalArgumentException();
        } else {
            int numFaces = faceDataArray.length;
            if (faceIndex < 0 || faceIndex > numFaces - 1) {
                Log.e(TAG, "updatePerson(): Invalid faceIndex");
                throw new IllegalArgumentException();
            }
            if (-1 == updatePerson(facialprocHandle, getFaceFeature(facialprocHandle, faceIndex), personId)) {
                Log.e(TAG, "updatePerson(): Updating person failed internally");
                return -2;
            }
            Log.d(TAG, "updatePerson(): Successful");
            return 0;
        }
    }

    public boolean deletePerson(int personId) throws IllegalArgumentException {
        if (personId < 0) {
            Log.e(TAG, "deletePerson(): Invalid faceId");
            throw new IllegalArgumentException();
        } else if (facialprocHandle == 0) {
            Log.e(TAG, "deletePerson(): Deleting person failed internally");
            return false;
        } else if (removePerson(facialprocHandle, personId) == -1) {
            Log.e(TAG, "deletePerson(): Deleting person failed internally");
            return false;
        } else {
            Log.d(TAG, "deletePerson(): Deleting person failed internally");
            return true;
        }
    }

    public boolean resetAlbum() {
        if (facialprocHandle == 0) {
            Log.e(TAG, "resetAlbum: Reset album failed internally");
            return false;
        } else if (resetAlbum(facialprocHandle) == -1) {
            Log.e(TAG, "resetAlbum: Reset album failed internally");
            return false;
        } else {
            Log.d(TAG, "resetAlbum: Reset album successful");
            return true;
        }
    }

    public byte[] serializeRecogntionAlbum() {
        if (facialprocHandle != 0) {
            Log.d(TAG, "serializeAlbum: Serializing album successful");
            return serializeAlbum(facialprocHandle);
        }
        Log.e(TAG, "serializeAlbum: Serializing album failed due to internal error");
        return null;
    }

    public boolean deserializeRecognitionAlbum(byte[] albumBuffer) throws IllegalArgumentException {
        if (albumBuffer == null) {
            Log.e(TAG, "deserializeAlbum(): Input buffer = NULL");
            throw new IllegalArgumentException();
        } else if (facialprocHandle == 0) {
            Log.e(TAG, "deserializeAlbum: Deserializing album failed internally");
            return false;
        } else if (deserializeAlbum(facialprocHandle, albumBuffer.length, albumBuffer) == -1) {
            Log.e(TAG, "deserializeAlbum: Deserializing album failed internally");
            return false;
        } else {
            Log.d(TAG, "deserializeAlbum: Deserializing album successful");
            return true;
        }
    }

    public int setRecognitionConfidence(int confidenceValue) throws IllegalArgumentException {
        if (confidenceValue <= 0 || confidenceValue >= 100) {
            throw new IllegalArgumentException();
        }
        setConfidenceValue(confidenceValue);
        return 0;
    }

    public int getAlbumPersonCount() {
        return getNumberOfPeople(facialprocHandle);
    }

    public void release() {
        if (myInstance != null) {
            destroy(facialprocHandle);
            deinitialize();
            myInstance = null;
        }
    }

    public boolean setProcessingMode(FP_MODES mode) {
        if (facialprocHandle == 0 || myInstance == null) {
            android.util.Log.e(TAG, "setMode failed. Mode: " + mode + ", handle: " + facialprocHandle);
            return false;
        }
        this.userPreferredMode = mode.getValue();
        this.presentMode = this.userPreferredMode;
        setMode(facialprocHandle, this.userPreferredMode);
        Log.d(TAG, "Mode set");
        return true;
    }

    protected int getHandle() {
        return facialprocHandle;
    }
}
