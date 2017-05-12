package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class ChubbyCheeksPointProvider extends ListPointProvider {
    public ChubbyCheeksPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(0.04729166999459267d, -0.34333330392837524d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.03729166090488434d, 0.27666670083999634d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.030625000596046448d, 0.9300000071525574d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.16270829737186432d, 0.07666666805744171d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.19090239703655243d, 0.07340268045663834d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.17270830273628235d, 0.4766666889190674d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.25007009506225586d, 0.5029171705245972d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.40937501192092896d, 0.14000000059604645d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.46395841240882874d, 0.14666670560836792d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.4086120128631592d, 0.47965309023857117d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.4463205933570862d, 0.4993757903575897d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.4893749952316284d, -0.2033333033323288d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.590624988079071d, -0.17000000178813934d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.1304887980222702d, 0.2578538954257965d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.2154923975467682d, 0.2970215976238251d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.03923780843615532d, -0.11423909664154053d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.10764099657535553d, 0.7017542719841003d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.1991724967956543d, 0.7017542719841003d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(0.05270833149552345d, -0.4166666865348816d));
        this.pointsTarget.add(new Pnt(0.039374999701976776d, 0.28333330154418945d));
        this.pointsTarget.add(new Pnt(0.029374999925494194d, 0.9300000071525574d));
        this.pointsTarget.add(new Pnt(-0.1357657015323639d, 0.056389220058918d));
        this.pointsTarget.add(new Pnt(0.18472379446029663d, 0.06298653781414032d));
        this.pointsTarget.add(new Pnt(-0.17222429811954498d, 0.4889554977416992d));
        this.pointsTarget.add(new Pnt(0.26437708735466003d, 0.51513671875d));
        this.pointsTarget.add(new Pnt(-0.57729172706604d, 0.18000000715255737d));
        this.pointsTarget.add(new Pnt(0.6293749809265137d, 0.20999999344348907d));
        this.pointsTarget.add(new Pnt(-0.4927099049091339d, 0.6125010251998901d));
        this.pointsTarget.add(new Pnt(0.5813906192779541d, 0.6320849061012268d));
        this.pointsTarget.add(new Pnt(-0.5439584255218506d, -0.2333333045244217d));
        this.pointsTarget.add(new Pnt(0.65604168176651d, -0.20999999344348907d));
        this.pointsTarget.add(new Pnt(-0.13062910735607147d, 0.26111799478530884d));
        this.pointsTarget.add(new Pnt(0.21861609816551208d, 0.30354949831962585d));
        this.pointsTarget.add(new Pnt(0.03909755125641823d, -0.10771109908819199d));
        this.pointsTarget.add(new Pnt(-0.10451730340719223d, 0.7115464210510254d));
        this.pointsTarget.add(new Pnt(0.1957682967185974d, 0.6984903812408447d));
    }
}
