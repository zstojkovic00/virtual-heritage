package com.zeljko.lego.graphics;

import com.zeljko.lego.graphics.math.Vector3f;

import java.io.*;

public class OBJLoader {
    public static Model loadModel(File f) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(f));
        Model model = new Model();
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ")) {
                float x = Float.parseFloat(line.split(" ")[1]);
                float y = Float.parseFloat(line.split(" ")[2]);
                float z = Float.parseFloat(line.split(" ")[3]);
                model.vertices.add(new Vector3f(x, y, z));
            } else if (line.startsWith("vn ")) {
                float x = Float.parseFloat(line.split(" ")[1]);
                float y = Float.parseFloat(line.split(" ")[2]);
                float z = Float.parseFloat(line.split(" ")[3]);
                model.normals.add(new Vector3f(x, y, z));
            } else if (line.startsWith("f ")) {

                Vector3f vertexIndices = new Vector3f(Float.parseFloat(line.split(" ")[1].split("/")[0])
                        , Float.parseFloat(line.split(" ")[2].split("/")[0]),
                        Float.parseFloat(line.split(" ")[3].split("/")[0])

                );

                Vector3f normalIndices = new Vector3f(Float.parseFloat(line.split(" ")[1].split("/")[2])
                        , Float.parseFloat(line.split(" ")[2].split("/")[2]),
                        Float.parseFloat(line.split(" ")[3].split("/")[2])
                );

                model.faces.add(new Face(vertexIndices, normalIndices));
            }
        }
        reader.close();
        return model;
    }

}
