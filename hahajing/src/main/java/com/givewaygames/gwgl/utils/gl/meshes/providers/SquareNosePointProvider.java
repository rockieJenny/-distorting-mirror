package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class SquareNosePointProvider extends ListPointProvider {
    public SquareNosePointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.15937499701976776d, -0.41333329677581787d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.34937500953674316d, -0.11999999731779099d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.3660416901111603d, 0.3266667127609253d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.42395830154418945d, -0.09666665643453598d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.41729170083999634d, 0.3199999928474426d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.032708361744880676d, 0.2966667115688324d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.10062500089406967d, 0.30000001192092896d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.09395836293697357d, -0.06666669249534607d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.007291671819984913d, -0.05999999865889549d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.3006249964237213d, -0.383333295583725d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.2839582860469818d, 0.5266667008399963d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.9560415744781494d, -0.9633334279060364d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.9560415744781494d, 0.9666666984558105d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.9606249928474426d, -0.9566665887832642d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.9639583230018616d, 0.9700000286102295d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.053958311676979065d, -0.41333329677581787d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.15937499701976776d, 0.533333420753479d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.16062499582767487d, -0.4166666865348816d));
        this.pointsTarget.add(new Pnt(-0.3539583086967468d, -0.11999999731779099d));
        this.pointsTarget.add(new Pnt(-0.37062498927116394d, 0.3266667127609253d));
        this.pointsTarget.add(new Pnt(0.42604169249534607d, -0.10000000149011612d));
        this.pointsTarget.add(new Pnt(0.40937501192092896d, 0.3199999928474426d));
        this.pointsTarget.add(new Pnt(-0.140625d, 0.30000001192092896d));
        this.pointsTarget.add(new Pnt(0.2227082997560501d, 0.30666670203208923d));
        this.pointsTarget.add(new Pnt(0.216041699051857d, -0.04999997094273567d));
        this.pointsTarget.add(new Pnt(-0.140625d, -0.046666670590639114d));
        this.pointsTarget.add(new Pnt(0.3060416877269745d, -0.3866666853427887d));
        this.pointsTarget.add(new Pnt(0.29270830750465393d, 0.5199999809265137d));
        this.pointsTarget.add(new Pnt(-0.9606249928474426d, -0.9633334279060364d));
        this.pointsTarget.add(new Pnt(-0.9572917222976685d, 0.9666666984558105d));
        this.pointsTarget.add(new Pnt(0.9593750238418579d, -0.9599999785423279d));
        this.pointsTarget.add(new Pnt(0.9693750143051147d, 0.9800000190734863d));
        this.pointsTarget.add(new Pnt(0.04604167118668556d, -0.41333329677581787d));
        this.pointsTarget.add(new Pnt(-0.16395829617977142d, 0.533333420753479d));
    }
}
