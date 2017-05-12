package com.givewaygames.gwgl.face;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import com.givewaygames.gwgl.utils.FPSHelper;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.camera.GLCamera;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider;
import com.google.android.gms.cast.TextTrackStyle;
import com.qualcomm.snapdragon.sdk.face.FaceData;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing;
import com.qualcomm.snapdragon.sdk.face.FacialProcessing.PREVIEW_ROTATION_ANGLE;
import com.qualcomm.snapdragon.sdk.face.FacialProcessingConstants;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

public class QualcommFaceProcessor implements PreviewCallback {
    private String TAG = "CameraFilterActivity";
    int b = 0;
    int blah = 5;
    final int ds = 4;
    final FPSHelper faceFpsHelper = new FPSHelper("Face FPS: ");
    FaceProcessingThread faceThread;
    FacialProcessing facialProcessing;
    final GLHelper glHelper;
    GLEquationMesh glMesh;
    boolean isDo = true;
    double maxx = -1.7976931348623157E308d;
    double maxy = -1.7976931348623157E308d;
    final FPSHelper meshFpsHelper = new FPSHelper("Mesh FPS: ");
    double minx = Double.MAX_VALUE;
    double miny = Double.MAX_VALUE;
    int missedFaceCount = 0;
    int mmaxx = FacialProcessingConstants.FP_NOT_PROCESSED;
    int mmaxy = FacialProcessingConstants.FP_NOT_PROCESSED;
    int mminx = Integer.MAX_VALUE;
    int mminy = Integer.MAX_VALUE;
    ListPointProvider pointProvider;
    int surfaceHeight = 0;
    int surfaceWidth = 0;

    class FaceProcessingThread extends Thread {
        LinkedList<FrameAvailable> frames = new LinkedList();
        Bitmap inputBitmap = null;
        Bitmap outputBitmap = null;
        RenderScript rs;
        boolean running = true;
        Allocation tmpOut;
        byte[] zig = null;

        public void notifyOfFrame(Camera camera, byte[] data) {
            if (this.frames.size() <= 1) {
                Size size = camera.getParameters().getPreviewSize();
                FrameAvailable frameAvailable = new FrameAvailable();
                frameAvailable.data = data;
                frameAvailable.width = size.width;
                frameAvailable.height = size.height;
                synchronized (this) {
                    this.frames.add(frameAvailable);
                    notifyAll();
                }
            }
        }

        public void kill() {
            this.running = false;
        }

        public void run() {
            while (this.running) {
                processFrame();
            }
        }

        public void processFrame() {
            FrameAvailable frame = null;
            synchronized (this) {
                while (this.running && this.frames.size() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
                if (this.running && this.frames.size() != 0) {
                    frame = (FrameAvailable) this.frames.getFirst();
                }
            }
            if (frame != null) {
                QualcommFaceProcessor.this.facialProcessing.setFrame(frame.data, frame.width, frame.height, true, PREVIEW_ROTATION_ANGLE.ROT_0);
                if (QualcommFaceProcessor.this.surfaceWidth == 0 || QualcommFaceProcessor.this.surfaceHeight == 0) {
                    QualcommFaceProcessor.this.surfaceWidth = QualcommFaceProcessor.this.glHelper.getWidth();
                    QualcommFaceProcessor.this.surfaceHeight = QualcommFaceProcessor.this.glHelper.getHeight();
                }
                boolean faceFound = false;
                if (QualcommFaceProcessor.this.facialProcessing.getNumFaces() > 0) {
                    FaceData[] faceArray = QualcommFaceProcessor.this.facialProcessing.getFaceData();
                    if (faceArray != null && faceArray.length > 0) {
                        QualcommFaceProcessor.this.facialProcessing.normalizeCoordinates(QualcommFaceProcessor.this.surfaceWidth, QualcommFaceProcessor.this.surfaceHeight);
                        QualcommFaceProcessor.this.updateFacePosition(faceArray[0]);
                        faceFound = true;
                    }
                }
                if (!faceFound) {
                    QualcommFaceProcessor.this.missedFace();
                }
                synchronized (this) {
                    this.frames.pop();
                }
                QualcommFaceProcessor.this.meshFpsHelper.tick();
            }
        }

        public Bitmap getBitmapImageFromYUV(byte[] data, int width, int height) {
            YuvImage yuvimage = new YuvImage(data, 17, width, height, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0, width, height), 80, baos);
            byte[] jdata = baos.toByteArray();
            Options bitmapFatoryOptions = new Options();
            bitmapFatoryOptions.inPreferredConfig = Config.RGB_565;
            return BitmapFactory.decodeByteArray(jdata, 0, jdata.length, bitmapFatoryOptions);
        }
    }

    static class FrameAvailable {
        byte[] data;
        int height;
        int width;

        FrameAvailable() {
        }
    }

    class UpdateFacePositionRunnable implements Runnable {
        FaceData face;

        public UpdateFacePositionRunnable(FaceData face) {
            this.face = face;
        }

