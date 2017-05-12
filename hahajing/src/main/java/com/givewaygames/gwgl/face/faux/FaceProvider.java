package com.givewaygames.gwgl.face.faux;

import android.graphics.Point;
import android.graphics.Rect;
import com.google.android.gms.cast.TextTrackStyle;
import com.qualcomm.snapdragon.sdk.face.EditableFaceData;
import com.qualcomm.snapdragon.sdk.face.FaceData;

public class FaceProvider {
    final int H_TIME = 13124;
    final int R_TIME = 8666;
    final int W_TIME = 10000;
    final int X_TIME = 10248;
    final int Y_TIME = 9490;
    boolean doHeight = true;
    boolean doHorizontal = true;
    boolean doRotate = false;
    boolean doVertical = true;
    boolean doWidth = true;
    float eyeDistance = 0.5f;
    float eyeHeight = 0.33f;
    float eyeStart = 0.25f;
    float h = 0.5f;
    float maxH = 0.8f;
    float maxR = 360.0f;
    float maxW = 0.8f;
    float minH = 0.2f;
    float minR = 0.0f;
    float minW = 0.2f;
    float mouthCenter = 0.5f;
    float mouthHeight = 0.75f;
    float r = 0.0f;
    long startTime = System.currentTimeMillis();
    float w = 0.5f;
    float x = 0.0f;
    float y = 0.0f;

    public FaceData getNextFace(int surfaceWidth, int surfaceHeight) {
        long dt = System.currentTimeMillis() - this.startTime;
        if (this.doWidth) {
            this.w = ((this.maxW - this.minW) * Math.abs(TextTrackStyle.DEFAULT_FONT_SCALE - (((float) (dt % 10000)) / 5000.0f))) + this.minW;
        }
        if (this.doHeight) {
            this.h = ((this.maxH - this.minH) * Math.abs(TextTrackStyle.DEFAULT_FONT_SCALE - (((float) (dt % 13124)) / 6562.0f))) + this.minH;
        }
        if (this.doRotate) {
            this.r = ((this.maxR - this.minR) * Math.abs(TextTrackStyle.DEFAULT_FONT_SCALE - (((float) (dt % 8666)) / 4333.0f))) + this.minR;
        }
        if (this.doHorizontal) {
            this.x = (TextTrackStyle.DEFAULT_FONT_SCALE - this.w) * Math.abs(TextTrackStyle.DEFAULT_FONT_SCALE - (((float) (dt % 10248)) / 5124.0f));
        }
        if (this.doVertical) {
            this.y = (TextTrackStyle.DEFAULT_FONT_SCALE - this.h) * Math.abs(TextTrackStyle.DEFAULT_FONT_SCALE - (((float) (dt % 9490)) / 4745.0f));
        }
        float leftEyeX = this.x + (this.w * this.eyeStart);
        float rightEyeX = this.x + (this.w * (this.eyeStart + this.eyeDistance));
        float eyeY = this.y + (this.h * this.eyeHeight);
        float mouthX = this.x + (this.w * this.mouthCenter);
        float mouthY = this.y + (this.h * this.mouthHeight);
        int sw = surfaceWidth;
        int sh = surfaceHeight;
        EditableFaceData faceData = new EditableFaceData(false, 90);
        faceData.rect = new Rect((int) (this.x * ((float) sw)), (int) (this.y * ((float) sh)), (int) ((this.x + this.w) * ((float) sw)), (int) ((this.y + this.h) * ((float) sh)));
        faceData.leftEye = new Point((int) (((float) sw) * leftEyeX), (int) (((float) sh) * eyeY));
        faceData.rightEye = new Point((int) (((float) sw) * rightEyeX), (int) (((float) sh) * eyeY));
        faceData.mouth = new Point((int) (((float) sw) * mouthX), (int) (((float) sh) * mouthY));
        faceData.setRoll((int) this.r);
        rotateAround(faceData.leftEye, (float) faceData.rect.centerX(), (float) faceData.rect.centerY(), this.r);
        rotateAround(faceData.rightEye, (float) faceData.rect.centerX(), (float) faceData.rect.centerY(), this.r);
        rotateAround(faceData.mouth, (float) faceData.rect.centerX(), (float) faceData.rect.centerY(), this.r);
        boundRect(faceData.rect, faceData.leftEye);
        boundRect(faceData.rect, faceData.rightEye);
        boundRect(faceData.rect, faceData.mouth);
        return faceData;
    }

    void boundRect(Rect r, Point p) {
        int px = (int) (this.eyeStart * ((float) (r.right - r.left)));
        int py = (int) (this.eyeStart * ((float) (r.bottom - r.top)));
        r.left = Math.min(r.left, p.x - px);
        r.right = Math.max(r.right, p.x + px);
        r.top = Math.min(r.top, p.y - py);
        r.bottom = Math.max(r.bottom, p.y + py);
    }

    void rotateAround(Point p, float centerX, float centerY, float angle) {
        double r = Math.toRadians((double) angle);
        double newY = (((double) centerY) + (((double) (((float) p.x) - centerX)) * Math.sin(r))) + (((double) (((float) p.y) - centerY)) * Math.cos(r));
        p.x = (int) ((((double) centerX) + (((double) (((float) p.x) - centerX)) * Math.cos(r))) - (((double) (((float) p.y) - centerY)) * Math.sin(r)));
        p.y = (int) newY;
    }
}
