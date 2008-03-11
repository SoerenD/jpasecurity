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
package net.sf.jpasecurity.jpql;

import junit.framework.TestCase;
import net.sf.jpasecurity.jpql.parser.JpqlParser;
import net.sf.jpasecurity.jpql.parser.JpqlStatement;
import net.sf.jpasecurity.jpql.parser.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ToStringVisitorTest extends TestCase {

	private static final Log LOG = LogFactory.getLog(ToStringVisitor.class);
	
	private JpqlParser parser;
	private ToStringVisitor toStringVisitor;
	
	public void testToStringVisitor() throws ParseException {
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.id = :id");
		assertJpql("SELECT bean FROM TestBean bean LEFT OUTER JOIN bean.name name WHERE bean.id = :id");
		assertJpql("SELECT bean FROM TestBean bean LEFT OUTER JOIN FETCH bean.name WHERE bean.id = :id");
		assertJpql("SELECT bean FROM TestBean bean INNER JOIN bean.name name WHERE bean.id = :id");
		assertJpql("SELECT bean FROM TestBean bean INNER JOIN FETCH bean.name WHERE bean.id = :id");
		assertJpql("SELECT bean FROM TestBean bean WHERE (bean.id NOT BETWEEN 5 AND 7)");
		assertJpql("SELECT DISTINCT bean, bean.id FROM TestBean bean WHERE :id = bean.id");
		assertJpql("SELECT bean1 FROM TestBean bean1, TestBean bean2 WHERE bean1.id < bean2.id");
		assertJpql("SELECT bean.name FROM TestBean bean WHERE (bean.name <> 'testBean')");
		assertJpql("SELECT bean1.name FROM TestBean bean1, TestBean bean2 WHERE (bean1.id - (bean2.id - 1)) = 5");
		assertJpql("SELECT bean.name FROM TestBean bean WHERE bean.booleanValue = true");
		assertJpql("SELECT bean.name FROM TestBean bean WHERE ((3 + 2) * 2) = 10.0");
		assertJpql("SELECT bean.name FROM TestBean bean WHERE ((3 + 2) / 2) = 10.0");
		assertJpql("SELECT bean.name FROM TestBean bean WHERE (-(3 - 2) * 2) <= 10.0");
		assertJpql("SELECT bean.name FROM TestBean bean WHERE ((3 + 2) * 2) >= 10.0");
		assertJpql("SELECT bean.id FROM TestBean bean WHERE ABS(bean.id) = 1 HAVING bean.id > 0");
		assertJpql("SELECT bean FROM TestBean bean WHERE TRIM(LEADING ' ' FROM bean.name) = TRIM(TRAILING FROM bean.name)");
		assertJpql("SELECT bean FROM TestBean bean WHERE TRIM(bean.name) = TRIM(BOTH FROM bean.name)");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.name = ALL( SELECT bean.collectionProperty.name FROM TestBean bean)");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.name = ANY( SELECT bean.collectionProperty.name FROM TestBean bean)");
		assertJpql("SELECT NEW net.sf.jpasecurity.TestBean(bean.id, bean.name) FROM TestBean bean");
		assertJpql("SELECT bean FROM TestBean bean WHERE SIZE(bean.collectionProperty) = 0");
		assertJpql("SELECT DISTINCT bean FROM TestBean bean, TestBean bean2 INNER JOIN FETCH bean.collectionProperty");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.dateProperty = CURRENT_DATE");
		assertJpql("SELECT DISTINCT bean FROM TestBean bean LEFT OUTER JOIN FETCH bean.beanProperty");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.id > 0");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.name LIKE '%beanName%'");
		assertJpql("SELECT bean FROM TestBean bean WHERE (bean.collectionProperty IS NULL OR SIZE(bean.collectionProperty) = 0)");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.name IS NOT NULL");
		assertJpql("SELECT bean FROM TestBean bean WHERE NOT (bean.id = SQRT(2) )");
		assertJpql("SELECT bean FROM TestBean bean WHERE LOWER(bean.name) = 'test'");
		assertJpql("SELECT bean FROM TestBean bean ORDER BY bean.id ASC, bean.name DESC");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.id = MIN( DISTINCT bean.id)");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.id = MAX( DISTINCT bean.id)");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.id = MAX( DISTINCT bean.id)");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.collectionProperty IS EMPTY");
		assertJpql("SELECT bean FROM TestBean bean WHERE (bean.id = 0 AND bean.name = 'Test')");
		assertJpql("SELECT bean FROM TestBean bean WHERE - MOD(bean.id, 2) = -1");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean NOT MEMBER OF bean.collectionProperty");
		assertJpql("SELECT bean FROM TestBean bean WHERE SUBSTRING(bean.name, 2, 3) = 'est'");
		assertJpql("SELECT bean FROM TestBean bean WHERE LOCATE(bean.name, 'est') = 2");
		assertJpql("SELECT bean FROM TestBean bean WHERE LOCATE(bean.name, 'est', 2) = -1");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.name = ?1");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.created < CURRENT_TIMESTAMP");
		assertJpql("SELECT bean FROM TestBean bean WHERE bean.created < CURRENT_TIME");
        assertJpql("SELECT bean FROM TestBean bean WHERE bean.name IN ('name 1', 'name 2')");
		assertJpql("UPDATE TestBean bean SET bean.name = 'test', bean.id = 0");
		assertJpql("UPDATE TestBean bean SET bean.name = 'test', bean.id = 0 WHERE bean.id = 0");
		assertJpql("DELETE FROM TestBean bean");
	}
	
	public void assertJpql(String query) throws ParseException {
		toStringVisitor.reset();
		JpqlStatement statement = parser.parseQuery(query);
		statement.visit(toStringVisitor);
		query = stripWhiteSpaces(query);
		String result = stripWhiteSpaces(toStringVisitor.toString());
		LOG.debug(query);
		LOG.debug(result);
		assertEquals("JPQL", query, result);
	}
	
	protected String stripWhiteSpaces(String query) {
		return query.replaceAll("\\s+", " ").trim();
	}
	
	public void setUp() {
		parser = new JpqlParser();
		toStringVisitor = new ToStringVisitor();
	}
}