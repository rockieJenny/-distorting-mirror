package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class BigForeheadPointProvider extends ListPointProvider {
    public BigForeheadPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.4640234112739563d, -0.4140968918800354d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.05286344140768051d, -0.4140968918800354d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.49241068959236145d, -0.4170336127281189d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.48751819133758545d, -0.6872246861457825d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.5374447703361511d, -0.6666666865348816d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.06861363351345062d, 0.855161190032959d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.43791159987449646d, -0.18268199265003204d));
        this.pointsTarget.add(new Pnt(0.04600837081670761d, -0.18235479295253754d));
        this.pointsTarget.add(new Pnt(0.44051429629325867d, -0.15526169538497925d));
        this.pointsTarget.add(new Pnt(-0.5080763101577759d, -0.9280471205711365d));
        this.pointsTarget.add(new Pnt(0.5345079898834229d, -0.9309837818145752d));
        this.pointsTarget.add(new Pnt(0.06847337633371353d, 0.8616892099380493d));
    }
}
