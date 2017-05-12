package com.qualcomm.snapdragon.sdk.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.hardware.Camera;
import android.support.v4.internal.view.SupportMenu;
import android.view.SurfaceView;
import com.google.android.gms.cast.TextTrackStyle;
import com.qualcomm.snapdragon.sdk.face.FaceData;

public class DrawView extends SurfaceView {
    boolean _inFrame;
    int cameraPreviewHeight;
    int cameraPreviewWidth;
    public final FaceData faceData;
    public Point leftEye;
    private Paint leftEyeBrush = new Paint();
    Rect mFaceRect;
    boolean mLandScapeMode;
    int mSurfaceHeight;
    int mSurfaceWidth;
    public Point mouth;
    private Paint mouthBrush = new Paint();
    private Paint rectBrush = new Paint();
    public Point rightEye;
    private Paint rightEyeBrush = new Paint();
    float scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
    float scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;

    public DrawView(Context context, FaceData face, boolean inFrame, int surfaceWidth, int surfaceHeight, Camera cameraObj, boolean landScapeMode) {
        super(context);
        setWillNotDraw(false);
        this.faceData = face;
        this._inFrame = inFrame;
        this.mSurfaceWidth = surfaceWidth;
        this.mSurfaceHeight = surfaceHeight;
        this.mLandScapeMode = landScapeMode;
        if (cameraObj != null) {
            this.cameraPreviewWidth = cameraObj.getParameters().getPreviewSize().width;
            this.cameraPreviewHeight = cameraObj.getParameters().getPreviewSize().height;
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this._inFrame) {
            FaceData face = this.faceData;
            this.leftEyeBrush.setColor(SupportMenu.CATEGORY_MASK);
            canvas.drawCircle(((float) face.leftEye.x) * this.scaleX, ((float) face.leftEye.y) * this.scaleY, 5.0f, this.leftEyeBrush);
            this.rightEyeBrush.setColor(-16711936);
            canvas.drawCircle(((float) face.rightEye.x) * this.scaleX, ((float) face.rightEye.y) * this.scaleY, 5.0f, this.rightEyeBrush);
            this.mouthBrush.setColor(-1);
            canvas.drawCircle(((float) face.mouth.x) * this.scaleX, ((float) face.mouth.y) * this.scaleY, 5.0f, this.mouthBrush);
            this.rectBrush.setColor(-256);
            this.rectBrush.setStyle(Style.STROKE);
            Canvas canvas2 = canvas;
            canvas2.drawRect(this.scaleX * ((float) face.rect.left), this.scaleY * ((float) face.rect.top), this.scaleX * ((float) face.rect.right), this.scaleY * ((float) face.rect.bottom), this.rectBrush);
            return;
        }
        canvas.drawColor(0, Mode.CLEAR);
    }
}
