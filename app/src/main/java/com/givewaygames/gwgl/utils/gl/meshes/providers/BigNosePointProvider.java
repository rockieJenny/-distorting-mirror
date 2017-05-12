package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;

public class BigNosePointProvider extends CombinedPointProvider {
    float growSize = 0.2f;
    float noseSize = 0.1f;
    float outerSize = 0.3f;
    float outerSizeFade = 0.4f;

    public BigNosePointProvider() {
        addMeshPointProvider(new CirclePointProvider(10, (double) this.noseSize, new Pnt(0.0d, 0.0d)));
        addMeshPointProvider(new CirclePointProvider(10, (double) this.outerSize, new Pnt(0.0d, 0.0d)));
    }

    public void updateNoseBigPosition(double x, double y) {
        CirclePointProvider inner = (CirclePointProvider) this.points.get(0);
        CirclePointProvider outer = (CirclePointProvider) this.points.get(1);
        inner.setCenter(x, y);
        outer.setCenter(x, y);
        inner.setRadius((double) this.growSize);
        outer.setRadius((double) this.outerSizeFade);
    }

    public void updateNoseSmallPosition() {
        ((CirclePointProvider) this.points.get(0)).setRadius((double) this.noseSize);
    }
}
