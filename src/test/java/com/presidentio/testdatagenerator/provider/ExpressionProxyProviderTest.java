/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ExpressionProxyProviderTest {

    @Test
    public void testNextValueSimpleMath() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "2 + 10";
        props.put(PropConst.EXPR, propExpr);
        ValueProvider provider = new ExpressionProxyProvider();
        provider.init(props);
        Context context = new Context(null, new HashMap<String, Object>(), null);
        Object result = provider.nextValue(context, new Field(null, TypeConst.INT, null));
        Assert.assertEquals(12, result);
    }

    @Test
    public void testNextValueMathVariable() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "++a + 10*b";
        props.put(PropConst.EXPR, propExpr);
        ValueProvider provider = new ExpressionProxyProvider();
        provider.init(props);
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", 5);
        variables.put("b", 2);
        Context context = new Context(null, variables, null);
        Object result = provider.nextValue(context, new Field(null, TypeConst.INT, null));
        Assert.assertEquals(26, result);
    }

    @Test
    public void testNextValueCounter() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "a++";
        props.put(PropConst.EXPR, propExpr);
        ValueProvider provider = new ExpressionProxyProvider();
        provider.init(props);
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", 5);
        variables.put("b", 2);
        Context context = new Context(null, variables, null);
        Object result = provider.nextValue(context, new Field(null, TypeConst.INT, null));
        Assert.assertEquals(5, result);
        Assert.assertEquals(6L, variables.get("a"));
    }

    @Test
    public void testNextValueParent() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "parent.parent.id";
        props.put(PropConst.EXPR, propExpr);
        ValueProvider provider = new ExpressionProxyProvider();
        provider.init(props);
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", 5);
        variables.put("b", 2);
        Context context = new Context(new Context(new Context(null, variables, null), Collections.<String, Object>singletonMap("id", "abc")), Collections.<String, Object>emptyMap());
        Object result = provider.nextValue(context, new Field(null, TypeConst.STRING, null));
        Assert.assertEquals("abc", result);
    }

    @Test
    public void testNextValueStringVariable() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "a + b";
        props.put(PropConst.EXPR, propExpr);
        ValueProvider provider = new ExpressionProxyProvider();
        provider.init(props);
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", "abc");
        variables.put("b", "zxc");
        Context context = new Context(null, variables, null);
        Object result = provider.nextValue(context, new Field(null, TypeConst.STRING, null));
        Assert.assertEquals("abczxc", result);
    }

    @Test
    public void testNextValueType() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "a + b";
        props.put(PropConst.EXPR, propExpr);
        ValueProvider provider = new ExpressionProxyProvider();
        provider.init(props);
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", "1");
        variables.put("b", "1");
        Context context = new Context(null, variables, null);
        Object result = provider.nextValue(context, new Field(null, TypeConst.STRING, null));
        Assert.assertEquals("11", result);
        variables.put("a", 1);
        variables.put("b", 1);
        context = new Context(null, variables, null);
        result = provider.nextValue(context, new Field(null, TypeConst.INT, null));
        Assert.assertEquals(2, result);
        result = provider.nextValue(context, new Field(null, TypeConst.LONG, null));
        Assert.assertEquals(2L, result);
        result = provider.nextValue(context, new Field(null, TypeConst.BOOLEAN, null));
        Assert.assertEquals(true, result);
        result = provider.nextValue(context, new Field(null, TypeConst.FLOAT, null));
        Assert.assertEquals(2F, result);
        result = provider.nextValue(context, new Field(null, TypeConst.DOUBLE, null));
        Assert.assertEquals(2D, result);
    }
}