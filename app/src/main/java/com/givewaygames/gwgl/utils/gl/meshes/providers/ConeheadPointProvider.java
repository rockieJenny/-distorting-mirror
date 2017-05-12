package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class ConeheadPointProvider extends ListPointProvider {
    public ConeheadPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.4640234112739563d, -0.4140968918800354d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.5085400938987732d, 0.29744940996170044d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.502202570438385d, -0.4170336127281189d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.20028850436210632d, -0.5566657185554504d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.29591068625450134d, -0.5687475204467773d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.48313820362091064d, 0.306813508272171d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.058821648359298706d, -0.6593226790428162d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.37528690695762634d, 0.6886984705924988d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.34278741478919983d, 0.6723785996437073d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.3008247911930084d, -0.5123432874679565d));
        this.pointsTarget.add(new Pnt(-0.47296351194381714d, 0.22890600562095642d));
        this.pointsTarget.add(new Pnt(0.37197089195251465d, -0.5240907073020935d));
        this.pointsTarget.add(new Pnt(-0.06743983179330826d, -0.706096887588501d));
        this.pointsTarget.add(new Pnt(0.1787348985671997d, -0.7090334892272949d));
        this.pointsTarget.add(new Pnt(0.45362231135368347d, 0.24153409898281097d));
        this.pointsTarget.add(new Pnt(0.06194537878036499d, -0.9106485843658447d));
        this.pointsTarget.add(new Pnt(-0.46355441212654114d, 0.6952263712882996d));
        this.pointsTarget.add(new Pnt(0.4373024106025696d, 0.6821706295013428d));
    }
}
