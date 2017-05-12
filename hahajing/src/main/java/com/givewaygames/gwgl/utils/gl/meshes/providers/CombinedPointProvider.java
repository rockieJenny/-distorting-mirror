package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.gl.delaunay.Point;
import com.givewaygames.gwgl.utils.gl.delaunay.Triangle;
import com.givewaygames.gwgl.utils.gl.meshes.GLEquationMesh.MeshPointProvider;
import java.util.ArrayList;
import java.util.List;

public class CombinedPointProvider implements MeshPointProvider {
    int meshIdx = 0;
    List<MeshPointProvider> points = new ArrayList();

    public void addMeshPointProvider(MeshPointProvider meshPointProvider) {
        this.points.add(meshPointProvider);
    }

    public boolean hasMore() {
        while (this.meshIdx < this.points.size()) {
            boolean hasMore = ((MeshPointProvider) this.points.get(this.meshIdx)).hasMore();
            if (!hasMore) {
                this.meshIdx++;
                continue;
            }
            if (hasMore) {
                return true;
            }
        }
        return false;
    }

    public Point nextPoint() {
        return ((MeshPointProvider) this.points.get(this.meshIdx)).nextPoint();
    }

    public void reset() {
        this.meshIdx = 0;
        for (MeshPointProvider mpp : this.points) {
            mpp.reset();
        }
    }

    public boolean isValid(Triangle triangle) {
        return true;
    }
}
