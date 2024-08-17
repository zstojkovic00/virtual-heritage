//package test;
//
//import com.zeljko.utils.BlueprintFactory;
//import com.zeljko.graphics.model.Blueprint;
//import com.zeljko.graphics.model.Model3D;
//import com.zeljko.utils.ShapeType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class BlueprintTest {
//    private Blueprint blueprint;
//
//    @BeforeEach
//    void setUp() {
//        blueprint = BlueprintFactory.createTreeBlueprint();
//    }
//
//    @Test
//    void testAllModelsAligned() {
//        List<Model3D> userModels = new ArrayList<>();
//
//        Model3D model1 = new Model3D(5.0, 1.5, 2.0, ShapeType.CUBOID);
//        model1.translate(0, 0, 0);
//        userModels.add(model1);
//
//        Model3D model2 = new Model3D(5.0, 1.5, 2.0, ShapeType.CUBOID);
//        model2.translate(0, 2, 0);
//        userModels.add(model2);
//
//        assertTrue(blueprint.areAllModelsAligned(userModels));
//    }
//
//}