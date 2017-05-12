package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class BugEyesPointProvider extends ListPointProvider {
    public BugEyesPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.5198236703872681d, -0.4140968918800354d, PointType.LEFT_EYE));
        this.pointsStart.add(new TPnt(-0.531571090221405d, -0.09104257822036743d, PointType.LEFT_EYE));
        this.pointsStart.add(new TPnt(-0.1409692019224167d, -0.3817914128303528d, PointType.LEFT_EYE));
        this.pointsStart.add(new TPnt(-0.15859030187129974d, -0.08223201334476471d, PointType.LEFT_EYE));
        this.pointsStart.add(new TPnt(0.23494860529899597d, -0.4199706017971039d, PointType.RIGHT_EYE));
        this.pointsStart.add(new TPnt(0.6431717872619629d, -0.41116011142730713d, PointType.RIGHT_EYE));
        this.pointsStart.add(new TPnt(0.23788540065288544d, -0.06754770874977112d, PointType.RIGHT_EYE));
        this.pointsStart.add(new TPnt(0.6431717872619629d, -0.03817914053797722d, PointType.RIGHT_EYE));
        this.pointsStart.add(new TPnt(0.04698970913887024d, -0.04698970913887024d, PointType.FACE));
        this.pointsStart.add(new TPnt(0.04111599177122116d, 0.2232012003660202d, PointType.FACE));
        this.pointsStart.add(new TPnt(0.04992656037211418d, -0.39060211181640625d, PointType.FACE));
        this.pointsStart.add(new TPnt(0.05873715877532959d, -0.7371513247489929d, PointType.FACE));
        this.pointsStart.add(new TPnt(-0.5874451994895935d, -0.24153409898281097d, PointType.LEFT_EYE));
        this.pointsStart.add(new TPnt(-0.32306331396102905d, -0.4830681085586548d, PointType.LEFT_EYE));
        this.pointsStart.add(new TPnt(-0.32959121465682983d, 0.0065279509872198105d, PointType.LEFT_EYE));
        this.pointsStart.add(new TPnt(-0.10111310333013535d, -0.22521419823169708d, PointType.LEFT_EYE));
        this.pointsStart.add(new TPnt(0.17632469534873962d, -0.22847819328308105d, PointType.RIGHT_EYE));
        this.pointsStart.add(new TPnt(0.4178588092327118d, 0.02284781076014042d, PointType.RIGHT_EYE));
        this.pointsStart.add(new TPnt(0.44070661067962646d, -0.476540207862854d, PointType.RIGHT_EYE));
        this.pointsStart.add(new TPnt(0.718144416809082d, -0.23174209892749786d, PointType.RIGHT_EYE));
        this.pointsTarget.add(new Pnt(-0.590308427810669d, -0.6167401075363159d));
        this.pointsTarget.add(new Pnt(-0.6607930064201355d, 0.09397946298122406d));
        this.pointsTarget.add(new Pnt(-0.1116006001830101d, -0.6138033866882324d));
        this.pointsTarget.add(new Pnt(-0.08223201334476471d, 0.07929515838623047d));
        this.pointsTarget.add(new Pnt(0.2143906056880951d, -0.6372979283332825d));
        this.pointsTarget.add(new Pnt(0.7753304839134216d, -0.6167401075363159d));
        this.pointsTarget.add(new Pnt(0.11453740298748016d, 0.09397946298122406d));
        this.pointsTarget.add(new Pnt(0.7782672047615051d, 0.1850219964981079d));
        this.pointsTarget.add(new Pnt(0.04698970913887024d, -0.038179170340299606d));
        this.pointsTarget.add(new Pnt(0.04111602157354355d, 0.22613799571990967d));
        this.pointsTarget.add(new Pnt(0.04992659017443657d, -0.3876650929450989d));
        this.pointsTarget.add(new Pnt(0.05873715877532959d, -0.7400879859924316d));
        this.pointsTarget.add(new Pnt(-0.7344642877578735d, -0.24479800462722778d));
        this.pointsTarget.add(new Pnt(-0.32646751403808594d, -0.7050182819366455d));
        this.pointsTarget.add(new Pnt(-0.31993961334228516d, 0.16319869458675385d));
        this.pointsTarget.add(new Pnt(-0.09798934310674667d, -0.22521419823169708d));
        this.pointsTarget.add(new Pnt(0.15660059452056885d, -0.2284781038761139d));
        this.pointsTarget.add(new Pnt(0.4209825098514557d, 0.2219502031803131d));
        this.pointsTarget.add(new Pnt(0.46015021204948425d, -0.7180743217468262d));
        this.pointsTarget.add(new Pnt(0.9073145985603333d, -0.23174209892749786d));
    }
}
