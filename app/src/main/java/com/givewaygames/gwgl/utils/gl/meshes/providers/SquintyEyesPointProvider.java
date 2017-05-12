package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class SquintyEyesPointProvider extends ListPointProvider {
    public SquintyEyesPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.5198236703872681d, -0.4140968918800354d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.531571090221405d, -0.09104257822036743d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.1409692019224167d, -0.3817914128303528d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.15859030187129974d, -0.08223201334476471d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.22851769626140594d, -0.374954491853714d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.6431717872619629d, -0.41116011142730713d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.23788540065288544d, -0.06754770874977112d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.6402348875999451d, -0.0734214335680008d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.04698970913887024d, -0.04698970913887024d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.04111599177122116d, 0.2232012003660202d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.04992656037211418d, -0.39060211181640625d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.05873715877532959d, -0.7371513247489929d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.1262848973274231d, 0.06461086124181747d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.7635831236839294d, 0.026431720703840256d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.22613810002803802d, 0.07048459351062775d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.851688802242279d, 0.07048459351062775d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.11747430264949799d, -0.4904552102088928d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.731277585029602d, -0.5315713286399841d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.21271909773349762d, -0.4977455139160156d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.8575624823570251d, -0.5403817892074585d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.5374451279640198d, -0.2907490134239197d));
        this.pointsTarget.add(new Pnt(-0.5374451279640198d, -0.1997063010931015d));
        this.pointsTarget.add(new Pnt(-0.1798093020915985d, -0.28836938738822937d));
        this.pointsTarget.add(new Pnt(-0.18274620175361633d, -0.18795889616012573d));
        this.pointsTarget.add(new Pnt(0.19828979671001434d, -0.2977367043495178d));
        this.pointsTarget.add(new Pnt(0.634361207485199d, -0.2819383144378662d));
        this.pointsTarget.add(new Pnt(0.19241610169410706d, -0.17788289487361908d));
        this.pointsTarget.add(new Pnt(0.6402348875999451d, -0.1850219964981079d));
        this.pointsTarget.add(new Pnt(0.04698970913887024d, -0.038179170340299606d));
        this.pointsTarget.add(new Pnt(0.04111602157354355d, 0.22613799571990967d));
        this.pointsTarget.add(new Pnt(0.04992659017443657d, -0.3876650929450989d));
        this.pointsTarget.add(new Pnt(0.05873715877532959d, -0.7400879859924316d));
        this.pointsTarget.add(new Pnt(-0.12334799766540527d, 0.06461088359355927d));
        this.pointsTarget.add(new Pnt(-0.7635831236839294d, 0.02349485084414482d));
        this.pointsTarget.add(new Pnt(0.2232012003660202d, 0.0734214335680008d));
        this.pointsTarget.add(new Pnt(0.8546255230903625d, 0.07048459351062775d));
        this.pointsTarget.add(new Pnt(-0.12334799766540527d, -0.4933921992778778d));
        this.pointsTarget.add(new Pnt(-0.731277585029602d, -0.5374447703361511d));
        this.pointsTarget.add(new Pnt(0.216213196516037d, -0.4913145899772644d));
        this.pointsTarget.add(new Pnt(0.8546255230903625d, -0.5433186888694763d));
    }
}
