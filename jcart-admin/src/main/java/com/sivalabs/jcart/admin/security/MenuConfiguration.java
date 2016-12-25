/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sivalabs.jcart.admin.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

/**
 * @author Siva
 * @author rajakolli
 *
 */
public final class MenuConfiguration
{

    @Getter
    private static Map<String, String> menuUrlPatternMap = new HashMap<>();

    static
    {
        menuUrlPatternMap.put("/home", "Home");
        menuUrlPatternMap.put("/categories", "Categories");
        menuUrlPatternMap.put("/products", "Products");
        menuUrlPatternMap.put("/orders", "Orders");
        menuUrlPatternMap.put("/customers", "Customers");
        menuUrlPatternMap.put("/users", "Users");
        menuUrlPatternMap.put("/roles", "Roles");
        menuUrlPatternMap.put("/permissions", "Permissions");
    }

    private MenuConfiguration()
    {
        super();
    }

    public static String getMatchingMenu(String uri)
    {
        Set<String> keySet = menuUrlPatternMap.keySet();
        for (String key : keySet)
        {
            if (uri.startsWith(key))
            {
                return menuUrlPatternMap.get(key);
            }
        }
        return "";
    }
}
