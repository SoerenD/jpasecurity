/*
 * Copyright 2011 Arne Limburg
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
package net.sf.jpasecurity.mapping;

import static net.sf.jpasecurity.util.Validate.notNull;

/**
 * A class representing a JPQL path.
 * @author Arne Limburg
 */
public class Path {

    private static final String[] EMPTY = new String[0];

    private Alias rootAlias;
    private String subpath;
    private String[] subpathComponents;

    public Path(String path) {
        int index = path.indexOf('.');
        if (index == -1) {
            rootAlias = new Alias(path.trim());
            subpath = null;
        } else {
            rootAlias = new Alias(path.substring(0, index));
            subpath = path.substring(index + 1).trim();
        }
    }

    Path(Alias alias) {
        notNull(Alias.class, alias);
        rootAlias = alias;
    }

    public boolean hasSubpath() {
        return subpath != null;
    }

    public Alias getRootAlias() {
        return rootAlias;
    }

    public String getSubpath() {
        return subpath;
    }

    public String[] getSubpathComponents() {
        if (subpathComponents == null) {
            subpathComponents = hasSubpath()? subpath.split("\\."): EMPTY;
        }
        return subpathComponents;
    }

    public Path append(String name) {
        return new Path(toString() + '.' + name);
    }

    public String toString() {
        return hasSubpath()? rootAlias.getName() + '.' + getSubpath(): rootAlias.getName();
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof Path)) {
            return false;
        }
        return toString().equals(object.toString());
    }
}
