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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomProvider implements ValueProvider {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private long min = -10;
    private long max = 10;
    private Random random = new Random();

    @Override
    public void init(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        if (propsCopy.containsKey(PropConst.MIN)) {
            min = Long.valueOf(propsCopy.remove(PropConst.MIN));
        }
        if (propsCopy.containsKey(PropConst.MAX)) {
            max = Long.valueOf(propsCopy.remove(PropConst.MAX));
        }
        if (!propsCopy.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for RandomProvider: " + propsCopy);
        }

    }

    @Override
    public Object nextValue(Context context, Field field) {
        String type = field.getType();
        switch (type) {
            case TypeConst.STRING:
                int minLen = (min >= 0) ? (int)min : 0;
                int len = random.nextInt((int)max - minLen + 1) + minLen;
                StringBuilder result = new StringBuilder(len);
                for (int i = 0; i < len; i++) {
                    result.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
                }
                return result;
            case TypeConst.LONG:
                return random.nextDouble() * (max - min) + min;
            case TypeConst.INT:
                return random.nextInt((int)(max - min + 1)) + (int)min;
            case TypeConst.BOOLEAN:
                return random.nextBoolean();
            case TypeConst.FLOAT:
                return random.nextFloat() * ( (float)(max - min) ) + (float)min;
            case TypeConst.DOUBLE:
                return random.nextDouble() * ( (double)(max - min) ) + (double)min;
            default:
                throw new IllegalArgumentException("Field type not known: " + type);
        }
    }
}
