package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class MonkeyMouthPointProvider extends ListPointProvider {
    public MonkeyMouthPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.1262059062719345d, 0.3159010112285614d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.1732243001461029d, 0.3074176013469696d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.12326899915933609d, 0.4799731969833374d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.1693062037229538d, 0.48682069778442383d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.9118279218673706d, 0.02216479927301407d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.868414580821991d, 0.0835261270403862d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.9451218247413635d, 0.9404773712158203d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.8893070220947266d, 0.9303584098815918d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.009862048551440239d, 0.26764580607414246d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.016389990225434303d, 0.5059158205986023d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.1827124059200287d, 0.3982048034667969d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.2611880898475647d, 0.4014689028263092d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.20227280259132385d, 0.273106187582016d));
        this.pointsTarget.add(new Pnt(0.2293805032968521d, 0.2388381063938141d));
        this.pointsTarget.add(new Pnt(-0.1761540025472641d, 0.5531107783317566d));
        this.pointsTarget.add(new Pnt(0.22088250517845154d, 0.5733488202095032d));
        this.pointsTarget.add(new Pnt(-0.9118279218673706d, 0.025101669132709503d));
        this.pointsTarget.add(new Pnt(0.8713514804840088d, 0.08646293729543686d));
        this.pointsTarget.add(new Pnt(-0.9421849250793457d, 0.9398232102394104d));
        this.pointsTarget.add(new Pnt(0.8896340727806091d, 0.9293770790100098d));
        this.pointsTarget.add(new Pnt(0.012985769659280777d, 0.1827826052904129d));
        this.pointsTarget.add(new Pnt(0.012985769659280777d, 0.580987274646759d));
        this.pointsTarget.add(new Pnt(-0.41785889863967896d, 0.40473300218582153d));
        this.pointsTarget.add(new Pnt(0.4829980134963989d, 0.40799659490585327d));
    }
}
