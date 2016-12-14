/**
 * 
 */
package com.sivalabs.jcart.admin.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Siva
 *
 */
public class MenuConfiguration
{
    private static Map<String, String> menu_url_pattern_map = new HashMap<>();

    static
    {
        menu_url_pattern_map.put("/home", "Home");
        menu_url_pattern_map.put("/categories", "Categories");
        menu_url_pattern_map.put("/products", "Products");
        menu_url_pattern_map.put("/orders", "Orders");
        menu_url_pattern_map.put("/customers", "Customers");
        menu_url_pattern_map.put("/users", "Users");
        menu_url_pattern_map.put("/roles", "Roles");
        menu_url_pattern_map.put("/permissions", "Permissions");

    }

    public static Map<String, String> getMenuUrlPatternMap()
    {
        return Collections.unmodifiableMap(menu_url_pattern_map);
    }

    public static String getMatchingMenu(String uri)
    {
        Set<String> keySet = menu_url_pattern_map.keySet();
        for (String key : keySet)
        {
            if (uri.startsWith(key))
            {
                return menu_url_pattern_map.get(key);
            }
        }
        return "";
    }
}
