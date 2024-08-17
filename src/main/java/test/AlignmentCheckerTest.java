package test;

import com.zeljko.graphics.model.Blueprint;
import com.zeljko.graphics.model.Model3D;
import com.zeljko.utils.AlignmentChecker;
import com.zeljko.utils.ShapeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlignmentCheckerTest {
    private Blueprint blueprint;
    private List<Model3D> userModels;

    @BeforeEach
    void setUp() {
        blueprint = new Blueprint(2, 1);
        userModels = new ArrayList<>();

        Model3D CUBOID_1 = new Model3D(1.0, 1.0, 1.0, ShapeType.CUBOID);
        CUBOID_1.translate(0, 0, 0);
        blueprint.addModel(CUBOID_1);

        Model3D CUBOID_2 = new Model3D(2.0, 2.0, 2.0, ShapeType.CUBOID);
        CUBOID_2.translate(2, 0, 0);
        blueprint.addModel(CUBOID_2);

        Model3D CYLINDER_1 = new Model3D(1.0, 3.0, 1.0, ShapeType.CYLINDER);
        CYLINDER_1.translate(0, 2, 0);
        blueprint.addModel(CYLINDER_1);
    }

    @Test
    void testAllModelsAligned() {
        Model3D USER_CUBOID_1 = new Model3D(1.0, 1.0, 1.0, ShapeType.CUBOID);
        USER_CUBOID_1.translate(0, 0, 0);
        userModels.add(USER_CUBOID_1);

        Model3D USER_CUBOID_2 = new Model3D(2.0, 2.0, 2.0, ShapeType.CUBOID);
        USER_CUBOID_2.translate(2, 0, 0);
        userModels.add(USER_CUBOID_2);

        Model3D USER_CYLINDER_1 = new Model3D(1.0, 3.0, 1.0, ShapeType.CYLINDER);
        USER_CYLINDER_1.translate(0, 2, 0);
        userModels.add(USER_CYLINDER_1);

        assertTrue(AlignmentChecker.areAllModelsAligned(blueprint, userModels));
    }

    @Test
    void testMisalignedModel() {
        Model3D USER_CUBOID_1 = new Model3D(1.0, 1.0, 1.0, ShapeType.CUBOID);
        USER_CUBOID_1.translate(0, 0, 0);
        userModels.add(USER_CUBOID_1);

        Model3D USER_CUBOID_2 = new Model3D(2.0, 2.0, 2.0, ShapeType.CUBOID);
        USER_CUBOID_2.translate(2.2, 0, 0);
        userModels.add(USER_CUBOID_2);

        Model3D USER_CYLINDER_1 = new Model3D(1.0, 3.0, 1.0, ShapeType.CYLINDER);
        USER_CYLINDER_1.translate(0, 2, 0);
        userModels.add(USER_CYLINDER_1);

        assertFalse(AlignmentChecker.areAllModelsAligned(blueprint, userModels));
    }

    @Test
    void testWrongShapeType() {
        Model3D USER_CUBOID_1 = new Model3D(1.0, 1.0, 1.0, ShapeType.CUBOID);
        USER_CUBOID_1.translate(0, 0, 0);
        userModels.add(USER_CUBOID_1);

        Model3D USER_CUBOID_2 = new Model3D(2.0, 2.0, 2.0, ShapeType.CUBOID);
        USER_CUBOID_2.translate(2, 0, 0);
        userModels.add(USER_CUBOID_2);

        Model3D USER_WRONG_SHAPE = new Model3D(1.0, 3.0, 1.0, ShapeType.CUBOID);
        USER_WRONG_SHAPE.translate(0, 2, 0);
        userModels.add(USER_WRONG_SHAPE);

        assertFalse(AlignmentChecker.areAllModelsAligned(blueprint, userModels));
    }

}