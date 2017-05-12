package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class FatChinPointProvider extends ListPointProvider {
    public FatChinPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.3582966923713684d, 0.6255505084991455d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.42584431171417236d, 0.6284875869750977d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.020558010786771774d, 0.6930983066558838d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.4552130103111267d, 0.3935387134552002d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.5168870091438293d, 0.12334799766540527d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.5256974101066589d, 0.12922179698944092d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.4082232117652893d, 0.40822309255599976d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.023494800552725792d, 0.38179150223731995d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.5991188883781433d, 0.7694569230079651d));
        this.pointsTarget.add(new Pnt(0.651982307434082d, 0.8340675830841064d));
        this.pointsTarget.add(new Pnt(0.005873632151633501d, 0.9897211194038391d));
        this.pointsTarget.add(new Pnt(0.5374451279640198d, 0.4111602008342743d));
        this.pointsTarget.add(new Pnt(-0.5873715281486511d, 0.12041120231151581d));
        this.pointsTarget.add(new Pnt(0.596182107925415d, 0.12922169268131256d));
        this.pointsTarget.add(new Pnt(-0.5051395297050476d, 0.42878109216690063d));
        this.pointsTarget.add(new Pnt(0.020558040589094162d, 0.3876650929450989d));
    }
}
