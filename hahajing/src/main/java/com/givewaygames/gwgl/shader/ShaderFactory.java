package com.givewaygames.gwgl.shader;

import android.content.Context;
import android.util.SparseArray;
import com.givewaygames.gwgl.CameraWrapper.MeshOrientation;
import com.givewaygames.gwgl.R;
import com.givewaygames.gwgl.shader.GLVariable.BinaryRandomizer;
import com.givewaygames.gwgl.shader.GLVariable.ExponentialRandomizer;
import com.givewaygames.gwgl.shader.pixel.BasicPixelShader;
import com.givewaygames.gwgl.shader.pixel.BloomPixelShader;
import com.givewaygames.gwgl.shader.pixel.CellPixelShader;
import com.givewaygames.gwgl.shader.pixel.CellShadedPixelShader;
import com.givewaygames.gwgl.shader.pixel.CircularRipplePixelShader;
import com.givewaygames.gwgl.shader.pixel.ColorMatrixPixelShader;
import com.givewaygames.gwgl.shader.pixel.DespecklePixelShader;
import com.givewaygames.gwgl.shader.pixel.DilatePixelShader;
import com.givewaygames.gwgl.shader.pixel.EmbossPixelShader;
import com.givewaygames.gwgl.shader.pixel.ExtractColorPixelShader;
import com.givewaygames.gwgl.shader.pixel.FrozenGlassPixelShader;
import com.givewaygames.gwgl.shader.pixel.GlassSpherePixelShader;
import com.givewaygames.gwgl.shader.pixel.HueShiftPixelShader;
import com.givewaygames.gwgl.shader.pixel.MirrorPixelShader;
import com.givewaygames.gwgl.shader.pixel.MotionBlurPixelShader;
import com.givewaygames.gwgl.shader.pixel.NeonPixelShader;
import com.givewaygames.gwgl.shader.pixel.NoctovisionPixelShader;
import com.givewaygames.gwgl.shader.pixel.OilifyPixelShader;
import com.givewaygames.gwgl.shader.pixel.OldPhotoPixelShader;
import com.givewaygames.gwgl.shader.pixel.PredatorPixelShader;
import com.givewaygames.gwgl.shader.pixel.RipplePixelShader;
import com.givewaygames.gwgl.shader.pixel.SketchPixelShader;
import com.givewaygames.gwgl.shader.pixel.SkinnyFatPixelShader;
import com.givewaygames.gwgl.shader.pixel.SmallCirclesPixelShader;
import com.givewaygames.gwgl.shader.pixel.SobelPixelShader;
import com.givewaygames.gwgl.shader.pixel.StainGlassPixelShader;
import com.givewaygames.gwgl.shader.pixel.SwirlPixelShader;
import com.givewaygames.gwgl.shader.pixel.TexturedMosaicPixelShader;
import com.givewaygames.gwgl.shader.vertex.BasicVertexShader;
import com.givewaygames.gwgl.utils.BitmapHelper;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.TextureSlotProvider;
import com.givewaygames.gwgl.utils.gl.GLEye;
import com.givewaygames.gwgl.utils.gl.GLImage;
import com.givewaygames.gwgl.utils.gl.GLMesh;
import com.givewaygames.gwgl.utils.gl.GLMosaicWall;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import com.givewaygames.gwgl.utils.gl.GLTexture;
import com.givewaygames.gwgl.utils.gl.GLTransform;
import com.givewaygames.gwgl.utils.gl.GLWall;
import com.givewaygames.gwgl.utils.gl.meshes.GLBitmapImage;
import com.givewaygames.gwgl.utils.gl.meshes.GLFlingMesh;
import com.givewaygames.gwgl.utils.gl.meshes.providers.BigEarsPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.BigForeheadPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.BugEyesPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ChubbyCheeksPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ConeheadPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.DoubleBumpHoreheadPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.FatChinPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.GorillaNosePointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.ListPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.MonkeyMouthPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.NoPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.PointyChinPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.SpikyHairPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.SquareNosePointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.SquintyEyesPointProvider;
import com.givewaygames.gwgl.utils.gl.meshes.providers.TinyMouthPointProvider;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class ShaderFactory {
    public static final int BASIC = 0;
    public static final int BIG_EARS = 33;
    public static final int BIG_FOREHEAD = 34;
    public static final int BLOOM = 5;
    public static final int BUG_EYES = 36;
    public static final int CELL_SHADED = 4;
    public static final int CELL_SHADED2 = 20;
    public static final int CHUBBY_CHEEKS = 42;
    public static final int CIRCULAR_RIPPLE = 1;
    public static final int COLORBLIND = 28;
    public static final int COLOR_MATRIX = 29;
    public static final int COLOR_PALETTE = 26;
    public static final int CONEHEAD = 32;
    public static final int DESPECKLE = 17;
    public static final int DILATE = 13;
    public static final int DOUBLE_BUMP_FOREHEAD = 44;
    public static final int EMBOSS = 2;
    public static final int EXTRACT_COLOR = 27;
    public static final int FAT_CHIN = 39;
    public static final int FROZEN_GLASS = 21;
    public static final int GLASS_SPHERE = 9;
    public static final int GORILLA_NOSE = 45;
    public static final int HUE_SHIFT = 31;
    public static final int INVERTED = 3;
    public static final int LAST_SHADER = 45;
    public static final int MIRROR = 30;
    public static final int MONKEY_MOUTH = 41;
    public static final int MOTION_BLUR = 12;
    public static final int NEON = 11;
    public static final int NOCTOVISION = 18;
    public static final int OILIFY = 22;
    public static final int OLD_PHOTO = 6;
    public static final int POINTY_CHIN = 35;
    public static final int PREDATOR = 14;
    public static final int RIPPLE = 15;
    public static final int SKETCH = 23;
    public static final int SKINNY_FAT = 19;
    public static final int SMALL_CIRCLES = 10;
    public static final int SOBEL = 7;
    public static final int SPIKY_HAIR = 43;
    public static final int SQUARE_NOSE = 40;
    public static final int SQUINTY_EYES = 37;
    public static final int STAIN_GLASS = 16;
    public static final int STYLE_CONSERVATIVE = 1;
    public static final int STYLE_IN_YOUR_FACE = 0;
    public static final int SWIRL = 8;
    public static final int TEXTURED_MOSAIC_LEOPARD = 24;
    public static final int TEXTURED_MOSAIC_SHAPES = 25;
    public static final int TINY_MOUTH = 38;
    public static final SparseArray<String> shaderNameLookup = new SparseArray();
    Context context;
    GLEye glEye;
    GLHelper glHelper;
    GLImage glImage;
    GLTexture glTexture;
    MeshOrientation meshOrientation;
    int secondarySlot;
    int shaderStyle = 1;
    TextureSlotProvider slotProvider;

    static {
        populateShaderNames();
    }

    public ShaderFactory(Context context, GLHelper glHelper) {
        this.context = context;
        this.glHelper = glHelper;
    }

    public ShaderFactory(Context context, GLHelper glHelper, GLTexture glTexture, GLImage glImage, GLEye glEye, MeshOrientation meshOrientation, TextureSlotProvider slotProvider) {
        this.context = context;
        this.glHelper = glHelper;
        this.glTexture = glTexture;
        this.glImage = glImage;
        this.glEye = glEye;
        this.meshOrientation = meshOrientation;
        this.slotProvider = slotProvider;
        this.secondarySlot = slotProvider.getNextTextureSlot();
    }

    public void setTexture(GLTexture glTexture) {
        this.glTexture = glTexture;
    }

    public void setShaderStyle(int style) {
        this.shaderStyle = style;
    }

    public GLProgram getProgram(int shaderType, int textureType) {
        boolean z = true;
        GLProgram program = new GLProgram(this.glHelper, textureType, null, null);
        program.setTag(shaderType);
        Context context;
        switch (shaderType) {
            case 1:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (shaderType != 0) {
                    z = false;
                }
                program.setPixelShader(new CircularRipplePixelShader(context, z));
                break;
            case 2:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new EmbossPixelShader(this.context, this.glHelper));
                if (this.shaderStyle == 0) {
                    program.getPixelShader().getVariable(0).setValidRange(0.05f, 0.4f, 0.01f);
                    break;
                }
                break;
            case 3:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new BasicPixelShader(this.context, R.raw.invert_frag));
                break;
            case 4:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (this.shaderStyle != 0) {
                    z = false;
                }
                program.setPixelShader(new CellPixelShader(context, z));
                break;
            case 5:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new BloomPixelShader(this.context));
                if (this.shaderStyle == 0) {
                    program.getPixelShader().getVariable(0).setValidRange(20.0f, BitmapDescriptorFactory.HUE_ORANGE, 0.01f);
                    break;
                }
                break;
            case 6:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new OldPhotoPixelShader(this.context));
                break;
            case 7:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new SobelPixelShader(this.context));
                break;
            case 8:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new SwirlPixelShader(this.context));
                break;
            case 9:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (this.shaderStyle != 0) {
                    z = false;
                }
                program.setPixelShader(new GlassSpherePixelShader(context, z));
                program.getPixelShader().getVariable(3).setRandomizer(new BinaryRandomizer(0.1f, TextTrackStyle.DEFAULT_FONT_SCALE, GLFlingMesh.minIndexSize));
                program.getPixelShader().getVariable(4).setRandomizer(new ExponentialRandomizer(3.0f, GLFlingMesh.minIndexSize, 0.2f));
                break;
            case 10:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new SmallCirclesPixelShader(this.context));
                break;
            case 11:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new NeonPixelShader(this.context));
                break;
            case 12:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new MotionBlurPixelShader(this.context));
                if (this.shaderStyle == 0) {
                    program.getPixelShader().getVariable(0).setValidRange(0.25f, 0.75f, 0.01f);
                    break;
                }
                break;
            case 13:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new DilatePixelShader(this.context));
                if (this.shaderStyle == 0) {
                    ((GLMultiVariable) program.getPixelShader().getUserEditableVariables().get(0)).setValidRange(0.01f, 0.1f, 0.01f);
                    break;
                }
                break;
            case 14:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new PredatorPixelShader(this.context));
                break;
            case 15:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new RipplePixelShader(this.context));
                program.getPixelShader().getVariable(1).setRandomizer(new ExponentialRandomizer(3.0f, 3.0f, 0.5f));
                program.getPixelShader().getVariable(3).setRandomizer(new BinaryRandomizer(0.5f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE));
                break;
            case 16:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (this.shaderStyle != 0) {
                    z = false;
                }
                program.setPixelShader(new StainGlassPixelShader(context, z));
                break;
            case 17:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new DespecklePixelShader(this.context));
                break;
            case 18:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (this.shaderStyle != 0) {
                    z = false;
                }
                program.setPixelShader(new NoctovisionPixelShader(context, z));
                break;
            case 19:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (this.shaderStyle != 0) {
                    z = false;
                }
                program.setPixelShader(new SkinnyFatPixelShader(context, z));
                break;
            case 20:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new CellShadedPixelShader(this.context));
                break;
            case 21:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (this.shaderStyle != 0) {
                    z = false;
                }
                program.setPixelShader(new FrozenGlassPixelShader(context, z));
                break;
            case 22:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new OilifyPixelShader(this.context));
                break;
            case 23:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (this.shaderStyle != 0) {
                    z = false;
                }
                program.setPixelShader(new SketchPixelShader(context, z));
                break;
            case 24:
            case 25:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new TexturedMosaicPixelShader(this.context));
                break;
            case 26:
                program.setVertexShader(new BasicVertexShader(this.context));
                BasicPixelShader colorPalette = new BasicPixelShader(this.context, R.raw.color_palette_frag);
                colorPalette.setupUVBounds();
                program.setPixelShader(colorPalette);
                break;
            case 27:
                program.setVertexShader(new BasicVertexShader(this.context));
                context = this.context;
                if (this.shaderStyle != 0) {
                    z = false;
                }
                program.setPixelShader(new ExtractColorPixelShader(context, z));
                break;
            case 28:
                ColorMatrixPixelShader cmPS = new ColorMatrixPixelShader(this.context, false, true);
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(cmPS);
                cmPS.getVariable(1).setRandomizer(cmPS.getColorblindRandomizer());
                break;
            case 29:
                ColorMatrixPixelShader cmPS2 = new ColorMatrixPixelShader(this.context, false, true);
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(cmPS2);
                cmPS2.getVariable(1).setRandomizer(cmPS2.getWildRandomizer(TextTrackStyle.DEFAULT_FONT_SCALE));
                break;
            case 30:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new MirrorPixelShader(this.context));
                break;
            case 31:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new HueShiftPixelShader(this.context));
                break;
            default:
                program.setVertexShader(new BasicVertexShader(this.context));
                program.setPixelShader(new BasicPixelShader(this.context));
                break;
        }
        return program;
    }

    public GLWall getWall(GLProgram glProgram) {
        int shaderType = glProgram.getProgramID();
        GLTransform transform = this.glEye != null ? new GLTransform(this.glEye) : null;
        switch (shaderType) {
            case 24:
                return setupMosaicWall(glProgram, transform, R.drawable.leopard_output);
            case 25:
                return setupMosaicWall(glProgram, transform, R.drawable.shapes_output);
            case 26:
                return setupMosaicWall(glProgram, transform, R.drawable.colors_output);
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
                throw new RuntimeException("This needs to be implemented, before it can be used.");
            default:
                return new GLWall(glProgram, new GLMesh(this.glHelper, this.glTexture, this.glImage, this.meshOrientation), transform);
        }
    }

    public GLWall getWall(int shaderType) {
        return getWall(getProgram(shaderType, this.glTexture.getTextureType()));
    }

    private GLMosaicWall setupMosaicWall(GLProgram program, GLTransform transform, int bitmapId) {
        program.setVertexShader(new BasicVertexShader(this.context));
        program.setPixelShader(new TexturedMosaicPixelShader(this.context));
        GLTexture mosaicTexture = new GLTexture(this.slotProvider, "mosaic", this.secondarySlot);
        mosaicTexture.setTextureType(3553);
        GLBitmapImage glImageA = new GLBitmapImage(BitmapHelper.decodeResourceWithDownsample(this.context.getResources(), bitmapId, 32), mosaicTexture);
        GLMesh mesh = new GLMesh(this.glHelper, null, this.glImage, this.meshOrientation);
        mesh.addTexture(this.glTexture);
        mesh.addTexture(mosaicTexture);
        return new GLMosaicWall(program, mesh, transform, glImageA, mosaicTexture);
    }

    public static void setupSecondaries(Context context, int shaderType, GLBitmapImage glSecondaryImage, GLTexture glSecondaryTexture) {
        if (glSecondaryImage != null && glSecondaryTexture != null) {
            int imageId = -1;
            switch (shaderType) {
                case 24:
                    imageId = R.drawable.leopard_output;
                    break;
                case 25:
                    imageId = R.drawable.shapes_output;
                    break;
                case 26:
                    imageId = R.drawable.colors_output;
                    break;
                default:
                    glSecondaryImage.setEnabled(false);
                    glSecondaryTexture.setEnabled(false);
                    break;
            }
            if (imageId != -1) {
                glSecondaryImage.updateImage(BitmapHelper.decodeResourceWithDownsample(context.getResources(), imageId, 32));
                glSecondaryImage.setEnabled(true);
                glSecondaryTexture.setEnabled(true);
            }
        }
    }

    public static ListPointProvider getPointProvider(GLHelper glHelper, int shaderType) {
        switch (shaderType) {
            case 32:
                return new ConeheadPointProvider(glHelper);
            case 33:
                return new BigEarsPointProvider(glHelper);
            case 34:
                return new BigForeheadPointProvider(glHelper);
            case 35:
                return new PointyChinPointProvider(glHelper);
            case 36:
                return new BugEyesPointProvider(glHelper);
            case 37:
                return new SquintyEyesPointProvider(glHelper);
            case 38:
                return new TinyMouthPointProvider(glHelper);
            case 39:
                return new FatChinPointProvider(glHelper);
            case 40:
                return new SquareNosePointProvider(glHelper);
            case 41:
                return new MonkeyMouthPointProvider(glHelper);
            case 42:
                return new ChubbyCheeksPointProvider(glHelper);
            case 43:
                return new SpikyHairPointProvider(glHelper);
            case 44:
                return new DoubleBumpHoreheadPointProvider(glHelper);
            case 45:
                return new GorillaNosePointProvider(glHelper);
            default:
                return new NoPointProvider(glHelper);
        }
    }

    private static void populateShaderNames() {
        shaderNameLookup.put(0, "None");
        shaderNameLookup.put(1, "Water Ripple");
        shaderNameLookup.put(2, "Emboss");
        shaderNameLookup.put(3, "Inverted");
        shaderNameLookup.put(4, "Comic Book");
        shaderNameLookup.put(5, "Overexposed");
        shaderNameLookup.put(6, "Old Photo");
        shaderNameLookup.put(7, "Sobel");
        shaderNameLookup.put(8, "Swirl");
        shaderNameLookup.put(9, "Glass Sphere");
        shaderNameLookup.put(10, "Small Circles");
        shaderNameLookup.put(11, "Neon");
        shaderNameLookup.put(12, "Motion Blur");
        shaderNameLookup.put(13, "Dilate");
        shaderNameLookup.put(14, "Predator");
        shaderNameLookup.put(15, "Ripple");
        shaderNameLookup.put(16, "Stain Glass");
        shaderNameLookup.put(17, "Despeckle");
        shaderNameLookup.put(18, "Noctovision");
        shaderNameLookup.put(19, "Skinny/Fat Mirror");
        shaderNameLookup.put(20, "Cell Shaded (2)");
        shaderNameLookup.put(21, "Frozen Glass");
        shaderNameLookup.put(22, "Oilify");
        shaderNameLookup.put(23, "Sketch");
        shaderNameLookup.put(24, "Mosaic (Leopard)");
        shaderNameLookup.put(25, "Mosaic (Shapes)");
        shaderNameLookup.put(26, "Color Palette");
        shaderNameLookup.put(27, "Extract Color");
        shaderNameLookup.put(28, "Colorblind");
        shaderNameLookup.put(29, "Color Matrix");
        shaderNameLookup.put(30, "Mirror");
        shaderNameLookup.put(31, "Color Shift");
    }
}
