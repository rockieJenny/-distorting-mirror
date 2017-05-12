package com.givewaygames.gwgl;

import com.givewaygames.gwgl.CameraWrapper.OnPhotoTaken;

public interface IPhotoTaker {
    void setOnPictureTakenCallback(OnPhotoTaken onPhotoTaken);

    void takePhoto(int i, int i2, String str);
}
