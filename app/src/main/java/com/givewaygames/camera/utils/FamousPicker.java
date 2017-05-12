package com.givewaygames.camera.utils;

import android.content.Context;
import android.graphics.Bitmap;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.utils.BitmapHelper;
import com.givewaygames.gwgl.utils.gl.meshes.GLBitmapImage.IBitmapProvider;
import java.util.Random;

public class FamousPicker {
    public static int[] famousers = new int[]{R.drawable.random_bear, R.drawable.random_boar, R.drawable.random_crocodile, R.drawable.random_dog, R.drawable.random_dolphine, R.drawable.random_elefant, R.drawable.random_frog, R.drawable.random_grizzly_bear, R.drawable.random_mustang, R.drawable.random_octopus, R.drawable.random_orangutan, R.drawable.random_owl, R.drawable.random_seal_baby, R.drawable.random_skunk, R.drawable.random_squirrel, R.drawable.random_snail, R.drawable.random_turtle, R.drawable.random_walrus, R.drawable.random_zebra_fish};

    public static class FamousBitmapProvider implements IBitmapProvider {
        boolean dirty = false;
        int doubleOldIndex = -1;
        Bitmap lastBitmap;
        int lastIndex = -1;
        BitmapLoaderThread loaderThread;
        int oldIndex = -1;
        Random r = new Random();

        class BitmapLoaderThread extends Thread {
            Bitmap bitmap = null;
            Context context;
            int resourceId;

            public BitmapLoaderThread(Context c, int id) {
                this.context = c;
                this.resourceId = id;
            }

            public void run() {
                this.bitmap = BitmapHelper.decodeResourceWithDownsample(this.context.getResources(), this.resourceId, 32);
            }
        }

        private int pickAndRememberFamousBitmap() {
            this.doubleOldIndex = this.oldIndex;
            this.oldIndex = this.lastIndex;
            do {
                this.lastIndex = FamousPicker.pickFamousBitmap(this.r);
                if (this.lastIndex != this.oldIndex) {
                    break;
                }
            } while (FamousPicker.famousers.length > 1);
            if (this.oldIndex == -1) {
                this.oldIndex = this.lastIndex;
            }
            if (this.doubleOldIndex == -1) {
                this.doubleOldIndex = this.oldIndex;
            }
            return this.lastIndex;
        }

        public void loadNewImage(Context c) {
            if (this.loaderThread != null) {
                try {
                    this.loaderThread.join();
                } catch (InterruptedException e) {
                }
                this.lastBitmap = this.loaderThread.bitmap;
            }
            this.loaderThread = new BitmapLoaderThread(c, pickAndRememberFamousBitmap());
            this.loaderThread.start();
            this.dirty = true;
        }

        public Bitmap loadOldImage(Context c) {
            return BitmapHelper.decodeResourceWithDownsample(c.getResources(), this.oldIndex, 32);
        }

        public boolean dirty() {
            return this.dirty;
        }

        public void setDirty(boolean dirty) {
            this.dirty = dirty;
        }

        public Bitmap getBitmap(Context c) {
            Bitmap b = null;
            if (this.lastBitmap != null) {
                b = this.lastBitmap;
            }
            if (this.loaderThread == null) {
                this.loaderThread = new BitmapLoaderThread(c, pickAndRememberFamousBitmap());
                this.loaderThread.start();
            }
            if (b != null) {
                return b;
            }
            try {
                this.loaderThread.join();
            } catch (InterruptedException e) {
            }
            b = this.loaderThread.bitmap;
            this.lastBitmap = b;
            this.loaderThread = new BitmapLoaderThread(c, pickAndRememberFamousBitmap());
            this.loaderThread.start();
            return b;
        }

        public boolean hasImage() {
            return this.loaderThread != null;
        }

        public boolean doRecycle() {
            return false;
        }
    }

    public static int pickFamousBitmap(Random r) {
        return famousers[r.nextInt(famousers.length)];
    }
}
