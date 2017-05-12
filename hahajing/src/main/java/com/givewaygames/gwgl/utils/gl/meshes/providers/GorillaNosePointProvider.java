package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class GorillaNosePointProvider extends ListPointProvider {
    public GorillaNosePointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.15937499701976776d, -0.41333329677581787d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.8827083706855774d, -0.42666658759117126d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.4027082920074463d, 0.5199999809265137d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.4972917139530182d, 0.20000000298023224d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.3972916007041931d, 0.5199999809265137d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.14604170620441437d, 0.366666704416275d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.18395839631557465d, 0.366666704416275d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.140625d, 0.06333331018686295d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.08937499672174454d, 0.06666667014360428d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.3006249964237213d, -0.383333295583725d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.6939582824707031d, -0.8399999737739563d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.9560415744781494d, -0.9633334279060364d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.9560415744781494d, 0.9666666984558105d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.9606249928474426d, -0.9566665887832642d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.9639583230018616d, 0.9700000286102295d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.4893749952316284d, 0.17666670680046082d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.04729166999459267d, 0.7766665816307068d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.16062499582767487d, -0.4166666865348816d));
        this.pointsTarget.add(new Pnt(-0.8839582800865173d, -0.41999998688697815d));
        this.pointsTarget.add(new Pnt(-0.40062499046325684d, 0.5233333706855774d));
        this.pointsTarget.add(new Pnt(0.49937498569488525d, 0.20000000298023224d));
        this.pointsTarget.add(new Pnt(0.3960416913032532d, 0.5266667008399963d));
        this.pointsTarget.add(new Pnt(-0.16395829617977142d, 0.11999999731779099d));
        this.pointsTarget.add(new Pnt(0.20604169368743896d, 0.10333330184221268d));
        this.pointsTarget.add(new Pnt(0.11270839720964432d, -0.11666660010814667d));
        this.pointsTarget.add(new Pnt(-0.0739583894610405d, -0.10666660219430923d));
        this.pointsTarget.add(new Pnt(0.3060416877269745d, -0.3866666853427887d));
        this.pointsTarget.add(new Pnt(0.6993749737739563d, -0.8433333039283752d));
        this.pointsTarget.add(new Pnt(-0.9606249928474426d, -0.9633334279060364d));
        this.pointsTarget.add(new Pnt(-0.9572917222976685d, 0.9666666984558105d));
        this.pointsTarget.add(new Pnt(0.9593750238418579d, -0.9599999785423279d));
        this.pointsTarget.add(new Pnt(0.9693750143051147d, 0.9800000190734863d));
        this.pointsTarget.add(new Pnt(-0.48729169368743896d, 0.18666669726371765d));
        this.pointsTarget.add(new Pnt(0.05270836129784584d, 0.7766665816307068d));
    }
}
