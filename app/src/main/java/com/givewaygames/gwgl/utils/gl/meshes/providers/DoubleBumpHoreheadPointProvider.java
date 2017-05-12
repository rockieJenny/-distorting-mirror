package com.givewaygames.gwgl.utils.gl.meshes.providers;

import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.gl.delaunay.Pnt;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.PointType;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider.TPnt;

public class DoubleBumpHoreheadPointProvider extends ListPointProvider {
    public DoubleBumpHoreheadPointProvider(GLHelper glHelper) {
        super(glHelper);
    }

    public void setupPoints() {
        this.pointsStart.add(new TPnt(-0.3640233874320984d, -0.2640968859195709d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.03953009843826294d, 0.085903100669384d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.43886929750442505d, -0.270366907119751d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.35751819610595703d, -0.4938913881778717d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.41744479537010193d, -0.5266667008399963d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.019374990835785866d, -0.5866665840148926d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.09604167193174362d, -0.5033332705497742d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.11937499791383743d, -0.22333329916000366d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.16729170083999634d, -0.5066667199134827d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.16062499582767487d, -0.2199999988079071d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.06729166954755783d, -0.15333330631256104d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.009375003166496754d, -0.15333330631256104d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.09729164093732834d, -0.5899999737739563d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.25125589966773987d, -0.16319869458675385d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.30688369274139404d, -0.19257450103759766d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.22188009321689606d, -0.551611602306366d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.29056379199028015d, -0.5614035129547119d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(-0.42424649000167847d, -0.3720931112766266d, PointType.UNKNOWN));
        this.pointsStart.add(new TPnt(0.5125141143798828d, -0.40146881341934204d, PointType.UNKNOWN));
        this.pointsTarget.add(new Pnt(-0.47735679149627686d, -0.23782679438591003d));
        this.pointsTarget.add(new Pnt(0.04246694967150688d, 0.09256979078054428d));
        this.pointsTarget.add(new Pnt(0.5259326100349426d, -0.2329075038433075d));
        this.pointsTarget.add(new Pnt(-0.4347429871559143d, -0.5580471158027649d));
        this.pointsTarget.add(new Pnt(0.5178413987159729d, -0.5509837865829468d));
        this.pointsTarget.add(new Pnt(-0.02069436013698578d, -0.5800694227218628d));
        this.pointsTarget.add(new Pnt(-0.09729169309139252d, -0.5d));
        this.pointsTarget.add(new Pnt(-0.1239582970738411d, -0.2199999988079071d));
        this.pointsTarget.add(new Pnt(0.1693750023841858d, -0.5033332705497742d));
        this.pointsTarget.add(new Pnt(0.16270829737186432d, -0.20999999344348907d));
        this.pointsTarget.add(new Pnt(0.06604167073965073d, -0.15000000596046448d));
        this.pointsTarget.add(new Pnt(-0.00395833607763052d, -0.15000000596046448d));
        this.pointsTarget.add(new Pnt(0.10270830243825912d, -0.5899999737739563d));
        this.pointsTarget.add(new Pnt(-0.2970918118953705d, -0.09465525299310684d));
        this.pointsTarget.add(new Pnt(0.3491750955581665d, -0.11423909664154053d));
        this.pointsTarget.add(new Pnt(-0.22202029824256897d, -0.6625866889953613d));
        this.pointsTarget.add(new Pnt(0.2643117904663086d, -0.6723785996437073d));
        this.pointsTarget.add(new Pnt(-0.545153796672821d, -0.37862101197242737d));
        this.pointsTarget.add(new Pnt(0.6233488917350769d, -0.40146881341934204d));
    }
}
