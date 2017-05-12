package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class TinyMouthPointProvider extends ListPointProvider {
    public TinyMouthPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.44933921098709106d, 0.21145370602607727d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.3788546025753021d, 0.1997063010931015d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.44640231132507324d, 0.6431717872619629d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.3847283124923706d, 0.6696034073829651d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.5168868899345398d, 0.12334799766540527d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.5256975293159485d, 0.12922169268131256d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.5110132098197937d, 0.8164464831352234d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.513949990272522d, 0.8193832039833069d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.38179150223731995d, 0.36123359203338623d));
        this.pointsTarget.add(new Pnt(0.31424370408058167d, 0.34654921293258667d));
        this.pointsTarget.add(new Pnt(-0.4111599922180176d, 0.5433186292648315d));
        this.pointsTarget.add(new Pnt(0.36123350262641907d, 0.5374450087547302d));
        this.pointsTarget.add(new Pnt(-0.5168868899345398d, 0.1262848973274231d));
        this.pointsTarget.add(new Pnt(0.5286344289779663d, 0.13215860724449158d));
        this.pointsTarget.add(new Pnt(-0.5080764293670654d, 0.8223202228546143d));
        this.pointsTarget.add(new Pnt(0.5110132098197937d, 0.828193724155426d));
    }
}
