package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class PointyChinPointProvider extends ListPointProvider {
    public PointyChinPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.36710721254348755d, 0.44640231132507324d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.4023495018482208d, 0.4669603109359741d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.04111599922180176d, 0.45521289110183716d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.2320117950439453d, 0.6754772067070007d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.311306893825531d, 0.6402348875999451d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.02936857007443905d, 0.7518355250358582d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.3612334132194519d, 0.45227599143981934d));
        this.pointsTarget.add(new Pnt(0.4082232117652893d, 0.46989721059799194d));
        this.pointsTarget.add(new Pnt(0.04698970913887024d, 0.4552130103111267d));
        this.pointsTarget.add(new Pnt(-0.14390599727630615d, 0.6754772067070007d));
        this.pointsTarget.add(new Pnt(0.19676950573921204d, 0.660792887210846d));
        this.pointsTarget.add(new Pnt(0.026431720703840256d, 0.9779735207557678d));
    }
}
