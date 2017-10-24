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

import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class RandomProviderTest {

    @Test
    public void testNextValue() throws Exception {
        RandomProvider randomProvider = new RandomProvider();
        randomProvider.init(Collections.<String, String>emptyMap());
        Assert.assertNotNull(randomProvider.nextValue(new Context(null, null, null), new Field(null, TypeConst.BOOLEAN, null)));
        Assert.assertNotNull(randomProvider.nextValue(new Context(null, null, null), new Field(null, TypeConst.STRING, null)));
        Assert.assertNotNull(randomProvider.nextValue(new Context(null, null, null), new Field(null, TypeConst.INT, null)));
        Assert.assertNotNull(randomProvider.nextValue(new Context(null, null, null), new Field(null, TypeConst.LONG, null)));
        Assert.assertNotNull(randomProvider.nextValue(new Context(null, null, null), new Field(null, TypeConst.DOUBLE, null)));
        Assert.assertNotNull(randomProvider.nextValue(new Context(null, null, null), new Field(null, TypeConst.FLOAT, null)));
    }
}