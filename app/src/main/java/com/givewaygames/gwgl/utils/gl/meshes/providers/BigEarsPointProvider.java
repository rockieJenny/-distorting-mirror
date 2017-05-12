package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class BigEarsPointProvider extends ListPointProvider {
    public BigEarsPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.41997069120407104d, 0.3054332137107849d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.6108664274215698d, -0.3201175034046173d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.5726872086524963d, -0.35242289304733276d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.47283411026000977d, 0.3083699941635132d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.7077826857566833d, 0.09397944808006287d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.6637297868728638d, 0.09985315054655075d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.035242289304733276d, -0.17327460646629333d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.487518310546875d, 0.27606460452079773d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.458149790763855d, -0.08516886830329895d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.47870779037475586d, -0.08810573071241379d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.026431720703840256d, 0.44052860140800476d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.39647579193115234d, 0.31424379348754883d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.46108660101890564d, -0.3083699941635132d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.45521289110183716d, -0.34948599338531494d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.39353880286216736d, -0.5433186292648315d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.3876652121543884d, -0.5550659894943237d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.4375917911529541d, 0.4963288903236389d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.4111599922180176d, 0.5198237895965576d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.4229075014591217d, 0.30249640345573425d));
        this.pointsTarget.add(new Pnt(-0.7342144250869751d, -0.46402350068092346d));
        this.pointsTarget.add(new Pnt(0.8135095238685608d, -0.44640231132507324d));
        this.pointsTarget.add(new Pnt(0.5374448895454407d, 0.42290741205215454d));
        this.pointsTarget.add(new Pnt(-0.9809104800224304d, 0.07342146337032318d));
        this.pointsTarget.add(new Pnt(0.9603524208068848d, 0.08516886085271835d));
        this.pointsTarget.add(new Pnt(0.035242289304733276d, -0.17327460646629333d));
        this.pointsTarget.add(new Pnt(-0.622613787651062d, 0.32305431365966797d));
        this.pointsTarget.add(new Pnt(-0.458149790763855d, -0.08223201334476471d));
        this.pointsTarget.add(new Pnt(0.47870779037475586d, -0.08810575306415558d));
        this.pointsTarget.add(new Pnt(0.03230541944503784d, 0.44346559047698975d));
        this.pointsTarget.add(new Pnt(0.399412602186203d, 0.3171806037425995d));
        this.pointsTarget.add(new Pnt(-0.4581497013568878d, -0.3054332137107849d));
        this.pointsTarget.add(new Pnt(0.45521289110183716d, -0.34948599338531494d));
        this.pointsTarget.add(new Pnt(-0.3935388922691345d, -0.5491923093795776d));
        this.pointsTarget.add(new Pnt(0.3935388922691345d, -0.5491924285888672d));
        this.pointsTarget.add(new Pnt(-0.4346548914909363d, 0.5080763101577759d));
        this.pointsTarget.add(new Pnt(0.41116011142730713d, 0.5256975293159485d));
    }
}
