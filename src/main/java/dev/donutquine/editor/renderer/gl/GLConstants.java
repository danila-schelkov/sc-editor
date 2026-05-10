package dev.donutquine.editor.renderer.gl;

/// Declares OpenGL and OpenGL ES enumerated constant values.
///
/// The one constant that's missing from this crate is `GL_ACTIVE_PROGRAM_EXT`,
/// which is part of the `GL_EXT_separate_shader_objects` extension. It's the
/// only constant which has a *different* value in OpenGL compared to OpenGL ES.
/// All other constants are the same value in both APIs.
@SuppressWarnings("unused")
public class GLConstants {
    /// Groups: `SamplePatternSGIS`, `SamplePatternEXT`
    public static final int GL_1PASS_EXT = 0x80A1;

    /// Group: `SamplePatternSGIS`
    public static final int GL_1PASS_SGIS = 0x80A1;

    /// Group: `FeedbackType`
    public static final int GL_2D = 0x0600;

    /// Groups: `SamplePatternSGIS`, `SamplePatternEXT`
    public static final int GL_2PASS_0_EXT = 0x80A2;

    /// Group: `SamplePatternSGIS`
    public static final int GL_2PASS_0_SGIS = 0x80A2;

    /// Groups: `SamplePatternSGIS`, `SamplePatternEXT`
    public static final int GL_2PASS_1_EXT = 0x80A3;

    /// Group: `SamplePatternSGIS`
    public static final int GL_2PASS_1_SGIS = 0x80A3;

    /// Groups: `FragmentShaderDestModMaskATI`, `FragmentShaderColorModMaskATI`
    public static final int GL_2X_BIT_ATI = 0x00000001;

    /// Group: `ListNameType`
    public static final int GL_2_BYTES = 0x1407;

    public static final int GL_2_BYTES_NV = 0x1407;

    /// Group: `FeedbackType`
    public static final int GL_3D = 0x0601;

    public static final int GL_3DC_XY_AMD = 0x87FA;

    public static final int GL_3DC_X_AMD = 0x87F9;

    /// Group: `FeedbackType`
    public static final int GL_3D_COLOR = 0x0602;

    /// Group: `FeedbackType`
    public static final int GL_3D_COLOR_TEXTURE = 0x0603;

    /// Group: `ListNameType`
    public static final int GL_3_BYTES = 0x1408;

    public static final int GL_3_BYTES_NV = 0x1408;

    public static final int GL_422_AVERAGE_EXT = 0x80CE;

    public static final int GL_422_EXT = 0x80CC;

    public static final int GL_422_REV_AVERAGE_EXT = 0x80CF;

    public static final int GL_422_REV_EXT = 0x80CD;

    /// Group: `FeedbackType`
    public static final int GL_4D_COLOR_TEXTURE = 0x0604;

    /// Groups: `SamplePatternSGIS`, `SamplePatternEXT`
    public static final int GL_4PASS_0_EXT = 0x80A4;

    /// Group: `SamplePatternSGIS`
    public static final int GL_4PASS_0_SGIS = 0x80A4;

    /// Groups: `SamplePatternSGIS`, `SamplePatternEXT`
    public static final int GL_4PASS_1_EXT = 0x80A5;

    /// Group: `SamplePatternSGIS`
    public static final int GL_4PASS_1_SGIS = 0x80A5;

    /// Groups: `SamplePatternSGIS`, `SamplePatternEXT`
    public static final int GL_4PASS_2_EXT = 0x80A6;

    /// Group: `SamplePatternSGIS`
    public static final int GL_4PASS_2_SGIS = 0x80A6;

    /// Groups: `SamplePatternSGIS`, `SamplePatternEXT`
    public static final int GL_4PASS_3_EXT = 0x80A7;

    /// Group: `SamplePatternSGIS`
    public static final int GL_4PASS_3_SGIS = 0x80A7;

    /// Group: `FragmentShaderDestModMaskATI`
    public static final int GL_4X_BIT_ATI = 0x00000002;

    /// Group: `ListNameType`
    public static final int GL_4_BYTES = 0x1409;

    public static final int GL_4_BYTES_NV = 0x1409;

    /// Group: `FragmentShaderDestModMaskATI`
    public static final int GL_8X_BIT_ATI = 0x00000004;

    /// Group: `PixelFormat`
    public static final int GL_ABGR_EXT = 0x8000;

    /// Group: `AccumOp`
    public static final int GL_ACCUM = 0x0100;

    /// Group: `PathListMode`
    public static final int GL_ACCUM_ADJACENT_PAIRS_NV = 0x90AD;

    /// Group: `GetPName`
    public static final int GL_ACCUM_ALPHA_BITS = 0x0D5B;

    /// Group: `GetPName`
    public static final int GL_ACCUM_BLUE_BITS = 0x0D5A;

    /// Groups: `ClearBufferMask`, `AttribMask`
    public static final int GL_ACCUM_BUFFER_BIT = 0x00000200;

    /// Group: `GetPName`
    public static final int GL_ACCUM_CLEAR_VALUE = 0x0B80;

    /// Group: `GetPName`
    public static final int GL_ACCUM_GREEN_BITS = 0x0D59;

    /// Group: `GetPName`
    public static final int GL_ACCUM_RED_BITS = 0x0D58;

    /// Group: `ProgramPropertyARB`
    public static final int GL_ACTIVE_ATOMIC_COUNTER_BUFFERS = 0x92D9;

    /// Group: `ProgramPropertyARB`
    public static final int GL_ACTIVE_ATTRIBUTES = 0x8B89;

    /// Group: `ProgramPropertyARB`
    public static final int GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A;

    /// Group: `PipelineParameterName`
    public static final int GL_ACTIVE_PROGRAM = 0x8259;

    /// Group: `ProgramInterfacePName`
    public static final int GL_ACTIVE_RESOURCES = 0x92F5;

    public static final int GL_ACTIVE_STENCIL_FACE_EXT = 0x8911;

    /// Group: `ProgramStagePName`
    public static final int GL_ACTIVE_SUBROUTINES = 0x8DE5;

    /// Group: `ProgramStagePName`
    public static final int GL_ACTIVE_SUBROUTINE_MAX_LENGTH = 0x8E48;

    /// Group: `ProgramStagePName`
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORMS = 0x8DE6;

    /// Group: `ProgramStagePName`
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_LOCATIONS = 0x8E47;

    /// Group: `ProgramStagePName`
    public static final int GL_ACTIVE_SUBROUTINE_UNIFORM_MAX_LENGTH = 0x8E49;

    /// Group: `GetPName`
    public static final int GL_ACTIVE_TEXTURE = 0x84E0;

    public static final int GL_ACTIVE_TEXTURE_ARB = 0x84E0;

    /// Group: `ProgramPropertyARB`
    public static final int GL_ACTIVE_UNIFORMS = 0x8B86;

    /// Group: `ProgramPropertyARB`
    public static final int GL_ACTIVE_UNIFORM_BLOCKS = 0x8A36;

    /// Group: `ProgramPropertyARB`
    public static final int GL_ACTIVE_UNIFORM_BLOCK_MAX_NAME_LENGTH = 0x8A35;

    /// Group: `ProgramPropertyARB`
    public static final int GL_ACTIVE_UNIFORM_MAX_LENGTH = 0x8B87;

    /// Group: `ProgramResourceProperty`
    public static final int GL_ACTIVE_VARIABLES = 0x9305;

    public static final int GL_ACTIVE_VARYINGS_NV = 0x8C81;

    public static final int GL_ACTIVE_VARYING_MAX_LENGTH_NV = 0x8C82;

    public static final int GL_ACTIVE_VERTEX_UNITS_ARB = 0x86A5;

    /// Groups: `TextureEnvMode`, `AccumOp`, `LightEnvModeSGIX`
    public static final int GL_ADD = 0x0104;

    /// Group: `FragmentOp2ATI`
    public static final int GL_ADD_ATI = 0x8963;

    public static final int GL_ADD_BLEND_IMG = 0x8C09;

    /// Group: `TextureEnvParameter`
    public static final int GL_ADD_SIGNED = 0x8574;

    /// Group: `TextureEnvParameter`
    public static final int GL_ADD_SIGNED_ARB = 0x8574;

    /// Group: `TextureEnvParameter`
    public static final int GL_ADD_SIGNED_EXT = 0x8574;

    /// Group: `PathListMode`
    public static final int GL_ADJACENT_PAIRS_NV = 0x90AE;

    /// Group: `PathTransformType`
    public static final int GL_AFFINE_2D_NV = 0x9092;

    /// Group: `PathTransformType`
    public static final int GL_AFFINE_3D_NV = 0x9094;

    /// Group: `GetPName`
    public static final int GL_ALIASED_LINE_WIDTH_RANGE = 0x846E;

    /// Group: `GetPName`
    public static final int GL_ALIASED_POINT_SIZE_RANGE = 0x846D;

    /// Group: `HintTarget`
    public static final int GL_ALLOW_DRAW_FRG_HINT_PGI = 0x1A210;

    /// Group: `HintTarget`
    public static final int GL_ALLOW_DRAW_MEM_HINT_PGI = 0x1A211;

    /// Group: `HintTarget`
    public static final int GL_ALLOW_DRAW_OBJ_HINT_PGI = 0x1A20E;

    /// Group: `HintTarget`
    public static final int GL_ALLOW_DRAW_WIN_HINT_PGI = 0x1A20F;

    /// Group: `AttribMask`
    public static final int GL_ALL_ATTRIB_BITS = 0xFFFFFFFF;

    /// Group: `MemoryBarrierMask`
    public static final int GL_ALL_BARRIER_BITS = 0xFFFFFFFF;

    /// Group: `MemoryBarrierMask`
    public static final int GL_ALL_BARRIER_BITS_EXT = 0xFFFFFFFF;

    /// Group: `FenceConditionNV`
    public static final int GL_ALL_COMPLETED_NV = 0x84F2;

    /// Group: `UseProgramStageMask`
    public static final int GL_ALL_SHADER_BITS = 0xFFFFFFFF;

    /// Group: `UseProgramStageMask`
    public static final int GL_ALL_SHADER_BITS_EXT = 0xFFFFFFFF;

    public static final int GL_ALL_STATIC_DATA_IBM = 103060;

    /// Groups: `PixelTexGenModeSGIX`, `FragmentShaderValueRepATI`,
    ///   `TextureSwizzle`, `CombinerPortionNV`, `PathColorFormat`,
    ///   `CombinerComponentUsageNV`, `PixelFormat`
    public static final int GL_ALPHA = 0x1906;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA12 = 0x803D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA12_EXT = 0x803D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA16 = 0x803E;

    public static final int GL_ALPHA16F_ARB = 0x881C;

    public static final int GL_ALPHA16F_EXT = 0x881C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA16I_EXT = 0x8D8A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA16UI_EXT = 0x8D78;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA16_EXT = 0x803E;

    public static final int GL_ALPHA16_SNORM = 0x9018;

    public static final int GL_ALPHA32F_ARB = 0x8816;

    public static final int GL_ALPHA32F_EXT = 0x8816;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA32I_EXT = 0x8D84;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA32UI_EXT = 0x8D72;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA4 = 0x803B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA4_EXT = 0x803B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA8 = 0x803C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA8I_EXT = 0x8D90;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA8UI_EXT = 0x8D7E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA8_EXT = 0x803C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ALPHA8_OES = 0x803C;

    public static final int GL_ALPHA8_SNORM = 0x9014;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_ALPHA_BIAS = 0x0D1D;

    /// Group: `GetPName`
    public static final int GL_ALPHA_BITS = 0x0D55;

    public static final int GL_ALPHA_FLOAT16_APPLE = 0x881C;

    public static final int GL_ALPHA_FLOAT16_ATI = 0x881C;

    public static final int GL_ALPHA_FLOAT32_APPLE = 0x8816;

    public static final int GL_ALPHA_FLOAT32_ATI = 0x8816;

    public static final int GL_ALPHA_INTEGER = 0x8D97;

    public static final int GL_ALPHA_INTEGER_EXT = 0x8D97;

    public static final int GL_ALPHA_MAX_CLAMP_INGR = 0x8567;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_ALPHA_MAX_SGIX = 0x8321;

    public static final int GL_ALPHA_MIN_CLAMP_INGR = 0x8563;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_ALPHA_MIN_SGIX = 0x8320;

    /// Group: `CommandOpcodesNV`
    public static final int GL_ALPHA_REF_COMMAND_NV = 0x000F;

    /// Groups: `PixelTransferParameter`, `GetPName`, `TextureEnvParameter`
    public static final int GL_ALPHA_SCALE = 0x0D1C;

    public static final int GL_ALPHA_SNORM = 0x9010;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_ALPHA_TEST = 0x0BC0;

    /// Group: `GetPName`
    public static final int GL_ALPHA_TEST_FUNC = 0x0BC1;

    /// Group: `GetPName`
    public static final int GL_ALPHA_TEST_FUNC_QCOM = 0x0BC1;

    /// Group: `GetPName`
    public static final int GL_ALPHA_TEST_QCOM = 0x0BC0;

    /// Group: `GetPName`
    public static final int GL_ALPHA_TEST_REF = 0x0BC2;

    /// Group: `GetPName`
    public static final int GL_ALPHA_TEST_REF_QCOM = 0x0BC2;

    public static final int GL_ALPHA_TO_COVERAGE_DITHER_DEFAULT_NV = 0x934D;

    public static final int GL_ALPHA_TO_COVERAGE_DITHER_DISABLE_NV = 0x934F;

    public static final int GL_ALPHA_TO_COVERAGE_DITHER_ENABLE_NV = 0x934E;

    public static final int GL_ALPHA_TO_COVERAGE_DITHER_MODE_NV = 0x92BF;

    /// Group: `SyncStatus`
    public static final int GL_ALREADY_SIGNALED = 0x911A;

    public static final int GL_ALREADY_SIGNALED_APPLE = 0x911A;

    /// Groups: `StencilFunction`, `IndexFunctionEXT`, `AlphaFunction`,
    ///   `DepthFunction`
    public static final int GL_ALWAYS = 0x0207;

    /// Group: `HintTarget`
    public static final int GL_ALWAYS_FAST_HINT_PGI = 0x1A20C;

    /// Group: `HintTarget`
    public static final int GL_ALWAYS_SOFT_HINT_PGI = 0x1A20D;

    /// Groups: `MaterialParameter`, `FragmentLightParameterSGIX`,
    ///   `ColorMaterialParameter`
    public static final int GL_AMBIENT = 0x1200;

    /// Groups: `MaterialParameter`, `ColorMaterialParameter`
    public static final int GL_AMBIENT_AND_DIFFUSE = 0x1602;

    /// Group: `LogicOp`
    public static final int GL_AND = 0x1501;

    /// Group: `LogicOp`
    public static final int GL_AND_INVERTED = 0x1504;

    /// Group: `LogicOp`
    public static final int GL_AND_REVERSE = 0x1502;

    /// Group: `QueryTarget`
    public static final int GL_ANY_SAMPLES_PASSED = 0x8C2F;

    /// Group: `QueryTarget`
    public static final int GL_ANY_SAMPLES_PASSED_CONSERVATIVE = 0x8D6A;

    public static final int GL_ANY_SAMPLES_PASSED_CONSERVATIVE_EXT = 0x8D6A;

    public static final int GL_ANY_SAMPLES_PASSED_EXT = 0x8C2F;

    /// Group: `PathCoordType`
    public static final int GL_ARC_TO_NV = 0xFE;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_ARRAY_BUFFER = 0x8892;

    public static final int GL_ARRAY_BUFFER_ARB = 0x8892;

    /// Group: `GetPName`
    public static final int GL_ARRAY_BUFFER_BINDING = 0x8894;

    public static final int GL_ARRAY_BUFFER_BINDING_ARB = 0x8894;

    public static final int GL_ARRAY_ELEMENT_LOCK_COUNT_EXT = 0x81A9;

    public static final int GL_ARRAY_ELEMENT_LOCK_FIRST_EXT = 0x81A8;

    public static final int GL_ARRAY_OBJECT_BUFFER_ATI = 0x8766;

    public static final int GL_ARRAY_OBJECT_OFFSET_ATI = 0x8767;

    /// Group: `ProgramResourceProperty`
    public static final int GL_ARRAY_SIZE = 0x92FB;

    /// Group: `ProgramResourceProperty`
    public static final int GL_ARRAY_STRIDE = 0x92FE;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_ASYNC_DRAW_PIXELS_SGIX = 0x835D;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_ASYNC_HISTOGRAM_SGIX = 0x832C;

    /// Group: `GetPName`
    public static final int GL_ASYNC_MARKER_SGIX = 0x8329;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_ASYNC_READ_PIXELS_SGIX = 0x835E;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_ASYNC_TEX_IMAGE_SGIX = 0x835C;

    public static final int GL_ATC_RGBA_EXPLICIT_ALPHA_AMD = 0x8C93;

    public static final int GL_ATC_RGBA_INTERPOLATED_ALPHA_AMD = 0x87EE;

    public static final int GL_ATC_RGB_AMD = 0x8C92;

    /// Group: `MemoryBarrierMask`
    public static final int GL_ATOMIC_COUNTER_BARRIER_BIT = 0x00001000;

    /// Group: `MemoryBarrierMask`
    public static final int GL_ATOMIC_COUNTER_BARRIER_BIT_EXT = 0x00001000;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_ATOMIC_COUNTER_BUFFER = 0x92C0;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTERS = 0x92C5;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_ACTIVE_ATOMIC_COUNTER_INDICES = 0x92C6;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_BINDING = 0x92C1;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_DATA_SIZE = 0x92C4;

    /// Group: `ProgramResourceProperty`
    public static final int GL_ATOMIC_COUNTER_BUFFER_INDEX = 0x9301;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_COMPUTE_SHADER = 0x90ED;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_FRAGMENT_SHADER = 0x92CB;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_GEOMETRY_SHADER = 0x92CA;

    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_MESH_SHADER_NV = 0x959E;

    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TASK_SHADER_NV = 0x959F;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_CONTROL_SHADER = 0x92C8;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_TESS_EVALUATION_SHADER = 0x92C9;

    /// Group: `AtomicCounterBufferPName`
    public static final int GL_ATOMIC_COUNTER_BUFFER_REFERENCED_BY_VERTEX_SHADER = 0x92C7;

    public static final int GL_ATOMIC_COUNTER_BUFFER_SIZE = 0x92C3;

    public static final int GL_ATOMIC_COUNTER_BUFFER_START = 0x92C2;

    public static final int GL_ATTACHED_MEMORY_OBJECT_NV = 0x95A4;

    public static final int GL_ATTACHED_MEMORY_OFFSET_NV = 0x95A5;

    /// Group: `ProgramPropertyARB`
    public static final int GL_ATTACHED_SHADERS = 0x8B85;

    /// Group: `LightTexturePNameEXT`
    public static final int GL_ATTENUATION_EXT = 0x834D;

    /// Group: `CommandOpcodesNV`
    public static final int GL_ATTRIBUTE_ADDRESS_COMMAND_NV = 0x0009;

    public static final int GL_ATTRIB_ARRAY_POINTER_NV = 0x8645;

    public static final int GL_ATTRIB_ARRAY_SIZE_NV = 0x8623;

    public static final int GL_ATTRIB_ARRAY_STRIDE_NV = 0x8624;

    public static final int GL_ATTRIB_ARRAY_TYPE_NV = 0x8625;

    /// Group: `GetPName`
    public static final int GL_ATTRIB_STACK_DEPTH = 0x0BB0;

    /// Group: `InternalFormatPName`
    public static final int GL_AUTO_GENERATE_MIPMAP = 0x8295;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_AUTO_NORMAL = 0x0D80;

    /// Groups: `ReadBufferMode`, `DrawBufferMode`
    public static final int GL_AUX0 = 0x0409;

    /// Groups: `ReadBufferMode`, `DrawBufferMode`
    public static final int GL_AUX1 = 0x040A;

    /// Groups: `ReadBufferMode`, `DrawBufferMode`
    public static final int GL_AUX2 = 0x040B;

    /// Groups: `ReadBufferMode`, `DrawBufferMode`
    public static final int GL_AUX3 = 0x040C;

    /// Group: `GetPName`
    public static final int GL_AUX_BUFFERS = 0x0C00;

    public static final int GL_AUX_DEPTH_STENCIL_APPLE = 0x8A14;

    public static final int GL_AVERAGE_EXT = 0x8335;

    public static final int GL_AVERAGE_HP = 0x8160;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`, `TriangleFace`
    public static final int GL_BACK = 0x0405;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`
    public static final int GL_BACK_LEFT = 0x0402;

    /// Group: `HintTarget`
    public static final int GL_BACK_NORMALS_HINT_PGI = 0x1A223;

    public static final int GL_BACK_PRIMARY_COLOR_NV = 0x8C77;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`
    public static final int GL_BACK_RIGHT = 0x0403;

    public static final int GL_BACK_SECONDARY_COLOR_NV = 0x8C78;

    public static final int GL_BEVEL_NV = 0x90A6;

    /// Group: `PixelFormat`
    public static final int GL_BGR = 0x80E0;

    /// Group: `PixelFormat`
    public static final int GL_BGRA = 0x80E1;

    public static final int GL_BGRA8_EXT = 0x93A1;

    /// Group: `PixelFormat`
    public static final int GL_BGRA_EXT = 0x80E1;

    /// Group: `PixelFormat`
    public static final int GL_BGRA_IMG = 0x80E1;

    /// Group: `PixelFormat`
    public static final int GL_BGRA_INTEGER = 0x8D9B;

    public static final int GL_BGRA_INTEGER_EXT = 0x8D9B;

    /// Group: `PixelFormat`
    public static final int GL_BGR_EXT = 0x80E0;

    /// Group: `PixelFormat`
    public static final int GL_BGR_INTEGER = 0x8D9A;

    public static final int GL_BGR_INTEGER_EXT = 0x8D9A;

    /// Group: `FragmentShaderColorModMaskATI`
    public static final int GL_BIAS_BIT_ATI = 0x00000008;

    /// Group: `CombinerBiasNV`
    public static final int GL_BIAS_BY_NEGATIVE_ONE_HALF_NV = 0x8541;

    /// Group: `HintTarget`
    public static final int GL_BINNING_CONTROL_HINT_QCOM = 0x8FB0;

    public static final int GL_BINORMAL_ARRAY_EXT = 0x843A;

    public static final int GL_BINORMAL_ARRAY_POINTER_EXT = 0x8443;

    public static final int GL_BINORMAL_ARRAY_STRIDE_EXT = 0x8441;

    public static final int GL_BINORMAL_ARRAY_TYPE_EXT = 0x8440;

    /// Group: `PixelType`
    public static final int GL_BITMAP = 0x1A00;

    /// Group: `FeedBackToken`
    public static final int GL_BITMAP_TOKEN = 0x0704;

    public static final int GL_BLACKHOLE_RENDER_INTEL = 0x83FC;

    /// Groups: `TextureEnvMode`, `EnableCap`, `GetPName`
    public static final int GL_BLEND = 0x0BE2;

    public static final int GL_BLEND_ADVANCED_COHERENT_KHR = 0x9285;

    public static final int GL_BLEND_ADVANCED_COHERENT_NV = 0x9285;

    /// Group: `GetPName`
    public static final int GL_BLEND_COLOR = 0x8005;

    /// Group: `CommandOpcodesNV`
    public static final int GL_BLEND_COLOR_COMMAND_NV = 0x000B;

    /// Group: `GetPName`
    public static final int GL_BLEND_COLOR_EXT = 0x8005;

    /// Group: `GetPName`
    public static final int GL_BLEND_DST = 0x0BE0;

    /// Group: `GetPName`
    public static final int GL_BLEND_DST_ALPHA = 0x80CA;

    public static final int GL_BLEND_DST_ALPHA_EXT = 0x80CA;

    public static final int GL_BLEND_DST_ALPHA_OES = 0x80CA;

    /// Group: `GetPName`
    public static final int GL_BLEND_DST_RGB = 0x80C8;

    public static final int GL_BLEND_DST_RGB_EXT = 0x80C8;

    public static final int GL_BLEND_DST_RGB_OES = 0x80C8;

    /// Group: `GetPName`
    public static final int GL_BLEND_EQUATION = 0x8009;

    /// Group: `GetPName`
    public static final int GL_BLEND_EQUATION_ALPHA = 0x883D;

    public static final int GL_BLEND_EQUATION_ALPHA_EXT = 0x883D;

    public static final int GL_BLEND_EQUATION_ALPHA_OES = 0x883D;

    /// Group: `GetPName`
    public static final int GL_BLEND_EQUATION_EXT = 0x8009;

    /// Group: `GetPName`
    public static final int GL_BLEND_EQUATION_OES = 0x8009;

    /// Group: `GetPName`
    public static final int GL_BLEND_EQUATION_RGB = 0x8009;

    public static final int GL_BLEND_EQUATION_RGB_EXT = 0x8009;

    public static final int GL_BLEND_EQUATION_RGB_OES = 0x8009;

    public static final int GL_BLEND_OVERLAP_NV = 0x9281;

    public static final int GL_BLEND_PREMULTIPLIED_SRC_NV = 0x9280;

    /// Group: `GetPName`
    public static final int GL_BLEND_SRC = 0x0BE1;

    /// Group: `GetPName`
    public static final int GL_BLEND_SRC_ALPHA = 0x80CB;

    public static final int GL_BLEND_SRC_ALPHA_EXT = 0x80CB;

    public static final int GL_BLEND_SRC_ALPHA_OES = 0x80CB;

    /// Group: `GetPName`
    public static final int GL_BLEND_SRC_RGB = 0x80C9;

    public static final int GL_BLEND_SRC_RGB_EXT = 0x80C9;

    public static final int GL_BLEND_SRC_RGB_OES = 0x80C9;

    /// Group: `ProgramResourceProperty`
    public static final int GL_BLOCK_INDEX = 0x92FD;

    /// Groups: `FragmentShaderValueRepATI`, `TextureSwizzle`,
    ///   `CombinerComponentUsageNV`, `PixelFormat`
    public static final int GL_BLUE = 0x1905;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_BLUE_BIAS = 0x0D1B;

    /// Group: `GetPName`
    public static final int GL_BLUE_BITS = 0x0D54;

    /// Group: `FragmentShaderDestMaskATI`
    public static final int GL_BLUE_BIT_ATI = 0x00000004;

    /// Group: `PixelFormat`
    public static final int GL_BLUE_INTEGER = 0x8D96;

    public static final int GL_BLUE_INTEGER_EXT = 0x8D96;

    public static final int GL_BLUE_MAX_CLAMP_INGR = 0x8566;

    public static final int GL_BLUE_MIN_CLAMP_INGR = 0x8562;

    public static final int GL_BLUE_NV = 0x1905;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_BLUE_SCALE = 0x0D1A;

    /// Group: `PathFontStyle`
    public static final int GL_BOLD_BIT_NV = 0x01;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_BOOL = 0x8B56;

    /// Group: `AttributeType`
    public static final int GL_BOOL_ARB = 0x8B56;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_BOOL_VEC2 = 0x8B57;

    /// Group: `AttributeType`
    public static final int GL_BOOL_VEC2_ARB = 0x8B57;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_BOOL_VEC3 = 0x8B58;

    /// Group: `AttributeType`
    public static final int GL_BOOL_VEC3_ARB = 0x8B58;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_BOOL_VEC4 = 0x8B59;

    /// Group: `AttributeType`
    public static final int GL_BOOL_VEC4_ARB = 0x8B59;

    /// Group: `PathCoverMode`
    public static final int GL_BOUNDING_BOX_NV = 0x908D;

    /// Group: `PathCoverMode`
    public static final int GL_BOUNDING_BOX_OF_BOUNDING_BOXES_NV = 0x909C;

    public static final int GL_BROWSER_DEFAULT_WEBGL = 0x9244;

    /// Group: `ObjectIdentifier`
    public static final int GL_BUFFER = 0x82E0;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_ACCESS = 0x88BB;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_ACCESS_ARB = 0x88BB;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_ACCESS_FLAGS = 0x911F;

    public static final int GL_BUFFER_ACCESS_OES = 0x88BB;

    /// Group: `ProgramResourceProperty`
    public static final int GL_BUFFER_BINDING = 0x9302;

    /// Group: `ProgramResourceProperty`
    public static final int GL_BUFFER_DATA_SIZE = 0x9303;

    public static final int GL_BUFFER_FLUSHING_UNMAP_APPLE = 0x8A13;

    public static final int GL_BUFFER_GPU_ADDRESS_NV = 0x8F1D;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_IMMUTABLE_STORAGE = 0x821F;

    public static final int GL_BUFFER_IMMUTABLE_STORAGE_EXT = 0x821F;

    public static final int GL_BUFFER_KHR = 0x82E0;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_MAPPED = 0x88BC;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_MAPPED_ARB = 0x88BC;

    public static final int GL_BUFFER_MAPPED_OES = 0x88BC;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_MAP_LENGTH = 0x9120;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_MAP_OFFSET = 0x9121;

    /// Group: `BufferPointerNameARB`
    public static final int GL_BUFFER_MAP_POINTER = 0x88BD;

    /// Group: `BufferPointerNameARB`
    public static final int GL_BUFFER_MAP_POINTER_ARB = 0x88BD;

    public static final int GL_BUFFER_MAP_POINTER_OES = 0x88BD;

    public static final int GL_BUFFER_OBJECT_APPLE = 0x85B3;

    public static final int GL_BUFFER_OBJECT_EXT = 0x9151;

    public static final int GL_BUFFER_SERIALIZED_MODIFY_APPLE = 0x8A12;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_SIZE = 0x8764;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_SIZE_ARB = 0x8764;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_STORAGE_FLAGS = 0x8220;

    public static final int GL_BUFFER_STORAGE_FLAGS_EXT = 0x8220;

    /// Group: `MemoryBarrierMask`
    public static final int GL_BUFFER_UPDATE_BARRIER_BIT = 0x00000200;

    /// Group: `MemoryBarrierMask`
    public static final int GL_BUFFER_UPDATE_BARRIER_BIT_EXT = 0x00000200;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_USAGE = 0x8765;

    /// Group: `BufferPNameARB`
    public static final int GL_BUFFER_USAGE_ARB = 0x8765;

    /// Group: `ProgramInterface`
    public static final int GL_BUFFER_VARIABLE = 0x92E5;

    public static final int GL_BUMP_ENVMAP_ATI = 0x877B;

    /// Group: `GetTexBumpParameterATI`
    public static final int GL_BUMP_NUM_TEX_UNITS_ATI = 0x8777;

    /// Groups: `GetTexBumpParameterATI`, `TexBumpParameterATI`
    public static final int GL_BUMP_ROT_MATRIX_ATI = 0x8775;

    /// Group: `GetTexBumpParameterATI`
    public static final int GL_BUMP_ROT_MATRIX_SIZE_ATI = 0x8776;

    public static final int GL_BUMP_TARGET_ATI = 0x877C;

    /// Group: `GetTexBumpParameterATI`
    public static final int GL_BUMP_TEX_UNITS_ATI = 0x8778;

    /// Groups: `VertexAttribIType`, `WeightPointerTypeARB`,
    ///   `TangentPointerTypeEXT`, `BinormalPointerTypeEXT`, `ColorPointerType`,
    ///   `ListNameType`, `NormalPointerType`, `PixelType`, `VertexAttribType`,
    ///   `VertexAttribPointerType`
    public static final int GL_BYTE = 0x1400;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_C3F_V3F = 0x2A24;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_C4F_N3F_V3F = 0x2A26;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_C4UB_V2F = 0x2A22;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_C4UB_V3F = 0x2A23;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_CALLIGRAPHIC_FRAGMENT_SGIX = 0x8183;

    public static final int GL_CAVEAT_SUPPORT = 0x82B8;

    /// Group: `FrontFaceDirection`
    public static final int GL_CCW = 0x0901;

    /// Group: `PathCoordType`
    public static final int GL_CIRCULAR_CCW_ARC_TO_NV = 0xF8;

    /// Group: `PathCoordType`
    public static final int GL_CIRCULAR_CW_ARC_TO_NV = 0xFA;

    /// Group: `PathCoordType`
    public static final int GL_CIRCULAR_TANGENT_ARC_TO_NV = 0xFC;

    /// Group: `TextureWrapMode`
    public static final int GL_CLAMP = 0x2900;

    public static final int GL_CLAMP_FRAGMENT_COLOR = 0x891B;

    /// Group: `ClampColorTargetARB`
    public static final int GL_CLAMP_FRAGMENT_COLOR_ARB = 0x891B;

    /// Group: `ClampColorTargetARB`
    public static final int GL_CLAMP_READ_COLOR = 0x891C;

    /// Group: `ClampColorTargetARB`
    public static final int GL_CLAMP_READ_COLOR_ARB = 0x891C;

    /// Group: `TextureWrapMode`
    public static final int GL_CLAMP_TO_BORDER = 0x812D;

    /// Group: `TextureWrapMode`
    public static final int GL_CLAMP_TO_BORDER_ARB = 0x812D;

    public static final int GL_CLAMP_TO_BORDER_EXT = 0x812D;

    /// Group: `TextureWrapMode`
    public static final int GL_CLAMP_TO_BORDER_NV = 0x812D;

    public static final int GL_CLAMP_TO_BORDER_OES = 0x812D;

    /// Group: `TextureWrapMode`
    public static final int GL_CLAMP_TO_BORDER_SGIS = 0x812D;

    /// Group: `TextureWrapMode`
    public static final int GL_CLAMP_TO_EDGE = 0x812F;

    /// Group: `TextureWrapMode`
    public static final int GL_CLAMP_TO_EDGE_SGIS = 0x812F;

    public static final int GL_CLAMP_VERTEX_COLOR = 0x891A;

    /// Group: `ClampColorTargetARB`
    public static final int GL_CLAMP_VERTEX_COLOR_ARB = 0x891A;

    /// Group: `LogicOp`
    public static final int GL_CLEAR = 0x1500;

    /// Group: `InternalFormatPName`
    public static final int GL_CLEAR_BUFFER = 0x82B4;

    /// Group: `InternalFormatPName`
    public static final int GL_CLEAR_TEXTURE = 0x9365;

    public static final int GL_CLIENT_ACTIVE_TEXTURE = 0x84E1;

    public static final int GL_CLIENT_ACTIVE_TEXTURE_ARB = 0x84E1;

    /// Group: `ClientAttribMask`
    public static final int GL_CLIENT_ALL_ATTRIB_BITS = 0xFFFFFFFF;

    /// Group: `GetPName`
    public static final int GL_CLIENT_ATTRIB_STACK_DEPTH = 0x0BB1;

    /// Group: `MemoryBarrierMask`
    public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT = 0x00004000;

    /// Group: `MemoryBarrierMask`
    public static final int GL_CLIENT_MAPPED_BUFFER_BARRIER_BIT_EXT = 0x00004000;

    /// Group: `ClientAttribMask`
    public static final int GL_CLIENT_PIXEL_STORE_BIT = 0x00000001;

    /// Group: `BufferStorageMask`
    public static final int GL_CLIENT_STORAGE_BIT = 0x0200;

    /// Group: `BufferStorageMask`
    public static final int GL_CLIENT_STORAGE_BIT_EXT = 0x0200;

    /// Group: `ClientAttribMask`
    public static final int GL_CLIENT_VERTEX_ARRAY_BIT = 0x00000002;

    public static final int GL_CLIPPING_INPUT_PRIMITIVES = 0x82F6;

    /// Alias: `GL_CLIPPING_INPUT_PRIMITIVES`
    public static final int GL_CLIPPING_INPUT_PRIMITIVES_ARB = 0x82F6;

    public static final int GL_CLIPPING_OUTPUT_PRIMITIVES = 0x82F7;

    /// Alias: `GL_CLIPPING_OUTPUT_PRIMITIVES`
    public static final int GL_CLIPPING_OUTPUT_PRIMITIVES_ARB = 0x82F7;

    public static final int GL_CLIP_DEPTH_MODE = 0x935D;

    /// Alias: `GL_CLIP_DEPTH_MODE`
    public static final int GL_CLIP_DEPTH_MODE_EXT = 0x935D;

    /// Groups: `EnableCap`, `ClipPlaneName`
    /// Alias: `GL_CLIP_PLANE0`
    public static final int GL_CLIP_DISTANCE0 = 0x3000;

    public static final int GL_CLIP_DISTANCE0_APPLE = 0x3000;

    /// Alias: `GL_CLIP_PLANE0`
    public static final int GL_CLIP_DISTANCE0_EXT = 0x3000;

    /// Groups: `EnableCap`, `ClipPlaneName`
    /// Alias: `GL_CLIP_PLANE1`
    public static final int GL_CLIP_DISTANCE1 = 0x3001;

    public static final int GL_CLIP_DISTANCE1_APPLE = 0x3001;

    /// Alias: `GL_CLIP_PLANE1`
    public static final int GL_CLIP_DISTANCE1_EXT = 0x3001;

    /// Groups: `EnableCap`, `ClipPlaneName`
    /// Alias: `GL_CLIP_PLANE2`
    public static final int GL_CLIP_DISTANCE2 = 0x3002;

    public static final int GL_CLIP_DISTANCE2_APPLE = 0x3002;

    /// Alias: `GL_CLIP_PLANE2`
    public static final int GL_CLIP_DISTANCE2_EXT = 0x3002;

    /// Groups: `EnableCap`, `ClipPlaneName`
    /// Alias: `GL_CLIP_PLANE3`
    public static final int GL_CLIP_DISTANCE3 = 0x3003;

    public static final int GL_CLIP_DISTANCE3_APPLE = 0x3003;

    /// Alias: `GL_CLIP_PLANE3`
    public static final int GL_CLIP_DISTANCE3_EXT = 0x3003;

    /// Groups: `EnableCap`, `ClipPlaneName`
    /// Alias: `GL_CLIP_PLANE4`
    public static final int GL_CLIP_DISTANCE4 = 0x3004;

    public static final int GL_CLIP_DISTANCE4_APPLE = 0x3004;

    /// Alias: `GL_CLIP_PLANE4`
    public static final int GL_CLIP_DISTANCE4_EXT = 0x3004;

    /// Groups: `EnableCap`, `ClipPlaneName`
    /// Alias: `GL_CLIP_PLANE5`
    public static final int GL_CLIP_DISTANCE5 = 0x3005;

    public static final int GL_CLIP_DISTANCE5_APPLE = 0x3005;

    /// Alias: `GL_CLIP_PLANE5`
    public static final int GL_CLIP_DISTANCE5_EXT = 0x3005;

    /// Groups: `EnableCap`, `ClipPlaneName`
    public static final int GL_CLIP_DISTANCE6 = 0x3006;

    public static final int GL_CLIP_DISTANCE6_APPLE = 0x3006;

    /// Alias: `GL_CLIP_DISTANCE6`
    public static final int GL_CLIP_DISTANCE6_EXT = 0x3006;

    /// Groups: `EnableCap`, `ClipPlaneName`
    public static final int GL_CLIP_DISTANCE7 = 0x3007;

    public static final int GL_CLIP_DISTANCE7_APPLE = 0x3007;

    /// Alias: `GL_CLIP_DISTANCE7`
    public static final int GL_CLIP_DISTANCE7_EXT = 0x3007;

    public static final int GL_CLIP_DISTANCE_NV = 0x8C7A;

    /// Group: `HintTarget`
    public static final int GL_CLIP_FAR_HINT_PGI = 0x1A221;

    /// Group: `HintTarget`
    public static final int GL_CLIP_NEAR_HINT_PGI = 0x1A220;

    public static final int GL_CLIP_ORIGIN = 0x935C;

    /// Alias: `GL_CLIP_ORIGIN`
    public static final int GL_CLIP_ORIGIN_EXT = 0x935C;

    /// Groups: `GetPName`, `ClipPlaneName`, `EnableCap`
    public static final int GL_CLIP_PLANE0 = 0x3000;

    public static final int GL_CLIP_PLANE0_IMG = 0x3000;

    /// Groups: `GetPName`, `ClipPlaneName`, `EnableCap`
    public static final int GL_CLIP_PLANE1 = 0x3001;

    public static final int GL_CLIP_PLANE1_IMG = 0x3001;

    /// Groups: `GetPName`, `ClipPlaneName`, `EnableCap`
    public static final int GL_CLIP_PLANE2 = 0x3002;

    public static final int GL_CLIP_PLANE2_IMG = 0x3002;

    /// Groups: `GetPName`, `ClipPlaneName`, `EnableCap`
    public static final int GL_CLIP_PLANE3 = 0x3003;

    public static final int GL_CLIP_PLANE3_IMG = 0x3003;

    /// Groups: `GetPName`, `ClipPlaneName`, `EnableCap`
    public static final int GL_CLIP_PLANE4 = 0x3004;

    public static final int GL_CLIP_PLANE4_IMG = 0x3004;

    /// Groups: `GetPName`, `ClipPlaneName`, `EnableCap`
    public static final int GL_CLIP_PLANE5 = 0x3005;

    public static final int GL_CLIP_PLANE5_IMG = 0x3005;

    /// Group: `HintTarget`
    public static final int GL_CLIP_VOLUME_CLIPPING_HINT_EXT = 0x80F0;

    /// Group: `PathCoordType`
    public static final int GL_CLOSE_PATH_NV = 0x00;

    /// Group: `PixelFormat`
    public static final int GL_CMYKA_EXT = 0x800D;

    /// Group: `PixelFormat`
    public static final int GL_CMYK_EXT = 0x800C;

    /// Group: `FragmentOp3ATI`
    public static final int GL_CND0_ATI = 0x896B;

    /// Group: `FragmentOp3ATI`
    public static final int GL_CND_ATI = 0x896A;

    /// Groups: `MapQuery`, `GetMapQuery`
    public static final int GL_COEFF = 0x0A00;

    /// Groups: `Buffer`, `PixelCopyType`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR = 0x1800;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_COLOR3_BIT_PGI = 0x00010000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_COLOR4_BIT_PGI = 0x00020000;

    public static final int GL_COLORBURN = 0x929A;

    public static final int GL_COLORBURN_KHR = 0x929A;

    public static final int GL_COLORBURN_NV = 0x929A;

    public static final int GL_COLORDODGE = 0x9299;

    public static final int GL_COLORDODGE_KHR = 0x9299;

    public static final int GL_COLORDODGE_NV = 0x9299;

    public static final int GL_COLOR_ALPHA_PAIRING_ATI = 0x8975;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_COLOR_ARRAY = 0x8076;

    public static final int GL_COLOR_ARRAY_ADDRESS_NV = 0x8F23;

    public static final int GL_COLOR_ARRAY_BUFFER_BINDING = 0x8898;

    public static final int GL_COLOR_ARRAY_BUFFER_BINDING_ARB = 0x8898;

    /// Group: `GetPName`
    public static final int GL_COLOR_ARRAY_COUNT_EXT = 0x8084;

    public static final int GL_COLOR_ARRAY_EXT = 0x8076;

    public static final int GL_COLOR_ARRAY_LENGTH_NV = 0x8F2D;

    public static final int GL_COLOR_ARRAY_LIST_IBM = 103072;

    public static final int GL_COLOR_ARRAY_LIST_STRIDE_IBM = 103082;

    public static final int GL_COLOR_ARRAY_PARALLEL_POINTERS_INTEL = 0x83F7;

    /// Group: `GetPointervPName`
    public static final int GL_COLOR_ARRAY_POINTER = 0x8090;

    /// Group: `GetPointervPName`
    public static final int GL_COLOR_ARRAY_POINTER_EXT = 0x8090;

    /// Group: `GetPName`
    public static final int GL_COLOR_ARRAY_SIZE = 0x8081;

    public static final int GL_COLOR_ARRAY_SIZE_EXT = 0x8081;

    /// Group: `GetPName`
    public static final int GL_COLOR_ARRAY_STRIDE = 0x8083;

    public static final int GL_COLOR_ARRAY_STRIDE_EXT = 0x8083;

    /// Group: `GetPName`
    public static final int GL_COLOR_ARRAY_TYPE = 0x8082;

    public static final int GL_COLOR_ARRAY_TYPE_EXT = 0x8082;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT0 = 0x8CE0;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT0_EXT = 0x8CE0;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT0_NV = 0x8CE0;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT0_OES = 0x8CE0;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT1 = 0x8CE1;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT10 = 0x8CEA;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT10_EXT = 0x8CEA;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT10_NV = 0x8CEA;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT11 = 0x8CEB;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT11_EXT = 0x8CEB;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT11_NV = 0x8CEB;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT12 = 0x8CEC;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT12_EXT = 0x8CEC;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT12_NV = 0x8CEC;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT13 = 0x8CED;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT13_EXT = 0x8CED;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT13_NV = 0x8CED;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT14 = 0x8CEE;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT14_EXT = 0x8CEE;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT14_NV = 0x8CEE;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT15 = 0x8CEF;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT15_EXT = 0x8CEF;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT15_NV = 0x8CEF;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT16 = 0x8CF0;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT17 = 0x8CF1;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT18 = 0x8CF2;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT19 = 0x8CF3;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT1_EXT = 0x8CE1;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT1_NV = 0x8CE1;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT2 = 0x8CE2;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT20 = 0x8CF4;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT21 = 0x8CF5;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT22 = 0x8CF6;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT23 = 0x8CF7;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT24 = 0x8CF8;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT25 = 0x8CF9;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT26 = 0x8CFA;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT27 = 0x8CFB;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT28 = 0x8CFC;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT29 = 0x8CFD;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT2_EXT = 0x8CE2;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT2_NV = 0x8CE2;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT3 = 0x8CE3;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT30 = 0x8CFE;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `FramebufferAttachment`,
    ///   `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT31 = 0x8CFF;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT3_EXT = 0x8CE3;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT3_NV = 0x8CE3;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT4 = 0x8CE4;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT4_EXT = 0x8CE4;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT4_NV = 0x8CE4;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT5 = 0x8CE5;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT5_EXT = 0x8CE5;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT5_NV = 0x8CE5;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT6 = 0x8CE6;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT6_EXT = 0x8CE6;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT6_NV = 0x8CE6;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT7 = 0x8CE7;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT7_EXT = 0x8CE7;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT7_NV = 0x8CE7;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT8 = 0x8CE8;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT8_EXT = 0x8CE8;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT8_NV = 0x8CE8;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`,
    ///   `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT9 = 0x8CE9;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_COLOR_ATTACHMENT9_EXT = 0x8CE9;

    /// Groups: `InvalidateFramebufferAttachment`, `DrawBufferMode`
    public static final int GL_COLOR_ATTACHMENT9_NV = 0x8CE9;

    public static final int GL_COLOR_ATTACHMENT_EXT = 0x90F0;

    /// Groups: `ClearBufferMask`, `AttribMask`
    public static final int GL_COLOR_BUFFER_BIT = 0x00004000;

    /// Group: `BufferBitQCOM`
    public static final int GL_COLOR_BUFFER_BIT0_QCOM = 0x00000001;

    /// Group: `BufferBitQCOM`
    public static final int GL_COLOR_BUFFER_BIT1_QCOM = 0x00000002;

    /// Group: `BufferBitQCOM`
    public static final int GL_COLOR_BUFFER_BIT2_QCOM = 0x00000004;

    /// Group: `BufferBitQCOM`
    public static final int GL_COLOR_BUFFER_BIT3_QCOM = 0x00000008;

    /// Group: `BufferBitQCOM`
    public static final int GL_COLOR_BUFFER_BIT4_QCOM = 0x00000010;

    /// Group: `BufferBitQCOM`
    public static final int GL_COLOR_BUFFER_BIT5_QCOM = 0x00000020;

    /// Group: `BufferBitQCOM`
    public static final int GL_COLOR_BUFFER_BIT6_QCOM = 0x00000040;

    /// Group: `BufferBitQCOM`
    public static final int GL_COLOR_BUFFER_BIT7_QCOM = 0x00000080;

    public static final int GL_COLOR_CLEAR_UNCLAMPED_VALUE_ATI = 0x8835;

    /// Group: `GetPName`
    public static final int GL_COLOR_CLEAR_VALUE = 0x0C22;

    /// Group: `InternalFormatPName`
    public static final int GL_COLOR_COMPONENTS = 0x8283;

    /// Group: `InternalFormatPName`
    public static final int GL_COLOR_ENCODING = 0x8296;

    /// Group: `PixelCopyType`
    public static final int GL_COLOR_EXT = 0x1800;

    public static final int GL_COLOR_FLOAT_APPLE = 0x8A0F;

    /// Group: `PixelFormat`
    public static final int GL_COLOR_INDEX = 0x1900;

    public static final int GL_COLOR_INDEX12_EXT = 0x80E6;

    public static final int GL_COLOR_INDEX16_EXT = 0x80E7;

    public static final int GL_COLOR_INDEX1_EXT = 0x80E2;

    public static final int GL_COLOR_INDEX2_EXT = 0x80E3;

    public static final int GL_COLOR_INDEX4_EXT = 0x80E4;

    public static final int GL_COLOR_INDEX8_EXT = 0x80E5;

    /// Group: `MaterialParameter`
    public static final int GL_COLOR_INDEXES = 0x1603;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_COLOR_LOGIC_OP = 0x0BF2;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_COLOR_MATERIAL = 0x0B57;

    /// Group: `GetPName`
    public static final int GL_COLOR_MATERIAL_FACE = 0x0B55;

    /// Group: `GetPName`
    public static final int GL_COLOR_MATERIAL_PARAMETER = 0x0B56;

    public static final int GL_COLOR_MATRIX = 0x80B1;

    /// Group: `GetPName`
    public static final int GL_COLOR_MATRIX_SGI = 0x80B1;

    public static final int GL_COLOR_MATRIX_STACK_DEPTH = 0x80B2;

    /// Group: `GetPName`
    public static final int GL_COLOR_MATRIX_STACK_DEPTH_SGI = 0x80B2;

    /// Group: `InternalFormatPName`
    public static final int GL_COLOR_RENDERABLE = 0x8286;

    public static final int GL_COLOR_SAMPLES_NV = 0x8E20;

    public static final int GL_COLOR_SUM = 0x8458;

    public static final int GL_COLOR_SUM_ARB = 0x8458;

    public static final int GL_COLOR_SUM_CLAMP_NV = 0x854F;

    public static final int GL_COLOR_SUM_EXT = 0x8458;

    /// Groups: `ColorTableTarget`, `ColorTableTargetSGI`, `EnableCap`
    public static final int GL_COLOR_TABLE = 0x80D0;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_ALPHA_SIZE = 0x80DD;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_ALPHA_SIZE_SGI = 0x80DD;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_BIAS = 0x80D7;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_BIAS_SGI = 0x80D7;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_BLUE_SIZE = 0x80DC;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_BLUE_SIZE_SGI = 0x80DC;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_FORMAT = 0x80D8;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_FORMAT_SGI = 0x80D8;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_GREEN_SIZE = 0x80DB;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_GREEN_SIZE_SGI = 0x80DB;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_INTENSITY_SIZE = 0x80DF;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_INTENSITY_SIZE_SGI = 0x80DF;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_LUMINANCE_SIZE = 0x80DE;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_LUMINANCE_SIZE_SGI = 0x80DE;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_RED_SIZE = 0x80DA;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_RED_SIZE_SGI = 0x80DA;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_SCALE = 0x80D6;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_SCALE_SGI = 0x80D6;

    /// Groups: `GetPName`, `ColorTableTargetSGI`, `EnableCap`
    public static final int GL_COLOR_TABLE_SGI = 0x80D0;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_WIDTH = 0x80D9;

    /// Group: `ColorTableParameterPName`
    public static final int GL_COLOR_TABLE_WIDTH_SGI = 0x80D9;

    /// Group: `GetPName`
    public static final int GL_COLOR_WRITEMASK = 0x0C23;

    /// Groups: `TextureEnvParameter`, `TextureEnvMode`
    public static final int GL_COMBINE = 0x8570;

    public static final int GL_COMBINE4_NV = 0x8503;

    /// Group: `CombinerStageNV`
    public static final int GL_COMBINER0_NV = 0x8550;

    /// Group: `CombinerStageNV`
    public static final int GL_COMBINER1_NV = 0x8551;

    /// Group: `CombinerStageNV`
    public static final int GL_COMBINER2_NV = 0x8552;

    /// Group: `CombinerStageNV`
    public static final int GL_COMBINER3_NV = 0x8553;

    /// Group: `CombinerStageNV`
    public static final int GL_COMBINER4_NV = 0x8554;

    /// Group: `CombinerStageNV`
    public static final int GL_COMBINER5_NV = 0x8555;

    /// Group: `CombinerStageNV`
    public static final int GL_COMBINER6_NV = 0x8556;

    /// Group: `CombinerStageNV`
    public static final int GL_COMBINER7_NV = 0x8557;

    public static final int GL_COMBINER_AB_DOT_PRODUCT_NV = 0x8545;

    public static final int GL_COMBINER_AB_OUTPUT_NV = 0x854A;

    public static final int GL_COMBINER_BIAS_NV = 0x8549;

    public static final int GL_COMBINER_CD_DOT_PRODUCT_NV = 0x8546;

    public static final int GL_COMBINER_CD_OUTPUT_NV = 0x854B;

    /// Group: `CombinerParameterNV`
    public static final int GL_COMBINER_COMPONENT_USAGE_NV = 0x8544;

    /// Group: `CombinerParameterNV`
    public static final int GL_COMBINER_INPUT_NV = 0x8542;

    /// Group: `CombinerParameterNV`
    public static final int GL_COMBINER_MAPPING_NV = 0x8543;

    public static final int GL_COMBINER_MUX_SUM_NV = 0x8547;

    public static final int GL_COMBINER_SCALE_NV = 0x8548;

    public static final int GL_COMBINER_SUM_OUTPUT_NV = 0x854C;

    /// Group: `TextureEnvParameter`
    public static final int GL_COMBINE_ALPHA = 0x8572;

    /// Group: `TextureEnvParameter`
    public static final int GL_COMBINE_ALPHA_ARB = 0x8572;

    /// Group: `TextureEnvParameter`
    public static final int GL_COMBINE_ALPHA_EXT = 0x8572;

    /// Group: `TextureEnvParameter`
    public static final int GL_COMBINE_ARB = 0x8570;

    /// Group: `TextureEnvParameter`
    public static final int GL_COMBINE_EXT = 0x8570;

    /// Group: `TextureEnvParameter`
    public static final int GL_COMBINE_RGB = 0x8571;

    /// Group: `TextureEnvParameter`
    public static final int GL_COMBINE_RGB_ARB = 0x8571;

    /// Group: `TextureEnvParameter`
    public static final int GL_COMBINE_RGB_EXT = 0x8571;

    /// Group: `MemoryBarrierMask`
    public static final int GL_COMMAND_BARRIER_BIT = 0x00000040;

    /// Group: `MemoryBarrierMask`
    public static final int GL_COMMAND_BARRIER_BIT_EXT = 0x00000040;

    public static final int GL_COMPARE_REF_DEPTH_TO_TEXTURE_EXT = 0x884E;

    /// Group: `TextureCompareMode`
    /// Alias: `GL_COMPARE_R_TO_TEXTURE`
    public static final int GL_COMPARE_REF_TO_TEXTURE = 0x884E;

    public static final int GL_COMPARE_REF_TO_TEXTURE_EXT = 0x884E;

    /// Group: `TextureCompareMode`
    public static final int GL_COMPARE_R_TO_TEXTURE = 0x884E;

    public static final int GL_COMPARE_R_TO_TEXTURE_ARB = 0x884E;

    /// Groups: `ProgramResourceProperty`, `SubroutineParameterName`
    public static final int GL_COMPATIBLE_SUBROUTINES = 0x8E4B;

    /// Group: `ListMode`
    public static final int GL_COMPILE = 0x1300;

    /// Group: `ListMode`
    public static final int GL_COMPILE_AND_EXECUTE = 0x1301;

    /// Group: `ShaderParameterName`
    public static final int GL_COMPILE_STATUS = 0x8B81;

    /// Alias: `GL_COMPLETION_STATUS_KHR`
    public static final int GL_COMPLETION_STATUS_ARB = 0x91B1;

    public static final int GL_COMPLETION_STATUS_KHR = 0x91B1;

    public static final int GL_COMPRESSED_ALPHA = 0x84E9;

    public static final int GL_COMPRESSED_ALPHA_ARB = 0x84E9;

    public static final int GL_COMPRESSED_INTENSITY = 0x84EC;

    public static final int GL_COMPRESSED_INTENSITY_ARB = 0x84EC;

    public static final int GL_COMPRESSED_LUMINANCE = 0x84EA;

    public static final int GL_COMPRESSED_LUMINANCE_ALPHA = 0x84EB;

    public static final int GL_COMPRESSED_LUMINANCE_ALPHA_3DC_ATI = 0x8837;

    public static final int GL_COMPRESSED_LUMINANCE_ALPHA_ARB = 0x84EB;

    public static final int GL_COMPRESSED_LUMINANCE_ALPHA_LATC2_EXT = 0x8C72;

    public static final int GL_COMPRESSED_LUMINANCE_ARB = 0x84EA;

    public static final int GL_COMPRESSED_LUMINANCE_LATC1_EXT = 0x8C70;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_R11_EAC = 0x9270;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_R11_EAC_OES = 0x9270;

    /// Group: `InternalFormat`
    public static final int GL_COMPRESSED_RED = 0x8225;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RED_GREEN_RGTC2_EXT = 0x8DBD;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RED_RGTC1 = 0x8DBB;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RED_RGTC1_EXT = 0x8DBB;

    /// Group: `InternalFormat`
    public static final int GL_COMPRESSED_RG = 0x8226;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RG11_EAC = 0x9272;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RG11_EAC_OES = 0x9272;

    /// Group: `InternalFormat`
    public static final int GL_COMPRESSED_RGB = 0x84ED;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB8_ETC2 = 0x9274;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB8_ETC2_OES = 0x9274;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 0x9276;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB8_PUNCHTHROUGH_ALPHA1_ETC2_OES = 0x9276;

    /// Group: `InternalFormat`
    public static final int GL_COMPRESSED_RGBA = 0x84EE;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA8_ETC2_EAC = 0x9278;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA8_ETC2_EAC_OES = 0x9278;

    public static final int GL_COMPRESSED_RGBA_ARB = 0x84EE;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_10x10 = 0x93BB;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_10x10_KHR = 0x93BB;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_10x5 = 0x93B8;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_10x5_KHR = 0x93B8;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_10x6 = 0x93B9;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_10x6_KHR = 0x93B9;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_10x8 = 0x93BA;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_10x8_KHR = 0x93BA;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_12x10 = 0x93BC;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_12x10_KHR = 0x93BC;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_12x12 = 0x93BD;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_12x12_KHR = 0x93BD;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_3x3x3_OES = 0x93C0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_4x3x3_OES = 0x93C1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_4x4 = 0x93B0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_4x4_KHR = 0x93B0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_4x4x3_OES = 0x93C2;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_4x4x4_OES = 0x93C3;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_5x4 = 0x93B1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_5x4_KHR = 0x93B1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_5x4x4_OES = 0x93C4;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_5x5 = 0x93B2;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_5x5_KHR = 0x93B2;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_5x5x4_OES = 0x93C5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_5x5x5_OES = 0x93C6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_6x5 = 0x93B3;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_6x5_KHR = 0x93B3;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_6x5x5_OES = 0x93C7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_6x6 = 0x93B4;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_6x6_KHR = 0x93B4;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_6x6x5_OES = 0x93C8;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_6x6x6_OES = 0x93C9;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_8x5 = 0x93B5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_8x5_KHR = 0x93B5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_8x6 = 0x93B6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_8x6_KHR = 0x93B6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_8x8 = 0x93B7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_RGBA_ASTC_8x8_KHR = 0x93B7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA_BPTC_UNORM = 0x8E8C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA_BPTC_UNORM_ARB = 0x8E8C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA_BPTC_UNORM_EXT = 0x8E8C;

    public static final int GL_COMPRESSED_RGBA_FXT1_3DFX = 0x86B1;

    public static final int GL_COMPRESSED_RGBA_PVRTC_2BPPV1_IMG = 0x8C03;

    public static final int GL_COMPRESSED_RGBA_PVRTC_2BPPV2_IMG = 0x9137;

    public static final int GL_COMPRESSED_RGBA_PVRTC_4BPPV1_IMG = 0x8C02;

    public static final int GL_COMPRESSED_RGBA_PVRTC_4BPPV2_IMG = 0x9138;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT1_EXT = 0x83F1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT3_ANGLE = 0x83F2;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT3_EXT = 0x83F2;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT5_ANGLE = 0x83F3;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGBA_S3TC_DXT5_EXT = 0x83F3;

    public static final int GL_COMPRESSED_RGB_ARB = 0x84ED;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT = 0x8E8E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT_ARB = 0x8E8E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB_BPTC_SIGNED_FLOAT_EXT = 0x8E8E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT = 0x8E8F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT_ARB = 0x8E8F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB_BPTC_UNSIGNED_FLOAT_EXT = 0x8E8F;

    public static final int GL_COMPRESSED_RGB_FXT1_3DFX = 0x86B0;

    public static final int GL_COMPRESSED_RGB_PVRTC_2BPPV1_IMG = 0x8C01;

    public static final int GL_COMPRESSED_RGB_PVRTC_4BPPV1_IMG = 0x8C00;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RGB_S3TC_DXT1_EXT = 0x83F0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_RG_RGTC2 = 0x8DBD;

    public static final int GL_COMPRESSED_SIGNED_LUMINANCE_ALPHA_LATC2_EXT = 0x8C73;

    public static final int GL_COMPRESSED_SIGNED_LUMINANCE_LATC1_EXT = 0x8C71;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SIGNED_R11_EAC = 0x9271;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SIGNED_R11_EAC_OES = 0x9271;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SIGNED_RED_GREEN_RGTC2_EXT = 0x8DBE;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SIGNED_RED_RGTC1 = 0x8DBC;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SIGNED_RED_RGTC1_EXT = 0x8DBC;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SIGNED_RG11_EAC = 0x9273;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SIGNED_RG11_EAC_OES = 0x9273;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SIGNED_RG_RGTC2 = 0x8DBE;

    public static final int GL_COMPRESSED_SLUMINANCE = 0x8C4A;

    public static final int GL_COMPRESSED_SLUMINANCE_ALPHA = 0x8C4B;

    public static final int GL_COMPRESSED_SLUMINANCE_ALPHA_EXT = 0x8C4B;

    public static final int GL_COMPRESSED_SLUMINANCE_EXT = 0x8C4A;

    /// Group: `InternalFormat`
    public static final int GL_COMPRESSED_SRGB = 0x8C48;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10 = 0x93DB;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x10_KHR = 0x93DB;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5 = 0x93D8;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x5_KHR = 0x93D8;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6 = 0x93D9;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x6_KHR = 0x93D9;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8 = 0x93DA;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_10x8_KHR = 0x93DA;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10 = 0x93DC;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x10_KHR = 0x93DC;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12 = 0x93DD;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_12x12_KHR = 0x93DD;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_3x3x3_OES = 0x93E0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x3x3_OES = 0x93E1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4 = 0x93D0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4_KHR = 0x93D0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4x3_OES = 0x93E2;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_4x4x4_OES = 0x93E3;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4 = 0x93D1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4_KHR = 0x93D1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x4x4_OES = 0x93E4;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5 = 0x93D2;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5_KHR = 0x93D2;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5x4_OES = 0x93E5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_5x5x5_OES = 0x93E6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5 = 0x93D3;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5_KHR = 0x93D3;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x5x5_OES = 0x93E7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6 = 0x93D4;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6_KHR = 0x93D4;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6x5_OES = 0x93E8;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_6x6x6_OES = 0x93E9;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5 = 0x93D5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x5_KHR = 0x93D5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6 = 0x93D6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x6_KHR = 0x93D6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8 = 0x93D7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`

    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ASTC_8x8_KHR = 0x93D7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC = 0x9279;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB8_ALPHA8_ETC2_EAC_OES = 0x9279;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB8_ETC2 = 0x9275;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB8_ETC2_OES = 0x9275;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2 = 0x9277;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB8_PUNCHTHROUGH_ALPHA1_ETC2_OES = 0x9277;

    /// Group: `InternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA = 0x8C49;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM = 0x8E8D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM_ARB = 0x8E8D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_BPTC_UNORM_EXT = 0x8E8D;

    public static final int GL_COMPRESSED_SRGB_ALPHA_EXT = 0x8C49;

    public static final int GL_COMPRESSED_SRGB_ALPHA_PVRTC_2BPPV1_EXT = 0x8A56;

    public static final int GL_COMPRESSED_SRGB_ALPHA_PVRTC_2BPPV2_IMG = 0x93F0;

    public static final int GL_COMPRESSED_SRGB_ALPHA_PVRTC_4BPPV1_EXT = 0x8A57;

    public static final int GL_COMPRESSED_SRGB_ALPHA_PVRTC_4BPPV2_IMG = 0x93F1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT1_EXT = 0x8C4D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT1_NV = 0x8C4D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT3_EXT = 0x8C4E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT3_NV = 0x8C4E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT5_EXT = 0x8C4F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_ALPHA_S3TC_DXT5_NV = 0x8C4F;

    public static final int GL_COMPRESSED_SRGB_EXT = 0x8C48;

    public static final int GL_COMPRESSED_SRGB_PVRTC_2BPPV1_EXT = 0x8A54;

    public static final int GL_COMPRESSED_SRGB_PVRTC_4BPPV1_EXT = 0x8A55;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_S3TC_DXT1_EXT = 0x8C4C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_COMPRESSED_SRGB_S3TC_DXT1_NV = 0x8C4C;

    /// Group: `GetPName`
    public static final int GL_COMPRESSED_TEXTURE_FORMATS = 0x86A3;

    public static final int GL_COMPRESSED_TEXTURE_FORMATS_ARB = 0x86A3;

    /// Group: `ProgramTarget`
    public static final int GL_COMPUTE_PROGRAM_NV = 0x90FB;

    public static final int GL_COMPUTE_PROGRAM_PARAMETER_BUFFER_NV = 0x90FC;

    /// Group: `ShaderType`
    public static final int GL_COMPUTE_SHADER = 0x91B9;

    /// Group: `UseProgramStageMask`
    public static final int GL_COMPUTE_SHADER_BIT = 0x00000020;

    public static final int GL_COMPUTE_SHADER_INVOCATIONS = 0x82F5;

    /// Alias: `GL_COMPUTE_SHADER_INVOCATIONS`
    public static final int GL_COMPUTE_SHADER_INVOCATIONS_ARB = 0x82F5;

    /// Group: `ProgramInterface`
    public static final int GL_COMPUTE_SUBROUTINE = 0x92ED;

    /// Group: `ProgramInterface`
    public static final int GL_COMPUTE_SUBROUTINE_UNIFORM = 0x92F3;

    /// Group: `InternalFormatPName`
    public static final int GL_COMPUTE_TEXTURE = 0x82A0;

    /// Group: `ProgramPropertyARB`
    public static final int GL_COMPUTE_WORK_GROUP_SIZE = 0x8267;

    /// Group: `FragmentShaderColorModMaskATI`
    public static final int GL_COMP_BIT_ATI = 0x00000002;

    /// Group: `SyncStatus`
    public static final int GL_CONDITION_SATISFIED = 0x911C;

    public static final int GL_CONDITION_SATISFIED_APPLE = 0x911C;

    public static final int GL_CONFORMANT_NV = 0x9374;

    /// Group: `PathCoordType`
    public static final int GL_CONIC_CURVE_TO_NV = 0x1A;

    public static final int GL_CONJOINT_NV = 0x9284;

    public static final int GL_CONSERVATIVE_RASTERIZATION_INTEL = 0x83FE;

    public static final int GL_CONSERVATIVE_RASTERIZATION_NV = 0x9346;

    public static final int GL_CONSERVATIVE_RASTER_DILATE_GRANULARITY_NV = 0x937B;

    public static final int GL_CONSERVATIVE_RASTER_DILATE_NV = 0x9379;

    public static final int GL_CONSERVATIVE_RASTER_DILATE_RANGE_NV = 0x937A;

    public static final int GL_CONSERVATIVE_RASTER_MODE_NV = 0x954D;

    public static final int GL_CONSERVATIVE_RASTER_MODE_POST_SNAP_NV = 0x954E;

    public static final int GL_CONSERVATIVE_RASTER_MODE_PRE_SNAP_NV = 0x9550;

    public static final int GL_CONSERVATIVE_RASTER_MODE_PRE_SNAP_TRIANGLES_NV = 0x954F;

    /// Group: `HintTarget`
    public static final int GL_CONSERVE_MEMORY_HINT_PGI = 0x1A1FD;

    /// Groups: `TextureEnvParameter`, `PathGenMode`
    public static final int GL_CONSTANT = 0x8576;

    /// Group: `BlendingFactor`
    public static final int GL_CONSTANT_ALPHA = 0x8003;

    public static final int GL_CONSTANT_ALPHA_EXT = 0x8003;

    /// Group: `TextureEnvParameter`
    public static final int GL_CONSTANT_ARB = 0x8576;

    /// Groups: `LightParameter`, `FragmentLightParameterSGIX`
    public static final int GL_CONSTANT_ATTENUATION = 0x1207;

    public static final int GL_CONSTANT_BORDER = 0x8151;

    public static final int GL_CONSTANT_BORDER_HP = 0x8151;

    /// Group: `BlendingFactor`
    public static final int GL_CONSTANT_COLOR = 0x8001;

    public static final int GL_CONSTANT_COLOR0_NV = 0x852A;

    public static final int GL_CONSTANT_COLOR1_NV = 0x852B;

    public static final int GL_CONSTANT_COLOR_EXT = 0x8001;

    /// Group: `TextureEnvParameter`
    public static final int GL_CONSTANT_EXT = 0x8576;

    /// Group: `TextureEnvParameter`
    public static final int GL_CONSTANT_NV = 0x8576;

    public static final int GL_CONST_EYE_NV = 0x86E5;

    /// Group: `ContextProfileMask`
    public static final int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 0x00000002;

    /// Group: `ContextProfileMask`
    public static final int GL_CONTEXT_CORE_PROFILE_BIT = 0x00000001;

    /// Group: `GetPName`
    public static final int GL_CONTEXT_FLAGS = 0x821E;

    /// Group: `ContextFlagMask`
    public static final int GL_CONTEXT_FLAG_DEBUG_BIT = 0x00000002;

    /// Group: `ContextFlagMask`
    public static final int GL_CONTEXT_FLAG_DEBUG_BIT_KHR = 0x00000002;

    /// Group: `ContextFlagMask`
    public static final int GL_CONTEXT_FLAG_FORWARD_COMPATIBLE_BIT = 0x00000001;

    /// Group: `ContextFlagMask`
    public static final int GL_CONTEXT_FLAG_NO_ERROR_BIT = 0x00000008;

    /// Group: `ContextFlagMask`
    /// Alias: `GL_CONTEXT_FLAG_NO_ERROR_BIT`
    public static final int GL_CONTEXT_FLAG_NO_ERROR_BIT_KHR = 0x00000008;

    /// Group: `ContextFlagMask`
    public static final int GL_CONTEXT_FLAG_PROTECTED_CONTENT_BIT_EXT = 0x00000010;

    /// Group: `ContextFlagMask`
    public static final int GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT = 0x00000004;

    /// Group: `ContextFlagMask`
    public static final int GL_CONTEXT_FLAG_ROBUST_ACCESS_BIT_ARB = 0x00000004;

    public static final int GL_CONTEXT_LOST = 0x0507;

    public static final int GL_CONTEXT_LOST_KHR = 0x0507;

    public static final int GL_CONTEXT_LOST_WEBGL = 0x9242;

    /// Group: `GetPName`
    public static final int GL_CONTEXT_PROFILE_MASK = 0x9126;

    public static final int GL_CONTEXT_RELEASE_BEHAVIOR = 0x82FB;

    public static final int GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH = 0x82FC;

    public static final int GL_CONTEXT_RELEASE_BEHAVIOR_FLUSH_KHR = 0x82FC;

    public static final int GL_CONTEXT_RELEASE_BEHAVIOR_KHR = 0x82FB;

    public static final int GL_CONTEXT_ROBUST_ACCESS = 0x90F3;

    public static final int GL_CONTEXT_ROBUST_ACCESS_EXT = 0x90F3;

    public static final int GL_CONTEXT_ROBUST_ACCESS_KHR = 0x90F3;

    public static final int GL_CONTINUOUS_AMD = 0x9007;

    public static final int GL_CONTRAST_NV = 0x92A1;

    /// Group: `PathCoverMode`
    public static final int GL_CONVEX_HULL_NV = 0x908B;

    /// Groups: `ConvolutionTarget`, `ConvolutionTargetEXT`
    public static final int GL_CONVOLUTION_1D = 0x8010;

    /// Groups: `GetPName`, `ConvolutionTargetEXT`, `EnableCap`
    public static final int GL_CONVOLUTION_1D_EXT = 0x8010;

    /// Groups: `ConvolutionTarget`, `ConvolutionTargetEXT`
    public static final int GL_CONVOLUTION_2D = 0x8011;

    /// Groups: `GetPName`, `ConvolutionTargetEXT`, `EnableCap`
    public static final int GL_CONVOLUTION_2D_EXT = 0x8011;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_BORDER_COLOR = 0x8154;

    public static final int GL_CONVOLUTION_BORDER_COLOR_HP = 0x8154;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_BORDER_MODE = 0x8013;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_BORDER_MODE_EXT = 0x8013;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_FILTER_BIAS = 0x8015;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_FILTER_BIAS_EXT = 0x8015;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_FILTER_SCALE = 0x8014;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_FILTER_SCALE_EXT = 0x8014;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_FORMAT = 0x8017;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_FORMAT_EXT = 0x8017;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_HEIGHT = 0x8019;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_HEIGHT_EXT = 0x8019;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_CONVOLUTION_HINT_SGIX = 0x8316;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_WIDTH = 0x8018;

    /// Group: `ConvolutionParameter`
    public static final int GL_CONVOLUTION_WIDTH_EXT = 0x8018;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_0_ATI = 0x8941;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_10_ATI = 0x894B;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_11_ATI = 0x894C;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_12_ATI = 0x894D;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_13_ATI = 0x894E;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_14_ATI = 0x894F;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_15_ATI = 0x8950;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_16_ATI = 0x8951;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_17_ATI = 0x8952;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_18_ATI = 0x8953;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_19_ATI = 0x8954;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_1_ATI = 0x8942;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_20_ATI = 0x8955;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_21_ATI = 0x8956;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_22_ATI = 0x8957;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_23_ATI = 0x8958;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_24_ATI = 0x8959;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_25_ATI = 0x895A;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_26_ATI = 0x895B;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_27_ATI = 0x895C;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_28_ATI = 0x895D;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_29_ATI = 0x895E;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_2_ATI = 0x8943;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_30_ATI = 0x895F;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_31_ATI = 0x8960;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_3_ATI = 0x8944;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_4_ATI = 0x8945;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_5_ATI = 0x8946;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_6_ATI = 0x8947;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_7_ATI = 0x8948;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_8_ATI = 0x8949;

    /// Groups: `FragmentShaderConATI`, `FragmentShaderGenericSourceATI`
    public static final int GL_CON_9_ATI = 0x894A;

    /// Group: `TextureEnvParameter`
    public static final int GL_COORD_REPLACE = 0x8862;

    public static final int GL_COORD_REPLACE_ARB = 0x8862;

    public static final int GL_COORD_REPLACE_NV = 0x8862;

    public static final int GL_COORD_REPLACE_OES = 0x8862;

    /// Group: `LogicOp`
    public static final int GL_COPY = 0x1503;

    /// Group: `LogicOp`
    public static final int GL_COPY_INVERTED = 0x150C;

    /// Group: `FeedBackToken`
    public static final int GL_COPY_PIXEL_TOKEN = 0x0706;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_COPY_READ_BUFFER = 0x8F36;

    /// Alias: `GL_COPY_READ_BUFFER`
    public static final int GL_COPY_READ_BUFFER_BINDING = 0x8F36;

    public static final int GL_COPY_READ_BUFFER_NV = 0x8F36;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_COPY_WRITE_BUFFER = 0x8F37;

    /// Alias: `GL_COPY_WRITE_BUFFER`
    public static final int GL_COPY_WRITE_BUFFER_BINDING = 0x8F37;

    public static final int GL_COPY_WRITE_BUFFER_NV = 0x8F37;

    public static final int GL_COUNTER_RANGE_AMD = 0x8BC1;

    public static final int GL_COUNTER_TYPE_AMD = 0x8BC0;

    /// Group: `PathFillMode`
    public static final int GL_COUNT_DOWN_NV = 0x9089;

    /// Group: `PathFillMode`
    public static final int GL_COUNT_UP_NV = 0x9088;

    public static final int GL_COVERAGE_ALL_FRAGMENTS_NV = 0x8ED5;

    public static final int GL_COVERAGE_ATTACHMENT_NV = 0x8ED2;

    public static final int GL_COVERAGE_AUTOMATIC_NV = 0x8ED7;

    public static final int GL_COVERAGE_BUFFERS_NV = 0x8ED3;

    /// Group: `ClearBufferMask`
    public static final int GL_COVERAGE_BUFFER_BIT_NV = 0x00008000;

    public static final int GL_COVERAGE_COMPONENT4_NV = 0x8ED1;

    public static final int GL_COVERAGE_COMPONENT_NV = 0x8ED0;

    public static final int GL_COVERAGE_EDGE_FRAGMENTS_NV = 0x8ED6;

    public static final int GL_COVERAGE_MODULATION_NV = 0x9332;

    public static final int GL_COVERAGE_MODULATION_TABLE_NV = 0x9331;

    public static final int GL_COVERAGE_MODULATION_TABLE_SIZE_NV = 0x9333;

    public static final int GL_COVERAGE_SAMPLES_NV = 0x8ED4;

    public static final int GL_CPU_OPTIMIZED_QCOM = 0x8FB1;

    /// Group: `PathCoordType`
    public static final int GL_CUBIC_CURVE_TO_NV = 0x0C;

    public static final int GL_CUBIC_EXT = 0x8334;

    public static final int GL_CUBIC_HP = 0x815F;

    public static final int GL_CUBIC_IMG = 0x9139;

    public static final int GL_CUBIC_MIPMAP_LINEAR_IMG = 0x913B;

    public static final int GL_CUBIC_MIPMAP_NEAREST_IMG = 0x913A;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_CULL_FACE = 0x0B44;

    /// Group: `GetPName`
    public static final int GL_CULL_FACE_MODE = 0x0B45;

    public static final int GL_CULL_FRAGMENT_NV = 0x86E7;

    public static final int GL_CULL_MODES_NV = 0x86E0;

    public static final int GL_CULL_VERTEX_EXT = 0x81AA;

    /// Group: `CullParameterEXT`
    public static final int GL_CULL_VERTEX_EYE_POSITION_EXT = 0x81AB;

    public static final int GL_CULL_VERTEX_IBM = 103050;

    /// Group: `CullParameterEXT`
    public static final int GL_CULL_VERTEX_OBJECT_POSITION_EXT = 0x81AC;

    public static final int GL_CURRENT_ATTRIB_NV = 0x8626;

    public static final int GL_CURRENT_BINORMAL_EXT = 0x843C;

    /// Group: `AttribMask`
    public static final int GL_CURRENT_BIT = 0x00000001;

    /// Group: `GetPName`
    public static final int GL_CURRENT_COLOR = 0x0B00;

    /// Alias: `GL_CURRENT_FOG_COORDINATE`
    public static final int GL_CURRENT_FOG_COORD = 0x8453;

    public static final int GL_CURRENT_FOG_COORDINATE = 0x8453;

    public static final int GL_CURRENT_FOG_COORDINATE_EXT = 0x8453;

    /// Group: `GetPName`
    public static final int GL_CURRENT_INDEX = 0x0B01;

    public static final int GL_CURRENT_MATRIX_ARB = 0x8641;

    public static final int GL_CURRENT_MATRIX_INDEX_ARB = 0x8845;

    public static final int GL_CURRENT_MATRIX_NV = 0x8641;

    public static final int GL_CURRENT_MATRIX_STACK_DEPTH_ARB = 0x8640;

    public static final int GL_CURRENT_MATRIX_STACK_DEPTH_NV = 0x8640;

    /// Group: `GetPName`
    public static final int GL_CURRENT_NORMAL = 0x0B02;

    public static final int GL_CURRENT_OCCLUSION_QUERY_ID_NV = 0x8865;

    public static final int GL_CURRENT_PALETTE_MATRIX_ARB = 0x8843;

    public static final int GL_CURRENT_PALETTE_MATRIX_OES = 0x8843;

    /// Group: `GetPName`
    public static final int GL_CURRENT_PROGRAM = 0x8B8D;

    /// Group: `QueryParameterName`
    public static final int GL_CURRENT_QUERY = 0x8865;

    public static final int GL_CURRENT_QUERY_ARB = 0x8865;

    public static final int GL_CURRENT_QUERY_EXT = 0x8865;

    /// Group: `GetPName`
    public static final int GL_CURRENT_RASTER_COLOR = 0x0B04;

    /// Group: `GetPName`
    public static final int GL_CURRENT_RASTER_DISTANCE = 0x0B09;

    /// Group: `GetPName`
    public static final int GL_CURRENT_RASTER_INDEX = 0x0B05;

    public static final int GL_CURRENT_RASTER_NORMAL_SGIX = 0x8406;

    /// Group: `GetPName`
    public static final int GL_CURRENT_RASTER_POSITION = 0x0B07;

    /// Group: `GetPName`
    public static final int GL_CURRENT_RASTER_POSITION_VALID = 0x0B08;

    public static final int GL_CURRENT_RASTER_SECONDARY_COLOR = 0x845F;

    /// Group: `GetPName`
    public static final int GL_CURRENT_RASTER_TEXTURE_COORDS = 0x0B06;

    public static final int GL_CURRENT_SECONDARY_COLOR = 0x8459;

    public static final int GL_CURRENT_SECONDARY_COLOR_EXT = 0x8459;

    public static final int GL_CURRENT_TANGENT_EXT = 0x843B;

    /// Groups: `GetPName`, `VertexShaderTextureUnitParameter`
    public static final int GL_CURRENT_TEXTURE_COORDS = 0x0B03;

    public static final int GL_CURRENT_TIME_NV = 0x8E28;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`
    public static final int GL_CURRENT_VERTEX_ATTRIB = 0x8626;

    public static final int GL_CURRENT_VERTEX_ATTRIB_ARB = 0x8626;

    /// Group: `VertexShaderParameterEXT`
    public static final int GL_CURRENT_VERTEX_EXT = 0x87E2;

    public static final int GL_CURRENT_VERTEX_WEIGHT_EXT = 0x850B;

    public static final int GL_CURRENT_WEIGHT_ARB = 0x86A8;

    /// Group: `FrontFaceDirection`
    public static final int GL_CW = 0x0900;

    /// Group: `SemaphoreParameterName`
    public static final int GL_D3D12_FENCE_VALUE_EXT = 0x9595;

    public static final int GL_DARKEN = 0x9297;

    public static final int GL_DARKEN_KHR = 0x9297;

    public static final int GL_DARKEN_NV = 0x9297;

    public static final int GL_DATA_BUFFER_AMD = 0x9151;

    public static final int GL_DEBUG_ASSERT_MESA = 0x875B;

    /// Group: `GetPointervPName`
    public static final int GL_DEBUG_CALLBACK_FUNCTION = 0x8244;

    public static final int GL_DEBUG_CALLBACK_FUNCTION_ARB = 0x8244;

    public static final int GL_DEBUG_CALLBACK_FUNCTION_KHR = 0x8244;

    /// Group: `GetPointervPName`
    public static final int GL_DEBUG_CALLBACK_USER_PARAM = 0x8245;

    public static final int GL_DEBUG_CALLBACK_USER_PARAM_ARB = 0x8245;

    public static final int GL_DEBUG_CALLBACK_USER_PARAM_KHR = 0x8245;

    public static final int GL_DEBUG_CATEGORY_API_ERROR_AMD = 0x9149;

    public static final int GL_DEBUG_CATEGORY_APPLICATION_AMD = 0x914F;

    public static final int GL_DEBUG_CATEGORY_DEPRECATION_AMD = 0x914B;

    public static final int GL_DEBUG_CATEGORY_OTHER_AMD = 0x9150;

    public static final int GL_DEBUG_CATEGORY_PERFORMANCE_AMD = 0x914D;

    public static final int GL_DEBUG_CATEGORY_SHADER_COMPILER_AMD = 0x914E;

    public static final int GL_DEBUG_CATEGORY_UNDEFINED_BEHAVIOR_AMD = 0x914C;

    public static final int GL_DEBUG_CATEGORY_WINDOW_SYSTEM_AMD = 0x914A;

    /// Group: `GetPName`
    public static final int GL_DEBUG_GROUP_STACK_DEPTH = 0x826D;

    public static final int GL_DEBUG_GROUP_STACK_DEPTH_KHR = 0x826D;

    public static final int GL_DEBUG_LOGGED_MESSAGES = 0x9145;

    public static final int GL_DEBUG_LOGGED_MESSAGES_AMD = 0x9145;

    public static final int GL_DEBUG_LOGGED_MESSAGES_ARB = 0x9145;

    public static final int GL_DEBUG_LOGGED_MESSAGES_KHR = 0x9145;

    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH = 0x8243;

    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH_ARB = 0x8243;

    public static final int GL_DEBUG_NEXT_LOGGED_MESSAGE_LENGTH_KHR = 0x8243;

    public static final int GL_DEBUG_OBJECT_MESA = 0x8759;

    /// Group: `EnableCap`
    public static final int GL_DEBUG_OUTPUT = 0x92E0;

    public static final int GL_DEBUG_OUTPUT_KHR = 0x92E0;

    /// Group: `EnableCap`
    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS = 0x8242;

    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS_ARB = 0x8242;

    public static final int GL_DEBUG_OUTPUT_SYNCHRONOUS_KHR = 0x8242;

    public static final int GL_DEBUG_PRINT_MESA = 0x875A;

    /// Group: `DebugSeverity`
    public static final int GL_DEBUG_SEVERITY_HIGH = 0x9146;

    public static final int GL_DEBUG_SEVERITY_HIGH_AMD = 0x9146;

    public static final int GL_DEBUG_SEVERITY_HIGH_ARB = 0x9146;

    public static final int GL_DEBUG_SEVERITY_HIGH_KHR = 0x9146;

    /// Group: `DebugSeverity`
    public static final int GL_DEBUG_SEVERITY_LOW = 0x9148;

    public static final int GL_DEBUG_SEVERITY_LOW_AMD = 0x9148;

    public static final int GL_DEBUG_SEVERITY_LOW_ARB = 0x9148;

    public static final int GL_DEBUG_SEVERITY_LOW_KHR = 0x9148;

    /// Group: `DebugSeverity`
    public static final int GL_DEBUG_SEVERITY_MEDIUM = 0x9147;

    public static final int GL_DEBUG_SEVERITY_MEDIUM_AMD = 0x9147;

    public static final int GL_DEBUG_SEVERITY_MEDIUM_ARB = 0x9147;

    public static final int GL_DEBUG_SEVERITY_MEDIUM_KHR = 0x9147;

    /// Group: `DebugSeverity`
    public static final int GL_DEBUG_SEVERITY_NOTIFICATION = 0x826B;

    public static final int GL_DEBUG_SEVERITY_NOTIFICATION_KHR = 0x826B;

    /// Group: `DebugSource`
    public static final int GL_DEBUG_SOURCE_API = 0x8246;

    public static final int GL_DEBUG_SOURCE_API_ARB = 0x8246;

    public static final int GL_DEBUG_SOURCE_API_KHR = 0x8246;

    /// Group: `DebugSource`
    public static final int GL_DEBUG_SOURCE_APPLICATION = 0x824A;

    public static final int GL_DEBUG_SOURCE_APPLICATION_ARB = 0x824A;

    public static final int GL_DEBUG_SOURCE_APPLICATION_KHR = 0x824A;

    /// Group: `DebugSource`
    public static final int GL_DEBUG_SOURCE_OTHER = 0x824B;

    public static final int GL_DEBUG_SOURCE_OTHER_ARB = 0x824B;

    public static final int GL_DEBUG_SOURCE_OTHER_KHR = 0x824B;

    /// Group: `DebugSource`
    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER = 0x8248;

    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER_ARB = 0x8248;

    public static final int GL_DEBUG_SOURCE_SHADER_COMPILER_KHR = 0x8248;

    /// Group: `DebugSource`
    public static final int GL_DEBUG_SOURCE_THIRD_PARTY = 0x8249;

    public static final int GL_DEBUG_SOURCE_THIRD_PARTY_ARB = 0x8249;

    public static final int GL_DEBUG_SOURCE_THIRD_PARTY_KHR = 0x8249;

    /// Group: `DebugSource`
    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM = 0x8247;

    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM_ARB = 0x8247;

    public static final int GL_DEBUG_SOURCE_WINDOW_SYSTEM_KHR = 0x8247;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR = 0x824D;

    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR_ARB = 0x824D;

    public static final int GL_DEBUG_TYPE_DEPRECATED_BEHAVIOR_KHR = 0x824D;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_ERROR = 0x824C;

    public static final int GL_DEBUG_TYPE_ERROR_ARB = 0x824C;

    public static final int GL_DEBUG_TYPE_ERROR_KHR = 0x824C;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_MARKER = 0x8268;

    public static final int GL_DEBUG_TYPE_MARKER_KHR = 0x8268;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_OTHER = 0x8251;

    public static final int GL_DEBUG_TYPE_OTHER_ARB = 0x8251;

    public static final int GL_DEBUG_TYPE_OTHER_KHR = 0x8251;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_PERFORMANCE = 0x8250;

    public static final int GL_DEBUG_TYPE_PERFORMANCE_ARB = 0x8250;

    public static final int GL_DEBUG_TYPE_PERFORMANCE_KHR = 0x8250;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_POP_GROUP = 0x826A;

    public static final int GL_DEBUG_TYPE_POP_GROUP_KHR = 0x826A;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_PORTABILITY = 0x824F;

    public static final int GL_DEBUG_TYPE_PORTABILITY_ARB = 0x824F;

    public static final int GL_DEBUG_TYPE_PORTABILITY_KHR = 0x824F;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_PUSH_GROUP = 0x8269;

    public static final int GL_DEBUG_TYPE_PUSH_GROUP_KHR = 0x8269;

    /// Group: `DebugType`
    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR = 0x824E;

    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR_ARB = 0x824E;

    public static final int GL_DEBUG_TYPE_UNDEFINED_BEHAVIOR_KHR = 0x824E;

    /// Group: `TextureEnvMode`
    public static final int GL_DECAL = 0x2101;

    public static final int GL_DECODE_EXT = 0x8A49;

    /// Group: `StencilOp`
    public static final int GL_DECR = 0x1E03;

    /// Group: `StencilOp`
    public static final int GL_DECR_WRAP = 0x8508;

    public static final int GL_DECR_WRAP_EXT = 0x8508;

    public static final int GL_DECR_WRAP_OES = 0x8508;

    /// Group: `MemoryObjectParameterName`
    public static final int GL_DEDICATED_MEMORY_OBJECT_EXT = 0x9581;

    /// Group: `GetPName`
    public static final int GL_DEFORMATIONS_MASK_SGIX = 0x8196;

    /// Groups: `ProgramPropertyARB`, `ShaderParameterName`
    public static final int GL_DELETE_STATUS = 0x8B80;

    public static final int GL_DEPENDENT_AR_TEXTURE_2D_NV = 0x86E9;

    public static final int GL_DEPENDENT_GB_TEXTURE_2D_NV = 0x86EA;

    public static final int GL_DEPENDENT_HILO_TEXTURE_2D_NV = 0x8858;

    public static final int GL_DEPENDENT_RGB_TEXTURE_3D_NV = 0x8859;

    public static final int GL_DEPENDENT_RGB_TEXTURE_CUBE_MAP_NV = 0x885A;

    /// Groups: `Buffer`, `PixelCopyType`, `InvalidateFramebufferAttachment`
    public static final int GL_DEPTH = 0x1801;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH24_STENCIL8 = 0x88F0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH24_STENCIL8_EXT = 0x88F0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH24_STENCIL8_OES = 0x88F0;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH32F_STENCIL8 = 0x8CAD;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH32F_STENCIL8_NV = 0x8DAC;

    /// Groups: `InvalidateFramebufferAttachment`, `FramebufferAttachment`
    public static final int GL_DEPTH_ATTACHMENT = 0x8D00;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_DEPTH_ATTACHMENT_EXT = 0x8D00;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_DEPTH_ATTACHMENT_OES = 0x8D00;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_DEPTH_BIAS = 0x0D1F;

    /// Group: `GetPName`
    public static final int GL_DEPTH_BITS = 0x0D56;

    public static final int GL_DEPTH_BOUNDS_EXT = 0x8891;

    public static final int GL_DEPTH_BOUNDS_TEST_EXT = 0x8890;

    /// Groups: `ClearBufferMask`, `AttribMask`
    public static final int GL_DEPTH_BUFFER_BIT = 0x00000100;

    /// Group: `BufferBitQCOM`
    public static final int GL_DEPTH_BUFFER_BIT0_QCOM = 0x00000100;

    /// Group: `BufferBitQCOM`
    public static final int GL_DEPTH_BUFFER_BIT1_QCOM = 0x00000200;

    /// Group: `BufferBitQCOM`
    public static final int GL_DEPTH_BUFFER_BIT2_QCOM = 0x00000400;

    /// Group: `BufferBitQCOM`
    public static final int GL_DEPTH_BUFFER_BIT3_QCOM = 0x00000800;

    /// Group: `BufferBitQCOM`
    public static final int GL_DEPTH_BUFFER_BIT4_QCOM = 0x00001000;

    /// Group: `BufferBitQCOM`
    public static final int GL_DEPTH_BUFFER_BIT5_QCOM = 0x00002000;

    /// Group: `BufferBitQCOM`
    public static final int GL_DEPTH_BUFFER_BIT6_QCOM = 0x00004000;

    /// Group: `BufferBitQCOM`
    public static final int GL_DEPTH_BUFFER_BIT7_QCOM = 0x00008000;

    public static final int GL_DEPTH_BUFFER_FLOAT_MODE_NV = 0x8DAF;

    /// Group: `EnableCap`
    public static final int GL_DEPTH_CLAMP = 0x864F;

    public static final int GL_DEPTH_CLAMP_EXT = 0x864F;

    public static final int GL_DEPTH_CLAMP_FAR_AMD = 0x901F;

    public static final int GL_DEPTH_CLAMP_NEAR_AMD = 0x901E;

    public static final int GL_DEPTH_CLAMP_NV = 0x864F;

    /// Group: `GetPName`
    public static final int GL_DEPTH_CLEAR_VALUE = 0x0B73;

    /// Groups: `InternalFormat`, `PixelFormat`, `DepthStencilTextureMode`
    public static final int GL_DEPTH_COMPONENT = 0x1902;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT16 = 0x81A5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT16_ARB = 0x81A5;

    public static final int GL_DEPTH_COMPONENT16_NONLINEAR_NV = 0x8E2C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT16_OES = 0x81A5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT16_SGIX = 0x81A5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT24 = 0x81A6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT24_ARB = 0x81A6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT24_OES = 0x81A6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT24_SGIX = 0x81A6;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT32 = 0x81A7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT32F = 0x8CAC;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT32F_NV = 0x8DAB;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT32_ARB = 0x81A7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT32_OES = 0x81A7;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_DEPTH_COMPONENT32_SGIX = 0x81A7;

    public static final int GL_DEPTH_COMPONENTS = 0x8284;

    /// Group: `PixelCopyType`
    public static final int GL_DEPTH_EXT = 0x1801;

    /// Group: `GetPName`
    public static final int GL_DEPTH_FUNC = 0x0B74;

    public static final int GL_DEPTH_PASS_INSTRUMENT_COUNTERS_SGIX = 0x8311;

    public static final int GL_DEPTH_PASS_INSTRUMENT_MAX_SGIX = 0x8312;

    public static final int GL_DEPTH_PASS_INSTRUMENT_SGIX = 0x8310;

    /// Group: `GetPName`
    public static final int GL_DEPTH_RANGE = 0x0B70;

    /// Group: `InternalFormatPName`
    public static final int GL_DEPTH_RENDERABLE = 0x8287;

    public static final int GL_DEPTH_SAMPLES_NV = 0x932D;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_DEPTH_SCALE = 0x0D1E;

    /// Groups: `InternalFormat`, `PixelFormat`
    public static final int GL_DEPTH_STENCIL = 0x84F9;

    /// Groups: `FramebufferAttachment`, `InvalidateFramebufferAttachment`
    public static final int GL_DEPTH_STENCIL_ATTACHMENT = 0x821A;

    /// Group: `InternalFormat`
    public static final int GL_DEPTH_STENCIL_EXT = 0x84F9;

    /// Group: `InternalFormat`
    public static final int GL_DEPTH_STENCIL_MESA = 0x8750;

    /// Group: `InternalFormat`
    public static final int GL_DEPTH_STENCIL_NV = 0x84F9;

    /// Group: `InternalFormat`
    public static final int GL_DEPTH_STENCIL_OES = 0x84F9;

    /// Group: `TextureParameterName`
    public static final int GL_DEPTH_STENCIL_TEXTURE_MODE = 0x90EA;

    public static final int GL_DEPTH_STENCIL_TO_BGRA_NV = 0x886F;

    public static final int GL_DEPTH_STENCIL_TO_RGBA_NV = 0x886E;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_DEPTH_TEST = 0x0B71;

    public static final int GL_DEPTH_TEXTURE_MODE = 0x884B;

    public static final int GL_DEPTH_TEXTURE_MODE_ARB = 0x884B;

    /// Group: `GetPName`
    public static final int GL_DEPTH_WRITEMASK = 0x0B72;

    public static final int GL_DETACHED_BUFFERS_NV = 0x95AB;

    public static final int GL_DETACHED_MEMORY_INCARNATION_NV = 0x95A9;

    public static final int GL_DETACHED_TEXTURES_NV = 0x95AA;

    /// Group: `GetPName`
    public static final int GL_DETAIL_TEXTURE_2D_BINDING_SGIS = 0x8096;

    /// Group: `TextureTarget`
    public static final int GL_DETAIL_TEXTURE_2D_SGIS = 0x8095;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_DETAIL_TEXTURE_FUNC_POINTS_SGIS = 0x809C;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_DETAIL_TEXTURE_LEVEL_SGIS = 0x809A;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_DETAIL_TEXTURE_MODE_SGIS = 0x809B;

    /// Group: `GetPName`
    public static final int GL_DEVICE_LUID_EXT = 0x9599;

    /// Group: `GetPName`
    public static final int GL_DEVICE_NODE_MASK_EXT = 0x959A;

    /// Group: `GetPName`
    public static final int GL_DEVICE_UUID_EXT = 0x9597;

    public static final int GL_DIFFERENCE = 0x929E;

    public static final int GL_DIFFERENCE_KHR = 0x929E;

    public static final int GL_DIFFERENCE_NV = 0x929E;

    /// Groups: `MaterialParameter`, `FragmentLightParameterSGIX`,
    ///   `ColorMaterialParameter`
    public static final int GL_DIFFUSE = 0x1201;

    /// Group: `PreserveModeATI`
    public static final int GL_DISCARD_ATI = 0x8763;

    /// Group: `CombinerRegisterNV`
    public static final int GL_DISCARD_NV = 0x8530;

    public static final int GL_DISCRETE_AMD = 0x9006;

    public static final int GL_DISJOINT_NV = 0x9283;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_DISPATCH_INDIRECT_BUFFER = 0x90EE;

    /// Group: `GetPName`
    public static final int GL_DISPATCH_INDIRECT_BUFFER_BINDING = 0x90EF;

    public static final int GL_DISPLAY_LIST = 0x82E7;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_DISTANCE_ATTENUATION_EXT = 0x8129;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_DISTANCE_ATTENUATION_SGIS = 0x8129;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_DITHER = 0x0BD0;

    public static final int GL_DMP_PROGRAM_BINARY_DMP = 0x9253;

    /// Groups: `MapQuery`, `GetMapQuery`
    public static final int GL_DOMAIN = 0x0A02;

    /// Groups: `DebugSeverity`, `HintMode`, `DebugSource`, `DebugType`
    public static final int GL_DONT_CARE = 0x1100;

    /// Group: `FragmentOp3ATI`
    public static final int GL_DOT2_ADD_ATI = 0x896C;

    /// Group: `FragmentOp2ATI`
    public static final int GL_DOT3_ATI = 0x8966;

    public static final int GL_DOT3_RGB = 0x86AE;

    public static final int GL_DOT3_RGBA = 0x86AF;

    public static final int GL_DOT3_RGBA_ARB = 0x86AF;

    public static final int GL_DOT3_RGBA_EXT = 0x8741;

    public static final int GL_DOT3_RGBA_IMG = 0x86AF;

    public static final int GL_DOT3_RGB_ARB = 0x86AE;

    public static final int GL_DOT3_RGB_EXT = 0x8740;

    /// Group: `FragmentOp2ATI`
    public static final int GL_DOT4_ATI = 0x8967;

    public static final int GL_DOT_PRODUCT_AFFINE_DEPTH_REPLACE_NV = 0x885D;

    public static final int GL_DOT_PRODUCT_CONST_EYE_REFLECT_CUBE_MAP_NV = 0x86F3;

    public static final int GL_DOT_PRODUCT_DEPTH_REPLACE_NV = 0x86ED;

    public static final int GL_DOT_PRODUCT_DIFFUSE_CUBE_MAP_NV = 0x86F1;

    public static final int GL_DOT_PRODUCT_NV = 0x86EC;

    public static final int GL_DOT_PRODUCT_PASS_THROUGH_NV = 0x885B;

    public static final int GL_DOT_PRODUCT_REFLECT_CUBE_MAP_NV = 0x86F2;

    public static final int GL_DOT_PRODUCT_TEXTURE_1D_NV = 0x885C;

    public static final int GL_DOT_PRODUCT_TEXTURE_2D_NV = 0x86EE;

    public static final int GL_DOT_PRODUCT_TEXTURE_3D_NV = 0x86EF;

    public static final int GL_DOT_PRODUCT_TEXTURE_CUBE_MAP_NV = 0x86F0;

    public static final int GL_DOT_PRODUCT_TEXTURE_RECTANGLE_NV = 0x864E;

    /// Groups: `VertexAttribLType`, `MapTypeNV`, `SecondaryColorPointerTypeIBM`,
    ///   `WeightPointerTypeARB`, `TangentPointerTypeEXT`, `BinormalPointerTypeEXT`,
    ///   `FogCoordinatePointerType`, `FogPointerTypeEXT`, `FogPointerTypeIBM`,
    ///   `IndexPointerType`, `NormalPointerType`, `TexCoordPointerType`,
    ///   `VertexPointerType`, `VertexAttribType`, `AttributeType`, `UniformType`,
    ///   `VertexAttribPointerType`
    public static final int GL_DOUBLE = 0x140A;

    /// Groups: `GetFramebufferParameter`, `GetPName`
    public static final int GL_DOUBLEBUFFER = 0x0C32;

    /// Groups: `BinormalPointerTypeEXT`, `TangentPointerTypeEXT`
    public static final int GL_DOUBLE_EXT = 0x140A;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_DOUBLE_MAT2 = 0x8F46;

    public static final int GL_DOUBLE_MAT2_EXT = 0x8F46;

    /// Groups: `UniformType`, `AttributeType`

    public static final int GL_DOUBLE_MAT2x3 = 0x8F49;


    public static final int GL_DOUBLE_MAT2x3_EXT = 0x8F49;

    /// Groups: `UniformType`, `AttributeType`

    public static final int GL_DOUBLE_MAT2x4 = 0x8F4A;


    public static final int GL_DOUBLE_MAT2x4_EXT = 0x8F4A;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_DOUBLE_MAT3 = 0x8F47;

    public static final int GL_DOUBLE_MAT3_EXT = 0x8F47;

    /// Groups: `UniformType`, `AttributeType`

    public static final int GL_DOUBLE_MAT3x2 = 0x8F4B;


    public static final int GL_DOUBLE_MAT3x2_EXT = 0x8F4B;

    /// Groups: `UniformType`, `AttributeType`

    public static final int GL_DOUBLE_MAT3x4 = 0x8F4C;


    public static final int GL_DOUBLE_MAT3x4_EXT = 0x8F4C;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_DOUBLE_MAT4 = 0x8F48;

    public static final int GL_DOUBLE_MAT4_EXT = 0x8F48;

    /// Groups: `UniformType`, `AttributeType`

    public static final int GL_DOUBLE_MAT4x2 = 0x8F4D;


    public static final int GL_DOUBLE_MAT4x2_EXT = 0x8F4D;

    /// Groups: `UniformType`, `AttributeType`

    public static final int GL_DOUBLE_MAT4x3 = 0x8F4E;


    public static final int GL_DOUBLE_MAT4x3_EXT = 0x8F4E;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_DOUBLE_VEC2 = 0x8FFC;

    public static final int GL_DOUBLE_VEC2_EXT = 0x8FFC;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_DOUBLE_VEC3 = 0x8FFD;

    public static final int GL_DOUBLE_VEC3_EXT = 0x8FFD;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_DOUBLE_VEC4 = 0x8FFE;

    public static final int GL_DOUBLE_VEC4_EXT = 0x8FFE;

    public static final int GL_DOWNSAMPLE_SCALES_IMG = 0x913E;

    /// Group: `CommandOpcodesNV`
    public static final int GL_DRAW_ARRAYS_COMMAND_NV = 0x0003;

    /// Group: `CommandOpcodesNV`
    public static final int GL_DRAW_ARRAYS_INSTANCED_COMMAND_NV = 0x0007;

    /// Group: `CommandOpcodesNV`
    public static final int GL_DRAW_ARRAYS_STRIP_COMMAND_NV = 0x0005;

    /// Group: `GetPName`
    public static final int GL_DRAW_BUFFER = 0x0C01;

    public static final int GL_DRAW_BUFFER0 = 0x8825;

    public static final int GL_DRAW_BUFFER0_ARB = 0x8825;

    public static final int GL_DRAW_BUFFER0_ATI = 0x8825;

    public static final int GL_DRAW_BUFFER0_EXT = 0x8825;

    public static final int GL_DRAW_BUFFER0_NV = 0x8825;

    public static final int GL_DRAW_BUFFER1 = 0x8826;

    public static final int GL_DRAW_BUFFER10 = 0x882F;

    public static final int GL_DRAW_BUFFER10_ARB = 0x882F;

    public static final int GL_DRAW_BUFFER10_ATI = 0x882F;

    public static final int GL_DRAW_BUFFER10_EXT = 0x882F;

    public static final int GL_DRAW_BUFFER10_NV = 0x882F;

    public static final int GL_DRAW_BUFFER11 = 0x8830;

    public static final int GL_DRAW_BUFFER11_ARB = 0x8830;

    public static final int GL_DRAW_BUFFER11_ATI = 0x8830;

    public static final int GL_DRAW_BUFFER11_EXT = 0x8830;

    public static final int GL_DRAW_BUFFER11_NV = 0x8830;

    public static final int GL_DRAW_BUFFER12 = 0x8831;

    public static final int GL_DRAW_BUFFER12_ARB = 0x8831;

    public static final int GL_DRAW_BUFFER12_ATI = 0x8831;

    public static final int GL_DRAW_BUFFER12_EXT = 0x8831;

    public static final int GL_DRAW_BUFFER12_NV = 0x8831;

    public static final int GL_DRAW_BUFFER13 = 0x8832;

    public static final int GL_DRAW_BUFFER13_ARB = 0x8832;

    public static final int GL_DRAW_BUFFER13_ATI = 0x8832;

    public static final int GL_DRAW_BUFFER13_EXT = 0x8832;

    public static final int GL_DRAW_BUFFER13_NV = 0x8832;

    public static final int GL_DRAW_BUFFER14 = 0x8833;

    public static final int GL_DRAW_BUFFER14_ARB = 0x8833;

    public static final int GL_DRAW_BUFFER14_ATI = 0x8833;

    public static final int GL_DRAW_BUFFER14_EXT = 0x8833;

    public static final int GL_DRAW_BUFFER14_NV = 0x8833;

    public static final int GL_DRAW_BUFFER15 = 0x8834;

    public static final int GL_DRAW_BUFFER15_ARB = 0x8834;

    public static final int GL_DRAW_BUFFER15_ATI = 0x8834;

    public static final int GL_DRAW_BUFFER15_EXT = 0x8834;

    public static final int GL_DRAW_BUFFER15_NV = 0x8834;

    public static final int GL_DRAW_BUFFER1_ARB = 0x8826;

    public static final int GL_DRAW_BUFFER1_ATI = 0x8826;

    public static final int GL_DRAW_BUFFER1_EXT = 0x8826;

    public static final int GL_DRAW_BUFFER1_NV = 0x8826;

    public static final int GL_DRAW_BUFFER2 = 0x8827;

    public static final int GL_DRAW_BUFFER2_ARB = 0x8827;

    public static final int GL_DRAW_BUFFER2_ATI = 0x8827;

    public static final int GL_DRAW_BUFFER2_EXT = 0x8827;

    public static final int GL_DRAW_BUFFER2_NV = 0x8827;

    public static final int GL_DRAW_BUFFER3 = 0x8828;

    public static final int GL_DRAW_BUFFER3_ARB = 0x8828;

    public static final int GL_DRAW_BUFFER3_ATI = 0x8828;

    public static final int GL_DRAW_BUFFER3_EXT = 0x8828;

    public static final int GL_DRAW_BUFFER3_NV = 0x8828;

    public static final int GL_DRAW_BUFFER4 = 0x8829;

    public static final int GL_DRAW_BUFFER4_ARB = 0x8829;

    public static final int GL_DRAW_BUFFER4_ATI = 0x8829;

    public static final int GL_DRAW_BUFFER4_EXT = 0x8829;

    public static final int GL_DRAW_BUFFER4_NV = 0x8829;

    public static final int GL_DRAW_BUFFER5 = 0x882A;

    public static final int GL_DRAW_BUFFER5_ARB = 0x882A;

    public static final int GL_DRAW_BUFFER5_ATI = 0x882A;

    public static final int GL_DRAW_BUFFER5_EXT = 0x882A;

    public static final int GL_DRAW_BUFFER5_NV = 0x882A;

    public static final int GL_DRAW_BUFFER6 = 0x882B;

    public static final int GL_DRAW_BUFFER6_ARB = 0x882B;

    public static final int GL_DRAW_BUFFER6_ATI = 0x882B;

    public static final int GL_DRAW_BUFFER6_EXT = 0x882B;

    public static final int GL_DRAW_BUFFER6_NV = 0x882B;

    public static final int GL_DRAW_BUFFER7 = 0x882C;

    public static final int GL_DRAW_BUFFER7_ARB = 0x882C;

    public static final int GL_DRAW_BUFFER7_ATI = 0x882C;

    public static final int GL_DRAW_BUFFER7_EXT = 0x882C;

    public static final int GL_DRAW_BUFFER7_NV = 0x882C;

    public static final int GL_DRAW_BUFFER8 = 0x882D;

    public static final int GL_DRAW_BUFFER8_ARB = 0x882D;

    public static final int GL_DRAW_BUFFER8_ATI = 0x882D;

    public static final int GL_DRAW_BUFFER8_EXT = 0x882D;

    public static final int GL_DRAW_BUFFER8_NV = 0x882D;

    public static final int GL_DRAW_BUFFER9 = 0x882E;

    public static final int GL_DRAW_BUFFER9_ARB = 0x882E;

    public static final int GL_DRAW_BUFFER9_ATI = 0x882E;

    public static final int GL_DRAW_BUFFER9_EXT = 0x882E;

    public static final int GL_DRAW_BUFFER9_NV = 0x882E;

    /// Group: `GetPName`
    public static final int GL_DRAW_BUFFER_EXT = 0x0C01;

    /// Group: `CommandOpcodesNV`
    public static final int GL_DRAW_ELEMENTS_COMMAND_NV = 0x0002;

    /// Group: `CommandOpcodesNV`
    public static final int GL_DRAW_ELEMENTS_INSTANCED_COMMAND_NV = 0x0006;

    /// Group: `CommandOpcodesNV`
    public static final int GL_DRAW_ELEMENTS_STRIP_COMMAND_NV = 0x0004;

    /// Group: `FramebufferTarget`
    public static final int GL_DRAW_FRAMEBUFFER = 0x8CA9;

    public static final int GL_DRAW_FRAMEBUFFER_ANGLE = 0x8CA9;

    public static final int GL_DRAW_FRAMEBUFFER_APPLE = 0x8CA9;

    /// Group: `GetPName`
    public static final int GL_DRAW_FRAMEBUFFER_BINDING = 0x8CA6;

    public static final int GL_DRAW_FRAMEBUFFER_BINDING_ANGLE = 0x8CA6;

    public static final int GL_DRAW_FRAMEBUFFER_BINDING_APPLE = 0x8CA6;

    public static final int GL_DRAW_FRAMEBUFFER_BINDING_EXT = 0x8CA6;

    public static final int GL_DRAW_FRAMEBUFFER_BINDING_NV = 0x8CA6;

    public static final int GL_DRAW_FRAMEBUFFER_EXT = 0x8CA9;

    public static final int GL_DRAW_FRAMEBUFFER_NV = 0x8CA9;

    public static final int GL_DRAW_INDIRECT_ADDRESS_NV = 0x8F41;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_DRAW_INDIRECT_BUFFER = 0x8F3F;

    public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 0x8F43;

    public static final int GL_DRAW_INDIRECT_LENGTH_NV = 0x8F42;

    public static final int GL_DRAW_INDIRECT_UNIFIED_NV = 0x8F40;

    /// Group: `ObjectTypeAPPLE`
    public static final int GL_DRAW_PIXELS_APPLE = 0x8A0A;

    /// Group: `FeedBackToken`
    public static final int GL_DRAW_PIXEL_TOKEN = 0x0705;

    /// Group: `GetPName`
    public static final int GL_DRIVER_UUID_EXT = 0x9598;

    public static final int GL_DSDT8_MAG8_INTENSITY8_NV = 0x870B;

    public static final int GL_DSDT8_MAG8_NV = 0x870A;

    public static final int GL_DSDT8_NV = 0x8709;

    public static final int GL_DSDT_MAG_INTENSITY_NV = 0x86DC;

    public static final int GL_DSDT_MAG_NV = 0x86F6;

    public static final int GL_DSDT_MAG_VIB_NV = 0x86F7;

    public static final int GL_DSDT_NV = 0x86F5;

    /// Group: `BlendingFactor`
    public static final int GL_DST_ALPHA = 0x0304;

    public static final int GL_DST_ATOP_NV = 0x928F;

    /// Group: `BlendingFactor`
    public static final int GL_DST_COLOR = 0x0306;

    public static final int GL_DST_IN_NV = 0x928B;

    public static final int GL_DST_NV = 0x9287;

    public static final int GL_DST_OUT_NV = 0x928D;

    public static final int GL_DST_OVER_NV = 0x9289;

    public static final int GL_DS_BIAS_NV = 0x8716;

    public static final int GL_DS_SCALE_NV = 0x8710;

    public static final int GL_DT_BIAS_NV = 0x8717;

    public static final int GL_DT_SCALE_NV = 0x8711;

    public static final int GL_DU8DV8_ATI = 0x877A;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_ALPHA12_SGIS = 0x8112;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_ALPHA16_SGIS = 0x8113;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_ALPHA4_SGIS = 0x8110;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_ALPHA8_SGIS = 0x8111;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_INTENSITY12_SGIS = 0x811A;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_INTENSITY16_SGIS = 0x811B;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_INTENSITY4_SGIS = 0x8118;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_INTENSITY8_SGIS = 0x8119;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_LUMINANCE12_SGIS = 0x8116;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_LUMINANCE16_SGIS = 0x8117;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_LUMINANCE4_SGIS = 0x8114;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_LUMINANCE8_SGIS = 0x8115;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_LUMINANCE_ALPHA4_SGIS = 0x811C;

    /// Group: `InternalFormat`
    public static final int GL_DUAL_LUMINANCE_ALPHA8_SGIS = 0x811D;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_DUAL_TEXTURE_SELECT_SGIS = 0x8124;

    public static final int GL_DUDV_ATI = 0x8779;

    /// Group: `PathCoordType`
    public static final int GL_DUP_FIRST_CUBIC_CURVE_TO_NV = 0xF2;

    /// Group: `PathCoordType`
    public static final int GL_DUP_LAST_CUBIC_CURVE_TO_NV = 0xF4;

    /// Group: `ArrayObjectUsageATI`
    public static final int GL_DYNAMIC_ATI = 0x8761;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_DYNAMIC_COPY = 0x88EA;

    public static final int GL_DYNAMIC_COPY_ARB = 0x88EA;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_DYNAMIC_DRAW = 0x88E8;

    public static final int GL_DYNAMIC_DRAW_ARB = 0x88E8;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_DYNAMIC_READ = 0x88E9;

    public static final int GL_DYNAMIC_READ_ARB = 0x88E9;

    /// Group: `BufferStorageMask`
    public static final int GL_DYNAMIC_STORAGE_BIT = 0x0100;

    /// Group: `BufferStorageMask`
    public static final int GL_DYNAMIC_STORAGE_BIT_EXT = 0x0100;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_EDGEFLAG_BIT_PGI = 0x00040000;

    /// Group: `GetPName`
    public static final int GL_EDGE_FLAG = 0x0B43;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_EDGE_FLAG_ARRAY = 0x8079;

    public static final int GL_EDGE_FLAG_ARRAY_ADDRESS_NV = 0x8F26;

    public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING = 0x889B;

    public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING_ARB = 0x889B;

    /// Group: `GetPName`
    public static final int GL_EDGE_FLAG_ARRAY_COUNT_EXT = 0x808D;

    public static final int GL_EDGE_FLAG_ARRAY_EXT = 0x8079;

    public static final int GL_EDGE_FLAG_ARRAY_LENGTH_NV = 0x8F30;

    public static final int GL_EDGE_FLAG_ARRAY_LIST_IBM = 103075;

    public static final int GL_EDGE_FLAG_ARRAY_LIST_STRIDE_IBM = 103085;

    /// Group: `GetPointervPName`
    public static final int GL_EDGE_FLAG_ARRAY_POINTER = 0x8093;

    /// Group: `GetPointervPName`
    public static final int GL_EDGE_FLAG_ARRAY_POINTER_EXT = 0x8093;

    /// Group: `GetPName`
    public static final int GL_EDGE_FLAG_ARRAY_STRIDE = 0x808C;

    public static final int GL_EDGE_FLAG_ARRAY_STRIDE_EXT = 0x808C;

    public static final int GL_EFFECTIVE_RASTER_SAMPLES_EXT = 0x932C;

    /// Group: `FragmentShaderDestModMaskATI`
    public static final int GL_EIGHTH_BIT_ATI = 0x00000020;

    /// Group: `CommandOpcodesNV`
    public static final int GL_ELEMENT_ADDRESS_COMMAND_NV = 0x0008;

    public static final int GL_ELEMENT_ARRAY_ADDRESS_NV = 0x8F29;

    public static final int GL_ELEMENT_ARRAY_APPLE = 0x8A0C;

    public static final int GL_ELEMENT_ARRAY_ATI = 0x8768;

    /// Group: `MemoryBarrierMask`
    public static final int GL_ELEMENT_ARRAY_BARRIER_BIT = 0x00000002;

    /// Group: `MemoryBarrierMask`
    public static final int GL_ELEMENT_ARRAY_BARRIER_BIT_EXT = 0x00000002;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_ELEMENT_ARRAY_BUFFER = 0x8893;

    public static final int GL_ELEMENT_ARRAY_BUFFER_ARB = 0x8893;

    /// Group: `GetPName`
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 0x8895;

    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING_ARB = 0x8895;

    public static final int GL_ELEMENT_ARRAY_LENGTH_NV = 0x8F33;

    public static final int GL_ELEMENT_ARRAY_POINTER_APPLE = 0x8A0E;

    public static final int GL_ELEMENT_ARRAY_POINTER_ATI = 0x876A;

    public static final int GL_ELEMENT_ARRAY_TYPE_APPLE = 0x8A0D;

    public static final int GL_ELEMENT_ARRAY_TYPE_ATI = 0x8769;

    public static final int GL_ELEMENT_ARRAY_UNIFIED_NV = 0x8F1F;

    public static final int GL_EMBOSS_CONSTANT_NV = 0x855E;

    public static final int GL_EMBOSS_LIGHT_NV = 0x855D;

    public static final int GL_EMBOSS_MAP_NV = 0x855F;

    /// Groups: `MaterialParameter`, `ColorMaterialParameter`
    public static final int GL_EMISSION = 0x1600;

    /// Group: `AttribMask`
    public static final int GL_ENABLE_BIT = 0x00002000;

    /// Groups: `StencilFunction`, `IndexFunctionEXT`, `AlphaFunction`,
    ///   `DepthFunction`
    public static final int GL_EQUAL = 0x0202;

    /// Group: `LogicOp`
    public static final int GL_EQUIV = 0x1509;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_ETC1_RGB8_OES = 0x8D64;

    public static final int GL_ETC1_SRGB8_NV = 0x88EE;

    /// Group: `EvalTargetNV`
    public static final int GL_EVAL_2D_NV = 0x86C0;

    /// Group: `AttribMask`
    public static final int GL_EVAL_BIT = 0x00010000;

    public static final int GL_EVAL_FRACTIONAL_TESSELLATION_NV = 0x86C5;

    /// Group: `EvalTargetNV`
    public static final int GL_EVAL_TRIANGULAR_2D_NV = 0x86C1;

    public static final int GL_EVAL_VERTEX_ATTRIB0_NV = 0x86C6;

    public static final int GL_EVAL_VERTEX_ATTRIB10_NV = 0x86D0;

    public static final int GL_EVAL_VERTEX_ATTRIB11_NV = 0x86D1;

    public static final int GL_EVAL_VERTEX_ATTRIB12_NV = 0x86D2;

    public static final int GL_EVAL_VERTEX_ATTRIB13_NV = 0x86D3;

    public static final int GL_EVAL_VERTEX_ATTRIB14_NV = 0x86D4;

    public static final int GL_EVAL_VERTEX_ATTRIB15_NV = 0x86D5;

    public static final int GL_EVAL_VERTEX_ATTRIB1_NV = 0x86C7;

    public static final int GL_EVAL_VERTEX_ATTRIB2_NV = 0x86C8;

    public static final int GL_EVAL_VERTEX_ATTRIB3_NV = 0x86C9;

    public static final int GL_EVAL_VERTEX_ATTRIB4_NV = 0x86CA;

    public static final int GL_EVAL_VERTEX_ATTRIB5_NV = 0x86CB;

    public static final int GL_EVAL_VERTEX_ATTRIB6_NV = 0x86CC;

    public static final int GL_EVAL_VERTEX_ATTRIB7_NV = 0x86CD;

    public static final int GL_EVAL_VERTEX_ATTRIB8_NV = 0x86CE;

    public static final int GL_EVAL_VERTEX_ATTRIB9_NV = 0x86CF;

    public static final int GL_EXCLUSION = 0x92A0;

    public static final int GL_EXCLUSION_KHR = 0x92A0;

    public static final int GL_EXCLUSION_NV = 0x92A0;

    public static final int GL_EXCLUSIVE_EXT = 0x8F11;

    /// Group: `FogMode`
    public static final int GL_EXP = 0x0800;

    /// Group: `FogMode`
    public static final int GL_EXP2 = 0x0801;

    /// Group: `CombinerMappingNV`
    public static final int GL_EXPAND_NEGATE_NV = 0x8539;

    /// Group: `CombinerMappingNV`
    public static final int GL_EXPAND_NORMAL_NV = 0x8538;

    /// Group: `StringName`
    public static final int GL_EXTENSIONS = 0x1F03;

    /// Group: `BufferStorageMask`
    public static final int GL_EXTERNAL_STORAGE_BIT_NVX = 0x2000;

    public static final int GL_EXTERNAL_VIRTUAL_MEMORY_BUFFER_AMD = 0x9160;

    /// Group: `TextureGenMode`
    public static final int GL_EYE_DISTANCE_TO_LINE_SGIS = 0x81F2;

    /// Group: `TextureGenMode`
    public static final int GL_EYE_DISTANCE_TO_POINT_SGIS = 0x81F0;

    /// Groups: `PathGenMode`, `TextureGenMode`
    public static final int GL_EYE_LINEAR = 0x2400;

    public static final int GL_EYE_LINEAR_NV = 0x2400;

    /// Group: `TextureGenParameter`
    public static final int GL_EYE_LINE_SGIS = 0x81F6;

    /// Group: `TextureGenParameter`
    public static final int GL_EYE_PLANE = 0x2502;

    public static final int GL_EYE_PLANE_ABSOLUTE_NV = 0x855C;

    /// Group: `TextureGenParameter`
    public static final int GL_EYE_POINT_SGIS = 0x81F4;

    public static final int GL_EYE_RADIAL_NV = 0x855B;

    public static final int GL_E_TIMES_F_NV = 0x8531;

    public static final int GL_FACTOR_ALPHA_MODULATE_IMG = 0x8C07;

    public static final int GL_FACTOR_MAX_AMD = 0x901D;

    public static final int GL_FACTOR_MIN_AMD = 0x901C;

    public static final int GL_FAILURE_NV = 0x9030;

    /// Groups: `SpecialNumbers`, `Boolean`, `VertexShaderWriteMaskEXT`,
    ///   `ClampColorModeARB`
    public static final int GL_FALSE = 0;

    /// Group: `HintMode`
    public static final int GL_FASTEST = 0x1101;

    /// Group: `RenderingMode`
    public static final int GL_FEEDBACK = 0x1C01;

    /// Group: `GetPointervPName`
    public static final int GL_FEEDBACK_BUFFER_POINTER = 0x0DF0;

    /// Group: `GetPName`
    public static final int GL_FEEDBACK_BUFFER_SIZE = 0x0DF1;

    /// Group: `GetPName`
    public static final int GL_FEEDBACK_BUFFER_TYPE = 0x0DF2;

    /// Group: `ObjectTypeAPPLE`
    public static final int GL_FENCE_APPLE = 0x8A0B;

    /// Group: `FenceParameterNameNV`
    public static final int GL_FENCE_CONDITION_NV = 0x84F4;

    /// Group: `FenceParameterNameNV`
    public static final int GL_FENCE_STATUS_NV = 0x84F3;

    /// Groups: `EnableCap`, `GetPName`
    public static final int GL_FETCH_PER_SAMPLE_ARM = 0x8F65;

    public static final int GL_FIELDS_NV = 0x8E27;

    public static final int GL_FIELD_LOWER_NV = 0x9023;

    public static final int GL_FIELD_UPPER_NV = 0x9022;

    /// Group: `PathFontTarget`
    public static final int GL_FILE_NAME_NV = 0x9074;

    /// Groups: `PolygonMode`, `MeshMode2`
    public static final int GL_FILL = 0x1B02;

    /// Group: `EvalMapsModeNV`
    public static final int GL_FILL_NV = 0x1B02;

    public static final int GL_FILL_RECTANGLE_NV = 0x933C;

    /// Group: `InternalFormatPName`
    public static final int GL_FILTER = 0x829A;

    /// Groups: `TextureFilterSGIS`, `TextureMinFilter`, `TextureMagFilter`
    public static final int GL_FILTER4_SGIS = 0x8146;

    /// Group: `PathListMode`
    public static final int GL_FIRST_TO_REST_NV = 0x90AF;

    /// Group: `VertexProvokingMode`
    public static final int GL_FIRST_VERTEX_CONVENTION = 0x8E4D;

    public static final int GL_FIRST_VERTEX_CONVENTION_EXT = 0x8E4D;

    public static final int GL_FIRST_VERTEX_CONVENTION_OES = 0x8E4D;

    /// Groups: `VertexAttribPointerType`, `VertexAttribType`
    public static final int GL_FIXED = 0x140C;

    public static final int GL_FIXED_OES = 0x140C;

    /// Group: `ClampColorModeARB`
    public static final int GL_FIXED_ONLY = 0x891D;

    /// Group: `ClampColorModeARB`
    public static final int GL_FIXED_ONLY_ARB = 0x891D;

    /// Group: `ShadingModel`
    public static final int GL_FLAT = 0x1D00;

    /// Groups: `MapTypeNV`, `SecondaryColorPointerTypeIBM`,
    ///   `WeightPointerTypeARB`, `VertexWeightPointerTypeEXT`,
    ///   `TangentPointerTypeEXT`, `BinormalPointerTypeEXT`,
    ///   `FogCoordinatePointerType`, `FogPointerTypeEXT`, `FogPointerTypeIBM`,
    ///   `IndexPointerType`, `ListNameType`, `NormalPointerType`, `PixelType`,
    ///   `TexCoordPointerType`, `VertexPointerType`, `VertexAttribType`,
    ///   `AttributeType`, `UniformType`, `VertexAttribPointerType`
    public static final int GL_FLOAT = 0x1406;

    public static final int GL_FLOAT16_MAT2_AMD = 0x91C5;


    public static final int GL_FLOAT16_MAT2x3_AMD = 0x91C8;


    public static final int GL_FLOAT16_MAT2x4_AMD = 0x91C9;

    public static final int GL_FLOAT16_MAT3_AMD = 0x91C6;


    public static final int GL_FLOAT16_MAT3x2_AMD = 0x91CA;


    public static final int GL_FLOAT16_MAT3x4_AMD = 0x91CB;

    public static final int GL_FLOAT16_MAT4_AMD = 0x91C7;


    public static final int GL_FLOAT16_MAT4x2_AMD = 0x91CC;


    public static final int GL_FLOAT16_MAT4x3_AMD = 0x91CD;

    public static final int GL_FLOAT16_NV = 0x8FF8;

    public static final int GL_FLOAT16_VEC2_NV = 0x8FF9;

    public static final int GL_FLOAT16_VEC3_NV = 0x8FFA;

    public static final int GL_FLOAT16_VEC4_NV = 0x8FFB;

    /// Group: `PixelType`
    public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV = 0x8DAD;

    /// Group: `PixelType`
    public static final int GL_FLOAT_32_UNSIGNED_INT_24_8_REV_NV = 0x8DAD;

    public static final int GL_FLOAT_CLEAR_COLOR_VALUE_NV = 0x888D;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_FLOAT_MAT2 = 0x8B5A;

    /// Group: `AttributeType`
    public static final int GL_FLOAT_MAT2_ARB = 0x8B5A;

    /// Groups: `AttributeType`, `UniformType`

    public static final int GL_FLOAT_MAT2x3 = 0x8B65;

    /// Group: `AttributeType`

    public static final int GL_FLOAT_MAT2x3_NV = 0x8B65;

    /// Groups: `AttributeType`, `UniformType`

    public static final int GL_FLOAT_MAT2x4 = 0x8B66;

    /// Group: `AttributeType`

    public static final int GL_FLOAT_MAT2x4_NV = 0x8B66;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_FLOAT_MAT3 = 0x8B5B;

    /// Group: `AttributeType`
    public static final int GL_FLOAT_MAT3_ARB = 0x8B5B;

    /// Groups: `AttributeType`, `UniformType`

    public static final int GL_FLOAT_MAT3x2 = 0x8B67;

    /// Group: `AttributeType`

    public static final int GL_FLOAT_MAT3x2_NV = 0x8B67;

    /// Groups: `AttributeType`, `UniformType`

    public static final int GL_FLOAT_MAT3x4 = 0x8B68;

    /// Group: `AttributeType`

    public static final int GL_FLOAT_MAT3x4_NV = 0x8B68;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_FLOAT_MAT4 = 0x8B5C;

    /// Group: `AttributeType`
    public static final int GL_FLOAT_MAT4_ARB = 0x8B5C;

    /// Groups: `AttributeType`, `UniformType`

    public static final int GL_FLOAT_MAT4x2 = 0x8B69;

    /// Group: `AttributeType`

    public static final int GL_FLOAT_MAT4x2_NV = 0x8B69;

    /// Groups: `AttributeType`, `UniformType`

    public static final int GL_FLOAT_MAT4x3 = 0x8B6A;

    /// Group: `AttributeType`

    public static final int GL_FLOAT_MAT4x3_NV = 0x8B6A;

    public static final int GL_FLOAT_R16_NV = 0x8884;

    public static final int GL_FLOAT_R32_NV = 0x8885;

    public static final int GL_FLOAT_RG16_NV = 0x8886;

    public static final int GL_FLOAT_RG32_NV = 0x8887;

    public static final int GL_FLOAT_RGB16_NV = 0x8888;

    public static final int GL_FLOAT_RGB32_NV = 0x8889;

    public static final int GL_FLOAT_RGBA16_NV = 0x888A;

    public static final int GL_FLOAT_RGBA32_NV = 0x888B;

    public static final int GL_FLOAT_RGBA_MODE_NV = 0x888E;

    public static final int GL_FLOAT_RGBA_NV = 0x8883;

    public static final int GL_FLOAT_RGB_NV = 0x8882;

    public static final int GL_FLOAT_RG_NV = 0x8881;

    public static final int GL_FLOAT_R_NV = 0x8880;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_FLOAT_VEC2 = 0x8B50;

    /// Group: `AttributeType`
    public static final int GL_FLOAT_VEC2_ARB = 0x8B50;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_FLOAT_VEC3 = 0x8B51;

    /// Group: `AttributeType`
    public static final int GL_FLOAT_VEC3_ARB = 0x8B51;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_FLOAT_VEC4 = 0x8B52;

    /// Group: `AttributeType`
    public static final int GL_FLOAT_VEC4_ARB = 0x8B52;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_FOG = 0x0B60;

    /// Group: `AttribMask`
    public static final int GL_FOG_BIT = 0x00000080;

    /// Groups: `GetPName`, `FogParameter`
    public static final int GL_FOG_COLOR = 0x0B66;

    /// Group: `FogCoordSrc`
    /// Alias: `GL_FOG_COORDINATE`
    public static final int GL_FOG_COORD = 0x8451;

    /// Group: `FogCoordSrc`
    public static final int GL_FOG_COORDINATE = 0x8451;

    public static final int GL_FOG_COORDINATE_ARRAY = 0x8457;

    public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING = 0x889D;

    public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING_ARB = 0x889D;

    public static final int GL_FOG_COORDINATE_ARRAY_EXT = 0x8457;

    public static final int GL_FOG_COORDINATE_ARRAY_LIST_IBM = 103076;

    public static final int GL_FOG_COORDINATE_ARRAY_LIST_STRIDE_IBM = 103086;

    public static final int GL_FOG_COORDINATE_ARRAY_POINTER = 0x8456;

    public static final int GL_FOG_COORDINATE_ARRAY_POINTER_EXT = 0x8456;

    public static final int GL_FOG_COORDINATE_ARRAY_STRIDE = 0x8455;

    public static final int GL_FOG_COORDINATE_ARRAY_STRIDE_EXT = 0x8455;

    public static final int GL_FOG_COORDINATE_ARRAY_TYPE = 0x8454;

    public static final int GL_FOG_COORDINATE_ARRAY_TYPE_EXT = 0x8454;

    /// Group: `FogCoordSrc`
    public static final int GL_FOG_COORDINATE_EXT = 0x8451;

    public static final int GL_FOG_COORDINATE_SOURCE = 0x8450;

    public static final int GL_FOG_COORDINATE_SOURCE_EXT = 0x8450;

    /// Alias: `GL_FOG_COORDINATE_ARRAY`
    public static final int GL_FOG_COORD_ARRAY = 0x8457;

    public static final int GL_FOG_COORD_ARRAY_ADDRESS_NV = 0x8F28;

    /// Alias: `GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING`
    public static final int GL_FOG_COORD_ARRAY_BUFFER_BINDING = 0x889D;

    public static final int GL_FOG_COORD_ARRAY_LENGTH_NV = 0x8F32;

    /// Alias: `GL_FOG_COORDINATE_ARRAY_POINTER`
    public static final int GL_FOG_COORD_ARRAY_POINTER = 0x8456;

    /// Alias: `GL_FOG_COORDINATE_ARRAY_STRIDE`
    public static final int GL_FOG_COORD_ARRAY_STRIDE = 0x8455;

    /// Alias: `GL_FOG_COORDINATE_ARRAY_TYPE`
    public static final int GL_FOG_COORD_ARRAY_TYPE = 0x8454;

    /// Group: `FogPName`
    /// Alias: `GL_FOG_COORDINATE_SOURCE`
    public static final int GL_FOG_COORD_SRC = 0x8450;

    /// Groups: `FogPName`, `FogParameter`, `GetPName`
    public static final int GL_FOG_DENSITY = 0x0B62;

    public static final int GL_FOG_DISTANCE_MODE_NV = 0x855A;

    /// Groups: `FogPName`, `FogParameter`, `GetPName`
    public static final int GL_FOG_END = 0x0B64;

    /// Group: `GetPName`
    public static final int GL_FOG_FUNC_POINTS_SGIS = 0x812B;

    /// Group: `FogMode`
    public static final int GL_FOG_FUNC_SGIS = 0x812A;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_FOG_HINT = 0x0C54;

    /// Groups: `FogPName`, `FogParameter`, `GetPName`
    public static final int GL_FOG_INDEX = 0x0B61;

    /// Groups: `FogPName`, `FogParameter`, `GetPName`
    public static final int GL_FOG_MODE = 0x0B65;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_FOG_OFFSET_SGIX = 0x8198;

    /// Groups: `GetPName`, `FogParameter`
    public static final int GL_FOG_OFFSET_VALUE_SGIX = 0x8199;

    public static final int GL_FOG_SPECULAR_TEXTURE_WIN = 0x80EC;

    /// Groups: `FogPName`, `FogParameter`, `GetPName`
    public static final int GL_FOG_START = 0x0B63;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_ASCENDER_BIT_NV = 0x00200000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_DESCENDER_BIT_NV = 0x00400000;

    public static final int GL_FONT_GLYPHS_AVAILABLE_NV = 0x9368;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_HAS_KERNING_BIT_NV = 0x10000000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_HEIGHT_BIT_NV = 0x00800000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_MAX_ADVANCE_HEIGHT_BIT_NV = 0x02000000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_MAX_ADVANCE_WIDTH_BIT_NV = 0x01000000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_NUM_GLYPH_INDICES_BIT_NV = 0x20000000;

    public static final int GL_FONT_TARGET_UNAVAILABLE_NV = 0x9369;

    public static final int GL_FONT_UNAVAILABLE_NV = 0x936A;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_UNDERLINE_POSITION_BIT_NV = 0x04000000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_UNDERLINE_THICKNESS_BIT_NV = 0x08000000;

    public static final int GL_FONT_UNINTELLIGIBLE_NV = 0x936B;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_UNITS_PER_EM_BIT_NV = 0x00100000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_X_MAX_BOUNDS_BIT_NV = 0x00040000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_X_MIN_BOUNDS_BIT_NV = 0x00010000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_Y_MAX_BOUNDS_BIT_NV = 0x00080000;

    /// Group: `PathMetricMask`
    public static final int GL_FONT_Y_MIN_BOUNDS_BIT_NV = 0x00020000;

    public static final int GL_FORCE_BLUE_TO_ONE_NV = 0x8860;

    public static final int GL_FORMAT_SUBSAMPLE_244_244_OML = 0x8983;

    public static final int GL_FORMAT_SUBSAMPLE_24_24_OML = 0x8982;

    /// Group: `FoveationConfigBitQCOM`
    public static final int GL_FOVEATION_ENABLE_BIT_QCOM = 0x00000001;

    /// Group: `FoveationConfigBitQCOM`
    public static final int GL_FOVEATION_SCALED_BIN_METHOD_BIT_QCOM = 0x00000002;

    /// Group: `FoveationConfigBitQCOM`
    public static final int GL_FOVEATION_SUBSAMPLED_LAYOUT_METHOD_BIT_QCOM = 0x00000004;

    public static final int GL_FRACTIONAL_EVEN = 0x8E7C;

    public static final int GL_FRACTIONAL_EVEN_EXT = 0x8E7C;

    public static final int GL_FRACTIONAL_EVEN_OES = 0x8E7C;

    public static final int GL_FRACTIONAL_ODD = 0x8E7B;

    public static final int GL_FRACTIONAL_ODD_EXT = 0x8E7B;

    public static final int GL_FRACTIONAL_ODD_OES = 0x8E7B;

    public static final int GL_FRAGMENTS_INSTRUMENT_COUNTERS_SGIX = 0x8314;

    public static final int GL_FRAGMENTS_INSTRUMENT_MAX_SGIX = 0x8315;

    public static final int GL_FRAGMENTS_INSTRUMENT_SGIX = 0x8313;

    public static final int GL_FRAGMENT_ALPHA_MODULATE_IMG = 0x8C08;

    /// Group: `LightTextureModeEXT`
    public static final int GL_FRAGMENT_COLOR_EXT = 0x834C;

    /// Group: `GetPName`
    public static final int GL_FRAGMENT_COLOR_MATERIAL_FACE_SGIX = 0x8402;

    /// Group: `GetPName`
    public static final int GL_FRAGMENT_COLOR_MATERIAL_PARAMETER_SGIX = 0x8403;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_FRAGMENT_COLOR_MATERIAL_SGIX = 0x8401;

    public static final int GL_FRAGMENT_COVERAGE_COLOR_NV = 0x92DE;

    public static final int GL_FRAGMENT_COVERAGE_TO_COLOR_NV = 0x92DD;

    /// Groups: `FogCoordSrc`, `LightTextureModeEXT`
    public static final int GL_FRAGMENT_DEPTH = 0x8452;

    /// Groups: `FogCoordSrc`, `LightTextureModeEXT`
    public static final int GL_FRAGMENT_DEPTH_EXT = 0x8452;

    public static final int GL_FRAGMENT_INPUT_NV = 0x936D;

    public static final int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS = 0x8E5D;

    public static final int GL_FRAGMENT_INTERPOLATION_OFFSET_BITS_OES = 0x8E5D;

    /// Groups: `LightName`, `FragmentLightNameSGIX`, `EnableCap`, `GetPName`
    public static final int GL_FRAGMENT_LIGHT0_SGIX = 0x840C;

    /// Groups: `LightName`, `FragmentLightNameSGIX`, `EnableCap`
    public static final int GL_FRAGMENT_LIGHT1_SGIX = 0x840D;

    /// Groups: `LightName`, `FragmentLightNameSGIX`, `EnableCap`
    public static final int GL_FRAGMENT_LIGHT2_SGIX = 0x840E;

    /// Groups: `LightName`, `FragmentLightNameSGIX`, `EnableCap`
    public static final int GL_FRAGMENT_LIGHT3_SGIX = 0x840F;

    /// Groups: `LightName`, `FragmentLightNameSGIX`, `EnableCap`
    public static final int GL_FRAGMENT_LIGHT4_SGIX = 0x8410;

    /// Groups: `LightName`, `FragmentLightNameSGIX`, `EnableCap`
    public static final int GL_FRAGMENT_LIGHT5_SGIX = 0x8411;

    /// Groups: `LightName`, `FragmentLightNameSGIX`, `EnableCap`
    public static final int GL_FRAGMENT_LIGHT6_SGIX = 0x8412;

    /// Groups: `LightName`, `FragmentLightNameSGIX`, `EnableCap`
    public static final int GL_FRAGMENT_LIGHT7_SGIX = 0x8413;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_FRAGMENT_LIGHTING_SGIX = 0x8400;

    /// Groups: `GetPName`, `FragmentLightModelParameterSGIX`
    public static final int GL_FRAGMENT_LIGHT_MODEL_AMBIENT_SGIX = 0x840A;

    /// Groups: `GetPName`, `FragmentLightModelParameterSGIX`
    public static final int GL_FRAGMENT_LIGHT_MODEL_LOCAL_VIEWER_SGIX = 0x8408;

    /// Groups: `GetPName`, `FragmentLightModelParameterSGIX`
    public static final int GL_FRAGMENT_LIGHT_MODEL_NORMAL_INTERPOLATION_SGIX = 0x840B;

    /// Groups: `GetPName`, `FragmentLightModelParameterSGIX`
    public static final int GL_FRAGMENT_LIGHT_MODEL_TWO_SIDE_SGIX = 0x8409;

    /// Group: `LightTextureModeEXT`
    public static final int GL_FRAGMENT_MATERIAL_EXT = 0x8349;

    /// Group: `LightTextureModeEXT`
    public static final int GL_FRAGMENT_NORMAL_EXT = 0x834A;

    /// Group: `ProgramTarget`
    public static final int GL_FRAGMENT_PROGRAM_ARB = 0x8804;

    public static final int GL_FRAGMENT_PROGRAM_BINDING_NV = 0x8873;

    public static final int GL_FRAGMENT_PROGRAM_CALLBACK_DATA_MESA = 0x8BB3;

    public static final int GL_FRAGMENT_PROGRAM_CALLBACK_FUNC_MESA = 0x8BB2;

    public static final int GL_FRAGMENT_PROGRAM_CALLBACK_MESA = 0x8BB1;

    public static final int GL_FRAGMENT_PROGRAM_INTERPOLATION_OFFSET_BITS_NV = 0x8E5D;

    public static final int GL_FRAGMENT_PROGRAM_NV = 0x8870;

    public static final int GL_FRAGMENT_PROGRAM_PARAMETER_BUFFER_NV = 0x8DA4;

    public static final int GL_FRAGMENT_PROGRAM_POSITION_MESA = 0x8BB0;

    /// Groups: `PipelineParameterName`, `ShaderType`
    public static final int GL_FRAGMENT_SHADER = 0x8B30;

    /// Group: `ShaderType`
    public static final int GL_FRAGMENT_SHADER_ARB = 0x8B30;

    /// Group: `GetPName`
    public static final int GL_FRAGMENT_SHADER_ATI = 0x8920;

    /// Group: `UseProgramStageMask`
    public static final int GL_FRAGMENT_SHADER_BIT = 0x00000002;

    /// Group: `UseProgramStageMask`
    public static final int GL_FRAGMENT_SHADER_BIT_EXT = 0x00000002;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 0x8B8B;

    /// Group: `HintTarget`
    public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT_ARB = 0x8B8B;

    /// Group: `HintTarget`
    public static final int GL_FRAGMENT_SHADER_DERIVATIVE_HINT_OES = 0x8B8B;

    public static final int GL_FRAGMENT_SHADER_DISCARDS_SAMPLES_EXT = 0x8A52;

    /// Group: `GetPName`
    public static final int GL_FRAGMENT_SHADER_FRAMEBUFFER_FETCH_MRT_ARM = 0x8F66;

    public static final int GL_FRAGMENT_SHADER_INVOCATIONS = 0x82F4;

    /// Alias: `GL_FRAGMENT_SHADER_INVOCATIONS`
    public static final int GL_FRAGMENT_SHADER_INVOCATIONS_ARB = 0x82F4;

    /// Group: `GetPName`
    public static final int GL_FRAGMENT_SHADING_RATE_ATTACHMENT_WITH_DEFAULT_FRAMEBUFFER_SUPPORTED_EXT =
        0x96DF;

    /// Group: `ShadingRateCombinerOp`
    public static final int GL_FRAGMENT_SHADING_RATE_COMBINER_OP_KEEP_EXT = 0x96D2;

    /// Group: `ShadingRateCombinerOp`
    public static final int GL_FRAGMENT_SHADING_RATE_COMBINER_OP_MAX_EXT = 0x96D5;

    /// Group: `ShadingRateCombinerOp`
    public static final int GL_FRAGMENT_SHADING_RATE_COMBINER_OP_MIN_EXT = 0x96D4;

    /// Group: `ShadingRateCombinerOp`
    public static final int GL_FRAGMENT_SHADING_RATE_COMBINER_OP_MUL_EXT = 0x96D6;

    /// Group: `ShadingRateCombinerOp`
    public static final int GL_FRAGMENT_SHADING_RATE_COMBINER_OP_REPLACE_EXT = 0x96D3;

    /// Group: `GetPName`
    public static final int GL_FRAGMENT_SHADING_RATE_NON_TRIVIAL_COMBINERS_SUPPORTED_EXT = 0x8F6F;

    /// Group: `GetPName`
    public static final int GL_FRAGMENT_SHADING_RATE_WITH_SAMPLE_MASK_SUPPORTED_EXT = 0x96DE;

    /// Group: `GetPName`
    public static final int GL_FRAGMENT_SHADING_RATE_WITH_SHADER_DEPTH_STENCIL_WRITES_SUPPORTED_EXT = 0x96DD;

    /// Group: `ProgramInterface`
    public static final int GL_FRAGMENT_SUBROUTINE = 0x92EC;

    /// Group: `ProgramInterface`
    public static final int GL_FRAGMENT_SUBROUTINE_UNIFORM = 0x92F2;

    /// Group: `InternalFormatPName`
    public static final int GL_FRAGMENT_TEXTURE = 0x829F;

    /// Groups: `ObjectIdentifier`, `FramebufferTarget`
    public static final int GL_FRAMEBUFFER = 0x8D40;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 0x8215;

    public static final int GL_FRAMEBUFFER_ATTACHMENT_ANGLE = 0x93A3;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 0x8214;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 0x8210;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING_EXT = 0x8210;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 0x8211;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE_EXT = 0x8211;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 0x8216;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 0x8213;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 0x8DA7;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED_ARB = 0x8DA7;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED_EXT = 0x8DA7;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED_OES = 0x8DA7;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 0x8CD1;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME_EXT = 0x8CD1;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME_OES = 0x8CD1;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 0x8CD0;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE_EXT = 0x8CD0;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE_OES = 0x8CD0;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 0x8212;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 0x8217;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_3D_ZOFFSET_EXT = 0x8CD4;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_3D_ZOFFSET_OES = 0x8CD4;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_BASE_VIEW_INDEX_OVR = 0x9632;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 0x8CD3;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE_EXT = 0x8CD3;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE_OES = 0x8CD3;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 0x8CD4;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER_EXT = 0x8CD4;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 0x8CD2;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL_EXT = 0x8CD2;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL_OES = 0x8CD2;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_NUM_VIEWS_OVR = 0x9630;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_SAMPLES_EXT = 0x8D6C;

    /// Group: `FramebufferAttachmentParameterName`
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_SCALE_IMG = 0x913F;

    /// Group: `MemoryBarrierMask`
    public static final int GL_FRAMEBUFFER_BARRIER_BIT = 0x00000400;

    /// Group: `MemoryBarrierMask`
    public static final int GL_FRAMEBUFFER_BARRIER_BIT_EXT = 0x00000400;

    public static final int GL_FRAMEBUFFER_BINDING = 0x8CA6;

    public static final int GL_FRAMEBUFFER_BINDING_ANGLE = 0x8CA6;

    public static final int GL_FRAMEBUFFER_BINDING_EXT = 0x8CA6;

    public static final int GL_FRAMEBUFFER_BINDING_OES = 0x8CA6;

    /// Group: `InternalFormatPName`
    public static final int GL_FRAMEBUFFER_BLEND = 0x828B;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_COMPLETE = 0x8CD5;

    public static final int GL_FRAMEBUFFER_COMPLETE_EXT = 0x8CD5;

    public static final int GL_FRAMEBUFFER_COMPLETE_OES = 0x8CD5;

    public static final int GL_FRAMEBUFFER_DEFAULT = 0x8218;

    /// Groups: `GetFramebufferParameter`, `FramebufferParameterName`
    public static final int GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = 0x9314;

    /// Groups: `GetFramebufferParameter`, `FramebufferParameterName`
    public static final int GL_FRAMEBUFFER_DEFAULT_HEIGHT = 0x9311;

    /// Groups: `GetFramebufferParameter`, `FramebufferParameterName`
    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS = 0x9312;

    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS_EXT = 0x9312;

    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS_OES = 0x9312;

    /// Groups: `GetFramebufferParameter`, `FramebufferParameterName`
    public static final int GL_FRAMEBUFFER_DEFAULT_SAMPLES = 0x9313;

    /// Groups: `GetFramebufferParameter`, `FramebufferParameterName`
    public static final int GL_FRAMEBUFFER_DEFAULT_WIDTH = 0x9310;

    public static final int GL_FRAMEBUFFER_EXT = 0x8D40;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_FRAMEBUFFER_FETCH_NONCOHERENT_QCOM = 0x96A2;

    public static final int GL_FRAMEBUFFER_FLIP_X_MESA = 0x8BBC;

    public static final int GL_FRAMEBUFFER_FLIP_Y_MESA = 0x8BBB;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 0x8CD6;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT = 0x8CD6;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_OES = 0x8CD6;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS = 0x8CD9;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT = 0x8CD9;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_OES = 0x8CD9;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 0x8CDB;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT = 0x8CDB;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_OES = 0x8CDB;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT = 0x8CDA;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_FORMATS_OES = 0x8CDA;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_FOVEATION_QCOM = 0x8BFF;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_INSUFFICIENT_SHADER_COMBINED_LOCAL_STORAGE_EXT = 0x9652;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_COUNT_ARB = 0x8DA9;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_COUNT_EXT = 0x8DA9;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 0x8DA8;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS_ARB = 0x8DA8;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS_EXT = 0x8DA8;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS_OES = 0x8DA8;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 0x8CD7;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT = 0x8CD7;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_OES = 0x8CD7;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 0x8D56;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_AND_DOWNSAMPLE_IMG = 0x913C;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_ANGLE = 0x8D56;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_APPLE = 0x8D56;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_EXT = 0x8D56;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_IMG = 0x9134;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE_NV = 0x8D56;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 0x8CDC;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT = 0x8CDC;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_OES = 0x8CDC;

    public static final int GL_FRAMEBUFFER_INCOMPLETE_VIEW_TARGETS_OVR = 0x9633;

    /// Group: `FramebufferTarget`
    public static final int GL_FRAMEBUFFER_OES = 0x8D40;

    public static final int GL_FRAMEBUFFER_PROGRAMMABLE_SAMPLE_LOCATIONS_ARB = 0x9342;

    public static final int GL_FRAMEBUFFER_PROGRAMMABLE_SAMPLE_LOCATIONS_NV = 0x9342;

    /// Group: `InternalFormatPName`
    public static final int GL_FRAMEBUFFER_RENDERABLE = 0x8289;

    /// Group: `InternalFormatPName`
    public static final int GL_FRAMEBUFFER_RENDERABLE_LAYERED = 0x828A;

    public static final int GL_FRAMEBUFFER_SAMPLE_LOCATION_PIXEL_GRID_ARB = 0x9343;

    public static final int GL_FRAMEBUFFER_SAMPLE_LOCATION_PIXEL_GRID_NV = 0x9343;

    /// Group: `EnableCap`
    public static final int GL_FRAMEBUFFER_SRGB = 0x8DB9;

    public static final int GL_FRAMEBUFFER_SRGB_CAPABLE_EXT = 0x8DBA;

    public static final int GL_FRAMEBUFFER_SRGB_EXT = 0x8DB9;

    public static final int GL_FRAMEBUFFER_SWAP_XY_MESA = 0x8BBD;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_UNDEFINED = 0x8219;

    public static final int GL_FRAMEBUFFER_UNDEFINED_OES = 0x8219;

    /// Group: `FramebufferStatus`
    public static final int GL_FRAMEBUFFER_UNSUPPORTED = 0x8CDD;

    public static final int GL_FRAMEBUFFER_UNSUPPORTED_EXT = 0x8CDD;

    public static final int GL_FRAMEBUFFER_UNSUPPORTED_OES = 0x8CDD;

    /// Group: `GetPName`
    public static final int GL_FRAMEZOOM_FACTOR_SGIX = 0x818C;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_FRAMEZOOM_SGIX = 0x818B;

    public static final int GL_FRAME_NV = 0x8E26;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`, `TriangleFace`
    public static final int GL_FRONT = 0x0404;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `TriangleFace`
    public static final int GL_FRONT_AND_BACK = 0x0408;

    /// Group: `GetPName`
    public static final int GL_FRONT_FACE = 0x0B46;

    /// Group: `CommandOpcodesNV`
    public static final int GL_FRONT_FACE_COMMAND_NV = 0x0012;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`
    public static final int GL_FRONT_LEFT = 0x0400;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`
    public static final int GL_FRONT_RIGHT = 0x0401;

    /// Group: `ParameterRangeEXT`
    public static final int GL_FULL_RANGE_EXT = 0x87E1;

    /// Group: `HintTarget`
    public static final int GL_FULL_STIPPLE_HINT_PGI = 0x1A219;

    public static final int GL_FULL_SUPPORT = 0x82B7;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_FUNC_ADD = 0x8006;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_FUNC_ADD_EXT = 0x8006;

    public static final int GL_FUNC_ADD_OES = 0x8006;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_FUNC_REVERSE_SUBTRACT = 0x800B;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_FUNC_REVERSE_SUBTRACT_EXT = 0x800B;

    public static final int GL_FUNC_REVERSE_SUBTRACT_OES = 0x800B;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_FUNC_SUBTRACT = 0x800A;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_FUNC_SUBTRACT_EXT = 0x800A;

    public static final int GL_FUNC_SUBTRACT_OES = 0x800A;

    /// Group: `ShaderBinaryFormat`
    public static final int GL_GCCSO_SHADER_BINARY_FJ = 0x9260;

    /// Groups: `InternalFormatPName`, `TextureParameterName`
    public static final int GL_GENERATE_MIPMAP = 0x8191;

    /// Group: `HintTarget`
    public static final int GL_GENERATE_MIPMAP_HINT = 0x8192;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_GENERATE_MIPMAP_HINT_SGIS = 0x8192;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_GENERATE_MIPMAP_SGIS = 0x8191;

    public static final int GL_GENERIC_ATTRIB_NV = 0x8C7D;

    /// Group: `FfdMaskSGIX`
    public static final int GL_GEOMETRY_DEFORMATION_BIT_SGIX = 0x00000002;

    /// Groups: `MapTarget`, `FfdTargetSGIX`
    public static final int GL_GEOMETRY_DEFORMATION_SGIX = 0x8194;

    /// Group: `ProgramPropertyARB`
    public static final int GL_GEOMETRY_INPUT_TYPE = 0x8917;

    public static final int GL_GEOMETRY_INPUT_TYPE_ARB = 0x8DDB;

    public static final int GL_GEOMETRY_INPUT_TYPE_EXT = 0x8DDB;

    public static final int GL_GEOMETRY_LINKED_INPUT_TYPE_EXT = 0x8917;

    public static final int GL_GEOMETRY_LINKED_INPUT_TYPE_OES = 0x8917;

    public static final int GL_GEOMETRY_LINKED_OUTPUT_TYPE_EXT = 0x8918;

    public static final int GL_GEOMETRY_LINKED_OUTPUT_TYPE_OES = 0x8918;

    public static final int GL_GEOMETRY_LINKED_VERTICES_OUT_EXT = 0x8916;

    public static final int GL_GEOMETRY_LINKED_VERTICES_OUT_OES = 0x8916;

    /// Group: `ProgramPropertyARB`
    public static final int GL_GEOMETRY_OUTPUT_TYPE = 0x8918;

    public static final int GL_GEOMETRY_OUTPUT_TYPE_ARB = 0x8DDC;

    public static final int GL_GEOMETRY_OUTPUT_TYPE_EXT = 0x8DDC;

    /// Group: `ProgramTarget`
    public static final int GL_GEOMETRY_PROGRAM_NV = 0x8C26;

    public static final int GL_GEOMETRY_PROGRAM_PARAMETER_BUFFER_NV = 0x8DA3;

    /// Groups: `PipelineParameterName`, `ShaderType`
    public static final int GL_GEOMETRY_SHADER = 0x8DD9;

    public static final int GL_GEOMETRY_SHADER_ARB = 0x8DD9;

    /// Group: `UseProgramStageMask`
    public static final int GL_GEOMETRY_SHADER_BIT = 0x00000004;

    /// Group: `UseProgramStageMask`
    public static final int GL_GEOMETRY_SHADER_BIT_EXT = 0x00000004;

    /// Group: `UseProgramStageMask`
    public static final int GL_GEOMETRY_SHADER_BIT_OES = 0x00000004;

    public static final int GL_GEOMETRY_SHADER_EXT = 0x8DD9;

    public static final int GL_GEOMETRY_SHADER_INVOCATIONS = 0x887F;

    public static final int GL_GEOMETRY_SHADER_INVOCATIONS_EXT = 0x887F;

    public static final int GL_GEOMETRY_SHADER_INVOCATIONS_OES = 0x887F;

    public static final int GL_GEOMETRY_SHADER_OES = 0x8DD9;

    public static final int GL_GEOMETRY_SHADER_PRIMITIVES_EMITTED = 0x82F3;

    /// Alias: `GL_GEOMETRY_SHADER_PRIMITIVES_EMITTED`
    public static final int GL_GEOMETRY_SHADER_PRIMITIVES_EMITTED_ARB = 0x82F3;

    /// Group: `ProgramInterface`
    public static final int GL_GEOMETRY_SUBROUTINE = 0x92EB;

    /// Group: `ProgramInterface`
    public static final int GL_GEOMETRY_SUBROUTINE_UNIFORM = 0x92F1;

    /// Group: `InternalFormatPName`
    public static final int GL_GEOMETRY_TEXTURE = 0x829E;

    /// Group: `ProgramPropertyARB`
    public static final int GL_GEOMETRY_VERTICES_OUT = 0x8916;

    public static final int GL_GEOMETRY_VERTICES_OUT_ARB = 0x8DDA;

    public static final int GL_GEOMETRY_VERTICES_OUT_EXT = 0x8DDA;

    /// Groups: `StencilFunction`, `IndexFunctionEXT`, `AlphaFunction`,
    ///   `DepthFunction`
    public static final int GL_GEQUAL = 0x0206;

    /// Group: `InternalFormatPName`
    public static final int GL_GET_TEXTURE_IMAGE_FORMAT = 0x8291;

    /// Group: `InternalFormatPName`
    public static final int GL_GET_TEXTURE_IMAGE_TYPE = 0x8292;

    public static final int GL_GLOBAL_ALPHA_FACTOR_SUN = 0x81DA;

    public static final int GL_GLOBAL_ALPHA_SUN = 0x81D9;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_HAS_KERNING_BIT_NV = 0x100;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_HEIGHT_BIT_NV = 0x02;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_HORIZONTAL_BEARING_ADVANCE_BIT_NV = 0x10;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_HORIZONTAL_BEARING_X_BIT_NV = 0x04;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_HORIZONTAL_BEARING_Y_BIT_NV = 0x08;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_VERTICAL_BEARING_ADVANCE_BIT_NV = 0x80;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_VERTICAL_BEARING_X_BIT_NV = 0x20;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_VERTICAL_BEARING_Y_BIT_NV = 0x40;

    /// Group: `PathMetricMask`
    public static final int GL_GLYPH_WIDTH_BIT_NV = 0x01;

    public static final int GL_GPU_ADDRESS_NV = 0x8F34;

    public static final int GL_GPU_DISJOINT_EXT = 0x8FBB;

    public static final int GL_GPU_MEMORY_INFO_CURRENT_AVAILABLE_VIDMEM_NVX = 0x9049;

    public static final int GL_GPU_MEMORY_INFO_DEDICATED_VIDMEM_NVX = 0x9047;

    public static final int GL_GPU_MEMORY_INFO_EVICTED_MEMORY_NVX = 0x904B;

    public static final int GL_GPU_MEMORY_INFO_EVICTION_COUNT_NVX = 0x904A;

    public static final int GL_GPU_MEMORY_INFO_TOTAL_AVAILABLE_MEMORY_NVX = 0x9048;

    public static final int GL_GPU_OPTIMIZED_QCOM = 0x8FB2;

    /// Groups: `StencilFunction`, `IndexFunctionEXT`, `AlphaFunction`,
    ///   `DepthFunction`
    public static final int GL_GREATER = 0x0204;

    /// Groups: `FragmentShaderValueRepATI`, `TextureSwizzle`, `PixelFormat`
    public static final int GL_GREEN = 0x1904;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_GREEN_BIAS = 0x0D19;

    /// Group: `GetPName`
    public static final int GL_GREEN_BITS = 0x0D53;

    /// Group: `FragmentShaderDestMaskATI`
    public static final int GL_GREEN_BIT_ATI = 0x00000002;

    /// Group: `PixelFormat`
    public static final int GL_GREEN_INTEGER = 0x8D95;

    public static final int GL_GREEN_INTEGER_EXT = 0x8D95;

    public static final int GL_GREEN_MAX_CLAMP_INGR = 0x8565;

    public static final int GL_GREEN_MIN_CLAMP_INGR = 0x8561;

    public static final int GL_GREEN_NV = 0x1904;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_GREEN_SCALE = 0x0D18;

    public static final int GL_GS_PROGRAM_BINARY_MTK = 0x9641;

    public static final int GL_GS_SHADER_BINARY_MTK = 0x9640;

    /// Group: `GraphicsResetStatus`
    public static final int GL_GUILTY_CONTEXT_RESET = 0x8253;

    public static final int GL_GUILTY_CONTEXT_RESET_ARB = 0x8253;

    public static final int GL_GUILTY_CONTEXT_RESET_EXT = 0x8253;

    public static final int GL_GUILTY_CONTEXT_RESET_KHR = 0x8253;

    /// Group: `PixelType`
    public static final int GL_HALF_APPLE = 0x140B;

    /// Group: `CombinerMappingNV`
    public static final int GL_HALF_BIAS_NEGATE_NV = 0x853B;

    /// Group: `CombinerMappingNV`
    public static final int GL_HALF_BIAS_NORMAL_NV = 0x853A;

    /// Group: `FragmentShaderDestModMaskATI`
    public static final int GL_HALF_BIT_ATI = 0x00000008;

    /// Groups: `PixelType`, `VertexAttribPointerType`, `VertexAttribType`
    public static final int GL_HALF_FLOAT = 0x140B;

    /// Group: `PixelType`
    public static final int GL_HALF_FLOAT_ARB = 0x140B;

    /// Group: `PixelType`
    public static final int GL_HALF_FLOAT_NV = 0x140B;

    public static final int GL_HALF_FLOAT_OES = 0x8D61;

    /// Group: `ExternalHandleType`
    public static final int GL_HANDLE_TYPE_D3D11_IMAGE_EXT = 0x958B;

    /// Group: `ExternalHandleType`
    public static final int GL_HANDLE_TYPE_D3D11_IMAGE_KMT_EXT = 0x958C;

    /// Group: `ExternalHandleType`
    public static final int GL_HANDLE_TYPE_D3D12_FENCE_EXT = 0x9594;

    /// Group: `ExternalHandleType`
    public static final int GL_HANDLE_TYPE_D3D12_RESOURCE_EXT = 0x958A;

    /// Group: `ExternalHandleType`
    public static final int GL_HANDLE_TYPE_D3D12_TILEPOOL_EXT = 0x9589;

    /// Group: `ExternalHandleType`
    public static final int GL_HANDLE_TYPE_OPAQUE_FD_EXT = 0x9586;

    /// Group: `ExternalHandleType`
    public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_EXT = 0x9587;

    /// Group: `ExternalHandleType`
    public static final int GL_HANDLE_TYPE_OPAQUE_WIN32_KMT_EXT = 0x9588;

    public static final int GL_HARDLIGHT = 0x929B;

    public static final int GL_HARDLIGHT_KHR = 0x929B;

    public static final int GL_HARDLIGHT_NV = 0x929B;

    public static final int GL_HARDMIX_NV = 0x92A9;

    /// Group: `PrecisionType`
    public static final int GL_HIGH_FLOAT = 0x8DF2;

    /// Group: `PrecisionType`
    public static final int GL_HIGH_INT = 0x8DF5;

    public static final int GL_HILO16_NV = 0x86F8;

    public static final int GL_HILO8_NV = 0x885E;

    public static final int GL_HILO_NV = 0x86F4;

    /// Group: `AttribMask`
    public static final int GL_HINT_BIT = 0x00008000;

    /// Groups: `HistogramTarget`, `HistogramTargetEXT`
    public static final int GL_HISTOGRAM = 0x8024;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_ALPHA_SIZE = 0x802B;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_ALPHA_SIZE_EXT = 0x802B;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_BLUE_SIZE = 0x802A;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_BLUE_SIZE_EXT = 0x802A;

    /// Groups: `HistogramTargetEXT`, `EnableCap`, `GetPName`
    public static final int GL_HISTOGRAM_EXT = 0x8024;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_FORMAT = 0x8027;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_FORMAT_EXT = 0x8027;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_GREEN_SIZE = 0x8029;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_GREEN_SIZE_EXT = 0x8029;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_LUMINANCE_SIZE = 0x802C;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_LUMINANCE_SIZE_EXT = 0x802C;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_RED_SIZE = 0x8028;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_RED_SIZE_EXT = 0x8028;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_SINK = 0x802D;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_SINK_EXT = 0x802D;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_WIDTH = 0x8026;

    /// Group: `GetHistogramParameterPNameEXT`
    public static final int GL_HISTOGRAM_WIDTH_EXT = 0x8026;

    public static final int GL_HI_BIAS_NV = 0x8714;

    public static final int GL_HI_SCALE_NV = 0x870E;

    /// Group: `PathCoordType`
    public static final int GL_HORIZONTAL_LINE_TO_NV = 0x06;

    public static final int GL_HSL_COLOR = 0x92AF;

    public static final int GL_HSL_COLOR_KHR = 0x92AF;

    public static final int GL_HSL_COLOR_NV = 0x92AF;

    public static final int GL_HSL_HUE = 0x92AD;

    public static final int GL_HSL_HUE_KHR = 0x92AD;

    public static final int GL_HSL_HUE_NV = 0x92AD;

    public static final int GL_HSL_LUMINOSITY = 0x92B0;

    public static final int GL_HSL_LUMINOSITY_KHR = 0x92B0;

    public static final int GL_HSL_LUMINOSITY_NV = 0x92B0;

    public static final int GL_HSL_SATURATION = 0x92AE;

    public static final int GL_HSL_SATURATION_KHR = 0x92AE;

    public static final int GL_HSL_SATURATION_NV = 0x92AE;

    public static final int GL_IDENTITY_NV = 0x862A;

    public static final int GL_IGNORE_BORDER_HP = 0x8150;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_1D = 0x904C;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_1D_ARRAY = 0x9052;

    public static final int GL_IMAGE_1D_ARRAY_EXT = 0x9052;

    public static final int GL_IMAGE_1D_EXT = 0x904C;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_2D = 0x904D;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_2D_ARRAY = 0x9053;

    public static final int GL_IMAGE_2D_ARRAY_EXT = 0x9053;

    public static final int GL_IMAGE_2D_EXT = 0x904D;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_2D_MULTISAMPLE = 0x9055;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_2D_MULTISAMPLE_ARRAY = 0x9056;

    public static final int GL_IMAGE_2D_MULTISAMPLE_ARRAY_EXT = 0x9056;

    public static final int GL_IMAGE_2D_MULTISAMPLE_EXT = 0x9055;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_2D_RECT = 0x904F;

    public static final int GL_IMAGE_2D_RECT_EXT = 0x904F;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_3D = 0x904E;

    public static final int GL_IMAGE_3D_EXT = 0x904E;

    public static final int GL_IMAGE_BINDING_ACCESS = 0x8F3E;

    public static final int GL_IMAGE_BINDING_ACCESS_EXT = 0x8F3E;

    public static final int GL_IMAGE_BINDING_FORMAT = 0x906E;

    public static final int GL_IMAGE_BINDING_FORMAT_EXT = 0x906E;

    public static final int GL_IMAGE_BINDING_LAYER = 0x8F3D;

    public static final int GL_IMAGE_BINDING_LAYERED = 0x8F3C;

    public static final int GL_IMAGE_BINDING_LAYERED_EXT = 0x8F3C;

    public static final int GL_IMAGE_BINDING_LAYER_EXT = 0x8F3D;

    public static final int GL_IMAGE_BINDING_LEVEL = 0x8F3B;

    public static final int GL_IMAGE_BINDING_LEVEL_EXT = 0x8F3B;

    public static final int GL_IMAGE_BINDING_NAME = 0x8F3A;

    public static final int GL_IMAGE_BINDING_NAME_EXT = 0x8F3A;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_BUFFER = 0x9051;

    public static final int GL_IMAGE_BUFFER_EXT = 0x9051;

    public static final int GL_IMAGE_BUFFER_OES = 0x9051;

    public static final int GL_IMAGE_CLASS_10_10_10_2 = 0x82C3;

    public static final int GL_IMAGE_CLASS_11_11_10 = 0x82C2;

    public static final int GL_IMAGE_CLASS_1_X_16 = 0x82BE;

    public static final int GL_IMAGE_CLASS_1_X_32 = 0x82BB;

    public static final int GL_IMAGE_CLASS_1_X_8 = 0x82C1;

    public static final int GL_IMAGE_CLASS_2_X_16 = 0x82BD;

    public static final int GL_IMAGE_CLASS_2_X_32 = 0x82BA;

    public static final int GL_IMAGE_CLASS_2_X_8 = 0x82C0;

    public static final int GL_IMAGE_CLASS_4_X_16 = 0x82BC;

    public static final int GL_IMAGE_CLASS_4_X_32 = 0x82B9;

    public static final int GL_IMAGE_CLASS_4_X_8 = 0x82BF;

    /// Group: `InternalFormatPName`
    public static final int GL_IMAGE_COMPATIBILITY_CLASS = 0x82A8;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_CUBE = 0x9050;

    public static final int GL_IMAGE_CUBE_EXT = 0x9050;

    /// Group: `AttributeType`
    public static final int GL_IMAGE_CUBE_MAP_ARRAY = 0x9054;

    public static final int GL_IMAGE_CUBE_MAP_ARRAY_EXT = 0x9054;

    public static final int GL_IMAGE_CUBE_MAP_ARRAY_OES = 0x9054;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_CUBIC_WEIGHT_HP = 0x815E;

    public static final int GL_IMAGE_FORMAT_COMPATIBILITY_BY_CLASS = 0x90C9;

    public static final int GL_IMAGE_FORMAT_COMPATIBILITY_BY_SIZE = 0x90C8;

    /// Group: `InternalFormatPName`
    public static final int GL_IMAGE_FORMAT_COMPATIBILITY_TYPE = 0x90C7;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_MAG_FILTER_HP = 0x815C;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_MIN_FILTER_HP = 0x815D;

    /// Group: `InternalFormatPName`
    public static final int GL_IMAGE_PIXEL_FORMAT = 0x82A9;

    /// Group: `InternalFormatPName`
    public static final int GL_IMAGE_PIXEL_TYPE = 0x82AA;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_ROTATE_ANGLE_HP = 0x8159;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_ROTATE_ORIGIN_X_HP = 0x815A;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_ROTATE_ORIGIN_Y_HP = 0x815B;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_SCALE_X_HP = 0x8155;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_SCALE_Y_HP = 0x8156;

    /// Group: `InternalFormatPName`
    public static final int GL_IMAGE_TEXEL_SIZE = 0x82A7;

    /// Group: `ImageTransformTargetHP`
    public static final int GL_IMAGE_TRANSFORM_2D_HP = 0x8161;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_TRANSLATE_X_HP = 0x8157;

    /// Group: `ImageTransformPNameHP`
    public static final int GL_IMAGE_TRANSLATE_Y_HP = 0x8158;

    /// Groups: `GetFramebufferParameter`, `GetPName`
    public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 0x8B9B;

    public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT_OES = 0x8B9B;

    /// Groups: `GetFramebufferParameter`, `GetPName`
    public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 0x8B9A;

    public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE_OES = 0x8B9A;

    public static final int GL_INCLUSIVE_EXT = 0x8F10;

    /// Group: `StencilOp`
    public static final int GL_INCR = 0x1E02;

    /// Group: `StencilOp`
    public static final int GL_INCR_WRAP = 0x8507;

    public static final int GL_INCR_WRAP_EXT = 0x8507;

    public static final int GL_INCR_WRAP_OES = 0x8507;

    public static final int GL_INDEX = 0x8222;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_INDEX_ARRAY = 0x8077;

    public static final int GL_INDEX_ARRAY_ADDRESS_NV = 0x8F24;

    public static final int GL_INDEX_ARRAY_BUFFER_BINDING = 0x8899;

    public static final int GL_INDEX_ARRAY_BUFFER_BINDING_ARB = 0x8899;

    /// Group: `GetPName`
    public static final int GL_INDEX_ARRAY_COUNT_EXT = 0x8087;

    public static final int GL_INDEX_ARRAY_EXT = 0x8077;

    public static final int GL_INDEX_ARRAY_LENGTH_NV = 0x8F2E;

    public static final int GL_INDEX_ARRAY_LIST_IBM = 103073;

    public static final int GL_INDEX_ARRAY_LIST_STRIDE_IBM = 103083;

    /// Group: `GetPointervPName`
    public static final int GL_INDEX_ARRAY_POINTER = 0x8091;

    /// Group: `GetPointervPName`
    public static final int GL_INDEX_ARRAY_POINTER_EXT = 0x8091;

    /// Group: `GetPName`
    public static final int GL_INDEX_ARRAY_STRIDE = 0x8086;

    public static final int GL_INDEX_ARRAY_STRIDE_EXT = 0x8086;

    /// Group: `GetPName`
    public static final int GL_INDEX_ARRAY_TYPE = 0x8085;

    public static final int GL_INDEX_ARRAY_TYPE_EXT = 0x8085;

    /// Group: `GetPName`
    public static final int GL_INDEX_BITS = 0x0D51;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_INDEX_BIT_PGI = 0x00080000;

    /// Group: `GetPName`
    public static final int GL_INDEX_CLEAR_VALUE = 0x0C20;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_INDEX_LOGIC_OP = 0x0BF1;

    public static final int GL_INDEX_MATERIAL_EXT = 0x81B8;

    public static final int GL_INDEX_MATERIAL_FACE_EXT = 0x81BA;

    public static final int GL_INDEX_MATERIAL_PARAMETER_EXT = 0x81B9;

    /// Group: `GetPName`
    public static final int GL_INDEX_MODE = 0x0C30;

    /// Groups: `PixelTransferParameter`, `IndexMaterialParameterEXT`, `GetPName`
    public static final int GL_INDEX_OFFSET = 0x0D13;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_INDEX_SHIFT = 0x0D12;

    public static final int GL_INDEX_TEST_EXT = 0x81B5;

    public static final int GL_INDEX_TEST_FUNC_EXT = 0x81B6;

    public static final int GL_INDEX_TEST_REF_EXT = 0x81B7;

    /// Group: `GetPName`
    public static final int GL_INDEX_WRITEMASK = 0x0C21;

    /// Groups: `ProgramPropertyARB`, `ShaderParameterName`,
    ///   `PipelineParameterName`
    public static final int GL_INFO_LOG_LENGTH = 0x8B84;

    /// Group: `GraphicsResetStatus`
    public static final int GL_INNOCENT_CONTEXT_RESET = 0x8254;

    public static final int GL_INNOCENT_CONTEXT_RESET_ARB = 0x8254;

    public static final int GL_INNOCENT_CONTEXT_RESET_EXT = 0x8254;

    public static final int GL_INNOCENT_CONTEXT_RESET_KHR = 0x8254;

    /// Group: `GetPointervPName`
    public static final int GL_INSTRUMENT_BUFFER_POINTER_SGIX = 0x8180;

    /// Group: `GetPName`
    public static final int GL_INSTRUMENT_MEASUREMENTS_SGIX = 0x8181;

    /// Groups: `VertexAttribIType`, `SecondaryColorPointerTypeIBM`,
    ///   `WeightPointerTypeARB`, `TangentPointerTypeEXT`, `BinormalPointerTypeEXT`,
    ///   `IndexPointerType`, `ListNameType`, `NormalPointerType`, `PixelType`,
    ///   `TexCoordPointerType`, `VertexPointerType`, `VertexAttribType`,
    ///   `AttributeType`, `UniformType`, `VertexAttribPointerType`
    public static final int GL_INT = 0x1404;

    public static final int GL_INT16_NV = 0x8FE4;

    public static final int GL_INT16_VEC2_NV = 0x8FE5;

    public static final int GL_INT16_VEC3_NV = 0x8FE6;

    public static final int GL_INT16_VEC4_NV = 0x8FE7;

    /// Groups: `VertexAttribPointerType`, `AttributeType`
    public static final int GL_INT64_ARB = 0x140E;

    /// Groups: `VertexAttribPointerType`, `AttributeType`
    public static final int GL_INT64_NV = 0x140E;

    /// Group: `AttributeType`
    public static final int GL_INT64_VEC2_ARB = 0x8FE9;

    public static final int GL_INT64_VEC2_NV = 0x8FE9;

    /// Group: `AttributeType`
    public static final int GL_INT64_VEC3_ARB = 0x8FEA;

    public static final int GL_INT64_VEC3_NV = 0x8FEA;

    /// Group: `AttributeType`
    public static final int GL_INT64_VEC4_ARB = 0x8FEB;

    public static final int GL_INT64_VEC4_NV = 0x8FEB;

    public static final int GL_INT8_NV = 0x8FE0;

    public static final int GL_INT8_VEC2_NV = 0x8FE1;

    public static final int GL_INT8_VEC3_NV = 0x8FE2;

    public static final int GL_INT8_VEC4_NV = 0x8FE3;

    /// Groups: `InternalFormat`, `PathColorFormat`
    public static final int GL_INTENSITY = 0x8049;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY12 = 0x804C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY12_EXT = 0x804C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY16 = 0x804D;

    public static final int GL_INTENSITY16F_ARB = 0x881D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY16I_EXT = 0x8D8B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY16UI_EXT = 0x8D79;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY16_EXT = 0x804D;

    public static final int GL_INTENSITY16_SNORM = 0x901B;

    public static final int GL_INTENSITY32F_ARB = 0x8817;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY32I_EXT = 0x8D85;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY32UI_EXT = 0x8D73;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY4 = 0x804A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY4_EXT = 0x804A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY8 = 0x804B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY8I_EXT = 0x8D91;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY8UI_EXT = 0x8D7F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_INTENSITY8_EXT = 0x804B;

    public static final int GL_INTENSITY8_SNORM = 0x9017;

    public static final int GL_INTENSITY_EXT = 0x8049;

    public static final int GL_INTENSITY_FLOAT16_APPLE = 0x881D;

    public static final int GL_INTENSITY_FLOAT16_ATI = 0x881D;

    public static final int GL_INTENSITY_FLOAT32_APPLE = 0x8817;

    public static final int GL_INTENSITY_FLOAT32_ATI = 0x8817;

    public static final int GL_INTENSITY_SNORM = 0x9013;

    public static final int GL_INTERLACE_OML = 0x8980;

    public static final int GL_INTERLACE_READ_INGR = 0x8568;

    public static final int GL_INTERLACE_READ_OML = 0x8981;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_INTERLACE_SGIX = 0x8094;

    /// Group: `TransformFeedbackBufferMode`
    public static final int GL_INTERLEAVED_ATTRIBS = 0x8C8C;

    public static final int GL_INTERLEAVED_ATTRIBS_EXT = 0x8C8C;

    public static final int GL_INTERLEAVED_ATTRIBS_NV = 0x8C8C;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_ALPHA_SIZE = 0x8274;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_ALPHA_TYPE = 0x827B;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_BLUE_SIZE = 0x8273;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_BLUE_TYPE = 0x827A;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_DEPTH_SIZE = 0x8275;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_DEPTH_TYPE = 0x827C;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_GREEN_SIZE = 0x8272;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_GREEN_TYPE = 0x8279;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_PREFERRED = 0x8270;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_RED_SIZE = 0x8271;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_RED_TYPE = 0x8278;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_SHARED_SIZE = 0x8277;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_STENCIL_SIZE = 0x8276;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_STENCIL_TYPE = 0x827D;

    /// Group: `InternalFormatPName`
    public static final int GL_INTERNALFORMAT_SUPPORTED = 0x826F;

    /// Group: `TextureEnvParameter`
    public static final int GL_INTERPOLATE = 0x8575;

    /// Group: `TextureEnvParameter`
    public static final int GL_INTERPOLATE_ARB = 0x8575;

    /// Group: `TextureEnvParameter`
    public static final int GL_INTERPOLATE_EXT = 0x8575;

    public static final int GL_INT_10_10_10_2_OES = 0x8DF7;

    /// Groups: `VertexAttribPointerType`, `VertexAttribType`
    public static final int GL_INT_2_10_10_10_REV = 0x8D9F;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_1D = 0x9057;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_1D_ARRAY = 0x905D;

    public static final int GL_INT_IMAGE_1D_ARRAY_EXT = 0x905D;

    public static final int GL_INT_IMAGE_1D_EXT = 0x9057;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_2D = 0x9058;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_2D_ARRAY = 0x905E;

    public static final int GL_INT_IMAGE_2D_ARRAY_EXT = 0x905E;

    public static final int GL_INT_IMAGE_2D_EXT = 0x9058;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_2D_MULTISAMPLE = 0x9060;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_2D_MULTISAMPLE_ARRAY = 0x9061;

    public static final int GL_INT_IMAGE_2D_MULTISAMPLE_ARRAY_EXT = 0x9061;

    public static final int GL_INT_IMAGE_2D_MULTISAMPLE_EXT = 0x9060;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_2D_RECT = 0x905A;

    public static final int GL_INT_IMAGE_2D_RECT_EXT = 0x905A;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_3D = 0x9059;

    public static final int GL_INT_IMAGE_3D_EXT = 0x9059;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_BUFFER = 0x905C;

    public static final int GL_INT_IMAGE_BUFFER_EXT = 0x905C;

    public static final int GL_INT_IMAGE_BUFFER_OES = 0x905C;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_CUBE = 0x905B;

    public static final int GL_INT_IMAGE_CUBE_EXT = 0x905B;

    /// Group: `AttributeType`
    public static final int GL_INT_IMAGE_CUBE_MAP_ARRAY = 0x905F;

    public static final int GL_INT_IMAGE_CUBE_MAP_ARRAY_EXT = 0x905F;

    public static final int GL_INT_IMAGE_CUBE_MAP_ARRAY_OES = 0x905F;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_1D = 0x8DC9;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_1D_ARRAY = 0x8DCE;

    public static final int GL_INT_SAMPLER_1D_ARRAY_EXT = 0x8DCE;

    public static final int GL_INT_SAMPLER_1D_EXT = 0x8DC9;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_2D = 0x8DCA;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_2D_ARRAY = 0x8DCF;

    public static final int GL_INT_SAMPLER_2D_ARRAY_EXT = 0x8DCF;

    public static final int GL_INT_SAMPLER_2D_EXT = 0x8DCA;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE = 0x9109;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910C;

    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY_OES = 0x910C;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_2D_RECT = 0x8DCD;

    public static final int GL_INT_SAMPLER_2D_RECT_EXT = 0x8DCD;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_3D = 0x8DCB;

    public static final int GL_INT_SAMPLER_3D_EXT = 0x8DCB;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_BUFFER = 0x8DD0;

    public static final int GL_INT_SAMPLER_BUFFER_AMD = 0x9002;

    public static final int GL_INT_SAMPLER_BUFFER_EXT = 0x8DD0;

    public static final int GL_INT_SAMPLER_BUFFER_OES = 0x8DD0;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_CUBE = 0x8DCC;

    public static final int GL_INT_SAMPLER_CUBE_EXT = 0x8DCC;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY = 0x900E;

    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY_ARB = 0x900E;

    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY_EXT = 0x900E;

    public static final int GL_INT_SAMPLER_CUBE_MAP_ARRAY_OES = 0x900E;

    public static final int GL_INT_SAMPLER_RENDERBUFFER_NV = 0x8E57;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_VEC2 = 0x8B53;

    /// Group: `AttributeType`
    public static final int GL_INT_VEC2_ARB = 0x8B53;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_VEC3 = 0x8B54;

    /// Group: `AttributeType`
    public static final int GL_INT_VEC3_ARB = 0x8B54;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_INT_VEC4 = 0x8B55;

    /// Group: `AttributeType`
    public static final int GL_INT_VEC4_ARB = 0x8B55;

    /// Group: `ErrorCode`
    public static final int GL_INVALID_ENUM = 0x0500;

    /// Group: `ErrorCode`
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION = 0x0506;

    /// Group: `ErrorCode`
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION_EXT = 0x0506;

    /// Group: `ErrorCode`
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION_OES = 0x0506;

    /// Group: `ErrorCode`
    public static final int GL_INVALID_OPERATION = 0x0502;

    /// Group: `ErrorCode`
    public static final int GL_INVALID_VALUE = 0x0501;

    public static final int GL_INVARIANT_DATATYPE_EXT = 0x87EB;

    /// Group: `VertexShaderStorageTypeEXT`
    public static final int GL_INVARIANT_EXT = 0x87C2;

    public static final int GL_INVARIANT_VALUE_EXT = 0x87EA;

    public static final int GL_INVERSE_NV = 0x862B;

    public static final int GL_INVERSE_TRANSPOSE_NV = 0x862D;

    /// Groups: `PathFillMode`, `LogicOp`, `StencilOp`
    public static final int GL_INVERT = 0x150A;

    public static final int GL_INVERTED_SCREEN_W_REND = 0x8491;

    public static final int GL_INVERT_OVG_NV = 0x92B4;

    public static final int GL_INVERT_RGB_NV = 0x92A3;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_IR_INSTRUMENT1_SGIX = 0x817F;

    public static final int GL_ISOLINES = 0x8E7A;

    public static final int GL_ISOLINES_EXT = 0x8E7A;

    public static final int GL_ISOLINES_OES = 0x8E7A;

    /// Group: `ProgramResourceProperty`
    public static final int GL_IS_PER_PATCH = 0x92E7;

    public static final int GL_IS_PER_PATCH_EXT = 0x92E7;

    public static final int GL_IS_PER_PATCH_OES = 0x92E7;

    /// Group: `ProgramResourceProperty`
    public static final int GL_IS_ROW_MAJOR = 0x9300;

    /// Group: `PathFontStyle`
    public static final int GL_ITALIC_BIT_NV = 0x02;

    public static final int GL_IUI_N3F_V2F_EXT = 0x81AF;

    public static final int GL_IUI_N3F_V3F_EXT = 0x81B0;

    public static final int GL_IUI_V2F_EXT = 0x81AD;

    public static final int GL_IUI_V3F_EXT = 0x81AE;

    /// Group: `StencilOp`
    public static final int GL_KEEP = 0x1E00;

    /// Group: `PathCoordType`
    public static final int GL_LARGE_CCW_ARC_TO_NV = 0x16;

    /// Group: `PathCoordType`
    public static final int GL_LARGE_CW_ARC_TO_NV = 0x18;

    /// Group: `VertexProvokingMode`
    public static final int GL_LAST_VERTEX_CONVENTION = 0x8E4E;

    public static final int GL_LAST_VERTEX_CONVENTION_EXT = 0x8E4E;

    public static final int GL_LAST_VERTEX_CONVENTION_OES = 0x8E4E;

    public static final int GL_LAST_VIDEO_CAPTURE_STATUS_NV = 0x9027;

    public static final int GL_LAYER_NV = 0x8DAA;

    /// Group: `GetPName`
    public static final int GL_LAYER_PROVOKING_VERTEX = 0x825E;

    public static final int GL_LAYER_PROVOKING_VERTEX_EXT = 0x825E;

    public static final int GL_LAYER_PROVOKING_VERTEX_OES = 0x825E;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_COLOR_ATTACHMENT_EXT = 0x958E;

    /// Group: `MapTextureFormatINTEL`
    public static final int GL_LAYOUT_DEFAULT_INTEL = 0;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_DEPTH_ATTACHMENT_STENCIL_READ_ONLY_EXT = 0x9531;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_DEPTH_READ_ONLY_STENCIL_ATTACHMENT_EXT = 0x9530;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_DEPTH_STENCIL_ATTACHMENT_EXT = 0x958F;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_DEPTH_STENCIL_READ_ONLY_EXT = 0x9590;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_GENERAL_EXT = 0x958D;

    /// Group: `MapTextureFormatINTEL`
    public static final int GL_LAYOUT_LINEAR_CPU_CACHED_INTEL = 2;

    /// Group: `MapTextureFormatINTEL`
    public static final int GL_LAYOUT_LINEAR_INTEL = 1;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_SHADER_READ_ONLY_EXT = 0x9591;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_TRANSFER_DST_EXT = 0x9593;

    /// Group: `TextureLayout`
    public static final int GL_LAYOUT_TRANSFER_SRC_EXT = 0x9592;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`
    public static final int GL_LEFT = 0x0406;

    /// Groups: `StencilFunction`, `IndexFunctionEXT`, `AlphaFunction`,
    ///   `DepthFunction`
    public static final int GL_LEQUAL = 0x0203;

    /// Group: `FragmentOp3ATI`
    public static final int GL_LERP_ATI = 0x8969;

    /// Groups: `StencilFunction`, `IndexFunctionEXT`, `AlphaFunction`,
    ///   `DepthFunction`
    public static final int GL_LESS = 0x0201;

    /// Group: `BufferStorageMask`
    public static final int GL_LGPU_SEPARATE_STORAGE_BIT_NVX = 0x0800;

    /// Groups: `LightName`, `EnableCap`, `GetPName`
    public static final int GL_LIGHT0 = 0x4000;

    /// Groups: `LightName`, `EnableCap`, `GetPName`
    public static final int GL_LIGHT1 = 0x4001;

    /// Groups: `LightName`, `EnableCap`, `GetPName`
    public static final int GL_LIGHT2 = 0x4002;

    /// Groups: `LightName`, `EnableCap`, `GetPName`
    public static final int GL_LIGHT3 = 0x4003;

    /// Groups: `LightName`, `EnableCap`, `GetPName`
    public static final int GL_LIGHT4 = 0x4004;

    /// Groups: `LightName`, `EnableCap`, `GetPName`
    public static final int GL_LIGHT5 = 0x4005;

    /// Groups: `LightName`, `EnableCap`, `GetPName`
    public static final int GL_LIGHT6 = 0x4006;

    /// Groups: `LightName`, `EnableCap`, `GetPName`
    public static final int GL_LIGHT7 = 0x4007;

    public static final int GL_LIGHTEN = 0x9298;

    public static final int GL_LIGHTEN_KHR = 0x9298;

    public static final int GL_LIGHTEN_NV = 0x9298;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_LIGHTING = 0x0B50;

    /// Group: `AttribMask`
    public static final int GL_LIGHTING_BIT = 0x00000040;

    /// Groups: `LightEnvParameterSGIX`, `GetPName`
    public static final int GL_LIGHT_ENV_MODE_SGIX = 0x8407;

    /// Groups: `LightModelParameter`, `GetPName`
    public static final int GL_LIGHT_MODEL_AMBIENT = 0x0B53;

    /// Groups: `LightModelParameter`, `GetPName`
    public static final int GL_LIGHT_MODEL_COLOR_CONTROL = 0x81F8;

    /// Group: `LightModelParameter`
    public static final int GL_LIGHT_MODEL_COLOR_CONTROL_EXT = 0x81F8;

    /// Groups: `LightModelParameter`, `GetPName`
    public static final int GL_LIGHT_MODEL_LOCAL_VIEWER = 0x0B51;

    public static final int GL_LIGHT_MODEL_SPECULAR_VECTOR_APPLE = 0x85B0;

    /// Groups: `LightModelParameter`, `GetPName`
    public static final int GL_LIGHT_MODEL_TWO_SIDE = 0x0B52;

    /// Groups: `PolygonMode`, `MeshMode1`, `MeshMode2`
    public static final int GL_LINE = 0x1B01;

    /// Groups: `BlitFramebufferFilter`, `FogMode`, `TextureMagFilter`,
    ///   `TextureMinFilter`
    public static final int GL_LINEAR = 0x2601;

    public static final int GL_LINEARBURN_NV = 0x92A5;

    public static final int GL_LINEARDODGE_NV = 0x92A4;

    public static final int GL_LINEARLIGHT_NV = 0x92A7;

    /// Groups: `LightParameter`, `FragmentLightParameterSGIX`
    public static final int GL_LINEAR_ATTENUATION = 0x1208;

    /// Group: `TextureMinFilter`
    public static final int GL_LINEAR_CLIPMAP_LINEAR_SGIX = 0x8170;

    /// Group: `TextureMinFilter`
    public static final int GL_LINEAR_CLIPMAP_NEAREST_SGIX = 0x844F;

    /// Group: `TextureMagFilter`
    public static final int GL_LINEAR_DETAIL_ALPHA_SGIS = 0x8098;

    /// Group: `TextureMagFilter`
    public static final int GL_LINEAR_DETAIL_COLOR_SGIS = 0x8099;

    /// Group: `TextureMagFilter`
    public static final int GL_LINEAR_DETAIL_SGIS = 0x8097;

    /// Groups: `TextureWrapMode`, `TextureMinFilter`
    public static final int GL_LINEAR_MIPMAP_LINEAR = 0x2703;

    /// Group: `TextureMinFilter`
    public static final int GL_LINEAR_MIPMAP_NEAREST = 0x2701;

    /// Group: `TextureMagFilter`
    public static final int GL_LINEAR_SHARPEN_ALPHA_SGIS = 0x80AE;

    /// Group: `TextureMagFilter`
    public static final int GL_LINEAR_SHARPEN_COLOR_SGIS = 0x80AF;

    /// Group: `TextureMagFilter`
    public static final int GL_LINEAR_SHARPEN_SGIS = 0x80AD;

    public static final int GL_LINEAR_TILING_EXT = 0x9585;

    /// Group: `PrimitiveType`
    public static final int GL_LINES = 0x0001;

    /// Group: `PrimitiveType`
    public static final int GL_LINES_ADJACENCY = 0x000A;

    /// Group: `PrimitiveType`
    public static final int GL_LINES_ADJACENCY_ARB = 0x000A;

    /// Group: `PrimitiveType`
    public static final int GL_LINES_ADJACENCY_EXT = 0x000A;

    public static final int GL_LINES_ADJACENCY_OES = 0x000A;

    /// Group: `AttribMask`
    public static final int GL_LINE_BIT = 0x00000004;

    /// Group: `PrimitiveType`
    public static final int GL_LINE_LOOP = 0x0002;

    public static final int GL_LINE_NV = 0x1B01;

    /// Group: `HintTarget`
    public static final int GL_LINE_QUALITY_HINT_SGIX = 0x835B;

    /// Group: `FeedBackToken`
    public static final int GL_LINE_RESET_TOKEN = 0x0707;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_LINE_SMOOTH = 0x0B20;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_LINE_SMOOTH_HINT = 0x0C52;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_LINE_STIPPLE = 0x0B24;

    /// Group: `GetPName`
    public static final int GL_LINE_STIPPLE_PATTERN = 0x0B25;

    /// Group: `GetPName`
    public static final int GL_LINE_STIPPLE_REPEAT = 0x0B26;

    /// Group: `PrimitiveType`
    public static final int GL_LINE_STRIP = 0x0003;

    /// Group: `PrimitiveType`
    public static final int GL_LINE_STRIP_ADJACENCY = 0x000B;

    /// Group: `PrimitiveType`
    public static final int GL_LINE_STRIP_ADJACENCY_ARB = 0x000B;

    /// Group: `PrimitiveType`
    public static final int GL_LINE_STRIP_ADJACENCY_EXT = 0x000B;

    public static final int GL_LINE_STRIP_ADJACENCY_OES = 0x000B;

    /// Group: `FeedBackToken`
    public static final int GL_LINE_TOKEN = 0x0702;

    /// Group: `PathCoordType`
    public static final int GL_LINE_TO_NV = 0x04;

    /// Group: `GetPName`
    public static final int GL_LINE_WIDTH = 0x0B21;

    /// Group: `CommandOpcodesNV`
    public static final int GL_LINE_WIDTH_COMMAND_NV = 0x000D;

    /// Group: `GetPName`
    public static final int GL_LINE_WIDTH_GRANULARITY = 0x0B23;

    /// Group: `GetPName`
    public static final int GL_LINE_WIDTH_RANGE = 0x0B22;

    /// Group: `ProgramPropertyARB`
    public static final int GL_LINK_STATUS = 0x8B82;

    /// Group: `GetPName`
    public static final int GL_LIST_BASE = 0x0B32;

    /// Group: `AttribMask`
    public static final int GL_LIST_BIT = 0x00020000;

    /// Group: `GetPName`
    public static final int GL_LIST_INDEX = 0x0B33;

    /// Group: `GetPName`
    public static final int GL_LIST_MODE = 0x0B30;

    /// Group: `ListParameterName`
    public static final int GL_LIST_PRIORITY_SGIX = 0x8182;

    /// Group: `AccumOp`
    public static final int GL_LOAD = 0x0101;

    public static final int GL_LOCAL_CONSTANT_DATATYPE_EXT = 0x87ED;

    /// Group: `VertexShaderStorageTypeEXT`
    public static final int GL_LOCAL_CONSTANT_EXT = 0x87C3;

    public static final int GL_LOCAL_CONSTANT_VALUE_EXT = 0x87EC;

    /// Group: `VertexShaderStorageTypeEXT`
    public static final int GL_LOCAL_EXT = 0x87C4;

    /// Group: `ProgramResourceProperty`
    public static final int GL_LOCATION = 0x930E;

    /// Group: `ProgramResourceProperty`
    public static final int GL_LOCATION_COMPONENT = 0x934A;

    /// Group: `ProgramResourceProperty`
    public static final int GL_LOCATION_INDEX = 0x930F;

    public static final int GL_LOCATION_INDEX_EXT = 0x930F;

    /// Group: `GetPName`
    public static final int GL_LOGIC_OP = 0x0BF1;

    /// Group: `GetPName`
    public static final int GL_LOGIC_OP_MODE = 0x0BF0;

    public static final int GL_LOSE_CONTEXT_ON_RESET = 0x8252;

    public static final int GL_LOSE_CONTEXT_ON_RESET_ARB = 0x8252;

    public static final int GL_LOSE_CONTEXT_ON_RESET_EXT = 0x8252;

    public static final int GL_LOSE_CONTEXT_ON_RESET_KHR = 0x8252;

    /// Group: `ClipControlOrigin`
    public static final int GL_LOWER_LEFT = 0x8CA1;

    /// Alias: `GL_LOWER_LEFT`
    public static final int GL_LOWER_LEFT_EXT = 0x8CA1;

    /// Group: `PrecisionType`
    public static final int GL_LOW_FLOAT = 0x8DF0;

    /// Group: `PrecisionType`
    public static final int GL_LOW_INT = 0x8DF3;

    public static final int GL_LO_BIAS_NV = 0x8715;

    public static final int GL_LO_SCALE_NV = 0x870F;

    /// Groups: `PathColorFormat`, `PixelFormat`
    public static final int GL_LUMINANCE = 0x1909;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE12 = 0x8041;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE12_ALPHA12 = 0x8047;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE12_ALPHA12_EXT = 0x8047;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE12_ALPHA4 = 0x8046;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE12_ALPHA4_EXT = 0x8046;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE12_EXT = 0x8041;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE16 = 0x8042;

    public static final int GL_LUMINANCE16F_ARB = 0x881E;

    public static final int GL_LUMINANCE16F_EXT = 0x881E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE16I_EXT = 0x8D8C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE16UI_EXT = 0x8D7A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE16_ALPHA16 = 0x8048;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE16_ALPHA16_EXT = 0x8048;

    public static final int GL_LUMINANCE16_ALPHA16_SNORM = 0x901A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE16_EXT = 0x8042;

    public static final int GL_LUMINANCE16_SNORM = 0x9019;

    public static final int GL_LUMINANCE32F_ARB = 0x8818;

    public static final int GL_LUMINANCE32F_EXT = 0x8818;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE32I_EXT = 0x8D86;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE32UI_EXT = 0x8D74;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE4 = 0x803F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE4_ALPHA4 = 0x8043;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE4_ALPHA4_EXT = 0x8043;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE4_ALPHA4_OES = 0x8043;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE4_EXT = 0x803F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE6_ALPHA2 = 0x8044;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE6_ALPHA2_EXT = 0x8044;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE8 = 0x8040;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE8I_EXT = 0x8D92;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE8UI_EXT = 0x8D80;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE8_ALPHA8 = 0x8045;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE8_ALPHA8_EXT = 0x8045;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE8_ALPHA8_OES = 0x8045;

    public static final int GL_LUMINANCE8_ALPHA8_SNORM = 0x9016;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE8_EXT = 0x8040;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE8_OES = 0x8040;

    public static final int GL_LUMINANCE8_SNORM = 0x9015;

    /// Groups: `PathColorFormat`, `PixelFormat`
    public static final int GL_LUMINANCE_ALPHA = 0x190A;

    public static final int GL_LUMINANCE_ALPHA16F_ARB = 0x881F;

    public static final int GL_LUMINANCE_ALPHA16F_EXT = 0x881F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE_ALPHA16I_EXT = 0x8D8D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE_ALPHA16UI_EXT = 0x8D7B;

    public static final int GL_LUMINANCE_ALPHA32F_ARB = 0x8819;

    public static final int GL_LUMINANCE_ALPHA32F_EXT = 0x8819;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE_ALPHA32I_EXT = 0x8D87;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE_ALPHA32UI_EXT = 0x8D75;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE_ALPHA8I_EXT = 0x8D93;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_LUMINANCE_ALPHA8UI_EXT = 0x8D81;

    public static final int GL_LUMINANCE_ALPHA_FLOAT16_APPLE = 0x881F;

    public static final int GL_LUMINANCE_ALPHA_FLOAT16_ATI = 0x881F;

    public static final int GL_LUMINANCE_ALPHA_FLOAT32_APPLE = 0x8819;

    public static final int GL_LUMINANCE_ALPHA_FLOAT32_ATI = 0x8819;

    public static final int GL_LUMINANCE_ALPHA_INTEGER_EXT = 0x8D9D;

    public static final int GL_LUMINANCE_ALPHA_SNORM = 0x9012;

    public static final int GL_LUMINANCE_FLOAT16_APPLE = 0x881E;

    public static final int GL_LUMINANCE_FLOAT16_ATI = 0x881E;

    public static final int GL_LUMINANCE_FLOAT32_APPLE = 0x8818;

    public static final int GL_LUMINANCE_FLOAT32_ATI = 0x8818;

    public static final int GL_LUMINANCE_INTEGER_EXT = 0x8D9C;

    public static final int GL_LUMINANCE_SNORM = 0x9011;

    /// Group: `FragmentOp3ATI`
    public static final int GL_MAD_ATI = 0x8968;

    public static final int GL_MAGNITUDE_BIAS_NV = 0x8718;

    public static final int GL_MAGNITUDE_SCALE_NV = 0x8712;

    /// Group: `GetPName`
    public static final int GL_MAJOR_VERSION = 0x821B;

    public static final int GL_MALI_PROGRAM_BINARY_ARM = 0x8F61;

    /// Group: `ShaderBinaryFormat`
    public static final int GL_MALI_SHADER_BINARY_ARM = 0x8F60;

    public static final int GL_MANUAL_GENERATE_MIPMAP = 0x8294;

    public static final int GL_MAP1_BINORMAL_EXT = 0x8446;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_COLOR_4 = 0x0D90;

    /// Group: `GetPName`
    public static final int GL_MAP1_GRID_DOMAIN = 0x0DD0;

    /// Group: `GetPName`
    public static final int GL_MAP1_GRID_SEGMENTS = 0x0DD1;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_INDEX = 0x0D91;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_NORMAL = 0x0D92;

    public static final int GL_MAP1_TANGENT_EXT = 0x8444;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_TEXTURE_COORD_1 = 0x0D93;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_TEXTURE_COORD_2 = 0x0D94;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_TEXTURE_COORD_3 = 0x0D95;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_TEXTURE_COORD_4 = 0x0D96;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_VERTEX_3 = 0x0D97;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP1_VERTEX_4 = 0x0D98;

    public static final int GL_MAP1_VERTEX_ATTRIB0_4_NV = 0x8660;

    public static final int GL_MAP1_VERTEX_ATTRIB10_4_NV = 0x866A;

    public static final int GL_MAP1_VERTEX_ATTRIB11_4_NV = 0x866B;

    public static final int GL_MAP1_VERTEX_ATTRIB12_4_NV = 0x866C;

    public static final int GL_MAP1_VERTEX_ATTRIB13_4_NV = 0x866D;

    public static final int GL_MAP1_VERTEX_ATTRIB14_4_NV = 0x866E;

    public static final int GL_MAP1_VERTEX_ATTRIB15_4_NV = 0x866F;

    public static final int GL_MAP1_VERTEX_ATTRIB1_4_NV = 0x8661;

    public static final int GL_MAP1_VERTEX_ATTRIB2_4_NV = 0x8662;

    public static final int GL_MAP1_VERTEX_ATTRIB3_4_NV = 0x8663;

    public static final int GL_MAP1_VERTEX_ATTRIB4_4_NV = 0x8664;

    public static final int GL_MAP1_VERTEX_ATTRIB5_4_NV = 0x8665;

    public static final int GL_MAP1_VERTEX_ATTRIB6_4_NV = 0x8666;

    public static final int GL_MAP1_VERTEX_ATTRIB7_4_NV = 0x8667;

    public static final int GL_MAP1_VERTEX_ATTRIB8_4_NV = 0x8668;

    public static final int GL_MAP1_VERTEX_ATTRIB9_4_NV = 0x8669;

    public static final int GL_MAP2_BINORMAL_EXT = 0x8447;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_COLOR_4 = 0x0DB0;

    /// Group: `GetPName`
    public static final int GL_MAP2_GRID_DOMAIN = 0x0DD2;

    /// Group: `GetPName`
    public static final int GL_MAP2_GRID_SEGMENTS = 0x0DD3;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_INDEX = 0x0DB1;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_NORMAL = 0x0DB2;

    public static final int GL_MAP2_TANGENT_EXT = 0x8445;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_TEXTURE_COORD_1 = 0x0DB3;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_TEXTURE_COORD_2 = 0x0DB4;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_TEXTURE_COORD_3 = 0x0DB5;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_TEXTURE_COORD_4 = 0x0DB6;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_VERTEX_3 = 0x0DB7;

    /// Groups: `MapTarget`, `EnableCap`, `GetPName`
    public static final int GL_MAP2_VERTEX_4 = 0x0DB8;

    public static final int GL_MAP2_VERTEX_ATTRIB0_4_NV = 0x8670;

    public static final int GL_MAP2_VERTEX_ATTRIB10_4_NV = 0x867A;

    public static final int GL_MAP2_VERTEX_ATTRIB11_4_NV = 0x867B;

    public static final int GL_MAP2_VERTEX_ATTRIB12_4_NV = 0x867C;

    public static final int GL_MAP2_VERTEX_ATTRIB13_4_NV = 0x867D;

    public static final int GL_MAP2_VERTEX_ATTRIB14_4_NV = 0x867E;

    public static final int GL_MAP2_VERTEX_ATTRIB15_4_NV = 0x867F;

    public static final int GL_MAP2_VERTEX_ATTRIB1_4_NV = 0x8671;

    public static final int GL_MAP2_VERTEX_ATTRIB2_4_NV = 0x8672;

    public static final int GL_MAP2_VERTEX_ATTRIB3_4_NV = 0x8673;

    public static final int GL_MAP2_VERTEX_ATTRIB4_4_NV = 0x8674;

    public static final int GL_MAP2_VERTEX_ATTRIB5_4_NV = 0x8675;

    public static final int GL_MAP2_VERTEX_ATTRIB6_4_NV = 0x8676;

    public static final int GL_MAP2_VERTEX_ATTRIB7_4_NV = 0x8677;

    public static final int GL_MAP2_VERTEX_ATTRIB8_4_NV = 0x8678;

    public static final int GL_MAP2_VERTEX_ATTRIB9_4_NV = 0x8679;

    /// Group: `MapAttribParameterNV`
    public static final int GL_MAP_ATTRIB_U_ORDER_NV = 0x86C3;

    /// Group: `MapAttribParameterNV`
    public static final int GL_MAP_ATTRIB_V_ORDER_NV = 0x86C4;

    /// Groups: `MapBufferAccessMask`, `BufferStorageMask`
    public static final int GL_MAP_COHERENT_BIT = 0x0080;

    /// Groups: `MapBufferAccessMask`, `BufferStorageMask`
    public static final int GL_MAP_COHERENT_BIT_EXT = 0x0080;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_MAP_COLOR = 0x0D10;

    /// Group: `MapBufferAccessMask`
    public static final int GL_MAP_FLUSH_EXPLICIT_BIT = 0x0010;

    /// Group: `MapBufferAccessMask`
    public static final int GL_MAP_FLUSH_EXPLICIT_BIT_EXT = 0x0010;

    /// Group: `MapBufferAccessMask`
    public static final int GL_MAP_INVALIDATE_BUFFER_BIT = 0x0008;

    /// Group: `MapBufferAccessMask`
    public static final int GL_MAP_INVALIDATE_BUFFER_BIT_EXT = 0x0008;

    /// Group: `MapBufferAccessMask`
    public static final int GL_MAP_INVALIDATE_RANGE_BIT = 0x0004;

    /// Group: `MapBufferAccessMask`
    public static final int GL_MAP_INVALIDATE_RANGE_BIT_EXT = 0x0004;

    /// Groups: `MapBufferAccessMask`, `BufferStorageMask`
    public static final int GL_MAP_PERSISTENT_BIT = 0x0040;

    /// Groups: `MapBufferAccessMask`, `BufferStorageMask`
    public static final int GL_MAP_PERSISTENT_BIT_EXT = 0x0040;

    /// Groups: `MapBufferAccessMask`, `BufferStorageMask`
    public static final int GL_MAP_READ_BIT = 0x0001;

    /// Groups: `MapBufferAccessMask`, `BufferStorageMask`
    public static final int GL_MAP_READ_BIT_EXT = 0x0001;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_MAP_STENCIL = 0x0D11;

    /// Group: `MapParameterNV`
    public static final int GL_MAP_TESSELLATION_NV = 0x86C2;

    /// Group: `MapBufferAccessMask`
    public static final int GL_MAP_UNSYNCHRONIZED_BIT = 0x0020;

    /// Group: `MapBufferAccessMask`
    public static final int GL_MAP_UNSYNCHRONIZED_BIT_EXT = 0x0020;

    /// Groups: `MapBufferAccessMask`, `BufferStorageMask`
    public static final int GL_MAP_WRITE_BIT = 0x0002;

    /// Groups: `MapBufferAccessMask`, `BufferStorageMask`
    public static final int GL_MAP_WRITE_BIT_EXT = 0x0002;

    /// Groups: `HintTarget`, `HintTargetPGI`
    public static final int GL_MATERIAL_SIDE_HINT_PGI = 0x1A22C;

    public static final int GL_MATRIX0_ARB = 0x88C0;

    public static final int GL_MATRIX0_NV = 0x8630;

    public static final int GL_MATRIX10_ARB = 0x88CA;

    public static final int GL_MATRIX11_ARB = 0x88CB;

    public static final int GL_MATRIX12_ARB = 0x88CC;

    public static final int GL_MATRIX13_ARB = 0x88CD;

    public static final int GL_MATRIX14_ARB = 0x88CE;

    public static final int GL_MATRIX15_ARB = 0x88CF;

    public static final int GL_MATRIX16_ARB = 0x88D0;

    public static final int GL_MATRIX17_ARB = 0x88D1;

    public static final int GL_MATRIX18_ARB = 0x88D2;

    public static final int GL_MATRIX19_ARB = 0x88D3;

    public static final int GL_MATRIX1_ARB = 0x88C1;

    public static final int GL_MATRIX1_NV = 0x8631;

    public static final int GL_MATRIX20_ARB = 0x88D4;

    public static final int GL_MATRIX21_ARB = 0x88D5;

    public static final int GL_MATRIX22_ARB = 0x88D6;

    public static final int GL_MATRIX23_ARB = 0x88D7;

    public static final int GL_MATRIX24_ARB = 0x88D8;

    public static final int GL_MATRIX25_ARB = 0x88D9;

    public static final int GL_MATRIX26_ARB = 0x88DA;

    public static final int GL_MATRIX27_ARB = 0x88DB;

    public static final int GL_MATRIX28_ARB = 0x88DC;

    public static final int GL_MATRIX29_ARB = 0x88DD;

    public static final int GL_MATRIX2_ARB = 0x88C2;

    public static final int GL_MATRIX2_NV = 0x8632;

    public static final int GL_MATRIX30_ARB = 0x88DE;

    public static final int GL_MATRIX31_ARB = 0x88DF;

    public static final int GL_MATRIX3_ARB = 0x88C3;

    public static final int GL_MATRIX3_NV = 0x8633;

    public static final int GL_MATRIX4_ARB = 0x88C4;

    public static final int GL_MATRIX4_NV = 0x8634;

    public static final int GL_MATRIX5_ARB = 0x88C5;

    public static final int GL_MATRIX5_NV = 0x8635;

    public static final int GL_MATRIX6_ARB = 0x88C6;

    public static final int GL_MATRIX6_NV = 0x8636;

    public static final int GL_MATRIX7_ARB = 0x88C7;

    public static final int GL_MATRIX7_NV = 0x8637;

    public static final int GL_MATRIX8_ARB = 0x88C8;

    public static final int GL_MATRIX9_ARB = 0x88C9;

    /// Group: `DataTypeEXT`
    public static final int GL_MATRIX_EXT = 0x87C0;

    public static final int GL_MATRIX_INDEX_ARRAY_ARB = 0x8844;

    public static final int GL_MATRIX_INDEX_ARRAY_BUFFER_BINDING_OES = 0x8B9E;

    public static final int GL_MATRIX_INDEX_ARRAY_OES = 0x8844;

    public static final int GL_MATRIX_INDEX_ARRAY_POINTER_ARB = 0x8849;

    public static final int GL_MATRIX_INDEX_ARRAY_POINTER_OES = 0x8849;

    public static final int GL_MATRIX_INDEX_ARRAY_SIZE_ARB = 0x8846;

    public static final int GL_MATRIX_INDEX_ARRAY_SIZE_OES = 0x8846;

    public static final int GL_MATRIX_INDEX_ARRAY_STRIDE_ARB = 0x8848;

    public static final int GL_MATRIX_INDEX_ARRAY_STRIDE_OES = 0x8848;

    public static final int GL_MATRIX_INDEX_ARRAY_TYPE_ARB = 0x8847;

    public static final int GL_MATRIX_INDEX_ARRAY_TYPE_OES = 0x8847;

    /// Group: `GetPName`
    public static final int GL_MATRIX_MODE = 0x0BA0;

    public static final int GL_MATRIX_PALETTE_ARB = 0x8840;

    public static final int GL_MATRIX_PALETTE_OES = 0x8840;

    /// Group: `ProgramResourceProperty`
    public static final int GL_MATRIX_STRIDE = 0x92FF;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_MAT_AMBIENT_AND_DIFFUSE_BIT_PGI = 0x00200000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_MAT_AMBIENT_BIT_PGI = 0x00100000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_MAT_COLOR_INDEXES_BIT_PGI = 0x01000000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_MAT_DIFFUSE_BIT_PGI = 0x00400000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_MAT_EMISSION_BIT_PGI = 0x00800000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_MAT_SHININESS_BIT_PGI = 0x02000000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_MAT_SPECULAR_BIT_PGI = 0x04000000;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_MAX = 0x8008;

    /// Group: `GetPName`
    public static final int GL_MAX_3D_TEXTURE_SIZE = 0x8073;

    /// Group: `GetPName`
    public static final int GL_MAX_3D_TEXTURE_SIZE_EXT = 0x8073;

    public static final int GL_MAX_3D_TEXTURE_SIZE_OES = 0x8073;

    /// Group: `GetPName`
    public static final int GL_MAX_4D_TEXTURE_SIZE_SGIS = 0x8138;

    /// Group: `GetPName`
    public static final int GL_MAX_ACTIVE_LIGHTS_SGIX = 0x8405;

    /// Group: `GetPName`
    public static final int GL_MAX_ARRAY_TEXTURE_LAYERS = 0x88FF;

    public static final int GL_MAX_ARRAY_TEXTURE_LAYERS_EXT = 0x88FF;

    /// Group: `GetPName`
    public static final int GL_MAX_ASYNC_DRAW_PIXELS_SGIX = 0x8360;

    /// Group: `GetPName`
    public static final int GL_MAX_ASYNC_HISTOGRAM_SGIX = 0x832D;

    /// Group: `GetPName`
    public static final int GL_MAX_ASYNC_READ_PIXELS_SGIX = 0x8361;

    /// Group: `GetPName`
    public static final int GL_MAX_ASYNC_TEX_IMAGE_SGIX = 0x835F;

    public static final int GL_MAX_ATOMIC_COUNTER_BUFFER_BINDINGS = 0x92DC;

    public static final int GL_MAX_ATOMIC_COUNTER_BUFFER_SIZE = 0x92D8;

    /// Group: `GetPName`
    public static final int GL_MAX_ATTRIB_STACK_DEPTH = 0x0D35;

    public static final int GL_MAX_BINDABLE_UNIFORM_SIZE_EXT = 0x8DED;

    /// Group: `GetPName`
    public static final int GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 0x0D3B;

    /// Group: `GetPName`
    public static final int GL_MAX_CLIPMAP_DEPTH_SGIX = 0x8177;

    /// Group: `GetPName`
    public static final int GL_MAX_CLIPMAP_VIRTUAL_DEPTH_SGIX = 0x8178;

    /// Group: `GetPName`
    /// Alias: `GL_MAX_CLIP_PLANES`
    public static final int GL_MAX_CLIP_DISTANCES = 0x0D32;

    public static final int GL_MAX_CLIP_DISTANCES_APPLE = 0x0D32;

    /// Alias: `GL_MAX_CLIP_PLANES`
    public static final int GL_MAX_CLIP_DISTANCES_EXT = 0x0D32;

    /// Group: `GetPName`
    public static final int GL_MAX_CLIP_PLANES = 0x0D32;

    public static final int GL_MAX_CLIP_PLANES_IMG = 0x0D32;

    public static final int GL_MAX_COARSE_FRAGMENT_SAMPLES_NV = 0x955F;

    /// Group: `GetPName`
    public static final int GL_MAX_COLOR_ATTACHMENTS = 0x8CDF;

    /// Group: `GetPName`
    public static final int GL_MAX_COLOR_ATTACHMENTS_EXT = 0x8CDF;

    /// Group: `GetPName`
    public static final int GL_MAX_COLOR_ATTACHMENTS_NV = 0x8CDF;

    public static final int GL_MAX_COLOR_FRAMEBUFFER_SAMPLES_AMD = 0x91B3;

    public static final int GL_MAX_COLOR_FRAMEBUFFER_STORAGE_SAMPLES_AMD = 0x91B4;

    public static final int GL_MAX_COLOR_MATRIX_STACK_DEPTH = 0x80B3;

    /// Group: `GetPName`
    public static final int GL_MAX_COLOR_MATRIX_STACK_DEPTH_SGI = 0x80B3;

    /// Group: `GetPName`
    public static final int GL_MAX_COLOR_TEXTURE_SAMPLES = 0x910E;

    /// Group: `GetPName`
    public static final int GL_MAX_COMBINED_ATOMIC_COUNTERS = 0x92D7;

    public static final int GL_MAX_COMBINED_ATOMIC_COUNTER_BUFFERS = 0x92D1;

    public static final int GL_MAX_COMBINED_CLIP_AND_CULL_DISTANCES = 0x82FA;

    /// Alias: `GL_MAX_COMBINED_CLIP_AND_CULL_DISTANCES`
    public static final int GL_MAX_COMBINED_CLIP_AND_CULL_DISTANCES_EXT = 0x82FA;

    /// Group: `GetPName`
    public static final int GL_MAX_COMBINED_COMPUTE_UNIFORM_COMPONENTS = 0x8266;

    public static final int GL_MAX_COMBINED_DIMENSIONS = 0x8282;

    /// Group: `GetPName`
    public static final int GL_MAX_COMBINED_FRAGMENT_UNIFORM_COMPONENTS = 0x8A33;

    /// Group: `GetPName`
    public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS = 0x8A32;

    public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS_EXT = 0x8A32;

    public static final int GL_MAX_COMBINED_GEOMETRY_UNIFORM_COMPONENTS_OES = 0x8A32;

    public static final int GL_MAX_COMBINED_IMAGE_UNIFORMS = 0x90CF;

    public static final int GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS = 0x8F39;

    public static final int GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS_EXT = 0x8F39;

    public static final int GL_MAX_COMBINED_MESH_UNIFORM_COMPONENTS_NV = 0x8E67;

    /// Alias: `GL_MAX_COMBINED_IMAGE_UNITS_AND_FRAGMENT_OUTPUTS`
    public static final int GL_MAX_COMBINED_SHADER_OUTPUT_RESOURCES = 0x8F39;

    /// Group: `GetPName`
    public static final int GL_MAX_COMBINED_SHADER_STORAGE_BLOCKS = 0x90DC;

    public static final int GL_MAX_COMBINED_TASK_UNIFORM_COMPONENTS_NV = 0x8E6F;

    public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS = 0x8E1E;

    public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS_EXT = 0x8E1E;

    public static final int GL_MAX_COMBINED_TESS_CONTROL_UNIFORM_COMPONENTS_OES = 0x8E1E;

    public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS = 0x8E1F;

    public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS_EXT = 0x8E1F;

    public static final int GL_MAX_COMBINED_TESS_EVALUATION_UNIFORM_COMPONENTS_OES = 0x8E1F;

    /// Group: `GetPName`
    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D;

    public static final int GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS_ARB = 0x8B4D;

    /// Group: `GetPName`
    public static final int GL_MAX_COMBINED_UNIFORM_BLOCKS = 0x8A2E;

    /// Group: `GetPName`
    public static final int GL_MAX_COMBINED_VERTEX_UNIFORM_COMPONENTS = 0x8A31;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_ATOMIC_COUNTERS = 0x8265;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_ATOMIC_COUNTER_BUFFERS = 0x8264;

    /// Alias: `GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS`
    public static final int GL_MAX_COMPUTE_FIXED_GROUP_INVOCATIONS_ARB = 0x90EB;

    /// Alias: `GL_MAX_COMPUTE_WORK_GROUP_SIZE`
    public static final int GL_MAX_COMPUTE_FIXED_GROUP_SIZE_ARB = 0x91BF;

    public static final int GL_MAX_COMPUTE_IMAGE_UNIFORMS = 0x91BD;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_SHADER_STORAGE_BLOCKS = 0x90DB;

    public static final int GL_MAX_COMPUTE_SHARED_MEMORY_SIZE = 0x8262;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_TEXTURE_IMAGE_UNITS = 0x91BC;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_UNIFORM_BLOCKS = 0x91BB;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_UNIFORM_COMPONENTS = 0x8263;

    public static final int GL_MAX_COMPUTE_VARIABLE_GROUP_INVOCATIONS_ARB = 0x9344;

    public static final int GL_MAX_COMPUTE_VARIABLE_GROUP_SIZE_ARB = 0x9345;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_WORK_GROUP_COUNT = 0x91BE;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_WORK_GROUP_INVOCATIONS = 0x90EB;

    /// Group: `GetPName`
    public static final int GL_MAX_COMPUTE_WORK_GROUP_SIZE = 0x91BF;

    /// Group: `ConvolutionParameter`
    public static final int GL_MAX_CONVOLUTION_HEIGHT = 0x801B;

    /// Group: `ConvolutionParameter`
    public static final int GL_MAX_CONVOLUTION_HEIGHT_EXT = 0x801B;

    /// Group: `ConvolutionParameter`
    public static final int GL_MAX_CONVOLUTION_WIDTH = 0x801A;

    /// Group: `ConvolutionParameter`
    public static final int GL_MAX_CONVOLUTION_WIDTH_EXT = 0x801A;

    /// Group: `GetPName`
    public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C;

    /// Group: `GetPName`
    public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE_ARB = 0x851C;

    /// Group: `GetPName`
    public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE_EXT = 0x851C;

    /// Group: `GetPName`
    public static final int GL_MAX_CUBE_MAP_TEXTURE_SIZE_OES = 0x851C;

    public static final int GL_MAX_CULL_DISTANCES = 0x82F9;

    /// Alias: `GL_MAX_CULL_DISTANCES`
    public static final int GL_MAX_CULL_DISTANCES_EXT = 0x82F9;

    /// Group: `GetPName`
    public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH = 0x826C;

    public static final int GL_MAX_DEBUG_GROUP_STACK_DEPTH_KHR = 0x826C;

    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES = 0x9144;

    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_AMD = 0x9144;

    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_ARB = 0x9144;

    public static final int GL_MAX_DEBUG_LOGGED_MESSAGES_KHR = 0x9144;

    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH = 0x9143;

    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_AMD = 0x9143;

    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_ARB = 0x9143;

    public static final int GL_MAX_DEBUG_MESSAGE_LENGTH_KHR = 0x9143;

    public static final int GL_MAX_DEEP_3D_TEXTURE_DEPTH_NV = 0x90D1;

    public static final int GL_MAX_DEEP_3D_TEXTURE_WIDTH_HEIGHT_NV = 0x90D0;

    public static final int GL_MAX_DEFORMATION_ORDER_SGIX = 0x8197;

    /// Group: `InternalFormatPName`
    public static final int GL_MAX_DEPTH = 0x8280;

    public static final int GL_MAX_DEPTH_STENCIL_FRAMEBUFFER_SAMPLES_AMD = 0x91B5;

    /// Group: `GetPName`
    public static final int GL_MAX_DEPTH_TEXTURE_SAMPLES = 0x910F;

    public static final int GL_MAX_DETACHED_BUFFERS_NV = 0x95AD;

    public static final int GL_MAX_DETACHED_TEXTURES_NV = 0x95AC;

    /// Group: `GetPName`
    public static final int GL_MAX_DRAW_BUFFERS = 0x8824;

    public static final int GL_MAX_DRAW_BUFFERS_ARB = 0x8824;

    public static final int GL_MAX_DRAW_BUFFERS_ATI = 0x8824;

    public static final int GL_MAX_DRAW_BUFFERS_EXT = 0x8824;

    public static final int GL_MAX_DRAW_BUFFERS_NV = 0x8824;

    public static final int GL_MAX_DRAW_MESH_TASKS_COUNT_NV = 0x953D;

    /// Group: `GetPName`
    public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS = 0x88FC;

    public static final int GL_MAX_DUAL_SOURCE_DRAW_BUFFERS_EXT = 0x88FC;

    /// Group: `GetPName`
    public static final int GL_MAX_ELEMENTS_INDICES = 0x80E9;

    public static final int GL_MAX_ELEMENTS_INDICES_EXT = 0x80E9;

    /// Group: `GetPName`
    public static final int GL_MAX_ELEMENTS_VERTICES = 0x80E8;

    public static final int GL_MAX_ELEMENTS_VERTICES_EXT = 0x80E8;

    /// Group: `GetPName`
    public static final int GL_MAX_ELEMENT_INDEX = 0x8D6B;

    /// Group: `GetPName`
    public static final int GL_MAX_EVAL_ORDER = 0x0D30;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_MAX_EXT = 0x8008;

    /// Group: `GetPName`
    public static final int GL_MAX_FOG_FUNC_POINTS_SGIS = 0x812C;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_ATOMIC_COUNTERS = 0x92D6;

    public static final int GL_MAX_FRAGMENT_ATOMIC_COUNTER_BUFFERS = 0x92D0;

    public static final int GL_MAX_FRAGMENT_BINDABLE_UNIFORMS_EXT = 0x8DE3;

    public static final int GL_MAX_FRAGMENT_IMAGE_UNIFORMS = 0x90CE;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 0x9125;

    public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET = 0x8E5C;

    public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET_NV = 0x8E5C;

    public static final int GL_MAX_FRAGMENT_INTERPOLATION_OFFSET_OES = 0x8E5C;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_LIGHTS_SGIX = 0x8404;

    public static final int GL_MAX_FRAGMENT_PROGRAM_LOCAL_PARAMETERS_NV = 0x8868;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_SHADER_STORAGE_BLOCKS = 0x90DA;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_SHADING_RATE_ATTACHMENT_LAYERS_EXT = 0x96DC;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_SHADING_RATE_ATTACHMENT_TEXEL_ASPECT_RATIO_EXT = 0x96DB;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_SHADING_RATE_ATTACHMENT_TEXEL_HEIGHT_EXT = 0x96DA;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_SHADING_RATE_ATTACHMENT_TEXEL_WIDTH_EXT = 0x96D8;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_UNIFORM_BLOCKS = 0x8A2D;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 0x8B49;

    public static final int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS_ARB = 0x8B49;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 0x8DFD;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAMEBUFFER_HEIGHT = 0x9316;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAMEBUFFER_LAYERS = 0x9317;

    public static final int GL_MAX_FRAMEBUFFER_LAYERS_EXT = 0x9317;

    public static final int GL_MAX_FRAMEBUFFER_LAYERS_OES = 0x9317;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAMEBUFFER_SAMPLES = 0x9318;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAMEBUFFER_WIDTH = 0x9315;

    /// Group: `GetPName`
    public static final int GL_MAX_FRAMEZOOM_FACTOR_SGIX = 0x818D;

    public static final int GL_MAX_GENERAL_COMBINERS_NV = 0x854D;

    /// Group: `GetPName`
    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTERS = 0x92D5;

    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTERS_EXT = 0x92D5;

    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTERS_OES = 0x92D5;

    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS = 0x92CF;

    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS_EXT = 0x92CF;

    public static final int GL_MAX_GEOMETRY_ATOMIC_COUNTER_BUFFERS_OES = 0x92CF;

    public static final int GL_MAX_GEOMETRY_BINDABLE_UNIFORMS_EXT = 0x8DE4;

    public static final int GL_MAX_GEOMETRY_IMAGE_UNIFORMS = 0x90CD;

    public static final int GL_MAX_GEOMETRY_IMAGE_UNIFORMS_EXT = 0x90CD;

    public static final int GL_MAX_GEOMETRY_IMAGE_UNIFORMS_OES = 0x90CD;

    /// Group: `GetPName`
    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 0x9123;

    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS_EXT = 0x9123;

    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS_OES = 0x9123;

    /// Group: `GetPName`
    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 0x9124;

    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS_EXT = 0x9124;

    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS_OES = 0x9124;

    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES = 0x8DE0;

    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES_ARB = 0x8DE0;

    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES_EXT = 0x8DE0;

    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES_OES = 0x8DE0;

    public static final int GL_MAX_GEOMETRY_PROGRAM_INVOCATIONS_NV = 0x8E5A;

    public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS = 0x8E5A;

    public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS_EXT = 0x8E5A;

    public static final int GL_MAX_GEOMETRY_SHADER_INVOCATIONS_OES = 0x8E5A;

    /// Group: `GetPName`
    public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS = 0x90D7;

    public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS_EXT = 0x90D7;

    public static final int GL_MAX_GEOMETRY_SHADER_STORAGE_BLOCKS_OES = 0x90D7;

    /// Group: `GetPName`
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 0x8C29;

    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS_ARB = 0x8C29;

    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS_EXT = 0x8C29;

    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS_OES = 0x8C29;

    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 0x8DE1;

    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS_ARB = 0x8DE1;

    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS_EXT = 0x8DE1;

    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS_OES = 0x8DE1;

    /// Group: `GetPName`
    public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS = 0x8A2C;

    public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS_EXT = 0x8A2C;

    public static final int GL_MAX_GEOMETRY_UNIFORM_BLOCKS_OES = 0x8A2C;

    /// Group: `GetPName`
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 0x8DDF;

    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS_ARB = 0x8DDF;

    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS_EXT = 0x8DDF;

    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS_OES = 0x8DDF;

    public static final int GL_MAX_GEOMETRY_VARYING_COMPONENTS_ARB = 0x8DDD;

    public static final int GL_MAX_GEOMETRY_VARYING_COMPONENTS_EXT = 0x8DDD;

    /// Group: `InternalFormatPName`
    public static final int GL_MAX_HEIGHT = 0x827F;

    public static final int GL_MAX_IMAGE_SAMPLES = 0x906D;

    public static final int GL_MAX_IMAGE_SAMPLES_EXT = 0x906D;

    public static final int GL_MAX_IMAGE_UNITS = 0x8F38;

    public static final int GL_MAX_IMAGE_UNITS_EXT = 0x8F38;

    /// Group: `GetPName`
    public static final int GL_MAX_INTEGER_SAMPLES = 0x9110;

    /// Group: `GetPName`
    public static final int GL_MAX_LABEL_LENGTH = 0x82E8;

    public static final int GL_MAX_LABEL_LENGTH_KHR = 0x82E8;

    /// Group: `InternalFormatPName`
    public static final int GL_MAX_LAYERS = 0x8281;

    public static final int GL_MAX_LGPU_GPUS_NVX = 0x92BA;

    /// Group: `GetPName`
    public static final int GL_MAX_LIGHTS = 0x0D31;

    /// Group: `GetPName`
    public static final int GL_MAX_LIST_NESTING = 0x0B31;

    public static final int GL_MAX_MAP_TESSELLATION_NV = 0x86D6;

    public static final int GL_MAX_MATRIX_PALETTE_STACK_DEPTH_ARB = 0x8841;

    public static final int GL_MAX_MESH_ATOMIC_COUNTERS_NV = 0x8E65;

    public static final int GL_MAX_MESH_ATOMIC_COUNTER_BUFFERS_NV = 0x8E64;

    public static final int GL_MAX_MESH_IMAGE_UNIFORMS_NV = 0x8E62;

    public static final int GL_MAX_MESH_OUTPUT_PRIMITIVES_NV = 0x9539;

    public static final int GL_MAX_MESH_OUTPUT_VERTICES_NV = 0x9538;

    public static final int GL_MAX_MESH_SHADER_STORAGE_BLOCKS_NV = 0x8E66;

    public static final int GL_MAX_MESH_TEXTURE_IMAGE_UNITS_NV = 0x8E61;

    public static final int GL_MAX_MESH_TOTAL_MEMORY_SIZE_NV = 0x9536;

    public static final int GL_MAX_MESH_UNIFORM_BLOCKS_NV = 0x8E60;

    public static final int GL_MAX_MESH_UNIFORM_COMPONENTS_NV = 0x8E63;

    public static final int GL_MAX_MESH_VIEWS_NV = 0x9557;

    public static final int GL_MAX_MESH_WORK_GROUP_INVOCATIONS_NV = 0x95A2;

    public static final int GL_MAX_MESH_WORK_GROUP_SIZE_NV = 0x953B;

    /// Group: `GetPName`
    public static final int GL_MAX_MODELVIEW_STACK_DEPTH = 0x0D36;

    public static final int GL_MAX_MULTISAMPLE_COVERAGE_MODES_NV = 0x8E11;

    public static final int GL_MAX_MULTIVIEW_BUFFERS_EXT = 0x90F2;

    /// Group: `ProgramInterfacePName`
    public static final int GL_MAX_NAME_LENGTH = 0x92F6;

    /// Group: `GetPName`
    public static final int GL_MAX_NAME_STACK_DEPTH = 0x0D37;

    /// Group: `ProgramInterfacePName`
    public static final int GL_MAX_NUM_ACTIVE_VARIABLES = 0x92F7;

    /// Group: `ProgramInterfacePName`
    public static final int GL_MAX_NUM_COMPATIBLE_SUBROUTINES = 0x92F8;

    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_INSTRUCTIONS_EXT = 0x87CA;

    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_INVARIANTS_EXT = 0x87CD;

    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_LOCALS_EXT = 0x87CE;

    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 0x87CC;

    public static final int GL_MAX_OPTIMIZED_VERTEX_SHADER_VARIANTS_EXT = 0x87CB;

    public static final int GL_MAX_PALETTE_MATRICES_ARB = 0x8842;

    public static final int GL_MAX_PALETTE_MATRICES_OES = 0x8842;

    public static final int GL_MAX_PATCH_VERTICES = 0x8E7D;

    public static final int GL_MAX_PATCH_VERTICES_EXT = 0x8E7D;

    public static final int GL_MAX_PATCH_VERTICES_OES = 0x8E7D;

    /// Group: `GetPName`
    public static final int GL_MAX_PIXEL_MAP_TABLE = 0x0D34;

    public static final int GL_MAX_PIXEL_TRANSFORM_2D_STACK_DEPTH_EXT = 0x8337;

    public static final int GL_MAX_PN_TRIANGLES_TESSELATION_LEVEL_ATI = 0x87F1;

    public static final int GL_MAX_PROGRAM_ADDRESS_REGISTERS_ARB = 0x88B1;

    public static final int GL_MAX_PROGRAM_ALU_INSTRUCTIONS_ARB = 0x880B;

    public static final int GL_MAX_PROGRAM_ATTRIBS_ARB = 0x88AD;

    public static final int GL_MAX_PROGRAM_ATTRIB_COMPONENTS_NV = 0x8908;

    public static final int GL_MAX_PROGRAM_CALL_DEPTH_NV = 0x88F5;

    public static final int GL_MAX_PROGRAM_ENV_PARAMETERS_ARB = 0x88B5;

    public static final int GL_MAX_PROGRAM_EXEC_INSTRUCTIONS_NV = 0x88F4;

    public static final int GL_MAX_PROGRAM_GENERIC_ATTRIBS_NV = 0x8DA5;

    public static final int GL_MAX_PROGRAM_GENERIC_RESULTS_NV = 0x8DA6;

    public static final int GL_MAX_PROGRAM_IF_DEPTH_NV = 0x88F6;

    public static final int GL_MAX_PROGRAM_INSTRUCTIONS_ARB = 0x88A1;

    public static final int GL_MAX_PROGRAM_LOCAL_PARAMETERS_ARB = 0x88B4;

    public static final int GL_MAX_PROGRAM_LOOP_COUNT_NV = 0x88F8;

    public static final int GL_MAX_PROGRAM_LOOP_DEPTH_NV = 0x88F7;

    public static final int GL_MAX_PROGRAM_MATRICES_ARB = 0x862F;

    public static final int GL_MAX_PROGRAM_MATRIX_STACK_DEPTH_ARB = 0x862E;

    public static final int GL_MAX_PROGRAM_NATIVE_ADDRESS_REGISTERS_ARB = 0x88B3;

    public static final int GL_MAX_PROGRAM_NATIVE_ALU_INSTRUCTIONS_ARB = 0x880E;

    public static final int GL_MAX_PROGRAM_NATIVE_ATTRIBS_ARB = 0x88AF;

    public static final int GL_MAX_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 0x88A3;

    public static final int GL_MAX_PROGRAM_NATIVE_PARAMETERS_ARB = 0x88AB;

    public static final int GL_MAX_PROGRAM_NATIVE_TEMPORARIES_ARB = 0x88A7;

    public static final int GL_MAX_PROGRAM_NATIVE_TEX_INDIRECTIONS_ARB = 0x8810;

    public static final int GL_MAX_PROGRAM_NATIVE_TEX_INSTRUCTIONS_ARB = 0x880F;

    public static final int GL_MAX_PROGRAM_OUTPUT_VERTICES_NV = 0x8C27;

    public static final int GL_MAX_PROGRAM_PARAMETERS_ARB = 0x88A9;

    public static final int GL_MAX_PROGRAM_PARAMETER_BUFFER_BINDINGS_NV = 0x8DA0;

    public static final int GL_MAX_PROGRAM_PARAMETER_BUFFER_SIZE_NV = 0x8DA1;

    public static final int GL_MAX_PROGRAM_PATCH_ATTRIBS_NV = 0x86D8;

    public static final int GL_MAX_PROGRAM_RESULT_COMPONENTS_NV = 0x8909;

    public static final int GL_MAX_PROGRAM_SUBROUTINE_NUM_NV = 0x8F45;

    public static final int GL_MAX_PROGRAM_SUBROUTINE_PARAMETERS_NV = 0x8F44;

    public static final int GL_MAX_PROGRAM_TEMPORARIES_ARB = 0x88A5;

    /// Group: `GetPName`
    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET = 0x8905;

    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET_EXT = 0x8905;

    public static final int GL_MAX_PROGRAM_TEXEL_OFFSET_NV = 0x8905;

    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_COMPONENTS_ARB = 0x8F9F;

    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET = 0x8E5F;

    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET_ARB = 0x8E5F;

    public static final int GL_MAX_PROGRAM_TEXTURE_GATHER_OFFSET_NV = 0x8E5F;

    public static final int GL_MAX_PROGRAM_TEX_INDIRECTIONS_ARB = 0x880D;

    public static final int GL_MAX_PROGRAM_TEX_INSTRUCTIONS_ARB = 0x880C;

    public static final int GL_MAX_PROGRAM_TOTAL_OUTPUT_COMPONENTS_NV = 0x8C28;

    /// Group: `GetPName`
    public static final int GL_MAX_PROJECTION_STACK_DEPTH = 0x0D38;

    public static final int GL_MAX_RASTER_SAMPLES_EXT = 0x9329;

    public static final int GL_MAX_RATIONAL_EVAL_ORDER_NV = 0x86D7;

    /// Group: `GetPName`
    public static final int GL_MAX_RECTANGLE_TEXTURE_SIZE = 0x84F8;

    public static final int GL_MAX_RECTANGLE_TEXTURE_SIZE_ARB = 0x84F8;

    public static final int GL_MAX_RECTANGLE_TEXTURE_SIZE_NV = 0x84F8;

    /// Group: `GetPName`
    public static final int GL_MAX_RENDERBUFFER_SIZE = 0x84E8;

    public static final int GL_MAX_RENDERBUFFER_SIZE_EXT = 0x84E8;

    public static final int GL_MAX_RENDERBUFFER_SIZE_OES = 0x84E8;

    public static final int GL_MAX_SAMPLES = 0x8D57;

    public static final int GL_MAX_SAMPLES_ANGLE = 0x8D57;

    public static final int GL_MAX_SAMPLES_APPLE = 0x8D57;

    public static final int GL_MAX_SAMPLES_EXT = 0x8D57;

    public static final int GL_MAX_SAMPLES_IMG = 0x9135;

    public static final int GL_MAX_SAMPLES_NV = 0x8D57;

    /// Group: `GetPName`
    public static final int GL_MAX_SAMPLE_MASK_WORDS = 0x8E59;

    public static final int GL_MAX_SAMPLE_MASK_WORDS_NV = 0x8E59;

    /// Group: `GetPName`
    public static final int GL_MAX_SERVER_WAIT_TIMEOUT = 0x9111;

    public static final int GL_MAX_SERVER_WAIT_TIMEOUT_APPLE = 0x9111;

    public static final int GL_MAX_SHADER_BUFFER_ADDRESS_NV = 0x8F35;

    public static final int GL_MAX_SHADER_COMBINED_LOCAL_STORAGE_FAST_SIZE_EXT = 0x9650;

    public static final int GL_MAX_SHADER_COMBINED_LOCAL_STORAGE_SIZE_EXT = 0x9651;

    /// Alias: `GL_MAX_SHADER_COMPILER_THREADS_KHR`
    public static final int GL_MAX_SHADER_COMPILER_THREADS_ARB = 0x91B0;

    public static final int GL_MAX_SHADER_COMPILER_THREADS_KHR = 0x91B0;

    public static final int GL_MAX_SHADER_PIXEL_LOCAL_STORAGE_FAST_SIZE_EXT = 0x8F63;

    public static final int GL_MAX_SHADER_PIXEL_LOCAL_STORAGE_SIZE_EXT = 0x8F67;

    public static final int GL_MAX_SHADER_STORAGE_BLOCK_SIZE = 0x90DE;

    /// Group: `GetPName`
    public static final int GL_MAX_SHADER_STORAGE_BUFFER_BINDINGS = 0x90DD;

    public static final int GL_MAX_SHADER_SUBSAMPLED_IMAGE_UNITS_QCOM = 0x8FA1;

    public static final int GL_MAX_SHININESS_NV = 0x8504;

    public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_AMD = 0x9199;

    public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_ARB = 0x9199;

    public static final int GL_MAX_SPARSE_3D_TEXTURE_SIZE_EXT = 0x9199;

    public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS = 0x919A;

    public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS_ARB = 0x919A;

    public static final int GL_MAX_SPARSE_ARRAY_TEXTURE_LAYERS_EXT = 0x919A;

    public static final int GL_MAX_SPARSE_TEXTURE_SIZE_AMD = 0x9198;

    public static final int GL_MAX_SPARSE_TEXTURE_SIZE_ARB = 0x9198;

    public static final int GL_MAX_SPARSE_TEXTURE_SIZE_EXT = 0x9198;

    public static final int GL_MAX_SPOT_EXPONENT_NV = 0x8505;

    public static final int GL_MAX_SUBPIXEL_PRECISION_BIAS_BITS_NV = 0x9349;

    public static final int GL_MAX_SUBROUTINES = 0x8DE7;

    public static final int GL_MAX_SUBROUTINE_UNIFORM_LOCATIONS = 0x8DE8;

    public static final int GL_MAX_TASK_ATOMIC_COUNTERS_NV = 0x8E6D;

    public static final int GL_MAX_TASK_ATOMIC_COUNTER_BUFFERS_NV = 0x8E6C;

    public static final int GL_MAX_TASK_IMAGE_UNIFORMS_NV = 0x8E6A;

    public static final int GL_MAX_TASK_OUTPUT_COUNT_NV = 0x953A;

    public static final int GL_MAX_TASK_SHADER_STORAGE_BLOCKS_NV = 0x8E6E;

    public static final int GL_MAX_TASK_TEXTURE_IMAGE_UNITS_NV = 0x8E69;

    public static final int GL_MAX_TASK_TOTAL_MEMORY_SIZE_NV = 0x9537;

    public static final int GL_MAX_TASK_UNIFORM_BLOCKS_NV = 0x8E68;

    public static final int GL_MAX_TASK_UNIFORM_COMPONENTS_NV = 0x8E6B;

    public static final int GL_MAX_TASK_WORK_GROUP_INVOCATIONS_NV = 0x95A3;

    public static final int GL_MAX_TASK_WORK_GROUP_SIZE_NV = 0x953C;

    /// Group: `GetPName`
    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS = 0x92D3;

    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS_EXT = 0x92D3;

    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTERS_OES = 0x92D3;

    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS = 0x92CD;

    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS_EXT = 0x92CD;

    public static final int GL_MAX_TESS_CONTROL_ATOMIC_COUNTER_BUFFERS_OES = 0x92CD;

    public static final int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS = 0x90CB;

    public static final int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS_EXT = 0x90CB;

    public static final int GL_MAX_TESS_CONTROL_IMAGE_UNIFORMS_OES = 0x90CB;

    public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS = 0x886C;

    public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS_EXT = 0x886C;

    public static final int GL_MAX_TESS_CONTROL_INPUT_COMPONENTS_OES = 0x886C;

    public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS = 0x8E83;

    public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS_EXT = 0x8E83;

    public static final int GL_MAX_TESS_CONTROL_OUTPUT_COMPONENTS_OES = 0x8E83;

    /// Group: `GetPName`
    public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS = 0x90D8;

    public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS_EXT = 0x90D8;

    public static final int GL_MAX_TESS_CONTROL_SHADER_STORAGE_BLOCKS_OES = 0x90D8;

    public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS = 0x8E81;

    public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS_EXT = 0x8E81;

    public static final int GL_MAX_TESS_CONTROL_TEXTURE_IMAGE_UNITS_OES = 0x8E81;

    public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS = 0x8E85;

    public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS_EXT = 0x8E85;

    public static final int GL_MAX_TESS_CONTROL_TOTAL_OUTPUT_COMPONENTS_OES = 0x8E85;

    /// Group: `GetPName`
    public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS = 0x8E89;

    public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS_EXT = 0x8E89;

    public static final int GL_MAX_TESS_CONTROL_UNIFORM_BLOCKS_OES = 0x8E89;

    public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS = 0x8E7F;

    public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS_EXT = 0x8E7F;

    public static final int GL_MAX_TESS_CONTROL_UNIFORM_COMPONENTS_OES = 0x8E7F;

    /// Group: `GetPName`
    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS = 0x92D4;

    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS_EXT = 0x92D4;

    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTERS_OES = 0x92D4;

    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS = 0x92CE;

    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS_EXT = 0x92CE;

    public static final int GL_MAX_TESS_EVALUATION_ATOMIC_COUNTER_BUFFERS_OES = 0x92CE;

    public static final int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS = 0x90CC;

    public static final int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS_EXT = 0x90CC;

    public static final int GL_MAX_TESS_EVALUATION_IMAGE_UNIFORMS_OES = 0x90CC;

    public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS = 0x886D;

    public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS_EXT = 0x886D;

    public static final int GL_MAX_TESS_EVALUATION_INPUT_COMPONENTS_OES = 0x886D;

    public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS = 0x8E86;

    public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS_EXT = 0x8E86;

    public static final int GL_MAX_TESS_EVALUATION_OUTPUT_COMPONENTS_OES = 0x8E86;

    /// Group: `GetPName`
    public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS = 0x90D9;

    public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS_EXT = 0x90D9;

    public static final int GL_MAX_TESS_EVALUATION_SHADER_STORAGE_BLOCKS_OES = 0x90D9;

    public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS = 0x8E82;

    public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS_EXT = 0x8E82;

    public static final int GL_MAX_TESS_EVALUATION_TEXTURE_IMAGE_UNITS_OES = 0x8E82;

    /// Group: `GetPName`
    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS = 0x8E8A;

    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS_EXT = 0x8E8A;

    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_BLOCKS_OES = 0x8E8A;

    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS = 0x8E80;

    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS_EXT = 0x8E80;

    public static final int GL_MAX_TESS_EVALUATION_UNIFORM_COMPONENTS_OES = 0x8E80;

    public static final int GL_MAX_TESS_GEN_LEVEL = 0x8E7E;

    public static final int GL_MAX_TESS_GEN_LEVEL_EXT = 0x8E7E;

    public static final int GL_MAX_TESS_GEN_LEVEL_OES = 0x8E7E;

    public static final int GL_MAX_TESS_PATCH_COMPONENTS = 0x8E84;

    public static final int GL_MAX_TESS_PATCH_COMPONENTS_EXT = 0x8E84;

    public static final int GL_MAX_TESS_PATCH_COMPONENTS_OES = 0x8E84;

    /// Group: `GetPName`
    public static final int GL_MAX_TEXTURE_BUFFER_SIZE = 0x8C2B;

    public static final int GL_MAX_TEXTURE_BUFFER_SIZE_ARB = 0x8C2B;

    public static final int GL_MAX_TEXTURE_BUFFER_SIZE_EXT = 0x8C2B;

    public static final int GL_MAX_TEXTURE_BUFFER_SIZE_OES = 0x8C2B;

    public static final int GL_MAX_TEXTURE_COORDS = 0x8871;

    public static final int GL_MAX_TEXTURE_COORDS_ARB = 0x8871;

    public static final int GL_MAX_TEXTURE_COORDS_NV = 0x8871;

    /// Group: `GetPName`
    public static final int GL_MAX_TEXTURE_IMAGE_UNITS = 0x8872;

    public static final int GL_MAX_TEXTURE_IMAGE_UNITS_ARB = 0x8872;

    public static final int GL_MAX_TEXTURE_IMAGE_UNITS_NV = 0x8872;

    /// Group: `GetPName`
    public static final int GL_MAX_TEXTURE_LOD_BIAS = 0x84FD;

    public static final int GL_MAX_TEXTURE_LOD_BIAS_EXT = 0x84FD;

    public static final int GL_MAX_TEXTURE_MAX_ANISOTROPY = 0x84FF;

    /// Alias: `GL_MAX_TEXTURE_MAX_ANISOTROPY`
    public static final int GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT = 0x84FF;

    /// Group: `GetPName`
    public static final int GL_MAX_TEXTURE_SIZE = 0x0D33;

    /// Group: `GetPName`
    public static final int GL_MAX_TEXTURE_STACK_DEPTH = 0x0D39;

    public static final int GL_MAX_TEXTURE_UNITS = 0x84E2;

    public static final int GL_MAX_TEXTURE_UNITS_ARB = 0x84E2;

    /// Group: `GetPName`
    public static final int GL_MAX_TIMELINE_SEMAPHORE_VALUE_DIFFERENCE_NV = 0x95B6;

    public static final int GL_MAX_TRACK_MATRICES_NV = 0x862F;

    public static final int GL_MAX_TRACK_MATRIX_STACK_DEPTH_NV = 0x862E;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_BUFFERS = 0x8E70;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS = 0x8C8A;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS_EXT = 0x8C8A;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_INTERLEAVED_COMPONENTS_NV = 0x8C8A;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS = 0x8C8B;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS_EXT = 0x8C8B;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_ATTRIBS_NV = 0x8C8B;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS = 0x8C80;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS_EXT = 0x8C80;

    public static final int GL_MAX_TRANSFORM_FEEDBACK_SEPARATE_COMPONENTS_NV = 0x8C80;

    /// Group: `GetPName`
    public static final int GL_MAX_UNIFORM_BLOCK_SIZE = 0x8A30;

    /// Group: `GetPName`
    public static final int GL_MAX_UNIFORM_BUFFER_BINDINGS = 0x8A2F;

    /// Group: `GetPName`
    public static final int GL_MAX_UNIFORM_LOCATIONS = 0x826E;

    /// Group: `GetPName`
    /// Alias: `GL_MAX_VARYING_FLOATS`
    public static final int GL_MAX_VARYING_COMPONENTS = 0x8B4B;

    public static final int GL_MAX_VARYING_COMPONENTS_EXT = 0x8B4B;

    /// Group: `GetPName`
    public static final int GL_MAX_VARYING_FLOATS = 0x8B4B;

    public static final int GL_MAX_VARYING_FLOATS_ARB = 0x8B4B;

    /// Group: `GetPName`
    public static final int GL_MAX_VARYING_VECTORS = 0x8DFC;

    public static final int GL_MAX_VERTEX_ARRAY_RANGE_ELEMENT_NV = 0x8520;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_ATOMIC_COUNTERS = 0x92D2;

    public static final int GL_MAX_VERTEX_ATOMIC_COUNTER_BUFFERS = 0x92CC;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_ATTRIBS = 0x8869;

    public static final int GL_MAX_VERTEX_ATTRIBS_ARB = 0x8869;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_ATTRIB_BINDINGS = 0x82DA;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_ATTRIB_RELATIVE_OFFSET = 0x82D9;

    public static final int GL_MAX_VERTEX_ATTRIB_STRIDE = 0x82E5;

    public static final int GL_MAX_VERTEX_BINDABLE_UNIFORMS_EXT = 0x8DE2;

    /// Groups: `HintTarget`, `HintTargetPGI`
    public static final int GL_MAX_VERTEX_HINT_PGI = 0x1A22D;

    public static final int GL_MAX_VERTEX_IMAGE_UNIFORMS = 0x90CA;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 0x9122;

    public static final int GL_MAX_VERTEX_SHADER_INSTRUCTIONS_EXT = 0x87C5;

    public static final int GL_MAX_VERTEX_SHADER_INVARIANTS_EXT = 0x87C7;

    public static final int GL_MAX_VERTEX_SHADER_LOCALS_EXT = 0x87C9;

    public static final int GL_MAX_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 0x87C8;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_SHADER_STORAGE_BLOCKS = 0x90D6;

    public static final int GL_MAX_VERTEX_SHADER_VARIANTS_EXT = 0x87C6;

    public static final int GL_MAX_VERTEX_STREAMS = 0x8E71;

    public static final int GL_MAX_VERTEX_STREAMS_ATI = 0x876B;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS = 0x8B4C;

    public static final int GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS_ARB = 0x8B4C;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_UNIFORM_BLOCKS = 0x8A2B;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS = 0x8B4A;

    public static final int GL_MAX_VERTEX_UNIFORM_COMPONENTS_ARB = 0x8B4A;

    /// Group: `GetPName`
    public static final int GL_MAX_VERTEX_UNIFORM_VECTORS = 0x8DFB;

    public static final int GL_MAX_VERTEX_UNITS_ARB = 0x86A4;

    public static final int GL_MAX_VERTEX_UNITS_OES = 0x86A4;

    public static final int GL_MAX_VERTEX_VARYING_COMPONENTS_ARB = 0x8DDE;

    public static final int GL_MAX_VERTEX_VARYING_COMPONENTS_EXT = 0x8DDE;

    /// Group: `GetPName`
    public static final int GL_MAX_VIEWPORTS = 0x825B;

    public static final int GL_MAX_VIEWPORTS_NV = 0x825B;

    public static final int GL_MAX_VIEWPORTS_OES = 0x825B;

    /// Group: `GetPName`
    public static final int GL_MAX_VIEWPORT_DIMS = 0x0D3A;

    public static final int GL_MAX_VIEWS_OVR = 0x9631;

    /// Group: `InternalFormatPName`
    public static final int GL_MAX_WIDTH = 0x827E;

    public static final int GL_MAX_WINDOW_RECTANGLES_EXT = 0x8F14;

    /// Group: `PrecisionType`
    public static final int GL_MEDIUM_FLOAT = 0x8DF1;

    /// Group: `PrecisionType`
    public static final int GL_MEDIUM_INT = 0x8DF4;

    public static final int GL_MEMORY_ATTACHABLE_ALIGNMENT_NV = 0x95A6;

    public static final int GL_MEMORY_ATTACHABLE_NV = 0x95A8;

    public static final int GL_MEMORY_ATTACHABLE_SIZE_NV = 0x95A7;

    public static final int GL_MESH_OUTPUT_PER_PRIMITIVE_GRANULARITY_NV = 0x9543;

    public static final int GL_MESH_OUTPUT_PER_VERTEX_GRANULARITY_NV = 0x92DF;

    public static final int GL_MESH_OUTPUT_TYPE_NV = 0x957B;

    public static final int GL_MESH_PRIMITIVES_OUT_NV = 0x957A;

    /// Group: `UseProgramStageMask`
    public static final int GL_MESH_SHADER_BIT_NV = 0x00000040;

    public static final int GL_MESH_SHADER_NV = 0x9559;

    public static final int GL_MESH_SUBROUTINE_NV = 0x957C;

    public static final int GL_MESH_SUBROUTINE_UNIFORM_NV = 0x957E;

    public static final int GL_MESH_VERTICES_OUT_NV = 0x9579;

    public static final int GL_MESH_WORK_GROUP_SIZE_NV = 0x953E;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_MIN = 0x8007;

    /// Groups: `MinmaxTarget`, `MinmaxTargetEXT`
    public static final int GL_MINMAX = 0x802E;

    /// Groups: `MinmaxTargetEXT`, `EnableCap`, `GetPName`
    public static final int GL_MINMAX_EXT = 0x802E;

    /// Group: `GetMinmaxParameterPNameEXT`
    public static final int GL_MINMAX_FORMAT = 0x802F;

    /// Group: `GetMinmaxParameterPNameEXT`
    public static final int GL_MINMAX_FORMAT_EXT = 0x802F;

    /// Group: `GetMinmaxParameterPNameEXT`
    public static final int GL_MINMAX_SINK = 0x8030;

    /// Group: `GetMinmaxParameterPNameEXT`
    public static final int GL_MINMAX_SINK_EXT = 0x8030;

    /// Group: `GetPName`
    public static final int GL_MINOR_VERSION = 0x821C;

    public static final int GL_MINUS_CLAMPED_NV = 0x92B3;

    public static final int GL_MINUS_NV = 0x929F;

    /// Group: `BlendEquationModeEXT`
    public static final int GL_MIN_EXT = 0x8007;

    public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET = 0x8E5B;

    public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET_NV = 0x8E5B;

    public static final int GL_MIN_FRAGMENT_INTERPOLATION_OFFSET_OES = 0x8E5B;

    /// Group: `GetPName`
    public static final int GL_MIN_FRAGMENT_SHADING_RATE_ATTACHMENT_TEXEL_HEIGHT_EXT = 0x96D9;

    /// Group: `GetPName`
    public static final int GL_MIN_FRAGMENT_SHADING_RATE_ATTACHMENT_TEXEL_WIDTH_EXT = 0x96D7;

    public static final int GL_MIN_LOD_WARNING_AMD = 0x919C;

    /// Group: `GetPName`
    public static final int GL_MIN_MAP_BUFFER_ALIGNMENT = 0x90BC;

    /// Group: `GetPName`
    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET = 0x8904;

    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET_EXT = 0x8904;

    public static final int GL_MIN_PROGRAM_TEXEL_OFFSET_NV = 0x8904;

    public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET = 0x8E5E;

    public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET_ARB = 0x8E5E;

    public static final int GL_MIN_PROGRAM_TEXTURE_GATHER_OFFSET_NV = 0x8E5E;

    public static final int GL_MIN_SAMPLE_SHADING_VALUE = 0x8C37;

    public static final int GL_MIN_SAMPLE_SHADING_VALUE_ARB = 0x8C37;

    public static final int GL_MIN_SAMPLE_SHADING_VALUE_OES = 0x8C37;

    public static final int GL_MIN_SPARSE_LEVEL_AMD = 0x919B;

    /// Group: `InternalFormatPName`
    public static final int GL_MIPMAP = 0x8293;

    /// Group: `TextureWrapMode`
    public static final int GL_MIRRORED_REPEAT = 0x8370;

    public static final int GL_MIRRORED_REPEAT_ARB = 0x8370;

    public static final int GL_MIRRORED_REPEAT_IBM = 0x8370;

    public static final int GL_MIRRORED_REPEAT_OES = 0x8370;

    public static final int GL_MIRROR_CLAMP_ATI = 0x8742;

    public static final int GL_MIRROR_CLAMP_EXT = 0x8742;

    public static final int GL_MIRROR_CLAMP_TO_BORDER_EXT = 0x8912;

    public static final int GL_MIRROR_CLAMP_TO_EDGE = 0x8743;

    public static final int GL_MIRROR_CLAMP_TO_EDGE_ATI = 0x8743;

    public static final int GL_MIRROR_CLAMP_TO_EDGE_EXT = 0x8743;

    public static final int GL_MITER_REVERT_NV = 0x90A7;

    public static final int GL_MITER_TRUNCATE_NV = 0x90A8;

    public static final int GL_MIXED_DEPTH_SAMPLES_SUPPORTED_NV = 0x932F;

    public static final int GL_MIXED_STENCIL_SAMPLES_SUPPORTED_NV = 0x9330;

    /// Group: `MatrixMode`
    public static final int GL_MODELVIEW = 0x1700;

    public static final int GL_MODELVIEW0_ARB = 0x1700;

    /// Group: `MatrixMode`
    public static final int GL_MODELVIEW0_EXT = 0x1700;

    /// Group: `GetPName`
    public static final int GL_MODELVIEW0_MATRIX_EXT = 0x0BA6;

    /// Group: `GetPName`
    public static final int GL_MODELVIEW0_STACK_DEPTH_EXT = 0x0BA3;

    public static final int GL_MODELVIEW10_ARB = 0x872A;

    public static final int GL_MODELVIEW11_ARB = 0x872B;

    public static final int GL_MODELVIEW12_ARB = 0x872C;

    public static final int GL_MODELVIEW13_ARB = 0x872D;

    public static final int GL_MODELVIEW14_ARB = 0x872E;

    public static final int GL_MODELVIEW15_ARB = 0x872F;

    public static final int GL_MODELVIEW16_ARB = 0x8730;

    public static final int GL_MODELVIEW17_ARB = 0x8731;

    public static final int GL_MODELVIEW18_ARB = 0x8732;

    public static final int GL_MODELVIEW19_ARB = 0x8733;

    public static final int GL_MODELVIEW1_ARB = 0x850A;

    public static final int GL_MODELVIEW1_EXT = 0x850A;

    public static final int GL_MODELVIEW1_MATRIX_EXT = 0x8506;

    public static final int GL_MODELVIEW1_STACK_DEPTH_EXT = 0x8502;

    public static final int GL_MODELVIEW20_ARB = 0x8734;

    public static final int GL_MODELVIEW21_ARB = 0x8735;

    public static final int GL_MODELVIEW22_ARB = 0x8736;

    public static final int GL_MODELVIEW23_ARB = 0x8737;

    public static final int GL_MODELVIEW24_ARB = 0x8738;

    public static final int GL_MODELVIEW25_ARB = 0x8739;

    public static final int GL_MODELVIEW26_ARB = 0x873A;

    public static final int GL_MODELVIEW27_ARB = 0x873B;

    public static final int GL_MODELVIEW28_ARB = 0x873C;

    public static final int GL_MODELVIEW29_ARB = 0x873D;

    public static final int GL_MODELVIEW2_ARB = 0x8722;

    public static final int GL_MODELVIEW30_ARB = 0x873E;

    public static final int GL_MODELVIEW31_ARB = 0x873F;

    public static final int GL_MODELVIEW3_ARB = 0x8723;

    public static final int GL_MODELVIEW4_ARB = 0x8724;

    public static final int GL_MODELVIEW5_ARB = 0x8725;

    public static final int GL_MODELVIEW6_ARB = 0x8726;

    public static final int GL_MODELVIEW7_ARB = 0x8727;

    public static final int GL_MODELVIEW8_ARB = 0x8728;

    public static final int GL_MODELVIEW9_ARB = 0x8729;

    /// Group: `GetPName`
    public static final int GL_MODELVIEW_MATRIX = 0x0BA6;

    public static final int GL_MODELVIEW_MATRIX_FLOAT_AS_INT_BITS_OES = 0x898D;

    public static final int GL_MODELVIEW_PROJECTION_NV = 0x8629;

    /// Group: `GetPName`
    public static final int GL_MODELVIEW_STACK_DEPTH = 0x0BA3;

    /// Groups: `TextureEnvMode`, `LightEnvModeSGIX`
    public static final int GL_MODULATE = 0x2100;

    public static final int GL_MODULATE_ADD_ATI = 0x8744;

    public static final int GL_MODULATE_COLOR_IMG = 0x8C04;

    public static final int GL_MODULATE_SIGNED_ADD_ATI = 0x8745;

    public static final int GL_MODULATE_SUBTRACT_ATI = 0x8746;

    /// Group: `GetPName`
    public static final int GL_MOTION_ESTIMATION_SEARCH_BLOCK_X_QCOM = 0x8C90;

    /// Group: `GetPName`
    public static final int GL_MOTION_ESTIMATION_SEARCH_BLOCK_Y_QCOM = 0x8C91;

    public static final int GL_MOVE_TO_CONTINUES_NV = 0x90B6;

    /// Group: `PathCoordType`
    public static final int GL_MOVE_TO_NV = 0x02;

    public static final int GL_MOVE_TO_RESETS_NV = 0x90B5;

    /// Group: `FragmentOp1ATI`
    public static final int GL_MOV_ATI = 0x8961;

    /// Group: `AccumOp`
    public static final int GL_MULT = 0x0103;

    public static final int GL_MULTICAST_GPUS_NV = 0x92BA;

    public static final int GL_MULTICAST_PROGRAMMABLE_SAMPLE_LOCATION_NV = 0x9549;

    public static final int GL_MULTIPLY = 0x9294;

    public static final int GL_MULTIPLY_KHR = 0x9294;

    public static final int GL_MULTIPLY_NV = 0x9294;

    /// Group: `EnableCap`
    public static final int GL_MULTISAMPLE = 0x809D;

    public static final int GL_MULTISAMPLES_NV = 0x9371;

    public static final int GL_MULTISAMPLE_3DFX = 0x86B2;

    public static final int GL_MULTISAMPLE_ARB = 0x809D;

    /// Group: `AttribMask`
    public static final int GL_MULTISAMPLE_BIT = 0x20000000;

    /// Group: `AttribMask`
    public static final int GL_MULTISAMPLE_BIT_3DFX = 0x20000000;

    /// Group: `AttribMask`
    public static final int GL_MULTISAMPLE_BIT_ARB = 0x20000000;

    /// Group: `AttribMask`
    public static final int GL_MULTISAMPLE_BIT_EXT = 0x20000000;

    /// Group: `BufferBitQCOM`
    public static final int GL_MULTISAMPLE_BUFFER_BIT0_QCOM = 0x01000000;

    /// Group: `BufferBitQCOM`
    public static final int GL_MULTISAMPLE_BUFFER_BIT1_QCOM = 0x02000000;

    /// Group: `BufferBitQCOM`
    public static final int GL_MULTISAMPLE_BUFFER_BIT2_QCOM = 0x04000000;

    /// Group: `BufferBitQCOM`
    public static final int GL_MULTISAMPLE_BUFFER_BIT3_QCOM = 0x08000000;

    /// Group: `BufferBitQCOM`
    public static final int GL_MULTISAMPLE_BUFFER_BIT4_QCOM = 0x10000000;

    /// Group: `BufferBitQCOM`
    public static final int GL_MULTISAMPLE_BUFFER_BIT5_QCOM = 0x20000000;

    /// Group: `BufferBitQCOM`
    public static final int GL_MULTISAMPLE_BUFFER_BIT6_QCOM = 0x40000000;

    /// Group: `BufferBitQCOM`
    public static final int GL_MULTISAMPLE_BUFFER_BIT7_QCOM = 0x80000000;

    public static final int GL_MULTISAMPLE_COVERAGE_MODES_NV = 0x8E12;

    public static final int GL_MULTISAMPLE_EXT = 0x809D;

    /// Group: `HintTarget`
    public static final int GL_MULTISAMPLE_FILTER_HINT_NV = 0x8534;

    public static final int GL_MULTISAMPLE_LINE_WIDTH_GRANULARITY = 0x9382;

    public static final int GL_MULTISAMPLE_LINE_WIDTH_GRANULARITY_ARB = 0x9382;

    public static final int GL_MULTISAMPLE_LINE_WIDTH_RANGE = 0x9381;

    public static final int GL_MULTISAMPLE_LINE_WIDTH_RANGE_ARB = 0x9381;

    public static final int GL_MULTISAMPLE_RASTERIZATION_ALLOWED_EXT = 0x932B;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_MULTISAMPLE_SGIS = 0x809D;

    public static final int GL_MULTIVIEW_EXT = 0x90F1;

    /// Group: `FragmentOp2ATI`
    public static final int GL_MUL_ATI = 0x8964;

    /// Group: `VertexShaderParameterEXT`
    public static final int GL_MVP_MATRIX_EXT = 0x87E3;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_N3F_V3F = 0x2A25;

    public static final int GL_NAMED_STRING_LENGTH_ARB = 0x8DE9;

    public static final int GL_NAMED_STRING_TYPE_ARB = 0x8DEA;

    /// Group: `ProgramResourceProperty`
    public static final int GL_NAME_LENGTH = 0x92F9;

    /// Group: `GetPName`
    public static final int GL_NAME_STACK_DEPTH = 0x0D70;

    /// Group: `LogicOp`
    public static final int GL_NAND = 0x150E;

    /// Group: `HintTarget`
    public static final int GL_NATIVE_GRAPHICS_BEGIN_HINT_PGI = 0x1A203;

    /// Group: `HintTarget`
    public static final int GL_NATIVE_GRAPHICS_END_HINT_PGI = 0x1A204;

    public static final int GL_NATIVE_GRAPHICS_HANDLE_PGI = 0x1A202;

    /// Groups: `BlitFramebufferFilter`, `TextureMagFilter`, `TextureMinFilter`
    public static final int GL_NEAREST = 0x2600;

    /// Group: `TextureMinFilter`
    public static final int GL_NEAREST_CLIPMAP_LINEAR_SGIX = 0x844E;

    /// Group: `TextureMinFilter`
    public static final int GL_NEAREST_CLIPMAP_NEAREST_SGIX = 0x844D;

    /// Group: `TextureMinFilter`
    public static final int GL_NEAREST_MIPMAP_LINEAR = 0x2702;

    /// Group: `TextureMinFilter`
    public static final int GL_NEAREST_MIPMAP_NEAREST = 0x2700;

    /// Group: `FragmentShaderColorModMaskATI`
    public static final int GL_NEGATE_BIT_ATI = 0x00000004;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_NEGATIVE_ONE_EXT = 0x87DF;

    /// Group: `ClipControlDepth`
    public static final int GL_NEGATIVE_ONE_TO_ONE = 0x935E;

    /// Alias: `GL_NEGATIVE_ONE_TO_ONE`
    public static final int GL_NEGATIVE_ONE_TO_ONE_EXT = 0x935E;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_NEGATIVE_W_EXT = 0x87DC;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_NEGATIVE_X_EXT = 0x87D9;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_NEGATIVE_Y_EXT = 0x87DA;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_NEGATIVE_Z_EXT = 0x87DB;

    /// Groups: `StencilFunction`, `IndexFunctionEXT`, `AlphaFunction`,
    ///   `DepthFunction`
    public static final int GL_NEVER = 0x0200;

    // /// Group: `TransformFeedbackTokenNV`
    // public static final int GL_NEXT_BUFFER_NV = (2 as GLenum).wrapping_neg();

    public static final int GL_NEXT_VIDEO_CAPTURE_BUFFER_STATUS_NV = 0x9025;

    /// Group: `HintMode`
    public static final int GL_NICEST = 0x1102;

    /// Groups: `SpecialNumbers`, `FragmentShaderValueRepATI`,
    ///   `FragmentShaderDestModMaskATI`, `FragmentShaderDestMaskATI`,
    ///   `SyncBehaviorFlags`, `TextureCompareMode`, `PathColorFormat`,
    ///   `CombinerBiasNV`, `CombinerScaleNV`, `DrawBufferMode`,
    ///   `PixelTexGenModeSGIX`, `ReadBufferMode`, `ColorBuffer`, `PathGenMode`,
    ///   `PathTransformType`, `PathFontStyle`
    public static final int GL_NONE = 0;

    /// Groups: `SpecialNumbers`, `ReadBufferMode`, `DrawBufferMode`
    public static final int GL_NONE_OES = 0;

    /// Group: `LogicOp`
    public static final int GL_NOOP = 0x1505;

    /// Group: `CommandOpcodesNV`
    public static final int GL_NOP_COMMAND_NV = 0x0001;

    /// Group: `LogicOp`
    public static final int GL_NOR = 0x1508;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_NORMALIZE = 0x0BA1;

    /// Group: `ParameterRangeEXT`
    public static final int GL_NORMALIZED_RANGE_EXT = 0x87E0;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_NORMAL_ARRAY = 0x8075;

    public static final int GL_NORMAL_ARRAY_ADDRESS_NV = 0x8F22;

    public static final int GL_NORMAL_ARRAY_BUFFER_BINDING = 0x8897;

    public static final int GL_NORMAL_ARRAY_BUFFER_BINDING_ARB = 0x8897;

    /// Group: `GetPName`
    public static final int GL_NORMAL_ARRAY_COUNT_EXT = 0x8080;

    public static final int GL_NORMAL_ARRAY_EXT = 0x8075;

    public static final int GL_NORMAL_ARRAY_LENGTH_NV = 0x8F2C;

    public static final int GL_NORMAL_ARRAY_LIST_IBM = 103071;

    public static final int GL_NORMAL_ARRAY_LIST_STRIDE_IBM = 103081;

    public static final int GL_NORMAL_ARRAY_PARALLEL_POINTERS_INTEL = 0x83F6;

    /// Group: `GetPointervPName`
    public static final int GL_NORMAL_ARRAY_POINTER = 0x808F;

    /// Group: `GetPointervPName`
    public static final int GL_NORMAL_ARRAY_POINTER_EXT = 0x808F;

    /// Group: `GetPName`
    public static final int GL_NORMAL_ARRAY_STRIDE = 0x807F;

    public static final int GL_NORMAL_ARRAY_STRIDE_EXT = 0x807F;

    /// Group: `GetPName`
    public static final int GL_NORMAL_ARRAY_TYPE = 0x807E;

    public static final int GL_NORMAL_ARRAY_TYPE_EXT = 0x807E;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_NORMAL_BIT_PGI = 0x08000000;

    /// Group: `GetTextureParameter`
    public static final int GL_NORMAL_MAP = 0x8511;

    /// Group: `GetTextureParameter`
    public static final int GL_NORMAL_MAP_ARB = 0x8511;

    /// Group: `GetTextureParameter`
    public static final int GL_NORMAL_MAP_EXT = 0x8511;

    /// Group: `GetTextureParameter`
    public static final int GL_NORMAL_MAP_NV = 0x8511;

    /// Group: `GetTextureParameter`
    public static final int GL_NORMAL_MAP_OES = 0x8511;

    /// Groups: `StencilFunction`, `IndexFunctionEXT`, `AlphaFunction`,
    ///   `DepthFunction`
    public static final int GL_NOTEQUAL = 0x0205;

    /// Groups: `SpecialNumbers`, `GraphicsResetStatus`, `ErrorCode`
    public static final int GL_NO_ERROR = 0;

    public static final int GL_NO_RESET_NOTIFICATION = 0x8261;

    public static final int GL_NO_RESET_NOTIFICATION_ARB = 0x8261;

    public static final int GL_NO_RESET_NOTIFICATION_EXT = 0x8261;

    public static final int GL_NO_RESET_NOTIFICATION_KHR = 0x8261;

    /// Group: `ProgramResourceProperty`
    public static final int GL_NUM_ACTIVE_VARIABLES = 0x9304;

    /// Groups: `ProgramResourceProperty`, `SubroutineParameterName`
    public static final int GL_NUM_COMPATIBLE_SUBROUTINES = 0x8E4A;

    /// Group: `GetPName`
    public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2;

    public static final int GL_NUM_COMPRESSED_TEXTURE_FORMATS_ARB = 0x86A2;

    /// Group: `GetPName`
    public static final int GL_NUM_DEVICE_UUIDS_EXT = 0x9596;

    public static final int GL_NUM_DOWNSAMPLE_SCALES_IMG = 0x913D;

    /// Group: `GetPName`
    public static final int GL_NUM_EXTENSIONS = 0x821D;

    public static final int GL_NUM_FILL_STREAMS_NV = 0x8E29;

    public static final int GL_NUM_FRAGMENT_CONSTANTS_ATI = 0x896F;

    public static final int GL_NUM_FRAGMENT_REGISTERS_ATI = 0x896E;

    public static final int GL_NUM_GENERAL_COMBINERS_NV = 0x854E;

    public static final int GL_NUM_INPUT_INTERPOLATOR_COMPONENTS_ATI = 0x8973;

    public static final int GL_NUM_INSTRUCTIONS_PER_PASS_ATI = 0x8971;

    public static final int GL_NUM_INSTRUCTIONS_TOTAL_ATI = 0x8972;

    public static final int GL_NUM_LOOPBACK_COMPONENTS_ATI = 0x8974;

    public static final int GL_NUM_PASSES_ATI = 0x8970;

    /// Group: `GetPName`
    public static final int GL_NUM_PROGRAM_BINARY_FORMATS = 0x87FE;

    public static final int GL_NUM_PROGRAM_BINARY_FORMATS_OES = 0x87FE;

    /// Group: `InternalFormatPName`
    public static final int GL_NUM_SAMPLE_COUNTS = 0x9380;

    /// Group: `GetPName`
    public static final int GL_NUM_SHADER_BINARY_FORMATS = 0x8DF9;

    public static final int GL_NUM_SHADING_LANGUAGE_VERSIONS = 0x82E9;

    public static final int GL_NUM_SPARSE_LEVELS_ARB = 0x91AA;

    public static final int GL_NUM_SPARSE_LEVELS_EXT = 0x91AA;

    public static final int GL_NUM_SPIR_V_EXTENSIONS = 0x9554;

    public static final int GL_NUM_SUPPORTED_MULTISAMPLE_MODES_AMD = 0x91B6;

    /// Group: `InternalFormatPName`
    public static final int GL_NUM_SURFACE_COMPRESSION_FIXED_RATES_EXT = 0x8F6E;

    public static final int GL_NUM_TILING_TYPES_EXT = 0x9582;

    public static final int GL_NUM_VIDEO_CAPTURE_STREAMS_NV = 0x9024;

    public static final int GL_NUM_VIRTUAL_PAGE_SIZES_ARB = 0x91A8;

    public static final int GL_NUM_VIRTUAL_PAGE_SIZES_EXT = 0x91A8;

    public static final int GL_NUM_WINDOW_RECTANGLES_EXT = 0x8F15;

    public static final int GL_OBJECT_ACTIVE_ATTRIBUTES_ARB = 0x8B89;

    public static final int GL_OBJECT_ACTIVE_ATTRIBUTE_MAX_LENGTH_ARB = 0x8B8A;

    public static final int GL_OBJECT_ACTIVE_UNIFORMS_ARB = 0x8B86;

    public static final int GL_OBJECT_ACTIVE_UNIFORM_MAX_LENGTH_ARB = 0x8B87;

    public static final int GL_OBJECT_ATTACHED_OBJECTS_ARB = 0x8B85;

    /// Group: `ArrayObjectPNameATI`
    public static final int GL_OBJECT_BUFFER_SIZE_ATI = 0x8764;

    /// Group: `ArrayObjectPNameATI`
    public static final int GL_OBJECT_BUFFER_USAGE_ATI = 0x8765;

    public static final int GL_OBJECT_COMPILE_STATUS_ARB = 0x8B81;

    public static final int GL_OBJECT_DELETE_STATUS_ARB = 0x8B80;

    /// Group: `TextureGenMode`
    public static final int GL_OBJECT_DISTANCE_TO_LINE_SGIS = 0x81F3;

    /// Group: `TextureGenMode`
    public static final int GL_OBJECT_DISTANCE_TO_POINT_SGIS = 0x81F1;

    public static final int GL_OBJECT_INFO_LOG_LENGTH_ARB = 0x8B84;

    /// Groups: `PathGenMode`, `TextureGenMode`
    public static final int GL_OBJECT_LINEAR = 0x2401;

    public static final int GL_OBJECT_LINEAR_NV = 0x2401;

    /// Group: `TextureGenParameter`
    public static final int GL_OBJECT_LINE_SGIS = 0x81F7;

    public static final int GL_OBJECT_LINK_STATUS_ARB = 0x8B82;

    /// Group: `TextureGenParameter`
    public static final int GL_OBJECT_PLANE = 0x2501;

    /// Group: `TextureGenParameter`
    public static final int GL_OBJECT_POINT_SGIS = 0x81F5;

    public static final int GL_OBJECT_SHADER_SOURCE_LENGTH_ARB = 0x8B88;

    public static final int GL_OBJECT_SUBTYPE_ARB = 0x8B4F;

    /// Group: `SyncParameterName`
    public static final int GL_OBJECT_TYPE = 0x9112;

    public static final int GL_OBJECT_TYPE_APPLE = 0x9112;

    public static final int GL_OBJECT_TYPE_ARB = 0x8B4E;

    public static final int GL_OBJECT_VALIDATE_STATUS_ARB = 0x8B83;

    public static final int GL_OCCLUSION_QUERY_EVENT_MASK_AMD = 0x874F;

    public static final int GL_OCCLUSION_TEST_HP = 0x8165;

    public static final int GL_OCCLUSION_TEST_RESULT_HP = 0x8166;

    /// Group: `ProgramResourceProperty`
    public static final int GL_OFFSET = 0x92FC;

    public static final int GL_OFFSET_HILO_PROJECTIVE_TEXTURE_2D_NV = 0x8856;

    public static final int GL_OFFSET_HILO_PROJECTIVE_TEXTURE_RECTANGLE_NV = 0x8857;

    public static final int GL_OFFSET_HILO_TEXTURE_2D_NV = 0x8854;

    public static final int GL_OFFSET_HILO_TEXTURE_RECTANGLE_NV = 0x8855;

    public static final int GL_OFFSET_PROJECTIVE_TEXTURE_2D_NV = 0x8850;

    public static final int GL_OFFSET_PROJECTIVE_TEXTURE_2D_SCALE_NV = 0x8851;

    public static final int GL_OFFSET_PROJECTIVE_TEXTURE_RECTANGLE_NV = 0x8852;

    public static final int GL_OFFSET_PROJECTIVE_TEXTURE_RECTANGLE_SCALE_NV = 0x8853;

    /// Alias: `GL_OFFSET_TEXTURE_BIAS_NV`
    public static final int GL_OFFSET_TEXTURE_2D_BIAS_NV = 0x86E3;

    /// Alias: `GL_OFFSET_TEXTURE_MATRIX_NV`
    public static final int GL_OFFSET_TEXTURE_2D_MATRIX_NV = 0x86E1;

    public static final int GL_OFFSET_TEXTURE_2D_NV = 0x86E8;

    /// Alias: `GL_OFFSET_TEXTURE_SCALE_NV`
    public static final int GL_OFFSET_TEXTURE_2D_SCALE_NV = 0x86E2;

    public static final int GL_OFFSET_TEXTURE_BIAS_NV = 0x86E3;

    public static final int GL_OFFSET_TEXTURE_MATRIX_NV = 0x86E1;

    public static final int GL_OFFSET_TEXTURE_RECTANGLE_NV = 0x864C;

    public static final int GL_OFFSET_TEXTURE_RECTANGLE_SCALE_NV = 0x864D;

    public static final int GL_OFFSET_TEXTURE_SCALE_NV = 0x86E2;

    /// Groups: `SpecialNumbers`, `TextureSwizzle`, `BlendingFactor`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_ONE = 1;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_ONE_EXT = 0x87DE;

    /// Group: `BlendingFactor`
    public static final int GL_ONE_MINUS_CONSTANT_ALPHA = 0x8004;

    public static final int GL_ONE_MINUS_CONSTANT_ALPHA_EXT = 0x8004;

    /// Group: `BlendingFactor`
    public static final int GL_ONE_MINUS_CONSTANT_COLOR = 0x8002;

    public static final int GL_ONE_MINUS_CONSTANT_COLOR_EXT = 0x8002;

    /// Group: `BlendingFactor`
    public static final int GL_ONE_MINUS_DST_ALPHA = 0x0305;

    /// Group: `BlendingFactor`
    public static final int GL_ONE_MINUS_DST_COLOR = 0x0307;

    /// Group: `BlendingFactor`
    public static final int GL_ONE_MINUS_SRC1_ALPHA = 0x88FB;

    public static final int GL_ONE_MINUS_SRC1_ALPHA_EXT = 0x88FB;

    /// Group: `BlendingFactor`
    public static final int GL_ONE_MINUS_SRC1_COLOR = 0x88FA;

    public static final int GL_ONE_MINUS_SRC1_COLOR_EXT = 0x88FA;

    /// Group: `BlendingFactor`
    public static final int GL_ONE_MINUS_SRC_ALPHA = 0x0303;

    /// Group: `BlendingFactor`
    public static final int GL_ONE_MINUS_SRC_COLOR = 0x0301;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND0_ALPHA = 0x8598;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND0_ALPHA_ARB = 0x8598;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND0_ALPHA_EXT = 0x8598;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND0_RGB = 0x8590;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND0_RGB_ARB = 0x8590;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND0_RGB_EXT = 0x8590;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND1_ALPHA = 0x8599;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND1_ALPHA_ARB = 0x8599;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND1_ALPHA_EXT = 0x8599;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND1_RGB = 0x8591;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND1_RGB_ARB = 0x8591;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND1_RGB_EXT = 0x8591;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND2_ALPHA = 0x859A;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND2_ALPHA_ARB = 0x859A;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND2_ALPHA_EXT = 0x859A;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND2_RGB = 0x8592;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND2_RGB_ARB = 0x8592;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND2_RGB_EXT = 0x8592;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND3_ALPHA_NV = 0x859B;

    /// Group: `TextureEnvParameter`
    public static final int GL_OPERAND3_RGB_NV = 0x8593;

    public static final int GL_OPTIMAL_TILING_EXT = 0x9584;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_ADD_EXT = 0x8787;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_CLAMP_EXT = 0x878E;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_CROSS_PRODUCT_EXT = 0x8797;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_DOT3_EXT = 0x8784;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_DOT4_EXT = 0x8785;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_EXP_BASE_2_EXT = 0x8791;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_FLOOR_EXT = 0x878F;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_FRAC_EXT = 0x8789;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_INDEX_EXT = 0x8782;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_LOG_BASE_2_EXT = 0x8792;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_MADD_EXT = 0x8788;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_MAX_EXT = 0x878A;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_MIN_EXT = 0x878B;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_MOV_EXT = 0x8799;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_MULTIPLY_MATRIX_EXT = 0x8798;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_MUL_EXT = 0x8786;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_NEGATE_EXT = 0x8783;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_POWER_EXT = 0x8793;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_RECIP_EXT = 0x8794;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_RECIP_SQRT_EXT = 0x8795;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_ROUND_EXT = 0x8790;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_SET_GE_EXT = 0x878C;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_SET_LT_EXT = 0x878D;

    /// Group: `VertexShaderOpEXT`
    public static final int GL_OP_SUB_EXT = 0x8796;

    /// Group: `LogicOp`
    public static final int GL_OR = 0x1507;

    /// Groups: `MapQuery`, `GetMapQuery`
    public static final int GL_ORDER = 0x0A01;

    /// Group: `LogicOp`
    public static final int GL_OR_INVERTED = 0x150D;

    /// Group: `LogicOp`
    public static final int GL_OR_REVERSE = 0x150B;

    public static final int GL_OUTPUT_COLOR0_EXT = 0x879B;

    public static final int GL_OUTPUT_COLOR1_EXT = 0x879C;

    public static final int GL_OUTPUT_FOG_EXT = 0x87BD;

    public static final int GL_OUTPUT_TEXTURE_COORD0_EXT = 0x879D;

    public static final int GL_OUTPUT_TEXTURE_COORD10_EXT = 0x87A7;

    public static final int GL_OUTPUT_TEXTURE_COORD11_EXT = 0x87A8;

    public static final int GL_OUTPUT_TEXTURE_COORD12_EXT = 0x87A9;

    public static final int GL_OUTPUT_TEXTURE_COORD13_EXT = 0x87AA;

    public static final int GL_OUTPUT_TEXTURE_COORD14_EXT = 0x87AB;

    public static final int GL_OUTPUT_TEXTURE_COORD15_EXT = 0x87AC;

    public static final int GL_OUTPUT_TEXTURE_COORD16_EXT = 0x87AD;

    public static final int GL_OUTPUT_TEXTURE_COORD17_EXT = 0x87AE;

    public static final int GL_OUTPUT_TEXTURE_COORD18_EXT = 0x87AF;

    public static final int GL_OUTPUT_TEXTURE_COORD19_EXT = 0x87B0;

    public static final int GL_OUTPUT_TEXTURE_COORD1_EXT = 0x879E;

    public static final int GL_OUTPUT_TEXTURE_COORD20_EXT = 0x87B1;

    public static final int GL_OUTPUT_TEXTURE_COORD21_EXT = 0x87B2;

    public static final int GL_OUTPUT_TEXTURE_COORD22_EXT = 0x87B3;

    public static final int GL_OUTPUT_TEXTURE_COORD23_EXT = 0x87B4;

    public static final int GL_OUTPUT_TEXTURE_COORD24_EXT = 0x87B5;

    public static final int GL_OUTPUT_TEXTURE_COORD25_EXT = 0x87B6;

    public static final int GL_OUTPUT_TEXTURE_COORD26_EXT = 0x87B7;

    public static final int GL_OUTPUT_TEXTURE_COORD27_EXT = 0x87B8;

    public static final int GL_OUTPUT_TEXTURE_COORD28_EXT = 0x87B9;

    public static final int GL_OUTPUT_TEXTURE_COORD29_EXT = 0x87BA;

    public static final int GL_OUTPUT_TEXTURE_COORD2_EXT = 0x879F;

    public static final int GL_OUTPUT_TEXTURE_COORD30_EXT = 0x87BB;

    public static final int GL_OUTPUT_TEXTURE_COORD31_EXT = 0x87BC;

    public static final int GL_OUTPUT_TEXTURE_COORD3_EXT = 0x87A0;

    public static final int GL_OUTPUT_TEXTURE_COORD4_EXT = 0x87A1;

    public static final int GL_OUTPUT_TEXTURE_COORD5_EXT = 0x87A2;

    public static final int GL_OUTPUT_TEXTURE_COORD6_EXT = 0x87A3;

    public static final int GL_OUTPUT_TEXTURE_COORD7_EXT = 0x87A4;

    public static final int GL_OUTPUT_TEXTURE_COORD8_EXT = 0x87A5;

    public static final int GL_OUTPUT_TEXTURE_COORD9_EXT = 0x87A6;

    public static final int GL_OUTPUT_VERTEX_EXT = 0x879A;

    /// Group: `ErrorCode`
    public static final int GL_OUT_OF_MEMORY = 0x0505;

    public static final int GL_OVERLAY = 0x9296;

    public static final int GL_OVERLAY_KHR = 0x9296;

    public static final int GL_OVERLAY_NV = 0x9296;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_ALIGNMENT = 0x0D05;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_PACK_CMYK_HINT_EXT = 0x800E;

    public static final int GL_PACK_COMPRESSED_BLOCK_DEPTH = 0x912D;

    public static final int GL_PACK_COMPRESSED_BLOCK_HEIGHT = 0x912C;

    public static final int GL_PACK_COMPRESSED_BLOCK_SIZE = 0x912E;

    public static final int GL_PACK_COMPRESSED_BLOCK_WIDTH = 0x912B;

    public static final int GL_PACK_COMPRESSED_SIZE_SGIX = 0x831C;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_IMAGE_DEPTH_SGIS = 0x8131;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_IMAGE_HEIGHT = 0x806C;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_IMAGE_HEIGHT_EXT = 0x806C;

    public static final int GL_PACK_INVERT_MESA = 0x8758;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_LSB_FIRST = 0x0D01;

    public static final int GL_PACK_MAX_COMPRESSED_SIZE_SGIX = 0x831B;

    /// Group: `PixelStoreParameter`
    public static final int GL_PACK_RESAMPLE_OML = 0x8984;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_RESAMPLE_SGIX = 0x842E;

    public static final int GL_PACK_REVERSE_ROW_ORDER_ANGLE = 0x93A4;

    public static final int GL_PACK_ROW_BYTES_APPLE = 0x8A15;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_ROW_LENGTH = 0x0D02;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_SKIP_IMAGES = 0x806B;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_SKIP_IMAGES_EXT = 0x806B;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_SKIP_PIXELS = 0x0D04;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_SKIP_ROWS = 0x0D03;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_SKIP_VOLUMES_SGIS = 0x8130;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_SUBSAMPLE_RATE_SGIX = 0x85A0;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PACK_SWAP_BYTES = 0x0D00;

    public static final int GL_PALETTE4_R5_G6_B5_OES = 0x8B92;

    public static final int GL_PALETTE4_RGB5_A1_OES = 0x8B94;

    public static final int GL_PALETTE4_RGB8_OES = 0x8B90;

    public static final int GL_PALETTE4_RGBA4_OES = 0x8B93;

    public static final int GL_PALETTE4_RGBA8_OES = 0x8B91;

    public static final int GL_PALETTE8_R5_G6_B5_OES = 0x8B97;

    public static final int GL_PALETTE8_RGB5_A1_OES = 0x8B99;

    public static final int GL_PALETTE8_RGB8_OES = 0x8B95;

    public static final int GL_PALETTE8_RGBA4_OES = 0x8B98;

    public static final int GL_PALETTE8_RGBA8_OES = 0x8B96;

    public static final int GL_PARALLEL_ARRAYS_INTEL = 0x83F4;

    /// Group: `BufferTargetARB`
    public static final int GL_PARAMETER_BUFFER = 0x80EE;

    /// Alias: `GL_PARAMETER_BUFFER`
    public static final int GL_PARAMETER_BUFFER_ARB = 0x80EE;

    public static final int GL_PARAMETER_BUFFER_BINDING = 0x80EF;

    /// Alias: `GL_PARAMETER_BUFFER_BINDING`
    public static final int GL_PARAMETER_BUFFER_BINDING_ARB = 0x80EF;

    public static final int GL_PARTIAL_SUCCESS_NV = 0x902E;

    public static final int GL_PASS_THROUGH_NV = 0x86E6;

    /// Group: `FeedBackToken`
    public static final int GL_PASS_THROUGH_TOKEN = 0x0700;

    /// Group: `PrimitiveType`
    public static final int GL_PATCHES = 0x000E;

    /// Group: `PrimitiveType`
    public static final int GL_PATCHES_EXT = 0x000E;

    public static final int GL_PATCHES_OES = 0x000E;

    /// Group: `PatchParameterName`
    public static final int GL_PATCH_DEFAULT_INNER_LEVEL = 0x8E73;

    public static final int GL_PATCH_DEFAULT_INNER_LEVEL_EXT = 0x8E73;

    /// Group: `PatchParameterName`
    public static final int GL_PATCH_DEFAULT_OUTER_LEVEL = 0x8E74;

    public static final int GL_PATCH_DEFAULT_OUTER_LEVEL_EXT = 0x8E74;

    /// Group: `PatchParameterName`
    public static final int GL_PATCH_VERTICES = 0x8E72;

    public static final int GL_PATCH_VERTICES_EXT = 0x8E72;

    public static final int GL_PATCH_VERTICES_OES = 0x8E72;

    /// Group: `PathParameter`
    public static final int GL_PATH_CLIENT_LENGTH_NV = 0x907F;

    /// Group: `PathParameter`
    public static final int GL_PATH_COMMAND_COUNT_NV = 0x909D;

    /// Group: `PathParameter`
    public static final int GL_PATH_COMPUTED_LENGTH_NV = 0x90A0;

    /// Group: `PathParameter`
    public static final int GL_PATH_COORD_COUNT_NV = 0x909E;

    public static final int GL_PATH_COVER_DEPTH_FUNC_NV = 0x90BF;

    /// Group: `PathParameter`
    public static final int GL_PATH_DASH_ARRAY_COUNT_NV = 0x909F;

    /// Group: `PathParameter`
    public static final int GL_PATH_DASH_CAPS_NV = 0x907B;

    /// Group: `PathParameter`
    public static final int GL_PATH_DASH_OFFSET_NV = 0x907E;

    /// Group: `PathParameter`
    public static final int GL_PATH_DASH_OFFSET_RESET_NV = 0x90B4;

    /// Group: `PathParameter`
    public static final int GL_PATH_END_CAPS_NV = 0x9076;

    public static final int GL_PATH_ERROR_POSITION_NV = 0x90AB;

    /// Group: `PathParameter`
    public static final int GL_PATH_FILL_BOUNDING_BOX_NV = 0x90A1;

    /// Groups: `PathCoverMode`, `PathParameter`
    public static final int GL_PATH_FILL_COVER_MODE_NV = 0x9082;

    /// Group: `PathParameter`
    public static final int GL_PATH_FILL_MASK_NV = 0x9081;

    /// Groups: `PathParameter`, `PathFillMode`
    public static final int GL_PATH_FILL_MODE_NV = 0x9080;

    public static final int GL_PATH_FOG_GEN_MODE_NV = 0x90AC;

    /// Group: `PathStringFormat`
    public static final int GL_PATH_FORMAT_PS_NV = 0x9071;

    /// Group: `PathStringFormat`
    public static final int GL_PATH_FORMAT_SVG_NV = 0x9070;

    public static final int GL_PATH_GEN_COEFF_NV = 0x90B1;

    public static final int GL_PATH_GEN_COLOR_FORMAT_NV = 0x90B2;

    public static final int GL_PATH_GEN_COMPONENTS_NV = 0x90B3;

    public static final int GL_PATH_GEN_MODE_NV = 0x90B0;

    /// Group: `PathParameter`
    public static final int GL_PATH_INITIAL_DASH_CAP_NV = 0x907C;

    /// Group: `PathParameter`
    public static final int GL_PATH_INITIAL_END_CAP_NV = 0x9077;

    /// Group: `PathParameter`
    public static final int GL_PATH_JOIN_STYLE_NV = 0x9079;

    public static final int GL_PATH_MAX_MODELVIEW_STACK_DEPTH_NV = 0x0D36;

    public static final int GL_PATH_MAX_PROJECTION_STACK_DEPTH_NV = 0x0D38;

    /// Group: `PathParameter`
    public static final int GL_PATH_MITER_LIMIT_NV = 0x907A;

    public static final int GL_PATH_MODELVIEW_MATRIX_NV = 0x0BA6;

    public static final int GL_PATH_MODELVIEW_NV = 0x1700;

    public static final int GL_PATH_MODELVIEW_STACK_DEPTH_NV = 0x0BA3;

    /// Groups: `PathGenMode`, `PathParameter`
    public static final int GL_PATH_OBJECT_BOUNDING_BOX_NV = 0x908A;

    public static final int GL_PATH_PROJECTION_MATRIX_NV = 0x0BA7;

    public static final int GL_PATH_PROJECTION_NV = 0x1701;

    public static final int GL_PATH_PROJECTION_STACK_DEPTH_NV = 0x0BA4;

    public static final int GL_PATH_STENCIL_DEPTH_OFFSET_FACTOR_NV = 0x90BD;

    public static final int GL_PATH_STENCIL_DEPTH_OFFSET_UNITS_NV = 0x90BE;

    public static final int GL_PATH_STENCIL_FUNC_NV = 0x90B7;

    public static final int GL_PATH_STENCIL_REF_NV = 0x90B8;

    public static final int GL_PATH_STENCIL_VALUE_MASK_NV = 0x90B9;

    /// Group: `PathParameter`
    public static final int GL_PATH_STROKE_BOUNDING_BOX_NV = 0x90A2;

    /// Group: `PathParameter`
    public static final int GL_PATH_STROKE_COVER_MODE_NV = 0x9083;

    /// Group: `PathParameter`
    public static final int GL_PATH_STROKE_MASK_NV = 0x9084;

    /// Group: `PathParameter`
    public static final int GL_PATH_STROKE_WIDTH_NV = 0x9075;

    /// Group: `PathParameter`
    public static final int GL_PATH_TERMINAL_DASH_CAP_NV = 0x907D;

    /// Group: `PathParameter`
    public static final int GL_PATH_TERMINAL_END_CAP_NV = 0x9078;

    public static final int GL_PATH_TRANSPOSE_MODELVIEW_MATRIX_NV = 0x84E3;

    public static final int GL_PATH_TRANSPOSE_PROJECTION_MATRIX_NV = 0x84E4;

    public static final int GL_PERCENTAGE_AMD = 0x8BC3;

    public static final int GL_PERFMON_GLOBAL_MODE_QCOM = 0x8FA0;

    public static final int GL_PERFMON_RESULT_AMD = 0x8BC6;

    public static final int GL_PERFMON_RESULT_AVAILABLE_AMD = 0x8BC4;

    public static final int GL_PERFMON_RESULT_SIZE_AMD = 0x8BC5;

    public static final int GL_PERFORMANCE_MONITOR_AMD = 0x9152;

    public static final int GL_PERFQUERY_COUNTER_DATA_BOOL32_INTEL = 0x94FC;

    public static final int GL_PERFQUERY_COUNTER_DATA_DOUBLE_INTEL = 0x94FB;

    public static final int GL_PERFQUERY_COUNTER_DATA_FLOAT_INTEL = 0x94FA;

    public static final int GL_PERFQUERY_COUNTER_DATA_UINT32_INTEL = 0x94F8;

    public static final int GL_PERFQUERY_COUNTER_DATA_UINT64_INTEL = 0x94F9;

    public static final int GL_PERFQUERY_COUNTER_DESC_LENGTH_MAX_INTEL = 0x94FF;

    public static final int GL_PERFQUERY_COUNTER_DURATION_NORM_INTEL = 0x94F1;

    public static final int GL_PERFQUERY_COUNTER_DURATION_RAW_INTEL = 0x94F2;

    public static final int GL_PERFQUERY_COUNTER_EVENT_INTEL = 0x94F0;

    public static final int GL_PERFQUERY_COUNTER_NAME_LENGTH_MAX_INTEL = 0x94FE;

    public static final int GL_PERFQUERY_COUNTER_RAW_INTEL = 0x94F4;

    public static final int GL_PERFQUERY_COUNTER_THROUGHPUT_INTEL = 0x94F3;

    public static final int GL_PERFQUERY_COUNTER_TIMESTAMP_INTEL = 0x94F5;

    public static final int GL_PERFQUERY_DONOT_FLUSH_INTEL = 0x83F9;

    public static final int GL_PERFQUERY_FLUSH_INTEL = 0x83FA;

    /// Group: `PerformanceQueryCapsMaskINTEL`
    public static final int GL_PERFQUERY_GLOBAL_CONTEXT_INTEL = 0x00000001;

    public static final int GL_PERFQUERY_GPA_EXTENDED_COUNTERS_INTEL = 0x9500;

    public static final int GL_PERFQUERY_QUERY_NAME_LENGTH_MAX_INTEL = 0x94FD;

    /// Group: `PerformanceQueryCapsMaskINTEL`
    public static final int GL_PERFQUERY_SINGLE_CONTEXT_INTEL = 0x00000000;

    public static final int GL_PERFQUERY_WAIT_INTEL = 0x83FB;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_PERSPECTIVE_CORRECTION_HINT = 0x0C50;

    /// Group: `TextureNormalModeEXT`
    public static final int GL_PERTURB_EXT = 0x85AE;

    /// Group: `BufferStorageMask`
    public static final int GL_PER_GPU_STORAGE_BIT_NV = 0x0800;

    public static final int GL_PER_GPU_STORAGE_NV = 0x9548;

    public static final int GL_PER_STAGE_CONSTANTS_NV = 0x8535;

    /// Group: `HintTarget`
    public static final int GL_PHONG_HINT_WIN = 0x80EB;

    public static final int GL_PHONG_WIN = 0x80EA;

    public static final int GL_PINLIGHT_NV = 0x92A8;

    public static final int GL_PIXELS_PER_SAMPLE_PATTERN_X_AMD = 0x91AE;

    public static final int GL_PIXELS_PER_SAMPLE_PATTERN_Y_AMD = 0x91AF;

    /// Group: `MemoryBarrierMask`
    public static final int GL_PIXEL_BUFFER_BARRIER_BIT = 0x00000080;

    /// Group: `MemoryBarrierMask`
    public static final int GL_PIXEL_BUFFER_BARRIER_BIT_EXT = 0x00000080;

    public static final int GL_PIXEL_COUNTER_BITS_NV = 0x8864;

    /// Group: `OcclusionQueryParameterNameNV`
    public static final int GL_PIXEL_COUNT_AVAILABLE_NV = 0x8867;

    /// Group: `OcclusionQueryParameterNameNV`
    public static final int GL_PIXEL_COUNT_NV = 0x8866;

    /// Group: `PixelTransformPNameEXT`
    public static final int GL_PIXEL_CUBIC_WEIGHT_EXT = 0x8333;

    /// Group: `PixelTexGenParameterNameSGIS`
    public static final int GL_PIXEL_FRAGMENT_ALPHA_SOURCE_SGIS = 0x8355;

    /// Group: `PixelTexGenParameterNameSGIS`
    public static final int GL_PIXEL_FRAGMENT_RGB_SOURCE_SGIS = 0x8354;

    public static final int GL_PIXEL_GROUP_COLOR_SGIS = 0x8356;

    /// Group: `PixelTransformPNameEXT`
    public static final int GL_PIXEL_MAG_FILTER_EXT = 0x8331;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_A_TO_A = 0x0C79;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_A_TO_A_SIZE = 0x0CB9;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_B_TO_B = 0x0C78;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_B_TO_B_SIZE = 0x0CB8;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_G_TO_G = 0x0C77;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_G_TO_G_SIZE = 0x0CB7;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_I_TO_A = 0x0C75;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_I_TO_A_SIZE = 0x0CB5;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_I_TO_B = 0x0C74;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_I_TO_B_SIZE = 0x0CB4;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_I_TO_G = 0x0C73;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_I_TO_G_SIZE = 0x0CB3;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_I_TO_I = 0x0C70;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_I_TO_I_SIZE = 0x0CB0;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_I_TO_R = 0x0C72;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_I_TO_R_SIZE = 0x0CB2;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_R_TO_R = 0x0C76;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_R_TO_R_SIZE = 0x0CB6;

    /// Group: `PixelMap`
    public static final int GL_PIXEL_MAP_S_TO_S = 0x0C71;

    /// Group: `GetPName`
    public static final int GL_PIXEL_MAP_S_TO_S_SIZE = 0x0CB1;

    /// Group: `PixelTransformPNameEXT`
    public static final int GL_PIXEL_MIN_FILTER_EXT = 0x8332;

    /// Group: `AttribMask`
    public static final int GL_PIXEL_MODE_BIT = 0x00000020;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_PIXEL_PACK_BUFFER = 0x88EB;

    public static final int GL_PIXEL_PACK_BUFFER_ARB = 0x88EB;

    /// Group: `GetPName`
    public static final int GL_PIXEL_PACK_BUFFER_BINDING = 0x88ED;

    public static final int GL_PIXEL_PACK_BUFFER_BINDING_ARB = 0x88ED;

    public static final int GL_PIXEL_PACK_BUFFER_BINDING_EXT = 0x88ED;

    public static final int GL_PIXEL_PACK_BUFFER_BINDING_NV = 0x88ED;

    public static final int GL_PIXEL_PACK_BUFFER_EXT = 0x88EB;

    public static final int GL_PIXEL_PACK_BUFFER_NV = 0x88EB;

    /// Group: `PixelStoreSubsampleRate`
    public static final int GL_PIXEL_SUBSAMPLE_2424_SGIX = 0x85A3;

    /// Group: `PixelStoreSubsampleRate`
    public static final int GL_PIXEL_SUBSAMPLE_4242_SGIX = 0x85A4;

    /// Group: `PixelStoreSubsampleRate`
    public static final int GL_PIXEL_SUBSAMPLE_4444_SGIX = 0x85A2;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_PIXEL_TEXTURE_SGIS = 0x8353;

    /// Group: `PixelTexGenModeSGIX`
    public static final int GL_PIXEL_TEX_GEN_ALPHA_LS_SGIX = 0x8189;

    /// Group: `PixelTexGenModeSGIX`
    public static final int GL_PIXEL_TEX_GEN_ALPHA_MS_SGIX = 0x818A;

    /// Group: `GetPName`
    public static final int GL_PIXEL_TEX_GEN_MODE_SGIX = 0x832B;

    /// Groups: `TextureMinFilter`, `PixelTexGenModeSGIX`, `TextureMagFilter`
    public static final int GL_PIXEL_TEX_GEN_Q_CEILING_SGIX = 0x8184;

    /// Groups: `TextureMinFilter`, `PixelTexGenModeSGIX`, `TextureMagFilter`
    public static final int GL_PIXEL_TEX_GEN_Q_FLOOR_SGIX = 0x8186;

    /// Groups: `TextureMinFilter`, `PixelTexGenModeSGIX`, `TextureMagFilter`
    public static final int GL_PIXEL_TEX_GEN_Q_ROUND_SGIX = 0x8185;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_PIXEL_TEX_GEN_SGIX = 0x8139;

    /// Group: `GetPName`
    public static final int GL_PIXEL_TILE_BEST_ALIGNMENT_SGIX = 0x813E;

    /// Group: `GetPName`
    public static final int GL_PIXEL_TILE_CACHE_INCREMENT_SGIX = 0x813F;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PIXEL_TILE_CACHE_SIZE_SGIX = 0x8145;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PIXEL_TILE_GRID_DEPTH_SGIX = 0x8144;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PIXEL_TILE_GRID_HEIGHT_SGIX = 0x8143;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PIXEL_TILE_GRID_WIDTH_SGIX = 0x8142;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PIXEL_TILE_HEIGHT_SGIX = 0x8141;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_PIXEL_TILE_WIDTH_SGIX = 0x8140;

    /// Group: `PixelTransformTargetEXT`
    public static final int GL_PIXEL_TRANSFORM_2D_EXT = 0x8330;

    public static final int GL_PIXEL_TRANSFORM_2D_MATRIX_EXT = 0x8338;

    public static final int GL_PIXEL_TRANSFORM_2D_STACK_DEPTH_EXT = 0x8336;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_PIXEL_UNPACK_BUFFER = 0x88EC;

    public static final int GL_PIXEL_UNPACK_BUFFER_ARB = 0x88EC;

    /// Group: `GetPName`
    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING = 0x88EF;

    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING_ARB = 0x88EF;

    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING_EXT = 0x88EF;

    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING_NV = 0x88EF;

    public static final int GL_PIXEL_UNPACK_BUFFER_EXT = 0x88EC;

    public static final int GL_PIXEL_UNPACK_BUFFER_NV = 0x88EC;

    public static final int GL_PLUS_CLAMPED_ALPHA_NV = 0x92B2;

    public static final int GL_PLUS_CLAMPED_NV = 0x92B1;

    public static final int GL_PLUS_DARKER_NV = 0x9292;

    public static final int GL_PLUS_NV = 0x9291;

    public static final int GL_PN_TRIANGLES_ATI = 0x87F0;

    /// Group: `PNTrianglesPNameATI`
    public static final int GL_PN_TRIANGLES_NORMAL_MODE_ATI = 0x87F3;

    public static final int GL_PN_TRIANGLES_NORMAL_MODE_LINEAR_ATI = 0x87F7;

    public static final int GL_PN_TRIANGLES_NORMAL_MODE_QUADRATIC_ATI = 0x87F8;

    /// Group: `PNTrianglesPNameATI`
    public static final int GL_PN_TRIANGLES_POINT_MODE_ATI = 0x87F2;

    public static final int GL_PN_TRIANGLES_POINT_MODE_CUBIC_ATI = 0x87F6;

    public static final int GL_PN_TRIANGLES_POINT_MODE_LINEAR_ATI = 0x87F5;

    /// Group: `PNTrianglesPNameATI`
    public static final int GL_PN_TRIANGLES_TESSELATION_LEVEL_ATI = 0x87F4;

    /// Groups: `PolygonMode`, `MeshMode1`, `MeshMode2`
    public static final int GL_POINT = 0x1B00;

    /// Group: `PrimitiveType`
    public static final int GL_POINTS = 0x0000;

    /// Group: `AttribMask`
    public static final int GL_POINT_BIT = 0x00000002;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_DISTANCE_ATTENUATION = 0x8129;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_DISTANCE_ATTENUATION_ARB = 0x8129;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_FADE_THRESHOLD_SIZE = 0x8128;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_FADE_THRESHOLD_SIZE_ARB = 0x8128;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_FADE_THRESHOLD_SIZE_EXT = 0x8128;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_FADE_THRESHOLD_SIZE_SGIS = 0x8128;

    public static final int GL_POINT_NV = 0x1B00;

    /// Group: `GetPName`
    public static final int GL_POINT_SIZE = 0x0B11;

    public static final int GL_POINT_SIZE_ARRAY_BUFFER_BINDING_OES = 0x8B9F;

    public static final int GL_POINT_SIZE_ARRAY_OES = 0x8B9C;

    public static final int GL_POINT_SIZE_ARRAY_POINTER_OES = 0x898C;

    public static final int GL_POINT_SIZE_ARRAY_STRIDE_OES = 0x898B;

    public static final int GL_POINT_SIZE_ARRAY_TYPE_OES = 0x898A;

    /// Group: `GetPName`
    public static final int GL_POINT_SIZE_GRANULARITY = 0x0B13;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_SIZE_MAX = 0x8127;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_SIZE_MAX_ARB = 0x8127;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_SIZE_MAX_EXT = 0x8127;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_SIZE_MAX_SGIS = 0x8127;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_SIZE_MIN = 0x8126;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_SIZE_MIN_ARB = 0x8126;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_SIZE_MIN_EXT = 0x8126;

    /// Groups: `PointParameterNameARB`, `GetPName`
    public static final int GL_POINT_SIZE_MIN_SGIS = 0x8126;

    /// Group: `GetPName`
    public static final int GL_POINT_SIZE_RANGE = 0x0B12;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_POINT_SMOOTH = 0x0B10;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_POINT_SMOOTH_HINT = 0x0C51;

    /// Group: `TextureEnvTarget`
    public static final int GL_POINT_SPRITE = 0x8861;

    public static final int GL_POINT_SPRITE_ARB = 0x8861;

    public static final int GL_POINT_SPRITE_COORD_ORIGIN = 0x8CA0;

    public static final int GL_POINT_SPRITE_NV = 0x8861;

    public static final int GL_POINT_SPRITE_OES = 0x8861;

    public static final int GL_POINT_SPRITE_R_MODE_NV = 0x8863;

    /// Group: `FeedBackToken`
    public static final int GL_POINT_TOKEN = 0x0701;

    /// Group: `PrimitiveType`
    public static final int GL_POLYGON = 0x0009;

    /// Group: `AttribMask`
    public static final int GL_POLYGON_BIT = 0x00000008;

    /// Group: `GetPName`
    public static final int GL_POLYGON_MODE = 0x0B40;

    public static final int GL_POLYGON_MODE_NV = 0x0B40;

    /// Group: `GetPName`
    public static final int GL_POLYGON_OFFSET_BIAS_EXT = 0x8039;

    public static final int GL_POLYGON_OFFSET_CLAMP = 0x8E1B;

    /// Alias: `GL_POLYGON_OFFSET_CLAMP`
    public static final int GL_POLYGON_OFFSET_CLAMP_EXT = 0x8E1B;

    /// Group: `CommandOpcodesNV`
    public static final int GL_POLYGON_OFFSET_COMMAND_NV = 0x000E;

    public static final int GL_POLYGON_OFFSET_EXT = 0x8037;

    /// Group: `GetPName`
    public static final int GL_POLYGON_OFFSET_FACTOR = 0x8038;

    public static final int GL_POLYGON_OFFSET_FACTOR_EXT = 0x8038;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_POLYGON_OFFSET_FILL = 0x8037;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_POLYGON_OFFSET_LINE = 0x2A02;

    public static final int GL_POLYGON_OFFSET_LINE_NV = 0x2A02;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_POLYGON_OFFSET_POINT = 0x2A01;

    public static final int GL_POLYGON_OFFSET_POINT_NV = 0x2A01;

    /// Group: `GetPName`
    public static final int GL_POLYGON_OFFSET_UNITS = 0x2A00;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_POLYGON_SMOOTH = 0x0B41;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_POLYGON_SMOOTH_HINT = 0x0C53;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_POLYGON_STIPPLE = 0x0B42;

    /// Group: `AttribMask`
    public static final int GL_POLYGON_STIPPLE_BIT = 0x00000010;

    /// Group: `FeedBackToken`
    public static final int GL_POLYGON_TOKEN = 0x0703;

    /// Groups: `LightParameter`, `FragmentLightParameterSGIX`
    public static final int GL_POSITION = 0x1203;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_COLOR_MATRIX_ALPHA_BIAS = 0x80BB;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_COLOR_MATRIX_ALPHA_BIAS_SGI = 0x80BB;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_COLOR_MATRIX_ALPHA_SCALE = 0x80B7;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_COLOR_MATRIX_ALPHA_SCALE_SGI = 0x80B7;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_COLOR_MATRIX_BLUE_BIAS = 0x80BA;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_COLOR_MATRIX_BLUE_BIAS_SGI = 0x80BA;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_COLOR_MATRIX_BLUE_SCALE = 0x80B6;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_COLOR_MATRIX_BLUE_SCALE_SGI = 0x80B6;

    /// Groups: `ColorTableTarget`, `ColorTableTargetSGI`, `EnableCap`
    public static final int GL_POST_COLOR_MATRIX_COLOR_TABLE = 0x80D2;

    /// Groups: `GetPName`, `ColorTableTargetSGI`, `EnableCap`
    public static final int GL_POST_COLOR_MATRIX_COLOR_TABLE_SGI = 0x80D2;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_COLOR_MATRIX_GREEN_BIAS = 0x80B9;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_COLOR_MATRIX_GREEN_BIAS_SGI = 0x80B9;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_COLOR_MATRIX_GREEN_SCALE = 0x80B5;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_COLOR_MATRIX_GREEN_SCALE_SGI = 0x80B5;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_COLOR_MATRIX_RED_BIAS = 0x80B8;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_COLOR_MATRIX_RED_BIAS_SGI = 0x80B8;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_COLOR_MATRIX_RED_SCALE = 0x80B4;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_COLOR_MATRIX_RED_SCALE_SGI = 0x80B4;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_CONVOLUTION_ALPHA_BIAS = 0x8023;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_CONVOLUTION_ALPHA_BIAS_EXT = 0x8023;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_CONVOLUTION_ALPHA_SCALE = 0x801F;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_CONVOLUTION_ALPHA_SCALE_EXT = 0x801F;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_CONVOLUTION_BLUE_BIAS = 0x8022;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_CONVOLUTION_BLUE_BIAS_EXT = 0x8022;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_CONVOLUTION_BLUE_SCALE = 0x801E;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_CONVOLUTION_BLUE_SCALE_EXT = 0x801E;

    /// Groups: `ColorTableTarget`, `ColorTableTargetSGI`, `EnableCap`
    public static final int GL_POST_CONVOLUTION_COLOR_TABLE = 0x80D1;

    /// Groups: `GetPName`, `ColorTableTargetSGI`, `EnableCap`
    public static final int GL_POST_CONVOLUTION_COLOR_TABLE_SGI = 0x80D1;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_CONVOLUTION_GREEN_BIAS = 0x8021;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_CONVOLUTION_GREEN_BIAS_EXT = 0x8021;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_CONVOLUTION_GREEN_SCALE = 0x801D;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_CONVOLUTION_GREEN_SCALE_EXT = 0x801D;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_CONVOLUTION_RED_BIAS = 0x8020;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_CONVOLUTION_RED_BIAS_EXT = 0x8020;

    /// Group: `PixelTransferParameter`
    public static final int GL_POST_CONVOLUTION_RED_SCALE = 0x801C;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_POST_CONVOLUTION_RED_SCALE_EXT = 0x801C;

    public static final int GL_POST_IMAGE_TRANSFORM_COLOR_TABLE_HP = 0x8162;

    /// Group: `GetPName`
    public static final int GL_POST_TEXTURE_FILTER_BIAS_RANGE_SGIX = 0x817B;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_POST_TEXTURE_FILTER_BIAS_SGIX = 0x8179;

    /// Group: `GetPName`
    public static final int GL_POST_TEXTURE_FILTER_SCALE_RANGE_SGIX = 0x817C;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_POST_TEXTURE_FILTER_SCALE_SGIX = 0x817A;

    /// Group: `HintTarget`
    public static final int GL_PREFER_DOUBLEBUFFER_HINT_PGI = 0x1A1F8;

    public static final int GL_PRESENT_DURATION_NV = 0x8E2B;

    public static final int GL_PRESENT_TIME_NV = 0x8E2A;

    /// Group: `PreserveModeATI`
    public static final int GL_PRESERVE_ATI = 0x8762;

    /// Group: `TextureEnvParameter`
    public static final int GL_PREVIOUS = 0x8578;

    /// Group: `TextureEnvParameter`
    public static final int GL_PREVIOUS_ARB = 0x8578;

    /// Group: `TextureEnvParameter`
    public static final int GL_PREVIOUS_EXT = 0x8578;

    public static final int GL_PREVIOUS_TEXTURE_INPUT_NV = 0x86E4;

    /// Groups: `TextureEnvParameter`, `PathColor`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_PRIMARY_COLOR = 0x8577;

    /// Groups: `TextureEnvParameter`, `FragmentShaderGenericSourceATI`
    public static final int GL_PRIMARY_COLOR_ARB = 0x8577;

    /// Groups: `TextureEnvParameter`, `FragmentShaderGenericSourceATI`
    public static final int GL_PRIMARY_COLOR_EXT = 0x8577;

    /// Groups: `PathColor`, `CombinerRegisterNV`
    public static final int GL_PRIMARY_COLOR_NV = 0x852C;

    /// Group: `QueryTarget`
    public static final int GL_PRIMITIVES_GENERATED = 0x8C87;

    public static final int GL_PRIMITIVES_GENERATED_EXT = 0x8C87;

    public static final int GL_PRIMITIVES_GENERATED_NV = 0x8C87;

    public static final int GL_PRIMITIVES_GENERATED_OES = 0x8C87;

    /// Group: `QueryTarget`
    public static final int GL_PRIMITIVES_SUBMITTED = 0x82EF;

    /// Alias: `GL_PRIMITIVES_SUBMITTED`
    public static final int GL_PRIMITIVES_SUBMITTED_ARB = 0x82EF;

    public static final int GL_PRIMITIVE_BOUNDING_BOX = 0x92BE;

    public static final int GL_PRIMITIVE_BOUNDING_BOX_ARB = 0x92BE;

    public static final int GL_PRIMITIVE_BOUNDING_BOX_EXT = 0x92BE;

    public static final int GL_PRIMITIVE_BOUNDING_BOX_OES = 0x92BE;

    public static final int GL_PRIMITIVE_ID_NV = 0x8C7C;

    /// Group: `EnableCap`
    public static final int GL_PRIMITIVE_RESTART = 0x8F9D;

    /// Group: `EnableCap`
    public static final int GL_PRIMITIVE_RESTART_FIXED_INDEX = 0x8D69;

    public static final int GL_PRIMITIVE_RESTART_FOR_PATCHES_SUPPORTED = 0x8221;

    public static final int GL_PRIMITIVE_RESTART_FOR_PATCHES_SUPPORTED_OES = 0x8221;

    /// Group: `GetPName`
    public static final int GL_PRIMITIVE_RESTART_INDEX = 0x8F9E;

    public static final int GL_PRIMITIVE_RESTART_INDEX_NV = 0x8559;

    public static final int GL_PRIMITIVE_RESTART_NV = 0x8558;

    /// Group: `ObjectIdentifier`
    public static final int GL_PROGRAM = 0x82E2;

    /// Group: `GetMultisamplePNameNV`
    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_ARB = 0x9341;

    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_NV = 0x9341;

    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_TABLE_SIZE_ARB = 0x9340;

    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_TABLE_SIZE_NV = 0x9340;

    public static final int GL_PROGRAM_ADDRESS_REGISTERS_ARB = 0x88B0;

    public static final int GL_PROGRAM_ALU_INSTRUCTIONS_ARB = 0x8805;

    public static final int GL_PROGRAM_ATTRIBS_ARB = 0x88AC;

    public static final int GL_PROGRAM_ATTRIB_COMPONENTS_NV = 0x8906;

    public static final int GL_PROGRAM_BINARY_ANGLE = 0x93A6;

    /// Group: `GetPName`
    public static final int GL_PROGRAM_BINARY_FORMATS = 0x87FF;

    public static final int GL_PROGRAM_BINARY_FORMATS_OES = 0x87FF;

    public static final int GL_PROGRAM_BINARY_FORMAT_MESA = 0x875F;

    /// Group: `ProgramPropertyARB`
    public static final int GL_PROGRAM_BINARY_LENGTH = 0x8741;

    public static final int GL_PROGRAM_BINARY_LENGTH_OES = 0x8741;

    /// Groups: `ProgramParameterPName`, `HintTarget`
    public static final int GL_PROGRAM_BINARY_RETRIEVABLE_HINT = 0x8257;

    public static final int GL_PROGRAM_BINDING_ARB = 0x8677;

    public static final int GL_PROGRAM_ERROR_POSITION_ARB = 0x864B;

    public static final int GL_PROGRAM_ERROR_POSITION_NV = 0x864B;

    public static final int GL_PROGRAM_ERROR_STRING_ARB = 0x8874;

    public static final int GL_PROGRAM_ERROR_STRING_NV = 0x8874;

    public static final int GL_PROGRAM_FORMAT_ARB = 0x8876;

    /// Group: `ProgramFormat`
    public static final int GL_PROGRAM_FORMAT_ASCII_ARB = 0x8875;

    /// Group: `ProgramInterface`
    public static final int GL_PROGRAM_INPUT = 0x92E3;

    public static final int GL_PROGRAM_INSTRUCTIONS_ARB = 0x88A0;

    public static final int GL_PROGRAM_KHR = 0x82E2;

    public static final int GL_PROGRAM_LENGTH_ARB = 0x8627;

    public static final int GL_PROGRAM_LENGTH_NV = 0x8627;

    public static final int GL_PROGRAM_MATRIX_EXT = 0x8E2D;

    public static final int GL_PROGRAM_MATRIX_STACK_DEPTH_EXT = 0x8E2F;

    public static final int GL_PROGRAM_NATIVE_ADDRESS_REGISTERS_ARB = 0x88B2;

    public static final int GL_PROGRAM_NATIVE_ALU_INSTRUCTIONS_ARB = 0x8808;

    public static final int GL_PROGRAM_NATIVE_ATTRIBS_ARB = 0x88AE;

    public static final int GL_PROGRAM_NATIVE_INSTRUCTIONS_ARB = 0x88A2;

    public static final int GL_PROGRAM_NATIVE_PARAMETERS_ARB = 0x88AA;

    public static final int GL_PROGRAM_NATIVE_TEMPORARIES_ARB = 0x88A6;

    public static final int GL_PROGRAM_NATIVE_TEX_INDIRECTIONS_ARB = 0x880A;

    public static final int GL_PROGRAM_NATIVE_TEX_INSTRUCTIONS_ARB = 0x8809;

    /// Group: `ContainerType`
    public static final int GL_PROGRAM_OBJECT_ARB = 0x8B40;

    /// Group: `ContainerType`
    public static final int GL_PROGRAM_OBJECT_EXT = 0x8B40;

    /// Group: `ProgramInterface`
    public static final int GL_PROGRAM_OUTPUT = 0x92E4;

    public static final int GL_PROGRAM_PARAMETERS_ARB = 0x88A8;

    /// Group: `VertexAttribEnumNV`
    public static final int GL_PROGRAM_PARAMETER_NV = 0x8644;

    /// Group: `ObjectIdentifier`
    public static final int GL_PROGRAM_PIPELINE = 0x82E4;

    /// Group: `GetPName`
    public static final int GL_PROGRAM_PIPELINE_BINDING = 0x825A;

    public static final int GL_PROGRAM_PIPELINE_BINDING_EXT = 0x825A;

    public static final int GL_PROGRAM_PIPELINE_KHR = 0x82E4;

    public static final int GL_PROGRAM_PIPELINE_OBJECT_EXT = 0x8A4F;

    /// Groups: `GetPName`, `EnableCap`
    /// Alias: `GL_VERTEX_PROGRAM_POINT_SIZE`
    public static final int GL_PROGRAM_POINT_SIZE = 0x8642;

    public static final int GL_PROGRAM_POINT_SIZE_ARB = 0x8642;

    public static final int GL_PROGRAM_POINT_SIZE_EXT = 0x8642;

    public static final int GL_PROGRAM_RESIDENT_NV = 0x8647;

    public static final int GL_PROGRAM_RESULT_COMPONENTS_NV = 0x8907;

    /// Group: `ProgramParameterPName`
    public static final int GL_PROGRAM_SEPARABLE = 0x8258;

    public static final int GL_PROGRAM_SEPARABLE_EXT = 0x8258;

    /// Group: `ProgramStringProperty`
    public static final int GL_PROGRAM_STRING_ARB = 0x8628;

    public static final int GL_PROGRAM_STRING_NV = 0x8628;

    public static final int GL_PROGRAM_TARGET_NV = 0x8646;

    public static final int GL_PROGRAM_TEMPORARIES_ARB = 0x88A4;

    public static final int GL_PROGRAM_TEX_INDIRECTIONS_ARB = 0x8807;

    public static final int GL_PROGRAM_TEX_INSTRUCTIONS_ARB = 0x8806;

    public static final int GL_PROGRAM_UNDER_NATIVE_LIMITS_ARB = 0x88B6;

    /// Group: `MatrixMode`
    public static final int GL_PROJECTION = 0x1701;

    /// Group: `GetPName`
    public static final int GL_PROJECTION_MATRIX = 0x0BA7;

    public static final int GL_PROJECTION_MATRIX_FLOAT_AS_INT_BITS_OES = 0x898E;

    /// Group: `GetPName`
    public static final int GL_PROJECTION_STACK_DEPTH = 0x0BA4;

    /// Group: `MemoryObjectParameterName`
    public static final int GL_PROTECTED_MEMORY_OBJECT_EXT = 0x959B;

    /// Group: `GetPName`
    public static final int GL_PROVOKING_VERTEX = 0x8E4F;

    public static final int GL_PROVOKING_VERTEX_EXT = 0x8E4F;

    /// Groups: `ColorTableTargetSGI`, `ColorTableTarget`
    public static final int GL_PROXY_COLOR_TABLE = 0x80D3;

    /// Group: `ColorTableTargetSGI`
    public static final int GL_PROXY_COLOR_TABLE_SGI = 0x80D3;

    /// Groups: `HistogramTarget`, `HistogramTargetEXT`
    public static final int GL_PROXY_HISTOGRAM = 0x8025;

    /// Group: `HistogramTargetEXT`
    public static final int GL_PROXY_HISTOGRAM_EXT = 0x8025;

    /// Groups: `ColorTableTargetSGI`, `ColorTableTarget`
    public static final int GL_PROXY_POST_COLOR_MATRIX_COLOR_TABLE = 0x80D5;

    /// Group: `ColorTableTargetSGI`
    public static final int GL_PROXY_POST_COLOR_MATRIX_COLOR_TABLE_SGI = 0x80D5;

    /// Groups: `ColorTableTargetSGI`, `ColorTableTarget`
    public static final int GL_PROXY_POST_CONVOLUTION_COLOR_TABLE = 0x80D4;

    /// Group: `ColorTableTargetSGI`
    public static final int GL_PROXY_POST_CONVOLUTION_COLOR_TABLE_SGI = 0x80D4;

    public static final int GL_PROXY_POST_IMAGE_TRANSFORM_COLOR_TABLE_HP = 0x8163;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_1D = 0x8063;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_1D_ARRAY = 0x8C19;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_1D_ARRAY_EXT = 0x8C19;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_1D_EXT = 0x8063;

    public static final int GL_PROXY_TEXTURE_1D_STACK_MESAX = 0x875B;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_2D = 0x8064;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_2D_ARRAY = 0x8C1B;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_2D_ARRAY_EXT = 0x8C1B;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_2D_EXT = 0x8064;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 0x9101;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 0x9103;

    public static final int GL_PROXY_TEXTURE_2D_STACK_MESAX = 0x875C;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_3D = 0x8070;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_3D_EXT = 0x8070;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_4D_SGIS = 0x8135;

    /// Group: `ColorTableTargetSGI`
    public static final int GL_PROXY_TEXTURE_COLOR_TABLE_SGI = 0x80BD;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_CUBE_MAP = 0x851B;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_CUBE_MAP_ARB = 0x851B;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_CUBE_MAP_ARRAY = 0x900B;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_CUBE_MAP_ARRAY_ARB = 0x900B;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_CUBE_MAP_EXT = 0x851B;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_RECTANGLE = 0x84F7;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_RECTANGLE_ARB = 0x84F7;

    /// Group: `TextureTarget`
    public static final int GL_PROXY_TEXTURE_RECTANGLE_NV = 0x84F7;

    public static final int GL_PURGEABLE_APPLE = 0x8A1D;

    public static final int GL_PURGED_CONTEXT_RESET_NV = 0x92BB;

    /// Group: `TextureCoordName`
    public static final int GL_Q = 0x2003;

    /// Groups: `LightParameter`, `FragmentLightParameterSGIX`
    public static final int GL_QUADRATIC_ATTENUATION = 0x1209;

    /// Group: `PathCoordType`
    public static final int GL_QUADRATIC_CURVE_TO_NV = 0x0A;

    /// Group: `PrimitiveType`
    public static final int GL_QUADS = 0x0007;

    /// Group: `PrimitiveType`
    public static final int GL_QUADS_EXT = 0x0007;

    public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 0x8E4C;

    public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION_EXT = 0x8E4C;

    public static final int GL_QUADS_OES = 0x0007;

    /// Group: `InternalFormat`
    public static final int GL_QUAD_ALPHA4_SGIS = 0x811E;

    /// Group: `InternalFormat`
    public static final int GL_QUAD_ALPHA8_SGIS = 0x811F;

    /// Group: `InternalFormat`
    public static final int GL_QUAD_INTENSITY4_SGIS = 0x8122;

    /// Group: `InternalFormat`
    public static final int GL_QUAD_INTENSITY8_SGIS = 0x8123;

    /// Group: `InternalFormat`
    public static final int GL_QUAD_LUMINANCE4_SGIS = 0x8120;

    /// Group: `InternalFormat`
    public static final int GL_QUAD_LUMINANCE8_SGIS = 0x8121;

    public static final int GL_QUAD_MESH_SUN = 0x8614;

    /// Group: `PrimitiveType`
    public static final int GL_QUAD_STRIP = 0x0008;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_QUAD_TEXTURE_SELECT_SGIS = 0x8125;

    /// Group: `FragmentShaderDestModMaskATI`
    public static final int GL_QUARTER_BIT_ATI = 0x00000010;

    /// Group: `ObjectIdentifier`
    public static final int GL_QUERY = 0x82E3;

    /// Group: `OcclusionQueryEventMaskAMD`
    public static final int GL_QUERY_ALL_EVENT_BITS_AMD = 0xFFFFFFFF;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_QUERY_BUFFER = 0x9192;

    public static final int GL_QUERY_BUFFER_AMD = 0x9192;

    /// Group: `MemoryBarrierMask`
    public static final int GL_QUERY_BUFFER_BARRIER_BIT = 0x00008000;

    public static final int GL_QUERY_BUFFER_BINDING = 0x9193;

    public static final int GL_QUERY_BUFFER_BINDING_AMD = 0x9193;

    /// Group: `ConditionalRenderMode`
    public static final int GL_QUERY_BY_REGION_NO_WAIT = 0x8E16;

    /// Group: `ConditionalRenderMode`
    public static final int GL_QUERY_BY_REGION_NO_WAIT_INVERTED = 0x8E1A;

    public static final int GL_QUERY_BY_REGION_NO_WAIT_NV = 0x8E16;

    /// Group: `ConditionalRenderMode`
    public static final int GL_QUERY_BY_REGION_WAIT = 0x8E15;

    /// Group: `ConditionalRenderMode`
    public static final int GL_QUERY_BY_REGION_WAIT_INVERTED = 0x8E19;

    public static final int GL_QUERY_BY_REGION_WAIT_NV = 0x8E15;

    /// Group: `QueryParameterName`
    public static final int GL_QUERY_COUNTER_BITS = 0x8864;

    public static final int GL_QUERY_COUNTER_BITS_ARB = 0x8864;

    public static final int GL_QUERY_COUNTER_BITS_EXT = 0x8864;

    /// Group: `OcclusionQueryEventMaskAMD`
    public static final int GL_QUERY_DEPTH_BOUNDS_FAIL_EVENT_BIT_AMD = 0x00000008;

    /// Group: `OcclusionQueryEventMaskAMD`
    public static final int GL_QUERY_DEPTH_FAIL_EVENT_BIT_AMD = 0x00000002;

    /// Group: `OcclusionQueryEventMaskAMD`
    public static final int GL_QUERY_DEPTH_PASS_EVENT_BIT_AMD = 0x00000001;

    public static final int GL_QUERY_KHR = 0x82E3;

    /// Group: `ConditionalRenderMode`
    public static final int GL_QUERY_NO_WAIT = 0x8E14;

    /// Group: `ConditionalRenderMode`
    public static final int GL_QUERY_NO_WAIT_INVERTED = 0x8E18;

    public static final int GL_QUERY_NO_WAIT_NV = 0x8E14;

    public static final int GL_QUERY_OBJECT_AMD = 0x9153;

    public static final int GL_QUERY_OBJECT_EXT = 0x9153;

    public static final int GL_QUERY_RESOURCE_BUFFEROBJECT_NV = 0x9547;

    public static final int GL_QUERY_RESOURCE_MEMTYPE_VIDMEM_NV = 0x9542;

    public static final int GL_QUERY_RESOURCE_RENDERBUFFER_NV = 0x9546;

    public static final int GL_QUERY_RESOURCE_SYS_RESERVED_NV = 0x9544;

    public static final int GL_QUERY_RESOURCE_TEXTURE_NV = 0x9545;

    public static final int GL_QUERY_RESOURCE_TYPE_VIDMEM_ALLOC_NV = 0x9540;

    /// Group: `QueryObjectParameterName`
    public static final int GL_QUERY_RESULT = 0x8866;

    public static final int GL_QUERY_RESULT_ARB = 0x8866;

    /// Group: `QueryObjectParameterName`
    public static final int GL_QUERY_RESULT_AVAILABLE = 0x8867;

    public static final int GL_QUERY_RESULT_AVAILABLE_ARB = 0x8867;

    public static final int GL_QUERY_RESULT_AVAILABLE_EXT = 0x8867;

    public static final int GL_QUERY_RESULT_EXT = 0x8866;

    /// Group: `QueryObjectParameterName`
    public static final int GL_QUERY_RESULT_NO_WAIT = 0x9194;

    public static final int GL_QUERY_RESULT_NO_WAIT_AMD = 0x9194;

    /// Group: `OcclusionQueryEventMaskAMD`
    public static final int GL_QUERY_STENCIL_FAIL_EVENT_BIT_AMD = 0x00000004;

    /// Group: `QueryObjectParameterName`
    public static final int GL_QUERY_TARGET = 0x82EA;

    /// Group: `ConditionalRenderMode`
    public static final int GL_QUERY_WAIT = 0x8E13;

    /// Group: `ConditionalRenderMode`
    public static final int GL_QUERY_WAIT_INVERTED = 0x8E17;

    public static final int GL_QUERY_WAIT_NV = 0x8E13;

    /// Group: `TextureCoordName`
    public static final int GL_R = 0x2002;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R11F_G11F_B10F = 0x8C3A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R11F_G11F_B10F_APPLE = 0x8C3A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R11F_G11F_B10F_EXT = 0x8C3A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R16 = 0x822A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R16F = 0x822D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R16F_EXT = 0x822D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R16I = 0x8233;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R16UI = 0x8234;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R16_EXT = 0x822A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R16_SNORM = 0x8F98;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R16_SNORM_EXT = 0x8F98;

    public static final int GL_R1UI_C3F_V3F_SUN = 0x85C6;

    public static final int GL_R1UI_C4F_N3F_V3F_SUN = 0x85C8;

    public static final int GL_R1UI_C4UB_V3F_SUN = 0x85C5;

    public static final int GL_R1UI_N3F_V3F_SUN = 0x85C7;

    public static final int GL_R1UI_T2F_C4F_N3F_V3F_SUN = 0x85CB;

    public static final int GL_R1UI_T2F_N3F_V3F_SUN = 0x85CA;

    public static final int GL_R1UI_T2F_V3F_SUN = 0x85C9;

    public static final int GL_R1UI_V3F_SUN = 0x85C4;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R32F = 0x822E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R32F_EXT = 0x822E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R32I = 0x8235;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R32UI = 0x8236;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R3_G3_B2 = 0x2A10;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R8 = 0x8229;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R8I = 0x8231;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R8UI = 0x8232;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R8_EXT = 0x8229;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_R8_SNORM = 0x8F94;

    /// Group: `EnableCap`
    public static final int GL_RASTERIZER_DISCARD = 0x8C89;

    public static final int GL_RASTERIZER_DISCARD_EXT = 0x8C89;

    public static final int GL_RASTERIZER_DISCARD_NV = 0x8C89;

    public static final int GL_RASTER_FIXED_SAMPLE_LOCATIONS_EXT = 0x932A;

    public static final int GL_RASTER_MULTISAMPLE_EXT = 0x9327;

    public static final int GL_RASTER_POSITION_UNCLIPPED_IBM = 0x19262;

    public static final int GL_RASTER_SAMPLES_EXT = 0x9328;

    /// Group: `GetPName`
    public static final int GL_READ_BUFFER = 0x0C02;

    /// Group: `GetPName`
    public static final int GL_READ_BUFFER_EXT = 0x0C02;

    /// Group: `GetPName`
    public static final int GL_READ_BUFFER_NV = 0x0C02;

    /// Group: `FramebufferTarget`
    public static final int GL_READ_FRAMEBUFFER = 0x8CA8;

    public static final int GL_READ_FRAMEBUFFER_ANGLE = 0x8CA8;

    public static final int GL_READ_FRAMEBUFFER_APPLE = 0x8CA8;

    /// Group: `GetPName`
    public static final int GL_READ_FRAMEBUFFER_BINDING = 0x8CAA;

    public static final int GL_READ_FRAMEBUFFER_BINDING_ANGLE = 0x8CAA;

    public static final int GL_READ_FRAMEBUFFER_BINDING_APPLE = 0x8CAA;

    public static final int GL_READ_FRAMEBUFFER_BINDING_EXT = 0x8CAA;

    public static final int GL_READ_FRAMEBUFFER_BINDING_NV = 0x8CAA;

    public static final int GL_READ_FRAMEBUFFER_EXT = 0x8CA8;

    public static final int GL_READ_FRAMEBUFFER_NV = 0x8CA8;

    /// Group: `BufferAccessARB`
    public static final int GL_READ_ONLY = 0x88B8;

    public static final int GL_READ_ONLY_ARB = 0x88B8;

    /// Group: `InternalFormatPName`
    public static final int GL_READ_PIXELS = 0x828C;

    /// Group: `InternalFormatPName`
    public static final int GL_READ_PIXELS_FORMAT = 0x828D;

    /// Group: `InternalFormatPName`
    public static final int GL_READ_PIXELS_TYPE = 0x828E;

    public static final int GL_READ_PIXEL_DATA_RANGE_LENGTH_NV = 0x887B;

    /// Group: `PixelDataRangeTargetNV`
    public static final int GL_READ_PIXEL_DATA_RANGE_NV = 0x8879;

    public static final int GL_READ_PIXEL_DATA_RANGE_POINTER_NV = 0x887D;

    /// Group: `BufferAccessARB`
    public static final int GL_READ_WRITE = 0x88BA;

    public static final int GL_READ_WRITE_ARB = 0x88BA;

    public static final int GL_RECIP_ADD_SIGNED_ALPHA_IMG = 0x8C05;

    /// Group: `HintTarget`
    public static final int GL_RECLAIM_MEMORY_HINT_PGI = 0x1A1FE;

    /// Group: `PathCoordType`
    public static final int GL_RECT_NV = 0xF6;

    /// Groups: `FragmentShaderValueRepATI`, `TextureSwizzle`, `PixelFormat`,
    ///   `InternalFormat`
    public static final int GL_RED = 0x1903;

    /// Group: `ConvolutionBorderModeEXT`
    public static final int GL_REDUCE = 0x8016;

    /// Group: `ConvolutionBorderModeEXT`
    public static final int GL_REDUCE_EXT = 0x8016;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_RED_BIAS = 0x0D15;

    /// Group: `GetPName`
    public static final int GL_RED_BITS = 0x0D52;

    /// Group: `FragmentShaderDestMaskATI`
    public static final int GL_RED_BIT_ATI = 0x00000001;

    /// Groups: `InternalFormat`, `PixelFormat`
    public static final int GL_RED_EXT = 0x1903;

    /// Group: `PixelFormat`
    public static final int GL_RED_INTEGER = 0x8D94;

    public static final int GL_RED_INTEGER_EXT = 0x8D94;

    public static final int GL_RED_MAX_CLAMP_INGR = 0x8564;

    public static final int GL_RED_MIN_CLAMP_INGR = 0x8560;

    public static final int GL_RED_NV = 0x1903;

    /// Groups: `PixelTransferParameter`, `GetPName`
    public static final int GL_RED_SCALE = 0x0D14;

    public static final int GL_RED_SNORM = 0x8F90;

    /// Group: `ProgramResourceProperty`
    public static final int GL_REFERENCED_BY_COMPUTE_SHADER = 0x930B;

    /// Group: `ProgramResourceProperty`
    public static final int GL_REFERENCED_BY_FRAGMENT_SHADER = 0x930A;

    /// Group: `ProgramResourceProperty`
    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER = 0x9309;

    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER_EXT = 0x9309;

    public static final int GL_REFERENCED_BY_GEOMETRY_SHADER_OES = 0x9309;

    public static final int GL_REFERENCED_BY_MESH_SHADER_NV = 0x95A0;

    public static final int GL_REFERENCED_BY_TASK_SHADER_NV = 0x95A1;

    /// Group: `ProgramResourceProperty`
    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER = 0x9307;

    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER_EXT = 0x9307;

    public static final int GL_REFERENCED_BY_TESS_CONTROL_SHADER_OES = 0x9307;

    /// Group: `ProgramResourceProperty`
    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER = 0x9308;

    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER_EXT = 0x9308;

    public static final int GL_REFERENCED_BY_TESS_EVALUATION_SHADER_OES = 0x9308;

    /// Group: `ProgramResourceProperty`
    public static final int GL_REFERENCED_BY_VERTEX_SHADER = 0x9306;

    /// Group: `GetPName`
    public static final int GL_REFERENCE_PLANE_EQUATION_SGIX = 0x817E;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_REFERENCE_PLANE_SGIX = 0x817D;

    /// Group: `GetTextureParameter`
    public static final int GL_REFLECTION_MAP = 0x8512;

    /// Group: `GetTextureParameter`
    public static final int GL_REFLECTION_MAP_ARB = 0x8512;

    /// Group: `GetTextureParameter`
    public static final int GL_REFLECTION_MAP_EXT = 0x8512;

    /// Group: `GetTextureParameter`
    public static final int GL_REFLECTION_MAP_NV = 0x8512;

    /// Group: `GetTextureParameter`
    public static final int GL_REFLECTION_MAP_OES = 0x8512;

    public static final int GL_REGISTER_COMBINERS_NV = 0x8522;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_0_ATI = 0x8921;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_10_ATI = 0x892B;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_11_ATI = 0x892C;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_12_ATI = 0x892D;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_13_ATI = 0x892E;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_14_ATI = 0x892F;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_15_ATI = 0x8930;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_16_ATI = 0x8931;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_17_ATI = 0x8932;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_18_ATI = 0x8933;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_19_ATI = 0x8934;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_1_ATI = 0x8922;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_20_ATI = 0x8935;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_21_ATI = 0x8936;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_22_ATI = 0x8937;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_23_ATI = 0x8938;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_24_ATI = 0x8939;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_25_ATI = 0x893A;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_26_ATI = 0x893B;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_27_ATI = 0x893C;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_28_ATI = 0x893D;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_29_ATI = 0x893E;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_2_ATI = 0x8923;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_30_ATI = 0x893F;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_31_ATI = 0x8940;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_3_ATI = 0x8924;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_4_ATI = 0x8925;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_5_ATI = 0x8926;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_6_ATI = 0x8927;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_7_ATI = 0x8928;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_8_ATI = 0x8929;

    /// Groups: `FragmentShaderRegATI`, `FragmentShaderTextureSourceATI`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_REG_9_ATI = 0x892A;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_ARC_TO_NV = 0xFF;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_CONIC_CURVE_TO_NV = 0x1B;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_CUBIC_CURVE_TO_NV = 0x0D;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_HORIZONTAL_LINE_TO_NV = 0x07;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_LARGE_CCW_ARC_TO_NV = 0x17;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_LARGE_CW_ARC_TO_NV = 0x19;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_LINE_TO_NV = 0x05;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_MOVE_TO_NV = 0x03;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_QUADRATIC_CURVE_TO_NV = 0x0B;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_RECT_NV = 0xF7;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_ROUNDED_RECT2_NV = 0xEB;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_ROUNDED_RECT4_NV = 0xED;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_ROUNDED_RECT8_NV = 0xEF;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_ROUNDED_RECT_NV = 0xE9;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_SMALL_CCW_ARC_TO_NV = 0x13;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_SMALL_CW_ARC_TO_NV = 0x15;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_SMOOTH_CUBIC_CURVE_TO_NV = 0x11;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_SMOOTH_QUADRATIC_CURVE_TO_NV = 0x0F;

    /// Group: `PathCoordType`
    public static final int GL_RELATIVE_VERTICAL_LINE_TO_NV = 0x09;

    public static final int GL_RELEASED_APPLE = 0x8A19;

    /// Group: `RenderingMode`
    public static final int GL_RENDER = 0x1C00;

    /// Groups: `ObjectIdentifier`, `RenderbufferTarget`,
    ///   `CopyImageSubDataTarget`, `TextureTarget`
    public static final int GL_RENDERBUFFER = 0x8D41;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_ALPHA_SIZE = 0x8D53;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_ALPHA_SIZE_EXT = 0x8D53;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_ALPHA_SIZE_OES = 0x8D53;

    /// Group: `GetPName`
    public static final int GL_RENDERBUFFER_BINDING = 0x8CA7;

    public static final int GL_RENDERBUFFER_BINDING_ANGLE = 0x8CA7;

    public static final int GL_RENDERBUFFER_BINDING_EXT = 0x8CA7;

    public static final int GL_RENDERBUFFER_BINDING_OES = 0x8CA7;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_BLUE_SIZE = 0x8D52;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_BLUE_SIZE_EXT = 0x8D52;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_BLUE_SIZE_OES = 0x8D52;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_COLOR_SAMPLES_NV = 0x8E10;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_COVERAGE_SAMPLES_NV = 0x8CAB;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_DEPTH_SIZE = 0x8D54;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_DEPTH_SIZE_EXT = 0x8D54;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_DEPTH_SIZE_OES = 0x8D54;

    public static final int GL_RENDERBUFFER_EXT = 0x8D41;

    public static final int GL_RENDERBUFFER_FREE_MEMORY_ATI = 0x87FD;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_GREEN_SIZE = 0x8D51;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_GREEN_SIZE_EXT = 0x8D51;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_GREEN_SIZE_OES = 0x8D51;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_HEIGHT = 0x8D43;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_HEIGHT_EXT = 0x8D43;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_HEIGHT_OES = 0x8D43;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 0x8D44;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT_EXT = 0x8D44;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT_OES = 0x8D44;

    /// Group: `RenderbufferTarget`
    public static final int GL_RENDERBUFFER_OES = 0x8D41;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_RED_SIZE = 0x8D50;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_RED_SIZE_EXT = 0x8D50;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_RED_SIZE_OES = 0x8D50;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_SAMPLES = 0x8CAB;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_SAMPLES_ANGLE = 0x8CAB;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_SAMPLES_APPLE = 0x8CAB;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_SAMPLES_EXT = 0x8CAB;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_SAMPLES_IMG = 0x9133;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_SAMPLES_NV = 0x8CAB;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_STENCIL_SIZE = 0x8D55;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_STENCIL_SIZE_EXT = 0x8D55;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_STENCIL_SIZE_OES = 0x8D55;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_STORAGE_SAMPLES_AMD = 0x91B2;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_WIDTH = 0x8D42;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_WIDTH_EXT = 0x8D42;

    /// Group: `RenderbufferParameterName`
    public static final int GL_RENDERBUFFER_WIDTH_OES = 0x8D42;

    /// Group: `StringName`
    public static final int GL_RENDERER = 0x1F01;

    public static final int GL_RENDER_DIRECT_TO_FRAMEBUFFER_QCOM = 0x8FB3;

    public static final int GL_RENDER_GPU_MASK_NV = 0x9558;

    /// Group: `GetPName`
    public static final int GL_RENDER_MODE = 0x0C40;

    /// Group: `TextureWrapMode`
    public static final int GL_REPEAT = 0x2901;

    /// Groups: `StencilOp`, `LightEnvModeSGIX`, `TextureEnvMode`
    public static final int GL_REPLACE = 0x1E01;

    public static final int GL_REPLACEMENT_CODE_ARRAY_POINTER_SUN = 0x85C3;

    public static final int GL_REPLACEMENT_CODE_ARRAY_STRIDE_SUN = 0x85C2;

    public static final int GL_REPLACEMENT_CODE_ARRAY_SUN = 0x85C0;

    public static final int GL_REPLACEMENT_CODE_ARRAY_TYPE_SUN = 0x85C1;

    public static final int GL_REPLACEMENT_CODE_SUN = 0x81D8;

    /// Group: `TextureEnvMode`
    public static final int GL_REPLACE_EXT = 0x8062;

    /// Group: `TriangleListSUN`
    public static final int GL_REPLACE_MIDDLE_SUN = 0x0002;

    /// Group: `TriangleListSUN`
    public static final int GL_REPLACE_OLDEST_SUN = 0x0003;

    public static final int GL_REPLACE_VALUE_AMD = 0x874B;

    public static final int GL_REPLICATE_BORDER = 0x8153;

    public static final int GL_REPLICATE_BORDER_HP = 0x8153;

    public static final int GL_REPRESENTATIVE_FRAGMENT_TEST_NV = 0x937F;

    public static final int GL_REQUIRED_TEXTURE_IMAGE_UNITS_OES = 0x8D68;

    public static final int GL_RESAMPLE_AVERAGE_OML = 0x8988;

    public static final int GL_RESAMPLE_DECIMATE_OML = 0x8989;

    /// Group: `PixelStoreResampleMode`
    public static final int GL_RESAMPLE_DECIMATE_SGIX = 0x8430;

    public static final int GL_RESAMPLE_REPLICATE_OML = 0x8986;

    /// Group: `PixelStoreResampleMode`
    public static final int GL_RESAMPLE_REPLICATE_SGIX = 0x8433;

    public static final int GL_RESAMPLE_ZERO_FILL_OML = 0x8987;

    /// Group: `PixelStoreResampleMode`
    public static final int GL_RESAMPLE_ZERO_FILL_SGIX = 0x8434;

    public static final int GL_RESCALE_NORMAL = 0x803A;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_RESCALE_NORMAL_EXT = 0x803A;

    public static final int GL_RESET_NOTIFICATION_STRATEGY = 0x8256;

    public static final int GL_RESET_NOTIFICATION_STRATEGY_ARB = 0x8256;

    public static final int GL_RESET_NOTIFICATION_STRATEGY_EXT = 0x8256;

    public static final int GL_RESET_NOTIFICATION_STRATEGY_KHR = 0x8256;

    /// Group: `PathCoordType`
    public static final int GL_RESTART_PATH_NV = 0xF0;

    /// Group: `TriangleListSUN`
    public static final int GL_RESTART_SUN = 0x0001;

    public static final int GL_RETAINED_APPLE = 0x8A1B;

    /// Group: `AccumOp`
    public static final int GL_RETURN = 0x0102;

    /// Groups: `InternalFormat`, `PixelFormat`
    public static final int GL_RG = 0x8227;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG16 = 0x822C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG16F = 0x822F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG16F_EXT = 0x822F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG16I = 0x8239;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG16UI = 0x823A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG16_EXT = 0x822C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG16_SNORM = 0x8F99;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG16_SNORM_EXT = 0x8F99;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG32F = 0x8230;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG32F_EXT = 0x8230;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG32I = 0x823B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG32UI = 0x823C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG8 = 0x822B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG8I = 0x8237;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG8UI = 0x8238;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG8_EXT = 0x822B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RG8_SNORM = 0x8F95;

    /// Groups: `PixelTexGenModeSGIX`, `CombinerPortionNV`, `PathColorFormat`,
    ///   `CombinerComponentUsageNV`, `PixelFormat`, `InternalFormat`
    public static final int GL_RGB = 0x1907;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB10 = 0x8052;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB10_A2 = 0x8059;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB10_A2UI = 0x906F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB10_A2_EXT = 0x8059;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB10_EXT = 0x8052;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB12 = 0x8053;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB12_EXT = 0x8053;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16 = 0x8054;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16F = 0x881B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16F_ARB = 0x881B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16F_EXT = 0x881B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16I = 0x8D89;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16I_EXT = 0x8D89;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16UI = 0x8D77;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16UI_EXT = 0x8D77;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16_EXT = 0x8054;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16_SNORM = 0x8F9A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB16_SNORM_EXT = 0x8F9A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB2_EXT = 0x804E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB32F = 0x8815;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB32F_ARB = 0x8815;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB32F_EXT = 0x8815;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB32I = 0x8D83;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB32I_EXT = 0x8D83;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB32UI = 0x8D71;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB32UI_EXT = 0x8D71;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB4 = 0x804F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB4_EXT = 0x804F;

    public static final int GL_RGB4_S3TC = 0x83A1;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB5 = 0x8050;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB565 = 0x8D62;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB565_OES = 0x8D62;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB5_A1 = 0x8057;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB5_A1_EXT = 0x8057;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB5_A1_OES = 0x8057;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB5_EXT = 0x8050;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB8 = 0x8051;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB8I = 0x8D8F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB8I_EXT = 0x8D8F;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB8UI = 0x8D7D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB8UI_EXT = 0x8D7D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB8_EXT = 0x8051;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB8_OES = 0x8051;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB8_SNORM = 0x8F96;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB9_E5 = 0x8C3D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB9_E5_APPLE = 0x8C3D;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGB9_E5_EXT = 0x8C3D;

    /// Groups: `PixelTexGenModeSGIX`, `PathColorFormat`, `PixelFormat`,
    ///   `InternalFormat`
    public static final int GL_RGBA = 0x1908;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA12 = 0x805A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA12_EXT = 0x805A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16 = 0x805B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16F = 0x881A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16F_ARB = 0x881A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16F_EXT = 0x881A;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16I = 0x8D88;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16I_EXT = 0x8D88;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16UI = 0x8D76;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16UI_EXT = 0x8D76;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16_EXT = 0x805B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16_SNORM = 0x8F9B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA16_SNORM_EXT = 0x8F9B;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA2 = 0x8055;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA2_EXT = 0x8055;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA32F = 0x8814;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA32F_ARB = 0x8814;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA32F_EXT = 0x8814;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA32I = 0x8D82;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA32I_EXT = 0x8D82;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA32UI = 0x8D70;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA32UI_EXT = 0x8D70;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA4 = 0x8056;

    public static final int GL_RGBA4_DXT5_S3TC = 0x83A5;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA4_EXT = 0x8056;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA4_OES = 0x8056;

    public static final int GL_RGBA4_S3TC = 0x83A3;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA8 = 0x8058;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA8I = 0x8D8E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA8I_EXT = 0x8D8E;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA8UI = 0x8D7C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA8UI_EXT = 0x8D7C;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA8_EXT = 0x8058;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA8_OES = 0x8058;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_RGBA8_SNORM = 0x8F97;

    public static final int GL_RGBA_DXT5_S3TC = 0x83A4;

    public static final int GL_RGBA_FLOAT16_APPLE = 0x881A;

    public static final int GL_RGBA_FLOAT16_ATI = 0x881A;

    public static final int GL_RGBA_FLOAT32_APPLE = 0x8814;

    public static final int GL_RGBA_FLOAT32_ATI = 0x8814;

    public static final int GL_RGBA_FLOAT_MODE_ARB = 0x8820;

    public static final int GL_RGBA_FLOAT_MODE_ATI = 0x8820;

    /// Group: `PixelFormat`
    public static final int GL_RGBA_INTEGER = 0x8D99;

    public static final int GL_RGBA_INTEGER_EXT = 0x8D99;

    public static final int GL_RGBA_INTEGER_MODE_EXT = 0x8D9E;

    /// Group: `GetPName`
    public static final int GL_RGBA_MODE = 0x0C31;

    public static final int GL_RGBA_S3TC = 0x83A2;

    public static final int GL_RGBA_SIGNED_COMPONENTS_EXT = 0x8C3C;

    public static final int GL_RGBA_SNORM = 0x8F93;

    public static final int GL_RGBA_UNSIGNED_DOT_PRODUCT_MAPPING_NV = 0x86D9;

    public static final int GL_RGB_422_APPLE = 0x8A1F;

    public static final int GL_RGB_FLOAT16_APPLE = 0x881B;

    public static final int GL_RGB_FLOAT16_ATI = 0x881B;

    public static final int GL_RGB_FLOAT32_APPLE = 0x8815;

    public static final int GL_RGB_FLOAT32_ATI = 0x8815;

    /// Group: `PixelFormat`
    public static final int GL_RGB_INTEGER = 0x8D98;

    public static final int GL_RGB_INTEGER_EXT = 0x8D98;

    public static final int GL_RGB_RAW_422_APPLE = 0x8A51;

    public static final int GL_RGB_S3TC = 0x83A0;

    /// Group: `TextureEnvParameter`
    public static final int GL_RGB_SCALE = 0x8573;

    /// Group: `TextureEnvParameter`
    public static final int GL_RGB_SCALE_ARB = 0x8573;

    /// Group: `TextureEnvParameter`
    public static final int GL_RGB_SCALE_EXT = 0x8573;

    public static final int GL_RGB_SNORM = 0x8F92;

    public static final int GL_RG_EXT = 0x8227;

    /// Group: `PixelFormat`
    public static final int GL_RG_INTEGER = 0x8228;

    public static final int GL_RG_SNORM = 0x8F91;

    /// Groups: `ColorBuffer`, `DrawBufferMode`, `ReadBufferMode`
    public static final int GL_RIGHT = 0x0407;

    public static final int GL_ROBUST_GPU_TIMEOUT_MS_KHR = 0x82FD;

    /// Group: `PathCoordType`
    public static final int GL_ROUNDED_RECT2_NV = 0xEA;

    /// Group: `PathCoordType`
    public static final int GL_ROUNDED_RECT4_NV = 0xEC;

    /// Group: `PathCoordType`
    public static final int GL_ROUNDED_RECT8_NV = 0xEE;

    /// Group: `PathCoordType`
    public static final int GL_ROUNDED_RECT_NV = 0xE8;

    public static final int GL_ROUND_NV = 0x90A4;

    /// Group: `TextureCoordName`
    public static final int GL_S = 0x2000;

    /// Group: `ObjectIdentifier`
    public static final int GL_SAMPLER = 0x82E6;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_1D = 0x8B5D;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_1D_ARB = 0x8B5D;

    /// Group: `UniformType`
    public static final int GL_SAMPLER_1D_ARRAY = 0x8DC0;

    public static final int GL_SAMPLER_1D_ARRAY_EXT = 0x8DC0;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_1D_ARRAY_SHADOW = 0x8DC3;

    public static final int GL_SAMPLER_1D_ARRAY_SHADOW_EXT = 0x8DC3;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_1D_SHADOW = 0x8B61;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_1D_SHADOW_ARB = 0x8B61;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_2D = 0x8B5E;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_2D_ARB = 0x8B5E;

    /// Group: `UniformType`
    public static final int GL_SAMPLER_2D_ARRAY = 0x8DC1;

    public static final int GL_SAMPLER_2D_ARRAY_EXT = 0x8DC1;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_2D_ARRAY_SHADOW = 0x8DC4;

    public static final int GL_SAMPLER_2D_ARRAY_SHADOW_EXT = 0x8DC4;

    public static final int GL_SAMPLER_2D_ARRAY_SHADOW_NV = 0x8DC4;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_2D_MULTISAMPLE = 0x9108;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910B;

    public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY_OES = 0x910B;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_2D_RECT = 0x8B63;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_2D_RECT_ARB = 0x8B63;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_2D_RECT_SHADOW = 0x8B64;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_2D_RECT_SHADOW_ARB = 0x8B64;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_2D_SHADOW = 0x8B62;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_2D_SHADOW_ARB = 0x8B62;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_2D_SHADOW_EXT = 0x8B62;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_3D = 0x8B5F;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_3D_ARB = 0x8B5F;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_3D_OES = 0x8B5F;

    /// Group: `GetPName`
    public static final int GL_SAMPLER_BINDING = 0x8919;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_BUFFER = 0x8DC2;

    public static final int GL_SAMPLER_BUFFER_AMD = 0x9001;

    public static final int GL_SAMPLER_BUFFER_EXT = 0x8DC2;

    public static final int GL_SAMPLER_BUFFER_OES = 0x8DC2;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_CUBE = 0x8B60;

    /// Group: `AttributeType`
    public static final int GL_SAMPLER_CUBE_ARB = 0x8B60;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY = 0x900C;

    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_ARB = 0x900C;

    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_EXT = 0x900C;

    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_OES = 0x900C;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW = 0x900D;

    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW_ARB = 0x900D;

    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW_EXT = 0x900D;

    public static final int GL_SAMPLER_CUBE_MAP_ARRAY_SHADOW_OES = 0x900D;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_SAMPLER_CUBE_SHADOW = 0x8DC5;

    public static final int GL_SAMPLER_CUBE_SHADOW_EXT = 0x8DC5;

    public static final int GL_SAMPLER_CUBE_SHADOW_NV = 0x8DC5;

    public static final int GL_SAMPLER_EXTERNAL_2D_Y2Y_EXT = 0x8BE7;

    public static final int GL_SAMPLER_EXTERNAL_OES = 0x8D66;

    public static final int GL_SAMPLER_KHR = 0x82E6;

    public static final int GL_SAMPLER_OBJECT_AMD = 0x9155;

    public static final int GL_SAMPLER_RENDERBUFFER_NV = 0x8E56;

    /// Groups: `GetFramebufferParameter`, `GetPName`, `InternalFormatPName`
    public static final int GL_SAMPLES = 0x80A9;

    public static final int GL_SAMPLES_3DFX = 0x86B4;

    public static final int GL_SAMPLES_ARB = 0x80A9;

    public static final int GL_SAMPLES_EXT = 0x80A9;

    /// Group: `QueryTarget`
    public static final int GL_SAMPLES_PASSED = 0x8914;

    public static final int GL_SAMPLES_PASSED_ARB = 0x8914;

    /// Group: `GetPName`
    public static final int GL_SAMPLES_SGIS = 0x80A9;

    /// Group: `EnableCap`
    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E;

    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE_ARB = 0x809E;

    public static final int GL_SAMPLE_ALPHA_TO_MASK_EXT = 0x809E;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_SAMPLE_ALPHA_TO_MASK_SGIS = 0x809E;

    /// Group: `EnableCap`
    public static final int GL_SAMPLE_ALPHA_TO_ONE = 0x809F;

    public static final int GL_SAMPLE_ALPHA_TO_ONE_ARB = 0x809F;

    public static final int GL_SAMPLE_ALPHA_TO_ONE_EXT = 0x809F;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_SAMPLE_ALPHA_TO_ONE_SGIS = 0x809F;

    /// Groups: `GetFramebufferParameter`, `GetPName`
    public static final int GL_SAMPLE_BUFFERS = 0x80A8;

    public static final int GL_SAMPLE_BUFFERS_3DFX = 0x86B3;

    public static final int GL_SAMPLE_BUFFERS_ARB = 0x80A8;

    public static final int GL_SAMPLE_BUFFERS_EXT = 0x80A8;

    /// Group: `GetPName`
    public static final int GL_SAMPLE_BUFFERS_SGIS = 0x80A8;

    /// Group: `EnableCap`
    public static final int GL_SAMPLE_COVERAGE = 0x80A0;

    public static final int GL_SAMPLE_COVERAGE_ARB = 0x80A0;

    /// Group: `GetPName`
    public static final int GL_SAMPLE_COVERAGE_INVERT = 0x80AB;

    public static final int GL_SAMPLE_COVERAGE_INVERT_ARB = 0x80AB;

    /// Group: `GetPName`
    public static final int GL_SAMPLE_COVERAGE_VALUE = 0x80AA;

    public static final int GL_SAMPLE_COVERAGE_VALUE_ARB = 0x80AA;

    /// Group: `GetMultisamplePNameNV`
    /// Alias: `GL_SAMPLE_POSITION`
    public static final int GL_SAMPLE_LOCATION_ARB = 0x8E50;

    /// Alias: `GL_SAMPLE_POSITION_NV`
    public static final int GL_SAMPLE_LOCATION_NV = 0x8E50;

    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_HEIGHT_ARB = 0x933F;

    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_HEIGHT_NV = 0x933F;

    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_WIDTH_ARB = 0x933E;

    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_WIDTH_NV = 0x933E;

    public static final int GL_SAMPLE_LOCATION_SUBPIXEL_BITS_ARB = 0x933D;

    public static final int GL_SAMPLE_LOCATION_SUBPIXEL_BITS_NV = 0x933D;

    /// Group: `EnableCap`
    public static final int GL_SAMPLE_MASK = 0x8E51;

    public static final int GL_SAMPLE_MASK_EXT = 0x80A0;

    public static final int GL_SAMPLE_MASK_INVERT_EXT = 0x80AB;

    /// Group: `GetPName`
    public static final int GL_SAMPLE_MASK_INVERT_SGIS = 0x80AB;

    public static final int GL_SAMPLE_MASK_NV = 0x8E51;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_SAMPLE_MASK_SGIS = 0x80A0;

    public static final int GL_SAMPLE_MASK_VALUE = 0x8E52;

    public static final int GL_SAMPLE_MASK_VALUE_EXT = 0x80AA;

    public static final int GL_SAMPLE_MASK_VALUE_NV = 0x8E52;

    /// Group: `GetPName`
    public static final int GL_SAMPLE_MASK_VALUE_SGIS = 0x80AA;

    public static final int GL_SAMPLE_PATTERN_EXT = 0x80AC;

    /// Group: `GetPName`
    public static final int GL_SAMPLE_PATTERN_SGIS = 0x80AC;

    /// Group: `GetMultisamplePNameNV`
    public static final int GL_SAMPLE_POSITION = 0x8E50;

    public static final int GL_SAMPLE_POSITION_NV = 0x8E50;

    /// Group: `EnableCap`
    public static final int GL_SAMPLE_SHADING = 0x8C36;

    public static final int GL_SAMPLE_SHADING_ARB = 0x8C36;

    public static final int GL_SAMPLE_SHADING_OES = 0x8C36;

    /// Group: `FragmentShaderDestModMaskATI`
    public static final int GL_SATURATE_BIT_ATI = 0x00000040;

    /// Group: `DataTypeEXT`
    public static final int GL_SCALAR_EXT = 0x87BE;

    /// Group: `HintTarget`
    public static final int GL_SCALEBIAS_HINT_SGIX = 0x8322;

    public static final int GL_SCALED_RESOLVE_FASTEST_EXT = 0x90BA;

    public static final int GL_SCALED_RESOLVE_NICEST_EXT = 0x90BB;

    /// Group: `CombinerScaleNV`
    public static final int GL_SCALE_BY_FOUR_NV = 0x853F;

    /// Group: `CombinerScaleNV`
    public static final int GL_SCALE_BY_ONE_HALF_NV = 0x8540;

    /// Group: `CombinerScaleNV`
    public static final int GL_SCALE_BY_TWO_NV = 0x853E;

    /// Group: `AttribMask`
    public static final int GL_SCISSOR_BIT = 0x00080000;

    /// Group: `GetPName`
    public static final int GL_SCISSOR_BOX = 0x0C10;

    public static final int GL_SCISSOR_BOX_EXCLUSIVE_NV = 0x9556;

    /// Group: `CommandOpcodesNV`
    public static final int GL_SCISSOR_COMMAND_NV = 0x0011;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_SCISSOR_TEST = 0x0C11;

    public static final int GL_SCISSOR_TEST_EXCLUSIVE_NV = 0x9555;

    public static final int GL_SCREEN = 0x9295;

    public static final int GL_SCREEN_COORDINATES_REND = 0x8490;

    public static final int GL_SCREEN_KHR = 0x9295;

    public static final int GL_SCREEN_NV = 0x9295;

    public static final int GL_SECONDARY_COLOR_ARRAY = 0x845E;

    public static final int GL_SECONDARY_COLOR_ARRAY_ADDRESS_NV = 0x8F27;

    public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 0x889C;

    public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING_ARB = 0x889C;

    public static final int GL_SECONDARY_COLOR_ARRAY_EXT = 0x845E;

    public static final int GL_SECONDARY_COLOR_ARRAY_LENGTH_NV = 0x8F31;

    public static final int GL_SECONDARY_COLOR_ARRAY_LIST_IBM = 103077;

    public static final int GL_SECONDARY_COLOR_ARRAY_LIST_STRIDE_IBM = 103087;

    public static final int GL_SECONDARY_COLOR_ARRAY_POINTER = 0x845D;

    public static final int GL_SECONDARY_COLOR_ARRAY_POINTER_EXT = 0x845D;

    public static final int GL_SECONDARY_COLOR_ARRAY_SIZE = 0x845A;

    public static final int GL_SECONDARY_COLOR_ARRAY_SIZE_EXT = 0x845A;

    public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE = 0x845C;

    public static final int GL_SECONDARY_COLOR_ARRAY_STRIDE_EXT = 0x845C;

    public static final int GL_SECONDARY_COLOR_ARRAY_TYPE = 0x845B;

    public static final int GL_SECONDARY_COLOR_ARRAY_TYPE_EXT = 0x845B;

    /// Groups: `PathColor`, `CombinerRegisterNV`
    public static final int GL_SECONDARY_COLOR_NV = 0x852D;

    /// Group: `FragmentShaderGenericSourceATI`
    public static final int GL_SECONDARY_INTERPOLATOR_ATI = 0x896D;

    /// Group: `RenderingMode`
    public static final int GL_SELECT = 0x1C02;

    /// Group: `GetPointervPName`
    public static final int GL_SELECTION_BUFFER_POINTER = 0x0DF3;

    /// Group: `GetPName`
    public static final int GL_SELECTION_BUFFER_SIZE = 0x0DF4;

    /// Group: `SemaphoreParameterName`
    public static final int GL_SEMAPHORE_TYPE_BINARY_NV = 0x95B4;

    /// Group: `SemaphoreParameterName`
    public static final int GL_SEMAPHORE_TYPE_NV = 0x95B3;

    /// Group: `SemaphoreParameterName`
    public static final int GL_SEMAPHORE_TYPE_TIMELINE_NV = 0x95B5;

    /// Groups: `SeparableTarget`, `SeparableTargetEXT`
    public static final int GL_SEPARABLE_2D = 0x8012;

    /// Groups: `SeparableTargetEXT`, `EnableCap`, `GetPName`
    public static final int GL_SEPARABLE_2D_EXT = 0x8012;

    /// Group: `TransformFeedbackBufferMode`
    public static final int GL_SEPARATE_ATTRIBS = 0x8C8D;

    public static final int GL_SEPARATE_ATTRIBS_EXT = 0x8C8D;

    public static final int GL_SEPARATE_ATTRIBS_NV = 0x8C8D;

    /// Group: `LightModelColorControl`
    public static final int GL_SEPARATE_SPECULAR_COLOR = 0x81FA;

    /// Group: `LightModelColorControl`
    public static final int GL_SEPARATE_SPECULAR_COLOR_EXT = 0x81FA;

    /// Group: `LogicOp`
    public static final int GL_SET = 0x150F;

    public static final int GL_SET_AMD = 0x874A;

    /// Group: `ShaderBinaryFormat`
    public static final int GL_SGX_BINARY_IMG = 0x8C0A;

    public static final int GL_SGX_PROGRAM_BINARY_IMG = 0x9130;

    /// Group: `ObjectIdentifier`
    public static final int GL_SHADER = 0x82E1;

    /// Group: `ShaderBinaryFormat`
    public static final int GL_SHADER_BINARY_DMP = 0x9250;

    /// Group: `GetPName`
    public static final int GL_SHADER_BINARY_FORMATS = 0x8DF8;

    /// Group: `ShaderBinaryFormat`
    public static final int GL_SHADER_BINARY_FORMAT_SPIR_V = 0x9551;

    /// Alias: `GL_SHADER_BINARY_FORMAT_SPIR_V`
    public static final int GL_SHADER_BINARY_FORMAT_SPIR_V_ARB = 0x9551;

    /// Group: `ShaderBinaryFormat`
    public static final int GL_SHADER_BINARY_VIV = 0x8FC4;

    /// Group: `GetPName`
    public static final int GL_SHADER_COMPILER = 0x8DFA;

    public static final int GL_SHADER_CONSISTENT_NV = 0x86DD;

    /// Group: `MemoryBarrierMask`
    public static final int GL_SHADER_GLOBAL_ACCESS_BARRIER_BIT_NV = 0x00000010;

    /// Group: `MemoryBarrierMask`
    public static final int GL_SHADER_IMAGE_ACCESS_BARRIER_BIT = 0x00000020;

    /// Group: `MemoryBarrierMask`
    public static final int GL_SHADER_IMAGE_ACCESS_BARRIER_BIT_EXT = 0x00000020;

    /// Group: `InternalFormatPName`
    public static final int GL_SHADER_IMAGE_ATOMIC = 0x82A6;

    /// Group: `InternalFormatPName`
    public static final int GL_SHADER_IMAGE_LOAD = 0x82A4;

    /// Group: `InternalFormatPName`
    public static final int GL_SHADER_IMAGE_STORE = 0x82A5;

    public static final int GL_SHADER_INCLUDE_ARB = 0x8DAE;

    public static final int GL_SHADER_KHR = 0x82E1;

    public static final int GL_SHADER_OBJECT_ARB = 0x8B48;

    public static final int GL_SHADER_OBJECT_EXT = 0x8B48;

    public static final int GL_SHADER_OPERATION_NV = 0x86DF;

    public static final int GL_SHADER_PIXEL_LOCAL_STORAGE_EXT = 0x8F64;

    /// Group: `ShaderParameterName`
    public static final int GL_SHADER_SOURCE_LENGTH = 0x8B88;

    /// Group: `MemoryBarrierMask`
    public static final int GL_SHADER_STORAGE_BARRIER_BIT = 0x00002000;

    /// Group: `ProgramInterface`
    public static final int GL_SHADER_STORAGE_BLOCK = 0x92E6;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_SHADER_STORAGE_BUFFER = 0x90D2;

    /// Group: `GetPName`
    public static final int GL_SHADER_STORAGE_BUFFER_BINDING = 0x90D3;

    /// Group: `GetPName`
    public static final int GL_SHADER_STORAGE_BUFFER_OFFSET_ALIGNMENT = 0x90DF;

    /// Group: `GetPName`
    public static final int GL_SHADER_STORAGE_BUFFER_SIZE = 0x90D5;

    /// Group: `GetPName`
    public static final int GL_SHADER_STORAGE_BUFFER_START = 0x90D4;

    /// Group: `ShaderParameterName`
    public static final int GL_SHADER_TYPE = 0x8B4F;

    /// Group: `GetPName`
    public static final int GL_SHADE_MODEL = 0x0B54;

    /// Group: `StringName`
    public static final int GL_SHADING_LANGUAGE_VERSION = 0x8B8C;

    public static final int GL_SHADING_LANGUAGE_VERSION_ARB = 0x8B8C;

    public static final int GL_SHADING_RATE_16_INVOCATIONS_PER_PIXEL_NV = 0x956F;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_1X1_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_1X1_PIXELS_EXT = 0x96A6;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_1X1_PIXELS_QCOM = 0x96A6;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_1X2_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_1X2_PIXELS_EXT = 0x96A7;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_1X2_PIXELS_QCOM = 0x96A7;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_1X4_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_1X4_PIXELS_EXT = 0x96AA;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_1X4_PIXELS_QCOM = 0x96AA;

    public static final int GL_SHADING_RATE_1_INVOCATION_PER_1X2_PIXELS_NV = 0x9566;

    public static final int GL_SHADING_RATE_1_INVOCATION_PER_2X1_PIXELS_NV = 0x9567;

    public static final int GL_SHADING_RATE_1_INVOCATION_PER_2X2_PIXELS_NV = 0x9568;

    public static final int GL_SHADING_RATE_1_INVOCATION_PER_2X4_PIXELS_NV = 0x9569;

    public static final int GL_SHADING_RATE_1_INVOCATION_PER_4X2_PIXELS_NV = 0x956A;

    public static final int GL_SHADING_RATE_1_INVOCATION_PER_4X4_PIXELS_NV = 0x956B;

    public static final int GL_SHADING_RATE_1_INVOCATION_PER_PIXEL_NV = 0x9565;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_2X1_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_2X1_PIXELS_EXT = 0x96A8;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_2X1_PIXELS_QCOM = 0x96A8;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_2X2_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_2X2_PIXELS_EXT = 0x96A9;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_2X2_PIXELS_QCOM = 0x96A9;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_2X4_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_2X4_PIXELS_EXT = 0x96AD;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_2X4_PIXELS_QCOM = 0x96AD;

    public static final int GL_SHADING_RATE_2_INVOCATIONS_PER_PIXEL_NV = 0x956C;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_4X1_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_4X1_PIXELS_EXT = 0x96AB;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_4X1_PIXELS_QCOM = 0x96AB;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_4X2_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_4X2_PIXELS_EXT = 0x96AC;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_4X2_PIXELS_QCOM = 0x96AC;

    /// Group: `ShadingRate`
    /// Alias: `GL_SHADING_RATE_4X4_PIXELS_QCOM`
    public static final int GL_SHADING_RATE_4X4_PIXELS_EXT = 0x96AE;

    /// Group: `ShadingRateQCOM`
    public static final int GL_SHADING_RATE_4X4_PIXELS_QCOM = 0x96AE;

    public static final int GL_SHADING_RATE_4_INVOCATIONS_PER_PIXEL_NV = 0x956D;

    public static final int GL_SHADING_RATE_8_INVOCATIONS_PER_PIXEL_NV = 0x956E;

    /// Group: `FramebufferAttachment`
    public static final int GL_SHADING_RATE_ATTACHMENT_EXT = 0x96D1;

    /// Group: `GetPName`
    public static final int GL_SHADING_RATE_EXT = 0x96D0;

    public static final int GL_SHADING_RATE_IMAGE_BINDING_NV = 0x955B;

    public static final int GL_SHADING_RATE_IMAGE_NV = 0x9563;

    /// Group: `GetPName`
    public static final int GL_SHADING_RATE_IMAGE_PALETTE_COUNT_NV = 0x95B2;

    public static final int GL_SHADING_RATE_IMAGE_PALETTE_SIZE_NV = 0x955E;

    /// Groups: `EnableCap`, `GetPName`
    public static final int GL_SHADING_RATE_IMAGE_PER_PRIMITIVE_NV = 0x95B1;

    public static final int GL_SHADING_RATE_IMAGE_TEXEL_HEIGHT_NV = 0x955D;

    public static final int GL_SHADING_RATE_IMAGE_TEXEL_WIDTH_NV = 0x955C;

    public static final int GL_SHADING_RATE_NO_INVOCATIONS_NV = 0x9564;

    /// Group: `EnableCap`
    public static final int GL_SHADING_RATE_PRESERVE_ASPECT_RATIO_QCOM = 0x96A5;

    /// Group: `GetPName`
    public static final int GL_SHADING_RATE_QCOM = 0x96A4;

    public static final int GL_SHADING_RATE_SAMPLE_ORDER_DEFAULT_NV = 0x95AE;

    public static final int GL_SHADING_RATE_SAMPLE_ORDER_PIXEL_MAJOR_NV = 0x95AF;

    public static final int GL_SHADING_RATE_SAMPLE_ORDER_SAMPLE_MAJOR_NV = 0x95B0;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_SHADOW_AMBIENT_SGIX = 0x80BF;

    /// Group: `LightTexturePNameEXT`
    public static final int GL_SHADOW_ATTENUATION_EXT = 0x834E;

    public static final int GL_SHARED_EDGE_NV = 0xC0;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_SHARED_TEXTURE_PALETTE_EXT = 0x81FB;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_SHARPEN_TEXTURE_FUNC_POINTS_SGIS = 0x80B0;

    /// Group: `MaterialParameter`
    public static final int GL_SHININESS = 0x1601;

    /// Groups: `VertexAttribIType`, `SecondaryColorPointerTypeIBM`,
    ///   `WeightPointerTypeARB`, `TangentPointerTypeEXT`, `BinormalPointerTypeEXT`,
    ///   `IndexPointerType`, `ListNameType`, `NormalPointerType`, `PixelType`,
    ///   `TexCoordPointerType`, `VertexPointerType`, `VertexAttribType`,
    ///   `VertexAttribPointerType`
    public static final int GL_SHORT = 0x1402;

    public static final int GL_SIGNALED = 0x9119;

    public static final int GL_SIGNALED_APPLE = 0x9119;

    public static final int GL_SIGNED_ALPHA8_NV = 0x8706;

    public static final int GL_SIGNED_ALPHA_NV = 0x8705;

    public static final int GL_SIGNED_HILO16_NV = 0x86FA;

    public static final int GL_SIGNED_HILO8_NV = 0x885F;

    public static final int GL_SIGNED_HILO_NV = 0x86F9;

    /// Group: `CombinerMappingNV`
    public static final int GL_SIGNED_IDENTITY_NV = 0x853C;

    public static final int GL_SIGNED_INTENSITY8_NV = 0x8708;

    public static final int GL_SIGNED_INTENSITY_NV = 0x8707;

    public static final int GL_SIGNED_LUMINANCE8_ALPHA8_NV = 0x8704;

    public static final int GL_SIGNED_LUMINANCE8_NV = 0x8702;

    public static final int GL_SIGNED_LUMINANCE_ALPHA_NV = 0x8703;

    public static final int GL_SIGNED_LUMINANCE_NV = 0x8701;

    /// Group: `CombinerMappingNV`
    public static final int GL_SIGNED_NEGATE_NV = 0x853D;

    public static final int GL_SIGNED_NORMALIZED = 0x8F9C;

    public static final int GL_SIGNED_RGB8_NV = 0x86FF;

    public static final int GL_SIGNED_RGB8_UNSIGNED_ALPHA8_NV = 0x870D;

    public static final int GL_SIGNED_RGBA8_NV = 0x86FC;

    public static final int GL_SIGNED_RGBA_NV = 0x86FB;

    public static final int GL_SIGNED_RGB_NV = 0x86FE;

    public static final int GL_SIGNED_RGB_UNSIGNED_ALPHA_NV = 0x870C;

    /// Group: `InternalFormatPName`
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_TEST = 0x82AC;

    /// Group: `InternalFormatPName`
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_DEPTH_WRITE = 0x82AE;

    /// Group: `InternalFormatPName`
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_TEST = 0x82AD;

    /// Group: `InternalFormatPName`
    public static final int GL_SIMULTANEOUS_TEXTURE_AND_STENCIL_WRITE = 0x82AF;

    /// Group: `LightModelColorControl`
    public static final int GL_SINGLE_COLOR = 0x81F9;

    /// Group: `LightModelColorControl`
    public static final int GL_SINGLE_COLOR_EXT = 0x81F9;

    // /// Group: `TransformFeedbackTokenNV`
    // public static final int GL_SKIP_COMPONENTS1_NV = (6 as GLenum).wrapping_neg();

    // /// Group: `TransformFeedbackTokenNV`
    // public static final int GL_SKIP_COMPONENTS2_NV = (5 as GLenum).wrapping_neg();

    // /// Group: `TransformFeedbackTokenNV`
    // public static final int GL_SKIP_COMPONENTS3_NV = (4 as GLenum).wrapping_neg();

    // /// Group: `TransformFeedbackTokenNV`
    // public static final int GL_SKIP_COMPONENTS4_NV = (3 as GLenum).wrapping_neg();

    public static final int GL_SKIP_DECODE_EXT = 0x8A4A;

    /// Group: `PathHandleMissingGlyphs`
    public static final int GL_SKIP_MISSING_GLYPH_NV = 0x90A9;

    public static final int GL_SLICE_ACCUM_SUN = 0x85CC;

    public static final int GL_SLIM10U_SGIX = 0x831E;

    public static final int GL_SLIM12S_SGIX = 0x831F;

    public static final int GL_SLIM8U_SGIX = 0x831D;

    public static final int GL_SLUMINANCE = 0x8C46;

    public static final int GL_SLUMINANCE8 = 0x8C47;

    public static final int GL_SLUMINANCE8_ALPHA8 = 0x8C45;

    public static final int GL_SLUMINANCE8_ALPHA8_EXT = 0x8C45;

    public static final int GL_SLUMINANCE8_ALPHA8_NV = 0x8C45;

    public static final int GL_SLUMINANCE8_EXT = 0x8C47;

    public static final int GL_SLUMINANCE8_NV = 0x8C47;

    public static final int GL_SLUMINANCE_ALPHA = 0x8C44;

    public static final int GL_SLUMINANCE_ALPHA_EXT = 0x8C44;

    public static final int GL_SLUMINANCE_ALPHA_NV = 0x8C44;

    public static final int GL_SLUMINANCE_EXT = 0x8C46;

    public static final int GL_SLUMINANCE_NV = 0x8C46;

    /// Group: `PathCoordType`
    public static final int GL_SMALL_CCW_ARC_TO_NV = 0x12;

    /// Group: `PathCoordType`
    public static final int GL_SMALL_CW_ARC_TO_NV = 0x14;

    public static final int GL_SMAPHS30_PROGRAM_BINARY_DMP = 0x9251;

    public static final int GL_SMAPHS_PROGRAM_BINARY_DMP = 0x9252;

    /// Group: `ShadingModel`
    public static final int GL_SMOOTH = 0x1D01;

    /// Group: `PathCoordType`
    public static final int GL_SMOOTH_CUBIC_CURVE_TO_NV = 0x10;

    /// Group: `GetPName`
    /// Alias: `GL_LINE_WIDTH_GRANULARITY`
    public static final int GL_SMOOTH_LINE_WIDTH_GRANULARITY = 0x0B23;

    /// Group: `GetPName`
    /// Alias: `GL_LINE_WIDTH_RANGE`
    public static final int GL_SMOOTH_LINE_WIDTH_RANGE = 0x0B22;

    /// Group: `GetPName`
    /// Alias: `GL_POINT_SIZE_GRANULARITY`
    public static final int GL_SMOOTH_POINT_SIZE_GRANULARITY = 0x0B13;

    /// Group: `GetPName`
    /// Alias: `GL_POINT_SIZE_RANGE`
    public static final int GL_SMOOTH_POINT_SIZE_RANGE = 0x0B12;

    /// Group: `PathCoordType`
    public static final int GL_SMOOTH_QUADRATIC_CURVE_TO_NV = 0x0E;

    public static final int GL_SM_COUNT_NV = 0x933B;

    public static final int GL_SOFTLIGHT = 0x929C;

    public static final int GL_SOFTLIGHT_KHR = 0x929C;

    public static final int GL_SOFTLIGHT_NV = 0x929C;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE0_ALPHA = 0x8588;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE0_ALPHA_ARB = 0x8588;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE0_ALPHA_EXT = 0x8588;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE0_RGB = 0x8580;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE0_RGB_ARB = 0x8580;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE0_RGB_EXT = 0x8580;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE1_ALPHA = 0x8589;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE1_ALPHA_ARB = 0x8589;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE1_ALPHA_EXT = 0x8589;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE1_RGB = 0x8581;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE1_RGB_ARB = 0x8581;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE1_RGB_EXT = 0x8581;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE2_ALPHA = 0x858A;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE2_ALPHA_ARB = 0x858A;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE2_ALPHA_EXT = 0x858A;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE2_RGB = 0x8582;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE2_RGB_ARB = 0x8582;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE2_RGB_EXT = 0x8582;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE3_ALPHA_NV = 0x858B;

    /// Group: `TextureEnvParameter`
    public static final int GL_SOURCE3_RGB_NV = 0x8583;

    /// Group: `CombinerRegisterNV`
    public static final int GL_SPARE0_NV = 0x852E;

    public static final int GL_SPARE0_PLUS_SECONDARY_COLOR_NV = 0x8532;

    /// Group: `CombinerRegisterNV`
    public static final int GL_SPARE1_NV = 0x852F;

    public static final int GL_SPARSE_BUFFER_PAGE_SIZE_ARB = 0x82F8;

    /// Group: `BufferStorageMask`
    public static final int GL_SPARSE_STORAGE_BIT_ARB = 0x0400;

    public static final int GL_SPARSE_TEXTURE_FULL_ARRAY_CUBE_MIPMAPS_ARB = 0x91A9;

    public static final int GL_SPARSE_TEXTURE_FULL_ARRAY_CUBE_MIPMAPS_EXT = 0x91A9;

    /// Groups: `MaterialParameter`, `FragmentLightParameterSGIX`,
    ///   `ColorMaterialParameter`
    public static final int GL_SPECULAR = 0x1202;

    /// Group: `TextureGenMode`
    public static final int GL_SPHERE_MAP = 0x2402;

    public static final int GL_SPIR_V_BINARY = 0x9552;

    /// Alias: `GL_SPIR_V_BINARY`
    public static final int GL_SPIR_V_BINARY_ARB = 0x9552;

    public static final int GL_SPIR_V_EXTENSIONS = 0x9553;

    /// Groups: `LightParameter`, `FragmentLightParameterSGIX`
    public static final int GL_SPOT_CUTOFF = 0x1206;

    /// Groups: `LightParameter`, `FragmentLightParameterSGIX`
    public static final int GL_SPOT_DIRECTION = 0x1204;

    /// Groups: `LightParameter`, `FragmentLightParameterSGIX`
    public static final int GL_SPOT_EXPONENT = 0x1205;

    /// Group: `SpriteModeSGIX`
    public static final int GL_SPRITE_AXIAL_SGIX = 0x814C;

    /// Group: `GetPName`
    public static final int GL_SPRITE_AXIS_SGIX = 0x814A;

    /// Group: `SpriteModeSGIX`
    public static final int GL_SPRITE_EYE_ALIGNED_SGIX = 0x814E;

    /// Groups: `GetPName`, `SpriteParameterNameSGIX`
    public static final int GL_SPRITE_MODE_SGIX = 0x8149;

    /// Group: `SpriteModeSGIX`
    public static final int GL_SPRITE_OBJECT_ALIGNED_SGIX = 0x814D;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_SPRITE_SGIX = 0x8148;

    /// Group: `GetPName`
    public static final int GL_SPRITE_TRANSLATION_SGIX = 0x814B;

    public static final int GL_SQUARE_NV = 0x90A3;

    /// Group: `InternalFormat`
    public static final int GL_SR8_EXT = 0x8FBD;

    /// Group: `TextureEnvParameter`
    /// Alias: `GL_SOURCE0_ALPHA`
    public static final int GL_SRC0_ALPHA = 0x8588;

    /// Group: `TextureEnvParameter`
    /// Alias: `GL_SOURCE0_RGB`
    public static final int GL_SRC0_RGB = 0x8580;

    /// Groups: `TextureEnvParameter`, `BlendingFactor`
    /// Alias: `GL_SOURCE1_ALPHA`
    public static final int GL_SRC1_ALPHA = 0x8589;

    /// Group: `TextureEnvParameter`
    public static final int GL_SRC1_ALPHA_EXT = 0x8589;

    /// Group: `BlendingFactor`
    public static final int GL_SRC1_COLOR = 0x88F9;

    public static final int GL_SRC1_COLOR_EXT = 0x88F9;

    /// Group: `TextureEnvParameter`
    /// Alias: `GL_SOURCE1_RGB`
    public static final int GL_SRC1_RGB = 0x8581;

    /// Group: `TextureEnvParameter`
    /// Alias: `GL_SOURCE2_ALPHA`
    public static final int GL_SRC2_ALPHA = 0x858A;

    /// Group: `TextureEnvParameter`
    /// Alias: `GL_SOURCE2_RGB`
    public static final int GL_SRC2_RGB = 0x8582;

    /// Group: `BlendingFactor`
    public static final int GL_SRC_ALPHA = 0x0302;

    /// Group: `BlendingFactor`
    public static final int GL_SRC_ALPHA_SATURATE = 0x0308;

    public static final int GL_SRC_ALPHA_SATURATE_EXT = 0x0308;

    public static final int GL_SRC_ATOP_NV = 0x928E;

    /// Group: `BlendingFactor`
    public static final int GL_SRC_COLOR = 0x0300;

    public static final int GL_SRC_IN_NV = 0x928A;

    public static final int GL_SRC_NV = 0x9286;

    public static final int GL_SRC_OUT_NV = 0x928C;

    public static final int GL_SRC_OVER_NV = 0x9288;

    /// Group: `InternalFormat`
    public static final int GL_SRG8_EXT = 0x8FBE;

    /// Group: `InternalFormat`
    public static final int GL_SRGB = 0x8C40;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_SRGB8 = 0x8C41;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_SRGB8_ALPHA8 = 0x8C43;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_SRGB8_ALPHA8_EXT = 0x8C43;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_SRGB8_EXT = 0x8C41;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_SRGB8_NV = 0x8C41;

    /// Group: `InternalFormat`
    public static final int GL_SRGB_ALPHA = 0x8C42;

    /// Group: `InternalFormat`
    public static final int GL_SRGB_ALPHA_EXT = 0x8C42;

    public static final int GL_SRGB_DECODE_ARB = 0x8299;

    /// Group: `InternalFormat`
    public static final int GL_SRGB_EXT = 0x8C40;

    /// Group: `InternalFormatPName`
    public static final int GL_SRGB_READ = 0x8297;

    /// Group: `InternalFormatPName`
    public static final int GL_SRGB_WRITE = 0x8298;

    /// Group: `ErrorCode`
    public static final int GL_STACK_OVERFLOW = 0x0503;

    public static final int GL_STACK_OVERFLOW_KHR = 0x0503;

    /// Group: `ErrorCode`
    public static final int GL_STACK_UNDERFLOW = 0x0504;

    public static final int GL_STACK_UNDERFLOW_KHR = 0x0504;

    public static final int GL_STANDARD_FONT_FORMAT_NV = 0x936C;

    /// Group: `PathFontTarget`
    public static final int GL_STANDARD_FONT_NAME_NV = 0x9072;

    public static final int GL_STATE_RESTORE = 0x8BDC;

    /// Group: `ArrayObjectUsageATI`
    public static final int GL_STATIC_ATI = 0x8760;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_STATIC_COPY = 0x88E6;

    public static final int GL_STATIC_COPY_ARB = 0x88E6;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_STATIC_DRAW = 0x88E4;

    public static final int GL_STATIC_DRAW_ARB = 0x88E4;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_STATIC_READ = 0x88E5;

    public static final int GL_STATIC_READ_ARB = 0x88E5;

    public static final int GL_STATIC_VERTEX_ARRAY_IBM = 103061;

    /// Groups: `Buffer`, `PixelCopyType`, `InvalidateFramebufferAttachment`
    public static final int GL_STENCIL = 0x1802;

    /// Group: `FramebufferAttachment`
    public static final int GL_STENCIL_ATTACHMENT = 0x8D20;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_STENCIL_ATTACHMENT_EXT = 0x8D20;

    /// Group: `InvalidateFramebufferAttachment`
    public static final int GL_STENCIL_ATTACHMENT_OES = 0x8D20;

    /// Group: `GetPName`
    public static final int GL_STENCIL_BACK_FAIL = 0x8801;

    public static final int GL_STENCIL_BACK_FAIL_ATI = 0x8801;

    /// Group: `GetPName`
    public static final int GL_STENCIL_BACK_FUNC = 0x8800;

    public static final int GL_STENCIL_BACK_FUNC_ATI = 0x8800;

    public static final int GL_STENCIL_BACK_OP_VALUE_AMD = 0x874D;

    /// Group: `GetPName`
    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL = 0x8802;

    public static final int GL_STENCIL_BACK_PASS_DEPTH_FAIL_ATI = 0x8802;

    /// Group: `GetPName`
    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS = 0x8803;

    public static final int GL_STENCIL_BACK_PASS_DEPTH_PASS_ATI = 0x8803;

    /// Group: `GetPName`
    public static final int GL_STENCIL_BACK_REF = 0x8CA3;

    /// Group: `GetPName`
    public static final int GL_STENCIL_BACK_VALUE_MASK = 0x8CA4;

    /// Group: `GetPName`
    public static final int GL_STENCIL_BACK_WRITEMASK = 0x8CA5;

    /// Group: `GetPName`
    public static final int GL_STENCIL_BITS = 0x0D57;

    /// Groups: `ClearBufferMask`, `AttribMask`
    public static final int GL_STENCIL_BUFFER_BIT = 0x00000400;

    /// Group: `BufferBitQCOM`
    public static final int GL_STENCIL_BUFFER_BIT0_QCOM = 0x00010000;

    /// Group: `BufferBitQCOM`
    public static final int GL_STENCIL_BUFFER_BIT1_QCOM = 0x00020000;

    /// Group: `BufferBitQCOM`
    public static final int GL_STENCIL_BUFFER_BIT2_QCOM = 0x00040000;

    /// Group: `BufferBitQCOM`
    public static final int GL_STENCIL_BUFFER_BIT3_QCOM = 0x00080000;

    /// Group: `BufferBitQCOM`
    public static final int GL_STENCIL_BUFFER_BIT4_QCOM = 0x00100000;

    /// Group: `BufferBitQCOM`
    public static final int GL_STENCIL_BUFFER_BIT5_QCOM = 0x00200000;

    /// Group: `BufferBitQCOM`
    public static final int GL_STENCIL_BUFFER_BIT6_QCOM = 0x00400000;

    /// Group: `BufferBitQCOM`
    public static final int GL_STENCIL_BUFFER_BIT7_QCOM = 0x00800000;

    public static final int GL_STENCIL_CLEAR_TAG_VALUE_EXT = 0x88F3;

    /// Group: `GetPName`
    public static final int GL_STENCIL_CLEAR_VALUE = 0x0B91;

    public static final int GL_STENCIL_COMPONENTS = 0x8285;

    /// Group: `PixelCopyType`
    public static final int GL_STENCIL_EXT = 0x1802;

    /// Group: `GetPName`
    public static final int GL_STENCIL_FAIL = 0x0B94;

    /// Group: `GetPName`
    public static final int GL_STENCIL_FUNC = 0x0B92;

    /// Groups: `InternalFormat`, `PixelFormat`, `DepthStencilTextureMode`
    public static final int GL_STENCIL_INDEX = 0x1901;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX1 = 0x8D46;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX16 = 0x8D49;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX16_EXT = 0x8D49;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX1_EXT = 0x8D46;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX1_OES = 0x8D46;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX4 = 0x8D47;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX4_EXT = 0x8D47;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX4_OES = 0x8D47;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX8 = 0x8D48;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX8_EXT = 0x8D48;

    /// Groups: `InternalFormat`, `SizedInternalFormat`
    public static final int GL_STENCIL_INDEX8_OES = 0x8D48;

    /// Group: `InternalFormat`
    public static final int GL_STENCIL_INDEX_OES = 0x1901;

    public static final int GL_STENCIL_OP_VALUE_AMD = 0x874C;

    /// Group: `GetPName`
    public static final int GL_STENCIL_PASS_DEPTH_FAIL = 0x0B95;

    /// Group: `GetPName`
    public static final int GL_STENCIL_PASS_DEPTH_PASS = 0x0B96;

    /// Group: `GetPName`
    public static final int GL_STENCIL_REF = 0x0B97;

    /// Group: `CommandOpcodesNV`
    public static final int GL_STENCIL_REF_COMMAND_NV = 0x000C;

    /// Group: `InternalFormatPName`
    public static final int GL_STENCIL_RENDERABLE = 0x8288;

    public static final int GL_STENCIL_SAMPLES_NV = 0x932E;

    public static final int GL_STENCIL_TAG_BITS_EXT = 0x88F2;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_STENCIL_TEST = 0x0B90;

    public static final int GL_STENCIL_TEST_TWO_SIDE_EXT = 0x8910;

    /// Group: `GetPName`
    public static final int GL_STENCIL_VALUE_MASK = 0x0B93;

    /// Group: `GetPName`
    public static final int GL_STENCIL_WRITEMASK = 0x0B98;

    /// Groups: `GetFramebufferParameter`, `GetPName`
    public static final int GL_STEREO = 0x0C33;

    /// Group: `VertexArrayPNameAPPLE`
    public static final int GL_STORAGE_CACHED_APPLE = 0x85BE;

    /// Group: `VertexArrayPNameAPPLE`
    public static final int GL_STORAGE_CLIENT_APPLE = 0x85B4;

    public static final int GL_STORAGE_PRIVATE_APPLE = 0x85BD;

    /// Group: `VertexArrayPNameAPPLE`
    public static final int GL_STORAGE_SHARED_APPLE = 0x85BF;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_STREAM_COPY = 0x88E2;

    public static final int GL_STREAM_COPY_ARB = 0x88E2;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_STREAM_DRAW = 0x88E0;

    public static final int GL_STREAM_DRAW_ARB = 0x88E0;

    public static final int GL_STREAM_RASTERIZATION_AMD = 0x91A0;

    /// Groups: `VertexBufferObjectUsage`, `BufferUsageARB`
    public static final int GL_STREAM_READ = 0x88E1;

    public static final int GL_STREAM_READ_ARB = 0x88E1;

    /// Group: `HintTarget`
    public static final int GL_STRICT_DEPTHFUNC_HINT_PGI = 0x1A216;

    /// Group: `HintTarget`
    public static final int GL_STRICT_LIGHTING_HINT_PGI = 0x1A217;

    /// Group: `HintTarget`
    public static final int GL_STRICT_SCISSOR_HINT_PGI = 0x1A218;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_ARITHMETIC_BIT_KHR = 0x00000004;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_BALLOT_BIT_KHR = 0x00000008;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_BASIC_BIT_KHR = 0x00000001;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_CLUSTERED_BIT_KHR = 0x00000040;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_PARTITIONED_BIT_NV = 0x00000100;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_QUAD_BIT_KHR = 0x00000080;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_SHUFFLE_BIT_KHR = 0x00000010;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_SHUFFLE_RELATIVE_BIT_KHR = 0x00000020;

    /// Group: `SubgroupSupportedFeatures`
    public static final int GL_SUBGROUP_FEATURE_VOTE_BIT_KHR = 0x00000002;

    public static final int GL_SUBGROUP_QUAD_ALL_STAGES_KHR = 0x9535;

    public static final int GL_SUBGROUP_SIZE_KHR = 0x9532;

    public static final int GL_SUBGROUP_SUPPORTED_FEATURES_KHR = 0x9534;

    public static final int GL_SUBGROUP_SUPPORTED_STAGES_KHR = 0x9533;

    /// Group: `GetPName`
    public static final int GL_SUBPIXEL_BITS = 0x0D50;

    public static final int GL_SUBPIXEL_PRECISION_BIAS_X_BITS_NV = 0x9347;

    public static final int GL_SUBPIXEL_PRECISION_BIAS_Y_BITS_NV = 0x9348;

    public static final int GL_SUBSAMPLE_DISTANCE_AMD = 0x883F;

    public static final int GL_SUBTRACT = 0x84E7;

    public static final int GL_SUBTRACT_ARB = 0x84E7;

    /// Group: `FragmentOp2ATI`
    public static final int GL_SUB_ATI = 0x8965;

    public static final int GL_SUCCESS_NV = 0x902F;

    public static final int GL_SUPERSAMPLE_SCALE_X_NV = 0x9372;

    public static final int GL_SUPERSAMPLE_SCALE_Y_NV = 0x9373;

    public static final int GL_SUPPORTED_MULTISAMPLE_MODES_AMD = 0x91B7;

    /// Groups: `TexStorageAttribs`, `GetTextureParameter`
    public static final int GL_SURFACE_COMPRESSION_EXT = 0x96C0;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_10BPC_EXT = 0x96CD;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_11BPC_EXT = 0x96CE;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_12BPC_EXT = 0x96CF;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_1BPC_EXT = 0x96C4;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_2BPC_EXT = 0x96C5;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_3BPC_EXT = 0x96C6;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_4BPC_EXT = 0x96C7;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_5BPC_EXT = 0x96C8;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_6BPC_EXT = 0x96C9;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_7BPC_EXT = 0x96CA;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_8BPC_EXT = 0x96CB;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_9BPC_EXT = 0x96CC;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_DEFAULT_EXT = 0x96C2;

    /// Group: `TexStorageAttribs`
    public static final int GL_SURFACE_COMPRESSION_FIXED_RATE_NONE_EXT = 0x96C1;

    public static final int GL_SURFACE_MAPPED_NV = 0x8700;

    public static final int GL_SURFACE_REGISTERED_NV = 0x86FD;

    public static final int GL_SURFACE_STATE_NV = 0x86EB;

    /// Group: `SwizzleOpATI`
    public static final int GL_SWIZZLE_STQ_ATI = 0x8977;

    /// Group: `SwizzleOpATI`
    public static final int GL_SWIZZLE_STQ_DQ_ATI = 0x8979;

    public static final int GL_SWIZZLE_STRQ_ATI = 0x897A;

    public static final int GL_SWIZZLE_STRQ_DQ_ATI = 0x897B;

    /// Group: `SwizzleOpATI`
    public static final int GL_SWIZZLE_STR_ATI = 0x8976;

    /// Group: `SwizzleOpATI`
    public static final int GL_SWIZZLE_STR_DR_ATI = 0x8978;

    public static final int GL_SYNC_CL_EVENT_ARB = 0x8240;

    public static final int GL_SYNC_CL_EVENT_COMPLETE_ARB = 0x8241;

    /// Group: `SyncParameterName`
    public static final int GL_SYNC_CONDITION = 0x9113;

    public static final int GL_SYNC_CONDITION_APPLE = 0x9113;

    public static final int GL_SYNC_FENCE = 0x9116;

    public static final int GL_SYNC_FENCE_APPLE = 0x9116;

    /// Group: `SyncParameterName`
    public static final int GL_SYNC_FLAGS = 0x9115;

    public static final int GL_SYNC_FLAGS_APPLE = 0x9115;

    /// Group: `SyncObjectMask`
    public static final int GL_SYNC_FLUSH_COMMANDS_BIT = 0x00000001;

    /// Group: `SyncObjectMask`
    public static final int GL_SYNC_FLUSH_COMMANDS_BIT_APPLE = 0x00000001;

    /// Group: `SyncCondition`
    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 0x9117;

    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE_APPLE = 0x9117;

    public static final int GL_SYNC_OBJECT_APPLE = 0x8A53;

    /// Group: `SyncParameterName`
    public static final int GL_SYNC_STATUS = 0x9114;

    public static final int GL_SYNC_STATUS_APPLE = 0x9114;

    public static final int GL_SYNC_X11_FENCE_EXT = 0x90E1;

    /// Group: `PathFontTarget`
    public static final int GL_SYSTEM_FONT_NAME_NV = 0x9073;

    /// Group: `TextureCoordName`
    public static final int GL_T = 0x2001;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_T2F_C3F_V3F = 0x2A2A;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_T2F_C4F_N3F_V3F = 0x2A2C;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_T2F_C4UB_V3F = 0x2A29;

    public static final int GL_T2F_IUI_N3F_V2F_EXT = 0x81B3;

    public static final int GL_T2F_IUI_N3F_V3F_EXT = 0x81B4;

    public static final int GL_T2F_IUI_V2F_EXT = 0x81B1;

    public static final int GL_T2F_IUI_V3F_EXT = 0x81B2;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_T2F_N3F_V3F = 0x2A2B;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_T2F_V3F = 0x2A27;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_T4F_C4F_N3F_V4F = 0x2A2D;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_T4F_V4F = 0x2A28;

    /// Group: `ErrorCode`
    public static final int GL_TABLE_TOO_LARGE = 0x8031;

    /// Group: `ErrorCode`
    public static final int GL_TABLE_TOO_LARGE_EXT = 0x8031;

    public static final int GL_TANGENT_ARRAY_EXT = 0x8439;

    public static final int GL_TANGENT_ARRAY_POINTER_EXT = 0x8442;

    public static final int GL_TANGENT_ARRAY_STRIDE_EXT = 0x843F;

    public static final int GL_TANGENT_ARRAY_TYPE_EXT = 0x843E;

    /// Group: `UseProgramStageMask`
    public static final int GL_TASK_SHADER_BIT_NV = 0x00000080;

    public static final int GL_TASK_SHADER_NV = 0x955A;

    public static final int GL_TASK_SUBROUTINE_NV = 0x957D;

    public static final int GL_TASK_SUBROUTINE_UNIFORM_NV = 0x957F;

    public static final int GL_TASK_WORK_GROUP_SIZE_NV = 0x953F;

    /// Group: `CommandOpcodesNV`
    public static final int GL_TERMINATE_SEQUENCE_COMMAND_NV = 0x0000;

    public static final int GL_TESSELLATION_FACTOR_AMD = 0x9005;

    public static final int GL_TESSELLATION_MODE_AMD = 0x9004;

    public static final int GL_TESS_CONTROL_OUTPUT_VERTICES = 0x8E75;

    public static final int GL_TESS_CONTROL_OUTPUT_VERTICES_EXT = 0x8E75;

    public static final int GL_TESS_CONTROL_OUTPUT_VERTICES_OES = 0x8E75;

    /// Group: `ProgramTarget`
    public static final int GL_TESS_CONTROL_PROGRAM_NV = 0x891E;

    public static final int GL_TESS_CONTROL_PROGRAM_PARAMETER_BUFFER_NV = 0x8C74;

    /// Groups: `PipelineParameterName`, `ShaderType`
    public static final int GL_TESS_CONTROL_SHADER = 0x8E88;

    /// Group: `UseProgramStageMask`
    public static final int GL_TESS_CONTROL_SHADER_BIT = 0x00000008;

    /// Group: `UseProgramStageMask`
    public static final int GL_TESS_CONTROL_SHADER_BIT_EXT = 0x00000008;

    /// Group: `UseProgramStageMask`
    public static final int GL_TESS_CONTROL_SHADER_BIT_OES = 0x00000008;

    public static final int GL_TESS_CONTROL_SHADER_EXT = 0x8E88;

    public static final int GL_TESS_CONTROL_SHADER_OES = 0x8E88;

    public static final int GL_TESS_CONTROL_SHADER_PATCHES = 0x82F1;

    /// Alias: `GL_TESS_CONTROL_SHADER_PATCHES`
    public static final int GL_TESS_CONTROL_SHADER_PATCHES_ARB = 0x82F1;

    /// Group: `ProgramInterface`
    public static final int GL_TESS_CONTROL_SUBROUTINE = 0x92E9;

    /// Group: `ProgramInterface`
    public static final int GL_TESS_CONTROL_SUBROUTINE_UNIFORM = 0x92EF;

    /// Group: `InternalFormatPName`
    public static final int GL_TESS_CONTROL_TEXTURE = 0x829C;

    /// Group: `ProgramTarget`
    public static final int GL_TESS_EVALUATION_PROGRAM_NV = 0x891F;

    public static final int GL_TESS_EVALUATION_PROGRAM_PARAMETER_BUFFER_NV = 0x8C75;

    /// Groups: `PipelineParameterName`, `ShaderType`
    public static final int GL_TESS_EVALUATION_SHADER = 0x8E87;

    /// Group: `UseProgramStageMask`
    public static final int GL_TESS_EVALUATION_SHADER_BIT = 0x00000010;

    /// Group: `UseProgramStageMask`
    public static final int GL_TESS_EVALUATION_SHADER_BIT_EXT = 0x00000010;

    /// Group: `UseProgramStageMask`
    public static final int GL_TESS_EVALUATION_SHADER_BIT_OES = 0x00000010;

    public static final int GL_TESS_EVALUATION_SHADER_EXT = 0x8E87;

    public static final int GL_TESS_EVALUATION_SHADER_INVOCATIONS = 0x82F2;

    /// Alias: `GL_TESS_EVALUATION_SHADER_INVOCATIONS`
    public static final int GL_TESS_EVALUATION_SHADER_INVOCATIONS_ARB = 0x82F2;

    public static final int GL_TESS_EVALUATION_SHADER_OES = 0x8E87;

    /// Group: `ProgramInterface`
    public static final int GL_TESS_EVALUATION_SUBROUTINE = 0x92EA;

    /// Group: `ProgramInterface`
    public static final int GL_TESS_EVALUATION_SUBROUTINE_UNIFORM = 0x92F0;

    /// Group: `InternalFormatPName`
    public static final int GL_TESS_EVALUATION_TEXTURE = 0x829D;

    public static final int GL_TESS_GEN_MODE = 0x8E76;

    public static final int GL_TESS_GEN_MODE_EXT = 0x8E76;

    public static final int GL_TESS_GEN_MODE_OES = 0x8E76;

    public static final int GL_TESS_GEN_POINT_MODE = 0x8E79;

    public static final int GL_TESS_GEN_POINT_MODE_EXT = 0x8E79;

    public static final int GL_TESS_GEN_POINT_MODE_OES = 0x8E79;

    public static final int GL_TESS_GEN_SPACING = 0x8E77;

    public static final int GL_TESS_GEN_SPACING_EXT = 0x8E77;

    public static final int GL_TESS_GEN_SPACING_OES = 0x8E77;

    public static final int GL_TESS_GEN_VERTEX_ORDER = 0x8E78;

    public static final int GL_TESS_GEN_VERTEX_ORDER_EXT = 0x8E78;

    public static final int GL_TESS_GEN_VERTEX_ORDER_OES = 0x8E78;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_TEXCOORD1_BIT_PGI = 0x10000000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_TEXCOORD2_BIT_PGI = 0x20000000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_TEXCOORD3_BIT_PGI = 0x40000000;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_TEXCOORD4_BIT_PGI = 0x80000000;

    /// Groups: `ObjectIdentifier`, `MatrixMode`
    public static final int GL_TEXTURE = 0x1702;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE0 = 0x84C0;

    /// Group: `CombinerRegisterNV`
    public static final int GL_TEXTURE0_ARB = 0x84C0;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE1 = 0x84C1;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE10 = 0x84CA;

    public static final int GL_TEXTURE10_ARB = 0x84CA;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE11 = 0x84CB;

    public static final int GL_TEXTURE11_ARB = 0x84CB;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE12 = 0x84CC;

    public static final int GL_TEXTURE12_ARB = 0x84CC;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE13 = 0x84CD;

    public static final int GL_TEXTURE13_ARB = 0x84CD;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE14 = 0x84CE;

    public static final int GL_TEXTURE14_ARB = 0x84CE;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE15 = 0x84CF;

    public static final int GL_TEXTURE15_ARB = 0x84CF;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE16 = 0x84D0;

    public static final int GL_TEXTURE16_ARB = 0x84D0;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE17 = 0x84D1;

    public static final int GL_TEXTURE17_ARB = 0x84D1;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE18 = 0x84D2;

    public static final int GL_TEXTURE18_ARB = 0x84D2;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE19 = 0x84D3;

    public static final int GL_TEXTURE19_ARB = 0x84D3;

    /// Group: `CombinerRegisterNV`
    public static final int GL_TEXTURE1_ARB = 0x84C1;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE2 = 0x84C2;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE20 = 0x84D4;

    public static final int GL_TEXTURE20_ARB = 0x84D4;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE21 = 0x84D5;

    public static final int GL_TEXTURE21_ARB = 0x84D5;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE22 = 0x84D6;

    public static final int GL_TEXTURE22_ARB = 0x84D6;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE23 = 0x84D7;

    public static final int GL_TEXTURE23_ARB = 0x84D7;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE24 = 0x84D8;

    public static final int GL_TEXTURE24_ARB = 0x84D8;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE25 = 0x84D9;

    public static final int GL_TEXTURE25_ARB = 0x84D9;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE26 = 0x84DA;

    public static final int GL_TEXTURE26_ARB = 0x84DA;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE27 = 0x84DB;

    public static final int GL_TEXTURE27_ARB = 0x84DB;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE28 = 0x84DC;

    public static final int GL_TEXTURE28_ARB = 0x84DC;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE29 = 0x84DD;

    public static final int GL_TEXTURE29_ARB = 0x84DD;

    public static final int GL_TEXTURE2_ARB = 0x84C2;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE3 = 0x84C3;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE30 = 0x84DE;

    public static final int GL_TEXTURE30_ARB = 0x84DE;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE31 = 0x84DF;

    public static final int GL_TEXTURE31_ARB = 0x84DF;

    public static final int GL_TEXTURE3_ARB = 0x84C3;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE4 = 0x84C4;

    public static final int GL_TEXTURE4_ARB = 0x84C4;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE5 = 0x84C5;

    public static final int GL_TEXTURE5_ARB = 0x84C5;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE6 = 0x84C6;

    public static final int GL_TEXTURE6_ARB = 0x84C6;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE7 = 0x84C7;

    public static final int GL_TEXTURE7_ARB = 0x84C7;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE8 = 0x84C8;

    public static final int GL_TEXTURE8_ARB = 0x84C8;

    /// Groups: `TextureUnit`, `FragmentShaderTextureSourceATI`
    public static final int GL_TEXTURE9 = 0x84C9;

    public static final int GL_TEXTURE9_ARB = 0x84C9;

    /// Groups: `CopyImageSubDataTarget`, `EnableCap`, `GetPName`, `TextureTarget`
    public static final int GL_TEXTURE_1D = 0x0DE0;

    /// Groups: `CopyImageSubDataTarget`, `TextureTarget`
    public static final int GL_TEXTURE_1D_ARRAY = 0x8C18;

    public static final int GL_TEXTURE_1D_ARRAY_EXT = 0x8C18;

    public static final int GL_TEXTURE_1D_BINDING_EXT = 0x8068;

    public static final int GL_TEXTURE_1D_STACK_BINDING_MESAX = 0x875D;

    public static final int GL_TEXTURE_1D_STACK_MESAX = 0x8759;

    /// Groups: `CopyImageSubDataTarget`, `EnableCap`, `GetPName`, `TextureTarget`
    public static final int GL_TEXTURE_2D = 0x0DE1;

    /// Groups: `CopyImageSubDataTarget`, `TextureTarget`
    public static final int GL_TEXTURE_2D_ARRAY = 0x8C1A;

    public static final int GL_TEXTURE_2D_ARRAY_EXT = 0x8C1A;

    public static final int GL_TEXTURE_2D_BINDING_EXT = 0x8069;

    /// Groups: `CopyImageSubDataTarget`, `TextureTarget`
    public static final int GL_TEXTURE_2D_MULTISAMPLE = 0x9100;

    /// Groups: `CopyImageSubDataTarget`, `TextureTarget`
    public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 0x9102;

    public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY_OES = 0x9102;

    public static final int GL_TEXTURE_2D_STACK_BINDING_MESAX = 0x875E;

    public static final int GL_TEXTURE_2D_STACK_MESAX = 0x875A;

    /// Groups: `CopyImageSubDataTarget`, `TextureTarget`
    public static final int GL_TEXTURE_3D = 0x806F;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_3D_BINDING_EXT = 0x806A;

    public static final int GL_TEXTURE_3D_BINDING_OES = 0x806A;

    /// Groups: `TextureTarget`, `EnableCap`, `GetPName`
    public static final int GL_TEXTURE_3D_EXT = 0x806F;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_3D_OES = 0x806F;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_4DSIZE_SGIS = 0x8136;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_4D_BINDING_SGIS = 0x814F;

    /// Groups: `TextureTarget`, `EnableCap`, `GetPName`
    public static final int GL_TEXTURE_4D_SGIS = 0x8134;

    public static final int GL_TEXTURE_ALPHA_MODULATE_IMG = 0x8C06;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_ALPHA_SIZE = 0x805F;

    public static final int GL_TEXTURE_ALPHA_SIZE_EXT = 0x805F;

    public static final int GL_TEXTURE_ALPHA_TYPE = 0x8C13;

    public static final int GL_TEXTURE_ALPHA_TYPE_ARB = 0x8C13;

    public static final int GL_TEXTURE_APPLICATION_MODE_EXT = 0x834F;

    public static final int GL_TEXTURE_ASTC_DECODE_PRECISION_EXT = 0x8F69;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_BASE_LEVEL = 0x813C;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_BASE_LEVEL_SGIS = 0x813C;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_1D = 0x8068;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_1D_ARRAY = 0x8C1C;

    public static final int GL_TEXTURE_BINDING_1D_ARRAY_EXT = 0x8C1C;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_2D = 0x8069;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_2D_ARRAY = 0x8C1D;

    public static final int GL_TEXTURE_BINDING_2D_ARRAY_EXT = 0x8C1D;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 0x9104;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 0x9105;

    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY_OES = 0x9105;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_3D = 0x806A;

    public static final int GL_TEXTURE_BINDING_3D_OES = 0x806A;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_BUFFER = 0x8C2C;

    public static final int GL_TEXTURE_BINDING_BUFFER_ARB = 0x8C2C;

    public static final int GL_TEXTURE_BINDING_BUFFER_EXT = 0x8C2C;

    public static final int GL_TEXTURE_BINDING_BUFFER_OES = 0x8C2C;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_CUBE_MAP = 0x8514;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARB = 0x8514;

    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY = 0x900A;

    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY_ARB = 0x900A;

    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY_EXT = 0x900A;

    public static final int GL_TEXTURE_BINDING_CUBE_MAP_ARRAY_OES = 0x900A;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_CUBE_MAP_EXT = 0x8514;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_CUBE_MAP_OES = 0x8514;

    public static final int GL_TEXTURE_BINDING_EXTERNAL_OES = 0x8D67;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_RECTANGLE = 0x84F6;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_RECTANGLE_ARB = 0x84F6;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BINDING_RECTANGLE_NV = 0x84F6;

    public static final int GL_TEXTURE_BINDING_RENDERBUFFER_NV = 0x8E53;

    /// Group: `AttribMask`
    public static final int GL_TEXTURE_BIT = 0x00040000;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_BLUE_SIZE = 0x805E;

    public static final int GL_TEXTURE_BLUE_SIZE_EXT = 0x805E;

    public static final int GL_TEXTURE_BLUE_TYPE = 0x8C12;

    public static final int GL_TEXTURE_BLUE_TYPE_ARB = 0x8C12;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_BORDER = 0x1005;

    /// Groups: `SamplerParameterF`, `GetTextureParameter`, `TextureParameterName`
    public static final int GL_TEXTURE_BORDER_COLOR = 0x1004;

    public static final int GL_TEXTURE_BORDER_COLOR_EXT = 0x1004;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_BORDER_COLOR_NV = 0x1004;

    public static final int GL_TEXTURE_BORDER_COLOR_OES = 0x1004;

    public static final int GL_TEXTURE_BORDER_VALUES_NV = 0x871A;

    /// Groups: `TextureTarget`, `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_TEXTURE_BUFFER = 0x8C2A;

    public static final int GL_TEXTURE_BUFFER_ARB = 0x8C2A;

    public static final int GL_TEXTURE_BUFFER_BINDING = 0x8C2A;

    public static final int GL_TEXTURE_BUFFER_BINDING_EXT = 0x8C2A;

    public static final int GL_TEXTURE_BUFFER_BINDING_OES = 0x8C2A;

    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING = 0x8C2D;

    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING_ARB = 0x8C2D;

    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING_EXT = 0x8C2D;

    public static final int GL_TEXTURE_BUFFER_DATA_STORE_BINDING_OES = 0x8C2D;

    public static final int GL_TEXTURE_BUFFER_EXT = 0x8C2A;

    public static final int GL_TEXTURE_BUFFER_FORMAT_ARB = 0x8C2E;

    public static final int GL_TEXTURE_BUFFER_FORMAT_EXT = 0x8C2E;

    public static final int GL_TEXTURE_BUFFER_OES = 0x8C2A;

    public static final int GL_TEXTURE_BUFFER_OFFSET = 0x919D;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT = 0x919F;

    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT_EXT = 0x919F;

    public static final int GL_TEXTURE_BUFFER_OFFSET_ALIGNMENT_OES = 0x919F;

    public static final int GL_TEXTURE_BUFFER_OFFSET_EXT = 0x919D;

    public static final int GL_TEXTURE_BUFFER_OFFSET_OES = 0x919D;

    public static final int GL_TEXTURE_BUFFER_SIZE = 0x919E;

    public static final int GL_TEXTURE_BUFFER_SIZE_EXT = 0x919E;

    public static final int GL_TEXTURE_BUFFER_SIZE_OES = 0x919E;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_CLIPMAP_CENTER_SGIX = 0x8171;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_CLIPMAP_DEPTH_SGIX = 0x8176;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_CLIPMAP_FRAME_SGIX = 0x8172;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_CLIPMAP_LOD_OFFSET_SGIX = 0x8175;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_CLIPMAP_OFFSET_SGIX = 0x8173;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_CLIPMAP_VIRTUAL_DEPTH_SGIX = 0x8174;

    public static final int GL_TEXTURE_COLOR_SAMPLES_NV = 0x9046;

    /// Groups: `GetPName`, `ColorTableTargetSGI`, `EnableCap`
    public static final int GL_TEXTURE_COLOR_TABLE_SGI = 0x80BC;

    public static final int GL_TEXTURE_COLOR_WRITEMASK_SGIS = 0x81EF;

    public static final int GL_TEXTURE_COMPARE_FAIL_VALUE_ARB = 0x80BF;

    /// Groups: `SamplerParameterI`, `TextureParameterName`
    public static final int GL_TEXTURE_COMPARE_FUNC = 0x884D;

    public static final int GL_TEXTURE_COMPARE_FUNC_ARB = 0x884D;

    public static final int GL_TEXTURE_COMPARE_FUNC_EXT = 0x884D;

    /// Groups: `SamplerParameterI`, `TextureParameterName`
    public static final int GL_TEXTURE_COMPARE_MODE = 0x884C;

    public static final int GL_TEXTURE_COMPARE_MODE_ARB = 0x884C;

    public static final int GL_TEXTURE_COMPARE_MODE_EXT = 0x884C;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_COMPARE_OPERATOR_SGIX = 0x819B;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_COMPARE_SGIX = 0x819A;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_COMPONENTS = 0x1003;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_COMPRESSED = 0x86A1;

    public static final int GL_TEXTURE_COMPRESSED_ARB = 0x86A1;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_HEIGHT = 0x82B2;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_SIZE = 0x82B3;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_COMPRESSED_BLOCK_WIDTH = 0x82B1;

    public static final int GL_TEXTURE_COMPRESSED_IMAGE_SIZE = 0x86A0;

    public static final int GL_TEXTURE_COMPRESSED_IMAGE_SIZE_ARB = 0x86A0;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_TEXTURE_COMPRESSION_HINT = 0x84EF;

    /// Group: `HintTarget`
    public static final int GL_TEXTURE_COMPRESSION_HINT_ARB = 0x84EF;

    public static final int GL_TEXTURE_CONSTANT_DATA_SUNX = 0x81D6;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_TEXTURE_COORD_ARRAY = 0x8078;

    public static final int GL_TEXTURE_COORD_ARRAY_ADDRESS_NV = 0x8F25;

    public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 0x889A;

    public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING_ARB = 0x889A;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_COORD_ARRAY_COUNT_EXT = 0x808B;

    public static final int GL_TEXTURE_COORD_ARRAY_EXT = 0x8078;

    public static final int GL_TEXTURE_COORD_ARRAY_LENGTH_NV = 0x8F2F;

    public static final int GL_TEXTURE_COORD_ARRAY_LIST_IBM = 103074;

    public static final int GL_TEXTURE_COORD_ARRAY_LIST_STRIDE_IBM = 103084;

    public static final int GL_TEXTURE_COORD_ARRAY_PARALLEL_POINTERS_INTEL = 0x83F8;

    /// Group: `GetPointervPName`
    public static final int GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092;

    /// Group: `GetPointervPName`
    public static final int GL_TEXTURE_COORD_ARRAY_POINTER_EXT = 0x8092;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_COORD_ARRAY_SIZE = 0x8088;

    public static final int GL_TEXTURE_COORD_ARRAY_SIZE_EXT = 0x8088;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_COORD_ARRAY_STRIDE = 0x808A;

    public static final int GL_TEXTURE_COORD_ARRAY_STRIDE_EXT = 0x808A;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_COORD_ARRAY_TYPE = 0x8089;

    public static final int GL_TEXTURE_COORD_ARRAY_TYPE_EXT = 0x8089;

    public static final int GL_TEXTURE_COORD_NV = 0x8C79;

    public static final int GL_TEXTURE_COVERAGE_SAMPLES_NV = 0x9045;

    public static final int GL_TEXTURE_CROP_RECT_OES = 0x8B9D;

    /// Groups: `CopyImageSubDataTarget`, `TextureTarget`, `EnableCap`
    public static final int GL_TEXTURE_CUBE_MAP = 0x8513;

    /// Groups: `TextureTarget`, `EnableCap`
    public static final int GL_TEXTURE_CUBE_MAP_ARB = 0x8513;

    /// Groups: `CopyImageSubDataTarget`, `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY = 0x9009;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY_ARB = 0x9009;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY_EXT = 0x9009;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_ARRAY_OES = 0x9009;

    /// Groups: `TextureTarget`, `EnableCap`
    public static final int GL_TEXTURE_CUBE_MAP_EXT = 0x8513;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X_ARB = 0x8516;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X_EXT = 0x8516;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_X_OES = 0x8516;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y_ARB = 0x8518;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y_EXT = 0x8518;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Y_OES = 0x8518;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z_ARB = 0x851A;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z_EXT = 0x851A;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_NEGATIVE_Z_OES = 0x851A;

    /// Groups: `TextureTarget`, `EnableCap`
    public static final int GL_TEXTURE_CUBE_MAP_OES = 0x8513;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X_ARB = 0x8515;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X_EXT = 0x8515;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_X_OES = 0x8515;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y_ARB = 0x8517;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y_EXT = 0x8517;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Y_OES = 0x8517;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z_ARB = 0x8519;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z_EXT = 0x8519;

    /// Group: `TextureTarget`
    public static final int GL_TEXTURE_CUBE_MAP_POSITIVE_Z_OES = 0x8519;

    /// Group: `EnableCap`
    public static final int GL_TEXTURE_CUBE_MAP_SEAMLESS = 0x884F;

    /// Group: `FfdMaskSGIX`
    public static final int GL_TEXTURE_DEFORMATION_BIT_SGIX = 0x00000001;

    /// Groups: `MapTarget`, `FfdTargetSGIX`
    public static final int GL_TEXTURE_DEFORMATION_SGIX = 0x8195;

    public static final int GL_TEXTURE_DEPTH = 0x8071;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_DEPTH_EXT = 0x8071;

    public static final int GL_TEXTURE_DEPTH_QCOM = 0x8BD4;

    public static final int GL_TEXTURE_DEPTH_SIZE = 0x884A;

    public static final int GL_TEXTURE_DEPTH_SIZE_ARB = 0x884A;

    public static final int GL_TEXTURE_DEPTH_TYPE = 0x8C16;

    public static final int GL_TEXTURE_DEPTH_TYPE_ARB = 0x8C16;

    public static final int GL_TEXTURE_DS_SIZE_NV = 0x871D;

    public static final int GL_TEXTURE_DT_SIZE_NV = 0x871E;

    /// Group: `TextureEnvTarget`
    public static final int GL_TEXTURE_ENV = 0x2300;

    /// Group: `TextureEnvMode`
    public static final int GL_TEXTURE_ENV_BIAS_SGIX = 0x80BE;

    /// Group: `TextureEnvParameter`
    public static final int GL_TEXTURE_ENV_COLOR = 0x2201;

    /// Group: `TextureEnvParameter`
    public static final int GL_TEXTURE_ENV_MODE = 0x2200;

    public static final int GL_TEXTURE_EXTERNAL_OES = 0x8D65;

    /// Group: `MemoryBarrierMask`
    public static final int GL_TEXTURE_FETCH_BARRIER_BIT = 0x00000008;

    /// Group: `MemoryBarrierMask`
    public static final int GL_TEXTURE_FETCH_BARRIER_BIT_EXT = 0x00000008;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_FILTER4_SIZE_SGIS = 0x8147;

    /// Group: `TextureEnvTarget`
    public static final int GL_TEXTURE_FILTER_CONTROL = 0x8500;

    public static final int GL_TEXTURE_FILTER_CONTROL_EXT = 0x8500;

    public static final int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 0x9107;

    public static final int GL_TEXTURE_FLOAT_COMPONENTS_NV = 0x888C;

    public static final int GL_TEXTURE_FORMAT_QCOM = 0x8BD6;

    public static final int GL_TEXTURE_FORMAT_SRGB_OVERRIDE_EXT = 0x8FBF;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_FOVEATED_CUTOFF_DENSITY_QCOM = 0x96A0;

    public static final int GL_TEXTURE_FOVEATED_FEATURE_BITS_QCOM = 0x8BFB;

    public static final int GL_TEXTURE_FOVEATED_FEATURE_QUERY_QCOM = 0x8BFD;

    public static final int GL_TEXTURE_FOVEATED_MIN_PIXEL_DENSITY_QCOM = 0x8BFC;

    public static final int GL_TEXTURE_FOVEATED_NUM_FOCAL_POINTS_QUERY_QCOM = 0x8BFE;

    public static final int GL_TEXTURE_FREE_MEMORY_ATI = 0x87FC;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_GATHER = 0x82A2;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_GATHER_SHADOW = 0x82A3;

    /// Group: `TextureGenParameter`
    public static final int GL_TEXTURE_GEN_MODE = 0x2500;

    /// Group: `TextureGenParameter`
    public static final int GL_TEXTURE_GEN_MODE_OES = 0x2500;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_TEXTURE_GEN_Q = 0x0C63;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_TEXTURE_GEN_R = 0x0C62;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_TEXTURE_GEN_S = 0x0C60;

    /// Groups: `EnableCap`, `GetPName`, `TextureCoordName`
    public static final int GL_TEXTURE_GEN_STR_OES = 0x8D60;

    /// Groups: `GetPName`, `EnableCap`
    public static final int GL_TEXTURE_GEN_T = 0x0C61;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_GEQUAL_R_SGIX = 0x819D;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_GREEN_SIZE = 0x805D;

    public static final int GL_TEXTURE_GREEN_SIZE_EXT = 0x805D;

    public static final int GL_TEXTURE_GREEN_TYPE = 0x8C11;

    public static final int GL_TEXTURE_GREEN_TYPE_ARB = 0x8C11;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_HEIGHT = 0x1001;

    public static final int GL_TEXTURE_HEIGHT_QCOM = 0x8BD3;

    public static final int GL_TEXTURE_HI_SIZE_NV = 0x871B;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_IMAGE_FORMAT = 0x828F;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_IMAGE_TYPE = 0x8290;

    public static final int GL_TEXTURE_IMAGE_VALID_QCOM = 0x8BD8;

    public static final int GL_TEXTURE_IMMUTABLE_FORMAT = 0x912F;

    public static final int GL_TEXTURE_IMMUTABLE_FORMAT_EXT = 0x912F;

    public static final int GL_TEXTURE_IMMUTABLE_LEVELS = 0x82DF;

    public static final int GL_TEXTURE_INDEX_SIZE_EXT = 0x80ED;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_INTENSITY_SIZE = 0x8061;

    public static final int GL_TEXTURE_INTENSITY_SIZE_EXT = 0x8061;

    public static final int GL_TEXTURE_INTENSITY_TYPE = 0x8C15;

    public static final int GL_TEXTURE_INTENSITY_TYPE_ARB = 0x8C15;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_INTERNAL_FORMAT = 0x1003;

    public static final int GL_TEXTURE_INTERNAL_FORMAT_QCOM = 0x8BD5;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_LEQUAL_R_SGIX = 0x819C;

    public static final int GL_TEXTURE_LIGHTING_MODE_HP = 0x8167;

    public static final int GL_TEXTURE_LIGHT_EXT = 0x8350;

    /// Groups: `TextureParameterName`, `SamplerParameterF`, `TextureEnvParameter`
    public static final int GL_TEXTURE_LOD_BIAS = 0x8501;

    public static final int GL_TEXTURE_LOD_BIAS_EXT = 0x8501;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_LOD_BIAS_R_SGIX = 0x8190;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_LOD_BIAS_S_SGIX = 0x818E;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_LOD_BIAS_T_SGIX = 0x818F;

    public static final int GL_TEXTURE_LO_SIZE_NV = 0x871C;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_LUMINANCE_SIZE = 0x8060;

    public static final int GL_TEXTURE_LUMINANCE_SIZE_EXT = 0x8060;

    public static final int GL_TEXTURE_LUMINANCE_TYPE = 0x8C14;

    public static final int GL_TEXTURE_LUMINANCE_TYPE_ARB = 0x8C14;

    /// Groups: `SamplerParameterI`, `GetTextureParameter`, `TextureParameterName`
    public static final int GL_TEXTURE_MAG_FILTER = 0x2800;

    public static final int GL_TEXTURE_MAG_SIZE_NV = 0x871F;

    public static final int GL_TEXTURE_MATERIAL_FACE_EXT = 0x8351;

    public static final int GL_TEXTURE_MATERIAL_PARAMETER_EXT = 0x8352;

    /// Groups: `GetPName`, `VertexShaderTextureUnitParameter`
    public static final int GL_TEXTURE_MATRIX = 0x0BA8;

    public static final int GL_TEXTURE_MATRIX_FLOAT_AS_INT_BITS_OES = 0x898F;

    /// Groups: `SamplerParameterF`, `TextureParameterName`
    public static final int GL_TEXTURE_MAX_ANISOTROPY = 0x84FE;

    /// Alias: `GL_TEXTURE_MAX_ANISOTROPY`
    public static final int GL_TEXTURE_MAX_ANISOTROPY_EXT = 0x84FE;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_MAX_CLAMP_R_SGIX = 0x836B;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_MAX_CLAMP_S_SGIX = 0x8369;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_MAX_CLAMP_T_SGIX = 0x836A;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_MAX_LEVEL = 0x813D;

    public static final int GL_TEXTURE_MAX_LEVEL_APPLE = 0x813D;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_MAX_LEVEL_SGIS = 0x813D;

    /// Groups: `SamplerParameterF`, `TextureParameterName`
    public static final int GL_TEXTURE_MAX_LOD = 0x813B;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_MAX_LOD_SGIS = 0x813B;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_MEMORY_LAYOUT_INTEL = 0x83FF;

    /// Groups: `SamplerParameterI`, `GetTextureParameter`, `TextureParameterName`
    public static final int GL_TEXTURE_MIN_FILTER = 0x2801;

    /// Groups: `SamplerParameterF`, `TextureParameterName`
    public static final int GL_TEXTURE_MIN_LOD = 0x813A;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_MIN_LOD_SGIS = 0x813A;

    /// Group: `HintTarget`
    public static final int GL_TEXTURE_MULTI_BUFFER_HINT_SGIX = 0x812E;

    public static final int GL_TEXTURE_NORMAL_EXT = 0x85AF;

    public static final int GL_TEXTURE_NUM_LEVELS_QCOM = 0x8BD9;

    public static final int GL_TEXTURE_OBJECT_VALID_QCOM = 0x8BDB;

    public static final int GL_TEXTURE_POST_SPECULAR_HP = 0x8168;

    public static final int GL_TEXTURE_PRE_SPECULAR_HP = 0x8169;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_PRIORITY = 0x8066;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_PRIORITY_EXT = 0x8066;

    public static final int GL_TEXTURE_PROTECTED_EXT = 0x8BFA;

    public static final int GL_TEXTURE_RANGE_LENGTH_APPLE = 0x85B7;

    public static final int GL_TEXTURE_RANGE_POINTER_APPLE = 0x85B8;

    /// Groups: `CopyImageSubDataTarget`, `TextureTarget`, `EnableCap`
    public static final int GL_TEXTURE_RECTANGLE = 0x84F5;

    /// Groups: `TextureTarget`, `EnableCap`
    public static final int GL_TEXTURE_RECTANGLE_ARB = 0x84F5;

    /// Groups: `TextureTarget`, `EnableCap`
    public static final int GL_TEXTURE_RECTANGLE_NV = 0x84F5;

    public static final int GL_TEXTURE_REDUCTION_MODE_ARB = 0x9366;

    /// Alias: `GL_TEXTURE_REDUCTION_MODE_ARB`
    public static final int GL_TEXTURE_REDUCTION_MODE_EXT = 0x9366;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_RED_SIZE = 0x805C;

    public static final int GL_TEXTURE_RED_SIZE_EXT = 0x805C;

    public static final int GL_TEXTURE_RED_TYPE = 0x8C10;

    public static final int GL_TEXTURE_RED_TYPE_ARB = 0x8C10;

    public static final int GL_TEXTURE_RENDERBUFFER_DATA_STORE_BINDING_NV = 0x8E54;

    public static final int GL_TEXTURE_RENDERBUFFER_NV = 0x8E55;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_RESIDENT = 0x8067;

    public static final int GL_TEXTURE_RESIDENT_EXT = 0x8067;

    public static final int GL_TEXTURE_SAMPLES = 0x9106;

    public static final int GL_TEXTURE_SAMPLES_IMG = 0x9136;

    public static final int GL_TEXTURE_SHADER_NV = 0x86DE;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_SHADOW = 0x82A1;

    public static final int GL_TEXTURE_SHARED_SIZE = 0x8C3F;

    public static final int GL_TEXTURE_SHARED_SIZE_EXT = 0x8C3F;

    public static final int GL_TEXTURE_SPARSE_ARB = 0x91A6;

    public static final int GL_TEXTURE_SPARSE_EXT = 0x91A6;

    public static final int GL_TEXTURE_SRGB_DECODE_EXT = 0x8A48;

    /// Group: `GetPName`
    public static final int GL_TEXTURE_STACK_DEPTH = 0x0BA5;

    public static final int GL_TEXTURE_STENCIL_SIZE = 0x88F1;

    public static final int GL_TEXTURE_STENCIL_SIZE_EXT = 0x88F1;

    /// Group: `HintTarget`
    public static final int GL_TEXTURE_STORAGE_HINT_APPLE = 0x85BC;

    /// Group: `TextureStorageMaskAMD`
    public static final int GL_TEXTURE_STORAGE_SPARSE_BIT_AMD = 0x00000001;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_SWIZZLE_A = 0x8E45;

    public static final int GL_TEXTURE_SWIZZLE_A_EXT = 0x8E45;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_SWIZZLE_B = 0x8E44;

    public static final int GL_TEXTURE_SWIZZLE_B_EXT = 0x8E44;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_SWIZZLE_G = 0x8E43;

    public static final int GL_TEXTURE_SWIZZLE_G_EXT = 0x8E43;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_SWIZZLE_R = 0x8E42;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_SWIZZLE_RGBA = 0x8E46;

    public static final int GL_TEXTURE_SWIZZLE_RGBA_EXT = 0x8E46;

    public static final int GL_TEXTURE_SWIZZLE_R_EXT = 0x8E42;

    public static final int GL_TEXTURE_TARGET = 0x1006;

    public static final int GL_TEXTURE_TARGET_QCOM = 0x8BDA;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_TILING_EXT = 0x9580;

    /// Group: `ErrorCode`
    public static final int GL_TEXTURE_TOO_LARGE_EXT = 0x8065;

    public static final int GL_TEXTURE_TYPE_QCOM = 0x8BD7;

    /// Groups: `SamplerParameterF`, `SamplerParameterI`, `GetTextureParameter`,
    ///   `TextureParameterName`
    public static final int GL_TEXTURE_UNNORMALIZED_COORDINATES_ARM = 0x8F6A;

    public static final int GL_TEXTURE_UNSIGNED_REMAP_MODE_NV = 0x888F;

    /// Group: `MemoryBarrierMask`
    public static final int GL_TEXTURE_UPDATE_BARRIER_BIT = 0x00000100;

    /// Group: `MemoryBarrierMask`
    public static final int GL_TEXTURE_UPDATE_BARRIER_BIT_EXT = 0x00000100;

    public static final int GL_TEXTURE_USAGE_ANGLE = 0x93A2;

    /// Group: `InternalFormatPName`
    public static final int GL_TEXTURE_VIEW = 0x82B5;

    public static final int GL_TEXTURE_VIEW_MIN_LAYER = 0x82DD;

    public static final int GL_TEXTURE_VIEW_MIN_LAYER_EXT = 0x82DD;

    public static final int GL_TEXTURE_VIEW_MIN_LAYER_OES = 0x82DD;

    public static final int GL_TEXTURE_VIEW_MIN_LEVEL = 0x82DB;

    public static final int GL_TEXTURE_VIEW_MIN_LEVEL_EXT = 0x82DB;

    public static final int GL_TEXTURE_VIEW_MIN_LEVEL_OES = 0x82DB;

    public static final int GL_TEXTURE_VIEW_NUM_LAYERS = 0x82DE;

    public static final int GL_TEXTURE_VIEW_NUM_LAYERS_EXT = 0x82DE;

    public static final int GL_TEXTURE_VIEW_NUM_LAYERS_OES = 0x82DE;

    public static final int GL_TEXTURE_VIEW_NUM_LEVELS = 0x82DC;

    public static final int GL_TEXTURE_VIEW_NUM_LEVELS_EXT = 0x82DC;

    public static final int GL_TEXTURE_VIEW_NUM_LEVELS_OES = 0x82DC;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_WIDTH = 0x1000;

    public static final int GL_TEXTURE_WIDTH_QCOM = 0x8BD2;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_WRAP_Q_SGIS = 0x8137;

    /// Groups: `SamplerParameterI`, `TextureParameterName`
    public static final int GL_TEXTURE_WRAP_R = 0x8072;

    /// Groups: `TextureParameterName`, `GetTextureParameter`
    public static final int GL_TEXTURE_WRAP_R_EXT = 0x8072;

    /// Group: `TextureParameterName`
    public static final int GL_TEXTURE_WRAP_R_OES = 0x8072;

    /// Groups: `SamplerParameterI`, `GetTextureParameter`, `TextureParameterName`
    public static final int GL_TEXTURE_WRAP_S = 0x2802;

    /// Groups: `SamplerParameterI`, `GetTextureParameter`, `TextureParameterName`
    public static final int GL_TEXTURE_WRAP_T = 0x2803;

    /// Group: `ProgramTarget`
    public static final int GL_TEXT_FRAGMENT_SHADER_ATI = 0x8200;

    public static final int GL_TILE_RASTER_ORDER_FIXED_MESA = 0x8BB8;

    public static final int GL_TILE_RASTER_ORDER_INCREASING_X_MESA = 0x8BB9;

    public static final int GL_TILE_RASTER_ORDER_INCREASING_Y_MESA = 0x8BBA;

    public static final int GL_TILING_TYPES_EXT = 0x9583;

    /// Group: `SemaphoreParameterName`
    public static final int GL_TIMELINE_SEMAPHORE_VALUE_NV = 0x9595;

    /// Group: `SyncStatus`
    public static final int GL_TIMEOUT_EXPIRED = 0x911B;

    public static final int GL_TIMEOUT_EXPIRED_APPLE = 0x911B;

    /// Groups: `QueryCounterTarget`, `GetPName`
    public static final int GL_TIMESTAMP = 0x8E28;

    /// Groups: `QueryCounterTarget`, `GetPName`
    public static final int GL_TIMESTAMP_EXT = 0x8E28;

    /// Group: `QueryTarget`
    public static final int GL_TIME_ELAPSED = 0x88BF;

    public static final int GL_TIME_ELAPSED_EXT = 0x88BF;

    /// Group: `ProgramResourceProperty`
    public static final int GL_TOP_LEVEL_ARRAY_SIZE = 0x930C;

    /// Group: `ProgramResourceProperty`
    public static final int GL_TOP_LEVEL_ARRAY_STRIDE = 0x930D;

    /// Group: `TraceMaskMESA`
    public static final int GL_TRACE_ALL_BITS_MESA = 0xFFFF;

    /// Group: `TraceMaskMESA`
    public static final int GL_TRACE_ARRAYS_BIT_MESA = 0x0004;

    /// Group: `TraceMaskMESA`
    public static final int GL_TRACE_ERRORS_BIT_MESA = 0x0020;

    public static final int GL_TRACE_MASK_MESA = 0x8755;

    public static final int GL_TRACE_NAME_MESA = 0x8756;

    /// Group: `TraceMaskMESA`
    public static final int GL_TRACE_OPERATIONS_BIT_MESA = 0x0001;

    /// Group: `TraceMaskMESA`
    public static final int GL_TRACE_PIXELS_BIT_MESA = 0x0010;

    /// Group: `TraceMaskMESA`
    public static final int GL_TRACE_PRIMITIVES_BIT_MESA = 0x0002;

    /// Group: `TraceMaskMESA`
    public static final int GL_TRACE_TEXTURES_BIT_MESA = 0x0008;

    public static final int GL_TRACK_MATRIX_NV = 0x8648;

    public static final int GL_TRACK_MATRIX_TRANSFORM_NV = 0x8649;

    /// Group: `AttribMask`
    public static final int GL_TRANSFORM_BIT = 0x00001000;

    /// Groups: `ObjectIdentifier`, `BindTransformFeedbackTarget`
    public static final int GL_TRANSFORM_FEEDBACK = 0x8E22;

    /// Group: `TransformFeedbackPName`
    /// Alias: `GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE`
    public static final int GL_TRANSFORM_FEEDBACK_ACTIVE = 0x8E24;

    public static final int GL_TRANSFORM_FEEDBACK_ATTRIBS_NV = 0x8C7E;

    /// Group: `MemoryBarrierMask`
    public static final int GL_TRANSFORM_FEEDBACK_BARRIER_BIT = 0x00000800;

    /// Group: `MemoryBarrierMask`
    public static final int GL_TRANSFORM_FEEDBACK_BARRIER_BIT_EXT = 0x00000800;

    public static final int GL_TRANSFORM_FEEDBACK_BINDING = 0x8E25;

    public static final int GL_TRANSFORM_FEEDBACK_BINDING_NV = 0x8E25;

    /// Groups: `ProgramInterface`, `BufferTargetARB`, `BufferStorageTarget`,
    ///   `CopyBufferSubDataTarget`
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER = 0x8C8E;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE = 0x8E24;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_ACTIVE_NV = 0x8E24;

    /// Groups: `TransformFeedbackPName`, `GetPName`
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING = 0x8C8F;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING_EXT = 0x8C8F;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_BINDING_NV = 0x8C8F;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_EXT = 0x8C8E;

    /// Group: `ProgramResourceProperty`
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_INDEX = 0x934B;

    /// Group: `ProgramPropertyARB`
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE = 0x8C7F;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE_EXT = 0x8C7F;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_MODE_NV = 0x8C7F;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_NV = 0x8C8E;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED = 0x8E23;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED_NV = 0x8E23;

    /// Groups: `TransformFeedbackPName`, `GetPName`
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE = 0x8C85;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE_EXT = 0x8C85;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_SIZE_NV = 0x8C85;

    /// Groups: `TransformFeedbackPName`, `GetPName`
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START = 0x8C84;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START_EXT = 0x8C84;

    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_START_NV = 0x8C84;

    /// Group: `ProgramResourceProperty`
    public static final int GL_TRANSFORM_FEEDBACK_BUFFER_STRIDE = 0x934C;

    public static final int GL_TRANSFORM_FEEDBACK_NV = 0x8E22;

    /// Group: `QueryTarget`
    public static final int GL_TRANSFORM_FEEDBACK_OVERFLOW = 0x82EC;

    /// Alias: `GL_TRANSFORM_FEEDBACK_OVERFLOW`
    public static final int GL_TRANSFORM_FEEDBACK_OVERFLOW_ARB = 0x82EC;

    /// Group: `TransformFeedbackPName`
    /// Alias: `GL_TRANSFORM_FEEDBACK_BUFFER_PAUSED`
    public static final int GL_TRANSFORM_FEEDBACK_PAUSED = 0x8E23;

    /// Group: `QueryTarget`
    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN = 0x8C88;

    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN_EXT = 0x8C88;

    public static final int GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN_NV = 0x8C88;

    public static final int GL_TRANSFORM_FEEDBACK_RECORD_NV = 0x8C86;

    public static final int GL_TRANSFORM_FEEDBACK_STREAM_OVERFLOW = 0x82ED;

    /// Alias: `GL_TRANSFORM_FEEDBACK_STREAM_OVERFLOW`
    public static final int GL_TRANSFORM_FEEDBACK_STREAM_OVERFLOW_ARB = 0x82ED;

    /// Group: `ProgramInterface`
    public static final int GL_TRANSFORM_FEEDBACK_VARYING = 0x92F4;

    /// Group: `ProgramPropertyARB`
    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS = 0x8C83;

    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS_EXT = 0x8C83;

    public static final int GL_TRANSFORM_FEEDBACK_VARYINGS_NV = 0x8C83;

    /// Group: `ProgramPropertyARB`
    public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH = 0x8C76;

    public static final int GL_TRANSFORM_FEEDBACK_VARYING_MAX_LENGTH_EXT = 0x8C76;

    /// Group: `HintTarget`
    public static final int GL_TRANSFORM_HINT_APPLE = 0x85B1;

    public static final int GL_TRANSLATED_SHADER_SOURCE_LENGTH_ANGLE = 0x93A0;

    /// Group: `PathTransformType`
    public static final int GL_TRANSLATE_2D_NV = 0x9090;

    /// Group: `PathTransformType`
    public static final int GL_TRANSLATE_3D_NV = 0x9091;

    /// Group: `PathTransformType`
    public static final int GL_TRANSLATE_X_NV = 0x908E;

    /// Group: `PathTransformType`
    public static final int GL_TRANSLATE_Y_NV = 0x908F;

    /// Group: `PathTransformType`
    public static final int GL_TRANSPOSE_AFFINE_2D_NV = 0x9096;

    /// Group: `PathTransformType`
    public static final int GL_TRANSPOSE_AFFINE_3D_NV = 0x9098;

    public static final int GL_TRANSPOSE_COLOR_MATRIX = 0x84E6;

    public static final int GL_TRANSPOSE_COLOR_MATRIX_ARB = 0x84E6;

    public static final int GL_TRANSPOSE_CURRENT_MATRIX_ARB = 0x88B7;

    public static final int GL_TRANSPOSE_MODELVIEW_MATRIX = 0x84E3;

    public static final int GL_TRANSPOSE_MODELVIEW_MATRIX_ARB = 0x84E3;

    public static final int GL_TRANSPOSE_NV = 0x862C;

    public static final int GL_TRANSPOSE_PROGRAM_MATRIX_EXT = 0x8E2E;

    public static final int GL_TRANSPOSE_PROJECTION_MATRIX = 0x84E4;

    public static final int GL_TRANSPOSE_PROJECTION_MATRIX_ARB = 0x84E4;

    public static final int GL_TRANSPOSE_TEXTURE_MATRIX = 0x84E5;

    public static final int GL_TRANSPOSE_TEXTURE_MATRIX_ARB = 0x84E5;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLES = 0x0004;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLES_ADJACENCY = 0x000C;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLES_ADJACENCY_ARB = 0x000C;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLES_ADJACENCY_EXT = 0x000C;

    public static final int GL_TRIANGLES_ADJACENCY_OES = 0x000C;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLE_FAN = 0x0006;

    public static final int GL_TRIANGLE_LIST_SUN = 0x81D7;

    public static final int GL_TRIANGLE_MESH_SUN = 0x8615;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLE_STRIP = 0x0005;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLE_STRIP_ADJACENCY = 0x000D;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLE_STRIP_ADJACENCY_ARB = 0x000D;

    /// Group: `PrimitiveType`
    public static final int GL_TRIANGLE_STRIP_ADJACENCY_EXT = 0x000D;

    public static final int GL_TRIANGLE_STRIP_ADJACENCY_OES = 0x000D;

    public static final int GL_TRIANGULAR_NV = 0x90A5;

    public static final int GL_TRP_ERROR_CONTEXT_RESET_IMG = 0x8EA1;

    public static final int GL_TRP_IMG = 0x8EA0;

    public static final int GL_TRP_UNSUPPORTED_CONTEXT_IMG = 0x8EA2;

    /// Groups: `SpecialNumbers`, `Boolean`, `VertexShaderWriteMaskEXT`,
    ///   `ClampColorModeARB`
    public static final int GL_TRUE = 1;

    /// Group: `ProgramResourceProperty`
    public static final int GL_TYPE = 0x92FA;

    public static final int GL_UNCORRELATED_NV = 0x9282;

    public static final int GL_UNDEFINED_APPLE = 0x8A1C;

    public static final int GL_UNDEFINED_VERTEX = 0x8260;

    public static final int GL_UNDEFINED_VERTEX_EXT = 0x8260;

    public static final int GL_UNDEFINED_VERTEX_OES = 0x8260;

    /// Groups: `ProgramResourceProperty`, `ProgramInterface`
    public static final int GL_UNIFORM = 0x92E1;

    /// Group: `CommandOpcodesNV`
    public static final int GL_UNIFORM_ADDRESS_COMMAND_NV = 0x000A;

    /// Group: `UniformPName`
    public static final int GL_UNIFORM_ARRAY_STRIDE = 0x8A3C;

    /// Group: `UniformPName`
    public static final int GL_UNIFORM_ATOMIC_COUNTER_BUFFER_INDEX = 0x92DA;

    /// Group: `MemoryBarrierMask`
    public static final int GL_UNIFORM_BARRIER_BIT = 0x00000004;

    /// Group: `MemoryBarrierMask`
    public static final int GL_UNIFORM_BARRIER_BIT_EXT = 0x00000004;

    /// Group: `ProgramInterface`
    public static final int GL_UNIFORM_BLOCK = 0x92E2;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORMS = 0x8A42;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_ACTIVE_UNIFORM_INDICES = 0x8A43;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_BINDING = 0x8A3F;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_DATA_SIZE = 0x8A40;

    /// Group: `UniformPName`
    public static final int GL_UNIFORM_BLOCK_INDEX = 0x8A3A;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_NAME_LENGTH = 0x8A41;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_COMPUTE_SHADER = 0x90EC;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_FRAGMENT_SHADER = 0x8A46;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_GEOMETRY_SHADER = 0x8A45;

    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_MESH_SHADER_NV = 0x959C;

    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TASK_SHADER_NV = 0x959D;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_CONTROL_SHADER = 0x84F0;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_TESS_EVALUATION_SHADER = 0x84F1;

    /// Group: `UniformBlockPName`
    public static final int GL_UNIFORM_BLOCK_REFERENCED_BY_VERTEX_SHADER = 0x8A44;

    /// Groups: `CopyBufferSubDataTarget`, `BufferTargetARB`,
    ///   `BufferStorageTarget`
    public static final int GL_UNIFORM_BUFFER = 0x8A11;

    public static final int GL_UNIFORM_BUFFER_ADDRESS_NV = 0x936F;

    /// Group: `GetPName`
    public static final int GL_UNIFORM_BUFFER_BINDING = 0x8A28;

    public static final int GL_UNIFORM_BUFFER_BINDING_EXT = 0x8DEF;

    public static final int GL_UNIFORM_BUFFER_EXT = 0x8DEE;

    public static final int GL_UNIFORM_BUFFER_LENGTH_NV = 0x9370;

    /// Group: `GetPName`
    public static final int GL_UNIFORM_BUFFER_OFFSET_ALIGNMENT = 0x8A34;

    /// Group: `GetPName`
    public static final int GL_UNIFORM_BUFFER_SIZE = 0x8A2A;

    /// Group: `GetPName`
    public static final int GL_UNIFORM_BUFFER_START = 0x8A29;

    public static final int GL_UNIFORM_BUFFER_UNIFIED_NV = 0x936E;

    /// Group: `UniformPName`
    public static final int GL_UNIFORM_IS_ROW_MAJOR = 0x8A3E;

    /// Group: `UniformPName`
    public static final int GL_UNIFORM_MATRIX_STRIDE = 0x8A3D;

    /// Groups: `SubroutineParameterName`, `UniformPName`
    public static final int GL_UNIFORM_NAME_LENGTH = 0x8A39;

    /// Group: `UniformPName`
    public static final int GL_UNIFORM_OFFSET = 0x8A3B;

    /// Groups: `SubroutineParameterName`, `UniformPName`
    public static final int GL_UNIFORM_SIZE = 0x8A38;

    /// Group: `UniformPName`
    public static final int GL_UNIFORM_TYPE = 0x8A37;

    /// Group: `GraphicsResetStatus`
    public static final int GL_UNKNOWN_CONTEXT_RESET = 0x8255;

    public static final int GL_UNKNOWN_CONTEXT_RESET_ARB = 0x8255;

    public static final int GL_UNKNOWN_CONTEXT_RESET_EXT = 0x8255;

    public static final int GL_UNKNOWN_CONTEXT_RESET_KHR = 0x8255;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_ALIGNMENT = 0x0CF5;

    public static final int GL_UNPACK_CLIENT_STORAGE_APPLE = 0x85B2;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_UNPACK_CMYK_HINT_EXT = 0x800F;

    public static final int GL_UNPACK_COLORSPACE_CONVERSION_WEBGL = 0x9243;

    public static final int GL_UNPACK_COMPRESSED_BLOCK_DEPTH = 0x9129;

    public static final int GL_UNPACK_COMPRESSED_BLOCK_HEIGHT = 0x9128;

    public static final int GL_UNPACK_COMPRESSED_BLOCK_SIZE = 0x912A;

    public static final int GL_UNPACK_COMPRESSED_BLOCK_WIDTH = 0x9127;

    public static final int GL_UNPACK_COMPRESSED_SIZE_SGIX = 0x831A;

    public static final int GL_UNPACK_CONSTANT_DATA_SUNX = 0x81D5;

    public static final int GL_UNPACK_FLIP_Y_WEBGL = 0x9240;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_IMAGE_DEPTH_SGIS = 0x8133;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_IMAGE_HEIGHT = 0x806E;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_IMAGE_HEIGHT_EXT = 0x806E;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_LSB_FIRST = 0x0CF1;

    public static final int GL_UNPACK_PREMULTIPLY_ALPHA_WEBGL = 0x9241;

    /// Group: `PixelStoreParameter`
    public static final int GL_UNPACK_RESAMPLE_OML = 0x8985;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_RESAMPLE_SGIX = 0x842F;

    public static final int GL_UNPACK_ROW_BYTES_APPLE = 0x8A16;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_ROW_LENGTH = 0x0CF2;

    /// Group: `PixelStoreParameter`
    public static final int GL_UNPACK_ROW_LENGTH_EXT = 0x0CF2;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_SKIP_IMAGES = 0x806D;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_SKIP_IMAGES_EXT = 0x806D;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_SKIP_PIXELS = 0x0CF4;

    /// Group: `PixelStoreParameter`
    public static final int GL_UNPACK_SKIP_PIXELS_EXT = 0x0CF4;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_SKIP_ROWS = 0x0CF3;

    /// Group: `PixelStoreParameter`
    public static final int GL_UNPACK_SKIP_ROWS_EXT = 0x0CF3;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_SKIP_VOLUMES_SGIS = 0x8132;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_SUBSAMPLE_RATE_SGIX = 0x85A1;

    /// Groups: `PixelStoreParameter`, `GetPName`
    public static final int GL_UNPACK_SWAP_BYTES = 0x0CF0;

    public static final int GL_UNSIGNALED = 0x9118;

    public static final int GL_UNSIGNALED_APPLE = 0x9118;

    /// Groups: `VertexAttribIType`, `ScalarType`, `ReplacementCodeTypeSUN`,
    ///   `ElementPointerTypeATI`, `MatrixIndexPointerTypeARB`,
    ///   `WeightPointerTypeARB`, `ColorPointerType`, `DrawElementsType`,
    ///   `ListNameType`, `PixelType`, `VertexAttribType`, `VertexAttribPointerType`
    public static final int GL_UNSIGNED_BYTE = 0x1401;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_BYTE_2_3_3_REV = 0x8362;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_BYTE_2_3_3_REV_EXT = 0x8362;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_BYTE_3_3_2 = 0x8032;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_BYTE_3_3_2_EXT = 0x8032;

    /// Group: `CombinerMappingNV`
    public static final int GL_UNSIGNED_IDENTITY_NV = 0x8536;

    /// Groups: `VertexAttribIType`, `ScalarType`, `ReplacementCodeTypeSUN`,
    ///   `ElementPointerTypeATI`, `MatrixIndexPointerTypeARB`,
    ///   `WeightPointerTypeARB`, `ColorPointerType`, `DrawElementsType`,
    ///   `ListNameType`, `PixelFormat`, `PixelType`, `VertexAttribType`,
    ///   `AttributeType`, `UniformType`, `VertexAttribPointerType`
    public static final int GL_UNSIGNED_INT = 0x1405;

    public static final int GL_UNSIGNED_INT16_NV = 0x8FF0;

    public static final int GL_UNSIGNED_INT16_VEC2_NV = 0x8FF1;

    public static final int GL_UNSIGNED_INT16_VEC3_NV = 0x8FF2;

    public static final int GL_UNSIGNED_INT16_VEC4_NV = 0x8FF3;

    public static final int GL_UNSIGNED_INT64_AMD = 0x8BC2;

    /// Groups: `VertexAttribPointerType`, `AttributeType`
    public static final int GL_UNSIGNED_INT64_ARB = 0x140F;

    /// Groups: `VertexAttribPointerType`, `AttributeType`
    public static final int GL_UNSIGNED_INT64_NV = 0x140F;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT64_VEC2_ARB = 0x8FF5;

    public static final int GL_UNSIGNED_INT64_VEC2_NV = 0x8FF5;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT64_VEC3_ARB = 0x8FF6;

    public static final int GL_UNSIGNED_INT64_VEC3_NV = 0x8FF6;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT64_VEC4_ARB = 0x8FF7;

    public static final int GL_UNSIGNED_INT64_VEC4_NV = 0x8FF7;

    public static final int GL_UNSIGNED_INT8_NV = 0x8FEC;

    public static final int GL_UNSIGNED_INT8_VEC2_NV = 0x8FED;

    public static final int GL_UNSIGNED_INT8_VEC3_NV = 0x8FEE;

    public static final int GL_UNSIGNED_INT8_VEC4_NV = 0x8FEF;

    /// Groups: `PixelType`, `VertexAttribPointerType`, `VertexAttribType`
    public static final int GL_UNSIGNED_INT_10F_11F_11F_REV = 0x8C3B;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_10F_11F_11F_REV_APPLE = 0x8C3B;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_10F_11F_11F_REV_EXT = 0x8C3B;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_10_10_10_2 = 0x8036;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_10_10_10_2_EXT = 0x8036;

    public static final int GL_UNSIGNED_INT_10_10_10_2_OES = 0x8DF6;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_24_8 = 0x84FA;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_24_8_EXT = 0x84FA;

    public static final int GL_UNSIGNED_INT_24_8_MESA = 0x8751;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_24_8_NV = 0x84FA;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_24_8_OES = 0x84FA;

    /// Groups: `PixelType`, `VertexAttribPointerType`, `VertexAttribType`
    public static final int GL_UNSIGNED_INT_2_10_10_10_REV = 0x8368;

    /// Groups: `PixelType`, `VertexAttribPointerType`, `VertexAttribType`
    public static final int GL_UNSIGNED_INT_2_10_10_10_REV_EXT = 0x8368;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_5_9_9_9_REV = 0x8C3E;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_5_9_9_9_REV_APPLE = 0x8C3E;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_5_9_9_9_REV_EXT = 0x8C3E;

    public static final int GL_UNSIGNED_INT_8_24_REV_MESA = 0x8752;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_8_8_8_8 = 0x8035;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_8_8_8_8_EXT = 0x8035;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_8_8_8_8_REV = 0x8367;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_INT_8_8_8_8_REV_EXT = 0x8367;

    public static final int GL_UNSIGNED_INT_8_8_S8_S8_REV_NV = 0x86DB;

    public static final int GL_UNSIGNED_INT_ATOMIC_COUNTER = 0x92DB;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_1D = 0x9062;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_1D_ARRAY = 0x9068;

    public static final int GL_UNSIGNED_INT_IMAGE_1D_ARRAY_EXT = 0x9068;

    public static final int GL_UNSIGNED_INT_IMAGE_1D_EXT = 0x9062;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_2D = 0x9063;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_2D_ARRAY = 0x9069;

    public static final int GL_UNSIGNED_INT_IMAGE_2D_ARRAY_EXT = 0x9069;

    public static final int GL_UNSIGNED_INT_IMAGE_2D_EXT = 0x9063;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE = 0x906B;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_ARRAY = 0x906C;

    public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_ARRAY_EXT = 0x906C;

    public static final int GL_UNSIGNED_INT_IMAGE_2D_MULTISAMPLE_EXT = 0x906B;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_2D_RECT = 0x9065;

    public static final int GL_UNSIGNED_INT_IMAGE_2D_RECT_EXT = 0x9065;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_3D = 0x9064;

    public static final int GL_UNSIGNED_INT_IMAGE_3D_EXT = 0x9064;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_BUFFER = 0x9067;

    public static final int GL_UNSIGNED_INT_IMAGE_BUFFER_EXT = 0x9067;

    public static final int GL_UNSIGNED_INT_IMAGE_BUFFER_OES = 0x9067;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_CUBE = 0x9066;

    public static final int GL_UNSIGNED_INT_IMAGE_CUBE_EXT = 0x9066;

    /// Group: `AttributeType`
    public static final int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY = 0x906A;

    public static final int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY_EXT = 0x906A;

    public static final int GL_UNSIGNED_INT_IMAGE_CUBE_MAP_ARRAY_OES = 0x906A;

    public static final int GL_UNSIGNED_INT_S8_S8_8_8_NV = 0x86DA;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_1D = 0x8DD1;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY = 0x8DD6;

    public static final int GL_UNSIGNED_INT_SAMPLER_1D_ARRAY_EXT = 0x8DD6;

    public static final int GL_UNSIGNED_INT_SAMPLER_1D_EXT = 0x8DD1;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_2D = 0x8DD2;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY = 0x8DD7;

    public static final int GL_UNSIGNED_INT_SAMPLER_2D_ARRAY_EXT = 0x8DD7;

    public static final int GL_UNSIGNED_INT_SAMPLER_2D_EXT = 0x8DD2;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 0x910A;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 0x910D;

    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY_OES = 0x910D;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_RECT = 0x8DD5;

    public static final int GL_UNSIGNED_INT_SAMPLER_2D_RECT_EXT = 0x8DD5;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_3D = 0x8DD3;

    public static final int GL_UNSIGNED_INT_SAMPLER_3D_EXT = 0x8DD3;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER = 0x8DD8;

    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_AMD = 0x9003;

    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_EXT = 0x8DD8;

    public static final int GL_UNSIGNED_INT_SAMPLER_BUFFER_OES = 0x8DD8;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE = 0x8DD4;

    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_EXT = 0x8DD4;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY = 0x900F;

    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY_ARB = 0x900F;

    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY_EXT = 0x900F;

    public static final int GL_UNSIGNED_INT_SAMPLER_CUBE_MAP_ARRAY_OES = 0x900F;

    public static final int GL_UNSIGNED_INT_SAMPLER_RENDERBUFFER_NV = 0x8E58;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_VEC2 = 0x8DC6;

    public static final int GL_UNSIGNED_INT_VEC2_EXT = 0x8DC6;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_VEC3 = 0x8DC7;

    public static final int GL_UNSIGNED_INT_VEC3_EXT = 0x8DC7;

    /// Groups: `AttributeType`, `UniformType`
    public static final int GL_UNSIGNED_INT_VEC4 = 0x8DC8;

    public static final int GL_UNSIGNED_INT_VEC4_EXT = 0x8DC8;

    /// Group: `CombinerMappingNV`
    public static final int GL_UNSIGNED_INVERT_NV = 0x8537;

    public static final int GL_UNSIGNED_NORMALIZED = 0x8C17;

    public static final int GL_UNSIGNED_NORMALIZED_ARB = 0x8C17;

    public static final int GL_UNSIGNED_NORMALIZED_EXT = 0x8C17;

    /// Groups: `VertexAttribIType`, `ScalarType`, `ReplacementCodeTypeSUN`,
    ///   `ElementPointerTypeATI`, `MatrixIndexPointerTypeARB`,
    ///   `WeightPointerTypeARB`, `ColorPointerType`, `DrawElementsType`,
    ///   `ListNameType`, `PixelFormat`, `PixelType`, `VertexAttribType`,
    ///   `VertexAttribPointerType`
    public static final int GL_UNSIGNED_SHORT = 0x1403;

    public static final int GL_UNSIGNED_SHORT_15_1_MESA = 0x8753;

    public static final int GL_UNSIGNED_SHORT_1_15_REV_MESA = 0x8754;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV = 0x8366;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV_EXT = 0x8366;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 0x8033;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_EXT = 0x8033;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV = 0x8365;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV_EXT = 0x8365;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV_IMG = 0x8365;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 0x8034;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_5_5_5_1_EXT = 0x8034;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_5_6_5 = 0x8363;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_5_6_5_EXT = 0x8363;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_5_6_5_REV = 0x8364;

    /// Group: `PixelType`
    public static final int GL_UNSIGNED_SHORT_5_6_5_REV_EXT = 0x8364;

    public static final int GL_UNSIGNED_SHORT_8_8_APPLE = 0x85BA;

    public static final int GL_UNSIGNED_SHORT_8_8_MESA = 0x85BA;

    public static final int GL_UNSIGNED_SHORT_8_8_REV_APPLE = 0x85BB;

    public static final int GL_UNSIGNED_SHORT_8_8_REV_MESA = 0x85BB;

    public static final int GL_UPLOAD_GPU_MASK_NVX = 0x954A;

    /// Group: `ClipControlOrigin`
    public static final int GL_UPPER_LEFT = 0x8CA2;

    /// Alias: `GL_UPPER_LEFT`
    public static final int GL_UPPER_LEFT_EXT = 0x8CA2;

    /// Group: `PathHandleMissingGlyphs`
    public static final int GL_USE_MISSING_GLYPH_NV = 0x90AA;

    /// Group: `PathElementType`
    public static final int GL_UTF16_NV = 0x909B;

    /// Group: `PathElementType`
    public static final int GL_UTF8_NV = 0x909A;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_V2F = 0x2A20;

    /// Group: `InterleavedArrayFormat`
    public static final int GL_V3F = 0x2A21;

    public static final int GL_VALIDATE_SHADER_BINARY_QCOM = 0x96A3;

    /// Group: `ProgramPropertyARB`
    public static final int GL_VALIDATE_STATUS = 0x8B83;

    /// Group: `CombinerVariableNV`
    public static final int GL_VARIABLE_A_NV = 0x8523;

    /// Group: `CombinerVariableNV`
    public static final int GL_VARIABLE_B_NV = 0x8524;

    /// Group: `CombinerVariableNV`
    public static final int GL_VARIABLE_C_NV = 0x8525;

    /// Group: `CombinerVariableNV`
    public static final int GL_VARIABLE_D_NV = 0x8526;

    /// Group: `CombinerVariableNV`
    public static final int GL_VARIABLE_E_NV = 0x8527;

    /// Group: `CombinerVariableNV`
    public static final int GL_VARIABLE_F_NV = 0x8528;

    /// Group: `CombinerVariableNV`
    public static final int GL_VARIABLE_G_NV = 0x8529;

    /// Group: `VariantCapEXT`
    public static final int GL_VARIANT_ARRAY_EXT = 0x87E8;

    public static final int GL_VARIANT_ARRAY_POINTER_EXT = 0x87E9;

    /// Group: `GetVariantValueEXT`
    public static final int GL_VARIANT_ARRAY_STRIDE_EXT = 0x87E6;

    /// Group: `GetVariantValueEXT`
    public static final int GL_VARIANT_ARRAY_TYPE_EXT = 0x87E7;

    /// Group: `GetVariantValueEXT`
    public static final int GL_VARIANT_DATATYPE_EXT = 0x87E5;

    /// Group: `VertexShaderStorageTypeEXT`
    public static final int GL_VARIANT_EXT = 0x87C1;

    /// Group: `GetVariantValueEXT`
    public static final int GL_VARIANT_VALUE_EXT = 0x87E4;

    public static final int GL_VBO_FREE_MEMORY_ATI = 0x87FB;

    /// Group: `DataTypeEXT`
    public static final int GL_VECTOR_EXT = 0x87BF;

    /// Group: `StringName`
    public static final int GL_VENDOR = 0x1F00;

    /// Group: `StringName`
    public static final int GL_VERSION = 0x1F02;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_VERTEX23_BIT_PGI = 0x00000004;

    /// Group: `VertexHintsMaskPGI`
    public static final int GL_VERTEX4_BIT_PGI = 0x00000008;

    /// Groups: `ObjectIdentifier`, `EnableCap`, `GetPName`
    public static final int GL_VERTEX_ARRAY = 0x8074;

    public static final int GL_VERTEX_ARRAY_ADDRESS_NV = 0x8F21;

    /// Group: `GetPName`
    public static final int GL_VERTEX_ARRAY_BINDING = 0x85B5;

    public static final int GL_VERTEX_ARRAY_BINDING_APPLE = 0x85B5;

    public static final int GL_VERTEX_ARRAY_BINDING_OES = 0x85B5;

    public static final int GL_VERTEX_ARRAY_BUFFER_BINDING = 0x8896;

    public static final int GL_VERTEX_ARRAY_BUFFER_BINDING_ARB = 0x8896;

    /// Group: `GetPName`
    public static final int GL_VERTEX_ARRAY_COUNT_EXT = 0x807D;

    public static final int GL_VERTEX_ARRAY_EXT = 0x8074;

    public static final int GL_VERTEX_ARRAY_KHR = 0x8074;

    public static final int GL_VERTEX_ARRAY_LENGTH_NV = 0x8F2B;

    public static final int GL_VERTEX_ARRAY_LIST_IBM = 103070;

    public static final int GL_VERTEX_ARRAY_LIST_STRIDE_IBM = 103080;

    public static final int GL_VERTEX_ARRAY_OBJECT_AMD = 0x9154;

    public static final int GL_VERTEX_ARRAY_OBJECT_EXT = 0x9154;

    public static final int GL_VERTEX_ARRAY_PARALLEL_POINTERS_INTEL = 0x83F5;

    /// Group: `GetPointervPName`
    public static final int GL_VERTEX_ARRAY_POINTER = 0x808E;

    /// Group: `GetPointervPName`
    public static final int GL_VERTEX_ARRAY_POINTER_EXT = 0x808E;

    public static final int GL_VERTEX_ARRAY_RANGE_APPLE = 0x851D;

    public static final int GL_VERTEX_ARRAY_RANGE_LENGTH_APPLE = 0x851E;

    public static final int GL_VERTEX_ARRAY_RANGE_LENGTH_NV = 0x851E;

    public static final int GL_VERTEX_ARRAY_RANGE_NV = 0x851D;

    public static final int GL_VERTEX_ARRAY_RANGE_POINTER_APPLE = 0x8521;

    public static final int GL_VERTEX_ARRAY_RANGE_POINTER_NV = 0x8521;

    public static final int GL_VERTEX_ARRAY_RANGE_VALID_NV = 0x851F;

    public static final int GL_VERTEX_ARRAY_RANGE_WITHOUT_FLUSH_NV = 0x8533;

    /// Group: `GetPName`
    public static final int GL_VERTEX_ARRAY_SIZE = 0x807A;

    public static final int GL_VERTEX_ARRAY_SIZE_EXT = 0x807A;

    /// Group: `HintTarget`
    public static final int GL_VERTEX_ARRAY_STORAGE_HINT_APPLE = 0x851F;

    /// Group: `GetPName`
    public static final int GL_VERTEX_ARRAY_STRIDE = 0x807C;

    public static final int GL_VERTEX_ARRAY_STRIDE_EXT = 0x807C;

    /// Group: `GetPName`
    public static final int GL_VERTEX_ARRAY_TYPE = 0x807B;

    public static final int GL_VERTEX_ARRAY_TYPE_EXT = 0x807B;

    public static final int GL_VERTEX_ATTRIB_ARRAY0_NV = 0x8650;

    public static final int GL_VERTEX_ATTRIB_ARRAY10_NV = 0x865A;

    public static final int GL_VERTEX_ATTRIB_ARRAY11_NV = 0x865B;

    public static final int GL_VERTEX_ATTRIB_ARRAY12_NV = 0x865C;

    public static final int GL_VERTEX_ATTRIB_ARRAY13_NV = 0x865D;

    public static final int GL_VERTEX_ATTRIB_ARRAY14_NV = 0x865E;

    public static final int GL_VERTEX_ATTRIB_ARRAY15_NV = 0x865F;

    public static final int GL_VERTEX_ATTRIB_ARRAY1_NV = 0x8651;

    public static final int GL_VERTEX_ATTRIB_ARRAY2_NV = 0x8652;

    public static final int GL_VERTEX_ATTRIB_ARRAY3_NV = 0x8653;

    public static final int GL_VERTEX_ATTRIB_ARRAY4_NV = 0x8654;

    public static final int GL_VERTEX_ATTRIB_ARRAY5_NV = 0x8655;

    public static final int GL_VERTEX_ATTRIB_ARRAY6_NV = 0x8656;

    public static final int GL_VERTEX_ATTRIB_ARRAY7_NV = 0x8657;

    public static final int GL_VERTEX_ATTRIB_ARRAY8_NV = 0x8658;

    public static final int GL_VERTEX_ATTRIB_ARRAY9_NV = 0x8659;

    public static final int GL_VERTEX_ATTRIB_ARRAY_ADDRESS_NV = 0x8F20;

    /// Group: `MemoryBarrierMask`
    public static final int GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT = 0x00000001;

    /// Group: `MemoryBarrierMask`
    public static final int GL_VERTEX_ATTRIB_ARRAY_BARRIER_BIT_EXT = 0x00000001;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`
    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F;

    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING_ARB = 0x889F;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`, `VertexArrayPName`
    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR = 0x88FE;

    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_ANGLE = 0x88FE;

    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_ARB = 0x88FE;

    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_EXT = 0x88FE;

    public static final int GL_VERTEX_ATTRIB_ARRAY_DIVISOR_NV = 0x88FE;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`, `VertexArrayPName`
    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED = 0x8622;

    public static final int GL_VERTEX_ATTRIB_ARRAY_ENABLED_ARB = 0x8622;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`, `VertexArrayPName`
    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER = 0x88FD;

    /// Group: `VertexAttribPropertyARB`
    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER_EXT = 0x88FD;

    public static final int GL_VERTEX_ATTRIB_ARRAY_INTEGER_NV = 0x88FD;

    public static final int GL_VERTEX_ATTRIB_ARRAY_LENGTH_NV = 0x8F2A;

    /// Groups: `VertexArrayPName`, `VertexAttribPropertyARB`
    public static final int GL_VERTEX_ATTRIB_ARRAY_LONG = 0x874E;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`, `VertexArrayPName`
    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A;

    public static final int GL_VERTEX_ATTRIB_ARRAY_NORMALIZED_ARB = 0x886A;

    /// Group: `VertexAttribPointerPropertyARB`
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;

    /// Group: `VertexAttribPointerPropertyARB`
    public static final int GL_VERTEX_ATTRIB_ARRAY_POINTER_ARB = 0x8645;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`, `VertexArrayPName`
    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE = 0x8623;

    public static final int GL_VERTEX_ATTRIB_ARRAY_SIZE_ARB = 0x8623;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`, `VertexArrayPName`
    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE = 0x8624;

    public static final int GL_VERTEX_ATTRIB_ARRAY_STRIDE_ARB = 0x8624;

    /// Groups: `VertexAttribEnum`, `VertexAttribPropertyARB`, `VertexArrayPName`
    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE = 0x8625;

    public static final int GL_VERTEX_ATTRIB_ARRAY_TYPE_ARB = 0x8625;

    public static final int GL_VERTEX_ATTRIB_ARRAY_UNIFIED_NV = 0x8F1E;

    /// Group: `VertexAttribPropertyARB`
    public static final int GL_VERTEX_ATTRIB_BINDING = 0x82D4;

    public static final int GL_VERTEX_ATTRIB_MAP1_APPLE = 0x8A00;

    public static final int GL_VERTEX_ATTRIB_MAP1_COEFF_APPLE = 0x8A03;

    public static final int GL_VERTEX_ATTRIB_MAP1_DOMAIN_APPLE = 0x8A05;

    public static final int GL_VERTEX_ATTRIB_MAP1_ORDER_APPLE = 0x8A04;

    public static final int GL_VERTEX_ATTRIB_MAP1_SIZE_APPLE = 0x8A02;

    public static final int GL_VERTEX_ATTRIB_MAP2_APPLE = 0x8A01;

    public static final int GL_VERTEX_ATTRIB_MAP2_COEFF_APPLE = 0x8A07;

    public static final int GL_VERTEX_ATTRIB_MAP2_DOMAIN_APPLE = 0x8A09;

    public static final int GL_VERTEX_ATTRIB_MAP2_ORDER_APPLE = 0x8A08;

    public static final int GL_VERTEX_ATTRIB_MAP2_SIZE_APPLE = 0x8A06;

    /// Groups: `VertexArrayPName`, `VertexAttribPropertyARB`
    public static final int GL_VERTEX_ATTRIB_RELATIVE_OFFSET = 0x82D5;

    public static final int GL_VERTEX_BINDING_BUFFER = 0x8F4F;

    /// Group: `GetPName`
    public static final int GL_VERTEX_BINDING_DIVISOR = 0x82D6;

    /// Group: `GetPName`
    public static final int GL_VERTEX_BINDING_OFFSET = 0x82D7;

    /// Group: `GetPName`
    public static final int GL_VERTEX_BINDING_STRIDE = 0x82D8;

    public static final int GL_VERTEX_BLEND_ARB = 0x86A7;

    /// Groups: `HintTarget`, `HintTargetPGI`
    public static final int GL_VERTEX_CONSISTENT_HINT_PGI = 0x1A22B;

    /// Groups: `HintTarget`, `HintTargetPGI`
    public static final int GL_VERTEX_DATA_HINT_PGI = 0x1A22A;

    public static final int GL_VERTEX_ELEMENT_SWIZZLE_AMD = 0x91A4;

    public static final int GL_VERTEX_ID_NV = 0x8C7B;

    public static final int GL_VERTEX_ID_SWIZZLE_AMD = 0x91A5;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_VERTEX_PRECLIP_HINT_SGIX = 0x83EF;

    /// Groups: `HintTarget`, `GetPName`
    public static final int GL_VERTEX_PRECLIP_SGIX = 0x83EE;

    /// Group: `ProgramTarget`
    public static final int GL_VERTEX_PROGRAM_ARB = 0x8620;

    public static final int GL_VERTEX_PROGRAM_BINDING_NV = 0x864A;

    public static final int GL_VERTEX_PROGRAM_CALLBACK_DATA_MESA = 0x8BB7;

    public static final int GL_VERTEX_PROGRAM_CALLBACK_FUNC_MESA = 0x8BB6;

    public static final int GL_VERTEX_PROGRAM_CALLBACK_MESA = 0x8BB5;

    public static final int GL_VERTEX_PROGRAM_NV = 0x8620;

    public static final int GL_VERTEX_PROGRAM_PARAMETER_BUFFER_NV = 0x8DA2;

    public static final int GL_VERTEX_PROGRAM_POINT_SIZE = 0x8642;

    public static final int GL_VERTEX_PROGRAM_POINT_SIZE_ARB = 0x8642;

    public static final int GL_VERTEX_PROGRAM_POINT_SIZE_NV = 0x8642;

    public static final int GL_VERTEX_PROGRAM_POSITION_MESA = 0x8BB4;

    public static final int GL_VERTEX_PROGRAM_TWO_SIDE = 0x8643;

    public static final int GL_VERTEX_PROGRAM_TWO_SIDE_ARB = 0x8643;

    public static final int GL_VERTEX_PROGRAM_TWO_SIDE_NV = 0x8643;

    /// Groups: `PipelineParameterName`, `ShaderType`
    public static final int GL_VERTEX_SHADER = 0x8B31;

    /// Group: `ShaderType`
    public static final int GL_VERTEX_SHADER_ARB = 0x8B31;

    public static final int GL_VERTEX_SHADER_BINDING_EXT = 0x8781;

    /// Group: `UseProgramStageMask`
    public static final int GL_VERTEX_SHADER_BIT = 0x00000001;

    /// Group: `UseProgramStageMask`
    public static final int GL_VERTEX_SHADER_BIT_EXT = 0x00000001;

    public static final int GL_VERTEX_SHADER_EXT = 0x8780;

    public static final int GL_VERTEX_SHADER_INSTRUCTIONS_EXT = 0x87CF;

    public static final int GL_VERTEX_SHADER_INVARIANTS_EXT = 0x87D1;

    /// Group: `QueryTarget`
    public static final int GL_VERTEX_SHADER_INVOCATIONS = 0x82F0;

    /// Alias: `GL_VERTEX_SHADER_INVOCATIONS`
    public static final int GL_VERTEX_SHADER_INVOCATIONS_ARB = 0x82F0;

    public static final int GL_VERTEX_SHADER_LOCALS_EXT = 0x87D3;

    public static final int GL_VERTEX_SHADER_LOCAL_CONSTANTS_EXT = 0x87D2;

    public static final int GL_VERTEX_SHADER_OPTIMIZED_EXT = 0x87D4;

    public static final int GL_VERTEX_SHADER_VARIANTS_EXT = 0x87D0;

    public static final int GL_VERTEX_SOURCE_ATI = 0x8774;

    public static final int GL_VERTEX_STATE_PROGRAM_NV = 0x8621;

    /// Group: `VertexStreamATI`
    public static final int GL_VERTEX_STREAM0_ATI = 0x876C;

    /// Group: `VertexStreamATI`
    public static final int GL_VERTEX_STREAM1_ATI = 0x876D;

    /// Group: `VertexStreamATI`
    public static final int GL_VERTEX_STREAM2_ATI = 0x876E;

    /// Group: `VertexStreamATI`
    public static final int GL_VERTEX_STREAM3_ATI = 0x876F;

    /// Group: `VertexStreamATI`
    public static final int GL_VERTEX_STREAM4_ATI = 0x8770;

    /// Group: `VertexStreamATI`
    public static final int GL_VERTEX_STREAM5_ATI = 0x8771;

    /// Group: `VertexStreamATI`
    public static final int GL_VERTEX_STREAM6_ATI = 0x8772;

    /// Group: `VertexStreamATI`
    public static final int GL_VERTEX_STREAM7_ATI = 0x8773;

    /// Group: `ProgramInterface`
    public static final int GL_VERTEX_SUBROUTINE = 0x92E8;

    /// Group: `ProgramInterface`
    public static final int GL_VERTEX_SUBROUTINE_UNIFORM = 0x92EE;

    /// Group: `InternalFormatPName`
    public static final int GL_VERTEX_TEXTURE = 0x829B;

    public static final int GL_VERTEX_WEIGHTING_EXT = 0x8509;

    public static final int GL_VERTEX_WEIGHT_ARRAY_EXT = 0x850C;

    public static final int GL_VERTEX_WEIGHT_ARRAY_POINTER_EXT = 0x8510;

    public static final int GL_VERTEX_WEIGHT_ARRAY_SIZE_EXT = 0x850D;

    public static final int GL_VERTEX_WEIGHT_ARRAY_STRIDE_EXT = 0x850F;

    public static final int GL_VERTEX_WEIGHT_ARRAY_TYPE_EXT = 0x850E;

    /// Group: `PathCoordType`
    public static final int GL_VERTICAL_LINE_TO_NV = 0x08;

    /// Group: `QueryTarget`
    public static final int GL_VERTICES_SUBMITTED = 0x82EE;

    /// Alias: `GL_VERTICES_SUBMITTED`
    public static final int GL_VERTICES_SUBMITTED_ARB = 0x82EE;

    public static final int GL_VIBRANCE_BIAS_NV = 0x8719;

    public static final int GL_VIBRANCE_SCALE_NV = 0x8713;

    public static final int GL_VIDEO_BUFFER_BINDING_NV = 0x9021;

    public static final int GL_VIDEO_BUFFER_INTERNAL_FORMAT_NV = 0x902D;

    public static final int GL_VIDEO_BUFFER_NV = 0x9020;

    public static final int GL_VIDEO_BUFFER_PITCH_NV = 0x9028;

    public static final int GL_VIDEO_CAPTURE_FIELD_LOWER_HEIGHT_NV = 0x903B;

    public static final int GL_VIDEO_CAPTURE_FIELD_UPPER_HEIGHT_NV = 0x903A;

    public static final int GL_VIDEO_CAPTURE_FRAME_HEIGHT_NV = 0x9039;

    public static final int GL_VIDEO_CAPTURE_FRAME_WIDTH_NV = 0x9038;

    public static final int GL_VIDEO_CAPTURE_SURFACE_ORIGIN_NV = 0x903C;

    public static final int GL_VIDEO_CAPTURE_TO_422_SUPPORTED_NV = 0x9026;

    public static final int GL_VIDEO_COLOR_CONVERSION_MATRIX_NV = 0x9029;

    public static final int GL_VIDEO_COLOR_CONVERSION_MAX_NV = 0x902A;

    public static final int GL_VIDEO_COLOR_CONVERSION_MIN_NV = 0x902B;

    public static final int GL_VIDEO_COLOR_CONVERSION_OFFSET_NV = 0x902C;

    /// Group: `GetPName`
    public static final int GL_VIEWPORT = 0x0BA2;

    /// Group: `AttribMask`
    public static final int GL_VIEWPORT_BIT = 0x00000800;

    /// Group: `GetPName`
    public static final int GL_VIEWPORT_BOUNDS_RANGE = 0x825D;

    public static final int GL_VIEWPORT_BOUNDS_RANGE_EXT = 0x825D;

    public static final int GL_VIEWPORT_BOUNDS_RANGE_NV = 0x825D;

    public static final int GL_VIEWPORT_BOUNDS_RANGE_OES = 0x825D;

    /// Group: `CommandOpcodesNV`
    public static final int GL_VIEWPORT_COMMAND_NV = 0x0010;

    /// Group: `GetPName`
    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX = 0x825F;

    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX_EXT = 0x825F;

    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX_NV = 0x825F;

    public static final int GL_VIEWPORT_INDEX_PROVOKING_VERTEX_OES = 0x825F;

    public static final int GL_VIEWPORT_POSITION_W_SCALE_NV = 0x937C;

    public static final int GL_VIEWPORT_POSITION_W_SCALE_X_COEFF_NV = 0x937D;

    public static final int GL_VIEWPORT_POSITION_W_SCALE_Y_COEFF_NV = 0x937E;

    /// Group: `GetPName`
    public static final int GL_VIEWPORT_SUBPIXEL_BITS = 0x825C;

    public static final int GL_VIEWPORT_SUBPIXEL_BITS_EXT = 0x825C;

    public static final int GL_VIEWPORT_SUBPIXEL_BITS_NV = 0x825C;

    public static final int GL_VIEWPORT_SUBPIXEL_BITS_OES = 0x825C;

    public static final int GL_VIEWPORT_SWIZZLE_NEGATIVE_W_NV = 0x9357;

    public static final int GL_VIEWPORT_SWIZZLE_NEGATIVE_X_NV = 0x9351;

    public static final int GL_VIEWPORT_SWIZZLE_NEGATIVE_Y_NV = 0x9353;

    public static final int GL_VIEWPORT_SWIZZLE_NEGATIVE_Z_NV = 0x9355;

    public static final int GL_VIEWPORT_SWIZZLE_POSITIVE_W_NV = 0x9356;

    public static final int GL_VIEWPORT_SWIZZLE_POSITIVE_X_NV = 0x9350;

    public static final int GL_VIEWPORT_SWIZZLE_POSITIVE_Y_NV = 0x9352;

    public static final int GL_VIEWPORT_SWIZZLE_POSITIVE_Z_NV = 0x9354;

    public static final int GL_VIEWPORT_SWIZZLE_W_NV = 0x935B;

    public static final int GL_VIEWPORT_SWIZZLE_X_NV = 0x9358;

    public static final int GL_VIEWPORT_SWIZZLE_Y_NV = 0x9359;

    public static final int GL_VIEWPORT_SWIZZLE_Z_NV = 0x935A;

    public static final int GL_VIEW_CLASS_128_BITS = 0x82C4;

    public static final int GL_VIEW_CLASS_16_BITS = 0x82CA;

    public static final int GL_VIEW_CLASS_24_BITS = 0x82C9;

    public static final int GL_VIEW_CLASS_32_BITS = 0x82C8;

    public static final int GL_VIEW_CLASS_48_BITS = 0x82C7;

    public static final int GL_VIEW_CLASS_64_BITS = 0x82C6;

    public static final int GL_VIEW_CLASS_8_BITS = 0x82CB;

    public static final int GL_VIEW_CLASS_96_BITS = 0x82C5;


    public static final int GL_VIEW_CLASS_ASTC_10x10_RGBA = 0x9393;


    public static final int GL_VIEW_CLASS_ASTC_10x5_RGBA = 0x9390;


    public static final int GL_VIEW_CLASS_ASTC_10x6_RGBA = 0x9391;


    public static final int GL_VIEW_CLASS_ASTC_10x8_RGBA = 0x9392;


    public static final int GL_VIEW_CLASS_ASTC_12x10_RGBA = 0x9394;


    public static final int GL_VIEW_CLASS_ASTC_12x12_RGBA = 0x9395;


    public static final int GL_VIEW_CLASS_ASTC_4x4_RGBA = 0x9388;


    public static final int GL_VIEW_CLASS_ASTC_5x4_RGBA = 0x9389;


    public static final int GL_VIEW_CLASS_ASTC_5x5_RGBA = 0x938A;


    public static final int GL_VIEW_CLASS_ASTC_6x5_RGBA = 0x938B;


    public static final int GL_VIEW_CLASS_ASTC_6x6_RGBA = 0x938C;


    public static final int GL_VIEW_CLASS_ASTC_8x5_RGBA = 0x938D;


    public static final int GL_VIEW_CLASS_ASTC_8x6_RGBA = 0x938E;


    public static final int GL_VIEW_CLASS_ASTC_8x8_RGBA = 0x938F;

    public static final int GL_VIEW_CLASS_BPTC_FLOAT = 0x82D3;

    public static final int GL_VIEW_CLASS_BPTC_UNORM = 0x82D2;

    public static final int GL_VIEW_CLASS_EAC_R11 = 0x9383;

    public static final int GL_VIEW_CLASS_EAC_RG11 = 0x9384;

    public static final int GL_VIEW_CLASS_ETC2_EAC_RGBA = 0x9387;

    public static final int GL_VIEW_CLASS_ETC2_RGB = 0x9385;

    public static final int GL_VIEW_CLASS_ETC2_RGBA = 0x9386;

    public static final int GL_VIEW_CLASS_RGTC1_RED = 0x82D0;

    public static final int GL_VIEW_CLASS_RGTC2_RG = 0x82D1;

    public static final int GL_VIEW_CLASS_S3TC_DXT1_RGB = 0x82CC;

    public static final int GL_VIEW_CLASS_S3TC_DXT1_RGBA = 0x82CD;

    public static final int GL_VIEW_CLASS_S3TC_DXT3_RGBA = 0x82CE;

    public static final int GL_VIEW_CLASS_S3TC_DXT5_RGBA = 0x82CF;

    /// Group: `InternalFormatPName`
    public static final int GL_VIEW_COMPATIBILITY_CLASS = 0x82B6;

    public static final int GL_VIRTUAL_PAGE_SIZE_INDEX_ARB = 0x91A7;

    public static final int GL_VIRTUAL_PAGE_SIZE_INDEX_EXT = 0x91A7;

    public static final int GL_VIRTUAL_PAGE_SIZE_X_AMD = 0x9195;

    public static final int GL_VIRTUAL_PAGE_SIZE_X_ARB = 0x9195;

    public static final int GL_VIRTUAL_PAGE_SIZE_X_EXT = 0x9195;

    public static final int GL_VIRTUAL_PAGE_SIZE_Y_AMD = 0x9196;

    public static final int GL_VIRTUAL_PAGE_SIZE_Y_ARB = 0x9196;

    public static final int GL_VIRTUAL_PAGE_SIZE_Y_EXT = 0x9196;

    public static final int GL_VIRTUAL_PAGE_SIZE_Z_AMD = 0x9197;

    public static final int GL_VIRTUAL_PAGE_SIZE_Z_ARB = 0x9197;

    public static final int GL_VIRTUAL_PAGE_SIZE_Z_EXT = 0x9197;

    public static final int GL_VIVIDLIGHT_NV = 0x92A6;

    public static final int GL_VOLATILE_APPLE = 0x8A1A;

    /// Group: `SyncStatus`
    public static final int GL_WAIT_FAILED = 0x911D;

    public static final int GL_WAIT_FAILED_APPLE = 0x911D;

    public static final int GL_WARPS_PER_SM_NV = 0x933A;

    public static final int GL_WARP_SIZE_NV = 0x9339;

    public static final int GL_WEIGHTED_AVERAGE_ARB = 0x9367;

    /// Alias: `GL_WEIGHTED_AVERAGE_ARB`
    public static final int GL_WEIGHTED_AVERAGE_EXT = 0x9367;

    public static final int GL_WEIGHT_ARRAY_ARB = 0x86AD;

    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING = 0x889E;

    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING_ARB = 0x889E;

    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING_OES = 0x889E;

    public static final int GL_WEIGHT_ARRAY_OES = 0x86AD;

    public static final int GL_WEIGHT_ARRAY_POINTER_ARB = 0x86AC;

    public static final int GL_WEIGHT_ARRAY_POINTER_OES = 0x86AC;

    public static final int GL_WEIGHT_ARRAY_SIZE_ARB = 0x86AB;

    public static final int GL_WEIGHT_ARRAY_SIZE_OES = 0x86AB;

    public static final int GL_WEIGHT_ARRAY_STRIDE_ARB = 0x86AA;

    public static final int GL_WEIGHT_ARRAY_STRIDE_OES = 0x86AA;

    public static final int GL_WEIGHT_ARRAY_TYPE_ARB = 0x86A9;

    public static final int GL_WEIGHT_ARRAY_TYPE_OES = 0x86A9;

    public static final int GL_WEIGHT_SUM_UNITY_ARB = 0x86A6;

    /// Group: `HintTarget`
    public static final int GL_WIDE_LINE_HINT_PGI = 0x1A222;

    public static final int GL_WINDOW_RECTANGLE_EXT = 0x8F12;

    public static final int GL_WINDOW_RECTANGLE_MODE_EXT = 0x8F13;

    public static final int GL_WRAP_BORDER_SUN = 0x81D4;

    public static final int GL_WRITEONLY_RENDERING_QCOM = 0x8823;

    public static final int GL_WRITE_DISCARD_NV = 0x88BE;

    /// Group: `BufferAccessARB`
    public static final int GL_WRITE_ONLY = 0x88B9;

    public static final int GL_WRITE_ONLY_ARB = 0x88B9;

    public static final int GL_WRITE_ONLY_OES = 0x88B9;

    public static final int GL_WRITE_PIXEL_DATA_RANGE_LENGTH_NV = 0x887A;

    /// Group: `PixelDataRangeTargetNV`
    public static final int GL_WRITE_PIXEL_DATA_RANGE_NV = 0x8878;

    public static final int GL_WRITE_PIXEL_DATA_RANGE_POINTER_NV = 0x887C;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_W_EXT = 0x87D8;

    /// Group: `LogicOp`
    public static final int GL_XOR = 0x1506;

    public static final int GL_XOR_NV = 0x1506;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_X_EXT = 0x87D5;

    public static final int GL_YCBAYCR8A_4224_NV = 0x9032;

    public static final int GL_YCBCR_422_APPLE = 0x85B9;

    public static final int GL_YCBCR_MESA = 0x8757;

    public static final int GL_YCBYCR8_422_NV = 0x9031;

    public static final int GL_YCRCBA_SGIX = 0x8319;

    /// Group: `PixelFormat`
    public static final int GL_YCRCB_422_SGIX = 0x81BB;

    /// Group: `PixelFormat`
    public static final int GL_YCRCB_444_SGIX = 0x81BC;

    public static final int GL_YCRCB_SGIX = 0x8318;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_Y_EXT = 0x87D6;

    public static final int GL_Z400_BINARY_AMD = 0x8740;

    public static final int GL_Z4Y12Z4CB12Z4A12Z4Y12Z4CR12Z4A12_4224_NV = 0x9036;

    public static final int GL_Z4Y12Z4CB12Z4CR12_444_NV = 0x9037;

    public static final int GL_Z4Y12Z4CB12Z4Y12Z4CR12_422_NV = 0x9035;

    public static final int GL_Z6Y10Z6CB10Z6A10Z6Y10Z6CR10Z6A10_4224_NV = 0x9034;

    public static final int GL_Z6Y10Z6CB10Z6Y10Z6CR10_422_NV = 0x9033;

    /// Groups: `SpecialNumbers`, `TextureSwizzle`, `StencilOp`, `BlendingFactor`,
    ///   `FragmentShaderGenericSourceATI`
    public static final int GL_ZERO = 0;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_ZERO_EXT = 0x87DD;

    /// Group: `ClipControlDepth`
    public static final int GL_ZERO_TO_ONE = 0x935F;

    /// Alias: `GL_ZERO_TO_ONE`
    public static final int GL_ZERO_TO_ONE_EXT = 0x935F;

    /// Group: `GetPName`
    public static final int GL_ZOOM_X = 0x0D16;

    /// Group: `GetPName`
    public static final int GL_ZOOM_Y = 0x0D17;

    /// Group: `VertexShaderCoordOutEXT`
    public static final int GL_Z_EXT = 0x87D7;
}
