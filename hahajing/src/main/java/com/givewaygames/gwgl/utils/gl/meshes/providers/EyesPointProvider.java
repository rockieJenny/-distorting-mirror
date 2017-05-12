package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;

public class EyesPointProvider extends CombinedPointProvider {
    float big = 0.15f;
    float offsetX = 0.15f;
    float offsetY = 0.17f;
    float small = 0.05f;

    public EyesPointProvider() {
        addMeshPointProvider(new CirclePointProvider(10, (double) this.small, new Pnt((double) this.offsetX, (double) (-this.offsetY))));
        addMeshPointProvider(new CirclePointProvider(10, (double) this.small, new Pnt((double) this.offsetX, (double) this.offsetY)));
    }

    public void updateNoseBigPosition(double x, double y) {
        CirclePointProvider left = (CirclePointProvider) this.points.get(0);
        CirclePointProvider right = (CirclePointProvider) this.points.get(1);
        left.setCenter(((double) this.offsetX) + x, y - ((double) this.offsetY));
        right.setCenter(((double) this.offsetX) + x, ((double) this.offsetY) + y);
        left.setRadius((double) this.big);
        right.setRadius((double) this.big);
    }

    public void updateNoseSmallPosition() {
        CirclePointProvider right = (CirclePointProvider) this.points.get(1);
        ((CirclePointProvider) this.points.get(0)).setRadius((double) this.small);
        right.setRadius((double) this.small);
    }
}
