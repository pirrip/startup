package com.repetentia.component.test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMain {

    public static void main(String[] args) throws IOException {
        File file = new File("d:/raw.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(file);

        Iterator<Entry<String, JsonNode>> itr = jsonNode.fields();

        while (itr.hasNext()) {
            Entry<String, JsonNode> e = itr.next();
            String key = e.getKey();
            JsonNode node = e.getValue();
            System.out.println(key + " - " + node);
            System.out.println(node.getNodeType());
        }
//		JsonNode jsonNode1 = jsonNode.get("geometry");
//		System.out.println(jsonNode1.asText()); 

    }
}