        public void run() {
            double t = normalizeH(this.face.rect.top);
            double l = normalizeW(this.face.rect.left);
            double b = normalizeH(this.face.rect.bottom);
            double r = normalizeW(this.face.rect.right);
            double sx = normalizeW(this.face.rect.centerX());
            double sy = normalizeH(this.face.rect.centerY());
            double elx = normalizeW(this.face.leftEye.x);
            double ely = normalizeH(this.face.leftEye.y);
            double erx = normalizeW(this.face.rightEye.x);
            double ery = normalizeH(this.face.rightEye.y);
            double mx = normalizeW(this.face.mouth.x);
            double my = normalizeH(this.face.mouth.y);
            QualcommFaceProcessor.this.miny = Math.min(QualcommFaceProcessor.this.miny, t);
            QualcommFaceProcessor.this.miny = Math.min(QualcommFaceProcessor.this.miny, b);
            QualcommFaceProcessor.this.maxy = Math.max(QualcommFaceProcessor.this.maxy, t);
            QualcommFaceProcessor.this.maxy = Math.max(QualcommFaceProcessor.this.maxy, b);
            QualcommFaceProcessor.this.minx = Math.min(QualcommFaceProcessor.this.minx, l);
            QualcommFaceProcessor.this.minx = Math.min(QualcommFaceProcessor.this.minx, r);
            QualcommFaceProcessor.this.maxx = Math.max(QualcommFaceProcessor.this.maxx, l);
            QualcommFaceProcessor.this.maxx = Math.max(QualcommFaceProcessor.this.maxx, r);
            QualcommFaceProcessor.this.mminy = Math.min(QualcommFaceProcessor.this.mminy, this.face.rect.top);
            QualcommFaceProcessor.this.mminy = Math.min(QualcommFaceProcessor.this.mminy, this.face.rect.bottom);
            QualcommFaceProcessor.this.mmaxy = Math.max(QualcommFaceProcessor.this.mmaxy, this.face.rect.top);
            QualcommFaceProcessor.this.mmaxy = Math.max(QualcommFaceProcessor.this.mmaxy, this.face.rect.bottom);
            QualcommFaceProcessor.this.mminx = Math.min(QualcommFaceProcessor.this.mminx, this.face.rect.left);
            QualcommFaceProcessor.this.mminx = Math.min(QualcommFaceProcessor.this.mminx, this.face.rect.right);
            QualcommFaceProcessor.this.mmaxx = Math.max(QualcommFaceProcessor.this.mmaxx, this.face.rect.left);
            QualcommFaceProcessor.this.mmaxx = Math.max(QualcommFaceProcessor.this.mmaxx, this.face.rect.right);
            double eyePLX = (elx - l) / (r - l);
            double eyePLY = (ely - t) / (b - t);
            double eyePRX = (erx - l) / (r - l);
            double eyePRY = (ery - t) / (b - t);
            double mPX = (mx - l) / (r - l);
            double mPY = (my - t) / (b - t);
            int pitch = this.face.getPitch();
            int roll = this.face.getRoll();
            int yaw = this.face.getYaw();
            QualcommFaceProcessor.this.pointProvider.updatePosition(TextTrackStyle.DEFAULT_FONT_SCALE);
            QualcommFaceProcessor.this.pointProvider.updateFace(l, -t, r, -b, elx, -ely, erx, -ery, mx, -my);
            if (QualcommFaceProcessor.this.isDo) {
                QualcommFaceProcessor.this.glMesh.buildValues();
                QualcommFaceProcessor.this.isDo = false;
            }
            long bef = System.currentTimeMillis();
            QualcommFaceProcessor.this.glMesh.updateUVandXYValues();
            Log.v(QualcommFaceProcessor.this.TAG, "Cost to build both: " + (System.currentTimeMillis() - bef));
            QualcommFaceProcessor.this.pointProvider.updatePosition(0.0f);
            bef = System.currentTimeMillis();
            QualcommFaceProcessor.this.glMesh.updateUVValues();
            Log.v(QualcommFaceProcessor.this.TAG, "Cost to build: " + (System.currentTimeMillis() - bef));
            QualcommFaceProcessor.this.faceFpsHelper.tick();
        }

        private String f2s(double value) {
            return String.format("%.2f", new Object[]{Double.valueOf(value)});
        }

        private double normalize(int v, int t) {
            return 1.0d - (2.0d * (((double) v) / ((double) t)));
        }

        private double normalizeW(int x) {
            return normalize(x, QualcommFaceProcessor.this.surfaceWidth);
        }

        private double normalizeH(int y) {
            return normalize(y, QualcommFaceProcessor.this.surfaceHeight);
        }
    }

    public QualcommFaceProcessor(GLHelper glHelper, ListPointProvider pointProvider, GLEquationMesh glMesh) {
        this.glHelper = glHelper;
        this.pointProvider = pointProvider;
        this.glMesh = glMesh;
    }

    public void setPointProvider(ListPointProvider pointProvider) {
        this.pointProvider = pointProvider;
    }

    public void initOpenGl(GLCamera glCamera) {
        glCamera.setPreviewCallback(this);
    }

    public void onPreviewFrame(byte[] data, Camera camera) {
        if (this.facialProcessing == null) {
            this.facialProcessing = FacialProcessing.getInstance();
        }
        if (this.faceThread == null) {
            this.faceThread = new FaceProcessingThread();
            this.faceThread.start();
        }
        this.faceThread.notifyOfFrame(camera, data);
    }

    private void updateFacePosition(FaceData face) {
        int i = this.b;
        this.b = i + 1;
        if (i > 5) {
            this.glHelper.runOnOpenGLThread(new UpdateFacePositionRunnable(face));
            this.b = 0;
        }
    }

    private void missedFace() {
        if (this.missedFaceCount % 300 == 0) {
            Log.v(this.TAG, "Missed face count: " + this.missedFaceCount);
        }
        this.missedFaceCount++;
    }
}
