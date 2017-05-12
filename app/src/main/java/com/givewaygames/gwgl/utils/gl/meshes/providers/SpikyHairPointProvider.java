package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class SpikyHairPointProvider extends ListPointProvider {
    public SpikyHairPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.46069011092185974d, -0.3540968894958496d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.05286344140768051d, -0.4140968918800354d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.5255358815193176d, -0.3637003004550934d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.48085159063339233d, -0.6138914227485657d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.5207781195640564d, -0.6133332848548889d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.3427082896232605d, -0.6600000262260437d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.08270833641290665d, -0.7233334183692932d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.2839582860469818d, -0.7200000286102295d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.2060416042804718d, -0.7066665887832642d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.41062501072883606d, -0.6700000166893005d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.14729170501232147d, -0.7300000190734863d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.04062499850988388d, -0.7333332896232605d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.35729169845581055d, -0.3866666853427887d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.2227082997560501d, -0.383333295583725d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.40937501192092896d, -0.9766666889190674d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.47729170322418213d, -0.9800000190734863d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.10604169964790344d, -0.9900000095367432d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.1706250011920929d, -0.9866666793823242d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.5606901049613953d, -0.3278267979621887d));
        this.pointsTarget.add(new Pnt(0.055800288915634155d, -0.4140968918800354d));
        this.pointsTarget.add(new Pnt(0.6125991940498352d, -0.3329075872898102d));
        this.pointsTarget.add(new Pnt(-0.6014096140861511d, -0.8247138261795044d));
        this.pointsTarget.add(new Pnt(0.6645079851150513d, -0.7109838128089905d));
        this.pointsTarget.add(new Pnt(-0.32729169726371765d, -0.6066666841506958d));
        this.pointsTarget.add(new Pnt(-0.07395832985639572d, -0.6733332872390747d));
        this.pointsTarget.add(new Pnt(0.2993749976158142d, -0.9100000262260437d));
        this.pointsTarget.add(new Pnt(-0.2172916978597641d, -0.9066666960716248d));
        this.pointsTarget.add(new Pnt(0.3760417103767395d, -0.6066666841506958d));
        this.pointsTarget.add(new Pnt(0.14604170620441437d, -0.6700000166893005d));
        this.pointsTarget.add(new Pnt(0.04604167118668556d, -0.9200000166893005d));
        this.pointsTarget.add(new Pnt(0.35604169964790344d, -0.383333295583725d));
        this.pointsTarget.add(new Pnt(-0.22395829856395721d, -0.383333295583725d));
        this.pointsTarget.add(new Pnt(-0.4139583110809326d, -0.9833332896232605d));
        this.pointsTarget.add(new Pnt(0.4793750047683716d, -0.9833332896232605d));
        this.pointsTarget.add(new Pnt(-0.10395830124616623d, -0.9833332896232605d));
        this.pointsTarget.add(new Pnt(0.1693750023841858d, -0.9833332896232605d));
    }
}
