/*
 * Copyright 2008 Arne Limburg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */

package net.sf.jpasecurity.xml;

import java.util.AbstractList;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A list containing {@link Node}s.
 * @author Arne Limburg
 */
public class XmlNodeList extends AbstractList<Node> {

    private NodeList nodes;

    public XmlNodeList(NodeList nodeList) {
        nodes = nodeList;
    }

    public boolean containsAttribute(String attributeName, String attributeValue) {
        for (Node node: this) {
            NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                Node namedItem = attributes.getNamedItem(attributeName);
                if (namedItem != null && namedItem.getTextContent().equals(attributeValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Node get(int index) {
        return nodes.item(index);
    }

    public int size() {
        return nodes.getLength();
    }
}
