package com.sivalabs.jcart.admin.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Getter;

/**
 * @author Siva
 *
 */
public final class MenuConfiguration
{

    @Getter
    private static Map<String, String> MENU_URL_PATTERn_MAP = new HashMap<>();

    static
    {
        MENU_URL_PATTERn_MAP.put("/home", "Home");
        MENU_URL_PATTERn_MAP.put("/categories", "Categories");
        MENU_URL_PATTERn_MAP.put("/products", "Products");
        MENU_URL_PATTERn_MAP.put("/orders", "Orders");
        MENU_URL_PATTERn_MAP.put("/customers", "Customers");
        MENU_URL_PATTERn_MAP.put("/users", "Users");
        MENU_URL_PATTERn_MAP.put("/roles", "Roles");
        MENU_URL_PATTERn_MAP.put("/permissions", "Permissions");
    }

    private MenuConfiguration()
    {
        super();
    }

    public static String getMatchingMenu(String uri)
    {
        Set<String> keySet = MENU_URL_PATTERn_MAP.keySet();
        for (String key : keySet)
        {
            if (uri.startsWith(key))
            {
                return MENU_URL_PATTERn_MAP.get(key);
            }
        }
        return "";
    }
}
